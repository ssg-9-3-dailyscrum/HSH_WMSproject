package main.java.com.hsh.common;

public enum Member {
    ADMIN_ID("사번: "),
    USER_ID("회원 번호: "),
    NAME("이름: "),
    BUSINESS_NUMBER("사업자 번호: "),
    COMPANY_NAME("상호명: "),
    ID("아이디: "),
    PW("비밀번호: "),
    EMAIL("이메일: "),
    ADMIN_COMPANY_EMAIL("회사 이메일: "),
    DEPARTMENT("부서: "),
    POSITION("직급: "),
    ROLE("권한: "),
    PHONE("전화번호: "),
    ZIP_CODE("우편 번호: "),
    ADDRESS("주소: "),
    IS_UNREGISTER("탈퇴 여부: "),
    CREATED_AT("최초 등록 시각: "),
    AUTHORIZER_ID("최종 수정자 ID: "),
    UPDATED_AT("최종 수정 시각: "),
    UPDATED_ADMIN_ID("최종 수정자(직원) ID: "),
    UPDATED_ADMIN_AT("최종 수정(직원) 시각: "),
    UNREGISTERED_AT("탈퇴 시각: "),
    APPROVAL_STATUS("승인 상태: "),
    PENDING_STATUS("\n승인 대기중입니다."),
    REJECTED_STATUS("\n승인이 거절되었습니다. 관리자에게 문의해주세요.\n[사유]\n"),
    ADMIN_BORDER_LINE("+----+----------------+------------+--------------+-------------+"),
    ADMIN_LIST("| id |      이름       |     부서     |     직급      |     권한     |"),
    USER_BORDER_LINE("+----+--------------------------+-----------------+-------------+"),
    USER_LIST("| id |           상호명           |     사업자 번호     |    대표명    |"),
    USER_APPROVAL_BORDER_LINE("+----+--------------------------+-----------------+-------------+---------------+-----------------------+"),
    USER_APPROVAL_LIST("| id |           상호명           |    사업자 번호     |    대표명     |    승인 상태    |          가입일         |");


    private final String description;

    Member(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
