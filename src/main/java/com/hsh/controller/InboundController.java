package main.java.com.hsh.controller;

import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;
import main.java.com.hsh.service.InboundService;
import main.java.com.hsh.service.serviceImpl.InboundServiceImpl;

import java.util.List;

public class InboundController {

    private static InboundController instance;
    private final InboundService service;

    private InboundController() {
        this.service = InboundServiceImpl.getInstance();
    }

    public static synchronized InboundController getInstance() {
        if (instance == null) {
            instance = new InboundController();
        }
        return instance;
    }

    // 입고 요청 생성
    public boolean createRequest(InboundRequestDto req) {
        return service.requestInbound(req);
    }

    // 입고 승인
    public boolean approveRequest(int inboundId, int approverId) {
        return service.approveInbound(inboundId, approverId);
    }

    public boolean cancelRequest(int inboundId, int memberId) {
        return service.cancelInboundByMember(inboundId, memberId); // 회원용
    }


    // 회원 전용 입고 수정
    public boolean updateRequest(InboundRequestDto req) {
        return service.updateInboundByMember(req);
    }

    // 입고 목록 조회
    public List<InboundResponseDto> getInboundList(int userType, int userId) {
        return service.getInboundList(userType, userId);
    }

    // 입고 상세 조회
    public InboundResponseDto getInboundDetail(int inboundId, int userType, int userId) {
        return service.getInboundDetail(inboundId, userType, userId);
    }
}
