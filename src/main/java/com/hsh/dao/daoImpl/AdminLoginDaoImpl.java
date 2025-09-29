package main.java.com.hsh.dao.daoImpl;

import main.java.com.hsh.dao.AdminLoginDao;
import main.java.com.hsh.domain.vo.AdminVo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminLoginDaoImpl implements AdminLoginDao {

    private static final String URL = "jdbc:mysql://gondola.proxy.rlwy.net:28068/railway?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "HCZMeXYxmlIqJvUCuMnALuhtfMlIbllC";

    // 로그인
    @Override
    public AdminVo adminLogin(String adminLoginId, String adminLoginPwd) {
        String sql = "SELECT * FROM Admin WHERE admin_login_id = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, adminLoginId);
            pstmt.setString(2, adminLoginPwd);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    AdminVo admin = new AdminVo();
                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setAdminLoginId(rs.getString("admin_login_id"));
                    admin.setPassword(rs.getString("password"));
                    admin.setAdminName(rs.getString("admin_name"));
                    admin.setDepartment(rs.getString("department"));
                    admin.setRole(rs.getString("role"));
                    admin.setAdminStatus(rs.getString("admin_status"));
                    return admin;
                }
            }
        } catch (SQLException e) {
            System.err.println("관리자 로그인 오류: " + e.getMessage());
        }
        return null;
    }

    // 관리자 생성
    @Override
    public int insertAdmin(AdminVo admin) {
        String sql = "INSERT INTO Admin (admin_login_id, password, admin_name, department, role, admin_status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, admin.getAdminLoginId());
            pstmt.setString(2, admin.getPassword());
            pstmt.setString(3, admin.getAdminName());
            pstmt.setString(4, admin.getDepartment());
            pstmt.setString(5, admin.getRole());
            pstmt.setString(6, admin.getAdminStatus()); // 상태 저장

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("관리자 등록 오류: " + e.getMessage());
        }
        return 0;
    }

    // 관리자 수정
    @Override
    public int updateAdmin(AdminVo admin) {
        String sql = "UPDATE Admin SET password = ?, admin_name = ?, department = ?, role = ?, admin_status = ? WHERE admin_login_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, admin.getPassword());
            pstmt.setString(2, admin.getAdminName());
            pstmt.setString(3, admin.getDepartment());
            pstmt.setString(4, admin.getRole());
            pstmt.setString(5, admin.getAdminStatus()); /// 현재 상태
            pstmt.setString(6, admin.getAdminLoginId());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("관리자 수정 오류: " + e.getMessage());
        }
        return 0;
    }


    // 관리자 비활성화
    @Override
    public int deactivateAdmin(String adminLoginId) {
        String sql = "UPDATE Admin SET admin_status = 'N' WHERE admin_login_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, adminLoginId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("관리자 비활성화 오류: " + e.getMessage());
        }
        return 0;
    }


    // 총관리자 제외 모든 계정 조회
    @Override
    public List<AdminVo> selectAllExceptSuperAdmin() {
        List<AdminVo> admins = new ArrayList<>();
        String sql = "SELECT * FROM Admin WHERE role != '총관리자'";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                AdminVo admin = new AdminVo();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setAdminLoginId(rs.getString("admin_login_id"));
                admin.setPassword(rs.getString("password"));
                admin.setAdminName(rs.getString("admin_name"));
                admin.setDepartment(rs.getString("department"));
                admin.setRole(rs.getString("role"));
                admin.setAdminStatus(rs.getString("admin_status")); ///상태가져오기
                admins.add(admin);
            }

        } catch (SQLException e) {
            System.err.println("계정 조회 오류: " + e.getMessage());
        }

        return admins;
    }

    //  관리자 ID로 조회
    @Override
    public AdminVo getAdminById(Integer adminId) {
        String sql = "SELECT * FROM Admin WHERE admin_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, adminId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    AdminVo admin = new AdminVo();
                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setAdminLoginId(rs.getString("admin_login_id"));
                    admin.setPassword(rs.getString("password"));
                    admin.setAdminName(rs.getString("admin_name"));
                    admin.setDepartment(rs.getString("department"));
                    admin.setRole(rs.getString("role"));
                    admin.setAdminStatus(rs.getString("admin_status"));
                    return admin;
                }
            }
        } catch (SQLException e) {
            System.err.println("관리자 단일 조회 오류: " + e.getMessage());
        }
        return null;
    }
}
