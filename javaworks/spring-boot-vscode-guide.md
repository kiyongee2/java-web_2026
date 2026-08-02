# VS Code에서 Spring Boot 사용하기

이 문서는 이 워크스페이스(JDK 21, Maven 3.9.11 설치됨) 기준으로 VS Code에서 Spring Boot 프로젝트를 생성·실행·디버깅하는 방법을 정리한 가이드입니다.

## 1. 필요한 확장(Extension) 설치

VS Code 좌측 Extensions(Ctrl+Shift+X)에서 아래를 검색해 설치합니다. 한 번에 설치하려면 **"Spring Boot Extension Pack"**만 설치해도 나머지가 함께 설치됩니다.

| 확장 이름 | 역할 |
|---|---|
| Extension Pack for Java | Java 언어 지원(문법 체크, 자동완성, 디버깅) |
| Spring Boot Extension Pack | 아래 3개를 묶은 패키지 |
| ├ Spring Boot Tools | `application.properties`/`yml` 자동완성, 어노테이션 지원 |
| ├ Spring Initializr Java Support | 프로젝트 생성 마법사 |
| └ Spring Boot Dashboard | 실행/중지 대시보드 |

> 이 워크스페이스에는 이미 `vscjava.vscode-spring-initializr`, `vscjava.vscode-spring-boot-dashboard`가 설치되어 있습니다. `Spring Boot Tools`만 추가로 확인하면 됩니다.

## 2. 새 프로젝트 생성

### 방법 A: VS Code 명령 팔레트 사용 (권장)

1. `Ctrl+Shift+P` → **"Spring Initializr: Create a Maven Project"** 선택
2. 순서대로 선택:
   - Language: **Java**
   - Group Id: 예) `com.example`
   - Artifact Id: 프로젝트 이름, 예) `demo`
   - Packaging: **Jar**
   - Java 버전: **21** (설치된 JDK와 일치)
   - Spring Boot 버전: 최신 안정 버전 선택
3. Dependencies 검색창에서 필요한 라이브러리 추가 후 Enter
   - 예: `Spring Web`, `Spring Data JPA`, `MySQL Driver`, `Lombok`, `Thymeleaf` 등
4. 프로젝트를 저장할 폴더 선택 → 생성 완료 후 "Open"

### 방법 B: 웹에서 생성 후 다운로드

1. https://start.spring.io 접속
2. 옵션 설정(Maven, Java 21 등) 후 Dependencies 추가
3. **Generate**로 zip 다운로드 → 압축 해제 후 VS Code에서 폴더 열기

## 3. 프로젝트 구조

```
demo/
├─ src/
│  ├─ main/
│  │  ├─ java/com/example/demo/
│  │  │  └─ DemoApplication.java   ← main() 진입점
│  │  └─ resources/
│  │     ├─ application.properties ← 설정 파일
│  │     ├─ static/                ← 정적 리소스(css, js)
│  │     └─ templates/             ← 뷰 템플릿(thymeleaf 등)
│  └─ test/java/...                ← 테스트 코드
├─ pom.xml                         ← Maven 의존성 관리
```

## 4. 실행 방법

### 방법 A: Spring Boot Dashboard 사용

1. 좌측 사이드바에서 스프링 아이콘(Spring Boot Dashboard) 클릭
2. 프로젝트 목록에서 실행할 앱 선택 후 ▶(재생) 버튼 클릭
3. 중지는 ■ 버튼

### 방법 B: 코드 에디터에서 직접 실행

`DemoApplication.java`를 열고 `main` 메서드 위에 표시되는 **"Run"** 링크 클릭 (또는 `F5`로 디버깅 모드 실행)

### 방법 C: 터미널에서 Maven 명령 사용

```bash
mvn spring-boot:run
```

기본적으로 `http://localhost:8080` 에서 애플리케이션이 실행됩니다.

## 5. 디버깅

- 코드 좌측 라인 번호 옆을 클릭해 브레이크포인트 설정
- `F5` 또는 Run and Debug 패널에서 **"Debug"**로 실행하면 브레이크포인트에서 멈춤
- 변수 확인, 호출 스택 확인 가능 (Java 확장 기본 제공)

## 6. 자주 쓰는 설정 (`application.properties`)

```properties
# 포트 변경
server.port=8081

# 데이터베이스 연결 (MySQL 예시)
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=1234

# JPA 로그로 SQL 확인
spring.jpa.show-sql=true
```

## 7. 자주 발생하는 문제

| 증상 | 원인/해결 |
|---|---|
| `Port 8080 already in use` | 이미 실행 중인 프로세스가 있음 → `application.properties`에서 `server.port` 변경하거나 기존 프로세스 종료 |
| 의존성 추가 후 인식 안 됨 | `pom.xml` 저장 후 우클릭 → **"Maven: Reload Project"** (또는 하단 알림의 "Reload" 클릭) |
| Java 버전 불일치 오류 | `pom.xml`의 `<java.version>`과 VS Code가 사용하는 JDK가 같은지 확인 (이 환경은 JDK 21) |
| Dashboard에 프로젝트가 안 보임 | 워크스페이스 폴더가 `pom.xml`을 포함한 루트인지 확인 |

## 8. 유용한 단축키

| 동작 | 단축키 |
|---|---|
| 명령 팔레트 열기 | `Ctrl+Shift+P` |
| 디버그 실행 | `F5` |
| 디버그 없이 실행 | `Ctrl+F5` |
| 자동 임포트 정리 | `Shift+Alt+O` |
