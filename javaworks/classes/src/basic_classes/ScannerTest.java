package basic_classes;

import java.util.Scanner;

public class ScannerTest {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		//  이름 입력
		System.out.print("이름 입력: ");
		String name = scan.nextLine();
		System.out.println("이름: " + name);
		
		// 나이 입력
		System.out.print("나이 입력: ");
		int age = scan.nextInt();
		System.out.println("나이: " + age);
		
		scan.close();
	}

}
