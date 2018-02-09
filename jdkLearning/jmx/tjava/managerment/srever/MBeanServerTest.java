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
		// �½�һ��Ĭ�ϵķ�����
		MBeanServer server = MBeanServerFactory.newMBeanServer();
		// ��ȡĬ�ϵ���������
		String defaultDomain = server.getDefaultDomain();// DefaultDomain
		// �����κ�MBean��ǰע�������б� һ���ַ����ڷ��ص������У����ҽ���������һ��MBeanע����һ��ObjectName��
		// ����getDomain�������ڸ��ַ����� û�ж��巵���������ַ�����˳��
		String[] domains = server.getDomains();// Ĭ����ӵ�JMImplementation
		// ��ȡ��ǰע���MBean������
		Integer mBeanCount = server.getMBeanCount();// 1

		StandardD standardD = new StandardD();
		ObjectName objectName = new ObjectName("DefaultDomain:name=tjavax.managerment.standardbean.Standard");
		// ע��һ����׼��MBean����������
		ObjectInstance registerMBean = server.registerMBean(standardD, objectName);
		objectInstance(registerMBean);

		// ��ȡ�Ѿ�ע�ᵽ��������Domain����
		String[] domains2 = server.getDomains();
		// �ڷ�������ʵ������ע��һ��MBean,������ʹ��Ĭ�ϵļ��������������ļ�, ʹ�õ��ǿղεĹ�����
		ObjectInstance createMBean = server.createMBean("tjavax.managerment.standardbean.StandardD",
				new ObjectName(":name=suppli"));

		// ʹ��ָ���Ĺ�������ʵ����MBean ��ע��
		ObjectInstance createMBean3 = server.createMBean("tjavax.managerment.standardbean.StandardD",
				new ObjectName(":name=construct"), new Object[] { true, true },
				new String[] { Boolean.TYPE.getName(), Boolean.TYPE.getName() });
		// ����Ĳ��� ��������ǩ��

		// ʹ��ָ����classLoader���������ļ�,Ȼ��ʵ����MBean��ע��. һ������MLET����applet�У�����û������
//		Mlet��Ҫ��ע�ᵽserver,Ȼ����ע���ObjectName������ע���Mlet�Ӷ�ʹ���Զ�����������
		// ObjectInstance createMBean4 =
		// server.createMBean("tjavax.managerment.standardbean.StandardD", new
		// ObjectName(":name=setloader"), new ObjectName("loader"));

		// ���ش�MBeanServer��ClassLoaderRepository��
		ClassLoaderRepository repository = server.getClassLoaderRepository();

		// ��ȡ��MBean��������ע��ĸ���MBean��ObjectInstance��
		ObjectInstance objectInstance = server.getObjectInstance(objectName);

		// ���ַ���������MBean��������ԺͲ�����
		MBeanInfo mBeanInfo = server.getMBeanInfo(objectName);
		// ��ȡָ��MBean���ض����Ե�ֵ�� MBean����������Ʊ�ʶ��
		Object attribute = server.getAttribute(objectName, "TraceOn");
		// ����ָ��MBean�ļ������Ե�ֵ
		AttributeList attributes = server.getAttributes(objectName, new String[] { "TraceOn", "DebugOn" });

		// ����ָ��ObjectName���������,����ע�ᵽ���������������
		// ClassLoader classLoader = server.getClassLoader(loadername);

		// ���ؼ���ָ��ObjectName���������
		ClassLoader classLoaderFor = server.getClassLoaderFor(objectName);

		// ʹ����MBean��������Class Loader Repository��ע�����������������б���ʵ����һ������ �ö������Ӧ����һ���������캯����
		// �˷������ض��´����Ķ�������á� �´����Ķ���δ��MBean��������ע�ᡣ
		Object instantiate = server.instantiate("tjavax.managerment.standardbean.StandardD");
		// ʹ��ָ���Ĺ���������ʼ��,���ӵ���ʵ��������
		Object instantiate2 = server.instantiate("tjavax.managerment.standardbean.StandardD",
				new Object[] { true, true }, new String[] { Boolean.TYPE.getName(), Boolean.TYPE.getName() });
		// ʹ��ָ��������������, һ������MLet��
		// server.instantiate(className, loaderName)
		// ʹ��ָ�������������ָ���Ĺ����������� һ������MLet��
		// server.instantiate(className, loaderName, params, signature)

		// ָ�����ֵ�mbean�Ƿ�ע��
		boolean registered = server.isRegistered(objectName);

		// ����ָ��mbean���ֵ�ָ������
		Object invoke = server.invoke(objectName, "enableTracing", new Object[] {}, new String[] {});
		/*
		 * ��ȡ��MBean���������Ƶ�MBean��
		 * �˷��������������κ�һ�����MBean��һ��ͨ��ObjectName��/��Query���ʽ�ϵ�ģʽƥ��ָ����MBean��һ���ض���MBean��
		 * ����������Ϊ�ջ���û��ָ����ͼ�����ʱ����ѡ�����ж������ָ���˲�ѯ������й��ˣ���
		 * ��������ѡMBean��ObjectInstance���󼯺ϣ�����ObjectName��Java Class���ƣ���
		ָ����Χ��objectName                                                                 ������ͨ���                              �޶�ʵ�����е�����ֵ�ķ�Χ */										
		Set<ObjectInstance> queryMBeans = server.queryMBeans(objectName, Query.eq(Query.attr("TraceOn"), Query.value(true)));
		
		/*��ȡ��MBean���������Ƶ�MBean�����ơ� �˷������Ի�������κ�һ�
		 * ����MBean�����ƣ���ObjectName��/��Query���ʽ�ϵ�ģʽƥ��ָ����һ��MBean�����ƣ�
		 * �ض���MBean���ƣ���ͬ�ڲ���MBean�Ƿ� ע�ᣩ�� 
		 * ����������Ϊ�ջ�û��ָ����ͼ�����ʱ����ѡ�����ж��󣨲���ָ����ѯʱ���й��ˣ��� 
		 * ��������ѡMBean��һ��ObjectName��*/
