package main.java.com.hsh.service;

import main.java.com.hsh.domain.dto.response.InventoryAuditResponse;
import main.java.com.hsh.domain.dto.response.InventoryResponse;
import main.java.com.hsh.domain.dto.response.ProductResponse;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponse;


import java.sql.SQLException;
import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getAllInventory(String userRole, int userId); // 전체 재고 조회

    List<String> getTopCategoryList(); // 대분류 카테고리 리스트
    List<InventoryResponse> getTopCategoryInventory(String categoryName); // 대분류 조회

    List<String> getSubCategoryList(String topCategory); // 소분류 카테고리 리스트
    List<InventoryResponse> getSubCategoryInventory(String categoryName); // 소분류 재고 조회

    List<ProductResponse> getProductDetail(String userRole, Integer userId, String productName); // 상품 상세 조회
    List<WarehouseStatusResponse> getWarehouse(); // 창고 현황 조회

    List<InventoryAuditResponse> getInventoryAuditLog(String userRole, Integer userId); // 재고 실사 조회
}
