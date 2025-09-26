package main.java.com.hsh.Dao;

///AdminDao -> 관리자 기능을 제공해야 하는 규칙만 있고 실제 동작은 없음.

import com.hsh.admin.domain.vo.AdminVo;// Admin 테이블의 데이터 객체
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AdminDao {
    /** 1. 새로운 관리자를 등록 */
    int register(Connection conn, AdminVo admin) throws SQLException;

    /** 2. 특정 관리자의 일반 정보를 수정(회원 정보 수정) */
    int update(Connection conn, AdminVo admin) throws SQLException;

    /** 3. 관리자의 비밀번호를 수정 */
    int updatePassword(Connection conn, Long adminId, String newHashedPassword) throws SQLException;

    /** 4. 특정 관리자의 권한(Role)을 수정 (권한 설정) */
    int updateRole(Connection conn, Long adminId, String newRole) throws SQLException;

    /** 5. 특정 관리자의 부서 및 직급 정보를 수정합니다. */
    int updateDeptPos(Connection conn, Long adminId, String department, String position) throws SQLException;

    /** 6. 관리자 계정을 비활성화  */
    int delete(Connection conn, Long adminId) throws SQLException;

    /** 7. ID를 기준으로 특정 관리자의 상세 정보를 조회*/
    AdminVo findById(Connection conn, Long adminId) throws SQLException;

    /** 8. 권한(Role)을 기준으로 관리자 목록을 조회. */
    List<AdminVo> findByRole(Connection conn, String role) throws SQLException;

    /** 9. 모든 활성 관리자 목록을 조회 */
    List<AdminVo> findAll(Connection conn) throws SQLException;

    /** 10. 로그인 ID를 기반으로 관리자 상세 정보를 조회. */
    AdminVo findByLoginId(Connection conn, String adminLoginId) throws SQLException;
}

