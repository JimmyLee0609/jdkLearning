package openMBeanserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import openmbean.Box;
import openmbean.stuff.Book;
import openmbean.stuff.BookMark;

public class ServerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args)
			throws MalformedObjectNameException, InstanceAlreadyExistsException, NotCompliantMBeanException,
			NoSuchMethodException, SecurityException, OpenDataException, InstanceNotFoundException,
			AttributeNotFoundException, ReflectionException, MBeanException, InvalidAttributeValueException {
		// �½�һ��MBean������
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		// ����һ��MBean,
		// �����jmx�Ĺ��߰��е�һ��html�����������ｫ����Ϊһ��Bean�ŵ��������У��Ϳ���ʹ��HTML��8082�˿ڲ鿴ע�ᵽ�����������MBean
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		// ����HTML�������ķ���������
		Object invoke = server.invoke(html.getObjectName(), "start", new Object[] {}, new String[] {});

		// �½�һ��objectName
		ObjectName name = new ObjectName("domain:name=book");
		// �½�һ��MBean�����MBean��һ��openMBean����ʽ
		Box box = new Box();
		// ��MBeanע�ᵽ��������
		server.registerMBean(box, name);

		// ��ȡһ��������������
		Object attribute2 = server.getAttribute(name, "name");
		// ����һ���������͵�����
		Attribute attribute = new Attribute("name", "�µ�����");
		server.setAttribute(name, attribute);
		
		// ��Ҫ���book���ָ����������ݣ���Ҫʹ���������CompositeData����������

		// �½�һ������������
		CompositeType markType = new CompositeType("stuff.BookMark", "��ҳ", new String[] { "bookMark" },
				new String[] { "��Ҷ������" }, new OpenType[] { SimpleType.STRING });
		// ����һ�����ӵ����������Ͷ�Ӧ�����
		CompositeType bookType = new CompositeType("stuff.Book", "����", new String[] { "name", "ISBN", "bookMark" },
				new String[] { "�������", "ISBN���ΰ���", "�����Ҷ" },
				new OpenType[] { SimpleType.STRING, SimpleType.STRING, markType });
		
		// �������½����Ͷ�Ӧ�����ݽṹ
		CompositeDataSupport bookdata = new CompositeDataSupport(bookType, new String[] { "name", "ISBN", "bookMark" },
				new Object[] { "�µ�����", "�ҵ�ISBNtiao",
						new CompositeDataSupport(markType, new String[] { "bookMark" }, new String[] { "�ҵ�����Ҷ������" }) });

		// �½�һ��Mbean�����ԣ����½������ݽṹ����������
		
		Attribute attributes = new Attribute("book", bookdata);
		// ==========����һ����������=================
		server.setAttribute(name, attributes);
		// ��ȡһ����������
		CompositeData attribute3 = (CompositeData)server.getAttribute(name, "book");
//		����������ת����ԭ�������ͣ�����һ�����ģʽ
		Book form = Book.form(attribute3);
		System.out.println(form);
		
//		=============����Collection<int>����Collection<E>����ΪopenBean��ArrayTypeȻ��ƥ�����ͽ��в���========================
		ArrayList<Book> list2 = new ArrayList<Book>();
		list2.add(new Book("�ҵ��·�����","auhfia",new BookMark("�ҵ��·�����bookmark")));
//		�����Ա�� [] �������ʽ
		Attribute attribute4 = new Attribute("list", list2.toArray(new Book[0]));
		// ���÷������� Collection<E>���͵�����ʹ��CompositeData��Array��ʽ��ʵ��
		server.setAttribute(name, attribute4);
		// ��ȡ������Collection<E>���͵����ԣ�ֱ�ӻ�ȡ������CompositeData[] ͨ�����ģʽ��ת��ʵ������
		CompositeData[] attribute5 = (CompositeData[])server.getAttribute(name, "list");
		System.out.println(Book.form(attribute5[0]));
		
//		����openBean�Ĺ����ӿڷ�����   ��ʵ���п�����˽�еķ�������ΪopenBeanֻ��invoke���Ƿ���
		CompositeData invoke2 = (CompositeData)server.invoke(name, "builtBook",
				new Object[] { "wowowowo", "isbn-aifoau", "bookMark-iuasnhfuia" }, new String[0]);
		
		System.out.println(Book.form(invoke2));
		
//		����һ��CompositeData��Ϊ�������÷���                           �����б���invoke����������ͬ���ķ������������ʱ�����ʵ��
		Object invoke3 = server.invoke(name, "addBook", new Object[] { invoke2 }, null);
		Object invoke4 = server.invoke(name, "addBook", new Object[] { invoke2 }, new String[] {CompositeData.class.getName()});
		System.out.println(invoke3);
		
		
		
//	========��ȡMap���͵�����===============	
//		Map<E,V>���͵�����   TabularData.put(CompositeData)     CompositeData������ֵ��
		TabularData attribute6 = (TabularData)server.getAttribute(name, "map");
//		�������ݹ�����TabularData���ݣ�����ȡ���е�ֵ
		attribute6.values().forEach((data)->{
//			����TabularData������CompositeData
			CompositeData d=(CompositeData)data;
			CompositeType compositeType = d.getCompositeType();
			Set<String> keySet = compositeType.keySet();
			for (String key : keySet) {
//				��ȡCompositeData�е��������ͼ�����
				OpenType<?> type = compositeType.getType(key);
				Object value = d.get(key);
			}
		});
//=====����Map���͵�����===========
		CompositeType mapDetail = new CompositeType("mapDetail", "Map���Եļ�ֵ��", new String[] {"key","value"}, new String[] {"key����","value����"},new OpenType[] {SimpleType.STRING,SimpleType.INTEGER} );
		TabularType map = new TabularType("map", "ӳ��", mapDetail, new String[] {"key","value"});
		TabularDataSupport mapData = new TabularDataSupport(map);
//		��װmap���͵�����
		HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
		hashMap.put("��һ������", 5);
		hashMap.put("�ڶ�������", 15);
		hashMap.put("����������", 25);
//		��Map���͵�ÿ��������װ��CompositeData����Ȼ����ӵ�TabularData��
		for (String key : hashMap.keySet()) {
			Integer value = hashMap.get(key);
			CompositeDataSupport detail = new CompositeDataSupport(mapDetail,new String[] {"key","value"},new Object[] {key,value});
			mapData.put(detail);
		}
//		ʹ��TabularData��װAttribute
		Attribute mapAttr = new Attribute("map",mapData);
//		����������Map���͵����ݣ����ݵ���TabularData������
		server.setAttribute(name, mapAttr);
		//���»�ȡmap�����ԣ��ı�ɹ�
		TabularData attribute7 = (TabularData)server.getAttribute(name, "map");
		System.out.println(attribute7.size());
		
		
//		=====invoke   list==���÷�������һ��CompositeData========
		Object invoke5 = server.invoke(name, "list", new Object[0], new String[0]);
		System.out.println(invoke5);
		
	}

}
