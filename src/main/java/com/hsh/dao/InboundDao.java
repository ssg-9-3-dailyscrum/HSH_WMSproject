package main.java.com.hsh.dao;

import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;
import main.java.com.hsh.domain.vo.InboundVo;

import java.util.List;

public interface InboundDao {
    boolean requestInbound(InboundRequestDto request);
    boolean approveInbound(int inboundId, int approverId);
    boolean cancelInbound(int inboundId);
    boolean deleteInbound(int inboundId);

    List<InboundResponseDto> getAllInbound(int userType, int userId);
    InboundResponseDto getInboundDetail(int inboundId, int userType, int userId);
}