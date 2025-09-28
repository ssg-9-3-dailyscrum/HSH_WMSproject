package main.java.com.hsh.view;

import main.java.com.hsh.controller.UserLoginController;
import main.java.com.hsh.domain.vo.UserVo; /// UserVo -> import ->  현재 사용자 정보를 담는 객체

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserMenuView {

    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    // 컨트롤러 싱글톤 인스턴스 사용
    private final UserLoginController userLoginController = UserLoginController.getInstance();

    public UserMenuView() {
        startUserMenu();
    }

    private void startUserMenu() {
        boolean running = true;
        while (running) {

            // 💡 수정: userLoginController.getCurrentLoggedInUser() 대신,
            //    세션에서 UserVo 객체를 가져오는 **userLoginController.getUserInfo()**를 사용합니다.
            //    (getUserInfo()는 세션의 UserLoginId를 사용해 DB에서 최신 UserVo를 가져옵니다.)
            UserVo currentUser = userLoginController.getUserInfo();

            // 현재 로그인된 사용자 정보 확인 (로그아웃되었거나 세션이 만료된 경우)
            if (currentUser == null) {
                System.out.println("세션이 종료되거나 유효하지 않아 메인 메뉴로 돌아갑니다.");
                break; // LoginTypeSelect로 돌아감
            }

            try {
                int choice = displayMainMenu(currentUser.getName()); // 사용자 이름을 전달

                switch (choice) {
                    case 1 -> { // 1. 회원 관리 (마이페이지 기능 등)
                        System.out.println(" 회원 관리 메뉴로 이동합니다.");
                        // 회원 관리 서브 메뉴 호출 (UserManageView는 회원 정보 수정/탈퇴 등의 로직을 담을 것입니다.)
                        new UserManageView();
                    }
                    case 2 -> System.out.println("2. 창고 관리 메뉴 (미구현)");
                    case 3 -> System.out.println("3. 재고 관리 메뉴 (미구현)");
                    case 4 -> System.out.println("4. 입고 관리 메뉴 (미구현)");
                    case 5 -> System.out.println("5. 출고 관리 메뉴 (미구현)");
                    case 6 -> { // 6. 로그아웃
                        userLoginController.logout(); // 컨트롤러에 로그아웃 요청 (세션 해제)
                        running = false; // UserMenuView 종료 -> LoginTypeSelect로 돌아감
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println(" 잘못된 입력 형식입니다. 숫자를 입력해주세요.");
            }
        }
    }

    // 회원 메인 메뉴 출력 (userName을 인수로 받도록 수정)
    private int displayMainMenu(String userName) throws IOException {
        System.out.println("\n=================================================");
        System.out.println("            **" + userName + "님, 메인 메뉴** ");
        System.out.println("=================================================");
        System.out.println("   1. 회원 관리");
        System.out.println("   2. 창고 관리");
        System.out.println("   3. 재고 관리");
        System.out.println("   4. 입고 관리");
        System.out.println("   5. 출고 관리");
        System.out.println("   6. 로그아웃");
        System.out.println("-------------------------------------------------");
        System.out.print("메뉴를 선택해주세요 : ");
        return Integer.parseInt(input.readLine().trim());
    }
}