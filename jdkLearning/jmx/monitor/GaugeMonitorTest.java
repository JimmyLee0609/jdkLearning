package monitor;

import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.monitor.GaugeMonitor;

public class GaugeMonitorTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		/*setDifferenceMode(false)的情况下，属性的值，超过高阈值会发出一次高阈值通知，下一次只要高于低阈值就不会发送，直到低于低阈值，发出低阈值通知
		 *同样的，先是值低于低阈值，发出低阈值通知，直到值大于高阈值才会发出高阈值通知。
		 * setDifferenceMode(true)的情况下，首先会默认触发低阈值 派生尺度->0.0，当触发高阈值的时候会触发高阈值，然后触发低阈值？？debug是这样。
		 * 由于尺度会变化      差值=新值-旧值   差值>尺度+高阈值   就触发高阈值  差值为新尺度，规律没摸清
		 * */
//		GaugeMonitor监视浮点数,就是double和float两种类型
//		开启服务器
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		Object invoke = server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);
		// 是一个标准的mbean,也是一个监视器
		GaugeMonitor gaugeMonitor = new GaugeMonitor();
//		添加监听器
		addLiatener(gaugeMonitor);
		Thread.sleep(500);
//		新建监视器
		ObjectName name = new ObjectName("domain:type=javax.management.monitor.GaugeMonitor,name=listen");
		ObjectInstance registerMBean = server.registerMBean(gaugeMonitor, name);
//		新建bean
		ObjectName standD = new ObjectName("domain:type=tjavax.managerment.standardbean.StandardD");
		ObjectInstance stand = server.createMBean("tjavax.managerment.standardbean.StandardD", standD);
		// 如果此对象尚不存在，则在观察到的MBeans集中添加指定的对象。
		gaugeMonitor.addObservedObject(standD);
//		设置观察的属性
		gaugeMonitor.setObservedAttribute("Dd");//属性的第一个字母大小写同get/set的方法所构造的一致，和类不一定一样
//		设置所有观察到的MBeans通用的差异模式标志值。
		gaugeMonitor.setDifferenceMode(false);
//		设置所有观察到的MBeans通用的高通知开关值。
		gaugeMonitor.setNotifyHigh(true);
//		设置所有观察到的MBeans通用的低通知开关值。
		gaugeMonitor.setNotifyLow(true);
//		设置所有观察到的MBeans共有的高阈值和低阈值。
		gaugeMonitor.setThresholds(new Float(50.4f),new Float(40.6f));
		// 设置粒度周期（以毫秒为单位）。
		gaugeMonitor.setGranularityPeriod(1000l);
		// 启动仪表监视器。
		gaugeMonitor.start();
		
		
		// 仪表监视器中是否存在指定的对象
		boolean containsObservedObject = gaugeMonitor.containsObservedObject(standD);
		boolean containsObservedObject2 = gaugeMonitor.containsObservedObject(stand.getObjectName());
		//* 获取粒度周期（以毫秒为单位）。 粒度周期的默认值是10秒。
		long granularityPeriod = gaugeMonitor.getGranularityPeriod();
		// 返回包含被观察对象的数组。
		ObjectName[] observedObjects = gaugeMonitor.getObservedObjects();
		// 监视器是否在活动
		boolean active = gaugeMonitor.isActive();
		/*// 允许监视器MBean在MBean服务器取消注册后执行所需的任何操作。
		gaugeMonitor.postDeregister();
		// 允许监视器MBean在MBean服务器中注册之后或注册失败后执行所需的任何操作。
		gaugeMonitor.postRegister(false);// 注册失败后
		// 允许监视器MBean在MBean服务器取消注册后执行所需的任何操作。
		gaugeMonitor.preDeregister();
		// 允许监视器MBean在MBean服务器中注册之后或注册失败后执行所需的任何操作。
		ObjectName preRegister = gaugeMonitor.preRegister(server, stand.getObjectName());*/
		// 从观察到的MBeans集合中删除指定的对象。
