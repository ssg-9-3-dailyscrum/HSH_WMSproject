package main.java.com.hsh.domain.dto.response;


import main.java.com.hsh.domain.dto.request.OutboundDetailDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OutboundResponseDto {
    public int outboundId;
    public int memberId;
    public String memberName;
    public int inventoryId;
    public LocalDate outboundDate;
    public String status;

    // 기존: public List<OutboundDetailDto> details;
    public List<OutboundDetailResponseDto> details; // 변경
}