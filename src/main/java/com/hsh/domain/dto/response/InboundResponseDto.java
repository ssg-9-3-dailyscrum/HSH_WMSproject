package main.java.com.hsh.domain.dto.response;

import main.java.com.hsh.domain.dto.request.InboundRequestDto;

import java.time.LocalDate;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InboundResponseDto {
    public int inboundId;
    public int memberId;
    public int sectionId;
    public int warehouseId;
    public String status;
    public int quantity;            // 현재 총 수량
    public int requestedQuantity;   // 입고 요청 수량
    public LocalDate inboundDate;   // 실제 입고일
    public LocalDate requestDate;   // 입고 요청일
    public List<InboundDetailDto> details = new ArrayList<>();

}
