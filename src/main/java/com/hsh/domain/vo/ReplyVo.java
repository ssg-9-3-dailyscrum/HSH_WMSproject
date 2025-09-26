/// Vo = Value Object, 자바에서 데이터를 전달하는 객체
/// Reply 값 객체들


package main.java.com.hsh.domain.vo;

import lombok.Data; // @Getter, @Setter, @ToString 등을 자동으로 생성합니다.
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

public class ReplyVo {


    private Long replyId;           // 답변 ID (PK: reply_id)
    private Long inquireId;         // 문의글 ID (FK: inquire_id)
    private Long adminId;           // 답변 관리자 ID (FK: admin_id)
    private String text;            // 답변 내용 (text)
    private LocalDateTime replyDate; // 답변 일시 (reply_date)
    private String replyStatus;     // 답변 상태 (reply_status: Y/N)
}