package polymorphism.downcasting;

public class TypeConversion {

	public static void main(String[] args) {
		//기본 자료형의 타입 변환 예제
		int iNum = 10;
		float fNum = iNum;   //자동타입 변환(큰 자료형 = 작은 자료형)
		
		System.out.println(iNum + ", " + fNum);
		
		double dNum = 2.54;
		iNum = (int)dNum;  //강제타입 변환(작은 자료형 = 큰 자료형)
		
		System.out.println(dNum); //2
	}
}

