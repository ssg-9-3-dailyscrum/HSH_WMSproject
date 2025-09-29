package main.java.com.hsh.Dao.daoImpl;

import main.java.com.hsh.Dao.UserLoginDao;
import main.java.com.hsh.domain.vo.UserVo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserLoginDaoImpl implements UserLoginDao {

    //DB 접속 정보
    private static final String URL = "jdbc:mysql://gondola.proxy.rlwy.net:28068/railway";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "HCZMeXYxmlIqJvUCuMnALuhtfMlIbllC";

    //DB 연결 객체를 반환
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // ResultSet의 데이터를 UserVo 객체로 매핑
    private UserVo mapResultSetToUserVo(ResultSet rs) throws SQLException {
        UserVo user = new UserVo();
        user.setMemberId(rs.getInt("member_id"));
        user.setUserLoginId(rs.getString("user_login_id"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setRole(rs.getString("role"));

        Timestamp joinTimestamp = rs.getTimestamp("join_date");
        if (joinTimestamp != null) {
            user.setJoinDate(joinTimestamp.toLocalDateTime());
        }

        user.setEmail(rs.getString("email"));
        user.setStatus(rs.getString("status"));
        return user;
    }

    // 1. 로그인
    @Override
    public UserVo userLogin(String userLoginId, String userLoginPwd) {
        String sql = "SELECT * FROM User WHERE user_login_id = ? AND password = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, userLoginId);
            pstmt.setString(2, userLoginPwd);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUserVo(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("사용자 로그인 오류 발생: " + e.getMessage());
        }
        return null;
    }

    // 2. 회원 정보 조회
    @Override
    public UserVo selectUserById(String userLoginId) {
        String sql = "SELECT * FROM User WHERE user_login_id = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, userLoginId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUserVo(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("사용자 정보 조회 오류 발생: " + e.getMessage());
        }
        return null;
    }

    // 3. 회원 정보 수정
    @Override
    public int updateUser(UserVo user) {
        String sql = "UPDATE User SET password = ?, phone = ?, address = ?, email = ? WHERE user_login_id = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUserLoginId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("사용자 정보 오류 발생: " + e.getMessage());
            return 0;
        }
    }

    // 4. 회원 상태 변경 Update
    @Override
    public int updateUserStatus(String userLoginId, String status) {
        String sql = "UPDATE User SET status = ? WHERE user_login_id = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, status);
            pstmt.setString(2, userLoginId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("사용자 상태 오류 발생: " + e.getMessage());
            return 0;
        }
    }

    // 5. ID와 비밀번호를 통해 DB에서 User 정보를 조회하여 반환
    @Override
    public UserVo selectUserByIdAndPassword(String id, String password) {
        return userLogin(id, password);
    }

    // 6. 회원가입
    @Override
    public int insertUser(UserVo newUser) {
        String sql = "INSERT INTO User (user_login_id, password, name, phone, address, role, email, status, join_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, newUser.getUserLoginId());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getName());
            pstmt.setString(4, newUser.getPhone());
            pstmt.setString(5, newUser.getAddress());
            pstmt.setString(6, newUser.getRole());
            pstmt.setString(7, newUser.getEmail());
            pstmt.setString(8, newUser.getStatus());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("회원가입 오류 발생: " + e.getMessage());
            return 0;
        }
    }

    // 7. 전체 회원 조회
    @Override
    public List<UserVo> selectAllUsers() {
        String sql = "SELECT * FROM User";
        List<UserVo> users = new ArrayList<>();

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                users.add(mapResultSetToUserVo(rs));
            }
        } catch (SQLException e) {
            System.err.println("전체 회원 조회 중 오류 발생: " + e.getMessage());
        }
        return users;
    }
}
