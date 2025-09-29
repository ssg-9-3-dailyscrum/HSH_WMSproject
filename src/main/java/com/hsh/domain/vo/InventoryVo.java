package main.java.com.hsh.domain.vo;

import lombok.Data;

@Data
public class InventoryVo {
    private int inventoryId; // 재고ID
    private int productId; // 상품ID
    private int sectionId; // 섹션ID
    private int warehouseId; // 창고ID
    private int quantity; // 수량
}
