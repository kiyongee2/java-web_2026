package bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO(Data Access Object) — 계좌 테이블에 대한 SQL(JDBC)을 담당한다.
 *
 * [학습 포인트]
 * - PreparedStatement 로 ? 자리에 값을 바인딩한다(SQL 인젝션 방지 + 안전).
 * - 각 메서드가 Connection 을 매개변수로 받는다.
 *   → 이체처럼 여러 SQL을 "하나의 트랜잭션(같은 Connection)"으로 묶을 수 있다.
 */
public class AccountDao {

    /** 계좌 저장 (INSERT) */
    public void insert(Connection conn, Account a) throws SQLException {
        String sql = "INSERT INTO account(account_number, owner, balance) VALUES(?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getAccountNumber());
            ps.setString(2, a.getOwner());
            ps.setLong(3, a.getBalance());
            ps.executeUpdate();
        }
    }

    /** 계좌번호로 조회 (없으면 null) */
    public Account findByNumber(Connection conn, String accountNumber) throws SQLException {
        String sql = "SELECT * FROM account WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        }
    }

    /** 전체 조회 */
    public List<Account> findAll(Connection conn) throws SQLException {
        String sql = "SELECT * FROM account ORDER BY id";
        List<Account> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /** 잔액 변경 (UPDATE) */
    public void updateBalance(Connection conn, String accountNumber, long balance) throws SQLException {
        String sql = "UPDATE account SET balance = ? WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, balance);
            ps.setString(2, accountNumber);
            ps.executeUpdate();
        }
    }

    /** 계좌 삭제 (DELETE) */
    public void delete(Connection conn, String accountNumber) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("DELETE FROM account WHERE account_number = ?")) {
            ps.setString(1, accountNumber);
            ps.executeUpdate();
        }
    }

    /** 계좌 개수 (계좌번호 생성에 사용) */
    public long count(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM account");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    // ResultSet 한 행 → Account 객체로 변환
    private Account map(ResultSet rs) throws SQLException {
        return new Account(
                rs.getString("account_number"),
                rs.getString("owner"),
                rs.getLong("balance"));
    }
}
