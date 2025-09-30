package main.java.com.hsh.dao;

import main.java.com.hsh.domain.vo.UserVo;

import java.util.List;

public interface UserLoginDao {

    // 1. 로그인
    UserVo userLogin(String userLoginId, String userLoginPwd);

    // 2. 회원 정보 조회
    UserVo selectUserById(String userLoginId);

    // 3. 회원 정보 수정
    int updateUser(UserVo user);

    // 4. 회원 상태 변경 (탈퇴/비활성화)
    int updateUserStatus(String userLoginId, String status);

    // 5. ID와 비밀번호를 통해 DB에서 User 정보를 조회하여 반환.
    UserVo selectUserByIdAndPassword(String id, String password);

    // 6. 회원가입
    int insertUser(UserVo newUser);

    // 7. 전체 회원 조회
    List<UserVo> selectAllUsers();
}
