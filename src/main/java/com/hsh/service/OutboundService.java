package main.java.com.hsh.service;

import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;

import java.util.List;

public interface OutboundService {

    boolean createRequest(OutboundRequestDto request);
    boolean updateRequest(OutboundRequestDto request);
    boolean approveRequest(int outboundId, int adminId);
    boolean cancelRequest(int outboundId);

    OutboundResponseDto getOutboundDetail(int outboundId, int userId);
    List<OutboundResponseDto> getPendingList(int userId);
    List<OutboundResponseDto> getAllRequests(int adminId);
}
