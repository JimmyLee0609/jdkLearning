package openmbean;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenMBeanAttributeInfo;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanConstructorInfo;
import javax.management.openmbean.OpenMBeanConstructorInfoSupport;
import javax.management.openmbean.OpenMBeanInfoSupport;
import javax.management.openmbean.OpenMBeanOperationInfo;
import javax.management.openmbean.OpenMBeanOperationInfoSupport;
import javax.management.openmbean.OpenMBeanParameterInfo;
import javax.management.openmbean.OpenMBeanParameterInfoSupport;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import openmbean.stuff.Book;
import openmbean.stuff.BookMark;

public class Box implements DynamicMBean {
	// 一个复杂的属性
	Book book = new Book("myBook", "myISBN", new BookMark("myBookMark"));
	// 一个基础的属性
	String name;
	// 一个集合的属性
	ArrayList<Book> list = new ArrayList<Book>();

	Map<String, Integer> map = new HashMap<String, Integer>();

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

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

	// 定义两个类型
	CompositeType bookType = null;
	CompositeType markType = null;
	TabularType mapType;
	CompositeType mapCompositeType;

	public Box() throws NoSuchMethodException, SecurityException, OpenDataException {
		/*
		 * 这里构造的信息是用来构建管理接口的信息会显示在管理接口中， 由于openMBean的特性operation中的方法都是由invoke来操作的。
		 * 这里需要注明它们的参数和返回值等信息以供调用。 HTML适配器中对Compositedata的复杂类型作为参数没有提供实现，由于它会嵌套，实际中需要注意。
		 * 另外,在构建Type,无论是CompositeType ,还是TabularType的时候,名字,类型一旦创建,
		 * 后面就必须和创建的符合,并且跟对应的类符合.
		 * CompositeDataSupport中的类型,名字也要与CompositeType的对应.TabularType也是一样
		 */
		// ==========属性===============
		// 构建一个name属性
		OpenMBeanAttributeInfoSupport name = new OpenMBeanAttributeInfoSupport("name", "MING ZI", SimpleType.STRING,
				true, true, false);

		// =================构建一个book属性============================
		// 新建复杂类型BookMark的类型
		markType = new CompositeType("stuff.BookMark", "bookMark", new String[] { "bookMark" }, new String[] { "树叶" },
				new OpenType[] { SimpleType.STRING });
		// 新建复杂类型Book的类型
		bookType = new CompositeType("stuff.Book", "books", new String[] { "name", "ISBN", "bookMark" },
				new String[] { "mingzi ", "条形码", "树叶" },
				new OpenType[] { SimpleType.STRING, SimpleType.STRING, markType });
		// 构建属性
		OpenMBeanAttributeInfoSupport support = new OpenMBeanAttributeInfoSupport("book", "书本", bookType, true, true,
				false);
		// ==========构建一个集合bookList属性========================
		// Collection都视为 [ ]类型
		ArrayType<?> bookListType = new ArrayType<>(1, bookType);
		OpenMBeanAttributeInfoSupport openMBeanAttributeInfoSupport = new OpenMBeanAttributeInfoSupport("list",
				"listbook", bookListType, true, true, false);

		// ============构建一个map属性=====================================
		OpenType[] openType = new OpenType[] { SimpleType.STRING, SimpleType.INTEGER };
		String[] keyValue = new String[] { "key", "value" };
		// 每一个映射的数据类型，这里可以清楚映射的类型，以便类型转换
		mapCompositeType = new CompositeType("java.lang.String,java.lang.Integer", "mapType", keyValue, keyValue,
				openType);
		mapType = new TabularType("map", "映射集合", mapCompositeType, new String[] { "key", "value" });

		OpenMBeanAttributeInfoSupport mapAttr = new OpenMBeanAttributeInfoSupport("map", "map映射集合", mapType, true, true,
				false);

		OpenMBeanAttributeInfo[] openAttributes = new OpenMBeanAttributeInfo[] { openMBeanAttributeInfoSupport, support,
				name, mapAttr };

		// ================构造器========================
		OpenMBeanConstructorInfo[] openConstructors = new OpenMBeanConstructorInfo[1];
		Constructor con = this.getClass().getConstructors()[0];
		OpenMBeanConstructorInfoSupport openMBeanConstructorInfoSupport = new OpenMBeanConstructorInfoSupport(
				con.getName(), "构造器", new OpenMBeanParameterInfoSupport[0]);
		openConstructors[0] = openMBeanConstructorInfoSupport;

		// =====operation====================

		// 这里公开的operation会在界面中显示，但是界面调用方法的时候会使用invoke()来包装，也就是使用dynamic接口的invoke来包装方法然后调用
		// ===============builtBook operation===============================
		// 构建3个参数
		OpenMBeanParameterInfoSupport paraInfo = new OpenMBeanParameterInfoSupport("name", "书籍的名字", SimpleType.STRING);
		OpenMBeanParameterInfoSupport isbn = new OpenMBeanParameterInfoSupport("isbn", "书籍的isbn", SimpleType.STRING);
		OpenMBeanParameterInfoSupport bookMark = new OpenMBeanParameterInfoSupport("bookMark", "书籍的树叶",
				SimpleType.STRING);
		// 放到集合中
		OpenMBeanParameterInfo[] signature = new OpenMBeanParameterInfo[] { paraInfo, isbn, bookMark };
		// 构建公开的方法builtBook
		OpenMBeanOperationInfoSupport openMBeanOperationInfoSupport = new OpenMBeanOperationInfoSupport("builtBook",
				"新建书本", signature, bookType, MBeanOperationInfo.UNKNOWN);
		// =================addBook operation =================
		// 新建一个参数
		OpenMBeanParameterInfoSupport bookParameter = new OpenMBeanParameterInfoSupport("book", "书的CompositeData",
				bookType);
		// 将参数放到集合中
		OpenMBeanParameterInfo[] addBookSignature = new OpenMBeanParameterInfo[] { bookParameter };
		// 构建公开的方法addBook
		OpenMBeanOperationInfoSupport addBook = new OpenMBeanOperationInfoSupport("addBook", "添加书本", addBookSignature,
				SimpleType.BOOLEAN, MBeanOperationInfo.INFO);

		// ============listBook operation==============================
		// 构建一个list,列出书籍集合的方法
		OpenMBeanOperationInfoSupport list = new OpenMBeanOperationInfoSupport("list", "列出所有的书籍",
				new OpenMBeanParameterInfo[0], bookListType, MBeanOperationInfo.INFO);

		OpenMBeanOperationInfo[] openOperations = new OpenMBeanOperationInfo[] { openMBeanOperationInfoSupport, addBook,
				list };

		// ======notification=================
		MBeanNotificationInfo notificationInfo = new MBeanNotificationInfo(new String[] { "jmx.attribute.change" },
				"openmbean.Box", "通知");
		MBeanNotificationInfo[] notifications = new MBeanNotificationInfo[] { notificationInfo };

		// ===========openMBeanInfo========================
		beanInfo = new OpenMBeanInfoSupport(Box.class.getName(), "box book", openAttributes, openConstructors,
				openOperations, notifications);
		map.put("键", 5);
	}

