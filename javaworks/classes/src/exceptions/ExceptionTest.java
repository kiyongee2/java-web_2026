package exceptions;

public class ExceptionTest {

	public static void main(String[] args) {
		try {
			int r = 10 / 0;
		}catch(ArithmeticException e) {
			System.out.println("0으로 나눌 수 없습니다.");
		}finally {
			System.out.println("처리 완료!");
		}
	}

}
