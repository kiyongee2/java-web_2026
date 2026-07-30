package bankapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankMain {
	static List<BankAccount> accountList = new ArrayList<>();
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		boolean run = true;
		while(run) {
			System.out.println("==================================================");
			System.out.println("1. 계좌 생성 | 2. 예금 | 3. 출금 | 4. 계좌 검색 | 5. 종료");
			System.out.println("==================================================");
			System.out.print("선택> ");
			
			int choice = Integer.parseInt(scan.nextLine());
			
			switch(choice) {
			case 1: // 계좌 생성 
				createAccount();
				break;	
			case 2: // 예금 
				deposit();
				break;
			case 3: // 출금 
				break;
			case 4: // 계좌 목록 
				getAccountList();
				break;
			case 5: // 프로그램 종료
				System.out.println("프로그램을 종료합니다.");
				run = false;
				break;
			default:
				System.out.println("지원되지 않는 기능입니다.");
				break;
			}
		}
	}
	
	public static void createAccount() {
		System.out.println("==================================================");
		System.out.println("                   계  좌  생  성                   ");
		System.out.println("==================================================");
		while(true) {
			System.out.println("계좌 번호 입력: ");
			String accNum = scan.nextLine();
			
			System.out.println("계좌주 입력: ");
			String name = scan.nextLine();
			
			BankAccount newAccount = new BankAccount(accNum, name);
			accountList.add(newAccount);
			System.out.println("계좌가 생성되었습니다.(계좌번호: " + accNum + ")");
			break;
		}
	}
	
	public static void getAccountList() {
		for(int i = 0; i < accountList.size(); i++) {
			BankAccount account = accountList.get(i);
			account.displayInfo();
		}
	}
	
	// 계좌 검색
	private static BankAccount searchAccount(String accNum) {
		BankAccount account = null;
		for(int i = 0; i < accountList.size(); i++) {
			String dbAccNum = accountList.get(i).getAccountNumber();
			if(dbAccNum.equals(accNum)) {
				account = accountList.get(i);
				break;
			}
		}
		return account;
	}
	
	public static void deposit() {
		while(true) {
			System.out.println("계좌 번호 입력: ");
			String accNum = scan.nextLine();
			
			System.out.print("입금액 입력: ");
			int amount = Integer.parseInt(scan.nextLine());
			
			if(searchAccount(accNum) != null) {
				BankAccount account = searchAccount(accNum);
				account.setBalance(account.getBalance() + amount);
				System.out.println("입금이 정상 처리 되었습니다. 현재 잔액: " + account.getBalance());
				break;
			}
		}
	}
}
