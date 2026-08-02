package interfaces.calculator;

public class MyCalc implements Calculator{

	@Override
	public int add(int a, int b) {
		return a + b;
	}

	@Override
	public int subtract(int a, int b) {
		return a - b;
	}

	@Override
	public int multiply(int a, int b) {
		return a * b;
	}

	@Override
	public int divide(int a, int b) {
		/*if(b == 0) {
			System.out.println("0으로 나눌 수 없습니다.");
			return 0;
		}
		return a / b;*/
		try {
			return a / b;
		}catch(ArithmeticException e) {
			System.out.println("0으로 나눌 수 없습니다.");
			return b;
		}
	}

}
