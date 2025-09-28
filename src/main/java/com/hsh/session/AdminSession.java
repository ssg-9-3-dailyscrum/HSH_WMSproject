package main.java.com.hsh.session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSession {

    private static final AdminSession instance = new AdminSession();

    private Long adminId;
    private String role;
    private String adminName;

    private AdminSession() {}

    public static AdminSession getInstance() {
        return instance;
    }


    public void login(Long adminId, String role, String adminName) {
        this.adminId = adminId;
        this.role = role;
        this.adminName = adminName;
    }

    public void logout() {
        this.adminId = null;
        this.role = null;
        this.adminName = null;



    }
}
