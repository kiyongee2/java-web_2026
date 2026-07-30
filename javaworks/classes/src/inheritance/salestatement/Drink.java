package inheritance.salestatement;

public class Drink {
	protected String name;
	protected int price;
	protected int quantity;
	
	public Drink(String name, int price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public int calcPrice() {
		return price * quantity;
	}
	
	public static void printTitle() {
		System.out.println("상품명\t가격\t수량\t금액");
	}
	
	public void printData() {
		System.out.println(name + "\t" + price + "\t" 
				+ quantity + "\t" + calcPrice());
	}
}
