package main.java.com.hsh.controller;

import main.java.com.hsh.domain.dto.response.InventoryAuditResponse;
import main.java.com.hsh.domain.dto.response.InventoryResponse;
import main.java.com.hsh.domain.dto.response.ProductResponse;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponse;
import main.java.com.hsh.domain.vo.UserVo;
import main.java.com.hsh.service.InventoryService;
import main.java.com.hsh.service.serviceImpl.InventoryServiceImpl;
import main.java.com.hsh.session.AdminSession;
import main.java.com.hsh.session.UserSession;

import java.sql.SQLException;
import java.util.ArrayList;
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
        AdminSession adminSession = AdminSession.getInstance();
        UserVo currentUser = UserSession.getInstance().getCurrentLoggedInUser();

        String userRole = null;
        Integer userId = null;

        if (adminSession.getAdminId() != null) {
            userRole = adminSession.getRole();
            userId = adminSession.getAdminId().intValue();
        } else if (currentUser != null) {
            userRole = "회원";
            userId = currentUser.getMemberId();
        } else {
            System.out.println("로그인이 필요합니다.");
            return new ArrayList<>();
        }
        return inventoryService.getAllInventory(userRole, userId);
    }

    // 대분류 리스트
    public List<String> showTopCategoryList() {
        return inventoryService.getTopCategoryList();
    }

    // 대분류 조회
    public List<InventoryResponse> showTopCategoryInventory(String categoryName) {
        return inventoryService.getTopCategoryInventory(categoryName);
    }

    // 소분류 리스트
    public List<String> showSubCategoryList(String category)  {
        return inventoryService.getSubCategoryList(category);
    }

    // 소분류 조회
    public List<InventoryResponse> showSubCategoryInventory(String categoryName) {
        return inventoryService.getSubCategoryInventory(categoryName);
    }

    // 상품 상세 조회
    public List<ProductResponse> showProductDetail(String productName) {
        AdminSession adminSession = AdminSession.getInstance();
        UserVo currentUser = UserSession.getInstance().getCurrentLoggedInUser();

        String userRole = null;
        Integer userId = null;

        if (adminSession.getAdminId() != null) {
            userRole = adminSession.getRole();
            userId = adminSession.getAdminId().intValue();
        } else if (currentUser != null) {
            userRole = "회원";
            userId = currentUser.getMemberId();
        } else {
            System.out.println("로그인이 필요합니다.");
            return new ArrayList<>();
        }

        return inventoryService.getProductDetail(userRole, userId, productName);
    }

    // 창고 현황 조회
    public List<WarehouseStatusResponse> showWarehouse() {
        return inventoryService.getWarehouse();
    }

    public List<InventoryAuditResponse> showInventoryAuditLog() {
        AdminSession adminSession = AdminSession.getInstance();
        UserVo currentUser = UserSession.getInstance().getCurrentLoggedInUser();

        String userRole = null;
        Integer userId = null;

        if (adminSession.getAdminId() != null) {
            userRole = adminSession.getRole();
            userId = adminSession.getAdminId().intValue();
        } else if (currentUser != null) {
            userRole = "회원";
            userId = currentUser.getMemberId();
        } else {
            System.out.println("로그인이 필요합니다.");
            return new ArrayList<>();
        }

        return inventoryService.getInventoryAuditLog(userRole, userId);
    }


}