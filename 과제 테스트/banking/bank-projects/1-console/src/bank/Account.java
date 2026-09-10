package bank;

/**
 * 계좌 한 개를 표현하는 클래스입니다.
 *
 * [학습 포인트]
 * - 실무의 은행 서버도 결국 "계좌(Account)"라는 데이터를 다룹니다.
 * - 필드(계좌번호, 예금주, 잔액)를 private 으로 숨기고(캡슐화),
 *   입금/출금은 메서드를 통해서만 하도록 만들어 데이터를 안전하게 보호합니다.
 */
public class Account {

    private final String accountNumber; // 계좌번호 (한 번 정해지면 바뀌지 않으므로 final)
    private final String owner;         // 예금주 이름
    private long balance;               // 잔액 (원 단위, 음수가 될 수 없음)

    public Account(String accountNumber, String owner, long initialBalance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialBalance;
    }

    /**
     * 입금: 0보다 큰 금액만 허용합니다.
     */
    public void deposit(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("입금액은 0보다 커야 합니다.");
        }
        this.balance += amount;
    }

    /**
     * 출금: 금액이 유효하고, 잔액이 충분할 때만 가능합니다.
     * 잔액이 부족하면 예외를 던져서 잘못된 출금을 막습니다.
     */
    public void withdraw(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("출금액은 0보다 커야 합니다.");
        }
        if (amount > this.balance) {
            throw new IllegalStateException("잔액이 부족합니다. (현재 잔액: " + this.balance + "원)");
        }
        this.balance -= amount;
    }

    // --- Getter: 외부에서 값을 읽기만 할 수 있도록 제공 ---

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s님 / 잔액: %,d원", accountNumber, owner, balance);
    }
}
