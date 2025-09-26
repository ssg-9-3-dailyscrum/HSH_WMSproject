package main.java.com.hsh.controller;

import dto.request.UserRequestDto;
import dto.response.AuthResponseDto;
import dto.response.UserApprovalResponseDto;
import dto.response.UserResponseDto;
import exception.Exception;
import handler.MemberInputHandler;
import common.Role;
import dto.response.AdminResponseDto;
import java.util.List;
import library.Script;
import common.ValidCheck;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import service.AdminService;
import service.UserService;
import service.serviceImpl.AdminServiceImpl;
import service.serviceImpl.UserServiceImpl;

public class AdminController {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static MemberInputHandler memberInputHandler = new MemberInputHandler();
    private static ValidCheck validCheck = new ValidCheck();
    private static Script script = new Script();
    private static AdminService adminService = new AdminServiceImpl();
    private static UserService userService = new UserServiceImpl();
    private static AuthResponseDto auth = new AuthResponseDto(); // 현재 로그인한 관리자 정보 저장


    /**
     * '회원 관리' 메뉴 선택 1. 조회 | 2. 수정 | 3. 권한 설정 | 4. 삭제 | 5. 이전 메뉴
     */
    public void manageMember(AuthResponseDto user) throws IOException {
        auth = user; // 현재 로그인한 관리자의 세션 정보 (ID, Role) 저장
        while (true) {
            script.manageMember();
            String menu = br.readLine().trim();
            validCheck.validateMenuNumber1To5(menu);

            switch (menu) {
                case "1" -> viewMember();     // 직원 및 쇼핑몰 사업자 조회
                case "2" -> editMember();     // 직원 정보 수정
                case "3" -> setMemberRole();  // 권한 및 승인 설정
                case "4" -> delete();         // 직원 및 사업자 삭제
                case "5" -> {
                    return;
                }
            }
        }
    }

    /**
     * '회원 관리 > 조회' 메뉴 (직원(Admin)과 사업자(User) 분리 조회)
     */
    private void viewMember() throws IOException {
        while (true) {
            try {
                script.viewMember();
                String menu = br.readLine().trim();
                validCheck.validateMenuNumber1To3(menu);

                switch (menu) {
                    case "1" -> viewAdmin(); // 직원(Admin) 테이블 조회
                    case "2" -> viewUser();  // 쇼핑몰 사업자(User) 테이블 조회
                    case "3" -> {
                        return;
                    }
                }
                script.viewSuccess();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                script.viewFailure();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                script.viewFailure();
            }
        }
    }

    /**
     * '회원 관리 > 조회 > 직원 조회' 메뉴 (Admin 테이블 대상)
     */
    private void viewAdmin() throws IOException {
        while (true) {
            try {
                script.viewAdmin();
                String menu = br.readLine().trim();
                validCheck.validateMenuNumber1To4(menu);

                switch (menu) {
                    case "1" -> viewAdminDetail(); // 직원 상세 조회
                    case "2" -> viewAllAdmin();    // 직원 전체 조회
                    case "3" -> viewAdminByRole(); // 권한별 직원 조회
                    case "4" -> {
                        return;
                    }
                }

                script.viewSuccess();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                script.viewFailure();
            }
        }
    }

