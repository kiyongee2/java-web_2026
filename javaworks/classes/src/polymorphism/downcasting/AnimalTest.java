package polymorphism.downcasting;

class Animal{
	public void move() {
		System.out.println("동물이 움직입니다.");
	}
}

class Human extends Animal{
	public void move() {
		System.out.println("사람이 두 발로 걷습니다.");
	}
	
	public void readBook() {
		System.out.println("사람이 책을 읽습니다.");
	}
}

class Eagle extends Animal{
	public void move() {
		System.out.println("독수리가 하늘을 날아갑니다.");
	}
	
	public void hunting() {
		System.out.println("독수리가 물고기를 사냥합니다.");
	}
}

public class AnimalTest {
	static Animal[] animals = new Animal[2];

	public static void main(String[] args) {
		//다형성 - 자동타입 변환, 업 캐스팅
		animals[0] = new Human();
		animals[1] = new Eagle();
		
		//animals[0].move();
		
		for(Animal animal : animals) {
			animal.move();
		}
		
		System.out.println("=== 원래 형으로 다운캐스팅 ===");
		
		for(int i=0; i<animals.length; i++) {
			Animal animal = animals[i];
			if(animal instanceof Human) {
				Human human = (Human)animal;
				human.readBook();
			}else if(animal instanceof Eagle) {
				Eagle eagle = (Eagle)animal;
				eagle.hunting();
			}else {
				System.out.println("지원하지 않는 타입입니다.");
			}
		}
	}
}
