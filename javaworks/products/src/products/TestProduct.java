package products;

public class TestProduct {

	public static void main(String[] args) {
		Product p1 = new Product();
		Product p2 = new Product();
		
		p1.setId("ssgnote9");
		p1.setName("갤럭시노트9");
		p1.setSite("경기도 수원");
		p1.setPrice(960000);
		p1.setTax(10.0);
		
		p2.setId("ssgnote9");
		p2.setName("lgxnote5");
		p2.setSite("경기도 평택");
		p2.setPrice(780000);
		p2.setTax(0.7);
		
		System.out.println(p1.information()); 
		System.out.println(p2.information()); 
		System.out.println("===========================================");
		
		// 가격 변경
		p1.setPrice(1260000);
//		p2.setPrice(1000000);
		
		System.err.println("상품명 = " + p1.getName());
		System.out.println("부가세 포함 가격 = " + p1.getPrice() + "원");

	}
	

}
