package dynamic.server;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class DynamicServer {

	public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, ReflectionException, InstanceNotFoundException {
//		构建MBean服务器
		MBeanServer server = MBeanServerFactory.createMBeanServer();
//		启动Html适配器
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);
//		注册一个Mbean
		ObjectName name = new ObjectName("domain:type=dynamic.beans.Box");
		ObjectInstance createMBean = server.createMBean("dynamic.beans.Box", name);
		Object invoke = server.invoke(name, "printN", new Object[0], new String[0]);
		
	}

}
