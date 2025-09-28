package main.java.com.hsh.view;

import main.java.com.hsh.controller.UserLoginController;
import main.java.com.hsh.domain.vo.UserVo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserManageView {

    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private final UserLoginController userLoginController = UserLoginController.getInstance();

    public UserManageView() {
        startManageMenu();
    }

    private void startManageMenu() {
        boolean running = true;
        while (running) {
            // 정보 조회 시 현재 로그인된 UserVo 객체를 가져옴
            UserVo currentUser = userLoginController.getCurrentLoggedInUser();
            if (currentUser == null) {
                // 로그아웃되었으면 상위 메뉴로 이동
                running = false;
                break;
            }

            try {
                int choice = displaySubMenu();

                switch (choice) {
                    case 1 -> displayUserInfo(currentUser.getUserLoginId()); // 1. 정보 조회
                    case 2 -> updateUserInfo(currentUser); // 2. 정보 수정
                    case 3 -> { // 3. 탈퇴 (비활성화)
                        if (deactivateUser()) {
                            running = false; // 탈퇴 성공 시 UserMenuView까지 종료 (LoginTypeSelect로 돌아감)
                        }
                    }
                    case 4 -> { // 4. 뒤로가기
                        System.out.println("이전 메뉴로 돌아갑니다.");
                        running = false; // UserManageView 종료 -> UserMenuView로 돌아감
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("y 잘못된 입력 형식입니다. 숫자를 입력해주세요.");
            }
        }
    }

    // 1. 정보 조회 로직
    private void displayUserInfo(String userId) {
        System.out.println("==================== 정보 조회 ====================");
        UserVo info = userLoginController.getUserInfo();
        if (info != null) {
            System.out.println("▶ 아이디: " + info.getUserLoginId());
            System.out.println("▶ 이름: " + info.getName());
            System.out.println("▶ 전화번호: " + info.getPhone());
            System.out.println("▶ 주소: " + info.getAddress());
            System.out.println("▶ 이메일: " + info.getEmail());
            System.out.println("▶ 가입일: " + info.getJoinDate());
            System.out.println("▶ 상태: " + (info.getStatus().equals("Y") ? "활성" : "비활성"));
        } else {
            System.out.println("정보를 불러오는 데 실패했습니다.");
        }
        System.out.println("-------------------------------------------------");
    }

    // 2. 정보 수정 로직
    private void updateUserInfo(UserVo currentUser) throws IOException {
        System.out.println("==================== 정보 수정 ====================");
        System.out.println("수정할 정보를 입력하세요 (변경하지 않으려면 엔터):");

        UserVo updatedVo = userLoginController.getUserInfo(); // 최신 정보로 초기화
        if (updatedVo == null) {
            System.out.println(" 사용자 정보를 가져올 수 없습니다.");
            return;
        }

        // 1) 비밀번호 수정
        System.out.print("새 비밀번호를 입력해주세요 : ");
        String newPw = input.readLine().trim();
        if (!newPw.isEmpty()) {
            updatedVo.setPassword(newPw);
        }

        // 2) 전화번호 수정
        System.out.print("새 전화번호를 입력해주세요 (현재: " + updatedVo.getPhone() + "): ");
        String newPhone = input.readLine().trim();
        if (!newPhone.isEmpty()) {
            updatedVo.setPhone(newPhone);
        }

        // 3) 주소 수정
        System.out.print("새 주소를 입력해주세요 (현재: " + updatedVo.getAddress() + "): ");
        String newAddr = input.readLine().trim();
        if (!newAddr.isEmpty()) {
            updatedVo.setAddress(newAddr);
        }

        // 4) 이메일 수정
        System.out.print("새 이메일 (현재: " + updatedVo.getEmail() + "): ");
        String newEmail = input.readLine().trim();
        if (!newEmail.isEmpty()) {
            updatedVo.setEmail(newEmail);
        }

        // 컨트롤러를 통해 수정 요청
        if (userLoginController.updateUserInfo(updatedVo)) {
            System.out.println(" 회원 정보가 성공적으로 수정되었습니다!");
        } else {
            System.out.println(" 회원 정보 수정에 실패했습니다. 다시 시도해주세요.");
        }
    }

    // 3. 탈퇴 로직 (비활성화)
    private boolean deactivateUser() throws IOException {
        System.out.println("==================== 회원 탈퇴/비활성화 ====================");
        System.out.print("정말로 계정을 비활성화(탈퇴) 하시겠습니까? (Y/N): ");
        String confirm = input.readLine().trim().toUpperCase();

        if (confirm.equals("Y")) {
            return userLoginController.deactivateUser();
        } else {
            System.out.println("회원 탈퇴를 취소합니다.");
            return false;
        }
    }

    // 회원 관리 서브 메뉴 출력
    private int displaySubMenu() throws IOException {
        System.out.println("\n=================================================");
        System.out.println("                   보람삼조회원 관리 메뉴             ");
        System.out.println("=================================================");
        System.out.println("   1. 정보 조회");
        System.out.println("   2. 정보 수정");
        System.out.println("   3. 탈퇴    ");
        System.out.println("   4. 뒤로가기");
        System.out.println("-------------------------------------------------");
        System.out.print("메뉴를 선택해주세요 : ");
        return Integer.parseInt(input.readLine().trim());
    }
}