package main.java.com.hsh.view.menuView;

import main.java.com.hsh.controller.UserLoginController;
import main.java.com.hsh.domain.vo.UserVo;
import main.java.com.hsh.view.UserManageView; /// UserVo -> import ->  현재 사용자 정보를 담는 객체

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


            UserVo currentUser = userLoginController.getUserInfo();

            // 현재 로그인된 사용자 정보 확인 (로그아웃되었거나 세션이 만료된 경우)
            if (currentUser == null) {
                System.out.println("세션이 종료되거나 유효하지 않아 메인 메뉴로 돌아갑니다.");
                break; // LoginTypeSelect로 돌아감
            }

            try {
                int choice = displayMainMenu(currentUser.getName()); // 사용자 이름을 전달

                switch (choice) {
                    case 1 -> {
                        System.out.println(" 회원 관리 메뉴로 이동합니다.");

                        new UserManageView();
                    }
                    case 2 -> {
                        System.out.println("2. 창고 관리 메뉴 ");
                        WarehouseMenuView warehouseMenuView = new WarehouseMenuView();
                        warehouseMenuView.warehouseUserMenu();
                    }
                    case 3 -> {
                        System.out.println("3. 재고 관리 메뉴 ");
                        InventoryMenuView inventoryMenuView = new InventoryMenuView();
                        inventoryMenuView.showInventoryMenu();
                    }
                    case 4 -> {
                        System.out.println("4. 입고 관리 메뉴 ");
                        InboundMenuView inboundMenuView = new InboundMenuView();
                        inboundMenuView.inboundMenu();
                    }
                    case 5 -> {
                        System.out.println("5. 출고 관리 메뉴 ");
                    }
                    case 6 -> {
                        userLoginController.logout(); // 컨트롤러에 로그아웃 요청
                        running = false; // UserMenuView 종료 -> LoginTypeSelect로 돌아감
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println(" 잘못된 입력 형식입니다. 숫자를 입력해주세요.");
            }
        }
    }

    // 회원 메인 메뉴 출력
    private int displayMainMenu(String userName) throws IOException {
        System.out.println("\n=================================================");
        System.out.println("|           " + userName + ", 메인 메뉴                       |");
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