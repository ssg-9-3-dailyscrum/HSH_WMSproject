package test;

import main.java.com.hsh.controller.InboundController;
import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;
import java.io.IOException;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class InboundTest {

    private final InboundController controller = new InboundController();
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public void start() throws IOException {
        while (true) {
            System.out.println("===== 입고 관리 CLI =====");
            System.out.println("1. 입고 요청 생성");
            System.out.println("2. 입고 승인");
            System.out.println("3. 입고 취소/삭제");
            System.out.println("4. 입고 상세 조회");
            System.out.println("5. 전체 입고 조회");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            String line = input.readLine();
            if (line == null) continue;
            switch (line.trim()) {
                case "1" -> createInbound();
                case "2" -> approveInbound();
                case "3" -> cancelInbound();
                case "4" -> showDetail();
                case "5" -> showAll();
                case "0" -> {
                    System.out.println("프로그램 종료");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void createInbound() throws IOException {
        InboundRequestDto req = new InboundRequestDto();
        System.out.print("회원 ID: ");
        req.memberId = Integer.parseInt(input.readLine().trim());
        System.out.print("섹션 ID: ");
        req.sectionId = Integer.parseInt(input.readLine().trim());
        System.out.print("창고 ID: ");
        req.warehouseId = Integer.parseInt(input.readLine().trim());
        System.out.print("상품 ID: ");
        req.productId = Integer.parseInt(input.readLine().trim());
        System.out.print("수량: ");
        req.quantity = Integer.parseInt(input.readLine().trim());

        boolean success = controller.createRequest(req);
        System.out.println(success ? "입고 요청 생성: 성공" : "입고 요청 생성: 실패");
    }

    private void approveInbound() throws IOException {
        System.out.print("입고 ID: ");
        int inboundId = Integer.parseInt(input.readLine().trim());
        System.out.print("관리자 ID: ");
        int adminId = Integer.parseInt(input.readLine().trim());

        boolean success = controller.approveRequest(inboundId, adminId);
        System.out.println(success ? "입고 승인: 성공" : "입고 승인: 실패");
    }

    private void cancelInbound() throws IOException {
        System.out.print("입고 ID: ");
        int inboundId = Integer.parseInt(input.readLine().trim());
        boolean success = controller.cancelRequest(inboundId);
        System.out.println(success ? "입고 취소/삭제: 성공" : "입고 취소/삭제: 실패");
    }

    private void showDetail() throws IOException {
        System.out.print("입고 ID: ");
        int inboundId = Integer.parseInt(input.readLine().trim());
        System.out.print("사용자 타입 (1=총관리자, 2=창고관리자, 3=회원): ");
        int userType = Integer.parseInt(input.readLine().trim());
        System.out.print("사용자 ID: ");
        int userId = Integer.parseInt(input.readLine().trim());

        InboundResponseDto detail = controller.getInboundDetail(inboundId, userType, userId);
        if (detail == null) {
            System.out.println("상세 조회 실패: 권한이 없거나 입고가 존재하지 않습니다.");
        } else {
            System.out.println("----- 입고 상세 -----");
            System.out.println("입고 ID: " + detail.inboundId);
            System.out.println("회원 ID: " + detail.memberId);
            System.out.println("창고 ID: " + detail.warehouseId);
            System.out.println("섹션 ID: " + detail.sectionId);
            System.out.println("상태: " + detail.status);
            System.out.println("입고일: " + detail.inboundDate);
        }
    }

    private void showAll() throws IOException {
        System.out.print("사용자 타입 (1=총관리자, 2=창고관리자, 3=회원): ");
        int userType = Integer.parseInt(input.readLine().trim());
        System.out.print("사용자 ID: ");
        int userId = Integer.parseInt(input.readLine().trim());

        List<InboundResponseDto> list = controller.getInboundList(userType, userId);
        System.out.println("----- 전체 입고 리스트 -----");
        for (InboundResponseDto dto : list) {
            System.out.printf("ID:%d | 상태:%s | 회원ID:%d | 창고ID:%d | 섹션ID:%d | 날짜:%s\n",
                    dto.inboundId, dto.status, dto.memberId, dto.warehouseId, dto.sectionId,
                    dto.inboundDate == null ? "null" : dto.inboundDate.toString());
        }
    }

    public static void main(String[] args) {
        InboundTest cli = new InboundTest();
        try {
            cli.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
