package main.java.com.hsh.Dao.daoImpl;

import main.java.com.hsh.Dao.AdminLoginDao;
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
        String sql = "INSERT INTO Admin (admin_login_id, password, admin_name, department, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, admin.getAdminLoginId());
            pstmt.setString(2, admin.getPassword());
            pstmt.setString(3, admin.getAdminName());
            pstmt.setString(4, admin.getDepartment());
            pstmt.setString(5, admin.getRole());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("관리자 등록 오류: " + e.getMessage());
        }
        return 0;
    }

    // 관리자 수정
    @Override
    public int updateAdmin(AdminVo admin) {
        String sql = "UPDATE Admin SET password = ?, admin_name = ?, department = ?, role = ? WHERE admin_login_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, admin.getPassword());
            pstmt.setString(2, admin.getAdminName());
            pstmt.setString(3, admin.getDepartment());
            pstmt.setString(4, admin.getRole());
            pstmt.setString(5, admin.getAdminLoginId());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("관리자 수정 오류: " + e.getMessage());
        }
        return 0;
    }

    // 관리자 삭제
    @Override
    public int deleteAdmin(String adminLoginId) {
        String sql = "DELETE FROM Admin WHERE admin_login_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, adminLoginId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("관리자 삭제 오류: " + e.getMessage());
        }
        return 0;
    }

    // 총관리자 제외 모든 계정 조회
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
                admins.add(admin);
            }

        } catch (SQLException e) {
            System.err.println("계정 조회 오류: " + e.getMessage());
        }

        return admins;
    }
}
