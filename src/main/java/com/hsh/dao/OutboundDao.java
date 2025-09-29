package main.java.com.hsh.dao;

import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;

import java.util.List;

public interface OutboundDao {
    boolean requestOutbound(OutboundRequestDto request);
    boolean approveOutbound(int outboundId);
    boolean updateOutboundRequest(OutboundRequestDto request);
    boolean cancelOutbound(int outboundId);

    OutboundResponseDto getOutboundDetail(int outboundId);
    List<OutboundResponseDto> getPendingOutboundRequests();
    List<OutboundResponseDto> getAllOutbound();
}
