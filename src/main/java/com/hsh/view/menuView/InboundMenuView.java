package main.java.com.hsh.view.menuView;

import main.java.com.hsh.controller.InboundController;
import main.java.com.hsh.domain.dto.request.InboundRequestDto;
import main.java.com.hsh.domain.dto.response.InboundDetailDto;
import main.java.com.hsh.domain.dto.response.InboundResponseDto;
import main.java.com.hsh.domain.vo.UserVo;
import main.java.com.hsh.session.AdminSession;
import main.java.com.hsh.session.UserSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class InboundMenuView {

    // 싱글톤으로 수정
    private final InboundController controller = InboundController.getInstance();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void inboundMenu() {
        AdminSession adminSession = AdminSession.getInstance();
        UserVo currentUser = UserSession.getInstance().getCurrentLoggedInUser();

        String userRole = null;
        Integer userId = null;
        int userType = 0; // 1=관리자, 2=회원

        if (adminSession.getAdminId() != null) {
            userRole = adminSession.getRole();
            userId = adminSession.getAdminId().intValue();
            userType = 1; // 관리자
        } else if (currentUser != null) {
            userRole = "회원";
            userId = currentUser.getMemberId();
            userType = 2; // 회원
        }

        boolean flag = false;

        while (!flag) {
            try {
                if (userRole.equals("총관리자") || userRole.equals("창고관리자")) {
                    superAdminMenu();
                    int choice = menuChoice();
                    switch (choice) {
                        case 1 -> approveInbound();
                        case 2 -> { // 1건 상세 조회
                            System.out.print("조회할 입고 ID 입력: ");
                            int inboundId = Integer.parseInt(reader.readLine().trim());
                            viewInboundDetail(inboundId, userType, userId);
                        }
                        case 3 -> listPendingInbound(userId);   // 대기 입고 조회
                        case 4 -> listAllInbound(userId);       // 전체 입고 조회
                        case 5 -> flag = true;
                        default -> System.out.println(":: 잘못된 입력입니다. ::");
                    }
                } else if (userRole.equals("회원")) {
                    memberMenu();
                    int choice = menuChoice();
                    switch (choice) {
                        case 1 -> createInboundRequest(userId);
                        case 2 -> updateOrCancelInbound(userId);
                        case 3 -> { // 1건 상세 조회
                            System.out.print("조회할 입고 ID 입력: ");
                            int inboundId = Integer.parseInt(reader.readLine().trim());
                            viewInboundDetail(inboundId, userType, userId);
                        }
                        case 4 -> listPendingInbound(userId);   // 본인 대기 입고 조회
                        case 5 -> flag = true;
                        default -> System.out.println(":: 잘못된 입력입니다. ::");
                    }
                } else {
                    System.out.println(":: 권한이 없습니다. ::");
                    flag = true;
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println(":: 잘못된 입력입니다. ::");
            }
        }
    }



    private int menuChoice() {
        System.out.print("메뉴를 선택하세요: ");
        try {
            return Integer.parseInt(reader.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            return -1;
        }
    }

    private void superAdminMenu() {
        System.out.println("===== 입고 관리 (관리자) =====");
        System.out.println("1. 입고 요청 승인");
        System.out.println("2. 입고 1건 상세 조회");
        System.out.println("3. 승인 전 대기상태 입고 요청 조회 (전체)");
        System.out.println("4. 전체 입고 현황 조회");
        System.out.println("5. 돌아가기");
        System.out.println("=============================");
    }

    private void memberMenu() {
        System.out.println("===== 입고 관리 (회원) =====");
        System.out.println("1. 입고 요청 생성");
        System.out.println("2. 입고 요청 취소/수정");
        System.out.println("3. 입고 1건 상세 조회");
        System.out.println("4. 승인 전 대기상태 입고 요청 조회 (본인것만)");
        System.out.println("5. 돌아가기");
        System.out.println("=============================");
    }

    private void createInboundRequest(int memberId) {
        try {
            InboundRequestDto req = new InboundRequestDto();
            req.memberId = memberId;

            System.out.print("섹션 ID 입력: ");
            req.sectionId = Integer.parseInt(reader.readLine().trim());
            System.out.print("창고 ID 입력: ");
            req.warehouseId = Integer.parseInt(reader.readLine().trim());
            System.out.print("상품 ID 입력: ");
            req.productId = Integer.parseInt(reader.readLine().trim());
            System.out.print("수량 입력: ");
            req.quantity = Integer.parseInt(reader.readLine().trim());

            boolean success = controller.createRequest(req);
            System.out.println(success ? ":: 입고 요청 생성 완료! ::" : ":: 입고 요청 실패 ::" );
        } catch (IOException | NumberFormatException e) {
            System.out.println(":: 잘못된 입력입니다. ::");
        }
    }

    private void approveInbound() {
        try {
            System.out.print("승인할 입고 ID 입력: ");
            int inboundId = Integer.parseInt(reader.readLine().trim());
            // 관리자 ID는 테스트용으로 1
            boolean success = controller.approveRequest(inboundId, 1);
            System.out.println(success ? ":: 입고 승인 완료! ::" : ":: 입고 승인 실패 ::" );
        } catch (IOException | NumberFormatException e) {
            System.out.println(":: 잘못된 입력입니다. ::");
        }
    }

    // InboundMenuView.java 수정 부분
    private void updateOrCancelInbound(int memberId) {
        try {
            // 입고 ID를 인자로 받도록 변경 (Scanner 제거)
            System.out.println("수정/취소할 입고 ID 입력: ");
            int inboundId = Integer.parseInt(reader.readLine().trim());

            System.out.println("1. 수정  2. 취소");
            int choice = Integer.parseInt(reader.readLine().trim());

            if (choice == 1) {
                // 수정
                InboundRequestDto req = new InboundRequestDto();
                req.inboundId = inboundId;

                System.out.println("상품 ID 입력: ");
                req.productId = Integer.parseInt(reader.readLine().trim());
                System.out.println("수량 입력: ");
                req.quantity = Integer.parseInt(reader.readLine().trim());
                req.memberId = memberId;

                boolean result = controller.updateRequest(req);
                System.out.println(result ? ":: 입고 수정 완료 ::" : ":: 입고 수정 실패 ::");

            } else if (choice == 2) {
                // 취소
                boolean result = controller.cancelRequest(inboundId, memberId);
                System.out.println(result ? ":: 입고 취소 완료 ::" : ":: 입고 취소 실패 ::");
            } else {
                System.out.println("잘못된 선택입니다.");
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println(":: 잘못된 입력입니다. ::");
        }
    }



    private void viewInboundDetail(int inboundId, int userType, int userId) {
        try {
            InboundResponseDto dto = controller.getInboundDetail(inboundId, userType, userId);

            if (dto == null) {
                System.out.println(":: 조회 권한이 없습니다. (회원은 본인 입고만 조회 가능) ::");
                return;
            }

            System.out.println("===== 입고 상세 =====");
            System.out.println("입고 ID: " + dto.inboundId);
            System.out.println("회원 ID: " + dto.memberId);
            System.out.println("섹션 ID: " + dto.sectionId);
            System.out.println("창고 ID: " + dto.warehouseId);
            System.out.println("상태: " + dto.status);
            System.out.println("입고일: " + dto.inboundDate);
            System.out.println("총수량: " + dto.quantity);

            System.out.println("----- 상품 상세 -----");
            if (dto.details != null && !dto.details.isEmpty()) {
                for (InboundDetailDto d : dto.details) {
                    System.out.println("상품ID: " + d.productId + ", 수량: " + d.quantity);
                }
            } else {
                System.out.println("등록된 상품이 없습니다.");
            }
            System.out.println("===================");
        } catch (NumberFormatException e) {
            System.out.println(":: 잘못된 입력입니다. ::");
        }
    }




    private void listPendingInbound(int userId) {
        List<InboundResponseDto> list = controller.getInboundList(1, userId);
        System.out.println("===== 대기 입고 목록 =====");
        for (InboundResponseDto dto : list) {
            if ("대기".equals(dto.status)) {
                System.out.printf("ID:%d | 회원:%d | 창고:%d | 섹션:%d | 수량: %d%n",
                        dto.inboundId, dto.memberId, dto.warehouseId, dto.sectionId, dto.quantity); // 0 -> dto.quantity
            }
        }
        System.out.println("=========================");
    }

    private void listAllInbound(int userId) {
        List<InboundResponseDto> list = controller.getInboundList(1, userId);
        System.out.println("===== 전체 입고 목록 =====");
        for (InboundResponseDto dto : list) {
            System.out.printf("ID:%d | 회원:%d | 창고:%d | 섹션:%d | 상태:%s | 수량: %d%n",
                    dto.inboundId, dto.memberId, dto.warehouseId, dto.sectionId, dto.status, dto.quantity); // 수량 표시
        }
        System.out.println("=========================");
    }
}
