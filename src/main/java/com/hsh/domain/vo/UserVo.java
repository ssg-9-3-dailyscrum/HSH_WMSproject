package main.java.com.hsh.domain.vo;

import lombok.Data; //
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// User 테이블의 데이터를 담는 Value Object (VO)
@Data
@NoArgsConstructor
public class UserVo {

    // DB 컬럼과 일치하는 속성
    private int memberId;             // 회원번호 (PK)
    private String userLoginId;        // 로그인 아이디
    private String password;           // 비밀번호
    private String name;               // 이름
    private String phone;              // 연락처
    private String address;            // 주소
    private String role;               // 권한
    private LocalDateTime joinDate;    // 가입일
    private String email;              // 이메일
    private String status;             // 가입 상태 (Y/N)


}