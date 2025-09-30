package main.java.com.hsh.domain.dto.response;

import lombok.Data;

@Data
public class InventoryResponseDto {
    private String warehouseName; // 창고명
    private String sectionText; // 섹션명
    private int productId; // 상품ID
    private String productName; // 상품명
    private int quantity; // 수량
    private String categoryName; // 카테고리명
}
