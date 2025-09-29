package main.java.com.hsh.controller;

import main.java.com.hsh.domain.vo.UserVo;
import main.java.com.hsh.service.UserLoginService;
import main.java.com.hsh.service.serviceImpl.UserLoginServiceImpl;

import main.java.com.hsh.session.UserSession;

public class UserLoginController {

    private static final UserLoginController instance = new UserLoginController();
    private final UserLoginService userLoginService;

    private UserLoginController() {
        this.userLoginService = UserLoginServiceImpl.getInstance();
    }

    public static UserLoginController getInstance() {
        return instance;
    }

    /// 로그인
    public boolean login(String id, String pw) {
        UserVo user = userLoginService.userLogin(id, pw);
        if (user != null) {
            // 로그인 성공 시 세션에 저장
            UserSession.getInstance().login(user);

            return true;
        }
        System.out.println("아이디 또는 비밀번호가 올바르지 않습니다.");
        return false;
    }

    /// 로그아웃
    public void logout() {
        UserSession.getInstance().logout();
        System.out.println("로그아웃 완료");
    }

    ///현재 로그인된 사용자 조회
    public UserVo getCurrentLoggedInUser() {

        return UserSession.getInstance().getCurrentLoggedInUser();
    }

    /// 사용자 정보 조회
    public UserVo getUserInfo() {

        return getCurrentLoggedInUser();
    }

    /// 사용자 정보 수정
    public boolean updateUserInfo(UserVo updatedUser) {

        int result = userLoginService.updateUser(updatedUser);

        if (result > 0) {
            // 세션에도 업데이트
            UserSession.getInstance().login(updatedUser);
            System.out.println("회원 정보가 업데이트 되었습니다.");
            return true;
        }

        System.out.println("회원 정보 업데이트 실패!");
        return false;
    }

    ///회원 탈퇴 / 비활성화
    public boolean deactivateUser() {

        UserVo user = getCurrentLoggedInUser();

        if (user != null) {
            int result = userLoginService.deleteUser(user.getUserLoginId()); // 수정: deleteUser
            if (result > 0) {
                logout(); // 세션 초기화
                System.out.println("회원 탈퇴(비활성화) 완료!");
                return true;
            }
        }
        System.out.println("회원 탈퇴(비활성화) 실패!");

        return false;
    }

    ///회원가입
    public boolean registerUser(UserVo newUser) {
        int result = userLoginService.createUser(newUser); // Create

        if (result > 0) {
            System.out.println(newUser.getName() + "님 회원가입 성공!");
            return true;
        }
        System.out.println("회원가입 실패!");

        return false;
    }
}
