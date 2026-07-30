package polymorphism.upcasting;

class Animal{
	public void move() {
		System.out.println("동물이 움직입니다.");
	}
}

class Human extends Animal{
	public void move() {
		System.out.println("사람이 두 발로 걷습니다.");
	}
}

class Eagle extends Animal{
	public void move() {
		System.out.println("독수리가 하늘을 날아갑니다.");
	}
}

public class AnimalTest {

	public static void main(String[] args) {
		//다형성 - 자동타입 변환, 업 캐스팅
		/*Animal human = new Human();
		human.move();*/
		
		Animal[] animals = {
			new Human(),
			new Eagle()
		};
		
		for(Animal animal : animals ) {
			animal.move();
		}
	}
	
	

}
