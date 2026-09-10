package com.example.bank.service;

import com.example.bank.dao.AccountDao;
import com.example.bank.dao.Db;
import com.example.bank.model.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 업무 로직 계층. DAO를 사용해 DB를 다루고, 규칙과 트랜잭션을 담당한다.
 * (콘솔 H2 버전과 동일한 로직 — 웹에서도 그대로 재사용)
 */
public class BankService {

    private final AccountDao dao = new AccountDao();

    public Account createAccount(String owner, long initialBalance) {
        if (owner == null || owner.isBlank())
            throw new IllegalArgumentException("예금주 이름을 입력해야 합니다.");
        if (initialBalance < 0)
            throw new IllegalArgumentException("초기 잔액은 0원 이상이어야 합니다.");
        try (Connection conn = Db.getConnection()) {
            long seq = dao.count(conn) + 1;
            String no = String.format("110-%04d", seq);
            while (dao.findByNumber(conn, no) != null) {
                seq++;
                no = String.format("110-%04d", seq);
            }
            Account account = new Account(no, owner, initialBalance);
            dao.insert(conn, account);
            return account;
        } catch (SQLException e) {
            throw new RuntimeException("계좌 개설 실패: " + e.getMessage(), e);
        }
    }

    public Account getAccount(String accountNumber) {
        try (Connection conn = Db.getConnection()) {
            return require(conn, accountNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Account> getAllAccounts() {
        try (Connection conn = Db.getConnection()) {
            return dao.findAll(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deposit(String accountNumber, long amount) {
        if (amount <= 0) throw new IllegalArgumentException("입금액은 0보다 커야 합니다.");
        try (Connection conn = Db.getConnection()) {
            Account a = require(conn, accountNumber);
            dao.updateBalance(conn, accountNumber, a.getBalance() + amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void withdraw(String accountNumber, long amount) {
        if (amount <= 0) throw new IllegalArgumentException("출금액은 0보다 커야 합니다.");
        try (Connection conn = Db.getConnection()) {
            Account a = require(conn, accountNumber);
            if (amount > a.getBalance())
                throw new IllegalStateException("잔액이 부족합니다. (현재 잔액: " + a.getBalance() + "원)");
            dao.updateBalance(conn, accountNumber, a.getBalance() - amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 이체 — 하나의 트랜잭션(commit/rollback) */
    public void transfer(String from, String to, long amount) {
        if (from.equals(to)) throw new IllegalArgumentException("같은 계좌로는 이체할 수 없습니다.");
        if (amount <= 0) throw new IllegalArgumentException("이체 금액은 0보다 커야 합니다.");
        Connection conn = null;
        try {
            conn = Db.getConnection();
            conn.setAutoCommit(false);   // 트랜잭션 시작

            Account fromA = require(conn, from);
            Account toA = require(conn, to);
            if (amount > fromA.getBalance())
                throw new IllegalStateException("잔액이 부족합니다. (현재 잔액: " + fromA.getBalance() + "원)");

            dao.updateBalance(conn, from, fromA.getBalance() - amount);
            dao.updateBalance(conn, to,   toA.getBalance()   + amount);

            conn.commit();               // 둘 다 성공 → 확정
        } catch (RuntimeException e) {
            rollback(conn);
            throw e;
        } catch (SQLException e) {
            rollback(conn);
            throw new RuntimeException("이체 실패: " + e.getMessage(), e);
        } finally {
            close(conn);
        }
    }

    public void closeAccount(String accountNumber) {
        try (Connection conn = Db.getConnection()) {
            require(conn, accountNumber);
            dao.delete(conn, accountNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Account require(Connection conn, String no) throws SQLException {
        Account a = dao.findByNumber(conn, no);
        if (a == null) throw new IllegalArgumentException("존재하지 않는 계좌입니다: " + no);
        return a;
    }

    private void rollback(Connection conn) {
        try { if (conn != null) conn.rollback(); } catch (SQLException ignore) {}
    }

    private void close(Connection conn) {
        try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException ignore) {}
    }
}
