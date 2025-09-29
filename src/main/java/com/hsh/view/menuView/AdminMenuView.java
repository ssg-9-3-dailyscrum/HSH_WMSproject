package main.java.com.hsh.view.menuView;

import main.java.com.hsh.controller.AdminLoginController;
import main.java.com.hsh.domain.vo.AdminVo;
import main.java.com.hsh.domain.vo.UserVo;
import main.java.com.hsh.service.UserLoginService;
import main.java.com.hsh.service.serviceImpl.UserLoginServiceImpl;
import main.java.com.hsh.view.AdminManageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class AdminMenuView {

    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private final AdminVo admin;

    public AdminMenuView(AdminVo admin) throws IOException {
        this.admin = admin;
        showMenu();
    }

    private void showMenu() throws IOException {
        boolean running = true;

        while (running) {
            String role = admin.getRole() != null ? admin.getRole().trim() : "";

            System.out.println("=================================================");

            if ("총관리자".equals(role)) {
                System.out.println("       [" + admin.getAdminName() + "님 총관리자 메뉴]          ");
                System.out.println("1. 회원/관리자 조회 및 계정 관리");
                System.out.println("2. 모든 재고관리");
                System.out.println("3. 모든 창고관리");
                System.out.println("4. 모든 입고관리");
                System.out.println("5. 모든 출고관리");
            } else {
                System.out.println("       [" + admin.getAdminName() + "님 관리자 메뉴]          ");
                System.out.println("1. 내 계정 관리");
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

            switch (choice) {
                case 1 -> {
                    if ("총관리자".equals(role)) {

                        showAllUsersExceptSuper();
                    }
                    new AdminManageView(admin);
                }
                case 2 -> System.out.println("재고관리 메뉴 호출");
                case 3 -> System.out.println("창고관리 메뉴 호출");
                case 4 -> System.out.println("입고관리 메뉴 호출");
                case 5 -> System.out.println("출고관리 메뉴 호출");
                case 6 -> {
                    AdminLoginController.getInstance().logout();
                    running = false;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void showAllUsersExceptSuper() {
        List<AdminVo> admins = AdminLoginController.getInstance().selectAllExceptSuperAdmin();
        UserLoginService userService = UserLoginServiceImpl.getInstance();
        List<UserVo> users = userService.getAllUsers();

        System.out.println("==================== 회원, 관리자 조회 ====================");

        if (admins.isEmpty() && users.isEmpty()) {
            System.out.println("회원 및 관리자가 존재하지 않습니다.");
            return;
        }

        if (!admins.isEmpty()) {
            System.out.println("------------------------ 관리자 ------------------------ ");
            for (AdminVo a : admins) {
                System.out.println("ID: " + a.getAdminLoginId()
                        + " | 이름: " + a.getAdminName()
                        + " | 부서: " + a.getDepartment()
                        + " | 권한: " + a.getRole());
            }
        }

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