//		����ָ����Χ��   ObjectName                                             ������ͨ�������ʽ                              �޶�ʵ����������ֵ�ľ��巶Χ
		Set<ObjectName> queryNames = server.queryNames(createMBean.getObjectName(), Query.eq(Query.attr("TraceOn"), Query.value(true)));

		// Ϊָ����MBean��������,��׼��MBean��Ҫ���Կ�д,���� �ӿ���Ҫ��set�ķ���
		server.setAttribute(objectName, new Attribute("TraceOn", false));
		// �÷�������ָ����MBean�����
		server.unregisterMBean(objectName);
//		ƥ�䵽�����objectName:  DefaultDomain:name=suppli
		String string = server.toString();
	}

	@SuppressWarnings("unused")
	private static void objectInstance(ObjectInstance registerMBean)
			throws MalformedObjectNameException, InstanceNotFoundException {
		/*
		 * ObjectInstance ���ڱ�ʾMBean�Ķ������Ƽ��������� ���MBean��һ����̬MBean����Ӧ�ô����ṩ��MBeanInfo�м���������
		 */

		// ��ȡע�ᵽ�����������֣��൱��һ��Э��ĸ�ʽ
		// ��ʾMBean�Ķ������ƣ����߿���ƥ�伸��MBean�����Ƶ�ģʽ�� ������ʵ���ǲ��ɱ�ġ�
		ObjectName objectName = registerMBean.getObjectName();
		objectName(objectName);
		// ��ȡ�����ȫ������
		String className = registerMBean.getClassName();
	}

	@SuppressWarnings("unused")
	private static void objectName(ObjectName objectName)
			throws MalformedObjectNameException, InstanceNotFoundException {
		// objectName�� DefaultDomain:name=tjavax.managerment.standardbean.Standard
		// �����򲿷֡�
		String domain = objectName.getDomain();// DefaultDomain
		/*
		 * ���ش���ʱָ���ļ������б���ַ�����ʾ��ʽ�� �����ObjectName��ʹ�ù��캯��ObjectName��String������ģ�
		 * �򷵻ص�String�еļ����Ե�˳���빹�캯���Ĳ���˳����ͬ��
		 */
		String keyPropertyListString = objectName.getKeyPropertyListString();// name=tjavax.managerment.standardbean.Standard
		/*
		 * ���عؼ������б���ַ�����ʾ��ʽ�����йؼ����԰��մʻ�˳������ ������ִ���ֵ�Աȣ��Ա���ݹؼ������б�ѡ��MBean��
		 * �ʻ�˳������String.compareTo��String����ʾ��˳��
		 */
		String canonicalKeyPropertyListString = objectName.getCanonicalKeyPropertyListString();// name=tjavax.managerment.standardbean.Standard
		/*
		 * �������ƵĹ淶��ʽ; Ҳ����˵��һ���ַ�����ʾ�����е����԰��ʷ�˳������
		 * ��ȷ�е�˵�����ƵĹ淶��ʽ��һ�����������֣�ð�ţ�:)���淶����Կ�����б��ģʽָʾ��ɵ��ַ�����
		 * �淶�������б���getCanonicalKeyPropertyListString����������������ͬ���ַ����� ģʽָʾ�ǣ�
		 * ���ڲ��������б�ģʽ��ObjectName�ǿյ�; һ��ObjectName���Ǻţ�����һ��û�м��������б�ģʽ; Ҫô
		 * һ�����ź�һ���Ǻţ���*����Ϊһ��������һ�����������б�ģʽ��
		 */
		String canonicalName = objectName.getCanonicalName();// DefaultDomain:name=tjavax.managerment.standardbean.Standard
		/*
		 * ����������Ϊɢ�б��ء� ���ص�ֵ��һ��Hashtable�� ����ÿ��������ObjectName�ļ������б��е�һ������ÿ��ֵ���ǹ�����ֵ��
		 * ���ص�ֵ�����ǲ����޸ĵġ� ������ǿ��޸ĵģ��ı��������ObjectNameû��Ӱ�졣
		 */
		Hashtable<String, String> keyPropertyList = objectName.getKeyPropertyList();// name=tjavax.managerment.standardbean.Standard
		// �����������Ƿ�Ϊ�򲿷ֵ�ģʽ��
		boolean domainPattern = objectName.isDomainPattern();// false
		/*
		 * �����������Ƿ���ģʽ�� ����������Ƶ������ͨ��������߶�������������ģʽ�������������ģʽ��
		 */
		boolean pattern = objectName.isPattern();// false
		boolean propertyListPattern = objectName.isPropertyListPattern();// false
		/*
		 * �����������Ƿ��Ǽ������б��ϵ�ģʽ�� ���磬��d��k = v��*���͡�d��k = *��*���ǹؼ������б�ģʽ������d��k = *�����ǡ�
		 */
		boolean propertyPattern = objectName.isPropertyPattern();// false
		/*
		 * �����������Ƿ�������һ��������ֵ���ֵ�ģʽ�� ���磬��d��k = *���͡�d��k = *��*��������ֵģʽ������d��k = v��*�����ǡ�
		 */
		boolean propertyValuePattern = objectName.isPropertyValuePattern();// false
		// �����������еļ��������ֵ�Ƿ���ģʽ�� ��Ҫ�����key
		boolean propertyValuePattern2 = objectName.isPropertyValuePattern("name");// false

//		===================pattern   ģʽ=====================================
		pattern(objectName);
		
		// �������ObjectName��������һ��ģʽ���Ƿ�����һ��ObjectName��ƥ�䡣
//		�����ģʽƥ�䣬��Ҫ��          ģʽ.apply(����)�����ģʽ���Ͼ����true
		boolean apply = objectName.apply(objectName);// true
		// �Ƚ�����objectName�Ƿ�ƥ��
		int compareTo = objectName.compareTo(objectName);// 0
		// ��ȡ��������еļ��������ֵ��
		String keyProperty = objectName.getKeyProperty("name");// "" ->null
																// name->tjavax.managerment.standardbean.Standard
		objectName.toString();

		// �����objectName��ͨ���
		ObjectName wildcard = ObjectName.WILDCARD;// *:*
		// ==========���캯��========================
		// �Ӹ������ַ�������һ���������ơ�
		ObjectName objectName2 = new ObjectName("default:name=bbc");// default:name=bbc
		// ֻ��һ���ؼ����Թ���һ���������ơ�
		ObjectName objectName3 = new ObjectName("default", "key", "value");// default:key=value

		Hashtable<String, String> table = new Hashtable<String, String>();
		String put = table.put("test", "value");
		// ��Hashtable�еļ����ؼ����Թ���һ���������ơ�
		ObjectName objectName4 = new ObjectName("domain", table);// domain:test=value

		/*
		 * ���ؿ����ڸ����������ʹ�õ��κεط�ʹ�õ�ObjectNameʵ���� ���صĶ��������ObjectName��һ�����ࡣ
		 * ���name��ObjectName�����࣬���ܱ�֤���صĶ�����ͬһ���ࡣ ���ص�ֵ������������ͬҲ���ܲ�ͬ��
		 * ʹ����ͬ�Ĳ�����������������ο��ܷ�����ͬ�Ķ����������ȵ�����ͬ�Ķ���
		 * ����ObjectName�ǲ��ɱ�ģ�����ͨ������Ҫ����һ��ObjectName�ĸ�����
		 * ���ַ�������Ҫ��;�Ƿ�ֹ����ĵ����߽�һ�������ʵ�������˾��ȵ���Ϊ���ݸ����еĴ��롣
		 * �����Ĵ�����Ե�����������������֪��û�����˾��ȵ���Ϊ��ObjectName��
		 */
		ObjectName instance = ObjectName.getInstance(objectName);// DefaultDomain:name=tjavax.managerment.standardbean.Standard
		/*
		 * ����ObjectName��һ��ʵ����������ʹ���µ�ObjectName��name�� ��õĶ������ʹ�õ��κεط�ʹ�ø�ʵ����
		 * ���صĶ��������ObjectName��һ�����ࡣ ʹ����ͬ�Ĳ�����������������ο��ܷ�����ͬ�Ķ����������ȵ�����ͬ�Ķ���
		 */
		ObjectName instance2 = ObjectName.getInstance("default:name=bbc");// default:name=bbc
		/*
		 * ����ObjectName��һ��ʵ����������ʹ����ObjectName���򣬼���ֵ����ȡ�Ķ�����κεط�ʹ�ø�ʵ����
		 * ���صĶ��������ObjectName��һ�����ࡣ ʹ����ͬ�Ĳ�����������������ο��ܷ�����ͬ�Ķ����������ȵ�����ͬ�Ķ���
		 */
		ObjectName instance3 = ObjectName.getInstance("domain", "key", "value");// domain:key=value
		/*
		 * ����ObjectName��һ��ʵ����������ʹ���µ�ObjectName��domain��table����ȡ�Ķ�����κεط�ʹ�á�
		 * ���صĶ��������ObjectName��һ�����ࡣ ʹ����ͬ�Ĳ�����������������ο��ܷ�����ͬ�Ķ����������ȵ�����ͬ�Ķ���
		 */
		ObjectName instance4 = ObjectName.getInstance(domain, table);// DefaultDomain:test=value
		
		/*���ظ���String��������ʽ���ʺϰ�����ObjectName�С� ���ص�ֵ����������ObjectName�еļ�������ֵ�� �ַ������԰����κ��ַ��� �ʵ������ÿ���ȷ�����ص�ֵ��ObjectName���ǺϷ��ġ�
		����Ϊ�ַ����������
		���ص�ֵ��һ��quote��'��'����һ����s���ַ���Ӧ���ַ����к���һ��quote��ɡ�
		     һ�����ţ�'��'����һ����б�ܣ�\�����һ��������ȡ����
		     �Ǻţ�'*'�����滻Ϊ��б�ܣ�\����������Ǻš�
		     �ʺţ�'��'�����滻Ϊ��б�ܣ�\�������һ���ʺš�
		     ��б�ܣ�'\'�����滻Ϊ������б�ܡ�
		     ���з���Java�е��ַ�'\ n'������б���滻����������ַ�'\ n'��*/
		String quote = ObjectName.quote("*");// "\*"
		String quote2 = ObjectName.quote("default:name=bbc");// "default:name=bbc"
		/*���ظ���String��δ��������ʽ�� ���q��һ����quote��s�����ص�String����ȡ����q��.equals��s����
		 *  ���û������.equals��q����String����ȡ�����ã�q��������IllegalArgumentException��
		��Щ������ζ�������úͲ����õı�֮����һ��һ��ӳ���ϵ��*/
		String unquote = ObjectName.unquote("\"\\*\"");//*
		String unquote2 = ObjectName.unquote("\"default:name=bbc\"");//default:name=bbc
		String unquote3 = ObjectName.unquote(quote2);//default:name=bbc
		
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		// ����Ҫ������ִ�в�ѯ��MBean��������
//		��QueryExp�̳еķ����������ǿ�ʵ�֣���ΪObjectName���������MBeanServer�ģ����Ҳ������Ӳ�ѯ��
		instance4.setMBeanServer(server);
	}

	@SuppressWarnings("unused")
	private static void pattern(ObjectName objectName) throws MalformedObjectNameException {
		String string = objectName.toString();//DefaultDomain:name=tjavax.managerment.standardbean.Standard
		
//		==============domainģʽ=====================
		ObjectName objectName2 = new ObjectName("*:name=tjavax.managerment.standardbean.Standard");
		boolean domainPattern3 = objectName2.isDomainPattern();//true
		boolean pattern3 = objectName2.isPattern();//true
		boolean propertyListPattern3 = objectName2.isPropertyListPattern();//false
		boolean propertyPattern3 = objectName2.isPropertyPattern();//false
		boolean propertyValuePattern3 = objectName2.isPropertyValuePattern();//false
		boolean apply3 = objectName.apply(objectName2);//false
		boolean apply8 = objectName2.apply(objectName);//true
		
//		=======domain������ģʽ======================
		ObjectName domain = new ObjectName("*:*");
		boolean domainPattern = domain.isDomainPattern();//true
		boolean pattern = domain.isPattern();//true
		boolean propertyListPattern = domain.isPropertyListPattern();//true
		boolean propertyPattern = domain.isPropertyPattern();//true
		boolean propertyValuePattern = domain.isPropertyValuePattern();//false
		boolean apply = domain.apply(objectName);//true
		boolean apply9 = objectName.apply(domain);//false
//		============���ԣ������б�ģʽ=======================
		ObjectName property = new ObjectName("DefaultDomain:*");
		boolean domainPattern2 = property.isDomainPattern();//false
		boolean pattern2 = property.isPattern();//true
		boolean propertyListPattern2 = property.isPropertyListPattern();//true
		boolean propertyPattern2 = property.isPropertyPattern();//true
		boolean propertyValuePattern2 = property.isPropertyValuePattern();//false
		boolean apply2 = objectName.apply(property);//false
		boolean apply10 = property.apply(objectName);//true
//		==================���ԣ�����ֵģʽ==========================
		ObjectName objectName3 = new ObjectName("DefaultDomain:name=*");
		boolean domainPattern4 = objectName3.isDomainPattern();//false
		boolean pattern4 = objectName3.isPattern();//true
		boolean propertyListPattern4 = objectName3.isPropertyListPattern();//false
		boolean propertyPattern4 = objectName3.isPropertyPattern();//true
		boolean propertyValuePattern4 = objectName3.isPropertyValuePattern();//true
		boolean apply4 = objectName.apply(objectName3);//false
		boolean apply7 = objectName3.apply(objectName);//true
		
		
//		==========���ԣ������б�ģʽ===================
		ObjectName objectName4 = new ObjectName("DefaultDomain:name=tjavax.managerment.standardbean.Standard,*");
		boolean domainPattern5 = objectName4.isDomainPattern();//false
		boolean pattern5 = objectName4.isPattern();//true
		boolean propertyListPattern5 = objectName4.isPropertyListPattern();//true
		boolean propertyPattern5 = objectName4.isPropertyPattern();//true
		boolean propertyValuePattern5 = objectName4.isPropertyValuePattern();//false
		boolean apply5 = objectName.apply(objectName4);//false
		boolean apply6 = objectName4.apply(objectName);//true
		
//		���ԣ�����ֵģʽ
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
