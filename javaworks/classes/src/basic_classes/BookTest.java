package basic_classes;

public class BookTest {

	public static void main(String[] args) {
//		String name = new String("김기용");
		String name = "김기용";
		Book book1 = new Book(1, "바이브코딩");
		Book book2 = new Book(1, "바이브코딩");
		
		System.out.println(name);
		System.out.println(book1);
		System.out.println(book1 == book2); //주소 다름
		System.out.println(book1.equals(book2)); //문자열은 같음
	}

}
