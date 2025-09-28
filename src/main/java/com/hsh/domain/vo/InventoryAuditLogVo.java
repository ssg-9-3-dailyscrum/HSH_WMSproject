package main.java.com.hsh.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryAuditLogVo {
    private int logId; // 로그ID
    private int inventoryId; // 재고ID
    private Date countDate; // 실사일시
    private int systemInventory; // 전산재고
    private int physicalInventory; // 실물재고
}
