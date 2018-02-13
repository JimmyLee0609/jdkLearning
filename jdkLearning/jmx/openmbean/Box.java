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
	// һ�����ӵ�����
	Book book = new Book("myBook", "myISBN", new BookMark("myBookMark"));
	// һ������������
	String name;
	// һ�����ϵ�����
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

	// ������������
	CompositeType bookType = null;
	CompositeType markType = null;
	TabularType mapType;
	CompositeType mapCompositeType;

	public Box() throws NoSuchMethodException, SecurityException, OpenDataException {
		/*
		 * ���ﹹ�����Ϣ��������������ӿڵ���Ϣ����ʾ�ڹ���ӿ��У� ����openMBean������operation�еķ���������invoke�������ġ�
		 * ������Ҫע�����ǵĲ����ͷ���ֵ����Ϣ�Թ����á� HTML�������ж�Compositedata�ĸ���������Ϊ����û���ṩʵ�֣���������Ƕ�ף�ʵ������Ҫע�⡣
		 * ����,�ڹ���Type,������CompositeType ,����TabularType��ʱ��,����,����һ������,
		 * ����ͱ���ʹ����ķ���,���Ҹ���Ӧ�������.
		 * CompositeDataSupport�е�����,����ҲҪ��CompositeType�Ķ�Ӧ.TabularTypeҲ��һ��
		 */
		// ==========����===============
		// ����һ��name����
		OpenMBeanAttributeInfoSupport name = new OpenMBeanAttributeInfoSupport("name", "MING ZI", SimpleType.STRING,
				true, true, false);

		// =================����һ��book����============================
		// �½���������BookMark������
		markType = new CompositeType("stuff.BookMark", "bookMark", new String[] { "bookMark" }, new String[] { "��Ҷ" },
				new OpenType[] { SimpleType.STRING });
		// �½���������Book������
		bookType = new CompositeType("stuff.Book", "books", new String[] { "name", "ISBN", "bookMark" },
				new String[] { "mingzi ", "������", "��Ҷ" },
				new OpenType[] { SimpleType.STRING, SimpleType.STRING, markType });
		// ��������
		OpenMBeanAttributeInfoSupport support = new OpenMBeanAttributeInfoSupport("book", "�鱾", bookType, true, true,
				false);
		// ==========����һ������bookList����========================
		// Collection����Ϊ [ ]����
		ArrayType<?> bookListType = new ArrayType<>(1, bookType);
		OpenMBeanAttributeInfoSupport openMBeanAttributeInfoSupport = new OpenMBeanAttributeInfoSupport("list",
				"listbook", bookListType, true, true, false);

		// ============����һ��map����=====================================
		OpenType[] openType = new OpenType[] { SimpleType.STRING, SimpleType.INTEGER };
		String[] keyValue = new String[] { "key", "value" };
		// ÿһ��ӳ����������ͣ�����������ӳ������ͣ��Ա�����ת��
		mapCompositeType = new CompositeType("java.lang.String,java.lang.Integer", "mapType", keyValue, keyValue,
				openType);
		mapType = new TabularType("map", "ӳ�伯��", mapCompositeType, new String[] { "key", "value" });

		OpenMBeanAttributeInfoSupport mapAttr = new OpenMBeanAttributeInfoSupport("map", "mapӳ�伯��", mapType, true, true,
				false);

		OpenMBeanAttributeInfo[] openAttributes = new OpenMBeanAttributeInfo[] { openMBeanAttributeInfoSupport, support,
				name, mapAttr };

		// ================������========================
		OpenMBeanConstructorInfo[] openConstructors = new OpenMBeanConstructorInfo[1];
		Constructor con = this.getClass().getConstructors()[0];
		OpenMBeanConstructorInfoSupport openMBeanConstructorInfoSupport = new OpenMBeanConstructorInfoSupport(
				con.getName(), "������", new OpenMBeanParameterInfoSupport[0]);
		openConstructors[0] = openMBeanConstructorInfoSupport;

		// =====operation====================

		// ���﹫����operation���ڽ�������ʾ�����ǽ�����÷�����ʱ���ʹ��invoke()����װ��Ҳ����ʹ��dynamic�ӿڵ�invoke����װ����Ȼ�����
		// ===============builtBook operation===============================
		// ����3������
		OpenMBeanParameterInfoSupport paraInfo = new OpenMBeanParameterInfoSupport("name", "�鼮������", SimpleType.STRING);
		OpenMBeanParameterInfoSupport isbn = new OpenMBeanParameterInfoSupport("isbn", "�鼮��isbn", SimpleType.STRING);
		OpenMBeanParameterInfoSupport bookMark = new OpenMBeanParameterInfoSupport("bookMark", "�鼮����Ҷ",
				SimpleType.STRING);
		// �ŵ�������
		OpenMBeanParameterInfo[] signature = new OpenMBeanParameterInfo[] { paraInfo, isbn, bookMark };
		// ���������ķ���builtBook
		OpenMBeanOperationInfoSupport openMBeanOperationInfoSupport = new OpenMBeanOperationInfoSupport("builtBook",
				"�½��鱾", signature, bookType, MBeanOperationInfo.UNKNOWN);
		// =================addBook operation =================
		// �½�һ������
		OpenMBeanParameterInfoSupport bookParameter = new OpenMBeanParameterInfoSupport("book", "���CompositeData",
				bookType);
		// �������ŵ�������
		OpenMBeanParameterInfo[] addBookSignature = new OpenMBeanParameterInfo[] { bookParameter };
		// ���������ķ���addBook
		OpenMBeanOperationInfoSupport addBook = new OpenMBeanOperationInfoSupport("addBook", "����鱾", addBookSignature,
				SimpleType.BOOLEAN, MBeanOperationInfo.INFO);

		// ============listBook operation==============================
		// ����һ��list,�г��鼮���ϵķ���
		OpenMBeanOperationInfoSupport list = new OpenMBeanOperationInfoSupport("list", "�г����е��鼮",
				new OpenMBeanParameterInfo[0], bookListType, MBeanOperationInfo.INFO);

		OpenMBeanOperationInfo[] openOperations = new OpenMBeanOperationInfo[] { openMBeanOperationInfoSupport, addBook,
				list };

		// ======notification=================
		MBeanNotificationInfo notificationInfo = new MBeanNotificationInfo(new String[] { "jmx.attribute.change" },
				"openmbean.Box", "֪ͨ");
		MBeanNotificationInfo[] notifications = new MBeanNotificationInfo[] { notificationInfo };

		// ===========openMBeanInfo========================
		beanInfo = new OpenMBeanInfoSupport(Box.class.getName(), "box book", openAttributes, openConstructors,
				openOperations, notifications);
		map.put("��", 5);
	}

	MBeanInfo beanInfo;

	// ===============Dynamic�ӿڵķ���==============================
	@Override
	public Object getAttribute(String attribute)
			throws AttributeNotFoundException, MBeanException, ReflectionException {
		// ���ݴ������Ե����ַ��ض�Ӧ�����Ե�ֵ
		if (attribute.equals("name"))
			return getName();
		else if (attribute.equals("book")) {
			// ������һ���������ԣ��������������ֻ�ȡ����ֵ��ʱ����Ҫ�����Եľ�����ת����ΪCompositeData�����ݳ�ȥ������openBean��һ����ʽ
			CompositeDataSupport bookData = formBookCompositeData(this.book);
			return bookData;
		} else if (attribute.equals("list")) {
			// ������һ��Collection<E>��ʱ�����Ҫ������ת��ΪComposite��ArrayType�����ݳ�ȥ
			CompositeData[] array = new CompositeData[list.size()];
			for (int i = 0; i < list.size(); i++) {
				array[i] = formBookCompositeData(list.get(i));
			}
			return array;
		} else if (attribute.equals("map")) {
			// ������һ��Map���ͣ���Ҫת��ΪTabularData����ʽ����
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
		// =======�Ӷ�����CompositeData===========
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
		// ���ݴ�������Ե����ֺ�ֵ��Ϊ����ֵ����һ���µ�ֵ
		if (attribute.getName().equals("name"))
			// ��������
			setName((String) attribute.getValue());
		else if (attribute.getName().equals("book")) {
			// ��������
			CompositeData value = (CompositeData) attribute.getValue();

			// setBook(value);
		} else if (attribute.getName().equals("list")) {
			// ���Ӽ�������
			Book[] value = (Book[]) attribute.getValue();
			ArrayList<Book> booklist = new ArrayList<Book>(Arrays.asList(value));
			setList(booklist);
		} else if (attribute.getName().equals("map")) {
			// Map<V,E>���͵ĵ��������ã��������TabularData
			TabularData mapData = (TabularData) attribute.getValue();
			// ����TabularData������ֵ��ÿ������CompositeData
			Collection<?> values = mapData.values();
			map.clear();
			for (Iterator it = values.iterator(); it.hasNext();) {
				CompositeData compositeData = (CompositeData) it.next();
				// ��ȡComposite�б����Դ�������ͣ�������Map����������װ��ӵ�ӳ����
				CompositeType compositeType = compositeData.getCompositeType();
				Set<String> keySet = compositeType.keySet();
				String mapKey = "";
				Integer mapValue = 0;
				for (String key : keySet) {
					OpenType<?> type = compositeType.getType(key);
					// ��ȡCompositeData��ֵ
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
		// ʵ������Ҫ���
		// ��ȡһϵ�е����ԣ������Dynamic�ӿڵı�׼
		// �½�һ��AttributeList���������ԣ�
		AttributeList list2 = new AttributeList();
		for (String attr : attributes) {
			try {
				// ������������ֻ�ȡ���ԣ�����ֵ������װ�����Ե�����
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
		// ʵ������Ҫ���
		ArrayList<String> list = new ArrayList<String>();
		for (Iterator<Object> it = attributes.iterator(); it.hasNext();) {
			// ������������Լ���������ÿ���������֣��趨���е�ֵ
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
		// ��ȡ������������ֲ���ȡ��Ӧ��ֵ
		String[] array = list.toArray(new String[0]);
		AttributeList attributes2 = getAttributes(array);
		return attributes2;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {
		if (actionName.equals("builtBook")) {
			try {
				// ʵ������Ҫ�������
				return builtBook((String) params[0], (String) params[1], (String) params[2]);

			} catch (OpenDataException e) {
				throw new MBeanException(e, "�������");
			}
		} else if (actionName.equals("addBook")) {
			// ʵ������Ҫ�������
			CompositeData book = (CompositeData) params[0];
			return addBook(Book.form(book));

		} else if (actionName.equals("list")) {
//			����list����һ���������Եļ��ϡ���Ҫת����ArrayType����ʽ����
			List<Book> list2 = list();
			CompositeData[] listData = new CompositeData[list2.size()];
			for (int i = 0; i < list2.size(); i++) {
				CompositeDataSupport formBookCompositeData = formBookCompositeData(list2.get(i));
				listData[i] = formBookCompositeData;
			}

			return listData;
		} else
			return "����";
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		return beanInfo;
	}

	// ===============operation,����ķ�����invoke����,��һ����Ҫ����================
	private CompositeData builtBook(String name, String isbn, String bookMark) throws OpenDataException {
		// ����һ��CompositeData ������ӷ��������ݽ��й���
		// ����
		if (name.equals("abc")) {
			throw new OpenDataException("abc ���Ƿ��ϵ�����");
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
		 * ��������ӵ�������,��Ҫ���
		 */ return list.add(book);
	}

	private List<Book> list() {
		return list;
	}

	// ===================other method============================

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