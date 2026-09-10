package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * H2 데이터베이스 연결과 초기화를 담당한다.
 *
 * [학습 포인트]
 * - JDBC는 DriverManager.getConnection(URL, 사용자, 비번)으로 DB에 연결한다.
 * - 아래 URL은 "파일 모드" H2 → 프로젝트 폴더에 bankdb.mv.db 파일로 저장된다.
 *   그래서 프로그램을 껐다 켜도 데이터가 유지된다(영구 저장).
 */
public class Db {

    // 파일 DB: 현재 폴더에 bankdb.mv.db 로 저장 (데이터 유지)
    // 메모리로 쓰고 싶으면: "jdbc:h2:mem:bankdb;DB_CLOSE_DELAY=-1"
    static final String URL = "jdbc:h2:./bankdb";
    static final String USER = "sa";
    static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /** account 테이블이 없으면 생성한다. */
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
