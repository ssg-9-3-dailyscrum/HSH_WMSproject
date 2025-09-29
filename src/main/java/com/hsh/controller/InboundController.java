package main.java.com.hsh.controller;

import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;
import main.java.com.hsh.domain.vo.InboundVo;
import main.java.com.hsh.service.InboundService;
import main.java.com.hsh.service.seviceImpl.InboundServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class InboundController {
    private final InboundService service = new InboundServiceImpl();

    public boolean createRequest(InboundRequestDto req) {
        return service.createRequest(req);
    }

    public boolean approveRequest(int inboundId, int approverId) {
        return service.approveRequest(inboundId, approverId);
    }

    public boolean cancelRequest(int inboundId) {
        return service.cancelRequest(inboundId);
    }

    // userType: 1=총관리자, 2=창고관리자, 3=회원
    public List<InboundResponseDto> getInboundList(int userType, int userId) {
        return service.getInboundList(userType, userId);
    }

    public InboundResponseDto getInboundDetail(int inboundId, int userType, int userId) {
        return service.getInboundDetail(inboundId, userType, userId);
    }
}