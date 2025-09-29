package main.java.com.hsh.session;

import lombok.Getter;
import lombok.Setter;
import main.java.com.hsh.domain.vo.UserVo;

@Getter
@Setter
public class UserSession {

    private static final UserSession instance = new UserSession();
    private UserVo currentUser;

    private UserSession() {}

    public static UserSession getInstance() {
        return instance;
    }

    public void login(UserVo user) {
        this.currentUser = user;
    }

    public void logout() {
        this.currentUser = null;
    }

    public UserVo getCurrentLoggedInUser() {
        return this.currentUser;
    }
}
