/// Vo = Value Object, 자바에서 데이터를 전달하는 객체
/// 문의글을 담는 객체들

package main.java.com.hsh.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// Inquire 테이블의 데이터를 담는 Value Object (VO)
@Data
@NoArgsConstructor
public class InquireVo {

    // DB 컬럼과 일치하는 속성
    private Long inquireId;           // 문의 ID (PK)
    private Long memberId;            // 작성자 ID (FK)
    private String title;             // 제목
    private String text;              // 내용
    private LocalDateTime inquireDate; // 작성일시
    private String inquireStatus;     // 문의 상태 (Y/N)

    // (Getter, Setter, toString, equals 등을 Lombok이 자동으로 생성합니다.)
}