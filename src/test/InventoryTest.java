// test/InventoryTest.java
package test;

import main.java.com.hsh.session.UserSession;
import main.java.com.hsh.session.AdminSession;
import main.java.com.hsh.view.menuView.InventoryMenuView;
import main.java.com.hsh.domain.vo.UserVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InventoryTest {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        try {
            showLoginMenu();
        } catch (IOException e) {
            System.out.println("테스트 중 오류 발생: " + e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                // 무시
            }
        }
    }

    private static void showLoginMenu() throws IOException {
        boolean running = true;

        while (running) {
            System.out.println("\n=== WMS 시스템 로그인 ===");
            System.out.println("1. 총관리자 로그인");
            System.out.println("2. 창고관리자 로그인");
            System.out.println("3. 회원 로그인");
            System.out.println("4. 현재 세션 확인");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            String choice = br.readLine().trim();

            switch (choice) {
                case "1":
                    loginAsSuperAdmin();
                    startInventoryMenuView();
                    break;
                case "2":
                    loginAsWarehouseAdmin();
                    startInventoryMenuView();
                    break;
                case "3":
                    loginAsMember();
                    startInventoryMenuView();
                    break;
                case "4":
                    printCurrentSession();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private static void loginAsSuperAdmin() {
        AdminSession.getInstance().login(1, "총관리자", "총관리자");
        System.out.println("총관리자로 로그인했습니다.");
        printCurrentSession();
    }

    private static void loginAsWarehouseAdmin() throws IOException {
        System.out.print("창고관리자 ID를 입력하세요: ");
        int adminId = Integer.parseInt(br.readLine().trim());
        AdminSession.getInstance().login(adminId, "창고관리자", "창고관리자" + adminId);
        System.out.println("창고관리자로 로그인했습니다.");
        printCurrentSession();
    }

    private static void loginAsMember() throws IOException {
        System.out.print("회원 ID를 입력하세요: ");
        int memberId = Integer.parseInt(br.readLine().trim());

        // UserVo 객체 생성
        UserVo user = new UserVo();
        user.setMemberId(memberId);

        UserSession.getInstance().login(user);
        System.out.println("회원으로 로그인했습니다.");
        printCurrentSession();
    }

    // 실제 InventoryMenuView 실행
    private static void startInventoryMenuView() throws IOException {
        InventoryMenuView inventoryMenuView = new InventoryMenuView();
        inventoryMenuView.showInventoryMenu();

        // 메뉴 종료 후 로그아웃
        AdminSession.getInstance().logout();
        UserSession.getInstance().logout();
        System.out.println("로그아웃되었습니다.");
    }

    // 현재 세션 정보 출력
    private static void printCurrentSession() {
        AdminSession adminSession = AdminSession.getInstance();
        UserVo currentUser = UserSession.getInstance().getCurrentLoggedInUser();

        System.out.println("\n=== 현재 세션 정보 ===");

        if (adminSession.getAdminId() != null) {
            System.out.println("로그인 상태: 관리자 로그인됨");
            System.out.println("관리자 ID: " + adminSession.getAdminId());
            System.out.println("권한: " + adminSession.getRole());
            System.out.println("이름: " + adminSession.getAdminName());
        } else if (currentUser != null) {
            System.out.println("로그인 상태: 회원 로그인됨");
            System.out.println("회원 ID: " + currentUser.getMemberId());
            System.out.println("권한: 회원");
        } else {
            System.out.println("로그인 상태: 로그아웃");
        }

        System.out.println("====================\n");
    }
}