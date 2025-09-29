package main.java.com.hsh.view.menuView;

import main.java.com.hsh.controller.WarehouseController;
import main.java.com.hsh.view.WarehouseUserView;
import main.java.com.hsh.view.WarehouseView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WarehouseMenuView {

    private WarehouseController warehouseController = WarehouseController.getInstance();
    private WarehouseView warehouseView = new WarehouseView();
    private WarehouseUserView warehouseUserView = new WarehouseUserView();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void warehouseMenu(){
                
//        String role = AdminSession.getInstance().getLoggedInadmin().getRole();
        // 테스트를 위해 총관리자라고 선언 - 추후 삭제
        String role = "총관리자";

        boolean flag = false;
        while(!flag){
            if(role.equals("총관리자")){
                // 총관리자 창고 메뉴 출력
                superAdminMenu();
                int choice = warehouseMenuChoice();
                switch(choice){
                    case 1 -> warehouseView.registerWarehouse();
                    case 2 -> warehouseView.updateWarehouseStatus();
                    case 3 -> warehouseView.runListWarehouse();
                    case 4 -> warehouseView.printWarehouseByName();
                    case 5 -> warehouseView.printWarehouseByType();
                    case 6 -> warehouseView.printWarehouseByLocation();
                    case 7 -> flag = true;
                    default -> System.out.println(":: 잘못된 입력입니다. ::");
                }
            } else if (role.equals("창고관리자")) {
                // 창고관리자 창고 메뉴 출력
                adminMenu();
                int choice = warehouseMenuChoice();
                switch(choice) {
                    case 1 -> warehouseView.runListWarehouse();
                    case 2 -> warehouseView.printWarehouseByName();
                    case 3 -> warehouseView.printWarehouseByType();
                    case 4 -> warehouseView.printWarehouseByLocation();
                    case 5 -> flag = true;
                    default -> System.out.println(":: 잘못된 입력입니다. ::");
                }
            } else {
                System.out.println(":: 권한이 없습니다. ::");
                flag = true;
            }
        }
    }

    public void warehouseUserMenu(){
        boolean flag = false;
        while(!flag){
            userMenu();
            int choice = warehouseMenuChoice();
            switch(choice) {
                case 1 -> warehouseUserView.runListWarehouse();
                case 2 -> warehouseUserView.printWarehouseByName();
                case 3 -> warehouseUserView.printWarehouseByType();
                case 4 -> warehouseUserView.printWarehouseByLocation();
                case 5 -> flag = true;
                default -> System.out.println(":: 잘못된 입력입니다. ::");
            }
        }
    }

    private int warehouseMenuChoice() {
        System.out.print("메뉴를 선택하세요: ");
        try{
            return Integer.parseInt(reader.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            System.out.println("잘못된 입력입니다.");
            return -1;
        }
    }

    public void superAdminMenu() {
        System.out.println("======================================");
        System.out.println("               관리자 메뉴             ");
        System.out.println("======================================");
        System.out.printf("%-3s %-20s\n", "1.", "창고 등록");
        System.out.printf("%-3s %-20s\n", "2.", "창고 상태 수정");
        System.out.printf("%-3s %-20s\n", "3.", "창고 현황 리스트");
        System.out.printf("%-3s %-20s\n", "4.", "창고명으로 창고 검색");
        System.out.printf("%-3s %-20s\n", "5.", "창고 종류별 창고 검색");
        System.out.printf("%-3s %-20s\n", "6.", "주소로 창고 검색");
        System.out.printf("%-3s %-20s\n", "7.", "돌아가기");
        System.out.println("======================================\n");
    }

    public void adminMenu() {
        System.out.println("======================================");
        System.out.println("                관리자 메뉴             ");
        System.out.println("======================================");
        System.out.printf("%-3s %-20s\n", "1.", "창고 현황 리스트");
        System.out.printf("%-3s %-20s\n", "2.", "창고명으로 창고 검색");
        System.out.printf("%-3s %-20s\n", "3.", "창고 종류별 창고 검색");
        System.out.printf("%-3s %-20s\n", "4.", "주소로 창고 검색");
        System.out.printf("%-3s %-20s\n", "5.", "돌아가기");
        System.out.println("======================================\n");
    }

    public void userMenu() {
        System.out.println("======================================");
        System.out.println("                회원 메뉴             ");
        System.out.println("======================================");
        System.out.printf("%-3s %-20s\n", "1.", "창고 현황 리스트");
        System.out.printf("%-3s %-20s\n", "2.", "창고명으로 창고 검색");
        System.out.printf("%-3s %-20s\n", "3.", "창고 종류별 창고 검색");
        System.out.printf("%-3s %-20s\n", "4.", "주소로 창고 검색");
        System.out.printf("%-3s %-20s\n", "5.", "돌아가기");
        System.out.println("======================================\n");
    }

}
