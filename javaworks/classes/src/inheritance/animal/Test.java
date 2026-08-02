package inheritance.animal;

public class Test {

	public static void main(String[] args) {
		/*Dog dog = new Dog();
		
		dog.eat();
		dog.bark();*/
		
		Animal dog = new Dog();
		dog.eat();
		callBark(new Dog());
	}
	
	public static void callBark(Animal a) {
		if(a instanceof Dog) {
			Dog dog = (Dog)a;
			dog.bark();
		}else {
			System.out.println("호출할 수 없습니다.");
		}
	}

}
