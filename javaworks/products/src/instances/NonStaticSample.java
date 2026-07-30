package instances;

public class NonStaticSample {

	public void printLottoNumbers() {
		// 1~45까지의 임의의 정수 6개가 중복되지 않게 발생시켜 출력하는 메소드
		// 응용 --> 오름차순 정렬로 출력
		int[] lotto = new int[6];
		int count = 0;

		while (count < 6) {
			int num = (int) (Math.random() * 45) + 1; // 1~45

			// 중복 검사
			boolean exist = false;
			for (int i = 0; i < count; i++) {
				if (lotto[i] == num) {
					exist = true;
					break;
				}
			}

			if (!exist) {
				lotto[count] = num;
				count++;
			}
		}

		// 오름차순 정렬 (선택 정렬)
		for (int i = 0; i < lotto.length - 1; i++) {
			for (int j = i + 1; j < lotto.length; j++) {
				if (lotto[i] > lotto[j]) {
					int temp = lotto[i];
					lotto[i] = lotto[j];
					lotto[j] = temp;
				}
			}
		}

		// 출력
		for (int n : lotto) {
			System.out.print(n + " ");
		}
		System.out.println();
	}

	public void outputChar(int num, char c) {
		// 매개변수로 전달받은 문자 c를 전달받은 num 갯수 만큼 출력하는 메소드
		for (int i = 0; i < num; i++) {
			System.out.print(c + " ");
		}
		System.out.println();
	}

	public char alphabet() {
		// 알파벳 범위의 임의의 영문자를 하나 발생시켜 리턴하는 메소드
		// 대문자(A~Z) 26개 + 소문자(a~z) 26개 = 52개 중 하나
		int r = (int) (Math.random() * 52); // 0~51
		if (r < 26) {
			return (char) ('A' + r); // 0~25 --> A~Z
		} else {
			return (char) ('a' + (r - 26)); // 26~51 --> a~z
		}
	}

	public String mySubstring(String str, int index1, int index2) {
		// 문자열과 시작 인덱스, 끝 인덱스를 전달받아 해당 범위의 문자열을 리턴
		// 끝 인덱스는 미포함 (Java substring 규칙과 동일)
		// 단, 문자열에 값이 없으면(null 또는 빈 문자열) null 리턴
		if (str == null || str.length() == 0) {
			return null;
		}
		// 인덱스 범위가 잘못된 경우도 null 리턴
		if (index1 < 0 || index2 > str.length() || index1 > index2) {
			return null;
		}
		return str.substring(index1, index2);
	}

}
