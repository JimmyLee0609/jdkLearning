package dynamic.mbean;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenMBeanAttributeInfo;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanConstructorInfo;
import javax.management.openmbean.OpenMBeanConstructorInfoSupport;
import javax.management.openmbean.OpenMBeanInfo;
import javax.management.openmbean.OpenMBeanInfoSupport;
import javax.management.openmbean.OpenMBeanOperationInfo;
import javax.management.openmbean.OpenMBeanOperationInfoSupport;
import javax.management.openmbean.OpenMBeanParameterInfo;
import javax.management.openmbean.OpenMBeanParameterInfoSupport;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularType;

import dynamic.stuff.Book;
import dynamic.stuff.BookMark;

public class Box implements DynamicMBean {
	// 一个复杂的属性
	Book book = new Book("myBook", "myISBN", new BookMark("myBookMark"));
	// 一个基础的属性
	String name;
	// 一个集合的属性
	ArrayList<Book> list = new ArrayList<Book>();

	public ArrayList<Book> getList() {
		return list;
	}

	public void setList(ArrayList<Book> list) {
		this.list = list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
//	定义两个类型
	CompositeType bookType=null;
	CompositeType markType=null;
	public Box() throws NoSuchMethodException, SecurityException, OpenDataException {
/*这里构造的信息是用来构建管理接口的信息会显示在管理接口中，
		由于openMBean的特性operation中的方法都是由invoke来操作的。
		这里需要注明它们的参数和返回值等信息以供调用。
		HTML适配器中对Compositedata的复杂类型作为参数没有提供实现，由于它会嵌套，实际中需要注意。
		另外,在构建Type,无论是CompositeType ,还是TabularType的时候,名字,类型一旦创建,
		后面就必须和创建的符合,并且跟对应的类符合.
		CompositeDataSupport中的类型,名字也要与CompositeType的对应.TabularType也是一样
*/
		// ==========属性===============
		OpenMBeanAttributeInfo[] openAttributes = new OpenMBeanAttributeInfo[3];
		// 构建一个name属性
		OpenMBeanAttributeInfoSupport name = new OpenMBeanAttributeInfoSupport("name", "MING ZI", SimpleType.STRING,
				true, false, false);
		openAttributes[0] = name;
		// =================构建一个book属性============================
		markType = new CompositeType("stuff.BookMark", "bookMark", new String[] { "bookMark" },
				new String[] { "树叶" }, new OpenType[] { SimpleType.STRING });
		bookType= new CompositeType("stuff.Book", "books", new String[] { "name", "ISBN", "bookMark" },
				new String[] { "mingzi ", "条形码", "树叶" },
				new OpenType[] { SimpleType.STRING, SimpleType.STRING, markType });
		//
		OpenMBeanAttributeInfoSupport support = new OpenMBeanAttributeInfoSupport("book", "书本", bookType, true, false,
				false);
		openAttributes[1] = support;
		// ==========构建一个集合book属性========================
		TabularType bookList = new TabularType("list", "list", bookType, new String[] { "name", "ISBN", "bookMark" });
		OpenMBeanAttributeInfoSupport openMBeanAttributeInfoSupport = new OpenMBeanAttributeInfoSupport("list",
				"listbook", bookList, true, true, false);
		openAttributes[2] = openMBeanAttributeInfoSupport;
		
		
		// ================构造器========================
		OpenMBeanConstructorInfo[] openConstructors = new OpenMBeanConstructorInfo[1];
		Constructor con = this.getClass().getConstructors()[0];
		OpenMBeanConstructorInfoSupport openMBeanConstructorInfoSupport = new OpenMBeanConstructorInfoSupport(
				con.getName(), "构造器", new OpenMBeanParameterInfoSupport[0]);
		openConstructors[0] = openMBeanConstructorInfoSupport;
		
		
		// =====operation====================
		
//		这里公开的operation会在界面中显示，但是界面调用方法的时候会使用invoke()来包装，也就是使用dynamic接口的invoke来包装方法然后调用
		OpenMBeanOperationInfo[] openOperations = new OpenMBeanOperationInfo[3];
//		构建3个参数
		OpenMBeanParameterInfoSupport paraInfo = new OpenMBeanParameterInfoSupport("name", "书籍的名字", SimpleType.STRING);
		OpenMBeanParameterInfoSupport isbn = new OpenMBeanParameterInfoSupport("isbn", "书籍的isbn", SimpleType.STRING);
		OpenMBeanParameterInfoSupport bookMark = new OpenMBeanParameterInfoSupport("bookMark", "书籍的树叶", SimpleType.STRING);
//		放到集合中
		OpenMBeanParameterInfo[] signature=new OpenMBeanParameterInfo[] {paraInfo,isbn,bookMark} ;
//		构建公开的方法builtBook
		OpenMBeanOperationInfoSupport openMBeanOperationInfoSupport = 
				new OpenMBeanOperationInfoSupport("builtBook", "新建书本", signature, bookType, MBeanOperationInfo.UNKNOWN);
//		将构建的方法放到公开方法集合中
		openOperations[0]=openMBeanOperationInfoSupport;
//		新建一个参数
		OpenMBeanParameterInfoSupport bookParameter = new OpenMBeanParameterInfoSupport("book", "书的CompositeData", bookType);
//		将参数放到集合中
		OpenMBeanParameterInfo[] addBookSignature=new OpenMBeanParameterInfo[] {bookParameter};
//		构建公开的方法addBook
		OpenMBeanOperationInfoSupport addBook = new OpenMBeanOperationInfoSupport("addBook","添加书本",addBookSignature,SimpleType.BOOLEAN,MBeanOperationInfo.INFO);
//		将方法放到公开的集合中
		openOperations[1]=addBook;
//		构建一个list,列出书籍集合的方法
		OpenMBeanOperationInfoSupport list = new OpenMBeanOperationInfoSupport("list", "列出所有的书籍", new OpenMBeanParameterInfo[0], bookList, MBeanOperationInfo.INFO);
//		将方法注册到方法列表
		openOperations[2]=list;
		
		
		// ======notification=================
		MBeanNotificationInfo[] notifications = new MBeanNotificationInfo[0];
		
//		===========openMBeanInfo========================
		beanInfo = new OpenMBeanInfoSupport(Box.class.getName(), "box book", openAttributes, openConstructors,
				openOperations, notifications);
	}

	MBeanInfo beanInfo;
//===============Dynamic接口的方法==============================
	@Override
	public Object getAttribute(String attribute)
			throws AttributeNotFoundException, MBeanException, ReflectionException {
		// 根据传入属性的名字返回对应的属性的值
		if (attribute.equals("name"))
			return getName();
		else if (attribute.equals("book"))
			return book;
		else if (attribute.equals("list"))
			return list;
		else
			return null;
	}

	@Override
	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
		// 根据传入的属性的名字和值，为属性值设置一个新的值
		if (attribute.getName().equals("name"))
			// 基础属性
			setName((String) attribute.getValue());
		else if (attribute.getName().equals("book")) {
			// 复杂属性
			modifyBook((CompositeData) attribute.getValue());
		} else if (attribute.getName().equals("list")) {
			// 复杂集合属性
			modifyList((TabularData) attribute.getValue());
		}
	}
	@Override
	public AttributeList getAttributes(String[] attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {
		if(actionName.equals("builtBook")) {
			try {
				return	builtBook((String)params[0],(String)params[1],(String)params[2]);
				  
			} catch (OpenDataException e) {
				throw new MBeanException(e,"错误参数");
			}
		}else if(actionName.equals("addBook")) {
			return addBook((CompositeData)params[0]);
			 
		}
		else if(actionName.equals("list")) {
			return list();
		}
		else
			return "错了";
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		return beanInfo;
	}
	

	//===============operation,这里的方法由invoke调用,不一定能要公开================
		private CompositeData builtBook(String name ,String isbn ,String bookMark)throws OpenDataException {
//			构建一个CompositeData 可以添加方法对数据进行过滤
//			例如
			if(name.equals("abc"))                             {
				throw new OpenDataException("abc 不是符合的名字");
			}
			CompositeDataSupport bookmark = new CompositeDataSupport(markType, new String[] {"bookMark"},new String[] {bookMark});
			CompositeDataSupport dataSupport = new CompositeDataSupport(bookType, new String[] {"name","ISBN","bookMark"},new Object[] {name,isbn,bookmark});
			
			return dataSupport;
		}
		private boolean addBook(CompositeData book) {
//			获取复杂数据结构中的数据
			String name = (String)book.get("name");
			String isbn=(String)book.get("ISBN");
			CompositeData mark =(CompositeData) book.get("bookMark");
			String bookMark =(String) mark.get("bookMark");
//			构建一个新的对象
			Book book2 = new Book(name,isbn,new BookMark(bookMark));
//			将对象添加到集合中
			return list.add(book2);
		}
		
		private List<Book> list(){
			return list;
		}
		
//	===================other method============================


	public void modifyBook(CompositeData newBook) {
		// 由于构建CompositeData的时候会根据属性的名字--属性值的方式来构建，这里直接获取属性值来设置属性
		String name = (String) newBook.get("name");
		book.setName(name);
		String ISBN = (String) newBook.get("ISBN");
		book.setISBN(ISBN);
		// 由于这个是嵌套属性，需要转换类型后再获取到里面的值
		CompositeDataSupport mark = (CompositeDataSupport) newBook.get("bookMark");
		String bookMark = (String) mark.get("bookMark");
		book.getBookMark().setBookMark(bookMark);
	}

	private void modifyList(TabularData value) {
		list.clear();
		Collection<?> values = value.values();
		for (Object object : values) {
			CompositeData obj = (CompositeData) object;
			String name = (String) obj.get("name");
			String ISBN = (String) obj.get("ISBN");
			CompositeData bookMark = (CompositeData) obj.get("bookMark");
			String mark = (String) bookMark.get("bookMark");
			Book book2 = new Book(name, ISBN, new BookMark(mark));// 这里应该使用内省或者其他的方式来构建对象，麻烦就直接用来测试
			boolean add = list.add(book2);
		}
	}
}