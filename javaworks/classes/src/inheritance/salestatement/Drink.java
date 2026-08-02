package inheritance.salestatement;

//음료 클래스
public class Drink {
	protected String name;  //상품명
	protected int price;    //가격
	protected int quantity; //수량
	
	//생성자
	public Drink(String name, int price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public int calcPrice() {
		return price * quantity; //금액 = 가격 * 수량
	}
	
	public static void printTitle() { //제목 출력
		System.out.println("상품명\t가격\t수량\t금액");
	}
	
	public void printData() { // 데이터 출력
		System.out.println(name + "\t" + price + "\t" 
				+ quantity + "\t" + calcPrice());
	}
}
