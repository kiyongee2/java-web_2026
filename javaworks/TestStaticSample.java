package statics;

public class TestStaticSample {

	public static void main(String[] args) {
		//StaticSample의 value의 초기값을 "Java"로 초기화
		StaticSample sample = new StaticSample();
		sample.setValue("Java");
		System.out.println("변경 전: " + sample.getValue());

		StaticSample.toUpper();
		System.out.println("변경 후: " + sample.getValue());
	}

}
