package main.java.com.hsh.dao;

import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;

import java.util.List;

public interface OutboundDao {
    boolean createOutboundRequest(OutboundRequestDto req);
    boolean approveOutbound(int outboundId, int adminId);
    boolean cancelOutbound(int outboundId);
    boolean updateOutboundRequest(OutboundRequestDto req);
    OutboundResponseDto getOutboundDetail(int outboundId, int userType, int userId);
    List<OutboundResponseDto> getPendingOutboundRequests(int userType, int userId);
    List<OutboundResponseDto> getAllOutbound(int adminId, String role);
    String getUserRole(int userId);
}
