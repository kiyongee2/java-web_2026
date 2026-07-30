package statics;

public class TestStaticSample {

	public static void main(String[] args) {
		// StaticSample 클래스 변수인 value를 "Java"로 초기화 후 출력
		StaticSample.setValue("Java");
		System.out.println("value : " + StaticSample.getValue());

		// toUpper 메소드 실행을 통해 대문자로 변경 후 출력
		StaticSample.toUpper();
		System.out.println("대문자로 : " + StaticSample.getValue());

		// valueLength 메소드 실행을 통해 길이 출력
		System.out.println("길이 : " + StaticSample.valueLength());

		// valueConcat 메소드 실행을 통해 "PROGRAMMING" 문자열 합친 후 출력
		System.out.println("PROGRAMMING 붙여서 : " + StaticSample.valueConcat("PROGRAMMING"));

		// setChar 메소드 실행을 통해 "J"를 "C"로 변경 후 출력
		StaticSample.setChar(0, 'C');
		System.out.println("J => C : " + StaticSample.getValue());
	}

}
