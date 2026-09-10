package bank;

/**
 * 계좌 한 개를 표현하는 데이터 클래스.
 * H2 DB의 account 테이블 한 행(row)에 대응한다.
 * (메모리 버전과 달리 잔액 변경은 DB의 UPDATE로 처리하므로, 여기서는 데이터만 담는다.)
 */
public class Account {

    private final String accountNumber; // 계좌번호
    private final String owner;         // 예금주
    private final long balance;         // 잔액

    public Account(String accountNumber, String owner, long balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwner()         { return owner; }
    public long getBalance()         { return balance; }

    @Override
    public String toString() {
        return String.format("[%s] %s님 / 잔액: %,d원", accountNumber, owner, balance);
    }
}
