package tjava.managerment.srever;

import java.util.Hashtable;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.Query;
import javax.management.loading.ClassLoaderRepository;

import tjavax.managerment.standardbean.StandardD;

public class MBeanServerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		standardMBean();
	}

	@SuppressWarnings("unused")
	private static void standardMBean() throws Exception {
		// 新建一个默认的服务器
		MBeanServer server = MBeanServerFactory.newMBeanServer();
		// 获取默认的主机名字
		String defaultDomain = server.getDefaultDomain();// DefaultDomain
		// 返回任何MBean当前注册的域的列表。 一个字符串在返回的数组中，当且仅当至少有一个MBean注册了一个ObjectName，
		// 它的getDomain（）等于该字符串。 没有定义返回数组中字符串的顺序。
		String[] domains = server.getDomains();// 默认添加的JMImplementation
		// 获取当前注册的MBean的数量
		Integer mBeanCount = server.getMBeanCount();// 1

		StandardD standardD = new StandardD();
		ObjectName objectName = new ObjectName("DefaultDomain:name=tjavax.managerment.standardbean.Standard");
		// 注册一个标准的MBean到服务器中
		ObjectInstance registerMBean = server.registerMBean(standardD, objectName);
		objectInstance(registerMBean);

		// 获取已经注册到服务器的Domain名字
		String[] domains2 = server.getDomains();
		// 在服务器中实例化并注册一个MBean,服务器使用默认的加载器来加载类文件, 使用的是空参的构造器
		ObjectInstance createMBean = server.createMBean("tjavax.managerment.standardbean.StandardD",
				new ObjectName(":name=suppli"));

		// 使用指定的构造器来实例化MBean 并注册
		ObjectInstance createMBean3 = server.createMBean("tjavax.managerment.standardbean.StandardD",
				new ObjectName(":name=construct"), new Object[] { true, true },
				new String[] { Boolean.TYPE.getName(), Boolean.TYPE.getName() });
		// 传入的参数 构造器的签名

		// 使用指定的classLoader来加载类文件,然后实例化MBean并注册. 一般用在MLET就是applet中，其他没见过。
//		Mlet需要先注册到server,然后用注册的ObjectName来检索注册的Mlet从而使用自定义的类加载器
		// ObjectInstance createMBean4 =
		// server.createMBean("tjavax.managerment.standardbean.StandardD", new
		// ObjectName(":name=setloader"), new ObjectName("loader"));

		// 返回此MBeanServer的ClassLoaderRepository。
		ClassLoaderRepository repository = server.getClassLoaderRepository();

		// 获取在MBean服务器中注册的给定MBean的ObjectInstance。
		ObjectInstance objectInstance = server.getObjectInstance(objectName);

		// 这种方法发现了MBean管理的属性和操作。
		MBeanInfo mBeanInfo = server.getMBeanInfo(objectName);
		// 获取指定MBean的特定属性的值。 MBean由其对象名称标识。
		Object attribute = server.getAttribute(objectName, "TraceOn");
		// 检索指定MBean的几个属性的值
		AttributeList attributes = server.getAttributes(objectName, new String[] { "TraceOn", "DebugOn" });

		// 返回指定ObjectName的类加载器,就是注册到服务器的类加载器
		// ClassLoader classLoader = server.getClassLoader(loadername);

		// 返回加载指定ObjectName的类加载器
		ClassLoader classLoaderFor = server.getClassLoaderFor(objectName);

		// 使用在MBean服务器的Class Loader Repository中注册的所有类加载器的列表来实例化一个对象。 该对象的类应该有一个公共构造函数。
		// 此方法返回对新创建的对象的引用。 新创建的对象未在MBean服务器中注册。
		Object instantiate = server.instantiate("tjavax.managerment.standardbean.StandardD");
		// 使用指定的构造器来初始化,发挥的是实例的引用
		Object instantiate2 = server.instantiate("tjavax.managerment.standardbean.StandardD",
				new Object[] { true, true }, new String[] { Boolean.TYPE.getName(), Boolean.TYPE.getName() });
		// 使用指定加载器来加载, 一般用在MLet中
		// server.instantiate(className, loaderName)
		// 使用指定的类加载器和指定的构造器来加载 一般用在MLet中
		// server.instantiate(className, loaderName, params, signature)

		// 指定名字的mbean是否注册
		boolean registered = server.isRegistered(objectName);

		// 调用指定mbean名字的指定方法
		Object invoke = server.invoke(objectName, "enableTracing", new Object[] {}, new String[] {});
		/*
		 * 获取由MBean服务器控制的MBean。
		 * 此方法允许获得以下任何一项：所有MBean，一组通过ObjectName和/或Query表达式上的模式匹配指定的MBean，一个特定的MBean。
		 * 当对象名称为空或者没有指定域和键属性时，将选择所有对象（如果指定了查询，则进行过滤）。
		 * 它返回所选MBean的ObjectInstance对象集合（包含ObjectName和Java Class名称）。
		指定范围找objectName                                                                 可以是通配符                              限定实现类中的属性值的范围 */										
		Set<ObjectInstance> queryMBeans = server.queryMBeans(objectName, Query.eq(Query.attr("TraceOn"), Query.value(true)));
		
		/*获取由MBean服务器控制的MBean的名称。 此方法可以获得以下任何一项：
		 * 所有MBean的名称，由ObjectName和/或Query表达式上的模式匹配指定的一组MBean的名称，
		 * 特定的MBean名称（等同于测试MBean是否 注册）。 
		 * 当对象名称为空或没有指定域和键属性时，将选择所有对象（并在指定查询时进行过滤）。 
		 * 它返回所选MBean的一组ObjectName。*/
//		查找指定范围的   ObjectName                                             可以是通配符的形式                              限定实现类中属性值的具体范围
		Set<ObjectName> queryNames = server.queryNames(createMBean.getObjectName(), Query.eq(Query.attr("TraceOn"), Query.value(true)));

		// 为指定的MBean设置属性,标准的MBean需要属性可写,就是 接口需要有set的方法
		server.setAttribute(objectName, new Attribute("TraceOn", false));
		// 让服务器将指定的MBean解除绑定
		server.unregisterMBean(objectName);
//		匹配到具体的objectName:  DefaultDomain:name=suppli
		String string = server.toString();
	}

	@SuppressWarnings("unused")
	private static void objectInstance(ObjectInstance registerMBean)
			throws MalformedObjectNameException, InstanceNotFoundException {
		/*
		 * ObjectInstance 用于表示MBean的对象名称及其类名。 如果MBean是一个动态MBean，则应该从它提供的MBeanInfo中检索类名。
		 */

		// 获取注册到服务器的名字，相当于一个协议的格式
		// 表示MBean的对象名称，或者可以匹配几个MBean的名称的模式。 这个类的实例是不可变的。
		ObjectName objectName = registerMBean.getObjectName();
		objectName(objectName);
		// 获取保存的全类名，
		String className = registerMBean.getClassName();
	}

	@SuppressWarnings("unused")
	private static void objectName(ObjectName objectName)
			throws MalformedObjectNameException, InstanceNotFoundException {
		// objectName： DefaultDomain:name=tjavax.managerment.standardbean.Standard
		// 返回域部分。
		String domain = objectName.getDomain();// DefaultDomain
		/*
		 * 返回创建时指定的键属性列表的字符串表示形式。 如果此ObjectName是使用构造函数ObjectName（String）构造的，
		 * 则返回的String中的键属性的顺序与构造函数的参数顺序相同。
		 */
		String keyPropertyListString = objectName.getKeyPropertyListString();// name=tjavax.managerment.standardbean.Standard
		/*
		 * 返回关键属性列表的字符串表示形式，其中关键属性按照词汇顺序排序。 这用于执行字典对比，以便根据关键属性列表选择MBean。
		 * 词汇顺序是由String.compareTo（String）暗示的顺序。
		 */
		String canonicalKeyPropertyListString = objectName.getCanonicalKeyPropertyListString();// name=tjavax.managerment.standardbean.Standard
		/*
		 * 返回名称的规范形式; 也就是说，一个字符串表示，其中的属性按词法顺序排序。
		 * 更确切地说，名称的规范形式是一个由域名部分，冒号（:)，规范的密钥属性列表和模式指示组成的字符串。
		 * 规范键属性列表与getCanonicalKeyPropertyListString（）所描述的是相同的字符串。 模式指示是：
		 * 对于不是属性列表模式的ObjectName是空的; 一个ObjectName的星号，它是一个没有键的属性列表模式; 要么
		 * 一个逗号和一个星号（，*）作为一个至少有一个键的属性列表模式。
		 */
		String canonicalName = objectName.getCanonicalName();// DefaultDomain:name=tjavax.managerment.standardbean.Standard
		/*
		 * 将键属性作为散列表返回。 返回的值是一个Hashtable， 其中每个键都是ObjectName的键属性列表中的一个键，每个值都是关联的值。
		 * 返回的值可能是不可修改的。 如果它是可修改的，改变它对这个ObjectName没有影响。
		 */
		Hashtable<String, String> keyPropertyList = objectName.getKeyPropertyList();// name=tjavax.managerment.standardbean.Standard
		// 检查对象名称是否为域部分的模式。
		boolean domainPattern = objectName.isDomainPattern();// false
		/*
		 * 检查对象名称是否是模式。 如果对象名称的域包含通配符，或者对象名称是属性模式，则对象名称是模式。
		 */
		boolean pattern = objectName.isPattern();// false
		boolean propertyListPattern = objectName.isPropertyListPattern();// false
		/*
		 * 检查对象名称是否是键属性列表上的模式。 例如，“d：k = v，*”和“d：k = *，*”是关键属性列表模式，而“d：k = *”不是。
		 */
		boolean propertyPattern = objectName.isPropertyPattern();// false
		/*
		 * 检查对象名称是否是至少一个键属性值部分的模式。 例如，“d：k = *”和“d：k = *，*”是属性值模式，而“d：k = v，*”不是。
		 */
		boolean propertyValuePattern = objectName.isPropertyValuePattern();// false
		// 检查与键属性中的键相关联的值是否是模式。 需要有这个key
		boolean propertyValuePattern2 = objectName.isPropertyValuePattern("name");// false

//		===================pattern   模式=====================================
		pattern(objectName);
		
		// 测试这个ObjectName（可能是一个模式）是否与另一个ObjectName相匹配。
//		如果是模式匹配，需要是          模式.apply(具体)，如果模式符合具体就true
		boolean apply = objectName.apply(objectName);// true
		// 比较两个objectName是否匹配
		int compareTo = objectName.compareTo(objectName);// 0
		// 获取与键属性中的键相关联的值。
		String keyProperty = objectName.getKeyProperty("name");// "" ->null
																// name->tjavax.managerment.standardbean.Standard
		objectName.toString();

		// 定义的objectName的通配符
		ObjectName wildcard = ObjectName.WILDCARD;// *:*
		// ==========构造函数========================
		// 从给定的字符串构造一个对象名称。
		ObjectName objectName2 = new ObjectName("default:name=bbc");// default:name=bbc
		// 只用一个关键属性构造一个对象名称。
		ObjectName objectName3 = new ObjectName("default", "key", "value");// default:key=value

		Hashtable<String, String> table = new Hashtable<String, String>();
		String put = table.put("test", "value");
		// 用Hashtable中的几个关键属性构造一个对象名称。
		ObjectName objectName4 = new ObjectName("domain", table);// domain:test=value

		/*
		 * 返回可以在给定对象可以使用的任何地方使用的ObjectName实例。 返回的对象可能是ObjectName的一个子类。
		 * 如果name是ObjectName的子类，则不能保证返回的对象是同一个类。 返回的值可能与名称相同也可能不同。
		 * 使用相同的参数调用这个方法两次可能返回相同的对象或两个相等但不相同的对象。
		 * 由于ObjectName是不可变的，所以通常不需要创建一个ObjectName的副本。
		 * 这种方法的主要用途是防止恶意的调用者将一个子类的实例以令人惊讶的行为传递给敏感的代码。
		 * 这样的代码可以调用这个方法来获得已知的没有令人惊讶的行为的ObjectName。
		 */
		ObjectName instance = ObjectName.getInstance(objectName);// DefaultDomain:name=tjavax.managerment.standardbean.Standard
		/*
		 * 返回ObjectName的一个实例，可以在使用新的ObjectName（name） 获得的对象可以使用的任何地方使用该实例。
		 * 返回的对象可能是ObjectName的一个子类。 使用相同的参数调用这个方法两次可能返回相同的对象或两个相等但不相同的对象。
		 */
		ObjectName instance2 = ObjectName.getInstance("default:name=bbc");// default:name=bbc
		/*
		 * 返回ObjectName的一个实例，可以在使用新ObjectName（域，键，值）获取的对象的任何地方使用该实例。
		 * 返回的对象可能是ObjectName的一个子类。 使用相同的参数调用这个方法两次可能返回相同的对象或两个相等但不相同的对象。
		 */
		ObjectName instance3 = ObjectName.getInstance("domain", "key", "value");// domain:key=value
		/*
		 * 返回ObjectName的一个实例，可以在使用新的ObjectName（domain，table）获取的对象的任何地方使用。
		 * 返回的对象可能是ObjectName的一个子类。 使用相同的参数调用这个方法两次可能返回相同的对象或两个相等但不相同的对象。
		 */
		ObjectName instance4 = ObjectName.getInstance(domain, table);// DefaultDomain:test=value
		
		/*返回给定String的引用形式，适合包含在ObjectName中。 返回的值可以用作与ObjectName中的键关联的值。 字符串可以包含任何字符。 适当的引用可以确保返回的值在ObjectName中是合法的。
		就是为字符串添加引号
		返回的值由一个quote（'“'），一个与s的字符对应的字符序列和另一个quote组成。
		     一个引号（'“'）被一个反斜杠（\）后跟一个引号所取代。
		     星号（'*'）被替换为反斜杠（\），后面跟星号。
		     问号（'？'）被替换为反斜杠（\），后跟一个问号。
		     反斜杠（'\'）被替换为两个反斜杠。
		     换行符（Java中的字符'\ n'）被反斜杠替换，后面跟着字符'\ n'。*/
		String quote = ObjectName.quote("*");// "\*"
		String quote2 = ObjectName.quote("default:name=bbc");// "default:name=bbc"
		/*返回给定String的未加引号形式。 如果q是一个由quote（s）返回的String，则取消（q）.equals（s）。
		 *  如果没有引用.equals（q）的String，则取消引用（q）将引发IllegalArgumentException。
		这些规则意味着在引用和不引用的表单之间有一对一的映射关系。*/
		String unquote = ObjectName.unquote("\"\\*\"");//*
		String unquote2 = ObjectName.unquote("\"default:name=bbc\"");//default:name=bbc
		String unquote3 = ObjectName.unquote(quote2);//default:name=bbc
		
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		// 设置要在其上执行查询的MBean服务器。
//		从QueryExp继承的方法，这里是空实现，因为ObjectName不是相对于MBeanServer的，并且不包含子查询。
		instance4.setMBeanServer(server);
	}

	@SuppressWarnings("unused")
	private static void pattern(ObjectName objectName) throws MalformedObjectNameException {
		String string = objectName.toString();//DefaultDomain:name=tjavax.managerment.standardbean.Standard
		
//		==============domain模式=====================
		ObjectName objectName2 = new ObjectName("*:name=tjavax.managerment.standardbean.Standard");
		boolean domainPattern3 = objectName2.isDomainPattern();//true
		boolean pattern3 = objectName2.isPattern();//true
		boolean propertyListPattern3 = objectName2.isPropertyListPattern();//false
		boolean propertyPattern3 = objectName2.isPropertyPattern();//false
		boolean propertyValuePattern3 = objectName2.isPropertyValuePattern();//false
		boolean apply3 = objectName.apply(objectName2);//false
		boolean apply8 = objectName2.apply(objectName);//true
		
//		=======domain和属性模式======================
		ObjectName domain = new ObjectName("*:*");
		boolean domainPattern = domain.isDomainPattern();//true
		boolean pattern = domain.isPattern();//true
		boolean propertyListPattern = domain.isPropertyListPattern();//true
		boolean propertyPattern = domain.isPropertyPattern();//true
		boolean propertyValuePattern = domain.isPropertyValuePattern();//false
		boolean apply = domain.apply(objectName);//true
		boolean apply9 = objectName.apply(domain);//false
//		============属性，属性列表模式=======================
		ObjectName property = new ObjectName("DefaultDomain:*");
		boolean domainPattern2 = property.isDomainPattern();//false
		boolean pattern2 = property.isPattern();//true
		boolean propertyListPattern2 = property.isPropertyListPattern();//true
		boolean propertyPattern2 = property.isPropertyPattern();//true
		boolean propertyValuePattern2 = property.isPropertyValuePattern();//false
		boolean apply2 = objectName.apply(property);//false
		boolean apply10 = property.apply(objectName);//true
//		==================属性，属性值模式==========================
		ObjectName objectName3 = new ObjectName("DefaultDomain:name=*");
		boolean domainPattern4 = objectName3.isDomainPattern();//false
		boolean pattern4 = objectName3.isPattern();//true
		boolean propertyListPattern4 = objectName3.isPropertyListPattern();//false
		boolean propertyPattern4 = objectName3.isPropertyPattern();//true
		boolean propertyValuePattern4 = objectName3.isPropertyValuePattern();//true
		boolean apply4 = objectName.apply(objectName3);//false
		boolean apply7 = objectName3.apply(objectName);//true
		
		
//		==========属性，属性列表模式===================
		ObjectName objectName4 = new ObjectName("DefaultDomain:name=tjavax.managerment.standardbean.Standard,*");
		boolean domainPattern5 = objectName4.isDomainPattern();//false
		boolean pattern5 = objectName4.isPattern();//true
		boolean propertyListPattern5 = objectName4.isPropertyListPattern();//true
		boolean propertyPattern5 = objectName4.isPropertyPattern();//true
		boolean propertyValuePattern5 = objectName4.isPropertyValuePattern();//false
		boolean apply5 = objectName.apply(objectName4);//false
		boolean apply6 = objectName4.apply(objectName);//true
		
//		属性，属性值模式
		ObjectName objectName5 = new ObjectName("DefaultDomain:name=t*d");
		boolean domainPattern6 = objectName5.isDomainPattern();//false
		boolean pattern6 = objectName5.isPattern();//true
		boolean propertyListPattern6 = objectName5.isPropertyListPattern();//false
		boolean propertyPattern6 = objectName5.isPropertyPattern();//true
		boolean propertyValuePattern6 = objectName5.isPropertyValuePattern();//true
		boolean apply11 = objectName5.apply(objectName);//true
		boolean apply12 = objectName.apply(objectName5);//false
		
		ObjectName objectName6 = new ObjectName("D*n:name=tjavax.managerment.standardbean.Standard");
		boolean domainPattern7 = objectName6.isDomainPattern();//true
		boolean pattern7 = objectName6.isPattern();//true
		boolean propertyListPattern7 = objectName6.isPropertyListPattern();//false
		boolean propertyPattern7 = objectName6.isPropertyPattern();//false
		boolean propertyValuePattern7 = objectName6.isPropertyValuePattern();//false
		boolean apply13 = objectName6.apply(objectName);//true
		boolean apply14 = objectName.apply(objectName6);//false
	}

}
