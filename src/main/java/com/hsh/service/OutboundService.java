package main.java.com.hsh.service;

import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;

import java.util.List;

public interface OutboundService {
    boolean createRequest(OutboundRequestDto req);
    boolean updateRequest(OutboundRequestDto req, int userType, int userId);
    boolean approveRequest(int outboundId, int adminId);
    boolean cancelRequest(int outboundId);
    OutboundResponseDto getOutboundDetail(int outboundId, int userType, int userId);
    List<OutboundResponseDto> getPendingList(int userType, int userId);
    List<OutboundResponseDto> getAllRequests(int userType, int userId);
    String getUserRole(int userId);
}