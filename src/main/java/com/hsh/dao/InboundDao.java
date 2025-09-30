package main.java.com.hsh.dao;

import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;
import java.util.List;

public interface InboundDao {
    boolean requestInbound(InboundRequestDto req);
    boolean approveInbound(int inboundId, int approverId);
    boolean cancelInboundByMember(int inboundId, int memberId);

    boolean updateInboundByMember(InboundRequestDto req);
    List<InboundResponseDto> getInboundList(int userType, int userId);
    InboundResponseDto getInboundDetail(int inboundId, int userType, int userId);
    String getUserRole(int userId);


}
