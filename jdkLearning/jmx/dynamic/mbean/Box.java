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
	// һ�����ӵ�����
	Book book = new Book("myBook", "myISBN", new BookMark("myBookMark"));
	// һ������������
	String name;
	// һ�����ϵ�����
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
//	������������
	CompositeType bookType=null;
	CompositeType markType=null;
	public Box() throws NoSuchMethodException, SecurityException, OpenDataException {
/*���ﹹ�����Ϣ��������������ӿڵ���Ϣ����ʾ�ڹ���ӿ��У�
		����openMBean������operation�еķ���������invoke�������ġ�
		������Ҫע�����ǵĲ����ͷ���ֵ����Ϣ�Թ����á�
		HTML�������ж�Compositedata�ĸ���������Ϊ����û���ṩʵ�֣���������Ƕ�ף�ʵ������Ҫע�⡣
		����,�ڹ���Type,������CompositeType ,����TabularType��ʱ��,����,����һ������,
		����ͱ���ʹ����ķ���,���Ҹ���Ӧ�������.
		CompositeDataSupport�е�����,����ҲҪ��CompositeType�Ķ�Ӧ.TabularTypeҲ��һ��
*/
		// ==========����===============
		OpenMBeanAttributeInfo[] openAttributes = new OpenMBeanAttributeInfo[3];
		// ����һ��name����
		OpenMBeanAttributeInfoSupport name = new OpenMBeanAttributeInfoSupport("name", "MING ZI", SimpleType.STRING,
				true, false, false);
		openAttributes[0] = name;
		// =================����һ��book����============================
		markType = new CompositeType("stuff.BookMark", "bookMark", new String[] { "bookMark" },
				new String[] { "��Ҷ" }, new OpenType[] { SimpleType.STRING });
		bookType= new CompositeType("stuff.Book", "books", new String[] { "name", "ISBN", "bookMark" },
				new String[] { "mingzi ", "������", "��Ҷ" },
				new OpenType[] { SimpleType.STRING, SimpleType.STRING, markType });
		//
		OpenMBeanAttributeInfoSupport support = new OpenMBeanAttributeInfoSupport("book", "�鱾", bookType, true, false,
				false);
		openAttributes[1] = support;
		// ==========����һ������book����========================
		TabularType bookList = new TabularType("list", "list", bookType, new String[] { "name", "ISBN", "bookMark" });
		OpenMBeanAttributeInfoSupport openMBeanAttributeInfoSupport = new OpenMBeanAttributeInfoSupport("list",
				"listbook", bookList, true, true, false);
		openAttributes[2] = openMBeanAttributeInfoSupport;
		
		
		// ================������========================
		OpenMBeanConstructorInfo[] openConstructors = new OpenMBeanConstructorInfo[1];
		Constructor con = this.getClass().getConstructors()[0];
		OpenMBeanConstructorInfoSupport openMBeanConstructorInfoSupport = new OpenMBeanConstructorInfoSupport(
				con.getName(), "������", new OpenMBeanParameterInfoSupport[0]);
		openConstructors[0] = openMBeanConstructorInfoSupport;
		
		
		// =====operation====================
		
//		���﹫����operation���ڽ�������ʾ�����ǽ�����÷�����ʱ���ʹ��invoke()����װ��Ҳ����ʹ��dynamic�ӿڵ�invoke����װ����Ȼ�����
		OpenMBeanOperationInfo[] openOperations = new OpenMBeanOperationInfo[3];
//		����3������
		OpenMBeanParameterInfoSupport paraInfo = new OpenMBeanParameterInfoSupport("name", "�鼮������", SimpleType.STRING);
		OpenMBeanParameterInfoSupport isbn = new OpenMBeanParameterInfoSupport("isbn", "�鼮��isbn", SimpleType.STRING);
		OpenMBeanParameterInfoSupport bookMark = new OpenMBeanParameterInfoSupport("bookMark", "�鼮����Ҷ", SimpleType.STRING);
//		�ŵ�������
		OpenMBeanParameterInfo[] signature=new OpenMBeanParameterInfo[] {paraInfo,isbn,bookMark} ;
//		���������ķ���builtBook
		OpenMBeanOperationInfoSupport openMBeanOperationInfoSupport = 
				new OpenMBeanOperationInfoSupport("builtBook", "�½��鱾", signature, bookType, MBeanOperationInfo.UNKNOWN);
//		�������ķ����ŵ���������������
		openOperations[0]=openMBeanOperationInfoSupport;
//		�½�һ������
		OpenMBeanParameterInfoSupport bookParameter = new OpenMBeanParameterInfoSupport("book", "���CompositeData", bookType);
//		�������ŵ�������
		OpenMBeanParameterInfo[] addBookSignature=new OpenMBeanParameterInfo[] {bookParameter};
//		���������ķ���addBook
		OpenMBeanOperationInfoSupport addBook = new OpenMBeanOperationInfoSupport("addBook","����鱾",addBookSignature,SimpleType.BOOLEAN,MBeanOperationInfo.INFO);
//		�������ŵ������ļ�����
		openOperations[1]=addBook;
//		����һ��list,�г��鼮���ϵķ���
		OpenMBeanOperationInfoSupport list = new OpenMBeanOperationInfoSupport("list", "�г����е��鼮", new OpenMBeanParameterInfo[0], bookList, MBeanOperationInfo.INFO);
//		������ע�ᵽ�����б�
		openOperations[2]=list;
		
		
		// ======notification=================
		MBeanNotificationInfo[] notifications = new MBeanNotificationInfo[0];
		
//		===========openMBeanInfo========================
		beanInfo = new OpenMBeanInfoSupport(Box.class.getName(), "box book", openAttributes, openConstructors,
				openOperations, notifications);
	}

	MBeanInfo beanInfo;
