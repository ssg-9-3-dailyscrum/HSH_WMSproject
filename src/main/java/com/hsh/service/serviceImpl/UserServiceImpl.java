//package main.java.com.hsh.service.seviceImpl;
//
//
//import com.hsh.dao.UserDAO;
//import com.hsh.dao.daoImpl.UserDaoImpl;
//import com.hsh.domain.vo.UserVo;
//import com.hsh.util.DBUtil; // DB 연결 유틸리티
//import common.ErrorCode; // 에러 코드 참조
//import exception.UserException; // 사용자 정의 예외 클래스 (필요에 따라 정의)
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class UserServiceImpl implements UserService {
//
//    // DAO 의존성 주입
//    private final UserDAO userDAO = new UserDaoImpl();
//
//    @Override
//    public boolean registerUser(UserVo user) {
//        Connection conn = null;
//        boolean result = false;
//
//        try {
//            conn = DBUtil.getConnection();
//            conn.setAutoCommit(false); // 1. 트랜잭션 시작
//
//            // 2. [비즈니스 로직] 아이디 중복 검사 실행
//            //    ID가 중복될 경우, DAO에서 true를 반환합니다.
//            if (userDAO.isIdDuplicate(conn, user.getUserLoginId())) {
//                // 중복될 경우, 사용자 정의 예외 발생 (ErrorCode.ID_DUPLICATE)
//                throw new UserException(ErrorCode.ID_DUPLICATE.getError());
//            }
//
//            // 3. DAO 호출 (DB INSERT 실행)
//            int rowsAffected = userDAO.register(conn, user);
//
//            if (rowsAffected > 0) {
//                conn.commit(); // 4. 성공 시 커밋
//                result = true;
//            } else {
//                conn.rollback(); // 실패 시 롤백
//            }
//
//        } catch (UserException e) {
//            // 비즈니스 예외 처리 (ID 중복)
//            System.err.println("회원 등록 오류: " + e.getMessage());
//            try { if (conn != null) conn.rollback(); } catch (SQLException rollbackEx) {}
//        } catch (Exception e) {
//            // SQL 오류 등 기타 예외 처리
//            System.err.println("시스템 오류: " + e.getMessage());
//            try { if (conn != null) conn.rollback(); } catch (SQLException rollbackEx) {}
//        } finally {
//            try {
//                if (conn != null) conn.close(); // 5. 연결 종료
//            } catch (SQLException closeEx) {}
//        }
//        return result;
//    }
//
//
//}