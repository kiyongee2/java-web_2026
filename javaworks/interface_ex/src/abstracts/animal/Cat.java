package abstracts.animal;

public class Cat extends Animal{
	
	public Cat() {
		this.kind = "포유류";
	}

	@Override
	public void cry() {
		System.out.println("야~ 옹!");
	}

}
