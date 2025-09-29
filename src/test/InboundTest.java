package test;

import main.java.com.hsh.controller.InboundController;
import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class InboundTest {

    InboundController controller = InboundController.getInstance();
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public void start() throws IOException {
        while (true) {
            System.out.println("===== 입고 관리 CLI =====");
            System.out.println("1. 입고 요청 생성");
            System.out.println("2. 입고 승인");
            System.out.println("3. 입고 취소/수정");
            System.out.println("4. 입고 1건 상세 조회");
            System.out.println("5. 대기 입고 조회");
            System.out.println("6. 전체 입고 조회 (관리자 전용)");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            String line = input.readLine();
            if (line == null) continue;
            switch (line.trim()) {
                case "1" -> createInbound();
                case "2" -> approveInbound();
                case "3" -> cancelInbound();
                case "4" -> showDetail();
                case "5" -> showPendingList();
                case "6" -> showAllList();
                case "0" -> {
                    System.out.println("프로그램 종료");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void updateInbound() throws IOException {
        System.out.print("회원 ID: ");
        int memberId = Integer.parseInt(input.readLine().trim());
        System.out.print("입고 ID: ");
        int inboundId = Integer.parseInt(input.readLine().trim());
        System.out.print("상품 ID: ");
        int productId = Integer.parseInt(input.readLine().trim());
        System.out.print("수량: ");
        int quantity = Integer.parseInt(input.readLine().trim());

        // DTO 객체 생성
        InboundRequestDto req = new InboundRequestDto();
        req.memberId = memberId;
        req.inboundId = inboundId; // DTO에 inboundId 필드 추가 필요
        req.productId = productId;
        req.quantity = quantity;

        boolean success = controller.updateRequest(req);
        System.out.println(success ? "입고 수정: 성공" : "입고 수정: 실패");
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
        System.out.print("관리자 ID: ");
        int adminId = Integer.parseInt(input.readLine().trim());

        System.out.print("입고 ID: ");
        int inboundId = Integer.parseInt(input.readLine().trim());

        boolean success = controller.approveRequest(inboundId, adminId);
        System.out.println(success ? "입고 승인: 성공" : "입고 승인: 실패");
    }

    // InboundTest.java (CLI)
    private void cancelInbound() throws IOException {
        System.out.print("회원 ID: ");  // 먼저 회원 ID 입력
        int memberId = Integer.parseInt(input.readLine().trim());

        System.out.print("입고 ID: ");
        int inboundId = Integer.parseInt(input.readLine().trim());

        System.out.print("1=수정, 2=삭제 선택: ");
        int choice = Integer.parseInt(input.readLine().trim());

        boolean success = false;
        switch (choice) {
            case 1 -> {  // 수정
                System.out.print("창고 ID: ");
                int warehouseId = Integer.parseInt(input.readLine().trim());
                System.out.print("섹션 ID: ");
                int sectionId = Integer.parseInt(input.readLine().trim());
                System.out.print("상품 ID: ");
                int productId = Integer.parseInt(input.readLine().trim());
                System.out.print("수량: ");
                int quantity = Integer.parseInt(input.readLine().trim());

                try {
                    // 수정 요청 DTO 생성
                    InboundRequestDto dto = new InboundRequestDto();
                    dto.memberId = memberId;
                    dto.inboundId = inboundId;
                    dto.warehouseId = warehouseId;
                    dto.sectionId = sectionId;
                    dto.productId = productId;
                    dto.quantity = quantity;




                    success = controller.updateRequest(dto);

                    if (success) {
                        System.out.println("입고 수정: 성공");
                    } else {
                        System.out.println("입고 수정: 실패 (대기 상태만 수정 가능 또는 FK 오류)");
                    }
                } catch (Exception e) {
                    System.out.println("입고 수정 실패: " + e.getMessage());
                }
            }
            case 2 -> {  // 삭제
                try {
                    success = controller.cancelRequest(inboundId);
                    if (success) {
                        System.out.println("입고 삭제: 성공");
                    } else {
                        System.out.println("입고 삭제: 실패 (대기 상태만 삭제 가능)");
                    }
                } catch (Exception e) {
                    System.out.println("입고 삭제 실패: " + e.getMessage());
                }
            }
            default -> System.out.println("잘못된 선택입니다.");
        }
    }






    private void showDetail() throws IOException {
        System.out.print("입고 ID: ");
        int inboundId = Integer.parseInt(input.readLine().trim());

        // 사용자 타입 입력 제거, 내부에서 회원으로 고정 (3)
        int userType = 3;

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

    private void showPendingList() throws IOException {
        // 관리자 타입 입력 제거, 내부에서 관리자(창고 관리자=2)로 고정
        int userType = 2;

        System.out.print("사용자 ID: ");
        int userId = Integer.parseInt(input.readLine().trim());

        List<InboundResponseDto> list = controller.getInboundList(userType, userId);
        System.out.println("----- 대기 입고 리스트 -----");
        for (InboundResponseDto dto : list) {
            if (!"대기".equals(dto.status)) continue; // 대기만 필터링
            System.out.printf("ID:%d | 상태:%s | 회원ID:%d | 창고ID:%d | 섹션ID:%d | 날짜:%s\n",
                    dto.inboundId, dto.status, dto.memberId, dto.warehouseId, dto.sectionId,
                    dto.inboundDate == null ? "null" : dto.inboundDate.toString());
        }
    }

    private void showAllList() throws IOException {
        // 전체 조회 (관리자 전용), 내부에서 관리자 타입 고정
        int userType = 2;

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
