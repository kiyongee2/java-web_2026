# 🏦 은행 계좌 관리 시스템 — 완성 프로젝트

같은 은행 로직을 세 가지 방식으로 구현한 바로 실행 가능한 완성본입니다.

| 폴더 | 버전 | 실행 |
|------|------|------|
| `1-console` | 순수 Java 콘솔 (메모리) | `javac` 후 `java bank.BankApp` |
| `1b-console-h2` | 순수 Java 콘솔 + H2 DB (JDBC) | h2.jar 넣고 `javac`/`java` (README 참고) |
| `2-spring-boot` | Spring Boot + JPA + H2 REST API | `mvn spring-boot:run` |

## 학습 발전 단계
메모리(Map) → JDBC(H2) → JPA(Spring). 같은 로직에서 "저장 방법"만 바뀝니다.
각 폴더의 README.md 를 참고하세요.
