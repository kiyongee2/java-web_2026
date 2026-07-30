package statics;

public class StaticSample {
	private static String value;

	public static void setValue(String value) {
		StaticSample.value = value;
	}

	public static String getValue() {
		return value;
	}

	public static void toUpper() {
		// value 필드 값을 모두 대문자로 변경 --> char 배열 응용
		char[] arr = value.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			// 소문자(a~z)이면 대문자로 변환
			if (arr[i] >= 'a' && arr[i] <= 'z') {
				arr[i] = (char) (arr[i] - 32);
			}
		}
		value = new String(arr);
	}

	public static void setChar(int index, char c) {
		// 전달받은 인덱스 위치의 value 값을 전달받은 문자로 변경하는 static 메소드
		char[] arr = value.toCharArray();
		arr[index] = c;
		value = new String(arr);
	}

	public static int valueLength() {
		// value 필드 값에 기록되어 있는 문자 갯수 리턴
		return value.length();
	}

	public static String valueConcat(String str) {
		// 문자열 값을 전달받아 value 필드 값과 하나로 합쳐서 리턴
		return value + str;
	}

}
