package com.sudo.banking.entity;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    private String owner;

    private long balance;

    protected Account(){}

    public Account(String accountNumber, String owner, long initialBalance){
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialBalance;
    }

    public void deposit(long amount){
        if(amount <= 0){
            throw new IllegalArgumentException("입금액은 0보다 커야합니다.");
        }
        this.balance += amount;
    }

    public void withdraw(long amount){
        if(amount <= 0){
            throw new IllegalArgumentException("입금액은 0보다 커야합니다.");
        }
        if(amount > this.balance){
            throw new IllegalStateException("잔액이 부족합니다. (현재 잔액: " + this.balance + "원)");
        }
        this.balance -= amount;
    }

    // Getter(JSON 응답 변환에 사용)

    public Long getId() {return id;}
    public String getAccountNumber() {return accountNumber;}
    public String getOwner() {return owner;}
    public long getBalance() {return balance;}
}
