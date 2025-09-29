package main.java.com.hsh.service;

import main.java.com.hsh.domain.vo.AdminVo;
import java.util.List;

public interface AdminLoginService {

    AdminVo adminLogin(String adminLoginId, String adminLoginPwd);

    int createAdmin(AdminVo newAdmin);

    int updateAdmin(AdminVo updatedAdmin);

    int deleteAdmin(String adminLoginId);

    /// 총관리자를 제외한 모든 회원과 관리자들 조회
    List<AdminVo> selectAllExceptSuperAdmin();

    /// ID로 관리자 조회
    AdminVo getAdminById(Integer adminId);
}
