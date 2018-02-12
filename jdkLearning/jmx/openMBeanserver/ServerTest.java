package dynamic.server;

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
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import dynamic.mbean.Box;

public class ServerTest {

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
		CompositeType mark = new CompositeType("stuff.BookMark", "书页", new String[] { "bookMark" },
				new String[] { "树叶的描述" }, new OpenType[] { SimpleType.STRING });
		// 构建一个复杂的数据体类型对应这个类
		CompositeType bookType = new CompositeType("stuff.Book", "书名", new String[] { "name", "ISBN", "bookMark" },
				new String[] { "书的名字", "ISBN条形阿门", "书的树叶" },
				new OpenType[] { SimpleType.STRING, SimpleType.STRING, mark });
		// 构建与新建类型对应的数据结构
		CompositeDataSupport support = new CompositeDataSupport(bookType, new String[] { "name", "ISBN", "bookMark" },
				new Object[] { "新的书名", "我的ISBNtiao",
						new CompositeDataSupport(mark, new String[] { "bookMark" }, new String[] { "我的新树叶！！！" }) });

		// 新建一个Mbean的属性，将新建的数据结构传入属性中
		Attribute attributes = new Attribute("book", support);
		// 使用服务器设置属性
		server.setAttribute(name, attributes);
		// 使用服务器获取属性
		Object attribute3 = server.getAttribute(name, "book");
		// 获取一个集合属性
		Object list = server.getAttribute(name, "list");
		// 构建一个带有集合属性的复杂属性
		TabularType tabularType = new TabularType("list", "list of book", bookType,
				new String[] { "name", "ISBN", "bookMark" });
		// 构建一个集合属性的复杂结构
		TabularDataSupport dataSupport = new TabularDataSupport(tabularType);
		// 构建一个集合中单个元素的结构
		CompositeDataSupport value = new CompositeDataSupport(bookType, new String[] { "name", "ISBN", "bookMark" },
				new Object[] { "啊啊啊list新的书名", "list我的ISBNtiao", new CompositeDataSupport(mark,
						new String[] { "bookMark" }, new String[] { "list我的新树叶！！！" + "" }) });
		// 在集合属性中添加单个属性
		dataSupport.put(value);
		// 新建一个集合复杂类型的属性
		Attribute attribute4 = new Attribute("list", dataSupport);
		// 服务器设置这个属性
		server.setAttribute(name, attribute4);
		// 获取修改属性后的值
		Object attribute5 = server.getAttribute(name, "list");
		// System.out.println(attribute5);
		CompositeDataSupport newBook = new CompositeDataSupport(bookType, new String[] { "name", "ISBN", "bookMark" },
				new Object[] { "invoke新书名", "invoke我的ISBNtiao", new CompositeDataSupport(mark,
						new String[] { "bookMark" }, new String[] { "invoke我的新树叶！！！" + "" }) });
		Object invoke2 = server.invoke(name, "builtBook",
				new Object[] { "wowowowo", "isbn-aifoau", "bookMark-iuasnhfuia" }, null);
		System.out.println(invoke2);
//		传递一个CompositeData作为参数调用方法                                                     参数列表我没有用来区分方法,实际中可以写上.
		Object invoke3 = server.invoke(name, "addBook", new Object[] { invoke2 }, null);
		System.out.println(invoke3);
	}

}
