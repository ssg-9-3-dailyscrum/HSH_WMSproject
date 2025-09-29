package main.java.com.hsh.service.serviceImpl;

import main.java.com.hsh.dao.InventoryDao;
import main.java.com.hsh.dao.daoImpl.InventoryDaoImpl;
import main.java.com.hsh.domain.dto.response.InventoryAuditResponse;
import main.java.com.hsh.domain.dto.response.InventoryResponse;

import main.java.com.hsh.domain.dto.response.ProductResponse;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponse;
import main.java.com.hsh.service.InventoryService;

import java.util.ArrayList;
import java.util.List;

public class InventoryServiceImpl implements InventoryService {
    private static InventoryServiceImpl instance;
    private InventoryDao inventoryDao;

    public InventoryServiceImpl() {
        this.inventoryDao = new InventoryDaoImpl();
    }

    public static InventoryServiceImpl getInstance() {
        if (instance == null) {
            instance = new InventoryServiceImpl();
        }
        return instance;
    }

    // 전체 재고 조회
    @Override
    public List<InventoryResponse> getAllInventory(String userRole, int userId) {
        try {
            // 권한별로 CASE
            switch (userRole) {
                // 총관리자
                case "총관리자":
                    return inventoryDao.selectAllInventorySuperAdmin();
                // 창고 관리자
                case "창고관리자":
                    return inventoryDao.selectAllInventoryWhAdmin(userId);
                // 회원
                case "회원":
                    return inventoryDao.selectAllInventoryMember(userId);
                default:
                    throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
            }
        } catch (Exception e) {
            System.out.println("재고 조회 오류 발생 : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // 대분류 카테고리 리스트
    @Override
    public List<String> getTopCategoryList() {
        return inventoryDao.selectTopCategoryList();
    }

    // 대분류 조회
    @Override
    public List<InventoryResponse> getTopCategoryInventory(String categoryName) {
        return inventoryDao.selectTopCategoryInventory(categoryName);
    }

    // 소분류 카테고리 리스트
    @Override
    public List<String> getSubCategoryList(String topCategory) {
        return inventoryDao.selectSubCategoryList(topCategory);
    }

    // 소분류 조회
    @Override
    public List<InventoryResponse> getSubCategoryInventory(String categoryName) {
        return inventoryDao.selectSubCategoryInventory(categoryName);
    }

    // 상품 상세 조회
    @Override
    public List<ProductResponse> getProductDetail(String userRole, Integer userId, String productName) {
        try {
            // 권한별로 CASE
            switch (userRole) {
                // 총관리자
                case "총관리자":
                    return inventoryDao.selectProductDetailSuperAdmin(productName);
                // 창고 관리자
                case "창고관리자":
                    return inventoryDao.selectProductDetailWhAdmin(userId, productName);
                // 회원
                case "회원":
                    return inventoryDao.selectProductDetailMember(userId, productName);
                default:
                    throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
            }
        } catch (Exception e) {
            System.out.println("상품 조회 오류 발생 : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // 창고 현황 조회
    @Override
    public List<WarehouseStatusResponse> getWarehouse() {
        return inventoryDao.selectWarehouse();
    }

    // 재고 실사 조회
    @Override
    public List<InventoryAuditResponse> getInventoryAuditLog(String userRole, Integer userId) {
        try {
            // 권한별로 CASE
            switch (userRole) {
                // 총관리자
                case "총관리자":
                    return inventoryDao.selectInventoryAuditLogSuperAdmin();
                // 창고 관리자
                case "창고관리자":
                    return inventoryDao.selectInventoryAuditLogWhAdmin(userId);
                default:
                    throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
            }
        } catch (Exception e) {
            System.out.println("재고 조회 오류 발생 : " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
