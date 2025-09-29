package main.java.com.hsh.controller;

import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;
import main.java.com.hsh.service.OutboundService;

import java.util.List;

public class OutboundController {

    private final OutboundService outboundService;

    public OutboundController() {
        this.outboundService = new main.java.com.hsh.service.seviceImpl.OutboundServiceImpl();
    }

    // 출고 요청 생성
    public boolean createRequest(OutboundRequestDto req) {
        return outboundService.createRequest(req);
    }

    // 출고 요청 수정
    public boolean updateRequest(OutboundRequestDto req) {
        return outboundService.updateRequest(req);
    }

    // 출고 승인 (관리자)
    public boolean approveRequest(int outboundId, int adminId) {
        return outboundService.approveRequest(outboundId, adminId);
    }

    // 특정 출고 상세 조회
    public OutboundResponseDto getOutboundDetail(int outboundId, int userId) {
        return outboundService.getOutboundDetail(outboundId, userId);
    }

    // 대기 출고 리스트 조회
    public List<OutboundResponseDto> getPendingList(int userId) {
        return outboundService.getPendingList(userId);
    }

    // 전체 출고 조회 (관리자 전용)
    public List<OutboundResponseDto> getAllRequests(int adminId) {
        return outboundService.getAllRequests(adminId);
    }

    // 출고 요청 취소
    public boolean cancelRequest(int outboundId) {
        return outboundService.cancelRequest(outboundId);
    }
}