	MBeanInfo beanInfo;

	// ===============Dynamic接口的方法==============================
	@Override
	public Object getAttribute(String attribute)
			throws AttributeNotFoundException, MBeanException, ReflectionException {
		// 根据传入属性的名字返回对应的属性的值
		if (attribute.equals("name"))
			return getName();
		else if (attribute.equals("book")) {
			// 属性是一个复杂属性，，根据属性名字获取属性值的时候需要将属性的具体中转换成为CompositeData来传递出去，这是openBean的一种形式
			CompositeDataSupport bookData = formBookCompositeData(this.book);
			return bookData;
		} else if (attribute.equals("list")) {
			// 属性是一个Collection<E>的时候就需要将类型转换为Composite的ArrayType来传递出去
			CompositeData[] array = new CompositeData[list.size()];
			for (int i = 0; i < list.size(); i++) {
				array[i] = formBookCompositeData(list.get(i));
			}
			return array;
		} else if (attribute.equals("map")) {
			// 属性是一个Map类型，需要转换为TabularData的形式传递
			TabularDataSupport mapData = new TabularDataSupport(mapType);
			try {
				for (String key : map.keySet()) {
					Integer value = map.get(key);
					addCompositeDataToTabularData(mapData, key, value);
				}

			} catch (OpenDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mapData;
		} else
			return null;
	}

	private void addCompositeDataToTabularData(TabularDataSupport mapData, String key, Integer value)
			throws OpenDataException {
		CompositeDataSupport CompositeDataSupport = new CompositeDataSupport(mapCompositeType,
				new String[] { "key", "value" }, new Object[] { key, value });
		mapData.put(CompositeDataSupport);
	}

	private CompositeDataSupport formBookCompositeData(Book book) {
		// =======从对象变成CompositeData===========
		CompositeDataSupport bookData = null;
		BookMark bookMark = book.getBookMark();
		String bookMark2 = bookMark.getBookMark();
		String isbn = book.getISBN();
		String name2 = book.getName();
		try {
			CompositeDataSupport bookMarkData = new CompositeDataSupport(markType, new String[] { "bookMark" },
					new Object[] { bookMark2 });
			bookData = new CompositeDataSupport(bookType, new String[] { "name", "ISBN", "bookMark" },
					new Object[] { name2, isbn, bookMarkData });
		} catch (OpenDataException e) {
			e.printStackTrace();
		}
		return bookData;
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
			CompositeData value = (CompositeData) attribute.getValue();

			// setBook(value);
		} else if (attribute.getName().equals("list")) {
			// 复杂集合属性
			Book[] value = (Book[]) attribute.getValue();
			ArrayList<Book> booklist = new ArrayList<Book>(Arrays.asList(value));
			setList(booklist);
		} else if (attribute.getName().equals("map")) {
			// Map<V,E>类型的的属性设置，传入的书TabularData
			TabularData mapData = (TabularData) attribute.getValue();
			// 遍历TabularData的所有值，每个都是CompositeData
			Collection<?> values = mapData.values();
			map.clear();
			for (Iterator it = values.iterator(); it.hasNext();) {
				CompositeData compositeData = (CompositeData) it.next();
				// 获取Composite中保存的源数据类型，并按照Map的类型来组装添加到映射中
				CompositeType compositeType = compositeData.getCompositeType();
				Set<String> keySet = compositeType.keySet();
				String mapKey = "";
				Integer mapValue = 0;
				for (String key : keySet) {
					OpenType<?> type = compositeType.getType(key);
					// 获取CompositeData的值
					Object value = compositeData.get(key);
					if (type.getTypeName().equals(String.class.getName()))
						mapKey = (String) value;
					else if (type.getTypeName().equals(Integer.class.getName())) {
						mapValue = (Integer) value;
					}
				}
				if (!mapKey.equals(""))
					map.put(mapKey, mapValue);
				mapKey = "";
			}
		}
	}

