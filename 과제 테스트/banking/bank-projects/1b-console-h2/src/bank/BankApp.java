package bank;

import java.util.Scanner;

/**
 * 프로그램 시작점(main). 콘솔 메뉴로 은행 기능을 사용한다.
 * 메모리 버전과 화면·메뉴는 똑같지만, 데이터가 H2 DB에 저장되어 프로그램을 껐다 켜도 유지된다.
 */
public class BankApp {

    private static final BankService bank = new BankService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== 스페이스시엘 은행 시스템 (콘솔 + H2 DB) ===");

        Db.init();       // 테이블 생성
        seedIfEmpty();   // 계좌가 없으면 샘플 2개 생성

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> createAccount();
                    case "2" -> deposit();
                    case "3" -> withdraw();
                    case "4" -> checkBalance();
                    case "5" -> transfer();
                    case "6" -> printAllAccounts();
                    case "0" -> {
                        System.out.println("프로그램을 종료합니다. (데이터는 DB에 저장됨)");
                        return;
                    }
                    default -> System.out.println("잘못된 선택입니다.");
                }
            } catch (Exception e) {
                System.out.println("[오류] " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void seedIfEmpty() {
        if (bank.getAllAccounts().isEmpty()) {
            bank.createAccount("홍길동", 100000);
            bank.createAccount("김철수", 50000);
            System.out.println("(샘플 계좌 생성: 110-0001 홍길동, 110-0002 김철수)");
        }
    }

    private static void printMenu() {
        System.out.println("----------------------------------------");
        System.out.println("1.계좌개설  2.입금  3.출금  4.잔액조회  5.이체  6.전체목록  0.종료");
        System.out.print("메뉴 선택 > ");
    }

    private static void createAccount() {
        System.out.print("예금주 이름: ");
        String owner = scanner.nextLine().trim();
        System.out.print("초기 입금액: ");
        long initial = Long.parseLong(scanner.nextLine().trim());
        System.out.println("계좌 개설 완료 → " + bank.createAccount(owner, initial));
    }

    private static void deposit() {
        System.out.print("계좌번호: ");
        String acc = scanner.nextLine().trim();
        System.out.print("입금액: ");
        long amount = Long.parseLong(scanner.nextLine().trim());
        System.out.println(amount + "원 입금 완료 → " + bank.deposit(acc, amount));
    }

    private static void withdraw() {
        System.out.print("계좌번호: ");
        String acc = scanner.nextLine().trim();
        System.out.print("출금액: ");
        long amount = Long.parseLong(scanner.nextLine().trim());
        System.out.println(amount + "원 출금 완료 → " + bank.withdraw(acc, amount));
    }

    private static void checkBalance() {
        System.out.print("계좌번호: ");
        String acc = scanner.nextLine().trim();
        System.out.println(bank.getAccount(acc));
    }

    private static void transfer() {
        System.out.print("보내는 계좌번호: ");
        String from = scanner.nextLine().trim();
        System.out.print("받는 계좌번호: ");
        String to = scanner.nextLine().trim();
        System.out.print("이체 금액: ");
        long amount = Long.parseLong(scanner.nextLine().trim());
        bank.transfer(from, to, amount);
        System.out.println("이체 완료!");
        System.out.println("  보낸 계좌 → " + bank.getAccount(from));
        System.out.println("  받은 계좌 → " + bank.getAccount(to));
    }

    private static void printAllAccounts() {
        System.out.println("=== 전체 계좌 목록 ===");
        bank.getAllAccounts().forEach(acc -> System.out.println("  " + acc));
    }
}
