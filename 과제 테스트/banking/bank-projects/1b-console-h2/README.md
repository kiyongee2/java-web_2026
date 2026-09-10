# 은행 계좌 관리 (콘솔 + H2 DB / 순수 Java JDBC)

메모리(Map) 버전과 화면·메뉴는 같지만, 데이터를 **H2 데이터베이스에 영구 저장**합니다.
Spring 없이 **순수 Java + JDBC**로 DB를 다루는 방법을 배웁니다.

## 실행 준비 — H2 드라이버 넣기
이 프로젝트는 H2 드라이버 jar가 필요합니다. `lib/` 폴더의 안내(`여기에_h2.jar_넣기.txt`)대로
`h2-2.4.240.jar`를 내려받아 `lib/`에 넣으세요.
- 직접 링크: https://repo1.maven.org/maven2/com/h2database/h2/2.4.240/h2-2.4.240.jar

> ⚠️ **IntelliJ 사용 시**: `lib/`에 jar만 넣으면 인식되지 않습니다. `File > Project Structure`
> (Ctrl+Alt+Shift+S) > `Libraries` > `+` > `Java` > `lib\h2-2.4.240.jar` 선택 후 모듈에 추가하세요.
> 등록하지 않으면 `ClassNotFoundException: org.h2.Driver` / `No suitable driver found` 오류가 납니다.

## 실행
프로젝트 루트(`1b-console-h2`)에서:

```bash
# Windows
javac -d out src\bank\*.java
java -cp "out;lib\h2-2.4.240.jar" bank.BankApp

# Mac / Linux
javac -d out src/bank/*.java
java -cp "out:lib/h2-2.4.240.jar" bank.BankApp
```

실행하면 `bankdb.mv.db` 파일이 생기고, 처음 한 번 샘플 계좌(110-0001 홍길동, 110-0002 김철수)가
만들어집니다. **프로그램을 껐다 켜도 데이터가 남아 있습니다.**

## 구조
```
1b-console-h2/
├─ lib/                      (h2-*.jar 를 여기에)
└─ src/bank/
   ├─ Account.java           계좌 데이터 (테이블 한 행)
   ├─ Db.java                DB 연결 + 테이블 생성
   ├─ AccountDao.java        JDBC CRUD (PreparedStatement)
   ├─ BankService.java       업무 로직 + 이체 트랜잭션(commit/rollback)
   └─ BankApp.java           콘솔 메뉴 (main)
```

## 세 버전 비교
| 버전 | 저장 위치 | DB 접근 방식 |
|------|-----------|--------------|
| 1-console | 메모리(Map) | 없음 (종료 시 사라짐) |
| **1b-console-h2** | **H2 파일 DB** | **순수 JDBC** |
| 2-spring-boot | H2 DB | Spring Data JPA |

같은 은행 로직이 "저장 방법"만 바뀌며 발전하는 과정을 보여줍니다.
