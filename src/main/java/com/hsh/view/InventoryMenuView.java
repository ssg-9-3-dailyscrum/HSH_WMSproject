<<<<<<< HEAD
//package main.java.com.hsh.view;
//
//import main.java.com.hsh.controller.InventoryController;
//import main.java.com.hsh.domain.dto.response.InventoryResponse;
//import main.java.com.hsh.domain.dto.response.ProductResponse;
//import main.java.com.hsh.domain.dto.response.WarehouseStatusResponse;
//import main.java.com.hsh.util.UserSession;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.List;
//
//
//public class InventoryMenuView {
//    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//    private InventoryController inventoryController = InventoryController.getInstance();
//    private InventoryView inventoryView = new InventoryView();
//
//    public void showInventoryMenu() throws IOException {
//        String userRole = UserSession.getCurrentUserRole();
//        boolean loop = true;
//        while (loop) {
//            System.out.println("\n========= 재고 관리 =========");
//            // 관리자인지 회원인지 체크
//            if ("SUPER_ADMIN".equals(userRole) || "WH_ADMIN".equals(userRole)) {
//                showAdminMenu();
//            } else {
//                showMemberMenu();
//            }
//            System.out.println("0. 나가기");
//            String input = br.readLine().trim();
//
//            switch(input) {
//                case "1":
//                    handleAllInventory();
////                    waitForUser();
//                    break;
//                case "2":
////                    handleCategoryInventory();
////                    waitForUser();
//                    break;
//                case "3":
//                    handleProductDetail();
////                    waitForUser();
//                    break;
//                case "4":
//                    if ("SUPER_ADMIN".equals(userRole) || "WH_ADMIN".equals(userRole)) {
//                        handleWareHouse();
////                        waitForUser();
//                    } else {
//                        System.out.println("잘못된 번호입니다.");
//                    }
//                    break;
//                case "5":
//                    if ("SUPER_ADMIN".equals(userRole) || "WH_ADMIN".equals(userRole)) {
////                        handleInventoryAuditLog();
////                        waitForUser();
//                    } else {
//                        System.out.println("잘못된 번호입니다.");
//                    }
//                    break;
//                case "0":
//                    loop = false;
//                    break;
//                default:
//                    System.out.println("잘못된 번호입니다.");
//            }
//        }
//    }
//
//    // 관리자 메뉴
//    private void showAdminMenu() {
//        System.out.println("1. 전체 재고 조회");
//        System.out.println("2. 카테고리별 재고 조회");
//        System.out.println("3. 상품 상세 조회");
//        System.out.println("4. 창고 현황 조회");
//        System.out.println("5. 재고 실사 조회");
//    }
//
//    // 회원 메뉴
//    private void showMemberMenu() {
//        System.out.println("1. 전체 재고 조회");
//        System.out.println("2. 카테고리별 재고 조회");
//        System.out.println("3. 상품 상세 조회");
//    }
//
//    // 전체 재고 조회
//    private void handleAllInventory() {
//        List<InventoryResponse> inventoryList = inventoryController.showAllInventory();
//        inventoryView.displayAllInventory(inventoryList);
//    }
//
////    // 카테고리별 재고 조회
////    private void handleCategoryInventory() throws IOException {
////        boolean loop = true;
////        while (loop) {
////            System.out.println("\n========= 카테고리별 재고 조회 =========");
////            System.out.println("1. 대분류 조회");
////            System.out.println("2. 돌아가기");
////            String input = br.readLine().trim();
////
////            switch (input) {
////                case "1":
////                    handleTopCategorySearch();
////                    break;
////                case "2":
////                    loop = false;
////                    break;
////                default:
////                    System.out.println("잘못된 번호입니다.");
////            }
////        }
////    }
////
////    private void handleTopCategorySearch() throws IOException {
////        // 1. 대분류 목록 표시
////        List<String> topCategories = inventoryController.showTopCategoryList();
////
////        if (topCategories.isEmpty()) {
////            System.out.println("조회 가능한 대분류가 없습니다.");
////            return;
////        }
////
////        System.out.println("\n========= 대분류 목록 =========");
////        for (int i = 0; i < topCategories.size(); i++) {
////            System.out.println((i + 1) + ". " + topCategories.get(i));
////        }
////
////        try {
////            int choice = Integer.parseInt(br.readLine().trim());
////            if (choice < 1 || choice > topCategories.size()) {
////                System.out.println("잘못된 번호입니다.");
////                return;
////            }
////
////            String selectedTopCategory = topCategories.get(choice - 1);
////
////            // 선택한 대분류의 재고 조회 및 출력
////            List<InventoryResponse> inventoryList = inventoryController.showTopCategoryInventory(selectedTopCategory);
////            inventoryView.displayCategoryInventory(inventoryList, selectedTopCategory, "대분류");
////
////            // 소분류 조회 옵션 제공
////            showSubCategoryOption(selectedTopCategory);
////
////        } catch (NumberFormatException e) {
////            System.out.println("올바른 숫자를 입력해주세요.");
////        }
////    }
////
////    private void showSubCategoryOption(String selectedTopCategory) throws IOException {
////        System.out.println("\n1. 소분류 조회");
////        System.out.println("2. 나가기");
////        String choice = br.readLine().trim();
////
////        if ("1".equals(choice)) {
////            handleSubCategorySearch(selectedTopCategory);
////        }
////    }
////
////    private void handleSubCategorySearch(String topCategory) throws IOException {
////        // 선택한 대분류의 소분류 목록 조회
////        List<String> subCategories = inventoryController.getSubCategoryList(topCategory);
////
////        if (subCategories.isEmpty()) {
////            System.out.println("해당 소분류가 없습니다.");
////            return;
////        }
////
////        System.out.println("\n========= 소분류 목록 =========");
////        for (int i = 0; i < subCategories.size(); i++) {
////            System.out.println((i + 1) + ". " + subCategories.get(i));
////        }
////        System.out.print("선택: ");
////
////        try {
////            int choice = Integer.parseInt(br.readLine().trim());
////            if (choice < 1 || choice > subCategories.size()) {
////                System.out.println("잘못된 번호입니다.");
////                return;
////            }
////
////            String selectedSubCategory = subCategories.get(choice - 1);
////
////            // 선택한 소분류의 재고 조회 및 출력
////            List<InventoryResponse> inventoryList = inventoryController.getSubCategoryInventory(selectedSubCategory);
////            inventoryView.displayCategoryInventory(inventoryList, selectedSubCategory, "소분류");
////
////        } catch (NumberFormatException e) {
////            System.out.println("올바른 숫자를 입력해주세요.");
////        }
////    }
//
//
//    // 상품 상세 조회
//    private void handleProductDetail() throws IOException {
//        System.out.print("조회할 상품명을 입력하세요: ");
//        String productName = br.readLine().trim();
//
//        if (productName.isEmpty()) {
//            System.out.println("해당 상품은 존재하지 않습니다.");
//            return;
//        }
//
//        List<ProductResponse> productList = inventoryController.showProductDetail(productName);
//        inventoryView.displayProductDetail(productList);
//    }
//
//    // 창고 현황 조회
//    private void handleWareHouse() {
//        List<WarehouseStatusResponse> warehouseList = inventoryController.showWarehouse();
//        inventoryView.displayWarehouseInventory(warehouseList);
//    }
//
////    // 재고 실사 조회
////    private void handleInventoryAuditLog() {
////        List<InventoryResponse> inventoryList = inventoryController.showInventoryAuditLog();
////        inventoryView.displayInventoryAuditLog(inventoryList);
////    }
//
////    // 결과 출력 후 사용자가 엔터를 눌러야 넘어감
////    private void waitForUser() throws IOException {
////        br.readLine();
////    }
//}
=======
package main.java.com.hsh.view;

