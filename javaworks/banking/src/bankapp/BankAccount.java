package bankapp;

import java.util.ArrayList;

public class BankAccount {
	private String accountNumber;
	private String owner;
	private int balance;
	ArrayList<Transaction> transactions;
	
	public BankAccount(String accountNumber, String owner) {
		this.accountNumber = accountNumber;
		this.owner = owner;
		this.balance = 0;
		transactions = new ArrayList<>();
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public void displayInfo() {
		System.out.println("계좌 번호: " + accountNumber);
		System.out.println("계좌주: " + owner);
		System.out.println("잔고: " + balance);
	}
}
