package main.java.com.hsh.service.serviceImpl;

import main.java.com.hsh.Dao.UserLoginDao;
import main.java.com.hsh.Dao.daoImpl.UserLoginDaoImpl;
import main.java.com.hsh.domain.vo.UserVo;
import main.java.com.hsh.service.UserLoginService;

import java.util.List;

public class UserLoginServiceImpl implements UserLoginService {

    private static UserLoginServiceImpl instance = new UserLoginServiceImpl();
    private final UserLoginDao userLoginDao;

    private UserLoginServiceImpl() {
        this.userLoginDao = new UserLoginDaoImpl();
    }

    public static UserLoginServiceImpl getInstance() {
        return instance;
    }

    // CREATE
    @Override
    public int createUser(UserVo newUser) {
        return userLoginDao.insertUser(newUser);
    }

    // READ (로그인)
    @Override
    public UserVo userLogin(String id, String pw) {
        return userLoginDao.userLogin(id, pw);
    }

    // READ (회원 정보 조회)
    @Override
    public UserVo getUserInfo(String id) {
        return userLoginDao.selectUserById(id);
    }

    // UPDATE
    @Override
    public int updateUser(UserVo updatedUser) {
        return userLoginDao.updateUser(updatedUser);
    }

    // DELETE (회원 탈퇴 / 비활성화)
    @Override
    public int deleteUser(String id) {
        return userLoginDao.updateUserStatus(id, "N"); // 상태 변경
    }

    @Override
    public List<UserVo> getAllUsers() {
        return userLoginDao.selectAllUsers();
    }

}
