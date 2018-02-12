package monitor;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.monitor.GaugeMonitor;

public class GaugeMonitorTest {
public static void main(String[] args) throws Exception {
//	是一个标准的mbean,也是一个监视器
	GaugeMonitor gaugeMonitor = new GaugeMonitor();
	
	MBeanServer server = MBeanServerFactory.createMBeanServer();
	ObjectName name = new ObjectName("domain:type=javax.management.monitor.GaugeMonitor,name=listen");
	ObjectInstance registerMBean = server.registerMBean(gaugeMonitor, name);
	ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
	Object invoke = server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);
	
	ObjectInstance box = server.createMBean("dynamic.beans.Box", null);
	
	gaugeMonitor.addObservedObject(box.getObjectName());
	
}
}
