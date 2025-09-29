package main.java.com.hsh.service.seviceImpl;

import main.java.com.hsh.dao.OutboundDao;
import main.java.com.hsh.dao.daoImpl.OutboundDaoImpl;
import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;
import main.java.com.hsh.service.OutboundService;
import main.java.com.hsh.domain.vo.OutboundVo;


import java.util.ArrayList;
import java.util.List;

public class OutboundServiceImpl implements OutboundService {
    private final OutboundDaoImpl dao = new OutboundDaoImpl();

    @Override
    public boolean createRequest(OutboundRequestDto req) {
        return outboundDao.requestOutbound(req);
    }




    private final OutboundDao outboundDao;

    public OutboundServiceImpl() {
        this.outboundDao = new OutboundDaoImpl(); // 반드시 초기화
    }

    @Override
    public boolean cancelRequest(int outboundId) {
        return outboundDao.cancelOutbound(outboundId);
    }


    @Override
    public boolean updateRequest(OutboundRequestDto req, int userType, int userId) {
        // 회원이면 본인 출고만 수정 가능
        if (userType == 1) {
            OutboundResponseDto dto = dao.getOutboundDetail(req.outboundId);
            if (dto == null || dto.memberId != userId) {
                return false; // 권한 없음 또는 존재하지 않음
            }
        }

        // DAO에서 update 수행 후 영향받은 row 확인
        return dao.updateOutboundRequest(req);
    }


    public String getUserRole(int userId) {
        return outboundDao.getUserRole(userId);
    }


    @Override
    public List<OutboundResponseDto> getAllRequests(int userType, int userId) {
        String role = outboundDao.getUserRole(userId);
        if (role == null) return new ArrayList<>();

        // 총관리자, 창고관리자만 전체 조회 가능
        if (!role.equals("총관리자") && !role.equals("창고관리자")) {
            System.out.println("전체 출고 조회는 관리자만 가능합니다.");
            return new ArrayList<>();
        }

        return outboundDao.getAllOutbound(userId, role);
    }






    @Override
    public boolean approveRequest(int outboundId, int adminId) {
        return outboundDao.approveOutbound(outboundId, adminId);
    }

    @Override
    public OutboundResponseDto getOutboundDetail(int outboundId, int userType, int userId) {
        OutboundResponseDto dto = outboundDao.getOutboundDetail(outboundId);
        if (dto == null) return null;

        // 회원은 본인 출고만 조회 가능
        if (userType == 1 && dto.memberId != userId) return null;

        return dto;
    }



    @Override
    public List<OutboundResponseDto> getPendingList(int userType, int userId) {
        return outboundDao.getPendingOutboundRequests(userType, userId);
    }





}
