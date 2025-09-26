package main.java.com.hsh.controller;

import common.ValidCheck;
import dto.request.UserRequestDto;
import dto.response.AuthResponseDto; // 로그인 성공 정보를 담은 DTO (memberId, Role)
import dto.response.UserResponseDto; // 회원 상세 정보 응답 DTO
import exception.Exception;
import handler.MemberInputHandler; // 사용자 입력 처리
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import library.Script;
import service.UserService;
import service.serviceImpl.UserServiceImpl;

public class UserController {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static MemberInputHandler memberInputHandler = new MemberInputHandler();
    private static ValidCheck validCheck = new ValidCheck();
    private static Script script = new Script();
    private static UserService userService = new UserServiceImpl();
    private static AuthResponseDto auth = new AuthResponseDto(null, null, false); // 인증 정보 객체

    /**
     * '회원 관리' 메뉴 선택
     *  현재 로그인한 사용자의 인증 정보 (AuthResponseDto)
     */
    public void manageUser(AuthResponseDto user) throws IOException {
        // 현재 로그인 세션 정보 저장 (memberId와 Role을 담고 있음)
        auth = user;

        while (true) {
            script.manageUser();
            String menu = br.readLine().trim();
            validCheck.validateMenuNumber1To5(menu);

            switch (menu) {
                case "1" -> viewInfo();
                case "2" -> editUser();
                case "3" -> editPwd();
                case "4" -> unregister();
                case "5" -> {
                    return;
                }
            }
        }
    }

    /**
     * '회원 관리 > 정보 조회' (본인 정보 조회)
     */
    private void viewInfo() {
        try {
            // Service 호출 시, DB PK인 memberId를 사용합니다.
            UserResponseDto response = userService.findById(auth.getId());
            script.userInfo(response);
            script.viewSuccess();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            script.viewFailure();
        }
    }

    /**
     * '회원 관리 > 정보 수정' (본인 정보 수정)
     */
    private void editUser() {
        try {
            // auth.getId()로 본인을 식별하고, memberInputHandler.updateUser()가 UserRequestDto를 반환합니다.
            userService.updateUser(auth.getId(), memberInputHandler.updateUser());
            script.updateSuccess();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            script.updateFailure();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            script.updateFailure();
        }
    }

    /**
     * '회원 관리 > 비밀번호 수정' (본인 비밀번호 수정)
     * NOTE: 실제 로직은 Service에서 auth.getId()를 사용해 본인임을 재확인해야 합니다.
     */
    public void editPwd() throws IOException {
        try {
            // 비밀번호 변경을 위해 사업자 번호(bizNo)를 입력받아 본인 확인용으로 사용 (기존 로직 유지)
            String bizNo = memberInputHandler.getBusinessNumberInput();
            userService.updateUserPwd(bizNo, memberInputHandler.updateUserPwd());
            script.updateSuccess();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            script.updateFailure();
        }
    }

    /**
     * '회원 관리 > 탈퇴' (본인 계정 비활성화)
     */
    private void unregister() throws IOException {
        script.confirm();
        String menu = br.readLine().trim();
        validCheck.validateMenuNumber1To2(menu);

        switch (menu) {
            case "1":
                // 탈퇴 요청 DTO를 생성하고, auth.getId()를 사용해 본인의 계정을 비활성화합니다.
                UserRequestDto request = new UserRequestDto();
                userService.unregister(auth.getId(), request);
                script.unregister();
                break;
            case "2":
                break;
        }
    }
}
