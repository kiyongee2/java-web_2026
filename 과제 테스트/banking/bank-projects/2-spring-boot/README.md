# 은행 계좌 관리 REST API (Spring Boot + H2)

설치 없이 **바로 실행되는** 완성 프로젝트입니다. H2 메모리 DB를 사용하므로 별도 DB 설치가 필요 없습니다.

## 실행 방법

### 방법 A. IntelliJ
1. `2-spring-boot` 폴더를 프로젝트로 엽니다 (pom.xml 을 Maven 프로젝트로 인식).
2. 의존성 다운로드가 끝나면 `BankApplication.java` 의 ▶ 실행.

### 방법 B. 터미널
```bash
cd 2-spring-boot
mvn spring-boot:run
```

> 첫 실행 시 인터넷에서 Spring/H2 라이브러리를 자동으로 내려받습니다.

## 실행하면

- 서버: http://localhost:8080/accounts
- H2 콘솔: http://localhost:8080/h2-console  (JDBC URL: `jdbc:h2:mem:bankdb`, 사용자 `sa`, 비밀번호 없음)
- 샘플 계좌 자동 생성: **110-0001(홍길동, 100,000원)**, **110-0002(김철수, 50,000원)**

## API 요약

| 기능 | 메서드 | URL |
|------|--------|-----|
| 계좌 목록 | GET | `/accounts` |
| 계좌 조회 | GET | `/accounts/{no}` |
| 계좌 개설 | POST | `/accounts` |
| 예금주 변경 | PUT | `/accounts/{no}` |
| 계좌 해지 | DELETE | `/accounts/{no}` |
| 입금 | POST | `/accounts/{no}/deposit` |
| 출금 | POST | `/accounts/{no}/withdraw` |
| 이체 | POST | `/accounts/transfer` |

`http/requests.http` 파일을 IntelliJ에서 열면 버튼 클릭으로 바로 테스트할 수 있습니다.

## 폴더 구조
```
2-spring-boot/
├─ pom.xml
├─ http/requests.http
└─ src/main/
   ├─ resources/application.properties   (H2 설정)
   └─ java/com/example/bank/
      ├─ BankApplication.java            (main)
      ├─ entity/Account.java             (@Entity)
      ├─ repository/AccountRepository.java (JpaRepository)
      ├─ service/AccountService.java     (@Service, @Transactional)
      ├─ controller/AccountController.java (@RestController)
      ├─ controller/GlobalExceptionHandler.java
      ├─ dto/ (record 4개)
      └─ config/DataInitializer.java     (샘플 데이터)
```
