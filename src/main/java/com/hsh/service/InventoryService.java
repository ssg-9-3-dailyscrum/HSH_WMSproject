package main.java.com.hsh.service;

import main.java.com.hsh.domain.dto.response.InventoryAuditResponseDto;
import main.java.com.hsh.domain.dto.response.InventoryResponseDto;
import main.java.com.hsh.domain.dto.response.ProductResponseDto;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponseDto;


import java.util.List;

public interface InventoryService {
    List<InventoryResponseDto> getAllInventory(String userRole, int userId); // 전체 재고 조회

    List<String> getTopCategoryList(); // 대분류 카테고리 리스트
    List<InventoryResponseDto> getTopCategoryInventory(String categoryName); // 대분류 조회

    List<String> getSubCategoryList(String topCategory); // 소분류 카테고리 리스트
    List<InventoryResponseDto> getSubCategoryInventory(String categoryName); // 소분류 재고 조회

    List<ProductResponseDto> getProductDetail(String userRole, Integer userId, String productName); // 상품 상세 조회
    List<WarehouseStatusResponseDto> getWarehouse(); // 창고 현황 조회

    List<InventoryAuditResponseDto> getInventoryAuditLog(String userRole, Integer userId); // 재고 실사 조회
}
