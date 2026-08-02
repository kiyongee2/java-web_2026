package interfaces.calculator;

public class CalculatorTest {

	public static void main(String[] args) {
		Calculator calc = new MyCalc();
		
		int value1 = calc.add(10, 20);
		System.out.println("두수의 합:" + value1);
	}

}
