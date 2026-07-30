package bankapp;

public class Transaction {
	TransactionType type;
	int amount;
	
	public Transaction(TransactionType type, int amount) {
		this.type = type;
		this.amount = amount;
	}

}
