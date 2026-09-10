# 호텔 투숙객 예약 API

호텔 객실 예약 기능을 제공하는 REST API입니다. 특정 기간에 대해 객실 예약을 생성/조회/취소하고,
**기간 중복 예약을 방지**하며 **동시 예약 요청에도 정합성**을 보장합니다.

## 기술 스택
- Java 21, Spring Boot 3.3.5
- Spring Web, Spring Data JPA, Bean Validation
- H2 인메모리 데이터베이스 (별도 설치 불필요)
- 테스트: JUnit 5 + Spring MockMvc + AssertJ

## 실행 방법
```bash
mvn spring-boot:run
```
- 기동 시 샘플 객실 5개(101, 102, 201, 202, 301)가 자동 등록됩니다.
- H2 콘솔: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:hoteldb`)

## 테스트 실행
```bash
mvn test
```

## 핵심 설계

### 1. 예약 기간 중복 방지 (체크아웃일 배타)
예약 기간은 반열린구간 **[checkIn, checkOut)** 으로 해석합니다(호텔 관례: 체크아웃 당일은
점유하지 않음). 두 구간 `[a,b)`, `[c,d)`는 **`a < d && c < b`** 일 때 겹칩니다.
따라서 어떤 예약의 **체크아웃 날짜에 다른 손님이 체크인**하는 것은 중복이 아닙니다.
판정 로직은 `Reservation.overlaps(...)` 도메인 메서드에 캡슐화되어 있습니다.

### 2. 동시성 처리 (중복 예약 방지)
동일 객실·동일 기간에 여러 예약 요청이 **동시에** 들어와도 하나만 성공해야 합니다.
예약 생성 시 대상 객실 행에 **비관적 쓰기 락(`SELECT ... FOR UPDATE`)** 을 걸어
동일 객실에 대한 예약을 직렬화한 뒤, 락을 확보한 상태에서 중복 검사 → 저장을 한 트랜잭션으로
수행합니다. (`RoomRepository.findByIdForUpdate` + `@Transactional`)

### 3. 취소
취소는 데이터를 삭제하지 않고 상태를 `CANCELLED`로 변경합니다(이력 보존).
취소된 예약은 객실을 점유하지 않으므로, 같은 기간을 다시 예약할 수 있습니다.

## 요구사항 대응
| 항목 | 구현 |
|------|------|
| 투숙객 예약 생성 | `POST /api/reservations` (투숙객명/연락처/인원/기간) |
| 예약 조회 | `GET /api/reservations/{id}`, `GET /api/reservations?guestPhone=` |
| 예약 취소 | `POST /api/reservations/{id}/cancel` |
| 예약 가능 객실 조회 | `GET /api/rooms/available?checkIn=&checkOut=` |
| 기간 중복 예약 방지 | 반열린구간 겹침 판정 + 409 응답 |
| 동시성 정합성 | 객실 비관적 락 + 트랜잭션 |
| 입력 검증 | 과거 날짜/역전 기간/수용인원 초과 → 400 |
| 테스트 코드 | 도메인 단위 + 서비스/ API 통합 + 동시성 테스트 |

## API 명세

### 예약 생성
```
POST /api/reservations
Content-Type: application/json

{
  "roomId": 3,
  "guestName": "홍길동",
  "guestPhone": "010-1234-5678",
  "guestCount": 2,
  "checkInDate": "2026-09-20",
  "checkOutDate": "2026-09-22"
}
```
응답 `201 Created`
```json
{
  "id": 1,
  "roomId": 3,
  "roomNumber": "201",
  "guestName": "홍길동",
  "guestPhone": "010-1234-5678",
  "guestCount": 2,
  "checkInDate": "2026-09-20",
  "checkOutDate": "2026-09-22",
  "nights": 2,
  "status": "RESERVED",
  "createdAt": "2026-09-09T05:00:00Z"
}
```

### 예약 조회 / 목록
```
GET /api/reservations/{id}
GET /api/reservations                       # 전체 (최근순)
GET /api/reservations?guestPhone=010-1234-5678   # 투숙객별
```

### 예약 취소
```
POST /api/reservations/{id}/cancel   → 200, status: "CANCELLED"
```

### 객실 조회 / 예약 가능 객실
```
GET /api/rooms
GET /api/rooms/available?checkIn=2026-09-20&checkOut=2026-09-22
```

### 오류 응답
| 상황 | 코드 |
|------|------|
| 잘못된 입력(과거 날짜, 역전 기간, 인원 초과, 필수값 누락) | `400 Bad Request` |
| 존재하지 않는 객실/예약 | `404 Not Found` |
| 해당 기간에 이미 예약됨 | `409 Conflict` |

## 예시 (curl)
```bash
# 예약 가능 객실 조회
curl "http://localhost:8080/api/rooms/available?checkIn=2026-09-20&checkOut=2026-09-22"

# 예약
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{"roomId":3,"guestName":"홍길동","guestPhone":"010-1234-5678","guestCount":2,"checkInDate":"2026-09-20","checkOutDate":"2026-09-22"}'

# 취소
curl -X POST http://localhost:8080/api/reservations/1/cancel
```

## 프로젝트 구조
```
src/main/java/com/spacecl/hotel/
├── HotelReservationApplication.java
├── domain/
│   ├── Room.java, RoomType.java
│   ├── Reservation.java          # overlaps() 중복 판정 규칙 포함
│   └── ReservationStatus.java
├── repository/
│   ├── RoomRepository.java       # findByIdForUpdate (비관적 락)
│   └── ReservationRepository.java
├── service/
│   ├── ReservationService.java   # 예약 생성/취소, 동시성·중복 방지
│   └── RoomService.java          # 객실/가용성 조회
├── controller/  ReservationController.java, RoomController.java
├── dto/         ReservationRequest, ReservationResponse, RoomResponse
├── exception/   4종 예외 + GlobalExceptionHandler
└── config/      AppConfig(Clock), DataInitializer(샘플 데이터)

src/test/java/com/spacecl/hotel/
├── ReservationOverlapTest.java   # 기간 중복 판정 순수 단위 테스트
├── ReservationServiceTest.java   # 서비스 통합 + 동시성 테스트
└── ReservationApiTest.java       # HTTP API 통합 테스트
```
