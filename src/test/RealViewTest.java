//// test/RealViewTest.java
//package test;
//
//import main.java.com.hsh.util.UserSession;
//import main.java.com.hsh.view.InventoryMenuView;
//import main.java.com.hsh.view.InventoryView;
//import main.java.com.hsh.controller.InventoryController;
//import main.java.com.hsh.domain.dto.response.InventoryResponse;
//import main.java.com.hsh.domain.dto.response.ProductResponse;
//import main.java.com.hsh.domain.dto.response.WarehouseStatusResponse;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.List;
//
//public class RealViewTest {
//    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//    private static InventoryController inventoryController = InventoryController.getInstance();
//    private static InventoryView inventoryView = new InventoryView();
//
//    public static void main(String[] args) {
//        try {
//            showLoginMenu();
//        } catch (IOException e) {
//            System.out.println("테스트 중 오류 발생: " + e.getMessage());
//        } finally {
//            try {
//                br.close();
//            } catch (IOException e) {
//                // 무시
//            }
//        }
//    }
//
//    private static void showLoginMenu() throws IOException {
//        boolean running = true;
//
//        while (running) {
//            System.out.println("\n=== WMS 시스템 로그인 ===");
//            System.out.println("1. 총관리자 로그인");
//            System.out.println("2. 창고관리자 로그인");
//            System.out.println("3. 회원 로그인");
//            System.out.println("4. 현재 세션 확인");
//            System.out.println("0. 종료");
//            System.out.print("선택: ");
//
//            String choice = br.readLine().trim();
//
//            switch (choice) {
//                case "1":
//                    loginAsSuperAdmin();
//                    startInventoryMenuView();
//                    break;
//                case "2":
//                    loginAsWarehouseAdmin();
//                    startInventoryMenuView();
//                    break;
//                case "3":
//                    loginAsMember();
//                    startInventoryMenuView();
//                    break;
//                case "4":
//                    UserSession.printSessionInfo();
//                    break;
//                case "0":
//                    running = false;
//                    break;
//                default:
//                    System.out.println("잘못된 선택입니다.");
//            }
//        }
//    }
//
//    private static void loginAsSuperAdmin() {
//        UserSession.login("SUPER_ADMIN", 1, "총관리자");
//        System.out.println("총관리자로 로그인했습니다.");
//    }
//
//    private static void loginAsWarehouseAdmin() throws IOException {
//        System.out.print("창고관리자 ID를 입력하세요: ");
//        int adminId = Integer.parseInt(br.readLine().trim());
//        UserSession.login("WH_ADMIN", adminId, "창고관리자" + adminId);
//        System.out.println("창고관리자로 로그인했습니다.");
//    }
//
//    private static void loginAsMember() throws IOException {
//        System.out.print("회원 ID를 입력하세요: ");
//        int memberId = Integer.parseInt(br.readLine().trim());
//        UserSession.login("MEMBER", memberId, "회원" + memberId);
//        System.out.println("회원으로 로그인했습니다.");
//    }
//
//    // 실제 InventoryMenuView 실행
//    private static void startInventoryMenuView() throws IOException {
//        InventoryMenuView inventoryMenuView = new InventoryMenuView();
//        inventoryMenuView.showInventoryMenu();
//
//        // 메뉴 종료 후 로그아웃
//        UserSession.logout();
//        System.out.println("로그아웃되었습니다.");
//    }
//}