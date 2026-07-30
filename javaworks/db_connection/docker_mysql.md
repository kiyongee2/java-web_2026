# Docker로 MySQL 설치 및 사용 가이드

db_connection 프로젝트에서 로컬에 MySQL을 직접 설치하는 대신, Docker로 MySQL을 실행하고 연동하는 방법을 정리한 문서입니다.

## 프로젝트 접속 정보

이 프로젝트(`UserDAO.java`, `DBTest.java`)는 아래 설정으로 DB에 연결합니다. Docker 구성도 이 값에 맞춰져 있습니다.

| 항목 | 값 |
|---|---|
| URL | `jdbc:mysql://localhost:3306/javadb` |
| DB 이름 | `javadb` |
| 사용자 | `javauser` |
| 비밀번호 | `pwjava` |
| 테이블 | `users` (id, user_id, password, name) |

---

## 1. Docker Desktop 설치

### 설치 전 확인

- **OS**: Windows 10 64비트(21H2 이상) 또는 Windows 11. Home / Pro / Enterprise / Education 모두 지원 (WSL 2 백엔드 사용)
- **하드웨어**: 64비트 CPU + 하드웨어 가상화 활성화(Intel VT-x / AMD-V), RAM 최소 4GB(8GB 권장), 여유 디스크 6GB 이상
- **관리자 권한** 필요

가상화가 켜져 있는지 확인: `작업 관리자 → 성능 → CPU → "가상화: 사용"`. 꺼져 있으면 BIOS/UEFI에서 활성화해야 합니다.

### CPU 아키텍처 확인

설치 파일을 잘못 받으면 "이 앱을 이 PC에서 실행할 수 없습니다" 오류가 납니다. 먼저 PowerShell에서 확인하세요.

```powershell
echo $env:PROCESSOR_ARCHITECTURE
```

- `AMD64` → Intel/AMD → **x86_64** 버전 다운로드 (대부분의 PC)
- `ARM64` → Snapdragon 등 → **Arm64** 버전 다운로드

### 설치 순서

1. 관리자 PowerShell에서 WSL 2 준비 후 재부팅
   ```powershell
   wsl --install
   ```
   (이미 설치돼 있으면 `wsl --update`)
2. <https://www.docker.com/products/docker-desktop/> 에서 CPU에 맞는 버전 다운로드
3. `Docker Desktop Installer.exe` 실행 → "Use WSL 2 instead of Hyper-V" 체크 후 설치
4. 재로그인 또는 재부팅
5. **Docker Desktop** 실행 → 우측 하단 고래 아이콘이 초록색이면 준비 완료

### 설치 확인

```bash
docker --version
docker run hello-world
```

`hello-world`가 정상 출력되면 성공입니다.

> 참고: 개인/소규모 사용은 무료이나, 일정 규모 이상 기업은 유료 구독 대상일 수 있습니다.

---

## 2. MySQL 실행

### 방법 A — docker compose (권장)

프로젝트 폴더에 있는 `docker-compose.yml` + `init.sql`을 사용하면 한 줄로 DB·계정·테이블이 모두 준비됩니다.

```bash
cd D:\스페이스시엘_EDU\2026년\javaworks\db_connection
docker compose up -d
```

자동으로 처리되는 것: **DB(javadb) 생성 → 계정(javauser) 생성 → users 테이블 생성 → 샘플 데이터 삽입**.

### 방법 B — docker run (단발성)

compose 없이 명령 한 줄로 실행할 수도 있습니다. (테이블은 별도로 생성해야 함)

```bash
docker run -d --name javadb-mysql ^
  -e MYSQL_ROOT_PASSWORD=rootpw ^
  -e MYSQL_DATABASE=javadb ^
  -e MYSQL_USER=javauser ^
  -e MYSQL_PASSWORD=pwjava ^
  -p 3306:3306 ^
  -v javadb-data:/var/lib/mysql ^
  mysql:8.0
```

(Windows CMD 기준 줄바꿈 `^`. PowerShell은 백틱, Git Bash/Mac은 `\`)

---

## 3. 포트 충돌 주의 ⚠️

로컬에 이미 MySQL이 설치돼 3306 포트를 쓰고 있으면 Docker MySQL과 충돌합니다. 둘 중 하나를 택하세요.

- **방법 A (권장)** — 로컬 MySQL을 끄고 Docker가 3306 사용 → **자바 코드 수정 불필요**
  ```cmd
  net stop MySQL80      :: 관리자 CMD에서 실행 (서비스 이름은 환경에 따라 다름)
  ```
  다시 켤 때: `net start MySQL80`
- **방법 B** — 로컬 MySQL을 켜둔 채 Docker는 3307 사용
  - `docker-compose.yml`의 `"3306:3306"`을 `"3307:3306"`으로 변경
  - 자바 URL도 `jdbc:mysql://localhost:3307/javadb`로 변경

---

## 4. 접속 및 데이터 확인

컨테이너 안의 MySQL에 직접 접속:

```bash
docker exec -it javadb-mysql mysql -u javauser -p
# 비밀번호: pwjava
```

```sql
USE javadb;
SHOW TABLES;
SELECT * FROM users;
```

이후 Eclipse에서 `TestUsers` / `DBTest`를 실행하면 코드 수정 없이 그대로 연결됩니다.

---

## 5. 자주 쓰는 명령어

### docker compose (프로젝트 폴더에서)

| 명령 | 설명 |
|---|---|
| `docker compose up -d` | 컨테이너 생성·시작 (백그라운드) |
| `docker compose stop` | 중지 |
| `docker compose start` | 재시작 |
| `docker compose logs -f` | 로그 실시간 확인 |
| `docker compose down` | 컨테이너 삭제 (데이터 볼륨은 유지) |
| `docker compose down -v` | 컨테이너 + 데이터 볼륨까지 삭제 (초기화) |

### 개별 컨테이너 제어

| 명령 | 설명 |
|---|---|
| `docker ps` | 실행 중인 컨테이너 목록 |
| `docker start javadb-mysql` | 시작 |
| `docker stop javadb-mysql` | 중지 |
| `docker logs javadb-mysql` | 로그 확인 |
| `docker rm -f javadb-mysql` | 삭제 |

---

## 6. 참고 사항

- **`init.sql`은 최초 1회만** 실행됩니다 (데이터 볼륨이 비어 있을 때). 이미 데이터가 있으면 다시 실행되지 않습니다. 완전히 초기화하려면 `docker compose down -v` 후 다시 `up`.
- 데이터는 `javadb-data` 볼륨에 저장되어 컨테이너를 지워도 유지됩니다.
- 문자셋은 utf8mb4로 설정되어 한글이 정상 저장됩니다.
- MySQL 커넥터(Connector/J) `.jar`는 자바 프로젝트의 Build Path에 포함되어 있어야 합니다.
