package main.java.com.hsh.service;

import main.java.com.hsh.domain.dto.response.InventoryResponse;
import main.java.com.hsh.domain.dto.response.ProductResponse;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponse;


import java.sql.SQLException;
import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getAllInventory(String userRole, int userId); // 전체 재고 조회
//    List<String> getTopCategoryList(String userRole, Integer userId) throws SQLException; // 대분류 카테고리 리스트
//    List<String> getSubCategoryList(String userRole, Integer userId, String topCategory) throws SQLException; // 소분류 카테고리 리스트
//    List<InventoryResponse> getCategoryInventory(); // 카테고리별 재고 조회

    List<ProductResponse> getProductDetail(String userRole, Integer userId, String productName); // 상품 상세 조회
    List<WarehouseStatusResponse> getWarehouse(); // 창고 현황 조회
}
