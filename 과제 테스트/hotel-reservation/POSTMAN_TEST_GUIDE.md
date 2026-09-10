# Postman으로 호텔 예약 API 테스트하기

이 문서는 `hotel-reservation` API를 **Postman**으로 처음부터 끝까지 테스트하는 방법을 정리한 가이드입니다.

---

## 0. 사전 준비

1. 애플리케이션 실행
   ```bash
   cd hotel-reservation
   mvn spring-boot:run
   ```
   기동되면 콘솔에 `Started HotelReservationApplication ...` 이 뜨고, 샘플 객실 5개(101, 102, 201, 202, 301)가 자동 등록됩니다.

2. 기본 주소(Base URL): `http://localhost:8080`

3. Postman에서 요청을 만들 때 공통 설정
   - **POST 요청**은 `Body` 탭 → `raw` 선택 → 오른쪽 드롭다운을 **JSON** 으로 지정.
   - `Content-Type: application/json` 헤더는 위처럼 JSON을 선택하면 자동으로 붙습니다.

> 💡 날짜는 반드시 **미래 날짜**로 넣으세요. 체크인이 과거이면 400 오류가 납니다.
> 아래 예시의 `2026-10-01` 같은 날짜는 오늘 이후로 적절히 바꿔서 사용하면 됩니다.

---

## 1. (선택) 환경 변수 설정

매번 주소를 치기 번거로우면 Postman **Environment**에 변수를 만들어 두면 편합니다.

| 변수명 | 초기값 |
|--------|--------|
| `baseUrl` | `http://localhost:8080` |
| `reservationId` | (비워둠 — 예약 생성 후 자동 저장) |

이후 요청 URL에 `{{baseUrl}}/api/rooms` 처럼 사용합니다.

---

## 2. API별 테스트

### 2-1. 전체 객실 조회
- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/rooms`

**기대 응답 `200 OK`**
```json
[
  { "id": 1, "roomNumber": "101", "type": "SINGLE", "typeName": "싱글", "capacity": 1 },
  { "id": 2, "roomNumber": "102", "type": "SINGLE", "typeName": "싱글", "capacity": 1 },
  { "id": 3, "roomNumber": "201", "type": "DOUBLE", "typeName": "더블", "capacity": 2 },
  { "id": 4, "roomNumber": "202", "type": "TWIN",   "typeName": "트윈", "capacity": 2 },
  { "id": 5, "roomNumber": "301", "type": "SUITE",  "typeName": "스위트", "capacity": 4 }
]
```
> 여기서 예약에 사용할 `id` 값을 확인하세요. (아래 예시는 `roomId: 3` = 201호 사용)

---

### 2-2. 예약 가능 객실 조회
- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/rooms/available?checkIn=2026-10-01&checkOut=2026-10-03`
- **Params 탭**에서 넣어도 됩니다:
  - `checkIn` = `2026-10-01`
  - `checkOut` = `2026-10-03`

**기대 응답 `200 OK`** — 해당 기간에 예약이 없는 객실 목록. (아직 예약 전이면 5개 전부)

---

### 2-3. 예약 생성 ✅ (핵심)
- **Method**: `POST`
- **URL**: `{{baseUrl}}/api/reservations`
- **Body (raw / JSON)**
```json
{
  "roomId": 3,
  "guestName": "홍길동",
  "guestPhone": "010-1234-5678",
  "guestCount": 2,
  "checkInDate": "2026-10-01",
  "checkOutDate": "2026-10-03"
}
```

**기대 응답 `201 Created`**
```json
{
  "id": 1,
  "roomId": 3,
  "roomNumber": "201",
  "guestName": "홍길동",
  "guestPhone": "010-1234-5678",
  "guestCount": 2,
  "checkInDate": "2026-10-01",
  "checkOutDate": "2026-10-03",
  "nights": 2,
  "status": "RESERVED",
  "createdAt": "2026-09-09T05:00:00Z"
}
```

> **응답의 `id`를 기억하세요.** 조회/취소 테스트에 사용합니다.
>
> 💡 **자동 저장 팁**: 이 요청의 `Scripts` → `Post-response`(구버전은 `Tests`) 탭에 아래를 넣으면 `reservationId` 변수에 자동 저장됩니다.
> ```javascript
> pm.environment.set("reservationId", pm.response.json().id);
> ```

---

