package main.java.com.hsh.controller;

import main.java.com.hsh.domain.vo.AdminVo;
import main.java.com.hsh.service.AdminLoginService;
import main.java.com.hsh.service.serviceImpl.AdminLoginServiceImpl;
import main.java.com.hsh.session.AdminSession;
import main.java.com.hsh.view.AdminManageView; // AdminManageView import

import java.util.List;

public class AdminLoginController {

    private static final AdminLoginController instance = new AdminLoginController();
    private final AdminLoginService adminLoginService;

    private AdminLoginController() {
        this.adminLoginService = AdminLoginServiceImpl.getInstance();
    }

    public static AdminLoginController getInstance() {
        return instance;
    }

    /// 관리자 로그인
    public boolean login(String id, String pw) {
        AdminVo admin = adminLoginService.adminLogin(id, pw);
        if (admin != null) {
            System.out.println(admin.getAdminName() + "님, 로그인 성공!");
            return true;
        }
        System.out.println("로그인 실패! 아이디 또는 비밀번호를 확인하세요.");
        return false;
    }

    /// 관리자 로그인 (AdminVo 반환)
    public AdminVo adminLogin(String id, String pw) {
        return adminLoginService.adminLogin(id, pw);
    }

    /// 로그아웃
    public void logout() {
        AdminSession.getInstance().logout();
        System.out.println("관리자 로그아웃 완료");
    }

    /// 관리자 회원가입
    public boolean registerAdmin(AdminVo newAdmin) {
        int result = adminLoginService.createAdmin(newAdmin);
        if (result > 0) {
            System.out.println(newAdmin.getAdminName() + "님 관리자 계정 생성 성공!");
            return true;
        }
        System.out.println("관리자 계정 생성 실패!");
        return false;
    }

    /// 관리자 정보 수정
    public boolean updateAdmin(AdminVo updatedAdmin) {
        int result = adminLoginService.updateAdmin(updatedAdmin);
        if (result > 0) {
            System.out.println(updatedAdmin.getAdminName() + "님의 정보가 수정되었습니다.");
            return true;
        }
        System.out.println("관리자 정보 수정 실패!");
        return false;
    }

    /// 관리자 삭제 (비활성화 처리 가능)
    public boolean deleteAdmin(String adminLoginId) {
        int result = adminLoginService.deleteAdmin(adminLoginId);
        if (result > 0) {
            System.out.println(adminLoginId + " 관리자 계정 삭제 완료!");
            return true;
        }
        System.out.println("관리자 계정 삭제 실패!");
        return false;
    }

    /// 특정 관리자 조회 (관리자 ID 기준)
    public AdminVo getAdminById(Integer adminId) {
        return adminLoginService.getAdminById(adminId);
    }

    /// 로그인 성공 후 관리자 정보 관리 메뉴 호출
    public void enterAdminManage(String id, String pw) {
        AdminVo admin = adminLogin(id, pw); // 로그인 시 AdminVo 반환
        if (admin != null) {
            // 세션 저장 후 관리자 정보 호출
            AdminSession.getInstance().login(admin.getAdminId(), admin.getRole(), admin.getAdminName());
            new AdminManageView(admin); // AdminManageView 실행
        }
    }

    /// 총관리자 제외 모든 관리자 조회
    public List<AdminVo> selectAllExceptSuperAdmin() {
        return adminLoginService.selectAllExceptSuperAdmin();
    }
}
