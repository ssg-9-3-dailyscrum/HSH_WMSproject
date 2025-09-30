# 📦 WMS Project

창고 관리(Warehouse Management System)를 위한 프로젝트입니다.
본 시스템은 가구 재고 관리와 입출고 현황 파악을 목적으로 하며, Java + JDBC + MySQL 기반으로 구현되었습니다.
MVC 패턴을 적용해 유지보수성과 확장성을 고려했습니다.

---

## 🛠️ 기술 스택

- Language : Java (JDK 17)
- Database : MySQL 8.0.22
- Database Access : JDBC
- Tooling : IntelliJ IDEA, GitHub, Workbench, Railway

---

## 📂 프로젝트 구조

```
src/
 ┣ controller/   # 사용자 요청 처리
 ┣ service/      # 비즈니스 로직
 ┣ serviceImpl/    # Service 구현체 (비즈니스 로직 실제 처리)
 ┣ dao/          # 데이터 접근 인터페이스
 ┣ daoImpl/      # DAO 구현체 (JDBC 활용)
 ┣ domain/          # Vo, Dto
 ┣ Session/       # 사용자 로그인 정보 저장
 ┗ view/         # CLI 기반 UI
```

---

## 🔀 Git Flow 전략

우리 팀(4명)의 협업을 위해 Git Flow 전략 및 커밋 컨벤션을 사용했습니다.
**feature → develop → main** 순서로 merge 진행

- main: 실제 서비스 배포용 안정 브랜치
- develop: 기능 통합 브랜치
- feature/: 새로운 기능 개발 브랜치
    - feature/inbound-outbound : 입출고 관리
    - feature/inventory : 재고 관리
    - feature/member-board : 회원/로그인 관리
    - feature/warehouse : 창고 관리

---

## ✨ 주요 기능

- 입출고 관리 : 상품 입고/출고 내역 기록 및 추적
- 재고 관리 : 창고별 재고 조회 및 실사 관리
- 회원/로그인 관리 : 사용자 등록, 로그인, 권한별 기능 접근
- 창고 관리 : 기능별 창고 조회 및 창고/섹션 등록·수정

---


## ⚙️ 확장 포인트