### 2-4. 예약 단건 조회
- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/reservations/1`  (또는 `{{baseUrl}}/api/reservations/{{reservationId}}`)

**기대 응답 `200 OK`** — 2-3과 동일한 예약 정보.

---

### 2-5. 예약 목록 조회
- **Method**: `GET`
- **전체**: `{{baseUrl}}/api/reservations`
- **투숙객별**: `{{baseUrl}}/api/reservations?guestPhone=010-1234-5678`

**기대 응답 `200 OK`** — 예약 배열(최근 생성순).

---

### 2-6. 예약 취소
- **Method**: `POST`
- **URL**: `{{baseUrl}}/api/reservations/1/cancel`  (또는 `.../{{reservationId}}/cancel`)

**기대 응답 `200 OK`**
```json
{ "id": 1, "status": "CANCELLED", "...": "..." }
```

---

## 3. 시나리오 테스트 (채점 포인트 확인)

아래 순서대로 실행하면 핵심 요구사항이 실제로 동작하는지 눈으로 확인할 수 있습니다.

### 시나리오 A — 기간 중복 예약 방지 (409)
1. **예약 생성** (2-3): `roomId=3`, `2026-10-01 ~ 2026-10-05` → `201 Created`
2. **겹치는 예약 생성**: 같은 `roomId=3`, `2026-10-03 ~ 2026-10-07`
   - **기대 응답 `409 Conflict`**
   ```json
   { "status": 409, "error": "Conflict",
     "message": "해당 기간(2026-10-03 ~ 2026-10-07)에는 201호가 이미 예약되어 있습니다." }
   ```

### 시나리오 B — 체크아웃 당일 이어서 예약 (허용)
1. 위 1번 예약(`~ 2026-10-05`)이 있는 상태에서
2. `roomId=3`, `2026-10-05 ~ 2026-10-08` 예약 생성
   - **기대 응답 `201 Created`** (체크아웃 날짜에 이어 입실하는 것은 중복이 아님)

### 시나리오 C — 취소 후 재예약 가능
1. 예약 하나 생성 후 **취소**(2-6)
2. 같은 객실·같은 기간으로 다시 예약 생성 → **`201 Created`**

### 시나리오 D — 예약 가능 객실에서 제외 확인
1. `roomId=3` 를 `2026-10-01 ~ 2026-10-03` 로 예약
2. `GET /api/rooms/available?checkIn=2026-10-02&checkOut=2026-10-03` 호출
   - **기대**: 응답 목록에 **201호(id=3)가 빠져 있음**

---

## 4. 오류 응답 테스트

| 테스트 | 요청 | 기대 코드 |
|--------|------|-----------|
| 과거 날짜 체크인 | `checkInDate`를 어제 날짜로 | `400 Bad Request` |
| 체크아웃 ≤ 체크인 | `checkIn=2026-10-05`, `checkOut=2026-10-05` | `400 Bad Request` |
| 수용 인원 초과 | 201호(정원 2)에 `guestCount: 3` | `400 Bad Request` |
| 필수값 누락 | `guestName` 없이 요청 | `400 Bad Request` |
| 없는 객실 | `roomId: 99999` | `404 Not Found` |
| 없는 예약 조회 | `GET /api/reservations/99999` | `404 Not Found` |
| 기간 중복 | 시나리오 A | `409 Conflict` |

**오류 응답 형식(공통)**
```json
{
  "timestamp": "2026-09-09T05:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "체크인 날짜는 오늘 이후여야 합니다: 2026-09-08"
}
```

---

## 5. Postman Collection 가져오기 (빠른 시작)

아래 JSON을 파일로 저장(`hotel-reservation.postman_collection.json`)한 뒤,
Postman에서 **Import → File** 로 불러오면 모든 요청이 폴더로 만들어집니다.
불러온 뒤 Collection 변수 `baseUrl`이 `http://localhost:8080` 인지 확인하고, 날짜만 미래로 바꿔 사용하세요.

```json
{
  "info": {
    "name": "Hotel Reservation API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    { "key": "baseUrl", "value": "http://localhost:8080" },
    { "key": "reservationId", "value": "" }
  ],
  "item": [
    {
      "name": "1. 전체 객실 조회",
      "request": { "method": "GET", "url": "{{baseUrl}}/api/rooms" }
    },
    {
      "name": "2. 예약 가능 객실 조회",
      "request": {
        "method": "GET",
        "url": {
          "raw": "{{baseUrl}}/api/rooms/available?checkIn=2026-10-01&checkOut=2026-10-03",
          "host": ["{{baseUrl}}"],
          "path": ["api", "rooms", "available"],
          "query": [
            { "key": "checkIn", "value": "2026-10-01" },
            { "key": "checkOut", "value": "2026-10-03" }
          ]
        }
      }
    },
    {
      "name": "3. 예약 생성",
      "event": [
        {
          "listen": "test",
          "script": {
            "exec": [
              "if (pm.response.code === 201) {",
              "  pm.environment.set('reservationId', pm.response.json().id);",
              "  pm.collectionVariables.set('reservationId', pm.response.json().id);",
              "}"
            ]
          }
        }
      ],
      "request": {
        "method": "POST",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"roomId\": 3,\n  \"guestName\": \"홍길동\",\n  \"guestPhone\": \"010-1234-5678\",\n  \"guestCount\": 2,\n  \"checkInDate\": \"2026-10-01\",\n  \"checkOutDate\": \"2026-10-03\"\n}"
        },
        "url": "{{baseUrl}}/api/reservations"
      }
    },
    {
      "name": "4. 예약 단건 조회",
      "request": { "method": "GET", "url": "{{baseUrl}}/api/reservations/{{reservationId}}" }
    },
    {
      "name": "5. 예약 목록(투숙객별)",
      "request": {
        "method": "GET",
        "url": {
          "raw": "{{baseUrl}}/api/reservations?guestPhone=010-1234-5678",
          "host": ["{{baseUrl}}"],
          "path": ["api", "reservations"],
          "query": [{ "key": "guestPhone", "value": "010-1234-5678" }]
        }
      }
    },
    {
      "name": "6. 예약 취소",
      "request": { "method": "POST", "url": "{{baseUrl}}/api/reservations/{{reservationId}}/cancel" }
    },
    {
      "name": "7. (오류) 겹치는 예약 - 409",
      "request": {
        "method": "POST",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"roomId\": 3,\n  \"guestName\": \"김철수\",\n  \"guestPhone\": \"010-9999-0000\",\n  \"guestCount\": 2,\n  \"checkInDate\": \"2026-10-02\",\n  \"checkOutDate\": \"2026-10-04\"\n}"
        },
        "url": "{{baseUrl}}/api/reservations"
      }
    }
  ]
}
```

---

## 6. 참고: H2 콘솔로 데이터 직접 확인

브라우저에서 `http://localhost:8080/h2-console` 접속 →
JDBC URL `jdbc:h2:mem:hoteldb`, 사용자명 `sa`, 비밀번호 공란으로 접속하면
`ROOM`, `RESERVATION` 테이블의 실제 저장 데이터를 SQL로 조회할 수 있습니다.
```sql
SELECT * FROM RESERVATION;
```
