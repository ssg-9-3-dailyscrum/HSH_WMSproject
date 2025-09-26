package main.java.com.hsh.Dao.daoImpl;

import main.java.com.hsh.Dao.AdminLoginDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLoginDaoImpl implements AdminLoginDao {

    private static final String URL = "jdbc:mysql://gondola.proxy.rlwy.net:28068/railway";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "HCZMeXYxmlIqJvUCuMnALuhtfMlIbllC";


    @Override
    public boolean adminLogin(String adminLoginId, String adminLoginPwd) {
        String sql = "SELECT COUNT(*) FROM Admin WHERE admin_login_id = ? AND password = ?";
        try (
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, adminLoginId);
            pstmt.setString(2, adminLoginPwd);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // 1 이상이면 로그인 성공
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 실제 서비스에서는 로깅 처리 권장
        }
        return false; // 로그인 실패
    }
}
