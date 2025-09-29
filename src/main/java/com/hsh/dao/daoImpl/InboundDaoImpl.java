package main.java.com.hsh.dao.daoImpl;

import main.java.com.hsh.dao.InboundDao;
import main.java.com.hsh.domain.dto.request.InboundRequestDto;
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
    public List<InboundResponseDto> getInboundList(int userType, int userId) {
        List<InboundResponseDto> list = new ArrayList<>();
        if (userType == 3) return list;

        String sql = "{CALL sp_get_all_inbound_by_admin(?)}";
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, userId);
            boolean hasResult = cs.execute();

            if (hasResult) {
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {
                        InboundResponseDto dto = new InboundResponseDto();
                        dto.inboundId = rs.getInt("inbound_id");
                        dto.memberId = rs.getInt("member_id");
                        dto.sectionId = rs.getInt("section_id");
                        dto.warehouseId = rs.getInt("warehouse_id");
                        dto.status = rs.getString("status");
                        dto.quantity = rs.getInt("quantity"); // DB에서 수량 가져오기
                        Date date = rs.getDate("inbound_date");
                        if (date != null) dto.inboundDate = date.toLocalDate();
                        list.add(dto);
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // 나머지 기존 메서드 그대로 사용 (request, approve, cancel 등)
    @Override
    public boolean requestInbound(InboundRequestDto req) { /* 그대로 */ return false; }

    @Override
    public boolean approveInbound(int inboundId, int approverId) {
        String sql = "UPDATE railway.Inbound SET status='승인' WHERE inbound_id=? AND status='대기'";

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
    public boolean cancelInbound(int inboundId) { /* 그대로 */ return false; }

    @Override
    public boolean updateInboundByMember(InboundRequestDto req) { /* 그대로 */ return false; }

    @Override
    public InboundResponseDto getInboundDetail(int inboundId, int userType, int userId) { /* 그대로 */ return null; }

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
                        return null; // 비활성 계정
                    }
                    return rs.getString("role"); // 총관리자 / 창고관리자
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
