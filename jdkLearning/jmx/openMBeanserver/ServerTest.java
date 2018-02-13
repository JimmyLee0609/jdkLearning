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
		// 新建一个MBean服务器
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		// 创建一个MBean,
		// 这个是jmx的工具包中的一个html适配器，这里将他作为一个Bean放到服务器中，就可以使用HTML的8082端口查看注册到这个服务器的MBean
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		// 启用HTML适配器的服务器功能
		Object invoke = server.invoke(html.getObjectName(), "start", new Object[] {}, new String[] {});

		// 新建一个objectName
		ObjectName name = new ObjectName("domain:name=book");
		// 新建一个MBean，这个MBean是一个openMBean的形式
		Box box = new Box();
		// 将MBean注册到服务器中
		server.registerMBean(box, name);

		// 获取一个基本数据类型
		Object attribute2 = server.getAttribute(name, "name");
		// 设置一个基础类型的数据
		Attribute attribute = new Attribute("name", "新的名字");
		server.setAttribute(name, attribute);
		
		// 想要变更book这种复杂属性内容，需要使用组合数据CompositeData来传递数据

		// 新建一个复杂数据体
		CompositeType markType = new CompositeType("stuff.BookMark", "书页", new String[] { "bookMark" },
				new String[] { "树叶的描述" }, new OpenType[] { SimpleType.STRING });
		// 构建一个复杂的数据体类型对应这个类
		CompositeType bookType = new CompositeType("stuff.Book", "书名", new String[] { "name", "ISBN", "bookMark" },
				new String[] { "书的名字", "ISBN条形阿门", "书的树叶" },
				new OpenType[] { SimpleType.STRING, SimpleType.STRING, markType });
		
		// 构建与新建类型对应的数据结构
		CompositeDataSupport bookdata = new CompositeDataSupport(bookType, new String[] { "name", "ISBN", "bookMark" },
				new Object[] { "新的书名", "我的ISBNtiao",
						new CompositeDataSupport(markType, new String[] { "bookMark" }, new String[] { "我的新树叶！！！" }) });

		// 新建一个Mbean的属性，将新建的数据结构传入属性中
		
		Attribute attributes = new Attribute("book", bookdata);
		// ==========设置一个复杂类型=================
		server.setAttribute(name, attributes);
		// 获取一个复杂类型
		CompositeData attribute3 = (CompositeData)server.getAttribute(name, "book");
//		将复杂类型转换成原来的类型，这是一种设计模式
		Book form = Book.form(attribute3);
		System.out.println(form);
		
//		=============对于Collection<int>或者Collection<E>都视为openBean的ArrayType然后匹配类型进行操作========================
		ArrayList<Book> list2 = new ArrayList<Book>();
		list2.add(new Book("我的新方法书","auhfia",new BookMark("我的新方法书bookmark")));
//		将属性变成 [] 数组的形式
		Attribute attribute4 = new Attribute("list", list2.toArray(new Book[0]));
		// 设置服务器的 Collection<E>类型的属性使用CompositeData的Array形式来实现
		server.setAttribute(name, attribute4);
		// 获取服务器Collection<E>类型的属性，直接获取到的是CompositeData[] 通过设计模式来转换实际类型
		CompositeData[] attribute5 = (CompositeData[])server.getAttribute(name, "list");
		System.out.println(Book.form(attribute5[0]));
		
//		调用openBean的公开接口方法，   类实现中可以是私有的方法，因为openBean只查invoke中是否有
		CompositeData invoke2 = (CompositeData)server.invoke(name, "builtBook",
				new Object[] { "wowowowo", "isbn-aifoau", "bookMark-iuasnhfuia" }, new String[0]);
		
		System.out.println(Book.form(invoke2));
		
//		传递一个CompositeData作为参数调用方法                           参数列表在invoke中用来区分同名的方法，构建类的时候可以实现
		Object invoke3 = server.invoke(name, "addBook", new Object[] { invoke2 }, null);
		Object invoke4 = server.invoke(name, "addBook", new Object[] { invoke2 }, new String[] {CompositeData.class.getName()});
		System.out.println(invoke3);
		
		
		
//	========获取Map类型的属性===============	
//		Map<E,V>类型的属性   TabularData.put(CompositeData)     CompositeData包含键值对
		TabularData attribute6 = (TabularData)server.getAttribute(name, "map");
//		遍历传递过来的TabularData数据，并获取其中的值
		attribute6.values().forEach((data)->{
//			遍历TabularData的所有CompositeData
			CompositeData d=(CompositeData)data;
			CompositeType compositeType = d.getCompositeType();
			Set<String> keySet = compositeType.keySet();
			for (String key : keySet) {
//				获取CompositeData中的数据类型及数据
				OpenType<?> type = compositeType.getType(key);
				Object value = d.get(key);
			}
		});
//=====设置Map类型的属性===========
		CompositeType mapDetail = new CompositeType("mapDetail", "Map属性的键值对", new String[] {"key","value"}, new String[] {"key描述","value描述"},new OpenType[] {SimpleType.STRING,SimpleType.INTEGER} );
		TabularType map = new TabularType("map", "映射", mapDetail, new String[] {"key","value"});
		TabularDataSupport mapData = new TabularDataSupport(map);
//		组装map类型的数据
		HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
		hashMap.put("第一个数据", 5);
		hashMap.put("第二个数据", 15);
		hashMap.put("第三个数据", 25);
//		将Map类型的每个数据组装成CompositeData类型然后添加到TabularData中
		for (String key : hashMap.keySet()) {
			Integer value = hashMap.get(key);
			CompositeDataSupport detail = new CompositeDataSupport(mapDetail,new String[] {"key","value"},new Object[] {key,value});
			mapData.put(detail);
		}
//		使用TabularData组装Attribute
		Attribute mapAttr = new Attribute("map",mapData);
//		服务器设置Map类型的数据，传递的是TabularData的类型
		server.setAttribute(name, mapAttr);
		//重新获取map的属性，改变成功
		TabularData attribute7 = (TabularData)server.getAttribute(name, "map");
		System.out.println(attribute7.size());
		
		
//		=====invoke   list==调用方法返回一个CompositeData========
		Object invoke5 = server.invoke(name, "list", new Object[0], new String[0]);
		System.out.println(invoke5);
		
	}

}