import main.java.com.hsh.controller.InventoryController;
import main.java.com.hsh.domain.dto.response.InventoryResponse;
import main.java.com.hsh.domain.dto.response.ProductResponse;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponse;
import main.java.com.hsh.util.UserSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class InventoryMenuView {
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private InventoryController inventoryController = InventoryController.getInstance();
    private InventoryView inventoryView = new InventoryView();

    public void showInventoryMenu() throws IOException {
        String userRole = UserSession.getCurrentUserRole();
        boolean loop = true;
        while (loop) {
            System.out.println("\n========= 재고 관리 =========");
            // 관리자인지 회원인지 체크
            if ("SUPER_ADMIN".equals(userRole) || "WH_ADMIN".equals(userRole)) {
                showAdminMenu();
            } else {
                showMemberMenu();
            }
            System.out.println("0. 나가기");
            System.out.print("선택: ");
            String input = br.readLine().trim();

            switch(input) {
                case "1":
                    handleAllInventory();
//                    waitForUser();
                    break;
                case "2":
                    if ("SUPER_ADMIN".equals(userRole) || "WH_ADMIN".equals(userRole)) {
                        handleCategoryInventory();
//                        waitForUser();
                    } else {
                        handleProductDetail();
                    }
//                    waitForUser();
                    break;
                case "3":
                    if ("SUPER_ADMIN".equals(userRole) || "WH_ADMIN".equals(userRole)) {
                        handleProductDetail();
//                        waitForUser();
                    } else {
                        System.out.println("잘못된 번호입니다.");
                    }
                    break;
                case "4":
                    if ("SUPER_ADMIN".equals(userRole) || "WH_ADMIN".equals(userRole)) {
                        handleWareHouse();
//                        waitForUser();
                    } else {
                        System.out.println("잘못된 번호입니다.");
                    }
                    break;
                case "5":
                    if ("SUPER_ADMIN".equals(userRole) || "WH_ADMIN".equals(userRole)) {
//                        handleInventoryAuditLog();
//                        waitForUser();
                    } else {
                        System.out.println("잘못된 번호입니다.");
                    }
                    break;
                case "0":
                    loop = false;
                    break;
                default:
                    System.out.println("잘못된 번호입니다.");
            }
        }
    }

    // 관리자 메뉴
    private void showAdminMenu() {
        System.out.println("1. 전체 재고 조회");
        System.out.println("2. 카테고리별 재고 조회");
        System.out.println("3. 상품 상세 조회");
        System.out.println("4. 창고 현황 조회");
        System.out.println("5. 재고 실사 조회");
    }

    // 회원 메뉴
    private void showMemberMenu() {
        System.out.println("1. 전체 재고 조회");
        System.out.println("2. 상품 상세 조회");
    }

    // 전체 재고 조회
    private void handleAllInventory() {
        List<InventoryResponse> inventoryList = inventoryController.showAllInventory();
        inventoryView.displayAllInventory(inventoryList);
    }

    // 카테고리별 재고 조회
    private void handleCategoryInventory() throws IOException {
            System.out.println("\n========= 카테고리별 재고 조회 =========");
            System.out.println("1. 대분류 조회");
            System.out.println("2. 나가기");
            System.out.print("선택: ");
            String input = br.readLine().trim();

            switch (input) {
                case "1":
                    handleTopCategorySearch();
                    break;
                case "2":
                    break;
                default:
                    System.out.println("잘못된 번호입니다.");
            }
    }

    private void handleTopCategorySearch() throws IOException {
        // 1. 대분류 목록 표시
        List<String> topCategories = inventoryController.showTopCategoryList();

        if (topCategories.isEmpty()) {
            System.out.println("조회 가능한 대분류가 없습니다.");
            return;
        }

        System.out.println("\n========= 대분류 목록 =========");
        for (int i = 0; i < topCategories.size(); i++) {
            System.out.println((i + 1) + ". " + topCategories.get(i));
        }

        try {
            System.out.print("선택: ");
            int choice = Integer.parseInt(br.readLine().trim());
            if (choice < 1 || choice > topCategories.size()) {
                System.out.println("잘못된 번호입니다.");
                return;
            }

            String selectedTopCategory = topCategories.get(choice - 1);

            // 선택한 대분류의 재고 조회
            List<InventoryResponse> inventoryList = inventoryController.showTopCategoryInventory(selectedTopCategory);
            inventoryView.displayTopCategoryInventory(inventoryList);

            // 소분류 조회 옵션 제공
            showSubCategoryOption(selectedTopCategory);

        } catch (NumberFormatException e) {
            System.out.println("올바른 숫자를 입력해주세요.");
        }
    }

    // 소분류 조회 옵션
    private void showSubCategoryOption(String selectedTopCategory) throws IOException {
            System.out.println("\n========= 카테고리별 재고 조회 =========");
            System.out.println("1. 소분류 조회");
            System.out.println("2. 나가기");
            System.out.print("선택: ");
            String input = br.readLine().trim();

            switch (input) {
                case "1":
                    handleSubCategorySearch(selectedTopCategory);
                    break;
                case "2":
                    break;
                default:
                    System.out.println("잘못된 번호입니다.");
            }

    }

    private void handleSubCategorySearch(String topCategory) throws IOException {
        // 선택한 대분류의 소분류 목록 조회
        List<String> subCategories = inventoryController.showSubCategoryList(topCategory);

        if (subCategories.isEmpty()) {
            System.out.println("해당 소분류가 없습니다.");
            return;
        }

        System.out.println("\n========= 소분류 목록 =========");
        for (int i = 0; i < subCategories.size(); i++) {
            System.out.println((i + 1) + ". " + subCategories.get(i));
        }

        try {
            System.out.print("선택: ");
            int choice = Integer.parseInt(br.readLine().trim());
            if (choice < 1 || choice > subCategories.size()) {
                System.out.println("잘못된 번호입니다.");
                return;
            }

            String selectedSubCategory = subCategories.get(choice - 1);

            // 선택한 소분류의 재고 조회 및 출력
            List<InventoryResponse> inventoryList = inventoryController.showSubCategoryInventory(selectedSubCategory);
            inventoryView.displaySubCategoryInventory(inventoryList);

        } catch (NumberFormatException e) {
            System.out.println("올바른 숫자를 입력해주세요.");
        }
    }

    // 상품 상세 조회
    private void handleProductDetail() throws IOException {
        System.out.print("조회할 상품명을 입력하세요: ");
        String productName = br.readLine().trim();

        if (productName.isEmpty()) {
            System.out.println("해당 상품은 존재하지 않습니다.");
            return;
        }

        List<ProductResponse> productList = inventoryController.showProductDetail(productName);
        inventoryView.displayProductDetail(productList);
    }

    // 창고 현황 조회
    private void handleWareHouse() {
        List<WarehouseStatusResponse> warehouseList = inventoryController.showWarehouse();
        inventoryView.displayWarehouseInventory(warehouseList);
    }

//    // 재고 실사 조회
//    private void handleInventoryAuditLog() {
//        List<InventoryResponse> inventoryList = inventoryController.showInventoryAuditLog();
//        inventoryView.displayInventoryAuditLog(inventoryList);
//    }

//    // 결과 출력 후 사용자가 엔터를 눌러야 넘어감
//    private void waitForUser() throws IOException {
//        System.out.println("조회를 종료하려면 Enter를 입력해주세요.");
//        br.readLine();
//    }
}
>>>>>>> develop
