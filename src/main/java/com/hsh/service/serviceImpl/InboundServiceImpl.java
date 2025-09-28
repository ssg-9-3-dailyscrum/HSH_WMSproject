package main.java.com.hsh.service.seviceImpl;


import main.java.com.hsh.dao.InboundDao;
import main.java.com.hsh.dao.daoImpl.InboundDaoImpl;
import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;
import main.java.com.hsh.service.InboundService;

import java.util.List;
import java.util.stream.Collectors;

public class InboundServiceImpl implements InboundService {
    private final InboundDao dao = new InboundDaoImpl();

    @Override
    public boolean createRequest(InboundRequestDto request) {
        return dao.requestInbound(request);
    }

    @Override
    public boolean approveRequest(int inboundId, int approverId) {
        return dao.approveInbound(inboundId, approverId);
    }

    @Override
    public boolean cancelRequest(int inboundId) {
        return dao.cancelInbound(inboundId);
    }

    @Override
    public List<InboundResponseDto> getInboundList(int userType, int userId) {
        return dao.getAllInbound(userType, userId);
    }

    @Override
    public InboundResponseDto getInboundDetail(int inboundId, int userType, int userId) {
        return dao.getInboundDetail(inboundId, userType, userId);
    }
}