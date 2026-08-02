package abstracts.animal;

public class AnimalTest {

	public static void main(String[] args) {
		// 다형성: 여러 Animal 하위 타입을 동일하게 처리
		Animal[] animals = {new Cat(), new Dog()};
		
		for(Animal animal : animals) {
			animal.breathe();
			//animal.cry();
			animalCry(animal);
		}
	}
	
	// 동물 울음소리 메서드(다형성)
	public static void animalCry(Animal animal) {
		animal.cry();
	}
}
