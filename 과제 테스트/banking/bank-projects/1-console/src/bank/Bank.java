package bank;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 여러 계좌를 관리하는 "은행" 역할의 클래스입니다.
 *
 * [학습 포인트]
 * - 계좌들을 Map(계좌번호 -> 계좌 객체) 에 담아 관리합니다.
 *   실무에서는 이 자리에 데이터베이스(DB)가 들어갑니다.
 * - 계좌 생성, 조회, 입금, 출금, 이체 같은 "업무 로직(비즈니스 로직)"을 담당합니다.
 */
public class Bank {

    // 계좌번호를 key 로 계좌를 저장 (LinkedHashMap: 생성 순서 유지)
    private final Map<String, Account> accounts = new LinkedHashMap<>();

    // 계좌번호 자동 생성을 위한 카운터 (110-0001 형태)
    private int sequence = 1;

    /**
     * 새 계좌를 개설합니다. 생성된 계좌를 돌려줍니다.
     */
    public Account createAccount(String owner, long initialBalance) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("예금주 이름을 입력해야 합니다.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("초기 잔액은 0원 이상이어야 합니다.");
        }
        String accountNumber = String.format("110-%04d", sequence++);
        Account account = new Account(accountNumber, owner, initialBalance);
        accounts.put(accountNumber, account);
        return account;
    }

    /**
     * 계좌번호로 계좌를 찾습니다. 없으면 예외를 던집니다.
     */
    public Account findAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("존재하지 않는 계좌입니다: " + accountNumber);
        }
        return account;
    }

    public void deposit(String accountNumber, long amount) {
        findAccount(accountNumber).deposit(amount);
    }

    public void withdraw(String accountNumber, long amount) {
        findAccount(accountNumber).withdraw(amount);
    }

    /**
     * 계좌 이체: 출금 계좌에서 빼고, 입금 계좌에 더합니다.
     *
     * [학습 포인트]
     * - 실무에서는 "출금은 됐는데 입금이 실패"하는 상황을 막기 위해
     *   트랜잭션(transaction)으로 두 작업을 하나로 묶습니다.
     * - 여기서는 먼저 출금을 시도하고, 성공하면 입금하는 순서로 간단히 구현합니다.
     */
    public void transfer(String fromAccount, String toAccount, long amount) {
        if (fromAccount.equals(toAccount)) {
            throw new IllegalArgumentException("같은 계좌로는 이체할 수 없습니다.");
        }
        Account from = findAccount(fromAccount);
        Account to = findAccount(toAccount);

        from.withdraw(amount); // 잔액 부족이면 여기서 예외 발생 → 입금 진행 안 됨
        to.deposit(amount);
    }

    /**
     * 등록된 모든 계좌를 반환합니다. (목록 출력용)
     */
    public Map<String, Account> getAllAccounts() {
        return accounts;
    }
}
