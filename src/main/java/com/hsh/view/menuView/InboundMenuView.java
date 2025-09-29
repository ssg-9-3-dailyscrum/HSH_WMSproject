

import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;
import main.java.com.hsh.service.InboundService;
import main.java.com.hsh.service.serviceImpl.InboundServiceImpl;

import java.util.List;

public class InboundController {

    // 싱글톤 인스턴스
    private static InboundController instance;

    private final InboundService service;

    // private 생성자
    private InboundController() {
        this.service = InboundServiceImpl.getInstance();
    }

    // 싱글톤 getInstance()
    public static synchronized InboundController getInstance() {
        if (instance == null) {
            instance = new InboundController();
        }
        return instance;
    }

    public boolean createRequest(InboundRequestDto req) {
        return service.requestInbound(req);
    }

    public boolean approveRequest(int inboundId, int approverId) {
        return service.approveInbound(inboundId, approverId);
    }

    public boolean cancelRequest(int inboundId) {
        return service.cancelInbound(inboundId);
    }

    public boolean updateRequest(InboundRequestDto req) {
        return service.updateInboundByMember(req);
    }

    public List<InboundResponseDto> getInboundList(int userType, int userId) {
        return service.getInboundList(userType, userId);
    }

    public InboundResponseDto getInboundDetail(int inboundId, int userType, int userId) {
        return service.getInboundDetail(inboundId, userType, userId);
    }
}
