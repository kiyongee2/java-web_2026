package instances;

public class TestNonStaticSample {

	public static void main(String[] args) {
		// NonStaticSample 클래스의 4가지 메소드 각각 호출
		NonStaticSample sample = new NonStaticSample();

		// 1. 랜덤 값 (1~45 중복 없는 6개, 오름차순 정렬)
		System.out.print("1. 랜덤 값 : ");
		sample.printLottoNumbers();

		// 2. 문자 5개 출력
		System.out.print("2. a문자 5개 출력 : ");
		sample.outputChar(5, 'a');

		// 3. 랜덤 영문자 출력
		System.out.println("3. 랜덤 영문자 출력 : " + sample.alphabet());

		// 4. apple의 2번~4번(미포함) 인덱스 사이의 값 출력
		System.out.println("4. apple의 2번 4번 인덱스 사이의 값 출력 : " + sample.mySubstring("apple", 2, 4));
	}

}
