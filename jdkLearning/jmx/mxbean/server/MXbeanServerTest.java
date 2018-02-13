package mxbean.server;

import java.lang.reflect.Proxy;

import javax.management.Attribute;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataInvocationHandler;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

import mxbean.beans.AnnotationBean;
import mxbean.beans.AnnotationInterface;
import mxbean.beans.MxBeanSampleMXBean;
import mxbean.beans.OtherType;
import mxbean.beans.StandardMxBeanSample;
import tjavax.managerment.standardbean.StandardD;
import tjavax.managerment.standardbean.StandardDMBean;

public class MXbeanServerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);
		ObjectName name = new ObjectName("domain:type=mxbean");
		// 使用有参构造器构建bean
		ObjectInstance createMBean = server.createMBean("mxbean.beans.StandardMxBeanSample", name,
				new Object[] { 8, "temptemp" }, new String[] { int.class.getName(), String.class.getName() });
		// 获取属性值
		Object attribute = server.getAttribute(name, "Max");
		System.out.println(attribute);
		Object temp = server.getAttribute(name, "Temp");
		System.out.println(temp);

		Object invoke = server.invoke(name, "reset", new String[0], new String[0]);

		Object attribute2 = server.getAttribute(name, "Type");// 返回一个compositeData
		System.out.println(attribute2);
		// javax.management.openmbean.CompositeDataSupport(compositeType=javax.management.openmbean.CompositeType(name=mxbean.beans.OtherType,items=((itemName=type,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)))),contents={type=我的新类型})
		// 获取复杂类型中的属性值
		CompositeData data = (CompositeData) attribute2;
		Object object = data.get("type");
		System.out.println(object);
		
//		使用代理的方法来简化方法的调用，因为要调用服务器中指定类的方法需要使用server.invoke(...)很麻烦
//		使用代理的方式简化代码，提高可读性
//		在服务器中新建一个Standardbean
		ObjectName stand = new ObjectName("domain:stand=bean");
		ObjectInstance createMBean2 = server.createMBean("tjavax.managerment.standardbean.StandardD", stand);
//		为设个bean设置代理
		StandardDMBean newMBeanProxy = JMX.newMBeanProxy(server, stand, tjavax.managerment.standardbean.StandardDMBean.class);
		//使用代理的方式来调用bean的方法。看上去就是对象的调用。实际使用服务器的invoke。使用前需要向服务器注册bean
		float dd = newMBeanProxy.getDd();
		System.out.println(dd);
		// 使用代理模式来调用MXBean
		ObjectName proxyName = new ObjectName("domain:type=otherType");
		server.createMBean(StandardMxBeanSample.class.getName(), proxyName);
		MxBeanSampleMXBean newMXBeanProxy = JMX.newMXBeanProxy(server, proxyName, MxBeanSampleMXBean.class);
//		复杂类型，代理帮你将类型转到正常
		OtherType type = newMXBeanProxy.getType();
		System.out.println(type);
		
		
		ObjectName objectName = new ObjectName("domian:type=AnnotationBean");
//		创建MXBean代理的时候需要用到接口
		AnnotationInterface AnnoBeanProxy = JMX.newMXBeanProxy(server, objectName, AnnotationInterface.class);
//		创建MXBean的时候需要用到具体类
		ObjectInstance registerMBean = server.createMBean(AnnotationBean.class.getName(), objectName);
//		使用代理的方式获取MXBean的属性
		int bbt = AnnoBeanProxy.getBbt();
		System.out.println(bbt);
//		使用服务器的方式设置属性值,获取属性值
		server.setAttribute(objectName, new Attribute("Bbt",50));
		Object attribute3 = server.getAttribute(objectName, "Bbt");
		System.out.println(attribute3);
		AnnoBeanProxy.testPara(new StandardMxBeanSample());
		String[] annItemNames=new String[] {"Bbt","Note"};
		OpenType<?>[] annItemTypes=new OpenType[] {SimpleType.INTEGER,SimpleType.STRING};
		CompositeType annType = new CompositeType("mxbean.beans.AnnotationBean", "AnnotationBean类型", annItemNames, annItemNames, annItemTypes);
		AnnotationBean bean = new AnnotationBean();
		CompositeData compositeData = bean.toCompositeData(annType);
		AnnotationBean from = AnnotationBean.from(compositeData);
		
//		===============使用代理的方式来处理CompositeData===================================
//		根据实验需要将创建一个CompositeData源类型是StandardD
		OpenType<StandardD> [] itemTypes=new OpenType[] {SimpleType.FLOAT,SimpleType.BOOLEAN,SimpleType.BOOLEAN,SimpleType.INTEGER};
		String []itemNames=new String[] {"Dd","TraceOn","DebugOn","NumberOfResets"};
		CompositeType compositeType = new CompositeType("StandardD","类型转换尝试",itemNames,itemNames,itemTypes);
		Object[] itemValues=new Object[] {5.5f,false,false,89};
		CompositeDataSupport sData = new CompositeDataSupport(compositeType,itemNames,itemValues);
//		创建一个代理InovcationHandler 就是使用反射的方式来调用方法
		CompositeDataInvocationHandler handler = new CompositeDataInvocationHandler(sData);
		
//		创建代理，创建的代理虽然可以写接口的类型，但是实际是Proxy类型，不能用于注册
		StandardDMBean newProxyInstance = (StandardDMBean)Proxy.newProxyInstance(StandardDMBean.class.getClassLoader(), new Class[] {StandardDMBean.class}, handler);
//		使用代理调用接口的方法
		float dd2 = newProxyInstance.getDd();
		
	}

}