//===============Dynamic�ӿڵķ���==============================
	@Override
	public Object getAttribute(String attribute)
			throws AttributeNotFoundException, MBeanException, ReflectionException {
		// ���ݴ������Ե����ַ��ض�Ӧ�����Ե�ֵ
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
		// ���ݴ�������Ե����ֺ�ֵ��Ϊ����ֵ����һ���µ�ֵ
		if (attribute.getName().equals("name"))
			// ��������
			setName((String) attribute.getValue());
		else if (attribute.getName().equals("book")) {
			// ��������
			modifyBook((CompositeData) attribute.getValue());
		} else if (attribute.getName().equals("list")) {
			// ���Ӽ�������
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
				throw new MBeanException(e,"�������");
			}
		}else if(actionName.equals("addBook")) {
			return addBook((CompositeData)params[0]);
			 
		}
		else if(actionName.equals("list")) {
			return list();
		}
		else
			return "����";
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		return beanInfo;
	}
	

	//===============operation,����ķ�����invoke����,��һ����Ҫ����================
		private CompositeData builtBook(String name ,String isbn ,String bookMark)throws OpenDataException {
//			����һ��CompositeData ������ӷ��������ݽ��й���
//			����
			if(name.equals("abc"))                             {
				throw new OpenDataException("abc ���Ƿ��ϵ�����");
			}
			CompositeDataSupport bookmark = new CompositeDataSupport(markType, new String[] {"bookMark"},new String[] {bookMark});
			CompositeDataSupport dataSupport = new CompositeDataSupport(bookType, new String[] {"name","ISBN","bookMark"},new Object[] {name,isbn,bookmark});
			
			return dataSupport;
		}
		private boolean addBook(CompositeData book) {
//			��ȡ�������ݽṹ�е�����
			String name = (String)book.get("name");
			String isbn=(String)book.get("ISBN");
			CompositeData mark =(CompositeData) book.get("bookMark");
			String bookMark =(String) mark.get("bookMark");
//			����һ���µĶ���
			Book book2 = new Book(name,isbn,new BookMark(bookMark));
//			��������ӵ�������
			return list.add(book2);
		}
		
		private List<Book> list(){
			return list;
		}
		
//	===================other method============================


	public void modifyBook(CompositeData newBook) {
		// ���ڹ���CompositeData��ʱ���������Ե�����--����ֵ�ķ�ʽ������������ֱ�ӻ�ȡ����ֵ����������
		String name = (String) newBook.get("name");
		book.setName(name);
		String ISBN = (String) newBook.get("ISBN");
		book.setISBN(ISBN);
		// ���������Ƕ�����ԣ���Ҫת�����ͺ��ٻ�ȡ�������ֵ
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
			Book book2 = new Book(name, ISBN, new BookMark(mark));// ����Ӧ��ʹ����ʡ���������ķ�ʽ�����������鷳��ֱ����������
			boolean add = list.add(book2);
		}
	}
}