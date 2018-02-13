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
//		����MBean������
		MBeanServer server = MBeanServerFactory.createMBeanServer();
//		����Html������
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);
//		ע��һ��Mbean
		ObjectName name = new ObjectName("domain:type=dynamic.beans.Box");
		ObjectInstance createMBean = server.createMBean("dynamic.beans.Box", name);
		Object invoke = server.invoke(name, "printN", new Object[0], new String[0]);
		
//		�½�������
		DyListener dyListener = new DyListener();
//		�½�������
		NotificationFilterSupport filter = new NotificationFilterSupport();
		filter.enableType("jmx.attribute.change");
//		��Ӽ�����
		server.invoke(name, "addNotificationListener", new Object[] {dyListener,filter,"hand-hand"},
				new String[] {"javax.management.NotificationListener","javax.management.NotificationFilter","java.lang.Object"});
//		�������ֵ
		server.setAttribute(name, new Attribute("name","5468535"));//����set�ķ�������sendNotification���Դ����ļ�����
	}

}
