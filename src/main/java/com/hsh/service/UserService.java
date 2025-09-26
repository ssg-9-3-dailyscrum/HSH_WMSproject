package main.java.com.hsh.service;

import dto.request.UserRequestDto;
import dto.response.ResponseDto;
import javax.naming.AuthenticationException;


public interface UserService {

    /**
     * 사용자 ID와 비밀번호를 검증하고 로그인 처리를 수행합니다.
     * @param authRequest ID와 PW가 담긴 요청 객체
     * @return 인증 성공 시 사용자 ID와 권한이 포함된 응답 객체
     * @throws AuthenticationException 인증 실패 시 (ID 없음, PW 불일치)
     */
    AuthResponseDto login(AuthRequestDto authRequest) throws AuthenticationException;
}