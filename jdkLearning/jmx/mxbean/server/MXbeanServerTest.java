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
		// ʹ���вι���������bean
		ObjectInstance createMBean = server.createMBean("mxbean.beans.StandardMxBeanSample", name,
				new Object[] { 8, "temptemp" }, new String[] { int.class.getName(), String.class.getName() });
		// ��ȡ����ֵ
		Object attribute = server.getAttribute(name, "Max");
		System.out.println(attribute);
		Object temp = server.getAttribute(name, "Temp");
		System.out.println(temp);

		Object invoke = server.invoke(name, "reset", new String[0], new String[0]);

		Object attribute2 = server.getAttribute(name, "Type");// ����һ��compositeData
		System.out.println(attribute2);
		// javax.management.openmbean.CompositeDataSupport(compositeType=javax.management.openmbean.CompositeType(name=mxbean.beans.OtherType,items=((itemName=type,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)))),contents={type=�ҵ�������})
		// ��ȡ���������е�����ֵ
		CompositeData data = (CompositeData) attribute2;
		Object object = data.get("type");
		System.out.println(object);
		
//		ʹ�ô���ķ������򻯷����ĵ��ã���ΪҪ���÷�������ָ����ķ�����Ҫʹ��server.invoke(...)���鷳
//		ʹ�ô���ķ�ʽ�򻯴��룬��߿ɶ���
//		�ڷ��������½�һ��Standardbean
		ObjectName stand = new ObjectName("domain:stand=bean");
		ObjectInstance createMBean2 = server.createMBean("tjavax.managerment.standardbean.StandardD", stand);
//		Ϊ���bean���ô���
		StandardDMBean newMBeanProxy = JMX.newMBeanProxy(server, stand, tjavax.managerment.standardbean.StandardDMBean.class);
		//ʹ�ô���ķ�ʽ������bean�ķ���������ȥ���Ƕ���ĵ��á�ʵ��ʹ�÷�������invoke��ʹ��ǰ��Ҫ�������ע��bean
		float dd = newMBeanProxy.getDd();
		System.out.println(dd);
		// ʹ�ô���ģʽ������MXBean
		ObjectName proxyName = new ObjectName("domain:type=otherType");
		server.createMBean(StandardMxBeanSample.class.getName(), proxyName);
		MxBeanSampleMXBean newMXBeanProxy = JMX.newMXBeanProxy(server, proxyName, MxBeanSampleMXBean.class);
//		�������ͣ�������㽫����ת������
		OtherType type = newMXBeanProxy.getType();
		System.out.println(type);
		
		
		ObjectName objectName = new ObjectName("domian:type=AnnotationBean");
//		����MXBean�����ʱ����Ҫ�õ��ӿ�
		AnnotationInterface AnnoBeanProxy = JMX.newMXBeanProxy(server, objectName, AnnotationInterface.class);
//		����MXBean��ʱ����Ҫ�õ�������
		ObjectInstance registerMBean = server.createMBean(AnnotationBean.class.getName(), objectName);
//		ʹ�ô���ķ�ʽ��ȡMXBean������
		int bbt = AnnoBeanProxy.getBbt();
		System.out.println(bbt);
//		ʹ�÷������ķ�ʽ��������ֵ,��ȡ����ֵ
		server.setAttribute(objectName, new Attribute("Bbt",50));
		Object attribute3 = server.getAttribute(objectName, "Bbt");
		System.out.println(attribute3);
		AnnoBeanProxy.testPara(new StandardMxBeanSample());
		String[] annItemNames=new String[] {"Bbt","Note"};
		OpenType<?>[] annItemTypes=new OpenType[] {SimpleType.INTEGER,SimpleType.STRING};
		CompositeType annType = new CompositeType("mxbean.beans.AnnotationBean", "AnnotationBean����", annItemNames, annItemNames, annItemTypes);
		AnnotationBean bean = new AnnotationBean();
		CompositeData compositeData = bean.toCompositeData(annType);
		AnnotationBean from = AnnotationBean.from(compositeData);
		
//		===============ʹ�ô���ķ�ʽ������CompositeData===================================
//		����ʵ����Ҫ������һ��CompositeDataԴ������StandardD
		OpenType<StandardD> [] itemTypes=new OpenType[] {SimpleType.FLOAT,SimpleType.BOOLEAN,SimpleType.BOOLEAN,SimpleType.INTEGER};
		String []itemNames=new String[] {"Dd","TraceOn","DebugOn","NumberOfResets"};
		CompositeType compositeType = new CompositeType("StandardD","����ת������",itemNames,itemNames,itemTypes);
		Object[] itemValues=new Object[] {5.5f,false,false,89};
		CompositeDataSupport sData = new CompositeDataSupport(compositeType,itemNames,itemValues);
//		����һ������InovcationHandler ����ʹ�÷���ķ�ʽ�����÷���
		CompositeDataInvocationHandler handler = new CompositeDataInvocationHandler(sData);
		
//		�������������Ĵ�����Ȼ����д�ӿڵ����ͣ�����ʵ����Proxy���ͣ���������ע��
		StandardDMBean newProxyInstance = (StandardDMBean)Proxy.newProxyInstance(StandardDMBean.class.getClassLoader(), new Class[] {StandardDMBean.class}, handler);
//		ʹ�ô�����ýӿڵķ���
		float dd2 = newProxyInstance.getDd();
		
	}

}
