package com.example.bank.model;

/**
 * 계좌 데이터 (테이블 한 행). JSP에서 EL(${a.owner})로 읽으므로 getter가 필요하다.
 */
public class Account {

    private final String accountNumber;
    private final String owner;
    private final long balance;

    public Account(String accountNumber, String owner, long balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwner()         { return owner; }
    public long getBalance()         { return balance; }
}
