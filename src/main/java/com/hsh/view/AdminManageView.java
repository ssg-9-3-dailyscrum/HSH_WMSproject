package main.java.com.hsh.view;

import main.java.com.hsh.controller.AdminLoginController;
import main.java.com.hsh.domain.vo.AdminVo;
import main.java.com.hsh.session.AdminSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminManageView {

    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private final AdminLoginController adminController = AdminLoginController.getInstance();
    private final AdminVo admin; // 현재 로그인된 관리자

    public AdminManageView(AdminVo admin) {
        this.admin = admin;
        // 세션 저장
        AdminSession.getInstance().login(admin.getAdminId(), admin.getRole(), admin.getAdminName());
        startManageMenu();
    }

    private void startManageMenu() {
        boolean running = true;

        while (running) {
            try {
                int choice = displaySubMenu();

                switch (choice) {
                    case 1 -> viewMyInfo();
                    case 2 -> updateMyInfo();
                    case 3 -> deleteMyAccount();
                    case 4 -> {                      // 로그아웃
                        adminController.logout();
                        running = false;
                        System.out.println("로그아웃 되었습니다.");
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }

            } catch (IOException | NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            }
        }
    }

    // 메뉴 출력
    private int displaySubMenu() throws IOException {
        System.out.println("\n=================================================");
        System.out.println("              총 관리자 내 계정 관리 메뉴              ");
        System.out.println("=================================================");
        System.out.println("1. 정보 조회");
        System.out.println("2. 정보 수정");
        System.out.println("3. 계정 삭제(비활성화)");
        System.out.println("4. 뒤로가기");
        System.out.println("-------------------------------------------------");
        System.out.print("메뉴 선택: ");
        return Integer.parseInt(input.readLine().trim());
    }

    // R: 정보 조회
    private void viewMyInfo() {
        AdminVo info = adminController.getAdminById(admin.getAdminId());
        if (info != null) {
            System.out.println("==================== 내 정보 조회 ====================");
            System.out.println("ID: " + info.getAdminLoginId());
            System.out.println("이름: " + info.getAdminName());
            System.out.println("부서: " + info.getDepartment());
            System.out.println("권한: " + info.getRole());

            System.out.println("=====================================================");
        } else {
            System.out.println("정보를 불러올 수 없습니다.");
        }
    }

    // U: 정보 수정
    private void updateMyInfo() throws IOException {
        AdminVo update = adminController.getAdminById(admin.getAdminId());
        if (update == null) return;

        System.out.println("==================== 정보 수정 ====================");
        System.out.print("새 비밀번호 : ");
        String pw = input.readLine().trim();
        if (!pw.isEmpty()) update.setPassword(pw);

        System.out.print("새 이름 (현재: " + update.getAdminName() + "): ");
        String name = input.readLine().trim();
        if (!name.isEmpty()) update.setAdminName(name);

        System.out.print("새 부서 (현재: " + update.getDepartment() + "): ");
        String dept = input.readLine().trim();
        if (!dept.isEmpty()) update.setDepartment(dept);

        System.out.print("새 권한 (현재: " + update.getRole() + "): ");
        String role = input.readLine().trim();
        if (!role.isEmpty()) update.setRole(role);

        if (adminController.updateAdmin(update)) {
            System.out.println("정보 수정 완료");
        } else {
            System.out.println("정보 수정 실패");
        }
    }

    // D: 계정 삭제(비활성화)
    private void deleteMyAccount() throws IOException {
        AdminVo current = adminController.getAdminById(admin.getAdminId());
        if (current == null) return;

        System.out.println("==================== 계정 삭제 ====================");
        System.out.print("정말 계정을 삭제 하시겠습니까? (Y/N): ");
        String confirm = input.readLine().trim().toUpperCase();

        if ("Y".equals(confirm)) {
            current.setAdminStatus("N");
            if (adminController.updateAdmin(current)) {
                System.out.println("계정이 비활성화되었습니다. 로그아웃됩니다.");
                adminController.logout();
                System.exit(0);
            } else {
                System.out.println("계정 삭제 실패");
            }
        } else {
            System.out.println("계정 삭제 취소");
        }
    }
}
