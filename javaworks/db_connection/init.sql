-- db_connection 프로젝트 초기화 스크립트
-- docker compose up -d 실행 시 최초 1회 자동 실행됩니다.
-- (users 테이블이 없을 때만 생성)

USE javadb;

CREATE TABLE IF NOT EXISTS users (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    user_id  VARCHAR(50)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    name     VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 샘플 데이터 (필요 없으면 아래 3줄 삭제)
INSERT INTO users (user_id, password, name) VALUES
    ('hong',  '1234', 'sudo-soft'),

    