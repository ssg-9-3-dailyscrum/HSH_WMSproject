package main.java.com.hsh.service;


import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;

import java.util.List;

public interface InboundService {
    boolean createRequest(InboundRequestDto request);
    boolean approveRequest(int inboundId, int approverId);
    boolean cancelRequest(int inboundId);
    boolean updateRequest(InboundRequestDto request); // 회원 입고 수정
    List<InboundResponseDto> getInboundList(int userType, int userId);
    InboundResponseDto getInboundDetail(int inboundId, int userType, int userId);
}
