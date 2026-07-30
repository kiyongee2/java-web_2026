package basic_classes;

public class StringTest {

	public static void main(String[] args) {
		String s = "Hello,Java";
		
		System.out.println(s.substring(0, 5));
		System.out.println(s.substring(6));
		
		String[] arr = s.split(",");
		System.out.println(arr[0]); //Hello
		
		for(String a : arr)
			System.out.println(a);
		
	}

}
