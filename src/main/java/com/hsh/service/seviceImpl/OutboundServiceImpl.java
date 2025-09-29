package main.java.com.hsh.service.seviceImpl;

import main.java.com.hsh.dao.OutboundDao;
import main.java.com.hsh.dao.daoImpl.OutboundDaoImpl;
import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;
import main.java.com.hsh.service.OutboundService;

import java.util.List;

public class OutboundServiceImpl implements OutboundService {

    private final OutboundDao dao = new OutboundDaoImpl();

    @Override
    public boolean createRequest(OutboundRequestDto request) {
        return dao.requestOutbound(request);
    }

    @Override
    public boolean updateRequest(OutboundRequestDto request) {
        return dao.updateOutboundRequest(request);
    }

    @Override
    public boolean approveRequest(int outboundId, int adminId) {
        return dao.approveOutbound(outboundId);
    }

    @Override
    public boolean cancelRequest(int outboundId) {
        return dao.cancelOutbound(outboundId);
    }

    @Override
    public OutboundResponseDto getOutboundDetail(int outboundId, int userId) {
        return dao.getOutboundDetail(outboundId); // 권한 체크는 나중에 추가 가능
    }

    @Override
    public List<OutboundResponseDto> getPendingList(int userId) {
        return dao.getPendingOutboundRequests();
    }

    @Override
    public List<OutboundResponseDto> getAllRequests(int adminId) {
        return dao.getAllOutbound();
    }
}
