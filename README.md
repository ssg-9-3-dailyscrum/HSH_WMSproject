# 패키지 구조
com.hsh<br>
 ├── controller   // 사용자의 요청을 받고 응답을 전달<br>
 ├── service      // 비즈니스 로직 처리<br>
 ├── dao          // DB 접근 (CRUD)<br>
 ├── domain       // VO, DTO, Entity<br>
 ├── view         // 콘솔/웹/JSP/Thymeleaf 등<br>
 └── util         // 공용 유틸리티 (로그, DB 연결 등)<br>


# Git Branch Strategy

우리 팀(4명)의 협업을 위한 브랜치 전략을 정리한 문서입니다.  
항상 **feature → develop → main** 순서로 머지합니다.  

---

## 1. 브랜치 구조

- **main**
  - 항상 배포 가능한 안정 버전 유지
  - QA/테스트 완료 후 `develop`에서 머지

- **develop**
  - 전체 기능 통합 브랜치
  - 모든 기능(`feature/*`)은 `develop`으로 PR 후 머지

- **feature/**
  - 담당자가 맡은 기능별 브랜치
  - 작업 후 `develop`으로 PR 생성

---

## 2. 기능별 브랜치

| 담당자 | 기능 영역 | 브랜치명 |
|--------|-----------|----------|
| 1번 | 창고관리 | `feature/warehouse` |
| 2번 | 재고관리 | `feature/inventory` |
| 3번 | 입고관리, 출고관리 | `feature/inbound-outbound` |
| 4번 | 회원관리, 고객센터, 로그인/로그아웃 | `feature/member-support` |

---

## 3. 작업 프로세스

1. **작업 시작 전** `develop` 최신화
   ```bash
   git checkout develop
   git pull origin develop
   git checkout feature/브랜치명
   git merge develop