//		gaugeMonitor.removeObservedObject(name);
		//获取正在观察的属性。 观察到的属性默认情况下未初始化（设置为空）。
		String observedAttribute = gaugeMonitor.getObservedAttribute();
		
//		如果此对象包含在观察到的MBeans集合中，则获取指定对象的派生尺度，否则返回null。
		Number derivedGauge = gaugeMonitor.getDerivedGauge(standD);
//		如果此对象包含在观察到的MBeans集合中，则获取指定对象的派生尺度时间戳，否则返回0。
		long derivedGaugeTimeStamp2 = gaugeMonitor.getDerivedGaugeTimeStamp(standD);
//		获取所有观察到的MBeans通用的差异模式标志值。
		boolean differenceMode = gaugeMonitor.getDifferenceMode();
//		获取所有观察到的MBeans共有的高阈值。
		Number highThreshold = gaugeMonitor.getHighThreshold();
//		获取所有观察到的MBeans共有的低阈值。
		Number lowThreshold = gaugeMonitor.getLowThreshold();
//		返回一个NotificationInfo对象，该对象包含通知的Java类名称和计量器监视器发送的通知类型。
		MBeanNotificationInfo[] notificationInfo = gaugeMonitor.getNotificationInfo();
//		获取所有观察到的MBeans通用的高通知开关值。
		boolean notifyHigh = gaugeMonitor.getNotifyHigh();
//		获取所有观察到的MBeans通用的低通知开关值。
		boolean notifyLow = gaugeMonitor.getNotifyLow();
		// 停止监视
