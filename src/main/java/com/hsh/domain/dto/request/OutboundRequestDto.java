package main.java.com.hsh.domain.dto.request;

import java.util.ArrayList;
import java.util.List;

public class OutboundRequestDto {
    public int outboundId;
    public int memberId;
    public int inventoryId;
    public List<ProductQuantity> products = new ArrayList<>();

    // 내부 클래스: 상품 ID, 수량, 이름(optional)
    public static class ProductQuantity {
        public int productId;
        public int quantity;
        public String productName; // 상세 조회용

        public ProductQuantity(int productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
    }
}
