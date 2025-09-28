package main.java.com.hsh.view;

import main.java.com.hsh.controller.AdminLoginController;
import main.java.com.hsh.domain.vo.AdminVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminmembershipView {

    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public void join() throws IOException {
        // 관리자 회원가입 시작
        System.out.println("==================== 관리자 회원가입 ====================");

        System.out.print("아이디: ");
        String adminLoginId = input.readLine().trim();

        System.out.print("비밀번호: ");
        String password = input.readLine().trim();

        System.out.print("부서(department) 입력 (예: 총관리부, 창고부): ");
        String department = input.readLine().trim();

        System.out.print("이름: ");
        String adminName = input.readLine().trim();

        System.out.print("역할(role) 입력 (예: 총관리자, 창고관리자): ");
        String role = input.readLine().trim();

        /// AdminVo 객체 생성 시 department 포함
        AdminVo newAdmin = new AdminVo(adminLoginId, password, adminName, role, department);

        /// Controller를 통해 DB에 저장
        boolean success = AdminLoginController.getInstance().registerAdmin(newAdmin);


        if (!success) {
            System.out.println("관리자 계정 생성 실패!");
        }
    }
}
