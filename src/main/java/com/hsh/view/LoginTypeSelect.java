package main.java.com.hsh.view;

import main.java.com.hsh.controller.AdminLoginController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;

public class LoginTypeSelect {

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public int inputLoginType() {
        System.out.println("====================== 창고관리시스템======================");
        System.out.println("로그인 메뉴를 선택해주세요: ");
        System.out.println("1 : 관리자 로그인");
        System.out.println("2 : 회원 로그인");



        try {
            return Integer.parseInt(input.readLine().trim());
        } catch (InputMismatchException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public LoginTypeSelect() throws IOException {
        AdminLoginController adminLoginController = AdminLoginController.getInstance();
        int loginType = inputLoginType();
        switch (loginType) {
            case 1 -> {
                System.out.println("==================== 관리자 로그인 ====================");
                System.out.print("아이디: ");
                String id = input.readLine();
                System.out.print("비밀번호: ");
                String pw = input.readLine();
                boolean success = adminLoginController.adminLogin(id, pw);
                if (success) {
                    System.out.println("관리자 로그인 성공!");
                } else {
                    System.out.println("관리자 로그인 실패!");
                }
            }
//            case 2 -> UserMenu();
            default -> {
                System.out.println("잘못된 입력입니다.");


            }
        }
    }


    public static void main(String[] args) {
        try {
            new LoginTypeSelect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
