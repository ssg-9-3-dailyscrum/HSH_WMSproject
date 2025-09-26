package main.java.com.hsh.domain.vo;

/// Vo = Value Object, 자바에서 데이터를 전달하는 객체
/// 공지사항 값 객체들


import lombok.Data; // @Getter, @Setter, @ToString 등을 자동으로 생성합니다.
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NoticeVo {

    // DB 컬럼과 일치하는 속성
    private Long noticeId;          // 공지 ID (PK: notice_id)
    private Long adminId;           // 관리자 번호 (FK: admin_id)
    private String title;           // 제목 (title)
    private String content;         // 내용 (content)
    private LocalDateTime createdAt; // 작성일시 (created_at)
    private String noticeStatus;    // 공지사항 사용 여부 (notice_status: 'Y' or 'N')
 }

