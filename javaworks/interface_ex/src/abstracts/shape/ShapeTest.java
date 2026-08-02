package abstracts.shape;

abstract class Shape{
	abstract double area();
}

class Circle extends Shape{
	private double r = 5;
	//private final double PI = 3.14;

	@Override
	double area() {
		return Math.PI * r * r;
	}
	
}

public class ShapeTest {

	public static void main(String[] args) {
		Shape shape = new Circle();
		System.out.println("원의 넓이: " + shape.area());
		System.out.printf("%.2f", shape.area());
	}

}
