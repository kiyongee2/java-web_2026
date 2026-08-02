package inheritance.salestatement;

public class SaleStatement {
	
	public static void main(String[] args) {
		Drink coffee = new Drink("아메리카노", 2500, 4);
		Drink tea = new Drink("녹차", 3500, 3);
		Alcohol beer = new Alcohol("맥주", 3000, 3, 5.52f);
		Alcohol soju = new Alcohol("소주", 4000, 2, 15f);
		
		Drink.printTitle();
		coffee.printData();
		tea.printData();
		System.out.println();
		
		Alcohol.printTitle();
		beer.printData();
		soju.printData();
		
		//총금액 계산
		int total = 0;
		total = coffee.calcPrice() + tea.calcPrice() + 
				+ beer.calcPrice() + soju.calcPrice();
		System.out.println("********** 합계 금액: " + total + "원 **********");
		
	}
}