	@Override
	public AttributeList getAttributes(String[] attributes) {
		// 实际中需要检查
		// 获取一系列的属性，这个是Dynamic接口的标准
		// 新建一个AttributeList来保存属性，
		AttributeList list2 = new AttributeList();
		for (String attr : attributes) {
			try {
				// 遍历传入的名字获取属性，属性值，并封装成属性的类型
				Object attribute = getAttribute(attr);
				list2.add(new Attribute(attr, attribute));
			} catch (AttributeNotFoundException | MBeanException | ReflectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list2;
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		// 实际中需要检查
		ArrayList<String> list = new ArrayList<String>();
		for (Iterator<Object> it = attributes.iterator(); it.hasNext();) {
			// 遍历传入的属性集，并保存每个属性名字，设定集中的值
			Attribute attr = (Attribute) it.next();
			String name2 = attr.getName();
			list.add(name2);
			try {
				setAttribute(attr);
			} catch (AttributeNotFoundException | InvalidAttributeValueException | MBeanException
					| ReflectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 获取保存的属性名字并获取对应的值
		String[] array = list.toArray(new String[0]);
		AttributeList attributes2 = getAttributes(array);
		return attributes2;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {
		if (actionName.equals("builtBook")) {
			try {
				// 实际中需要检查类型
				return builtBook((String) params[0], (String) params[1], (String) params[2]);

			} catch (OpenDataException e) {
				throw new MBeanException(e, "错误参数");
			}
		} else if (actionName.equals("addBook")) {
			// 实际中需要检查类型
			CompositeData book = (CompositeData) params[0];
			return addBook(Book.form(book));

		} else if (actionName.equals("list")) {
//			由于list是是一个复杂属性的集合。需要转换成ArrayType的形式传递
			List<Book> list2 = list();
			CompositeData[] listData = new CompositeData[list2.size()];
			for (int i = 0; i < list2.size(); i++) {
				CompositeDataSupport formBookCompositeData = formBookCompositeData(list2.get(i));
				listData[i] = formBookCompositeData;
			}

			return listData;
		} else
			return "错了";
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		return beanInfo;
	}

	// ===============operation,这里的方法由invoke调用,不一定能要公开================
	private CompositeData builtBook(String name, String isbn, String bookMark) throws OpenDataException {
		// 构建一个CompositeData 可以添加方法对数据进行过滤
		// 例如
		if (name.equals("abc")) {
			throw new OpenDataException("abc 不是符合的名字");
		}
		/*
		 * CompositeDataSupport bookmark = new CompositeDataSupport(markType, new
		 * String[] { "bookMark" }, new String[] { bookMark }); CompositeDataSupport
		 * dataSupport = new CompositeDataSupport(bookType, new String[] { "name",
		 * "ISBN", "bookMark" }, new Object[] { name, isbn, bookmark });
		 */
		Book book2 = new Book(name, isbn, new BookMark(bookMark));
		CompositeDataSupport bookData = formBookCompositeData(book2);
		return bookData;
	}

	private boolean addBook(Book book) {
		/*
		 * 将对象添加到集合中,需要检查
		 */ return list.add(book);
	}

	private List<Book> list() {
		return list;
	}

	// ===================other method============================

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