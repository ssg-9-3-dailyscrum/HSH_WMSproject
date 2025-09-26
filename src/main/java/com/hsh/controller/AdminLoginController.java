package main.java.com.hsh.controller;

import main.java.com.hsh.service.AdminLoginService;
import main.java.com.hsh.service.seviceImpl.AdminLoginServiceImpl;

public class AdminLoginController {

    // 싱글톤 인스턴스를 담을 static 변수
    // (클래스 내부에 자기 자신을 담을 정적 변수 선언)
    // Eager Singleton 방식
    private static AdminLoginController instance = new AdminLoginController();
    private AdminLoginService adminloginService;


    private AdminLoginController() {
        // 싱글톤 서비스 구현체 가져오기
        this.adminloginService = AdminLoginServiceImpl.getInstance();
    }

    // 전역 접근 지점 (항상 같은 인스턴스 반환 가능하도록)
    public static AdminLoginController getInstance() {
        return instance;
    }

    //
    public boolean adminLogin(String id, String pw) {
        return adminloginService.adminLogin(id, pw);
    }

}