package main.java.com.hsh.dao.daoImpl;

import main.java.com.hsh.dao.OutboundDao;
import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundDetailResponseDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OutboundDaoImpl implements OutboundDao {

    private static final String URL = "jdbc:mysql://gondola.proxy.rlwy.net:28068/railway";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "HCZMeXYxmlIqJvUCuMnALuhtfMlIbllC";

    @Override
    public boolean cancelOutbound(int outboundId) {
        String sql = "{CALL sp_cancel_outbound(?)}"; // DB에 cancel 프로시저가 있다면
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, outboundId);
            int result = cs.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean requestOutbound(OutboundRequestDto request) {
        String sql = "{CALL sp_request_outbound(?, ?, ?)}"; // JSON 1개 포함
        boolean success = false;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            conn.setAutoCommit(false);

            // Java List<OutboundDetailDto> → JSON 문자열로 변환
            String jsonProducts = "[";
            for (int i = 0; i < request.products.size(); i++) {
                var d = request.products.get(i);
                jsonProducts += String.format("{\"product_id\":%d,\"quantity\":%d}", d.productId, d.quantity);
                if (i < request.products.size() - 1) jsonProducts += ",";
            }
            jsonProducts += "]";

            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.setInt(1, request.memberId);
                cs.setInt(2, request.inventoryId);
                cs.setString(3, jsonProducts); // JSON 전달

                cs.executeUpdate();
                success = true;
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }



    @Override
    public boolean approveOutbound(int outboundId) {
        return false;
    }

    @Override
    public boolean updateOutboundRequest(OutboundRequestDto request) {
        String sql = "{CALL sp_update_outbound_request(?, ?, ?)}"; // 프로시저 호출
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, request.outboundId);
            cs.setInt(2, request.memberId);
            cs.setInt(3, request.inventoryId);

            int result = cs.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // 대기 중인 출고 요청 리스트 조회
    @Override
    public List<OutboundResponseDto> getPendingOutboundRequests() {
        List<OutboundResponseDto> list = new ArrayList<>();
        String sql = "{CALL sp_get_pending_outbound_requests()}";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            boolean hasResult = cs.execute();
            if (hasResult) {
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {
                        OutboundResponseDto dto = new OutboundResponseDto();
                        dto.outboundId = rs.getInt("outbound_id");
                        dto.memberId = rs.getInt("member_id");
                        dto.memberName = rs.getString("name");
                        dto.inventoryId = rs.getInt("inventory_id");
                        Date date = rs.getDate("outbound_date");
                        if (date != null) dto.outboundDate = date.toLocalDate();
                        dto.status = rs.getString("outbound_status");
                        dto.details = new ArrayList<>(); // 상세 리스트 초기화
                        list.add(dto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // 출고 요청 상세 조회
    @Override
    public OutboundResponseDto getOutboundDetail(int outboundId) {
        String sql = "{CALL sp_get_outbound_detail(?)}";
        OutboundResponseDto dto = null;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, outboundId);
            boolean hasResult = cs.execute();

            if (hasResult) {
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {
                        if (dto == null) {
                            dto = new OutboundResponseDto();
                            dto.outboundId = rs.getInt("outbound_id");
                            dto.memberId = rs.getInt("member_id");
                            dto.memberName = rs.getString("name");
                            dto.inventoryId = rs.getInt("inventory_id");
                            Date date = rs.getDate("outbound_date");
                            if (date != null) dto.outboundDate = date.toLocalDate();
                            dto.status = rs.getString("outbound_status");
                            dto.details = new ArrayList<>();
                        }

                        // Response DTO 사용
                        OutboundDetailResponseDto detail = new OutboundDetailResponseDto();
                        detail.outboundDetailId = rs.getInt("outbound_detail_id");
                        detail.productId = rs.getInt("product_id");
                        detail.productName = rs.getString("product_name");
                        detail.quantity = rs.getInt("quantity");

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
    public List<OutboundResponseDto> getAllOutbound() {
        List<OutboundResponseDto> list = new ArrayList<>();
        String sql = "{CALL sp_get_all_outbound()}";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            boolean hasResult = cs.execute();
            if (hasResult) {
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {
                        OutboundResponseDto dto = new OutboundResponseDto();
                        dto.outboundId = rs.getInt("outbound_id");
                        dto.memberId = rs.getInt("member_id");
                        dto.memberName = rs.getString("member_name");
                        dto.inventoryId = rs.getInt("inventory_id");
                        Date date = rs.getDate("outbound_date");
                        if (date != null) dto.outboundDate = date.toLocalDate();
                        dto.status = rs.getString("outbound_status");
                        dto.details = new ArrayList<>();
                        OutboundDetailResponseDto detail = new OutboundDetailResponseDto();
                        detail.outboundDetailId = rs.getInt("outbound_detail_id");
                        detail.productId = rs.getInt("product_id");
                        detail.productName = rs.getString("product_name");
                        detail.quantity = rs.getInt("quantity");
                        dto.details.add(detail);
                        list.add(dto);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }



}
