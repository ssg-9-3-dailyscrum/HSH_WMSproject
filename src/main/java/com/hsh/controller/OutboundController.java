package main.java.com.hsh.controller;

import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;
import main.java.com.hsh.service.OutboundService;
import main.java.com.hsh.service.serviceImpl.OutboundServiceImpl;

import java.util.List;

public class OutboundController {
    private final OutboundService service;

    public OutboundController() {
        this.service = new OutboundServiceImpl();
    }

    public boolean createRequest(OutboundRequestDto req) { return service.createRequest(req); }
    public boolean updateRequest(OutboundRequestDto req, int userType, int userId) { return service.updateRequest(req, userType, userId); }
    public boolean approveRequest(int outboundId, int adminId) { return service.approveRequest(outboundId, adminId); }
    public boolean cancelRequest(int outboundId) { return service.cancelRequest(outboundId); }
    public OutboundResponseDto getOutboundDetail(int outboundId, int userType, int userId) { return service.getOutboundDetail(outboundId, userType, userId); }
    public List<OutboundResponseDto> getPendingList(int userType, int userId) {
        return service.getPendingList(userType, userId);
    }
    public List<OutboundResponseDto> getAllRequests(int userType, int userId) { return service.getAllRequests(userType, userId); }
    public String getUserRole(int userId) { return service.getUserRole(userId); }
}
