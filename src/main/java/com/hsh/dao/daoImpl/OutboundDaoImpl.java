package main.java.com.hsh.dao.daoImpl;

import main.java.com.hsh.dao.OutboundDao;
import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OutboundDaoImpl implements OutboundDao {

    private static final String URL = "jdbc:mysql://gondola.proxy.rlwy.net:28068/railway?serverTimezone=Asia/Seoul";
    private static final String USER = "root";
    private static final String PASSWORD = "HCZMeXYxmlIqJvUCuMnALuhtfMlIbllC";

    private Connection getConnection() throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException ignored) {}
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public boolean createOutboundRequest(OutboundRequestDto req) {
        String sql = "{CALL sp_request_outbound(?, ?, ?)}";
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, req.memberId);
            cs.setInt(2, req.inventoryId);

            // JSON 생성
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < req.products.size(); i++) {
                OutboundRequestDto.ProductQuantity p = req.products.get(i);
                sb.append(String.format("{\"product_id\":%d,\"quantity\":%d}", p.productId, p.quantity));
                if (i != req.products.size() - 1) sb.append(",");
            }
            sb.append("]");
            cs.setString(3, sb.toString());

            cs.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean approveOutbound(int outboundId, int adminId) {
        String checkSql = "SELECT outbound_status FROM Outbound WHERE outbound_id = ?";
        String approveSql = "CALL sp_approve_outbound(?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // 1. 존재 여부 및 상태 체크
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, outboundId);
                ResultSet rs = psCheck.executeQuery();

                if (!rs.next()) { // 존재하지 않음
                    conn.rollback();
                    return false;
                }

                String status = rs.getString("outbound_status");
                if (!"대기".equals(status)) { // 대기 상태 아니면 승인 불가
                    conn.rollback();
                    return false;
                }
            }

            // 2. 승인 프로시저 호출
            try (PreparedStatement psApprove = conn.prepareStatement(approveSql)) {
                psApprove.setInt(1, outboundId);
                int affected = psApprove.executeUpdate();

                if (affected == 0) {
                    conn.rollback();
                    return false;
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    @Override
    public OutboundResponseDto getOutboundDetail(int outboundId, int userType, int userId) {
        String sql = "{CALL sp_get_outbound_detail(?)}";
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, outboundId);
            ResultSet rs = cs.executeQuery();

            OutboundResponseDto dto = null;
            while (rs.next()) {
                if (dto == null) {
                    dto = new OutboundResponseDto();
                    dto.outboundId = rs.getInt("outbound_id");
                    dto.memberId = rs.getInt("member_id");
                    dto.memberName = rs.getString("name");
                    dto.inventoryId = rs.getInt("inventory_id");
                    dto.outboundStatus = rs.getString("outbound_status");
                    dto.outboundDate = rs.getTimestamp("outbound_date").toLocalDateTime();
                }
                OutboundRequestDto.ProductQuantity detail = new OutboundRequestDto.ProductQuantity(
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );
                detail.productName = rs.getString("product_name");
                dto.products.add(detail);
            }
            return dto;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OutboundResponseDto> getPendingOutboundRequests(int userType, int userId) {
        List<OutboundResponseDto> list = new ArrayList<>();

        String sql = "CALL sp_get_pending_outbound_requests()"; // 회원/관리자 구분은 View/Service에서 처리

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OutboundResponseDto dto = new OutboundResponseDto();
                dto.outboundId = rs.getInt("outbound_id");
                dto.memberId = rs.getInt("member_id");
                dto.memberName = rs.getString("member_name"); // 여기 수정됨
                dto.inventoryId = rs.getInt("inventory_id");
                dto.outboundStatus = "대기"; // 고정
                // 합계 수량 따로 쓰고 싶으면:
                dto.products.add(new OutboundRequestDto.ProductQuantity(0, rs.getInt("total_quantity")));
                list.add(dto);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 회원이면 본인 요청만 필터링
        if (userType == 3) {
            list.removeIf(dto -> dto.memberId != userId);
        }

        return list;
    }

    @Override
    public boolean updateOutboundRequest(OutboundRequestDto req) {
        String sql = "{CALL sp_update_outbound_request(?, ?, ?)}";
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, req.outboundId);
            cs.setInt(2, req.memberId);
            cs.setInt(3, req.inventoryId);
            cs.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OutboundResponseDto> getAllOutbound(int adminId, String role) {
        List<OutboundResponseDto> list = new ArrayList<>();
        String sql = "{CALL sp_get_all_outbound()}";

        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                OutboundResponseDto dto = new OutboundResponseDto();
                dto.outboundId = rs.getInt("outbound_id");
                dto.memberId = rs.getInt("member_id");
                dto.memberName = rs.getString("member_name");
                dto.inventoryId = rs.getInt("inventory_id");
                dto.outboundStatus = rs.getString("outbound_status");
                dto.outboundDate = rs.getTimestamp("outbound_date").toLocalDateTime();

                OutboundRequestDto.ProductQuantity detail = new OutboundRequestDto.ProductQuantity(
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );
                detail.productName = rs.getString("product_name");
                dto.products.add(detail);

                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public boolean cancelOutbound(int outboundId) {
        // 취소는 update로 상태 변경 가능
        return updateOutboundRequest(new OutboundRequestDto() {{
            outboundId = outboundId;
        }});
    }

    @Override
    public String getUserRole(int userId) {
        return "총관리자"; // 테스트용
    }
}