package main.java.com.hsh.util;


public class UserSession {
    private static String currentUserRole;   // "SUPER_ADMIN", "WH_ADMIN", "MEMBER"
    private static Integer currentUserId;    // admin_id 또는 member_id
    private static String currentUserName;   // 사용자 이름

    // 로그인
    public static void login(String userRole, Integer userId, String userName) {
        currentUserRole = userRole;
        currentUserId = userId;
        currentUserName = userName;
    }

    // 로그아웃
    public static void logout() {
        currentUserRole = null;
        currentUserId = null;
        currentUserName = null;
    }

    // 현재 사용자 정보 조회
    public static String getCurrentUserRole() {
        return currentUserRole;
    }

    public static Integer getCurrentUserId() {
        return currentUserId;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    // 로그인 상태 확인
    public static boolean isLoggedIn() {
        return currentUserRole != null && currentUserId != null;
    }

    // 권한 체크 헬퍼 메서드들
    public static boolean isAdmin() {
        return "SUPER_ADMIN".equals(currentUserRole) || "WH_ADMIN".equals(currentUserRole);
    }

    public static boolean isSuperAdmin() {
        return "SUPER_ADMIN".equals(currentUserRole);
    }

    public static boolean isWarehouseAdmin() {
        return "WH_ADMIN".equals(currentUserRole);
    }

    public static boolean isMember() {
        return "MEMBER".equals(currentUserRole);
    }

    // 테스트용 메서드들
    public static void printSessionInfo() {
        System.out.println("=== UserSession 정보 ===");
        System.out.println("로그인 상태: " + isLoggedIn());
        System.out.println("사용자 권한: " + getCurrentUserRole());
        System.out.println("사용자 ID: " + getCurrentUserId());
        System.out.println("사용자 이름: " + getCurrentUserName());
        System.out.println("========================");
    }

    // 테스트용 빠른 로그인
    public static void quickLoginAsAdmin() {
        login("SUPER_ADMIN", 1, "테스트관리자");
    }

    public static void quickLoginAsWhAdmin(int adminId) {
        login("WH_ADMIN", adminId, "테스트창고관리자" + adminId);
    }

    public static void quickLoginAsMember(int memberId) {
        login("MEMBER", memberId, "테스트회원" + memberId);
    }
}