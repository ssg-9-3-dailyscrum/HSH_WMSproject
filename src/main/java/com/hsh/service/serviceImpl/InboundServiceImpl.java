package main.java.com.hsh.service.serviceImpl;

import main.java.com.hsh.dao.InboundDao;
import main.java.com.hsh.dao.daoImpl.InboundDaoImpl;
import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;
import main.java.com.hsh.service.InboundService;

import java.util.List;

public class InboundServiceImpl implements InboundService {

    private static InboundServiceImpl instance; // 싱글톤
    private final InboundDao inboundDao;

    private InboundServiceImpl() {
        this.inboundDao = new InboundDaoImpl();
    }

    public static synchronized InboundServiceImpl getInstance() {
        if (instance == null) {
            instance = new InboundServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean requestInbound(InboundRequestDto req) {
        return inboundDao.requestInbound(req);
    }

    @Override
    public boolean approveInbound(int inboundId, int approverId) {
        String role = inboundDao.getUserRole(approverId);
        if (!"총관리자".equals(role) && !"창고관리자".equals(role)) {
            System.out.println("승인 권한이 없습니다");
            return false;
        }
        return inboundDao.approveInbound(inboundId, approverId);
    }

    @Override
    public boolean cancelInbound(int inboundId) {
        return inboundDao.cancelInbound(inboundId);
    }

    @Override
    public boolean updateInboundByMember(InboundRequestDto req) {
        return inboundDao.updateInboundByMember(req);
    }

    @Override
    public List<InboundResponseDto> getInboundList(int userType, int userId) {
        return inboundDao.getInboundList(userType, userId);
    }

    @Override
    public InboundResponseDto getInboundDetail(int inboundId, int userType, int userId) {
        return inboundDao.getInboundDetail(inboundId, userType, userId);
    }
}
