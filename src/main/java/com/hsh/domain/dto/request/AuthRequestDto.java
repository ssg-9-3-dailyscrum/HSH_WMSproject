package main.java.com.hsh.domain.dto.request;

/*
 * 인증 요청 데이터 전송 객체 Dto
 * DTO = 오직 getter/setter만 갖고 있는다 봐도 됨. userlogin id 구현
 * 로그인 아이디
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequestDto {
    private String user_login_id;
    private String password;

    public AuthRequestDto(String user_login_id, String password) {
        this.user_login_id = user_login_id;
        this.password = password;
    }
}
