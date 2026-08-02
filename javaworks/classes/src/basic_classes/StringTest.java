package basic_classes;

public class StringTest {

	public static void main(String[] args) {
		String str = "Hello,Java";
		
		System.out.println(str);
		System.out.println(str.substring(0, 5));
		System.out.println(str.substring(6));
		
		String[] arr = str.split(",");
		System.out.println(arr[0]); //Hello
		
		for(String a : arr)
			System.out.println(a);
		
	}

}
