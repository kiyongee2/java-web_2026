package interfaces.calculator;

public class CalculatorTest {

	public static void main(String[] args) {
		Calculator calc = new MyCalc();
		
		int value1 = calc.add(10, 20);
		System.out.println("두수의 합:" + value1);
		
		int value2 = calc.subtract(10, 20);
		System.out.println("두수의 차:" + value2);
		
		int value3 = calc.multiply(10, 20);
		System.out.println("두수의 곱:" + value3);
		
		int value4 = calc.divide(10, 0);
		System.out.println("두수의 나누기:" + value4);
	}

}
