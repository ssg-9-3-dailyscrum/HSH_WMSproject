package test;

import main.java.com.hsh.controller.OutboundController;
import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.request.OutboundDetailDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;
import main.java.com.hsh.domain.dto.response.OutboundDetailResponseDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OutboundTest {

    private final OutboundController controller = new OutboundController();
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public void start() throws IOException {
        while (true) {
            System.out.println("===== 출고 관리 CLI =====");
            System.out.println("1. 출고 요청 생성");
            System.out.println("2. 출고 승인");
            System.out.println("3. 출고 요청 수정");
            System.out.println("4. 출고 상세 조회");
            System.out.println("5. 대기 출고 조회");
            System.out.println("6. 전체 출고 조회 (관리자 전용)");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            String line = input.readLine();
            if (line == null) continue;

            switch (line.trim()) {
                case "1" -> createOutbound();
                case "2" -> approveOutbound();
                case "3" -> updateOutbound();
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

    private void createOutbound() throws IOException {
        OutboundRequestDto req = new OutboundRequestDto();

        System.out.print("회원 ID: ");
        req.memberId = Integer.parseInt(input.readLine().trim());

        System.out.print("재고 ID: ");
        req.inventoryId = Integer.parseInt(input.readLine().trim());

        List<OutboundDetailDto> products = new ArrayList<>();
        while (true) {
            System.out.print("상품 ID (0 입력 시 종료): ");
            int productId = Integer.parseInt(input.readLine().trim());
            if (productId == 0) break;

            System.out.print("수량: ");
            int quantity = Integer.parseInt(input.readLine().trim());

            OutboundDetailDto detail = new OutboundDetailDto();
            detail.productId = productId;
            detail.quantity = quantity;
            products.add(detail);
        }
        req.products = products;

        boolean success = controller.createRequest(req);
        System.out.println(success ? "출고 요청 생성: 성공" : "출고 요청 생성: 실패");
    }

    private void approveOutbound() throws IOException {
        System.out.print("관리자 ID: ");
        int adminId = Integer.parseInt(input.readLine().trim());

        System.out.print("출고 ID: ");
        int outboundId = Integer.parseInt(input.readLine().trim());

        boolean success = controller.approveRequest(outboundId, adminId);
        System.out.println(success ? "출고 승인: 성공" : "출고 승인: 실패");
    }

    private void updateOutbound() throws IOException {
        System.out.print("회원 ID: ");
        int memberId = Integer.parseInt(input.readLine().trim());

        System.out.print("출고 ID: ");
        int outboundId = Integer.parseInt(input.readLine().trim());

        System.out.print("재고 ID: ");
        int inventoryId = Integer.parseInt(input.readLine().trim());

        List<OutboundDetailDto> products = new ArrayList<>();
        System.out.print("상품 ID: ");
        int productId = Integer.parseInt(input.readLine().trim());
        System.out.print("수량: ");
        int quantity = Integer.parseInt(input.readLine().trim());

        OutboundDetailDto detail = new OutboundDetailDto();
        detail.productId = productId;
        detail.quantity = quantity;
        products.add(detail);

        OutboundRequestDto req = new OutboundRequestDto();
        req.memberId = memberId;
        req.outboundId = outboundId;
        req.inventoryId = inventoryId;
        req.products = products;

        boolean success = controller.updateRequest(req);
        System.out.println(success ? "출고 요청 수정: 성공" : "출고 요청 수정: 실패");
    }

    private void showDetail() throws IOException {
        System.out.print("출고 ID: ");
        int outboundId = Integer.parseInt(input.readLine().trim());

        System.out.print("사용자 ID: ");
        int userId = Integer.parseInt(input.readLine().trim());

        OutboundResponseDto dto = controller.getOutboundDetail(outboundId, userId);
        if (dto == null) {
            System.out.println("상세 조회 실패: 권한이 없거나 출고가 존재하지 않습니다.");
            return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("----- 출고 상세 -----");
        System.out.println("출고 ID: " + dto.outboundId);
        System.out.println("회원 ID: " + dto.memberId);
        System.out.println("회원 이름: " + dto.memberName);
        System.out.println("재고 ID: " + dto.inventoryId);
        System.out.println("출고일: " + (dto.outboundDate == null ? "null" : dto.outboundDate.format(fmt)));
        System.out.println("상태: " + dto.status);
        if (dto.details != null) {
            System.out.println("상품 내역:");
            for (OutboundDetailResponseDto d : dto.details) {
                System.out.printf(" - 상품ID:%d | 상품명:%s | 수량:%d\n", d.productId, d.productName, d.quantity);
            }
        }
    }

    private void showPendingList() throws IOException {
        System.out.print("사용자 ID: ");
        int userId = Integer.parseInt(input.readLine().trim());

        List<OutboundResponseDto> list = controller.getPendingList(userId);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("----- 대기 출고 리스트 -----");
        for (OutboundResponseDto dto : list) {
            System.out.printf("출고ID:%d | 회원ID:%d | 이름:%s | 재고ID:%d | 상태:%s | 날짜:%s\n",
                    dto.outboundId, dto.memberId, dto.memberName, dto.inventoryId,
                    dto.status, dto.outboundDate == null ? "null" : dto.outboundDate.format(fmt));
        }
    }

    private void showAllList() throws IOException {
        System.out.print("관리자 ID: ");
        int adminId = Integer.parseInt(input.readLine().trim());

        List<OutboundResponseDto> list = controller.getAllRequests(adminId);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("===== 전체 출고 리스트 =====");

        for (OutboundResponseDto dto : list) {
            System.out.println("───────────────────────────────");
            System.out.printf("출고 ID: %d | 회원 ID: %d | 이름: %s\n",
                    dto.outboundId, dto.memberId, dto.memberName);
            System.out.printf("재고 ID: %d | 상태: %s | 날짜: %s\n",
                    dto.inventoryId, dto.status, dto.outboundDate == null ? "null" : dto.outboundDate.format(fmt));

            if (dto.details != null && !dto.details.isEmpty()) {
                System.out.println("상품 내역:");
                for (OutboundDetailResponseDto d : dto.details) {
                    System.out.printf("   - 상품 ID: %d | 상품명: %s | 수량: %d\n",
                            d.productId, d.productName, d.quantity);
                }
            } else {
                System.out.println("상품 내역: 없음");
            }
        }
        System.out.println("───────────────────────────────");
    }


    public static void main(String[] args) {
        OutboundTest cli = new OutboundTest();
        try {
            cli.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
