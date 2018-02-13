package dynamic.server;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationFilterSupport;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import dynamic.beans.DyListener;

public class DynamicServer {

	public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, ReflectionException, InstanceNotFoundException, InvalidAttributeValueException, AttributeNotFoundException {
//		构建MBean服务器
		MBeanServer server = MBeanServerFactory.createMBeanServer();
//		启动Html适配器
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);
//		注册一个Mbean
		ObjectName name = new ObjectName("domain:type=dynamic.beans.Box");
		ObjectInstance createMBean = server.createMBean("dynamic.beans.Box", name);
		Object invoke = server.invoke(name, "printN", new Object[0], new String[0]);
		
//		新建监听器
		DyListener dyListener = new DyListener();
//		新建过滤器
		NotificationFilterSupport filter = new NotificationFilterSupport();
		filter.enableType("jmx.attribute.change");
//		添加监听器
		server.invoke(name, "addNotificationListener", new Object[] {dyListener,filter,"hand-hand"},
				new String[] {"javax.management.NotificationListener","javax.management.NotificationFilter","java.lang.Object"});
//		变更属性值
		server.setAttribute(name, new Attribute("name","5468535"));//由于set的方法中有sendNotification所以触发的监听器
	}

}
