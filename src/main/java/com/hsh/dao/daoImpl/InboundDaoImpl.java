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
        String sql = "{CALL sp_request_inbound(?, ?, ?, ?, ?)}";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, request.memberId);
            cs.setInt(2, request.sectionId);
            cs.setInt(3, request.warehouseId);
            cs.setInt(4, request.productId);
            cs.setInt(5, request.quantity);
            cs.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean approveInbound(int inboundId, int approverId) {
        String sql = "{CALL sp_approve_inbound(?, ?)}";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, inboundId);
            cs.setInt(2, approverId);
            cs.execute();
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean cancelInbound(int inboundId) {
        String sql = "{CALL sp_delete_inbound(?)}";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, inboundId);
            cs.execute();
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean deleteInbound(int inboundId) {
        return cancelInbound(inboundId);
    }

    @Override
    public List<InboundResponseDto> getAllInbound(int userType, int userId) {
        List<InboundResponseDto> list = new ArrayList<>();
        String sql = "{CALL sp_get_inbound_list(?, ?)}";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, userType);
            cs.setInt(2, userId);
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
                        Date date = rs.getDate("inbound_date");
                        if (date != null) dto.inboundDate = date.toLocalDate();
                        list.add(dto);
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public InboundResponseDto getInboundDetail(int inboundId, int userType, int userId) {
        String sql = "{CALL sp_get_inbound_detail(?, ?, ?)}";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, inboundId);
            cs.setInt(2, userType);
            cs.setInt(3, userId);
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
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}