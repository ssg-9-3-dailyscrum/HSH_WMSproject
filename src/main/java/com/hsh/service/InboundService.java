package main.java.com.hsh.service;

import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;

import java.util.List;

public interface InboundService {
    boolean requestInbound(InboundRequestDto req);
    boolean approveInbound(int inboundId, int approverId);

    boolean cancelInboundByMember(int inboundId, int memberId); // 회원용
    boolean updateInboundByMember(InboundRequestDto req);

    List<InboundResponseDto> getInboundList(int userType, int userId);
    InboundResponseDto getInboundDetail(int inboundId, int userType, int userId);
}
