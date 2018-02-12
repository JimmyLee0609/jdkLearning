package monitor;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.monitor.MonitorNotification;
import javax.management.monitor.StringMonitor;

public class StringMonitorTest {

	public static void main(String[] args) throws Exception, InstanceAlreadyExistsException, MBeanRegistrationException,
			MBeanException, ReflectionException {
//		StringMonitor监视器，在属性值与匹配字段不同的时候会发出通知，再有不同不会发出，直到匹配到吻合，才会再次发送，
//		匹配到吻合后，如果还是吻合，就不会发送通知，直到匹配到不吻合再发送不同的通知。如此循环
		
		
		// 新建MBean服务器
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		// 创建HTML配时期
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		Object invoke = server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);

		// 将一个标准mbean注册到服务器
		ObjectName box = new ObjectName("domain:type=dynamic.beans.Box");
		ObjectInstance boxBean = server.createMBean("dynamic.beans.Box", box);
		
//		新建字符串监听器
		StringMonitor stringMonitor = new StringMonitor();
		//添加监听器
		addListener(stringMonitor);
//		注册监视器
		ObjectName objectName = new ObjectName("domain:type=StringMonitor");
		ObjectInstance registerMBean = server.registerMBean(stringMonitor, objectName);
//		添加监听对象
		stringMonitor.addObservedObject(box);
//		添加监听属性
		stringMonitor.setObservedAttribute("name");
//		设定不同通知
		stringMonitor.setNotifyDiffer(true);
//		设定吻合通知
		stringMonitor.setNotifyMatch(true);
//		设定比较的基址
		stringMonitor.setStringToCompare("boxbox");
//		设定粒度周期
		stringMonitor.setGranularityPeriod(1000);
//		监视服务开始
		stringMonitor.start();
	}

	private static void addListener(StringMonitor stringMonitor) {
//		新建通知过滤器
		NotificationFilterSupport filter = new NotificationFilterSupport();
		filter.enableType(MonitorNotification.STRING_TO_COMPARE_VALUE_DIFFERED);//jmx.monitor.string.differs
		filter.enableType(MonitorNotification.STRING_TO_COMPARE_VALUE_MATCHED);//jmx.monitor.string.matches
//		新建监听器
		NotificationListener listener = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object handback) {
				MonitorNotification no=(MonitorNotification)notification;
				Object trigger = no.getTrigger();
				String message = no.getMessage();
				String type = no.getType();
				System.out.println("类型->"+type+"|  消息->"+message+"|  触发器->"+trigger);
//				获取源，监视器
				StringMonitor source =(StringMonitor) no.getSource();
//				观察的对象集
				ObjectName[] observedObjects = source.getObservedObjects();
//				获取观察的属性
				String observedAttribute = source.getObservedAttribute();
				String derivedGauge = source.getDerivedGauge(observedObjects[0]);
//				获取观察粒度
				long granularityPeriod = source.getGranularityPeriod();
//				不同是否通知
				boolean notifyDiffer = source.getNotifyDiffer();
//				吻合是否通知
				boolean notifyMatch = source.getNotifyMatch();
//				需要匹配的字符串
				String stringToCompare = source.getStringToCompare();
				
				String string = source.toString();
			}
		};
		
		stringMonitor.addNotificationListener(listener, filter, "handback");
	}

}
