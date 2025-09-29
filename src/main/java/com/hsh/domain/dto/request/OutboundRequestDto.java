package main.java.com.hsh.domain.dto.request;

import java.util.List;

public class OutboundRequestDto {
    public int outboundId;  // 수정/조회용
    public int memberId;
    public int inventoryId;

    // 상품 ID + 수량을 담는 리스트
    public List<OutboundDetailDto> products;
}