package main.java.com.hsh.controller;

import main.java.com.hsh.Dao.UserLoginDao;
import main.java.com.hsh.Dao.daoImpl.UserLoginDaoImpl;
import main.java.com.hsh.domain.vo.UserVo;

public class UserMembershipController {

    private static UserMembershipController instance = new UserMembershipController();
    private final UserLoginDao userDao = new UserLoginDaoImpl();

    private UserMembershipController() {}

    public static UserMembershipController getInstance() {
        return instance;
    }

    // 회원가입 처리
    public boolean registerUser(UserVo user) {
        // 1. 아이디 중복 체크
        if(userDao.selectUserById(user.getUserLoginId()) != null) {
            System.out.println("이미 사용중인 아이디입니다.");
            return false;
        }

        // 2. DB에 저장
        int result = userDao.insertUser(user);
        return result > 0;
    }
}