//		gaugeMonitor.stop();
		Thread.sleep(5000);
		server.invoke(standD, "add", new Object[] {new Float(60)}, new String[] {Float.TYPE.getName()});
		Thread.sleep(5000);
		
		server.invoke(standD, "add", new Object[] {new Float(120)}, new String[] {Float.TYPE.getName()});
		Thread.sleep(5000);
		server.invoke(standD, "add", new Object[] {new Float(7)}, new String[] {Float.TYPE.getName()});
		System.out.println("finish");
	}

	private static void addLiatener(GaugeMonitor gaugeMonitor) {
		//		新建监听器
				NotificationListener listener = new NotificationListener() {
					@Override
					public void handleNotification(Notification notification, Object handback) {
//						获取通知类型和消息
						String type = notification.getType();
						String message = notification.getMessage();
						System.out.println("====触发类型==="+type+"=====触发消息====="+message);
//						获取通知的源对象
						GaugeMonitor gm=(GaugeMonitor)notification.getSource();
//						获取观察的属性
						String observedAttribute = gm.getObservedAttribute();
						System.out.println("观察的属性->"+observedAttribute);
//						获取观察的对象
						ObjectName[] observedObjects = gm.getObservedObjects();
		//				如果此对象包含在观察到的MBeans集合中，则获取指定对象的派生尺度，否则返回null。
						Number derivedGauge = gm.getDerivedGauge(observedObjects[0]);
		//				如果此对象包含在观察到的MBeans集合中，则获取指定对象的派生尺度时间戳，否则为0。
						long derivedGaugeTimeStamp = gm.getDerivedGaugeTimeStamp(observedObjects[0]);
						
		//				获取所有观察到的MBeans通用的差异模式标志值。
						boolean differenceMode = gm.getDifferenceMode();
						System.out.println("派生尺度->"+derivedGauge+"---派生尺度产生时间 "+derivedGaugeTimeStamp+" |getDifferenceMode->"+differenceMode);
						/*获取粒度周期（以毫秒为单位）。
						粒度周期的默认值是10秒。*/
						long granularityPeriod = gm.getGranularityPeriod();
		//				获取所有观察到的MBeans共有的高阈值
						Number highThreshold = gm.getHighThreshold();
		//				获取所有观察到的MBeans共有的低阈值。
						Number lowThreshold = gm.getLowThreshold();
						System.out.println("高阈值->"+highThreshold+"  |低阈值->"+lowThreshold+"  |粒度周期->"+granularityPeriod);
		//				获取所有观察到的MBeans通用的高通知开关值。
						boolean notifyHigh = gm.getNotifyHigh();
		//				获取所有观察到的MBeans通用的低通知开关值。
						boolean notifyLow = gm.getNotifyLow();
						System.out.println("是否高位开关->"+notifyHigh+"  |是否低位开关->"+notifyLow);
		//				返回一个NotificationInfo对象，该对象包含通知的Java类名称和计量器监视器发送的通知类型。
						MBeanNotificationInfo[] notificationInfo = gm.getNotificationInfo();
						System.out.println(notificationInfo);
					}
				};
		//		新建过滤器
				NotificationFilterSupport filter = new NotificationFilterSupport();
				filter.enableType("jmx.monitor.gauge.high");
		//		监视器添加监听器
				gaugeMonitor.addNotificationListener(listener, filter, "handback");
//				新建监听器
						NotificationListener lowListen = new NotificationListener() {
							@Override
							public void handleNotification(Notification notification, Object handback) {
//								获取通知类型和消息
								String type = notification.getType();
								String message = notification.getMessage();
								System.out.println("====触发类型==="+type+"=====触发消息====="+message);
//								获取通知的源对象
								GaugeMonitor gm=(GaugeMonitor)notification.getSource();
//								获取观察的属性
								String observedAttribute = gm.getObservedAttribute();
								System.out.println("观察的属性->"+observedAttribute);
//								获取观察的对象
								ObjectName[] observedObjects = gm.getObservedObjects();
				//				如果此对象包含在观察到的MBeans集合中，则获取指定对象的派生尺度，否则返回null。
								Number derivedGauge = gm.getDerivedGauge(observedObjects[0]);
				//				如果此对象包含在观察到的MBeans集合中，则获取指定对象的派生尺度时间戳，否则为0。
								long derivedGaugeTimeStamp = gm.getDerivedGaugeTimeStamp(observedObjects[0]);
								
				//				获取所有观察到的MBeans通用的差异模式标志值。
								boolean differenceMode = gm.getDifferenceMode();
								System.out.println("派生尺度->"+derivedGauge+"---派生尺度产生时间 "+derivedGaugeTimeStamp+" |getDifferenceMode->"+differenceMode);
								/*获取粒度周期（以毫秒为单位）。
								粒度周期的默认值是10秒。*/
								long granularityPeriod = gm.getGranularityPeriod();
				//				获取所有观察到的MBeans共有的高阈值
								Number highThreshold = gm.getHighThreshold();
				//				获取所有观察到的MBeans共有的低阈值。
								Number lowThreshold = gm.getLowThreshold();
								System.out.println("高阈值->"+highThreshold+"  |低阈值->"+lowThreshold+"  |粒度周期->"+granularityPeriod);
				//				获取所有观察到的MBeans通用的高通知开关值。
								boolean notifyHigh = gm.getNotifyHigh();
				//				获取所有观察到的MBeans通用的低通知开关值。
								boolean notifyLow = gm.getNotifyLow();
								System.out.println("是否高位开关->"+notifyHigh+"  |是否低位开关->"+notifyLow);
				//				返回一个NotificationInfo对象，该对象包含通知的Java类名称和计量器监视器发送的通知类型。
								MBeanNotificationInfo[] notificationInfo = gm.getNotificationInfo();
								System.out.println(notificationInfo);
							}
						};
				//		新建过滤器
						NotificationFilterSupport lowfilter = new NotificationFilterSupport();
						lowfilter.enableType("jmx.monitor.gauge.low");
						gaugeMonitor.addNotificationListener(lowListen, lowfilter, "handback");
	}
}
