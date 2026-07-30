package statics;

public class StaticSample {
	private static String value;

	public void setValue(String value) {
		StaticSample.value = value;
	}

	public String getValue() {
		return value;
	}

	public static void toUpper() {
		//value 값을 대문자로 변경
		if (value != null) {
			value = value.toUpperCase();
		}
	}
}
