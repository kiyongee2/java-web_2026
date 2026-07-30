package inheritance.person;

class Person{
	protected String name;
	
	public Person(String name) {
		this.name = name;
	}
	
	public void introduce() {
		System.out.println("안녕하세요, " + name + "입니다.");
	}
}

class Student extends Person{
	private String school;

	public Student(String name, String school) {
		super(name);
		this.school = school;
	}

	@Override
	public void introduce() {
		super.introduce();
		System.out.println(school + " 학생입니다.");
	}
}

public class Test {
	
	public static void main(String[] args) {
		Student st = new Student("김기용", "한국대학교");
		st.introduce();
	}
}
