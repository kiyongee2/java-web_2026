package interfaces.inheritance;

public class MyClassTest {

	public static void main(String[] args) {
		MyClass myClass = new MyClass();
		
		X x = myClass;
		x.x();
		
		Y y = myClass;
		y.y();
		
		System.out.println("** 다중 상속한 iClass 출력 **");
		MyInterface iClass = myClass;
		iClass.myMethod();
		iClass.x();
		iClass.y();
		
	}

}
