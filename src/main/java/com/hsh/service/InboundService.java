package main.java.com.hsh.service;

import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;

import java.util.List;

public interface InboundService {
    boolean requestInbound(InboundRequestDto req);
    boolean approveInbound(int inboundId, int approverId);
    boolean cancelInbound(int inboundId);
    boolean updateInboundByMember(InboundRequestDto req);
    List<InboundResponseDto> getInboundList(int userType, int userId); // <-- 반드시 이 이름과 시그니처
    InboundResponseDto getInboundDetail(int inboundId, int userType, int userId);
}
