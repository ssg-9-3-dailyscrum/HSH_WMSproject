package main.java.com.hsh.dao;

import java.sql.SQLException;
import java.util.List;

import main.java.com.hsh.domain.dto.response.InventoryResponse;
import main.java.com.hsh.domain.dto.response.ProductResponse;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponse;

public interface InventoryDao {
    // 전체 재고 조회
    List<InventoryResponse> selectAllInventorySuperAdmin();
    List<InventoryResponse> selectAllInventoryWhAdmin(int adminId);
    List<InventoryResponse> selectAllInventoryMember(int memberId);

//    // 대분류, 소분류 카테고리 리스트
//    List<String> selectTopCategoryList(String userRole, Integer userId) throws SQLException;
//    List<String> selectSubCategoryList(String userRole, Integer userId, String topCategory) throws SQLException;

    // 소분류 카테고리별 조회
//    List<InventoryVo> selectSubCategoryInventorySuperAdmin(String category);
//    List<InventoryVo> selectSubCategoryInventoryWhAdmin(int adminId, String category);
//    List<InventoryVo> selectSubCategoryInventoryMember(int userId, String category);
//
    // 상품 상세 조회
    List<ProductResponse> selectProductDetailSuperAdmin(String productName);
    List<ProductResponse> selectProductDetailWhAdmin(int adminId, String productName);
    List<ProductResponse> selectProductDetailMember(int userId, String productName);

    // 창고 현황 조회
    List<WarehouseStatusResponse> selectWarehouse();

//
//    // 재고실사 조회
//    List<InventoryAuditLogVo> selectInventoryAudit();
}
