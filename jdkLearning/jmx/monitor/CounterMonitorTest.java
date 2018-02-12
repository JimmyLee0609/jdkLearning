package monitor;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.monitor.CounterMonitor;

public class CounterMonitorTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws NotCompliantMBeanException, InstanceAlreadyExistsException,
			MBeanRegistrationException, MBeanException, Exception, InstanceNotFoundException {
//		CounterMonitor可以监视多个对象，但是一个监视器只能检测一个属性值
		
		// 新建MBean服务器
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		// 创建HTML配时期
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		Object invoke = server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);

		// 将一个标准mbean注册到服务器
		ObjectName stD = new ObjectName("domain:type=tjavax.managerment.standardbean.StandardD");
		ObjectInstance createMBean = server.createMBean("tjavax.managerment.standardbean.StandardD", stD);

		// 新建一个计数监视器
		CounterMonitor monitor = new CounterMonitor();
		// 新建一个过滤器，只关注CounterMonitor发出的通知
		NotificationFilterSupport filter = new NotificationFilterSupport();
		filter.enableType("jmx.monitor.counter.threshold");// 这个是CounterMonitor发出通知的type
		// 新建一个监视器
		NotificationListener listener = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object handback) {
//				获取源对象，就是监视器CounterMonitor
				CounterMonitor source = (CounterMonitor) notification.getSource();
//				获取监视的属性
				String observedAttribute = source.getObservedAttribute();
//				获取监视的对象
				ObjectName[] observedObjects = source.getObservedObjects();
//				如果此对象包含在观察到的MBeans集合中，则获取指定对象的派生尺度，否则返回null。
				Number derivedGauge = source.getDerivedGauge(observedObjects[0]);
//				如果此对象包含在观察到的MBeans集合中，则获取指定对象的派生尺度时间戳，否则为0。
				long derivedGaugeTimeStamp = source.getDerivedGaugeTimeStamp(observedObjects[0]);
				/*获取粒度周期（以毫秒为单位）。
				粒度周期的默认值是10秒。*/
				long granularityPeriod = source.getGranularityPeriod();
//				获取初始的阈值
				Number initThreshold = source.getInitThreshold();
//				获取所有观察到的MBeans共同的模数值。
				Number modulus = source.getModulus();
//				获取偏移量
				Number offset = source.getOffset();
//				阈值是否根据偏移量偏移   
				boolean differenceMode = source.getDifferenceMode();
				System.out.println("observedAttribute ->" + observedAttribute+"  |getDerivedGauge->"+derivedGauge+"  |getDerivedGaugeTimeStamp"+derivedGaugeTimeStamp);
				System.out.println(" | getGranularityPeriod ->" + granularityPeriod + " | getInitThreshold ->"
						+ initThreshold + "  |getModulus->" + modulus + " getOffset->" + offset);
			}
		};
		// 为监视器添加监听器
		monitor.addNotificationListener(listener, filter, "handBack");
		// 将监视器注册到服务器
		ObjectName name = new ObjectName("domain:class=CounterMonitor");
		server.registerMBean(monitor, name);

		Thread.sleep(1000);
		// 为监视器添加监视对象
		monitor.addObservedObject(stD);
		// 监视指定的属性
		monitor.setObservedAttribute("NumberOfResets");
		// 观察值改变超过阈值是否发出通知
		monitor.setNotify(true);
		// 设置所有观察对象共有的初始阈值。
		monitor.setInitThreshold(5);

		// 当观察值变化超过阈值就会与这个偏移量运算。
		// setDifferenceMode（false）是固定偏移量  基址，  第一次 新值-旧值>0,同InitThreshold，新值-基质的差，超出Threshold 基址=新值  发出通知；  以后 新值-旧值>0, 新值-基址的差>Offset，基址=新值，发出通知。
//		只要有一次新值-旧值<0 , 基址=InitThreshold    新值> InitThreshold发出通知 ，新值小于InitThreshold就视InitThreshold为第一个阈值，然后按照上面的
		// setDifferenceMode（true）时，第一次或者负数复位时getDerivedGauge=InitThreshold，，     差值=新值-基址的差(值>0)，差值>getDerivedGauge+offset,超过阈值成立，getDerivedGauge=差值，基址=新值
//		新值-旧值<0时  （新值-旧值+Modulus-getDerivedGauge的值)(这个新值会成为新的getDerivedGauge)>=getDerivedGauge 触发通知    新值=基址
		monitor.setOffset(2);
		// 设置所有观察到的MBeans通用的差异模式标志值。
		monitor.setDifferenceMode(true);

		// 设置所有观察到的MBeans共同的模数值。
		// 观察值的变化 > = 阈值-Modulus 就会发出通知。
		// 例子 0 -->5 变化5 5+2=7<8 在 setDifferenceMode(false)模式下不会发出通知
		// 5--> 12 变化是7 7+2=9>8在setDifferenceMode(false)模式下就会发出通知
		monitor.setModulus(100);

		/*
		 * 获取粒度周期（以毫秒为单位）。 粒度周期的默认值是10秒。
		 */
		monitor.setGranularityPeriod(1);
		monitor.start();
		boolean containsObservedObject = monitor.containsObservedObject(stD);
		// 如果此对象包含在观察到的MBeans集合中，则获取指定对象的派生尺度，否则返回null。
		Number derivedGauge = monitor.getDerivedGauge(stD);
		// 如果此对象包含在观察到的MBeans集合中，则获取指定对象的派生尺度时间戳，否则为0。
		long derivedGaugeTimeStamp = monitor.getDerivedGaugeTimeStamp(stD);
		boolean differenceMode = monitor.getDifferenceMode();
		long granularityPeriod = monitor.getGranularityPeriod();
		Number initThreshold = monitor.getInitThreshold();
		Number modulus = monitor.getModulus();
		MBeanNotificationInfo[] notificationInfo = monitor.getNotificationInfo();
		String observedAttribute = monitor.getObservedAttribute();
		ObjectName[] observedObjects = monitor.getObservedObjects();
		Number offset = monitor.getOffset();
		Number threshold = monitor.getThreshold(stD);
		boolean notify = monitor.getNotify();
		// monitor.stop();
	}

}
