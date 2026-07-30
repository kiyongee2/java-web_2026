package basic_classes;

public class Book {
	private int bookNumber;
	private String bookTitle;
	
	public Book(int bookNumber, String bookTitle) {
		this.bookNumber = bookNumber;
		this.bookTitle = bookTitle;
	}
	
	public int getBookNumber() {
		return bookNumber;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}

	@Override
	public String toString() {
		return "Book[" + bookTitle + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Book) {
			Book book = (Book)obj;
			return this.bookNumber == book.bookNumber;
		}
		return false;
	}
}
