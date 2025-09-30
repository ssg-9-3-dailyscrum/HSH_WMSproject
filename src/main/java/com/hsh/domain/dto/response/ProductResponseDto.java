package main.java.com.hsh.domain.dto.response;

import lombok.Data;

@Data
public class ProductResponseDto {
    private int productId; // 상품ID
    private String productName; // 상품명
    private String productColor; // 색상
    private String price; // 가격
    private String materialCare; // 소재
    private String dimensions; // 가로x세로x높이
    private String content; // 설명
    private String locationAndQuantity; // 위치 및 수량
}