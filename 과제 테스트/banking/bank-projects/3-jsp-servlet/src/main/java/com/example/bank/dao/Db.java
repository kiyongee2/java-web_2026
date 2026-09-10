package com.example.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * H2 연결과 테이블 생성.
 *
 * [학습 포인트]
 * - 웹앱은 실행 위치(톰캣 bin)가 헷갈리므로, 파일 경로 문제 없는 "메모리 DB"를 쓴다.
 *   DB_CLOSE_DELAY=-1 → 서버(JVM)가 켜져 있는 동안 데이터 유지.
 * - 파일로 저장하려면 URL을 "jdbc:h2:~/bankdb" (사용자 홈)처럼 바꾸면 된다.
 */
public class Db {

    static final String URL = "jdbc:h2:mem:bankdb;DB_CLOSE_DELAY=-1";
    static final String USER = "sa";
    static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void init() {
        String ddl = """
            CREATE TABLE IF NOT EXISTS account (
                id             BIGINT AUTO_INCREMENT PRIMARY KEY,
                account_number VARCHAR(20) NOT NULL UNIQUE,
                owner          VARCHAR(50) NOT NULL,
                balance        BIGINT      NOT NULL
            )
            """;
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.execute(ddl);
        } catch (SQLException e) {
            throw new RuntimeException("DB 초기화 실패: " + e.getMessage(), e);
        }
    }
}