    /**
     * '회원 관리 > 조회 > 직원 조회 > 직원 상세 조회'
     */
    private void viewAdminDetail() {
        try {
            script.getAdminId();
            int id = validCheck.validateNumber(br.readLine());
            // AdminService 호출하여 DB에서 직원 정보 조회
            AdminResponseDto response = adminService.findById(id);
            script.adminInfo(response);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * '회원 관리 > 조회 > 직원 조회 > 직원 전체 조회'
     */
    private void viewAllAdmin() {
        // AdminService 호출하여 DB에서 모든 직원 목록 조회
        List<AdminResponseDto> list = adminService.findAll();
        script.adminListTitle();
        list.forEach(l -> script.adminList(l));
        script.adminListBorder();
    }

    /**
     * '회원 관리 > 조회 > 직원 조회 > 권한별 직원 조회'
     */
    private void viewAdminByRole() throws IOException {
        script.viewMemberRole();
        String menu = br.readLine().trim();
        validCheck.validateMenuNumber1To3(menu);
        String role = "";

        switch (menu) {
            // WMS 권한 Enum을 사용하여 DB 쿼리 파라미터 생성
            case "1" -> role = Role.SUPER_ADMIN.toString();
            case "2" -> role = Role.ADMIN.toString();
            case "3" -> role = Role.EMPLOYEE.toString();
        }

        List<AdminResponseDto> list = adminService.findByRole(role);
        script.adminListTitle();
        list.forEach(l -> script.adminList(l));
        script.adminListBorder();
    }


    /**
     * '회원 관리 > 조회 > 쇼핑몰 사업자 회원 조회' 메뉴 (User 테이블 대상)
     */
    private void viewUser() {

        while (true) {
            try {
                script.viewUser();
                String menu = br.readLine().trim();
                validCheck.validateMenuNumber1To4(menu);

                switch (menu) {
                    case "1" -> viewUserDetail();
                    case "2" -> viewAllUser();
                    case "3" -> viewPendingApproval(); // 승인 대기자 조회 (WMS 핵심 기능)
                    case "4" -> {
                        return;
                    }
                }
                script.viewSuccess();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                script.viewFailure();
            }
        }
    }
    private void viewUserDetail() throws IOException {
        int targetUserId = memberInputHandler.getUserIdInput();
        // UserService 호출하여 DB에서 사업자 회원 정보 조회
        UserResponseDto response = userService.findById(targetUserId);
        script.userInfo(response);
    }

    private void viewAllUser() {
        List<UserResponseDto> list = userService.findAll();
        script.userListTitle();
        list.forEach(l -> script.userList(l));
        script.userListBorder();
    }

    private void viewPendingApproval() {
        // UserService 호출하여 DB에서 승인 대기자 목록 조회
        List<UserApprovalResponseDto> list = userService.findByApproval();
        script.userApprovalListTitle();
        list.forEach(l -> script.userApprovalList(l));
        script.userApprovalListBorder();
    }

    /**
     * '회원 관리 > 수정' 메뉴
     */
    private void editMember() {

        while (true) {
            try {
                script.editMember();
                String menu = br.readLine().trim();
                validCheck.validateMenuNumber1To3(menu);

                switch (menu) {
                    case "1" -> editAdmin(); // 직원 정보 수정
                    case "2" -> editPwd();   // 직원 비밀번호 수정
                    case "3" -> {
                        return;
                    }
                }
                script.updateSuccess();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                script.updateFailure();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                script.updateFailure();
            }
        }
    }

    /**
     * '회원 관리 > 수정 > 회원 정보 수정' (직원 본인 수정)
     */
    private void editAdmin() throws IOException {
        // auth.getId()는 현재 로그인한 관리자의 ID를 Service에 전달하여 본인 정보만 수정하도록 권한을 부여함
        adminService.updateAdmin(auth.getId(), memberInputHandler.updateAdmin());
    }

    /**
     * '회원 관리 > 수정 > 비밀번호 수정' (직원 비밀번호 수정)
     */
    public void editPwd() throws IOException {
        String adminId = memberInputHandler.getIdInput();
        adminService.updatePwd(adminId, memberInputHandler.updateAdminPwd());
    }

    /**
     * '회원 관리 > 권한 설정' 메뉴 (WMS 핵심 통제 기능)
     */
    private void setMemberRole() {

        while (true) {
            try {
                script.setMemberPermission();
                String menu = br.readLine().trim();
                validCheck.validateMenuNumber1To4(menu);

                switch (menu) {
                    case "1" -> setAdminRole();         // 직원 권한 (Role) 설정
                    case "2" -> setDeptAndPosition();   // 직원 부서 및 직급 설정
                    case "3" -> approveUser();          // 사업자 가입 승인 (WMS 핵심)
                    case "4" -> {
                        return;
                    }
                }
                script.updateSuccess();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                script.updateFailure();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                script.updateFailure();
            }
        }
    }

    /**
     * '회원 관리 > 권한 설정 > 직원 권한' (RBAC/Admin 테이블 업데이트)
     */
    private void setAdminRole() throws IOException {
        int targetEmployeeId = memberInputHandler.getAdminIdInput();
        // auth.getId()를 전달하여 요청자가 SUPER_ADMIN인지 확인하는 로직이 Service에 포함됨
        adminService.updateRole(auth.getId(), targetEmployeeId,
                memberInputHandler.updateAdminRole());
    }

    /**
     * '회원 관리 > 권한 설정 > 직원 부서 및 직급' (Admin 테이블 업데이트)
     */
    private void setDeptAndPosition() throws IOException {
        int targetEmployeeId = memberInputHandler.getAdminIdInput();
        adminService.updateAdminDeptPos(auth.getId(), targetEmployeeId,
                memberInputHandler.updateAdminDeptPos());
    }

    /**
     * '회원 관리 > 권한 설정 > 쇼핑몰 사업자 권한 승인' (User 테이블 status 업데이트)
     */
    private void approveUser() throws IOException {
        int targetUserId = memberInputHandler.getUserIdInput();
        // auth.getId()를 전달하여 승인자가 누구인지 기록하고 권한 확인
        userService.updateApprovalStatus(auth.getId(), targetUserId,
                memberInputHandler.updateApprovalStatus());
    }

    /**
     * '회원 관리 > 삭제' 메뉴 (소프트 삭제)
     */
    private void delete() throws IOException {

        while (true) {
            try {
                script.deleteMember();
                String menu = br.readLine().trim();
                validCheck.validateMenuNumber1To3(menu);

                switch (menu) {
                    case "1" -> deleteAdmin();
                    case "2" -> deleteUser();
                    case "3" -> {
                        return;
                    }
                }

                script.deleteSuccess();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                script.deleteFailure();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                script.deleteFailure();
            }
        }
    }


    /**
     * '회원 관리 > 삭제 > 직원 삭제' (Admin 테이블 status 업데이트)
     */
    private void deleteAdmin() throws IOException {
        int targetEmployeeId = memberInputHandler.getAdminIdInput();
        script.confirm();
        String menu = br.readLine().trim();
        validCheck.validateMenuNumber1To2(menu);

        switch (menu) {
            case "1":
                adminService.deleteAdmin(targetEmployeeId);
                break;
            case "2":
                break;
        }
    }

    /**
     * '회원 관리 > 삭제 > 쇼핑몰 사업자 회원 삭제' (User 테이블 status 업데이트)
     */
    private void deleteUser() throws IOException {
        int targetUserId = memberInputHandler.getUserIdInput();

        script.confirm();
        String menu = br.readLine().trim();
        validCheck.validateMenuNumber1To2(menu);

        switch (menu) {
            case "1":
                UserRequestDto request = new UserRequestDto();
                userService.delete(targetUserId); // Service에서 소프트 삭제 처리
                break;
            case "2":
                break;
        }
    }
}