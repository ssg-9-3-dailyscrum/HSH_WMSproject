//package main.java.com.hsh.domain.dto.request;
//
////import main.java.com.hsh.common.Department; // common/Department.java 파일 참조
//import main.java.com.hsh.common.Position;   // common/Position.java 파일 참조
//import main.java.com.hsh.common.Role;       // common/Role.java 파일 참조
//import main.java.com.hsh.library.LocalDateTime; // library/LocalDateTime.getTime() 참조
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//public class AdminRequestDto {
//
//    // DB의 'admin_id'와 대응되는 고유 ID (PK)
//    private Long id;
//
//    // Admin 테이블의 'admin_login_id'와 대응
//    private String adminLoginId;
//
//    private String name;
//    private String password;
//    private String salt; // 비밀번호 암호화용 salt
//    private String email;
////    private Department department;
//    private Position position;
//    private Role role;
//    private String phone;
//    private String createdAt;
//
//    /// 승인/권한 변경을 요청한 직원의 ID
//    private Long authorizerId;
//
//    private String updatedAt;
//
//
//    /// 1. 회원 가입 (Registration)
//
//    public AdminRequestDto(String name, String adminLoginId, String salt, String password, String phone) {
//        this.name = name;
//        this.adminLoginId = adminLoginId;
//        this.salt = salt; // 비밀번호 암호화를 하기위해 쓰는 것.
//        this.password = password;
//        // companyEmail은 adminLoginId를 기반으로 자동 생성
//        this.email = adminLoginId + "@user1.example.com";
//        this.phone = phone;
//        this.createdAt = LocalDateTime.getTime();
//        // **DB에는 admin_status가 'Y'로 들어가는 로직이 필요합니다.**
//    }
//
//
//    /// 2. 정보 수정 (Update - 이름, 전화번호 등)
//    // ------------------------------------------------
//    public AdminRequestDto(String name, String adminLoginId, String phone) {
//        this.name = name;
//        this.adminLoginId = adminLoginId;
//        this.email = adminLoginId + "@user1.example.com";
//        this.phone = phone;
//        this.updatedAt = LocalDateTime.getTime();
//    }
//
//
//
//
//    /// 3. 비밀번호 수정 (Update Password)
//
//    public AdminRequestDto(String salt, String password) {
//        this.salt = salt;
//        this.password = password;
//        this.updatedAt = LocalDateTime.getTime();
//    }
//
//
//    /// 4. 권한 수정 (Update Role)
//
//    public AdminRequestDto(Role role) {
//        this.role = role;
//        this.updatedAt = LocalDateTime.getTime();
//    }
//
////    /// 5. 부서 및 직급 수정 (Update Dept & Position)
////
////    public AdminRequestDto(Department department, Position position) {
////        this.department = department;
////        this.position = position;
////        this.updatedAt = LocalDateTime.getTime();
////    }
////}