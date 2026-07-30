package abstracts.animal;

public class AnimalTest {

	public static void main(String[] args) {
		/*Cat cat = new Cat();
		
		cat.breathe();
		cat.cry();*/
		
		// 메서드의 다형성
		animalCry(new Cat());
	}
	
	// 동물 울음소리 메서드 정의
	public static void animalCry(Animal animal) {
		animal.cry();
	}

}
