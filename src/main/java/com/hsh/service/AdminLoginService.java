package main.java.com.hsh.service;

import main.java.com.hsh.domain.vo.AdminVo;
import java.util.List;

public interface AdminLoginService {

    AdminVo adminLogin(String adminLoginId, String adminLoginPwd);

    /// 관리자 생성
    int createAdmin(AdminVo newAdmin);

    /// 관리자 수정
    int updateAdmin(AdminVo updatedAdmin);

    /// 총관리자를 제외한 모든 회원과 관리자들 조회
    List<AdminVo> selectAllExceptSuperAdmin();

    /// ID로 관리자 조회
    AdminVo getAdminById(Integer adminId);

    /// 관리자 활성화 비활성화
    int deactivateAdmin(String adminLoginId);

}
