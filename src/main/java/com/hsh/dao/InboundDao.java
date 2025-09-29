package main.java.com.hsh.dao;

import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;
import main.java.com.hsh.domain.vo.InboundVo;

import java.util.List;

public interface InboundDao {
    boolean requestInbound(InboundRequestDto request);
    boolean approveInbound(int inboundId, int approverId);
    boolean cancelInbound(int inboundId);
    boolean updateInboundByMember(InboundRequestDto request); // 회원 입고 수정

    boolean deleteInbound(int inboundId);

    List<InboundResponseDto> getAllInbound(int userType, int userId);

    // InboundDaoImpl.java
    boolean updateInboundByMember(int inboundId, int memberId, int productId, int quantity);

    InboundResponseDto getInboundDetail(int inboundId, int userType, int userId);
}