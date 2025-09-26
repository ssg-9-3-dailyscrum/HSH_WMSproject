///  Vo = 값 객체
///  특정 데이터를 한 묶음으로 표현하는 패키지.


/// 1. 관리자 값 객체 Vo 구현
package main.java.com.hsh.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Admin {
    private Long adminId;           // 관리자 번호 (PK)
    private String adminLoginId;    // 로그인 아이디
    private String password;        // 비밀번호
    private String adminName;       // 관리자 이름
    private String department;      // 부서
    private String role;            // 권한 (예: SUPER_ADMIN, ADMIN)
    private String email;           //  이메일
    private String phone;           // 연락처 (Admin DTO에서 사용됨)
    private String address;         // 주소 (Admin DTO에서 사용됨)
    private String adminStatus;     // 활성화 상태 (Y, N)
    private LocalDateTime joinDate; // 가입일 (DB에 따라 DATETIME 또는 TIMESTAMP)
    private String salt;            // 비밀번호 암호화용 Salt  (DB에 저장한다면 필요함)



}
