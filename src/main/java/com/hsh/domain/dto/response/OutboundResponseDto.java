package main.java.com.hsh.domain.dto.response;

import main.java.com.hsh.domain.dto.request.OutboundRequestDto.ProductQuantity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OutboundResponseDto {
    public int outboundId;
    public int memberId;
    public String memberName;
    public int inventoryId;
    public String outboundStatus;
    public LocalDateTime outboundDate;
    public List<ProductQuantity> products = new ArrayList<>();
}