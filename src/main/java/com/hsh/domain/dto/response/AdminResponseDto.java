//package main.java.com.hsh.domain.dto.response;
//
//
//import main.java.com.hsh.common.epartment;
//import main.java.com.hsh.common.Position;
//import main.java.com.hsh.common.Role;
//
//import main.java.com.hsh.library.ResultSetReader;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Optional;
//
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//
//public class AdminResponseDto {
//    private int id;
//    private String name;
//    private String adminId;
//    private String password;
//    private String companyEmail;
//    private Department department;
//    private Position position;
//    private Role role;
//    private String phone;
//    private String createdAt;
//    private Integer authorizerId;
//    private String updatedAt;
//
//    private static ResultSetReader rsr = new ResultSetReader();
//
//    public AdminResponseDto(ResultSet rs) throws SQLException {
//        this.id = rs.getInt("id");
//        this.name = rs.getString("name");
//        this.adminId = rsr.getColumnValue(rs, "admin_id", String.class);
//        this.password = rsr.getColumnValue(rs,"password", String.class);
//        this.companyEmail = rsr.getColumnValue(rs,"company_email", String.class);
//        this.department = Optional.ofNullable(rs.getString("department"))
//                .map(d -> {
//                    try {
//                        return Department.valueOf(d);
//                    } catch (IllegalArgumentException e) {
//                        return null;
//                    }
//                })
//                .orElse(null);
//        this.position = Optional.ofNullable(rs.getString("position"))
//                .map(d -> {
//                    try {
//                        return Position.valueOf(d);
//                    } catch (IllegalArgumentException e) {
//                        return null;
//                    }
//                })
//                .orElse(null);
//        this.role = Optional.ofNullable(rs.getString("role"))
//                .map(d -> {
//                    try {
//                        return Role.valueOf(d);
//                    } catch (IllegalArgumentException e) {
//                        return null;
//                    }
//                })
//                .orElse(null);
//        this.phone = rsr.getColumnValue(rs,"phone", String.class);
//        this.createdAt = rsr.getColumnValue(rs,"created_at", String.class);
//        this.authorizerId = rsr.getColumnValue(rs,"authorizer_id", Integer.class);
//        this.updatedAt = rsr.getColumnValue(rs,"updated_at", String.class);
//    }
//
//    public String formatAdminList() {
//        return String.format("| %-2d | %-12s\t| %-10s\t| %-10s\t| %-10s\t|",
//                id,
//                name,
//                department,
//                position,
//                role
//        );
//    }
//}