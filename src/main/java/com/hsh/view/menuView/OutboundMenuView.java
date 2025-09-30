package main.java.com.hsh.view.menuView;

import main.java.com.hsh.controller.OutboundController;
import main.java.com.hsh.domain.dto.request.OutboundRequestDto;
import main.java.com.hsh.domain.dto.response.OutboundResponseDto;
import main.java.com.hsh.domain.vo.UserVo;
import main.java.com.hsh.session.AdminSession;
import main.java.com.hsh.session.UserSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class OutboundMenuView {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final OutboundController controller = new OutboundController();

    public void outboundMenu() {
        AdminSession adminSession = AdminSession.getInstance();
        UserVo currentUser = UserSession.getInstance().getCurrentLoggedInUser();

        int userType = 0; // 1=관리자, 2=회원
        Integer userId = null;

        if (adminSession.getAdminId() != null) {
            userId = adminSession.getAdminId().intValue();
            userType = 1;
        } else if (currentUser != null) {
            userId = currentUser.getMemberId();
            userType = 2;
        } else {
            System.out.println(":: 로그인 필요 ::");
            return;
        }

        boolean flag = false;

        while (!flag) {
            try {
                if (userType == 1) superAdminMenu();
                else memberMenu();

                int choice = menuChoice();

                switch (choice) {
                    case 1:
                        if (userType == 1) approveOutbound();
                        else createOutboundRequest(userId);
                        break;
                    case 2:
                        viewOutboundDetail(userType, userId);
                        break;
                    case 3:
                        listPendingOutbound(userType, userId);
                        break;
                    case 4:
                        if (userType == 1) listAllOutbound(userId);
                        else System.out.println("잘못된 입력");
                        break;
                    case 0:
                        flag = true;
                        break;
                    default:
                        System.out.println("잘못된 입력");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private int menuChoice() throws IOException {
        System.out.print("메뉴 선택: ");
        return Integer.parseInt(reader.readLine().trim());
    }

    private void superAdminMenu() {
        System.out.println("===== 관리자 출고 메뉴 =====");
        System.out.println("1. 출고 승인");
        System.out.println("2. 출고 상세 조회");
        System.out.println("3. 대기 출고 조회");
        System.out.println("4. 전체 출고 조회");
        System.out.println("0. 돌아가기");
    }

    private void memberMenu() {
        System.out.println("===== 회원 출고 메뉴 =====");
        System.out.println("1. 출고 요청 생성");
        System.out.println("2. 출고 상세 조회");
        System.out.println("3. 대기 출고 조회");
        System.out.println("0. 돌아가기");
    }

    private void createOutboundRequest(int memberId) {
        try {
            OutboundRequestDto req = new OutboundRequestDto();
            req.memberId = memberId;

            System.out.print("창고 ID 입력: ");
            req.inventoryId = Integer.parseInt(reader.readLine().trim());

            while (true) {
                System.out.print("상품 ID 입력 (0 입력 시 종료): ");
                int productId = Integer.parseInt(reader.readLine().trim());
                if (productId == 0) break;

                System.out.print("수량 입력: ");
                int quantity = Integer.parseInt(reader.readLine().trim());

                req.products.add(new OutboundRequestDto.ProductQuantity(productId, quantity));
            }

            if (req.products.isEmpty()) {
                System.out.println(":: 최소 1개의 상품을 입력해야 합니다. ::");
                return;
            }

            boolean success = controller.createRequest(req);
            System.out.println(success ? ":: 출고 요청 생성 완료! ::" : ":: 출고 요청 실패 ::");

        } catch (IOException | NumberFormatException e) {
            System.out.println(":: 잘못된 입력입니다. ::");
        }
    }

    private void approveOutbound() throws IOException {
        AdminSession adminSession = AdminSession.getInstance();
        Integer adminId = adminSession.getAdminId();

        if (adminId == null) {
            System.out.println(":: 관리자 로그인 필요 ::");
            return;
        }

        System.out.print("승인할 출고 ID: ");
        int oid = Integer.parseInt(reader.readLine().trim());

        boolean result = controller.approveRequest(oid, adminId);
        System.out.println(result ? "승인 완료" : ":: 존재하지 않거나 대기 상태가 아닌 출고 ID입니다. ::");
    }

    private void viewOutboundDetail(int userType, int userId) throws IOException {
        System.out.print("출고 ID 입력: ");
        int oid = Integer.parseInt(reader.readLine().trim());

        OutboundResponseDto dto = controller.getOutboundDetail(oid, userType, userId);
        if (dto == null) {
            System.out.println("조회 불가");
            return;
        }

        System.out.println("출고 ID: " + dto.outboundId);
        System.out.println("회원명: " + dto.memberName);
        System.out.println("재고 ID: " + dto.inventoryId);
        System.out.println("상태: " + dto.outboundStatus);
        System.out.println("상품 내역:");
        if (dto.products != null) {
            dto.products.forEach(p ->
                    System.out.println("  상품ID:" + p.productId + " 수량:" + p.quantity + " 이름:" + p.productName));
        }
    }

    private void listPendingOutbound(int userType, int userId) {
        List<OutboundResponseDto> list = controller.getPendingList(userType, userId);

        System.out.println("===== 대기 출고 목록 =====");
        if (list == null || list.isEmpty()) {
            System.out.println(":: 대기 중인 출고 요청이 없습니다. ::");
            System.out.println("=========================");
            return;
        }

        for (OutboundResponseDto dto : list) {
            int totalQty = 0;
            if (dto.products != null) {
                for (OutboundRequestDto.ProductQuantity p : dto.products) {
                    totalQty += p.quantity;
                }
            }

            System.out.printf("ID:%d | 회원:%d | 창고:%d | 수량: %d%n",
                    dto.outboundId,
                    dto.memberId,
                    dto.inventoryId,
                    totalQty);
        }

        System.out.println("=========================");
    }

    private void listAllOutbound(int adminId) {
        List<OutboundResponseDto> list = controller.getAllRequests(1, adminId);

        if (list == null || list.isEmpty()) {
            System.out.println(":: 전체 출고 내역이 없습니다. ::");
            return;
        }

        System.out.println("===== 전체 출고 목록 =====");
        System.out.printf("%-5s | %-10s | %-6s | %-4s%n", "ID", "회원명", "상태", "수량");
        System.out.println("--------------------------------");

        for (OutboundResponseDto dto : list) {
            int totalQty = 0;
            if (dto.products != null) {
                for (OutboundRequestDto.ProductQuantity p : dto.products) {
                    totalQty += p.quantity;
                }
            }
            System.out.printf("%-5d | %-10s | %-6s | %-4d%n",
                    dto.outboundId,
                    dto.memberName,
                    dto.outboundStatus,
                    totalQty);
        }

        System.out.println("===========================");
    }

}
