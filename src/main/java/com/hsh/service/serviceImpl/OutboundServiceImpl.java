package main.java.com.hsh.service.serviceImpl;

import main.java.com.hsh.dao.OutboundDao;
import main.java.com.hsh.dao.daoImpl.OutboundDaoImpl;
import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;
import main.java.com.hsh.service.OutboundService;

import java.util.ArrayList;
import java.util.List;

public class OutboundServiceImpl implements OutboundService {

    private final OutboundDao outboundDao;

    public OutboundServiceImpl() {
        this.outboundDao = new OutboundDaoImpl();
    }

    @Override
    public boolean createRequest(OutboundRequestDto req) {
        return outboundDao.createOutboundRequest(req);
    }

    @Override
    public boolean approveRequest(int outboundId, int adminId) {
        // DB 호출 후 성공 여부 반환
        boolean success = outboundDao.approveOutbound(outboundId, adminId);

        if (!success) {
            System.out.println(":: 승인 실패: 존재하지 않는 출고 ID이거나 이미 승인된 요청입니다. ::");
        }
        return success;
    }


    @Override
    public boolean cancelRequest(int outboundId) {
        return outboundDao.cancelOutbound(outboundId);
    }

    @Override
    public boolean updateRequest(OutboundRequestDto req, int userType, int userId) {
        // 회원이면 본인 출고만 수정 가능
        if (userType == 2) {
            OutboundResponseDto dto = outboundDao.getOutboundDetail(req.outboundId, userType, userId);
            if (dto == null || dto.memberId != userId) return false;
        }
        return outboundDao.updateOutboundRequest(req);
    }

    @Override
    public OutboundResponseDto getOutboundDetail(int outboundId, int userType, int userId) {
        OutboundResponseDto dto = outboundDao.getOutboundDetail(outboundId, userType, userId);
        if (dto == null) return null;
        if (userType == 2 && dto.memberId != userId) return null;
        return dto;
    }

    @Override
    public List<OutboundResponseDto> getPendingList(int userType, int userId) {
        return outboundDao.getPendingOutboundRequests(userType, userId);
    }

    @Override
    public List<OutboundResponseDto> getAllRequests(int userType, int userId) {
        String role = outboundDao.getUserRole(userId);
        if (!role.equals("총관리자") && !role.equals("창고관리자")) return new ArrayList<>();
        return outboundDao.getAllOutbound(userId, role);
    }

    @Override
    public String getUserRole(int userId) {
        return outboundDao.getUserRole(userId);
    }
}
