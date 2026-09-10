package com.example.bank.dao;

import com.example.bank.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO(Model) — 계좌 테이블에 대한 JDBC(SQL)를 담당.
 * 각 메서드가 Connection을 매개변수로 받아, 이체를 하나의 트랜잭션으로 묶을 수 있게 한다.
 */
public class AccountDao {

    public void insert(Connection conn, Account a) throws SQLException {
        String sql = "INSERT INTO account(account_number, owner, balance) VALUES(?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getAccountNumber());
            ps.setString(2, a.getOwner());
            ps.setLong(3, a.getBalance());
            ps.executeUpdate();
        }
    }

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

    public List<Account> findAll(Connection conn) throws SQLException {
        String sql = "SELECT * FROM account ORDER BY id";
        List<Account> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void updateBalance(Connection conn, String accountNumber, long balance) throws SQLException {
        String sql = "UPDATE account SET balance = ? WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, balance);
            ps.setString(2, accountNumber);
            ps.executeUpdate();
        }
    }

    public void delete(Connection conn, String accountNumber) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("DELETE FROM account WHERE account_number = ?")) {
            ps.setString(1, accountNumber);
            ps.executeUpdate();
        }
    }

    public long count(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM account");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private Account map(ResultSet rs) throws SQLException {
        return new Account(
                rs.getString("account_number"),
                rs.getString("owner"),
                rs.getLong("balance"));
    }
}
