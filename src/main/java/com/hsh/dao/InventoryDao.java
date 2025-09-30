package main.java.com.hsh.dao;

import java.util.List;

import main.java.com.hsh.domain.dto.response.InventoryAuditResponseDto;
import main.java.com.hsh.domain.dto.response.InventoryResponseDto;
import main.java.com.hsh.domain.dto.response.ProductResponseDto;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponseDto;

public interface InventoryDao {
    // 전체 재고 조회
    List<InventoryResponseDto> selectAllInventorySuperAdmin();
    List<InventoryResponseDto> selectAllInventoryWhAdmin(Integer adminId);
    List<InventoryResponseDto> selectAllInventoryMember(Integer memberId);

    // 대분류 카테고리 리스트
    List<String> selectTopCategoryList() ;
    // 대분류 조회
    List<InventoryResponseDto> selectTopCategoryInventory(String categoryName);

    // 소분류 카테고리 리스트
    List<String> selectSubCategoryList(String topCategory);
    // 소분류 조회
    List<InventoryResponseDto> selectSubCategoryInventory(String categoryName);

    // 상품 상세 조회
    List<ProductResponseDto> selectProductDetailSuperAdmin(String productName);
    List<ProductResponseDto> selectProductDetailWhAdmin(Integer adminId, String productName);
    List<ProductResponseDto> selectProductDetailMember(Integer userId, String productName);

    // 창고 현황 조회
    List<WarehouseStatusResponseDto> selectWarehouse();


    // 재고실사 조회
    List<InventoryAuditResponseDto> selectInventoryAuditLogSuperAdmin();
    List<InventoryAuditResponseDto> selectInventoryAuditLogWhAdmin(Integer userId);
}
