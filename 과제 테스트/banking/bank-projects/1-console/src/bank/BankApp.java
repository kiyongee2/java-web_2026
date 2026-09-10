package bank;

import java.util.Scanner;

/**
 * 프로그램 시작점(main). 콘솔 메뉴로 은행 기능을 사용해 봅니다.
 *
 * [학습 포인트]
 * - 실무 서버에서는 사용자의 요청을 "화면/앱/웹"이 받아서 서버로 전달합니다.
 * - 이 콘솔 메뉴가 바로 그 "요청을 받는 부분" 역할을 합니다.
 *   (뒤의 Spring Boot 버전에서는 이 부분이 REST API 로 바뀝니다.)
 */
public class BankApp {

    private static final Bank bank = new Bank();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== 스페이스시엘 은행 시스템 (콘솔 버전) ===");

        // 예제를 바로 체험할 수 있도록 샘플 계좌 2개 생성
        bank.createAccount("홍길동", 100000);
        bank.createAccount("김철수", 50000);

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
                        System.out.println("프로그램을 종료합니다. 이용해 주셔서 감사합니다.");
                        return;
                    }
                    default -> System.out.println("잘못된 선택입니다. 다시 입력해 주세요.");
                }
            } catch (Exception e) {
                // 업무 로직에서 던진 예외를 여기서 한 번에 처리해 프로그램이 죽지 않게 함
                System.out.println("[오류] " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("----------------------------------------");
        System.out.println("1. 계좌 개설   2. 입금   3. 출금");
        System.out.println("4. 잔액 조회   5. 이체   6. 전체 계좌 목록");
        System.out.println("0. 종료");
        System.out.print("메뉴 선택 > ");
    }

    private static void createAccount() {
        System.out.print("예금주 이름: ");
        String owner = scanner.nextLine().trim();
        System.out.print("초기 입금액: ");
        long initial = Long.parseLong(scanner.nextLine().trim());

        Account account = bank.createAccount(owner, initial);
        System.out.println("계좌가 개설되었습니다 → " + account);
    }

    private static void deposit() {
        System.out.print("계좌번호: ");
        String acc = scanner.nextLine().trim();
        System.out.print("입금액: ");
        long amount = Long.parseLong(scanner.nextLine().trim());

        bank.deposit(acc, amount);
        System.out.println(amount + "원 입금 완료 → " + bank.findAccount(acc));
    }

    private static void withdraw() {
        System.out.print("계좌번호: ");
        String acc = scanner.nextLine().trim();
        System.out.print("출금액: ");
        long amount = Long.parseLong(scanner.nextLine().trim());

        bank.withdraw(acc, amount);
        System.out.println(amount + "원 출금 완료 → " + bank.findAccount(acc));
    }

    private static void checkBalance() {
        System.out.print("계좌번호: ");
        String acc = scanner.nextLine().trim();
        System.out.println(bank.findAccount(acc));
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
        System.out.println("  보낸 계좌 → " + bank.findAccount(from));
        System.out.println("  받은 계좌 → " + bank.findAccount(to));
    }

    private static void printAllAccounts() {
        System.out.println("=== 전체 계좌 목록 ===");
        bank.getAllAccounts().values().forEach(acc -> System.out.println("  " + acc));
    }
}
