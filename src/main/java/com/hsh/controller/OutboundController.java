package main.java.com.hsh.controller;

import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;
import main.java.com.hsh.service.OutboundService;
import main.java.com.hsh.service.serviceImpl.OutboundServiceImpl;

import java.util.List;

public class OutboundController {

    private final OutboundService service;

    public OutboundController() {
        this.service = new main.java.com.hsh.service.serviceImpl.OutboundServiceImpl();
    }

    public String getUserRole(int userId) {
        return ((OutboundServiceImpl) service).getUserRole(userId);
    }



    public boolean createRequest(OutboundRequestDto req) { return service.createRequest(req); }
    public boolean updateRequest(OutboundRequestDto req, int userType, int userId) {
        return service.updateRequest(req, userType, userId);
    }

    public boolean approveRequest(int outboundId, int adminId) { return service.approveRequest(outboundId, adminId); }
    public OutboundResponseDto getOutboundDetail(int outboundId, int userType, int userId) { return service.getOutboundDetail(outboundId, userType, userId); }
    public List<OutboundResponseDto> getPendingList(int userType, int userId) { return service.getPendingList(userType, userId); }
    public List<OutboundResponseDto> getAllRequests(int adminId) {
        return service.getAllRequests(0, adminId); // userType은 CLI에서 의미 없음
    }



    public int determineUserType(int userId) {
        if (userId <= 100) return 1;
        if (userId <= 200) return 2;
        return 3;
    }
}
