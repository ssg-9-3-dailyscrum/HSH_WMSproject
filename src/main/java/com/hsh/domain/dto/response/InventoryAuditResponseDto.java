package main.java.com.hsh.domain.dto.response;

import lombok.Data;


@Data
public class InventoryAuditResponseDto {
    private int logId; // 로그ID
    private String warehouseName; // 창고명
    private String sectionText; // 섹션명
    private String productName; // 상품명
    private java.time.LocalDateTime countDate; // 실사일자
    private int SystemInventory; // 시스템재고
    private int PhysicalInventory; // 시스템재고
    private int diffInventory; // 차이
    private String status; // 상태
}
