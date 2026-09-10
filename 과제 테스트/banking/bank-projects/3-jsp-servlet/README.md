# 은행 계좌 관리 (JSP + Servlet + H2) — STS5

Spring 이전의 전통적인 **Model 2 MVC** 웹앱입니다.
`Servlet`(Controller) + `JSP`(View) + `DAO`(Model) + `H2`(DB) 구조로, 순수 Jakarta EE로 만들었습니다.

> ⚙️ 개발 환경: **STS5(이클립스 기반) + Apache Tomcat v10.1 + JDK 21** (Jakarta EE 10, `jakarta.servlet.*`)
> 프로젝트 형식: **Dynamic Web Project** (Maven 아님)

## 구조 (Dynamic Web Project)
```
3-jsp-servlet/
└─ src/main/
   ├─ java/com/example/bank/
   │  ├─ model/Account.java          Model — 데이터
   │  ├─ dao/Db.java                 DB 연결 + 테이블 생성
   │  ├─ dao/AccountDao.java         Model — JDBC(SQL)
   │  ├─ service/BankService.java    업무 로직 + 이체 트랜잭션
   │  ├─ controller/AccountServlet.java   Controller — @WebServlet("/accounts")
   │  └─ listener/DbInitListener.java     시작 시 테이블·샘플 생성
   └─ webapp/
      ├─ index.jsp                   /accounts 로 리다이렉트
      ├─ css/style.css
      └─ WEB-INF/
         ├─ web.xml
         ├─ lib/h2-2.4.240.jar       ← H2 드라이버(포함됨). Build Path에 추가
         └─ views/accounts.jsp       View — 화면(스크립트릿/표현식)
```

## STS5에서 실행하기

> 1장 교안의 "개발 환경 구축"이 끝나 있다고 가정합니다 (STS5 + Tomcat v10.1 + JDK 21, UTF-8 인코딩).

### 방법 A — 새 Dynamic Web Project 만들고 소스 넣기 (권장, 1장 방식)
1. `File → New → Dynamic Web Project`
   - 프로젝트 이름: `bank` (원하는 이름)
   - **Target runtime: Apache Tomcat v10.1**
   - 다음 진행 → **"Generate web.xml deployment descriptor" 체크** → Finish
2. 제공된 소스를 프로젝트에 복사
   - `src/main/java/com/example/bank/...` 의 자바 파일들 → 프로젝트의 `src/main/java` (소스 폴더)
   - `src/main/webapp/` 의 `index.jsp`, `css/`, `WEB-INF/views/accounts.jsp`, `WEB-INF/web.xml` → 프로젝트의 `webapp`(웹 콘텐츠) 아래 같은 위치
3. **H2 드라이버 등록**: `WEB-INF/lib/h2-2.4.240.jar` 를 프로젝트의 `webapp/WEB-INF/lib` 에 넣기
   (WEB-INF/lib 에 있으면 배포 시 자동 인식됨. 안 잡히면 프로젝트 우클릭 → `Build Path → Configure Build Path → Libraries → Add JARs`)
4. **실행**: 프로젝트(또는 `index.jsp`) 우클릭 → `Run As → Run on Server` → Tomcat v10.1 선택 → Finish
5. 브라우저: **http://localhost:8080/bank/**
   - 샘플 계좌 110-0001(홍길동), 110-0002(김철수)가 자동 생성됨

### 방법 B — 이 폴더를 그대로 임포트
`File → Import → General → Existing Projects into Workspace` 로 이 폴더를 열 수도 있습니다.
서버 런타임(Tomcat v10.1)이 안 잡히면 프로젝트 우클릭 → `Properties → Targeted Runtimes` 에서 지정하세요.

## 참고
- **컨텍스트 경로(context root)**: 배포 이름이 곧 URL 경로입니다. `/bank` 로 접속하려면 프로젝트 이름/컨텍스트를 `bank` 로 두세요. 루트(`/`)로 바꾸려면 서버의 `Modules → Edit` 에서 변경합니다.
- **한글 깨짐**: 서블릿에서 `request.setCharacterEncoding("UTF-8")` 처리 완료. STS 인코딩도 UTF-8(1장 참고).
- **H2 데이터**: 메모리 DB(`jdbc:h2:mem:bankdb`)라 서버를 끄면 초기화됩니다. 파일 저장은 `dao/Db.java`의 URL을 `jdbc:h2:~/bankdb` 로 변경.
- **JSTL 미사용**: 화면은 1장에서 배운 **스크립트릿(`<% %>`)·표현식(`<%= %>`)**으로 작성해, 별도 JSTL 라이브러리가 필요 없습니다.

## 다른 버전과의 관계
| 버전 | 구조 | IDE / 실행 |
|------|------|-----------|
| **3-jsp-servlet** | 순수 Servlet/JSP (Model 2) | **STS5 + Tomcat (Run on Server)** |
| 2-spring-boot | Spring MVC + REST | IntelliJ / `mvn spring-boot:run` |
| 2b-spring-thymeleaf | Spring MVC + Thymeleaf | IntelliJ / `mvn spring-boot:run` |

Spring이 자동으로 해주던 것(요청 매핑·DI·뷰 렌더링)을 **직접** 하는 게 이 버전입니다.
