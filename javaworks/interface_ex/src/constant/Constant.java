package constant;

public class Constant {
	static int num = 10;  //클래스 변수
	static final int Num = 100; // 상수 선언
	static final double PI = 3.1415;
	
	public static void main(String[] args) {
		num = 20;
		//NUM = 200
		
		System.out.println(num);
		System.out.println(PI);
	}
}
