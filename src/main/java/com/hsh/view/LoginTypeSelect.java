package main.java.com.hsh.view;

import main.java.com.hsh.controller.AdminLoginController;
import main.java.com.hsh.controller.UserLoginController;
import main.java.com.hsh.domain.vo.AdminVo;
import main.java.com.hsh.domain.vo.UserVo;
import main.java.com.hsh.session.AdminSession; ///관리자 세션
import main.java.com.hsh.session.UserSession; /// 사용자 세션

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginTypeSelect {

    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // --- 메인 메뉴 출력 ---
    public int inputMainMenu() throws IOException {
        System.out.println("=================================================");
        System.out.println("          보람삼조 창고관리시스템 메인 메뉴        ");
        System.out.println("=================================================");
        System.out.println("   1. 관리자");
        System.out.println("   2. 회원");
        System.out.println("   3. 종료");
        System.out.println("-------------------------------------------------");
        System.out.print("메뉴를 선택해주세요 : ");
        return Integer.parseInt(input.readLine().trim());
    }

    // --- 서브 메뉴 출력 (로그인/회원가입/뒤로가기) ---
    public int inputSubMenu(String userType) throws IOException {
        System.out.println("=================================================");
        System.out.println("          [" + userType + " 메뉴]                 ");
        System.out.println("=================================================");
        System.out.println("   1. 로그인");
        System.out.println("   2. 회원가입");
        System.out.println("   3. 뒤로가기");
        System.out.println("-------------------------------------------------");
        System.out.print("메뉴를 선택해주세요 : ");
        return Integer.parseInt(input.readLine().trim());
    }

    public LoginTypeSelect() throws IOException {
        AdminLoginController adminLoginController = AdminLoginController.getInstance();
        UserLoginController userLoginController = UserLoginController.getInstance();

        boolean running = true;
        while (running) {
            int mainChoice = inputMainMenu();

            switch (mainChoice) {
                case 1 -> { // 관리자
                    boolean adminMenu = true;
                    while (adminMenu) {
                        int adminChoice = inputSubMenu("관리자");
                        switch (adminChoice) {
                            case 1 -> { // 로그인
                                System.out.println("==================== 관리자 로그인 ====================");
                                System.out.print("아이디: ");
                                String id = input.readLine();
                                System.out.print("비밀번호: ");
                                String pw = input.readLine();

                                AdminVo admin = adminLoginController.adminLogin(id, pw);
                                if (admin != null) {
                                    /// 관리자 세션에 로그인 정보 저장
                                    AdminSession.getInstance().login(admin.getAdminId(), admin.getRole(), admin.getAdminName());
                                    System.out.println(admin.getAdminName() + "님 로그인 성공! (ID: " + admin.getAdminId() + ")");


                                    new AdminMenuView(admin);
                                    adminMenu = false;
                                } else {
                                    System.out.println("관리자 로그인 실패! 아이디와 비밀번호를 확인해주세요.");
                                }
                            }
                            case 2 -> { // 관리자 회원가입
                                new AdminmembershipView().join();
                            }
                            case 3 -> adminMenu = false; // 뒤로가기
                            default -> System.out.println("잘못된 입력입니다.");
                        }
                    }
                }

                case 2 -> { // 회원
                    boolean userMenu = true;
                    while (userMenu) {
                        int userChoice = inputSubMenu("보람삼조 회원");
                        switch (userChoice) {
                            case 1 -> { // 로그인
                                System.out.println("==================== 회원 로그인 ====================");
                                System.out.print("아이디: ");
                                String id = input.readLine();
                                System.out.print("비밀번호: ");
                                String pw = input.readLine();

                                boolean success = userLoginController.login(id, pw);
                                if (success) {
                                    /// 로그인 성공 후  UserSession에서 가져오기
                                    UserVo user = UserSession.getInstance().getCurrentLoggedInUser();
                                    System.out.println(user.getName() + "님 로그인 성공! (ID: " + user.getMemberId() + ")");

                                    // 로그인 성공 후 회원 메뉴 이동
                                    new UserMenuView();
                                    userMenu = false;
                                } else {
                                    System.out.println("회원 로그인 실패! 아이디와 비밀번호를 확인해주세요.");
                                }
                            }
                            case 2 -> { // 회원가입
                                new UsermembershipView().join();
                            }
                            case 3 -> userMenu = false; // 뒤로가기
                            default -> System.out.println("잘못된 입력입니다.");
                        }
                    }
                }

                case 3 -> { // 종료
                    System.out.println("시스템을 종료합니다.");
                    running = false;
                }

                default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
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
