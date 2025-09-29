package main.java.com.hsh.service;

import main.java.com.hsh.domain.vo.UserVo;
import java.util.List;

public interface UserLoginService {

    // CREATE
    int createUser(UserVo newUser);

    // READ
    UserVo userLogin(String id, String pw);
    UserVo getUserInfo(String id);

    // UPDATE
    int updateUser(UserVo updatedUser);

    // DELETE
    int deleteUser(String id);

    // 전체 회원 조회
    List<UserVo> getAllUsers();

}
