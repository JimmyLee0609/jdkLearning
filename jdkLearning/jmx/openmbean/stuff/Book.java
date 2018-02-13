package openmbean.stuff;

import javax.management.openmbean.CompositeData;

public class Book {
	String name;
	String ISBN;
	BookMark bookMark;
	public BookMark getBookMark() {
		return bookMark;
	}

	public void setBookMark(BookMark bookMark) {
		this.bookMark = bookMark;
	}

	public Book(String name, String iSBN,BookMark bookMark) {
		super();
		this.name = name;
		ISBN = iSBN;
		this.bookMark=bookMark;
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", ISBN=" + ISBN +bookMark+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public static Book form(CompositeData data) {
		String isbn = (String)data.get("ISBN");
		String name =(String) data.get("name");
		CompositeData bookMarkData = (CompositeData)data.get("bookMark");
		BookMark mark = BookMark.form(bookMarkData);
		return new Book(name,isbn,mark);
	}
}
