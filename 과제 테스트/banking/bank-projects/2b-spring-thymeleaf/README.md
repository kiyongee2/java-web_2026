# 은행 계좌 관리 웹앱 (Spring Boot + Thymeleaf + H2)

REST(JSON) 버전과 달리, **Thymeleaf로 서버가 HTML 화면을 그려서** 응답하는 웹앱입니다.
브라우저에서 폼으로 계좌 개설·입금·출금·이체를 처리합니다. H2 메모리 DB라 바로 실행됩니다.

## 실행
```bash
cd 2b-spring-thymeleaf
mvn spring-boot:run
```
또는 IntelliJ에서 `BankApplication` 실행 → 브라우저에서 **http://localhost:8080** 접속.

실행하면 샘플 계좌 110-0001(홍길동), 110-0002(김철수)가 자동 생성됩니다.

## 화면 기능
- 계좌 목록(표)
- 계좌 개설 / 입금 / 출금 / 이체 (폼)
- 계좌 해지 (표의 "해지" 버튼)
- 결과·오류 메시지 표시(잔액 부족 등)
- H2 콘솔: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:bankdb`, 사용자 `sa`)

## REST 버전과 무엇이 다른가
| 구분 | REST (2-spring-boot) | Thymeleaf (이 프로젝트) |
|------|----------------------|--------------------------|
| 컨트롤러 | `@RestController` | `@Controller` |
| 반환값 | 데이터(JSON) | 뷰 이름(html) → 서버가 화면 렌더링 |
| 화면 | 없음(Postman/curl) | 브라우저에서 폼으로 조작 |
| 결과 전달 | 응답 JSON | `Model` + flash 메시지, redirect(PRG) |

**엔티티·리포지토리·서비스(업무 로직)는 REST 버전과 100% 동일**합니다.
바뀐 것은 컨트롤러와 화면(templates) 뿐입니다.

## 구조
```
2b-spring-thymeleaf/
├─ pom.xml                                   (web + thymeleaf + data-jpa + H2)
└─ src/main/
   ├─ resources/
   │  ├─ application.properties              (H2 + thymeleaf)
   │  ├─ templates/accounts.html             (Thymeleaf 화면)
   │  └─ static/css/style.css                (스타일)
   └─ java/com/example/bank/
      ├─ BankApplication.java
      ├─ entity/Account.java                 (@Entity)
      ├─ repository/AccountRepository.java    (JpaRepository)
      ├─ service/AccountService.java          (@Service, @Transactional)
      ├─ controller/AccountViewController.java (@Controller — 뷰 반환)
      └─ config/DataInitializer.java          (샘플 계좌)
```
