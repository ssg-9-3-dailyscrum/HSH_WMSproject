package main.java.com.hsh.domain.vo;

///  Vo = 값 객체
///  특정 데이터를 한 묶음으로 표현하는 패키지.
/// 1. 관리자 값 객체 Vo 구현

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AdminVo {

    private int adminId;
    private String adminLoginId;
    private String password;
    private String adminName;
    private String department; // 부서
    private String role;       // 권한
    private String email;
    private String phone;
    private String address;
    private String adminStatus;
    private LocalDateTime joinDate;
    private String salt;

    // 생성자 추가
    public AdminVo(String adminLoginId, String password, String adminName, String role, String department, String status) {
        this.adminLoginId = adminLoginId;
        this.password = password;
        this.adminName = adminName;
        this.role = role;
        this.department = department;
        this.adminStatus = status;
    }

}