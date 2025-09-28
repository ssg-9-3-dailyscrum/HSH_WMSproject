package main.java.com.hsh.view;

import main.java.com.hsh.controller.UserMembershipController;
import main.java.com.hsh.domain.vo.UserVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UsermembershipView {

    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private final UserMembershipController controller = UserMembershipController.getInstance();

    public void join() throws IOException {
        System.out.println("=================== 회원가입 ===================");

        System.out.print("아이디: ");
        String userId = input.readLine().trim();

        System.out.print("비밀번호: ");
        String password = input.readLine().trim();

        System.out.print("이름: ");
        String name = input.readLine().trim();

        System.out.print("전화번호: ");
        String phone = input.readLine().trim();

        System.out.print("이메일: ");
        String email = input.readLine().trim();

        System.out.print("주소: ");
        String address = input.readLine().trim();

        // VO 생성
        UserVo user = new UserVo();
        user.setUserLoginId(userId);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setRole("회원"); // 기본 회원 권한
        user.setStatus("Y"); // 가입 상태

        boolean success = controller.registerUser(user);

        if(success) {
            System.out.println("회원가입 성공! 로그인 후 서비스를 이용하세요.");
        } else {
            System.out.println("회원가입 실패!");
        }
    }
}
