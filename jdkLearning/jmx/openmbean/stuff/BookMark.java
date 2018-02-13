package openmbean.stuff;

import javax.management.openmbean.CompositeData;

public class BookMark {
String bookMark;

public String getBookMark() {
	return bookMark;
}

public void setBookMark(String mark) {
	this.bookMark = mark;
}

public BookMark(String mark) {
	super();
	this.bookMark = mark;
}

public BookMark() {
	super();
	// TODO Auto-generated constructor stub
}

@Override
public String toString() {
	return "--¡·  BookMark [mark=" + bookMark + "]";
}
public static BookMark form(CompositeData data) {
	String mark =(String) data.get("bookMark");
	return new BookMark(mark);
}
}
