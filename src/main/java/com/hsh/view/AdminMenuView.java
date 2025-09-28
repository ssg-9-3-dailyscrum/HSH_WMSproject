package main.java.com.hsh.view;

import main.java.com.hsh.controller.AdminLoginController;
import main.java.com.hsh.domain.vo.AdminVo;
import main.java.com.hsh.domain.vo.UserVo;
import main.java.com.hsh.service.UserLoginService;
import main.java.com.hsh.service.serviceImpl.UserLoginServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class AdminMenuView {

    // 입력을 받기 위한 BufferedReader
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private final AdminVo admin;

    // 생성자: 로그인한 관리자 객체를 받아 메뉴 표시
    public AdminMenuView(AdminVo admin) throws IOException {
        this.admin = admin;
        showMenu();
    }

    // 메뉴 표시
    private void showMenu() throws IOException {
        boolean running = true;

        while (running) {
            String role = admin.getRole() != null ? admin.getRole().trim() : "";

            System.out.println("=================================================");

            // 총관리자 / 일반 관리자 메뉴 구분
            if ("총관리자".equals(role)) {
                System.out.println("       [" + admin.getAdminName() + "님 총관리자 메뉴]          ");
                System.out.println("1. 모든 회원/관리자 조회");
                System.out.println("2. 모든 재고관리");
                System.out.println("3. 모든 창고관리");
                System.out.println("4. 모든 입고관리");
                System.out.println("5. 모든 출고관리");
            } else {
                System.out.println("       [" + admin.getAdminName() + "님 관리자 메뉴]          ");
                System.out.println("1. 회원관리");
                System.out.println("2. 재고관리");
                System.out.println("3. 창고관리");
                System.out.println("4. 입고관리");
                System.out.println("5. 출고관리");
            }

            System.out.println("6. 로그아웃");
            System.out.println("-------------------------------------------------");
            System.out.print("메뉴 선택: ");

            int choice;
            try {
                choice = Integer.parseInt(input.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                continue;
            }

            // 메뉴 선택 처리
            switch (choice) {
                case 1 -> {
                    if ("총관리자".equals(role)) {
                        // 총관리자는 회원+관리자 통합 조회
                        showAllUsersExceptSuper();
                    } else {
                        System.out.println("회원관리 메뉴 호출");
                    }
                }
                case 2 -> System.out.println("재고관리 메뉴 호출");
                case 3 -> System.out.println("창고관리 메뉴 호출");
                case 4 -> System.out.println("입고관리 메뉴 호출");
                case 5 -> System.out.println("출고관리 메뉴 호출");
                case 6 -> {
                    // 로그아웃 처리
                    AdminLoginController.getInstance().logout();
                    running = false;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 총관리자 제외 모든 회원, 관리자 조회
    private void showAllUsersExceptSuper() {
        // 관리자 목록 조회
        List<AdminVo> admins = AdminLoginController.getInstance().selectAllExceptSuperAdmin();

        // 회원 목록 조회
        UserLoginService userService = UserLoginServiceImpl.getInstance();
        List<UserVo> users = userService.getAllUsers();

        System.out.println("==================== 회원, 관리자 조회 ====================");

        // 관리자와 회원 모두 없는 경우
        if (admins.isEmpty() && users.isEmpty()) {
            System.out.println("회원 및 관리자가 존재하지 않습니다.");
            return;
        }

        // 관리자 출력
        if (!admins.isEmpty()) {
            System.out.println("------------------------ 관리자 ------------------------ ");
            for (AdminVo a : admins) {
                System.out.println("ID: " + a.getAdminLoginId()
                        + " | 이름: " + a.getAdminName()
                        + " | 부서: " + a.getDepartment()
                        + " | 권한: " + a.getRole()); // '역할' → '권한'
            }
        }

        // 회원 출력
        if (!users.isEmpty()) {
            System.out.println("------------------  회원------------------ ");
            for (UserVo u : users) {
                System.out.println("ID: " + u.getUserLoginId()
                        + " | 이름: " + u.getName()
                        + " | 전화: " + u.getPhone()
                        + " | 이메일: " + u.getEmail()
                        + " | 상태: " + u.getStatus());
            }
        }
    }
}
