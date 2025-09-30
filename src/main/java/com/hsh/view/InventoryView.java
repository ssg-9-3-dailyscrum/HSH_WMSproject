package main.java.com.hsh.view;

import main.java.com.hsh.domain.dto.response.InventoryAuditResponseDto;
import main.java.com.hsh.domain.dto.response.InventoryResponseDto;
import main.java.com.hsh.domain.dto.response.ProductResponseDto;
import main.java.com.hsh.domain.dto.response.WarehouseStatusResponseDto;
import main.java.com.hsh.domain.vo.UserVo;
import main.java.com.hsh.session.UserSession;
import main.java.com.hsh.session.AdminSession;
import java.util.List;


public class InventoryView {

    // 전체 재고 조회
    public void displayAllInventory(List<InventoryResponseDto> list) {
        System.out.println("\n========= 전체 재고 조회 =========");
        System.out.println();

        if (list.isEmpty()) {
            System.out.println("재고가 존재하지 않습니다.");
            return;
        }

        System.out.println("총 " + list.size() + "개의 상품이 검색되었습니다.");
        System.out.println();

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
        }

        printHeader(userRole);
        printSeparator(userRole);

        for (InventoryResponseDto dto : list) {
            printRow(dto, userRole);
        }
        System.out.println();
    }

    private void printHeader(String userRole) {
        switch(userRole) {
            case "총관리자" ->
                    System.out.printf("%-15s | %-15s | %-10s | %-20s | %10s\n",
                            "창고명", "섹션명", "상품ID", "상품명", "수량");
            case "창고관리자" ->
                    System.out.printf("%-15s | %-10s | %-20s | %10s\n",
                            "섹션명", "상품ID", "상품명", "수량");
            case "회원" ->
                    System.out.printf("%-15s | %-10s | %-20s | %10s\n",
                            "창고명", "상품ID", "상품명", "수량");
        }
    }

    private void printSeparator(String userRole) {
        switch(userRole) {
            case "총관리자":
                System.out.println("-".repeat(75));
                break;
            case "창고관리자":
            case "회원":
                System.out.println("-".repeat(60));
                break;
        }
    }

    private void printRow(InventoryResponseDto dto, String userRole) {
        switch(userRole) {
            case "총관리자" ->
                    System.out.printf("%-15s | %-15s | %-10d | %-20s | %,10d\n",
                            dto.getWarehouseName() != null ? dto.getWarehouseName() : "-",
                            dto.getSectionText() != null ? dto.getSectionText() : "-",
                            dto.getProductId(),
                            dto.getProductName(),
                            dto.getQuantity());
            case "창고관리자" ->
                    System.out.printf("%-15s | %-10d | %-20s | %,10d\n",
                            dto.getSectionText() != null ? dto.getSectionText() : "-",
                            dto.getProductId(),
                            dto.getProductName(),
                            dto.getQuantity());
            case "회원" ->
                    System.out.printf("%-15s | %-10d | %-20s | %,10d\n",
                            dto.getWarehouseName() != null ? dto.getWarehouseName() : "-",
                            dto.getProductId(),
                            dto.getProductName(),
                            dto.getQuantity());
        }
    }

    // 대분류 조회
    public void displayTopCategoryInventory(List<InventoryResponseDto> list) {
        System.out.println("\n========= 대분류 조회 =========");
        System.out.println();

        if (list.isEmpty()) {
            System.out.println("상품이 존재하지 않습니다.");
            return;
        }

        System.out.println("총 " + list.size() + "개의 상품이 검색되었습니다.");
        System.out.println();

        System.out.printf("%-15s | %-10s | %-20s | %-15s | %-15s | %10s\n",
                "대분류", "상품ID", "상품명", "창고명", "섹션명", "수량");

        for (InventoryResponseDto dto : list) {
            System.out.printf("%-15s | %-10d | %-20s | %-15s | %-15s | %10d\n",
                    dto.getCategoryName(),
                    dto.getProductId(),
                    dto.getProductName(),
                    dto.getWarehouseName(),
                    dto.getSectionText(),
                    dto.getQuantity());
        }
        System.out.println();
    }

    // 소분류 조회
    public void displaySubCategoryInventory(List<InventoryResponseDto> list) {
        System.out.println("\n========= 소분류 조회 =========");
        System.out.println();

        if (list.isEmpty()) {
            System.out.println("상품이 존재하지 않습니다.");
            return;
        }

        System.out.println("총 " + list.size() + "개의 상품이 검색되었습니다.");
        System.out.println();

        System.out.printf("%-15s | %-10s | %-20s | %-15s | %-15s | %10s\n",
                "소분류", "상품ID", "상품명", "창고명", "섹션명", "수량");

        for (InventoryResponseDto dto : list) {
            System.out.printf("%-15s | %-10d | %-20s | %-15s | %-15s | %10d\n",
                    dto.getCategoryName(),
                    dto.getProductId(),
                    dto.getProductName(),
                    dto.getWarehouseName(),
                    dto.getSectionText(),
                    dto.getQuantity());
        }
        System.out.println();
    }

    // 상품 상세 조회
    public void displayProductDetail(List<ProductResponseDto> list) {
        System.out.println("\n========= 상품 상세 조회 =========");
        System.out.println();

        if (list.isEmpty()) {
            System.out.println("상품이 존재하지 않습니다.");
            return;
        }

        System.out.println("총 " + list.size() + "개의 상품이 검색되었습니다.");
        System.out.println();

        for (ProductResponseDto product : list) {
            printProductDetailBox(product);
            System.out.println();
        }
    }

    private void printProductDetailBox(ProductResponseDto product) {
        System.out.println("─".repeat(60));
        System.out.printf("%-58s\n", "상품 정보");
        System.out.println("─".repeat(60));
        System.out.printf("상품ID    : %-48d\n", product.getProductId());
        System.out.printf("상품명    : %-48s\n", product.getProductName());
        System.out.printf("색상      : %-48s\n", product.getProductColor());
        System.out.printf("가격      : %-48s\n", product.getPrice());
        System.out.printf("소재      : %-48s\n", product.getMaterialCare());
        System.out.printf("크기      : %-48s\n", product.getDimensions());
        System.out.println("─".repeat(60));

        // 설명은 길 수 있으므로 여러 줄로 처리
        String content = product.getContent();
        if (content != null && content.length() > 48) {
            System.out.printf("설명      : %-48s\n", content.substring(0, 48) + "...");
        } else {
            System.out.printf("설명      : %-48s\n", content != null ? content : "-");
        }

        System.out.println("─".repeat(60));
        System.out.printf("%-58s\n", "위치 및 수량");
        System.out.println("─".repeat(60));

        // 위치 및 수량 정보 처리
        String locationInfo = product.getLocationAndQuantity();
        if (locationInfo != null && !locationInfo.isEmpty()) {
            String[] locations = locationInfo.split(", ");
            for (String location : locations) {
                if (location.length() > 56) {
                    System.out.printf("%-58s\n", location.substring(0, 56) + "...");
                } else {
                    System.out.printf("%-58s\n", location);
                }
            }
        } else {
            System.out.printf("%-58s\n", "상품 정보 없음");
        }

        System.out.println("─".repeat(60));
    }

    // 창고 현황 조회
    public void displayWarehouseInventory(List<WarehouseStatusResponseDto> list) {
        System.out.println("\n========= 창고 현황 조회 =========");
        System.out.println();

        if (list.isEmpty()) {
            System.out.println("창고가 존재하지 않습니다.");
            return;
        }

        System.out.println("총 " + list.size() + "개의 창고가 조회되었습니다.");
        System.out.println();

        printWarehouseTable(list);
    }

    private void printWarehouseTable(List<WarehouseStatusResponseDto> warehouseList) {
        printWarehouseHeader();
        printWarehouseSeparator();

        for (WarehouseStatusResponseDto warehouse : warehouseList) {
            printWarehouseRow(warehouse);
        }
        System.out.println();
    }

    private void printWarehouseHeader() {
        System.out.printf("%-5s | %-12s | %-8s | %-8s | %-8s | %-8s | %-6s | %-4s | %-8s\n",
                "창고ID", "창고명", "창고종류", "수용한도", "점유량", "남은용량", "점유율", "운용", "총수량");
        System.out.printf("%-5s | %-12s | %-8s | %-8s | %-8s | %-8s | %-6s | %-4s | %-8s\n",
                "", "", "", "(m³)", "(m³)", "(m³)", "(%)", "현황", "수량");
    }

    private void printWarehouseSeparator() {
        System.out.println("-".repeat(5) + "-+-" + "-".repeat(12) + "-+-" + "-".repeat(8) + "-+-" +
                "-".repeat(8) + "-+-" + "-".repeat(8) + "-+-" + "-".repeat(8) + "-+-" +
                "-".repeat(6) + "-+-" + "-".repeat(4) + "-+-" + "-".repeat(8));
    }

    private void printWarehouseRow(WarehouseStatusResponseDto warehouse) {
        System.out.printf("%-5d | %-12s | %-8s | %8.1f | %8.1f | %8.1f | %5.1f%% | %-4s | %,8d\n",
                warehouse.getWarehouseId(),
                warehouse.getWarehouseName(),
                warehouse.getWarehouseType(),
                warehouse.getWarehouseCapacity(),
                warehouse.getCurrentOccupancy(),
                warehouse.getRemainingCapacity(),
                warehouse.getOccupancyRate(),
                warehouse.getWarehouseStatus(),
                warehouse.getTotalCount());
    }

    public void displayInventoryAuditLog(List<InventoryAuditResponseDto> list) {
        System.out.println("\n========= 재고 실사 조회 =========");
        System.out.println();

        if (list.isEmpty()) {
            System.out.println("재고실사가 기록이 없습니다.");
            return;
        }

        System.out.println("총 " + list.size() + "건의 재고실사 기록이 조회되었습니다.");
        System.out.println();

        printAuditLogTable(list);
    }

    // 재고 실사 조회
    private void printAuditLogTable(List<InventoryAuditResponseDto> auditList) {
        System.out.println("로그ID | 창고명                | 섹션명     | 상품명                     | 실사일자            | 시스템 | 실제 | 차이 | 상태");
        System.out.println("=".repeat(120));

        for (InventoryAuditResponseDto audit : auditList) {
            System.out.printf("%6d | %-18s | %-8s | %-25s | %-17s | %6d | %4d | %4s | %-4s\n",
                    audit.getLogId(),
                    audit.getWarehouseName(),
                    audit.getSectionText(),
                    audit.getProductName(),
                    audit.getCountDate(),
                    audit.getSystemInventory(),
                    audit.getPhysicalInventory(),
                    audit.getDiffInventory(),
                    audit.getStatus());
        }
        System.out.println();
    }
}
