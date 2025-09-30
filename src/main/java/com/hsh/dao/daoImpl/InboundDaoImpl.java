package main.java.com.hsh.dao.daoImpl;

import main.java.com.hsh.dao.InboundDao;
import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundDetailDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InboundDaoImpl implements InboundDao {

    private static final String URL = "jdbc:mysql://gondola.proxy.rlwy.net:28068/railway";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "HCZMeXYxmlIqJvUCuMnALuhtfMlIbllC";

    private Connection getConnection() throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException ignored) {}
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Override
    public boolean requestInbound(InboundRequestDto req) {
        String sql = "INSERT INTO Inbound (member_id, section_id, warehouse_id, status) VALUES (?, ?, ?, '대기')";
        String detailSql = "INSERT INTO InboundDetail (inbound_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Inbound 등록
            ps.setInt(1, req.memberId);
            ps.setInt(2, req.sectionId);
            ps.setInt(3, req.warehouseId);
            int affected = ps.executeUpdate();
            if (affected == 0) return false;

            // 생성된 inbound_id 가져오기
            int inboundId;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) inboundId = rs.getInt(1);
                else return false;
            }

            // InboundDetail 등록
            try (PreparedStatement psDetail = conn.prepareStatement(detailSql)) {
                psDetail.setInt(1, inboundId);
                psDetail.setInt(2, req.productId);
                psDetail.setInt(3, req.quantity);
                psDetail.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean approveInbound(int inboundId, int approverId) {
        // 단일 행만 업데이트하도록 LIMIT 1 추가
        String sql = "UPDATE Inbound SET status='승인', inbound_date=NOW() WHERE inbound_id=? AND status='대기' LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, inboundId);
            int affected = ps.executeUpdate();

            if (affected == 0) {
                System.out.println("[DEBUG] 승인 실패 - 존재하지 않거나 이미 승인됨 (inboundId=" + inboundId + ")");
                return false;
            }
            System.out.println("[DEBUG] 승인 성공 (inboundId=" + inboundId + ")");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    @Override
    public boolean cancelInboundByMember(int inboundId, int memberId) {
        String deleteDetailSql = "DELETE FROM InboundDetail WHERE inbound_id = ?";
        String deleteInboundSql = "DELETE FROM Inbound WHERE inbound_id = ? AND member_id = ? AND status = '대기'";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psDetail = conn.prepareStatement(deleteDetailSql)) {
                psDetail.setInt(1, inboundId);
                psDetail.executeUpdate();
            }

            try (PreparedStatement psInbound = conn.prepareStatement(deleteInboundSql)) {
                psInbound.setInt(1, inboundId);
                psInbound.setInt(2, memberId);
                int affected = psInbound.executeUpdate();

                if (affected == 0) {
                    conn.rollback();
                    return false; // 본인 요청 아님 or 이미 승인됨
                } else {
                    conn.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateInboundByMember(InboundRequestDto req) {
        String sql = "UPDATE InboundDetail d "
                + "JOIN Inbound i ON d.inbound_id = i.inbound_id "
                + "SET d.product_id = ?, d.quantity = ? "
                + "WHERE d.inbound_id = ? AND i.member_id = ? AND i.status = '대기'";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, req.productId);
            ps.setInt(2, req.quantity);
            ps.setInt(3, req.inboundId);
            ps.setInt(4, req.memberId);

            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<InboundResponseDto> getInboundList(int userType, int userId) {
        List<InboundResponseDto> list = new ArrayList<>();
        String sql;

        if (userType == 1) { // 관리자
            sql = "SELECT i.inbound_id, i.member_id, i.section_id, i.warehouse_id, i.status, i.inbound_date, "
                    + "SUM(d.quantity) AS quantity "
                    + "FROM Inbound i "
                    + "JOIN InboundDetail d ON i.inbound_id = d.inbound_id "
                    + "GROUP BY i.inbound_id, i.member_id, i.section_id, i.warehouse_id, i.status, i.inbound_date";
        } else { // 회원 → 본인만
            sql = "SELECT i.inbound_id, i.member_id, i.section_id, i.warehouse_id, i.status, i.inbound_date, "
                    + "SUM(d.quantity) AS quantity "
                    + "FROM Inbound i "
                    + "JOIN InboundDetail d ON i.inbound_id = d.inbound_id "
                    + "WHERE i.member_id = ? "
                    + "GROUP BY i.inbound_id, i.member_id, i.section_id, i.warehouse_id, i.status, i.inbound_date";
        }

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (userType != 1) ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InboundResponseDto dto = new InboundResponseDto();
                    dto.inboundId = rs.getInt("inbound_id");
                    dto.memberId = rs.getInt("member_id");
                    dto.sectionId = rs.getInt("section_id");
                    dto.warehouseId = rs.getInt("warehouse_id");
                    dto.status = rs.getString("status");
                    dto.quantity = rs.getInt("quantity");
                    java.sql.Date date = rs.getDate("inbound_date");
                    if (date != null) dto.inboundDate = date.toLocalDate();
                    list.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public InboundResponseDto getInboundDetail(int inboundId, int userType, int userId) {
        String sql = "SELECT i.inbound_id, i.member_id, i.section_id, i.warehouse_id, i.status, i.inbound_date, "
                + "SUM(d.quantity) AS total_quantity "
                + "FROM Inbound i "
                + "JOIN InboundDetail d ON i.inbound_id = d.inbound_id "
                + "WHERE i.inbound_id = ? "
                + (userType == 2 ? "AND i.member_id = ? " : "")
                + "GROUP BY i.inbound_id, i.member_id, i.section_id, i.warehouse_id, i.status, i.inbound_date";

        InboundResponseDto dto = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, inboundId);
            if (userType == 2) ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new InboundResponseDto();
                    dto.inboundId = rs.getInt("inbound_id");
                    dto.memberId = rs.getInt("member_id");
                    dto.sectionId = rs.getInt("section_id");
                    dto.warehouseId = rs.getInt("warehouse_id");
                    dto.status = rs.getString("status");
                    dto.quantity = rs.getInt("total_quantity");
                    java.sql.Date date = rs.getDate("inbound_date");
                    if (date != null) dto.inboundDate = date.toLocalDate();
                } else {
                    return null;
                }
            }

            String detailSql = "SELECT inbound_detail_id, product_id, quantity FROM InboundDetail WHERE inbound_id = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(detailSql)) {
                ps2.setInt(1, inboundId);
                try (ResultSet rs2 = ps2.executeQuery()) {
                    while (rs2.next()) {
                        InboundDetailDto detail = new InboundDetailDto();
                        detail.inboundDetailId = rs2.getInt("inbound_detail_id");
                        detail.productId = rs2.getInt("product_id");
                        detail.quantity = rs2.getInt("quantity");
                        dto.details.add(detail);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dto;
    }

    @Override
    public String getUserRole(int adminId) {
        String sql = "SELECT role, admin_status FROM Admin WHERE admin_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adminId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("admin_status");
                    if (!"Y".equalsIgnoreCase(status)) {
                        return null;
                    }
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
