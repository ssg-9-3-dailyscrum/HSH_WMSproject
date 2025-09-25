package main.java.com.hsh.domain.vo;/// vo 회원 데이터를 담는다. 일종의 그릇 역활.

import java.time.LocalDateTime;

public class User {
    private Long memberId;
    private String userLoginId;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String role;
    private String email;
    private String status;
    private LocalDateTime joinDate;
}
