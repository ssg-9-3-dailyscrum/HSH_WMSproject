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

    @Override
    public boolean requestInbound(InboundRequestDto request) {
        String sql = "{CALL sp_request_inbound(?, ?, ?, ?, ?)}"; // 프로시저 그대로 사용
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, request.memberId);
            cs.setInt(2, request.sectionId);
            cs.setInt(3, request.warehouseId);
            cs.setInt(4, request.productId);
            cs.setInt(5, request.quantity);

            cs.execute(); // CURDATE()가 프로시저 안에서 자동으로 들어가므로 Java에서는 별도 처리 필요 없음
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean approveInbound(int inboundId, int approverId) {
        String sql = "{CALL sp_approve_inbound(?, ?, ?, ?)}";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, inboundId);
            cs.setInt(2, approverId);
            cs.registerOutParameter(3, Types.INTEGER);      // result_code
            cs.registerOutParameter(4, Types.VARCHAR);      // result_msg

            cs.execute();

            int code = cs.getInt(3);
            String msg = cs.getString(4);
            System.out.println(msg);  // Java에서 메시지 출력
            return code == 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean cancelInbound(int inboundId) {
        String sql = "{CALL sp_delete_inbound(?, ?, ?)}";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, inboundId);
            cs.registerOutParameter(2, Types.INTEGER);   // result_code
            cs.registerOutParameter(3, Types.VARCHAR);   // result_msg

            cs.execute();

            int code = cs.getInt(2);
            String msg = cs.getString(3);
            System.out.println(msg);  // 메시지 출력
            return code == 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateInboundByMember(InboundRequestDto request) {
        // 기존에 구현 안 되어 있던 부분을 채움
        return updateInboundByMember(request.inboundId, request.memberId, request.productId, request.quantity);
    }



    @Override
    public boolean deleteInbound(int inboundId) {
        return cancelInbound(inboundId);
    }

    @Override
    public List<InboundResponseDto> getAllInbound(int userType, int userId) {
        List<InboundResponseDto> list = new ArrayList<>();

        if (userType == 3) { // 회원이면 접근 불가
            System.out.println("권한 없음: 관리자만 전체 조회 가능");
            return list;
        }

        String sql = "{CALL sp_get_all_inbound_by_admin(?)}";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, userId); // admin_id
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
                        java.sql.Date date = rs.getDate("inbound_date");
                        if (date != null) dto.inboundDate = date.toLocalDate();
                        list.add(dto);
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    // InboundDaoImpl.java
    @Override
    public boolean updateInboundByMember(int inboundId, int memberId, int productId, int quantity) {
        String sql = "{CALL sp_update_inbound_member(?, ?, ?, ?)}"; // OUT 파라미터 제거
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, inboundId);
            cs.setInt(2, memberId);
            cs.setInt(3, productId);
            cs.setInt(4, quantity);

            cs.execute();
            return true; // 성공이면 true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }







    @Override
    public InboundResponseDto getInboundDetail(int inboundId, int userType, int userId) {
        // userType이 3(회원)이면 바로 null 반환
        if (userType == 3) return null;

        String sql = "{CALL sp_get_inbound_detail(?, ?)}"; // 2개 인자
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, inboundId);
            cs.setInt(2, userId); // 관리자 ID
            boolean hasResult = cs.execute();
            if (hasResult) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) {
                        InboundResponseDto dto = new InboundResponseDto();
                        dto.inboundId = rs.getInt("inbound_id");
                        dto.memberId = rs.getInt("member_id");
                        dto.sectionId = rs.getInt("section_id");
                        dto.warehouseId = rs.getInt("warehouse_id");
                        dto.status = rs.getString("status");
                        Date date = rs.getDate("inbound_date");
                        if (date != null) dto.inboundDate = date.toLocalDate();
                        return dto;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}