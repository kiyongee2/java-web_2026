package abstracts.animal;

public abstract class Animal {
	 
	String kind;
	
	void breathe() {
		System.out.println("동물이 숨을 쉽니다.");
	}
	
	public abstract void cry();

}
