package main.java.com.hsh.dao;

import main.java.com.hsh.domain.vo.AdminVo;
import java.util.List;

public interface AdminLoginDao {

    // 로그인
    AdminVo adminLogin(String adminLoginId, String adminLoginPwd);

    // 관리자 계정 생성
    int insertAdmin(AdminVo admin);

    // 관리자 정보 수정
    int updateAdmin(AdminVo admin);

    //관리자 권한 활성화 비활성화
    int deactivateAdmin(String adminLoginId);

    // 총관리자가 모든 사용자를 볼 수 있게.
    List<AdminVo> selectAllExceptSuperAdmin();

    //  관리자 ID로 단일 조회
    AdminVo getAdminById(Integer adminId);
}
