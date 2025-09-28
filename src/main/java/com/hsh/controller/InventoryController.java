package main.java.com.hsh.controller;

import main.java.com.hsh.domain.dto.response.InventoryResponse;
import main.java.com.hsh.domain.dto.response.ProductResponse;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponse;
import main.java.com.hsh.service.InventoryService;
import main.java.com.hsh.service.seviceImpl.InventoryServiceImpl;
import main.java.com.hsh.util.UserSession;

import java.sql.SQLException;
import java.util.List;


public class InventoryController {
    private static InventoryController instance = new InventoryController();
    private InventoryService inventoryService;

    public InventoryController() {
        this.inventoryService = InventoryServiceImpl.getInstance();
    }

    // 전역 접근 지점 (항상 같은 인스턴스를 반환)
    public static InventoryController getInstance() {
        return instance; // 이미 있으면 기존 객체 반환
    }


    // 전체 재고 조회
    public List<InventoryResponse> showAllInventory() {
        String userRole = UserSession.getCurrentUserRole();
        Integer userId = UserSession.getCurrentUserId();
        return inventoryService.getAllInventory(userRole, userId);
    }

//    // 대분류, 소분류 리스트
//    public List<String> showTopCategoryList() throws SQLException {
//        return inventoryService.getTopCategoryList(userRole, userId);
//    }
//
//    public List<String> showSubCategoryList() throws SQLException {
//        return inventoryService.getSubCategoryList(userRole, userId, category);
//    }
//
//    public List<InventoryResponse> showTopCategoryInventory() {
//        return inventoryService.getCategoryInventory(userRole, userId);
//    }

    // 상품 상세 조회
    public List<ProductResponse> showProductDetail(String productName) {
        String userRole = UserSession.getCurrentUserRole();
        Integer userId = UserSession.getCurrentUserId();
        return inventoryService.getProductDetail(userRole, userId, productName);
    }

    // 창고 현황 조회
    public List<WarehouseStatusResponse> showWarehouse() {
        return inventoryService.getWarehouse();
    }

//    public List<InventoryResponse> showInventoryAuditLog() {
//        System.out.println("재고 실사 조회 기능 준비 중입니다.");
//    }


}
