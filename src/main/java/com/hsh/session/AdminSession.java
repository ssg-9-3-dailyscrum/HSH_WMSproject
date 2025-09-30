package main.java.com.hsh.session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSession {

    private static final AdminSession instance = new AdminSession();

    private Integer adminId;
    private String role;
    private String adminName;

    private AdminSession() {}

    public static AdminSession getInstance() {
        return instance;
    }

    // 로그인 시 세션에 값 저장
    public void login(Integer adminId, String role, String adminName) {
        this.adminId = adminId;
        this.role = role;
        this.adminName = adminName;
    }

    // 로그아웃 시 초기화
    public void logout() {
        this.adminId = null;
        this.role = null;
        this.adminName = null;
    }
}
