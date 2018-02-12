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
		CompositeType mark = new CompositeType("stuff.BookMark", "��ҳ", new String[] { "bookMark" },
				new String[] { "��Ҷ������" }, new OpenType[] { SimpleType.STRING });
		// ����һ�����ӵ����������Ͷ�Ӧ�����
		CompositeType bookType = new CompositeType("stuff.Book", "����", new String[] { "name", "ISBN", "bookMark" },
				new String[] { "�������", "ISBN���ΰ���", "�����Ҷ" },
				new OpenType[] { SimpleType.STRING, SimpleType.STRING, mark });
		// �������½����Ͷ�Ӧ�����ݽṹ
		CompositeDataSupport support = new CompositeDataSupport(bookType, new String[] { "name", "ISBN", "bookMark" },
				new Object[] { "�µ�����", "�ҵ�ISBNtiao",
						new CompositeDataSupport(mark, new String[] { "bookMark" }, new String[] { "�ҵ�����Ҷ������" }) });

		// �½�һ��Mbean�����ԣ����½������ݽṹ����������
		Attribute attributes = new Attribute("book", support);
		// ʹ�÷�������������
		server.setAttribute(name, attributes);
		// ʹ�÷�������ȡ����
		Object attribute3 = server.getAttribute(name, "book");
		// ��ȡһ����������
		Object list = server.getAttribute(name, "list");
		// ����һ�����м������Եĸ�������
		TabularType tabularType = new TabularType("list", "list of book", bookType,
				new String[] { "name", "ISBN", "bookMark" });
		// ����һ���������Եĸ��ӽṹ
		TabularDataSupport dataSupport = new TabularDataSupport(tabularType);
		// ����һ�������е���Ԫ�صĽṹ
		CompositeDataSupport value = new CompositeDataSupport(bookType, new String[] { "name", "ISBN", "bookMark" },
				new Object[] { "������list�µ�����", "list�ҵ�ISBNtiao", new CompositeDataSupport(mark,
						new String[] { "bookMark" }, new String[] { "list�ҵ�����Ҷ������" + "" }) });
		// �ڼ�����������ӵ�������
		dataSupport.put(value);
		// �½�һ�����ϸ������͵�����
		Attribute attribute4 = new Attribute("list", dataSupport);
		// �����������������
		server.setAttribute(name, attribute4);
		// ��ȡ�޸����Ժ��ֵ
		Object attribute5 = server.getAttribute(name, "list");
		// System.out.println(attribute5);
		CompositeDataSupport newBook = new CompositeDataSupport(bookType, new String[] { "name", "ISBN", "bookMark" },
				new Object[] { "invoke������", "invoke�ҵ�ISBNtiao", new CompositeDataSupport(mark,
						new String[] { "bookMark" }, new String[] { "invoke�ҵ�����Ҷ������" + "" }) });
		Object invoke2 = server.invoke(name, "builtBook",
				new Object[] { "wowowowo", "isbn-aifoau", "bookMark-iuasnhfuia" }, null);
		System.out.println(invoke2);
//		����һ��CompositeData��Ϊ�������÷���                                                     �����б���û���������ַ���,ʵ���п���д��.
		Object invoke3 = server.invoke(name, "addBook", new Object[] { invoke2 }, null);
		System.out.println(invoke3);
	}

}
