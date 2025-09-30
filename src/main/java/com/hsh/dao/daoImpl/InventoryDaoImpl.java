package main.java.com.hsh.dao.daoImpl;

import main.java.com.hsh.dao.InventoryDao;
import main.java.com.hsh.domain.dto.response.InventoryAuditResponseDto;
import main.java.com.hsh.domain.dto.response.InventoryResponseDto;
import main.java.com.hsh.domain.dto.response.ProductResponseDto;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponseDto;
import main.java.com.hsh.util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventoryDaoImpl implements InventoryDao {

    // 전체 재고 조회 : 총관리자
    @Override
    public List<InventoryResponseDto> selectAllInventorySuperAdmin() {
        List<InventoryResponseDto> resultList = new ArrayList<>();
        String sql = "CALL usp_inventory_SelectAll_superadmin()";

        try(Connection conn = DBUtil.getConnection();
        CallableStatement call = conn.prepareCall(sql);
            ResultSet rs = call.executeQuery()) {

            while(rs.next()) {
                InventoryResponseDto dto = new InventoryResponseDto();
                dto.setWarehouseName(rs.getString("창고명"));
                dto.setSectionText(rs.getString("섹션명"));
                dto.setProductId(rs.getInt("상품ID"));
                dto.setProductName(rs.getString("상품명"));
                dto.setQuantity(rs.getInt("수량"));
                resultList.add(dto);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 전체 재고 조회 : 창고관리자
    @Override
    public List<InventoryResponseDto> selectAllInventoryWhAdmin(Integer userId) {
        List<InventoryResponseDto> resultList = new ArrayList<>();
        String sql = "CALL usp_inventory_SelectAll_whadmin(?)";

        try(Connection conn = DBUtil.getConnection();
            CallableStatement call = conn.prepareCall(sql)) {

            call.setInt(1, userId);

            try(ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    InventoryResponseDto dto = new InventoryResponseDto();
                    dto.setSectionText(rs.getString("섹션명"));
                    dto.setProductId(rs.getInt("상품ID"));
                    dto.setProductName(rs.getString("상품명"));
                    dto.setQuantity(rs.getInt("수량"));
                    resultList.add(dto);
                }
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 전체 재고 조회 : 회원
    @Override
    public List<InventoryResponseDto> selectAllInventoryMember(Integer userId) {
        List<InventoryResponseDto> resultList = new ArrayList<>();
        String sql = "CALL usp_inventory_SelectAll_member(?)";

        try(Connection conn = DBUtil.getConnection();
            CallableStatement call = conn.prepareCall(sql)) {

            call.setInt(1, userId);

            try(ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    InventoryResponseDto dto = new InventoryResponseDto();
                    dto.setWarehouseName(rs.getString("창고명"));
                    dto.setProductId(rs.getInt("상품ID"));
                    dto.setProductName(rs.getString("상품명"));
                    dto.setQuantity(rs.getInt("수량"));
                    resultList.add(dto);
                }
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 대분류 카테고리 리스트
    @Override
    public List<String> selectTopCategoryList() {
        List<String> categories = new ArrayList<>();
        String sql = "CALL usp_Category_TopList()";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {

            try (ResultSet rs = call.executeQuery()) {
                while (rs.next()) {
                    categories.add(rs.getString("카테고리명"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    // 대분류 조회
    @Override
    public List<InventoryResponseDto> selectTopCategoryInventory(String categoryName) {
        List<InventoryResponseDto> list = new ArrayList<>();
        String sql = "CALL usp_Inventory_SelectTopCategory(?)";

        try(Connection conn = DBUtil.getConnection();
            CallableStatement call = conn.prepareCall(sql)) {

            call.setString(1, categoryName);

            try(ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    InventoryResponseDto dto = new InventoryResponseDto();
                    dto.setCategoryName(rs.getString("대분류"));
                    dto.setProductId(rs.getInt("상품ID"));
                    dto.setProductName(rs.getString("상품명"));
                    dto.setWarehouseName(rs.getString("창고명"));
                    dto.setSectionText(rs.getString("섹션명"));
                    dto.setQuantity(rs.getInt("수량"));
                    list.add(dto);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 소분류 카테고리 리스트
    @Override
    public List<String> selectSubCategoryList(String topCategory) {
        List<String> categories = new ArrayList<>();
        String sql = "CALL usp_Category_SubList(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {

            call.setString(1, topCategory);

            try (ResultSet rs = call.executeQuery()) {
                while (rs.next()) {
                    categories.add(rs.getString("카테고리명"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    // 소분류 조회
    @Override
    public List<InventoryResponseDto> selectSubCategoryInventory(String categoryName) {
        List<InventoryResponseDto> list = new ArrayList<>();
        String sql = "CALL usp_Inventory_SelectSubCategory(?)";

        try(Connection conn = DBUtil.getConnection();
            CallableStatement call = conn.prepareCall(sql)) {

            call.setString(1, categoryName);

            try(ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    InventoryResponseDto dto = new InventoryResponseDto();
                    dto.setCategoryName(rs.getString("소분류"));
                    dto.setProductId(rs.getInt("상품ID"));
                    dto.setProductName(rs.getString("상품명"));
                    dto.setWarehouseName(rs.getString("창고명"));
                    dto.setSectionText(rs.getString("섹션명"));
                    dto.setQuantity(rs.getInt("수량"));
                    list.add(dto);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 상품 상세 조회 : 총관리자
    @Override
    public List<ProductResponseDto> selectProductDetailSuperAdmin(String productName) {
        List<ProductResponseDto> list = new ArrayList<>();
        String sql = "CALL usp_inventory_SelectProductDetail_superadmin(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {

            call.setString(1, productName);

            try (ResultSet rs = call.executeQuery()) {
                while (rs.next()) {
                    ProductResponseDto dto = new ProductResponseDto();
                    dto.setProductId(rs.getInt("상품ID"));
                    dto.setProductName(rs.getString("상품명"));
                    dto.setProductColor(rs.getString("색상"));
                    dto.setPrice(rs.getString("가격"));
                    dto.setMaterialCare(rs.getString("소재"));
                    dto.setDimensions(rs.getString("가로x세로x높이"));
                    dto.setContent(rs.getString("설명"));
                    dto.setLocationAndQuantity(rs.getString("위치 및 수량"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 상품 상세 조회 : 창고관리자
    @Override
    public List<ProductResponseDto> selectProductDetailWhAdmin(Integer adminId, String productName) {
        List<ProductResponseDto> list = new ArrayList<>();
        String sql = "CALL usp_inventory_SelectProductDetail_whadmin(?, ?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {

            call.setInt(1, adminId);
            call.setString(2, productName);

            try (ResultSet rs = call.executeQuery()) {
                while (rs.next()) {
                    ProductResponseDto dto = new ProductResponseDto();
                    dto.setProductId(rs.getInt("상품ID"));
                    dto.setProductName(rs.getString("상품명"));
                    dto.setProductColor(rs.getString("색상"));
                    dto.setPrice(rs.getString("가격"));
                    dto.setMaterialCare(rs.getString("소재"));
                    dto.setDimensions(rs.getString("가로x세로x높이"));
                    dto.setContent(rs.getString("설명"));
                    dto.setLocationAndQuantity(rs.getString("위치 및 수량"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 상품 상세 조회 : 회원
    @Override
    public List<ProductResponseDto> selectProductDetailMember(Integer userId, String productName) {
        List<ProductResponseDto> list = new ArrayList<>();
        String sql = "CALL usp_inventory_SelectProductDetail_member(?, ?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement call = conn.prepareCall(sql)) {

            call.setInt(1, userId);
            call.setString(2, productName);

            try (ResultSet rs = call.executeQuery()) {
                while (rs.next()) {
                    ProductResponseDto dto = new ProductResponseDto();
                    dto.setProductId(rs.getInt("상품ID"));
                    dto.setProductName(rs.getString("상품명"));
                    dto.setProductColor(rs.getString("색상"));
                    dto.setPrice(rs.getString("가격"));
                    dto.setMaterialCare(rs.getString("소재"));
                    dto.setDimensions(rs.getString("가로x세로x높이"));
                    dto.setContent(rs.getString("설명"));
                    dto.setLocationAndQuantity(rs.getString("위치 및 수량"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 창고 현황 조회 : 총관리자, 창고관리자
    @Override
    public List<WarehouseStatusResponseDto> selectWarehouse() {
        List<WarehouseStatusResponseDto> list = new ArrayList<>();
        String sql = "CALL usp_inventory_SelectWarehouse()";

        try(Connection conn = DBUtil.getConnection();
            CallableStatement call = conn.prepareCall(sql);
            ResultSet rs = call.executeQuery()) {

            while(rs.next()) {
                WarehouseStatusResponseDto dto = new WarehouseStatusResponseDto();
                dto.setWarehouseId(rs.getInt("창고ID"));
                dto.setWarehouseName(rs.getString("창고명"));
                dto.setWarehouseType(rs.getString("창고종류"));
                dto.setWarehouseCapacity(rs.getDouble("수용한도(m³)"));
                dto.setCurrentOccupancy(rs.getDouble("현재점유량(m³)"));
                dto.setRemainingCapacity(rs.getDouble("남은수용량(m³)"));
                dto.setOccupancyRate(rs.getDouble("점유율(%)"));
                dto.setWarehouseStatus(rs.getString("운용현황"));
                dto.setTotalCount(rs.getInt("총재고수량"));
                list.add(dto);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 재고 실사 조회 : 총관리자
    @Override
    public List<InventoryAuditResponseDto> selectInventoryAuditLogSuperAdmin() {
        List<InventoryAuditResponseDto> list = new ArrayList<>();
        String sql = "CALL usp_inventory_AuditLog_superadmin()";

        try(Connection conn = DBUtil.getConnection();
            CallableStatement call = conn.prepareCall(sql);
            ResultSet rs = call.executeQuery()) {

            while(rs.next()) {
                InventoryAuditResponseDto dto = new InventoryAuditResponseDto();
                dto.setLogId(rs.getInt("로그ID"));
                dto.setWarehouseName(rs.getString("창고명"));
                dto.setSectionText(rs.getString("섹션명"));
                dto.setProductName(rs.getString("상품명"));
                dto.setCountDate(rs.getTimestamp("실사일자").toLocalDateTime());
                dto.setSystemInventory(rs.getInt("시스템재고"));
                dto.setPhysicalInventory(rs.getInt("실제재고"));
                dto.setDiffInventory(rs.getInt("차이"));
                dto.setStatus(rs.getString("상태"));
                list.add(dto);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 재고 실사 조회 : 창고관리자
    @Override
    public List<InventoryAuditResponseDto> selectInventoryAuditLogWhAdmin(Integer userId) {
        List<InventoryAuditResponseDto> list = new ArrayList<>();
        String sql = "CALL usp_inventory_AuditLog_whadmin(?)";

        try(Connection conn = DBUtil.getConnection();
            CallableStatement call = conn.prepareCall(sql)) {

            call.setInt(1, userId);

            try (ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    InventoryAuditResponseDto dto = new InventoryAuditResponseDto();
                    dto.setLogId(rs.getInt("로그ID"));
                    dto.setWarehouseName(rs.getString("창고명"));
                    dto.setSectionText(rs.getString("섹션명"));
                    dto.setProductName(rs.getString("상품명"));
                    dto.setCountDate(rs.getTimestamp("실사일자").toLocalDateTime());
                    dto.setSystemInventory(rs.getInt("시스템재고"));
                    dto.setPhysicalInventory(rs.getInt("실제재고"));
                    dto.setDiffInventory(rs.getInt("차이"));
                    dto.setStatus(rs.getString("상태"));
                    list.add(dto);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
