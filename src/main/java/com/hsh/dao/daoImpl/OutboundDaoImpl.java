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
        // 구현 내용 그대로
        return false; // 예시
    }

    @Override
    public boolean approveOutbound(int outboundId, int adminId) {
        // 구현 내용 그대로
        return false; // 예시
    }

    @Override
    public boolean cancelOutbound(int outboundId) {
        // 구현 내용 그대로
        return false; // 예시
    }

    @Override
    public boolean updateOutboundRequest(OutboundRequestDto req) {
        return false; // 예시
    }

    @Override
    public OutboundResponseDto getOutboundDetail(int outboundId, int userType, int userId) {
        return null; // 예시
    }

    @Override
    public List<OutboundResponseDto> getPendingOutboundRequests(int userType, int userId) {
        return new ArrayList<>();
    }

    @Override
    public List<OutboundResponseDto> getAllOutbound(int adminId, String role) {
        return new ArrayList<>();
    }

    @Override
    public String getUserRole(int userId) {
        return "총관리자"; // 테스트용, DB 연결시 실제 SQL로 대체
    }
}
