package test;

import main.java.com.hsh.controller.OutboundController;
import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
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
            System.out.println("6. 전체 출고 조회");
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

    // -------------------- 출고 요청 생성 --------------------
    private void createOutbound() throws IOException {
        OutboundRequestDto req = new OutboundRequestDto();
        req.products = new ArrayList<>();

        System.out.print("회원 ID: ");
        req.memberId = Integer.parseInt(input.readLine().trim());

        System.out.print("재고 ID: ");
        req.inventoryId = Integer.parseInt(input.readLine().trim());

        while (true) {
            System.out.print("상품 ID (0 입력 시 종료): ");
            int productId = Integer.parseInt(input.readLine().trim());
            if (productId == 0) break;

            System.out.print("수량: ");
            int quantity = Integer.parseInt(input.readLine().trim());

            OutboundRequestDto.ProductQuantity pq = new OutboundRequestDto.ProductQuantity(productId, quantity);
            req.products.add(pq);
        }

        boolean success = controller.createRequest(req);
        System.out.println(success ? "출고 요청 생성: 성공" : "출고 요청 생성: 실패");
    }

    // -------------------- 출고 승인 --------------------
    private void approveOutbound() throws IOException {
        System.out.print("관리자 ID: ");
        int adminId = Integer.parseInt(input.readLine().trim());

        String role = controller.getUserRole(adminId);
        if (!"창고관리자".equals(role) && !"총관리자".equals(role)) {
            System.out.println("출고 승인 권한이 없습니다.");
            return;
        }

        System.out.print("출고ID: ");
        int outboundId = Integer.parseInt(input.readLine().trim());

        boolean success = controller.approveRequest(outboundId, adminId);
        System.out.println(success ? "출고 승인: 성공" : "출고 승인: 실패 또는 존재하지 않음");
    }

    // -------------------- 출고 요청 수정 --------------------
    private void updateOutbound() throws IOException {
        System.out.print("출고ID: ");
        int outboundId = Integer.parseInt(input.readLine().trim());

        System.out.print("회원 ID: ");
        int memberId = Integer.parseInt(input.readLine().trim());

        System.out.print("재고 ID: ");
        int inventoryId = Integer.parseInt(input.readLine().trim());

        String role = controller.getUserRole(memberId);
        if (role == null) {
            System.out.println("사용자 정보 없음");
            return;
        }

        OutboundResponseDto dto = controller.getOutboundDetail(outboundId, 0, memberId);
        if (dto == null) {
            System.out.println("출고 요청 수정 실패: 존재하지 않음");
            return;
        }

        // 회원은 본인 출고만 수정 가능
        if ("회원".equals(role) && dto.memberId != memberId) {
            System.out.println("출고 요청 수정 실패: 권한 없음");
            return;
        }

        OutboundRequestDto req = new OutboundRequestDto();
        req.outboundId = outboundId;
        req.memberId = memberId;
        req.inventoryId = inventoryId;
        req.products = new ArrayList<>();

        while (true) {
            System.out.print("수정할 상품 ID (0 입력 시 종료): ");
            int productId = Integer.parseInt(input.readLine().trim());
            if (productId == 0) break;

            System.out.print("수량: ");
            int quantity = Integer.parseInt(input.readLine().trim());

            OutboundRequestDto.ProductQuantity pq = new OutboundRequestDto.ProductQuantity(productId, quantity);
            req.products.add(pq);
        }

        boolean success = controller.updateRequest(req, 0, memberId);
        System.out.println(success ? "출고 요청 수정: 성공" : "출고 요청 수정 실패: 존재하지 않거나 승인된 건");
    }

    // -------------------- 출고 상세 조회 --------------------
    // -------------------- 출고 상세 조회 --------------------
    private void showDetail() throws IOException {
        System.out.println("사용자 타입 선택 (1=회원, 2=창고 관리자, 3=총 관리자): ");
        int userType = Integer.parseInt(input.readLine().trim());

        int userId;
        if (userType == 1) {
            System.out.print("회원 ID 입력: ");
            userId = Integer.parseInt(input.readLine().trim());
        } else if (userType == 2 || userType == 3) {
            System.out.print("관리자 ID 입력: ");
            userId = Integer.parseInt(input.readLine().trim());
        } else {
            System.out.println("잘못된 사용자 타입입니다.");
            return;
        }

        System.out.print("출고ID: ");
        int outboundId = Integer.parseInt(input.readLine().trim());

        OutboundResponseDto dto = controller.getOutboundDetail(outboundId, userType, userId);
        if (dto == null) {
            System.out.println("상세 조회 실패: 권한 없음 또는 존재하지 않음");
            return;
        }

        System.out.println("----- 출고 상세 -----");
        System.out.println("출고ID: " + dto.outboundId);
        System.out.println("회원ID: " + dto.memberId + " | 이름: " + dto.memberName);
        System.out.println("재고ID: " + dto.inventoryId);
        System.out.println("상태: " + dto.outboundStatus);
        System.out.println("출고일: " + dto.outboundDate);

        // 상품 정보 출력
        if (dto.products != null && !dto.products.isEmpty()) {
            System.out.println("상품 목록:");
            for (OutboundRequestDto.ProductQuantity pq : dto.products) {
                System.out.printf("상품ID:%d | 이름:%s | 수량:%d\n", pq.productId, pq.productName, pq.quantity);
            }
        } else {
            System.out.println("상품 정보가 없습니다.");
        }

        // 회원 권한 체크
        if (userType == 1 && dto.memberId != userId) {
            System.out.println("권한 없음: 본인 출고만 조회 가능");
        }
    }

    // -------------------- 대기 출고 조회 --------------------
    private void showPendingList() throws IOException {
        System.out.print("사용자 타입 선택 (1=회원, 2=창고 관리자, 3=총 관리자): ");
        int userType = Integer.parseInt(input.readLine().trim());

        if (userType == 1) {
            System.out.print("회원 ID 입력: ");
        } else {
            System.out.print("관리자 ID 입력: ");
        }
        int userId = Integer.parseInt(input.readLine().trim());

        List<OutboundResponseDto> list = controller.getPendingList(userType, userId);

        System.out.println("----- 대기 출고 리스트 -----");
        for (OutboundResponseDto dto : list) {
            if (userType == 1 && dto.memberId != userId) continue; // 회원은 본인만
            System.out.printf("출고ID:%d | 회원:%s | 상태:%s | 날짜:%s\n",
                    dto.outboundId, dto.memberName, dto.outboundStatus, dto.outboundDate);
        }
    }


    // -------------------- 전체 출고 조회 --------------------
    private void showAllList() throws IOException {
        System.out.print("사용자 타입 선택 (1=회원, 2=관리자): ");
        int userType = Integer.parseInt(input.readLine().trim());

        if (userType == 1) {
            System.out.println("전체 출고 조회는 관리자만 가능합니다.");
            return;
        }

        System.out.print("관리자 ID 입력: ");
        int adminId = Integer.parseInt(input.readLine().trim());

        List<OutboundResponseDto> list = controller.getAllRequests(adminId);

        System.out.println("----- 전체 출고 리스트 -----");
        for (OutboundResponseDto dto : list) {
            System.out.printf("출고ID:%d | 회원:%s | 상태:%s | 날짜:%s\n",
                    dto.outboundId, dto.memberName, dto.outboundStatus,
                    dto.outboundDate == null ? "null" : dto.outboundDate.toString());
        }
    }


    // -------------------- main --------------------
    public static void main(String[] args) {
        OutboundTest cli = new OutboundTest();
        try {
            cli.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
