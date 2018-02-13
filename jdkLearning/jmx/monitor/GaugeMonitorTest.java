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
		/*setDifferenceMode(false)������£����Ե�ֵ����������ֵ�ᷢ��һ�θ���ֵ֪ͨ����һ��ֻҪ���ڵ���ֵ�Ͳ��ᷢ�ͣ�ֱ�����ڵ���ֵ����������ֵ֪ͨ
		 *ͬ���ģ�����ֵ���ڵ���ֵ����������ֵ֪ͨ��ֱ��ֵ���ڸ���ֵ�Żᷢ������ֵ֪ͨ��
		 * setDifferenceMode(true)������£����Ȼ�Ĭ�ϴ�������ֵ �����߶�->0.0������������ֵ��ʱ��ᴥ������ֵ��Ȼ�󴥷�����ֵ����debug��������
		 * ���ڳ߶Ȼ�仯      ��ֵ=��ֵ-��ֵ   ��ֵ>�߶�+����ֵ   �ʹ�������ֵ  ��ֵΪ�³߶ȣ�����û����
		 * */
//		GaugeMonitor���Ӹ�����,����double��float��������
//		����������
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		Object invoke = server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);
		// ��һ����׼��mbean,Ҳ��һ��������
		GaugeMonitor gaugeMonitor = new GaugeMonitor();
//		��Ӽ�����
		addLiatener(gaugeMonitor);
		Thread.sleep(500);
//		�½�������
		ObjectName name = new ObjectName("domain:type=javax.management.monitor.GaugeMonitor,name=listen");
		ObjectInstance registerMBean = server.registerMBean(gaugeMonitor, name);
//		�½�bean
		ObjectName standD = new ObjectName("domain:type=tjavax.managerment.standardbean.StandardD");
		ObjectInstance stand = server.createMBean("tjavax.managerment.standardbean.StandardD", standD);
		// ����˶����в����ڣ����ڹ۲쵽��MBeans�������ָ���Ķ���
		gaugeMonitor.addObservedObject(standD);
//		���ù۲������
		gaugeMonitor.setObservedAttribute("Dd");//���Եĵ�һ����ĸ��Сдͬget/set�ķ����������һ�£����಻һ��һ��
//		�������й۲쵽��MBeansͨ�õĲ���ģʽ��־ֵ��
		gaugeMonitor.setDifferenceMode(false);
//		�������й۲쵽��MBeansͨ�õĸ�֪ͨ����ֵ��
		gaugeMonitor.setNotifyHigh(true);
//		�������й۲쵽��MBeansͨ�õĵ�֪ͨ����ֵ��
		gaugeMonitor.setNotifyLow(true);
//		�������й۲쵽��MBeans���еĸ���ֵ�͵���ֵ��
		gaugeMonitor.setThresholds(new Float(50.4f),new Float(40.6f));
		// �����������ڣ��Ժ���Ϊ��λ����
		gaugeMonitor.setGranularityPeriod(1000l);
		// �����Ǳ��������
		gaugeMonitor.start();
		
		
		// �Ǳ���������Ƿ����ָ���Ķ���
		boolean containsObservedObject = gaugeMonitor.containsObservedObject(standD);
		boolean containsObservedObject2 = gaugeMonitor.containsObservedObject(stand.getObjectName());
		//* ��ȡ�������ڣ��Ժ���Ϊ��λ���� �������ڵ�Ĭ��ֵ��10�롣
		long granularityPeriod = gaugeMonitor.getGranularityPeriod();
		// ���ذ������۲��������顣
		ObjectName[] observedObjects = gaugeMonitor.getObservedObjects();
		// �������Ƿ��ڻ
		boolean active = gaugeMonitor.isActive();
		/*// ���������MBean��MBean������ȡ��ע���ִ��������κβ�����
		gaugeMonitor.postDeregister();
		// ���������MBean��MBean��������ע��֮���ע��ʧ�ܺ�ִ��������κβ�����
		gaugeMonitor.postRegister(false);// ע��ʧ�ܺ�
		// ���������MBean��MBean������ȡ��ע���ִ��������κβ�����
		gaugeMonitor.preDeregister();
		// ���������MBean��MBean��������ע��֮���ע��ʧ�ܺ�ִ��������κβ�����
		ObjectName preRegister = gaugeMonitor.preRegister(server, stand.getObjectName());*/
		// �ӹ۲쵽��MBeans������ɾ��ָ���Ķ���
//		gaugeMonitor.removeObservedObject(name);
		//��ȡ���ڹ۲�����ԡ� �۲쵽������Ĭ�������δ��ʼ��������Ϊ�գ���
		String observedAttribute = gaugeMonitor.getObservedAttribute();
		
//		����˶�������ڹ۲쵽��MBeans�����У����ȡָ������������߶ȣ����򷵻�null��
		Number derivedGauge = gaugeMonitor.getDerivedGauge(standD);
//		����˶�������ڹ۲쵽��MBeans�����У����ȡָ������������߶�ʱ��������򷵻�0��
		long derivedGaugeTimeStamp2 = gaugeMonitor.getDerivedGaugeTimeStamp(standD);
//		��ȡ���й۲쵽��MBeansͨ�õĲ���ģʽ��־ֵ��
		boolean differenceMode = gaugeMonitor.getDifferenceMode();
//		��ȡ���й۲쵽��MBeans���еĸ���ֵ��
		Number highThreshold = gaugeMonitor.getHighThreshold();
//		��ȡ���й۲쵽��MBeans���еĵ���ֵ��
		Number lowThreshold = gaugeMonitor.getLowThreshold();
//		����һ��NotificationInfo���󣬸ö������֪ͨ��Java�����ƺͼ��������������͵�֪ͨ���͡�
		MBeanNotificationInfo[] notificationInfo = gaugeMonitor.getNotificationInfo();
//		��ȡ���й۲쵽��MBeansͨ�õĸ�֪ͨ����ֵ��
		boolean notifyHigh = gaugeMonitor.getNotifyHigh();
//		��ȡ���й۲쵽��MBeansͨ�õĵ�֪ͨ����ֵ��
		boolean notifyLow = gaugeMonitor.getNotifyLow();
		// ֹͣ����
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
		//		�½�������
				NotificationListener listener = new NotificationListener() {
					@Override
					public void handleNotification(Notification notification, Object handback) {
//						��ȡ֪ͨ���ͺ���Ϣ
						String type = notification.getType();
						String message = notification.getMessage();
						System.out.println("====��������==="+type+"=====������Ϣ====="+message);
//						��ȡ֪ͨ��Դ����
						GaugeMonitor gm=(GaugeMonitor)notification.getSource();
//						��ȡ�۲������
						String observedAttribute = gm.getObservedAttribute();
						System.out.println("�۲������->"+observedAttribute);
//						��ȡ�۲�Ķ���
						ObjectName[] observedObjects = gm.getObservedObjects();
		//				����˶�������ڹ۲쵽��MBeans�����У����ȡָ������������߶ȣ����򷵻�null��
						Number derivedGauge = gm.getDerivedGauge(observedObjects[0]);
		//				����˶�������ڹ۲쵽��MBeans�����У����ȡָ������������߶�ʱ���������Ϊ0��
						long derivedGaugeTimeStamp = gm.getDerivedGaugeTimeStamp(observedObjects[0]);
						
		//				��ȡ���й۲쵽��MBeansͨ�õĲ���ģʽ��־ֵ��
						boolean differenceMode = gm.getDifferenceMode();
						System.out.println("�����߶�->"+derivedGauge+"---�����߶Ȳ���ʱ�� "+derivedGaugeTimeStamp+" |getDifferenceMode->"+differenceMode);
						/*��ȡ�������ڣ��Ժ���Ϊ��λ����
						�������ڵ�Ĭ��ֵ��10�롣*/
						long granularityPeriod = gm.getGranularityPeriod();
		//				��ȡ���й۲쵽��MBeans���еĸ���ֵ
						Number highThreshold = gm.getHighThreshold();
		//				��ȡ���й۲쵽��MBeans���еĵ���ֵ��
						Number lowThreshold = gm.getLowThreshold();
						System.out.println("����ֵ->"+highThreshold+"  |����ֵ->"+lowThreshold+"  |��������->"+granularityPeriod);
		//				��ȡ���й۲쵽��MBeansͨ�õĸ�֪ͨ����ֵ��
						boolean notifyHigh = gm.getNotifyHigh();
		//				��ȡ���й۲쵽��MBeansͨ�õĵ�֪ͨ����ֵ��
						boolean notifyLow = gm.getNotifyLow();
						System.out.println("�Ƿ��λ����->"+notifyHigh+"  |�Ƿ��λ����->"+notifyLow);
		//				����һ��NotificationInfo���󣬸ö������֪ͨ��Java�����ƺͼ��������������͵�֪ͨ���͡�
						MBeanNotificationInfo[] notificationInfo = gm.getNotificationInfo();
						System.out.println(notificationInfo);
					}
				};
		//		�½�������
				NotificationFilterSupport filter = new NotificationFilterSupport();
				filter.enableType("jmx.monitor.gauge.high");
		//		��������Ӽ�����
				gaugeMonitor.addNotificationListener(listener, filter, "handback");
//				�½�������
						NotificationListener lowListen = new NotificationListener() {
							@Override
							public void handleNotification(Notification notification, Object handback) {
//								��ȡ֪ͨ���ͺ���Ϣ
								String type = notification.getType();
								String message = notification.getMessage();
								System.out.println("====��������==="+type+"=====������Ϣ====="+message);
//								��ȡ֪ͨ��Դ����
								GaugeMonitor gm=(GaugeMonitor)notification.getSource();
//								��ȡ�۲������
								String observedAttribute = gm.getObservedAttribute();
								System.out.println("�۲������->"+observedAttribute);
//								��ȡ�۲�Ķ���
								ObjectName[] observedObjects = gm.getObservedObjects();
				//				����˶�������ڹ۲쵽��MBeans�����У����ȡָ������������߶ȣ����򷵻�null��
								Number derivedGauge = gm.getDerivedGauge(observedObjects[0]);
				//				����˶�������ڹ۲쵽��MBeans�����У����ȡָ������������߶�ʱ���������Ϊ0��
								long derivedGaugeTimeStamp = gm.getDerivedGaugeTimeStamp(observedObjects[0]);
								
				//				��ȡ���й۲쵽��MBeansͨ�õĲ���ģʽ��־ֵ��
								boolean differenceMode = gm.getDifferenceMode();
								System.out.println("�����߶�->"+derivedGauge+"---�����߶Ȳ���ʱ�� "+derivedGaugeTimeStamp+" |getDifferenceMode->"+differenceMode);
								/*��ȡ�������ڣ��Ժ���Ϊ��λ����
								�������ڵ�Ĭ��ֵ��10�롣*/
								long granularityPeriod = gm.getGranularityPeriod();
				//				��ȡ���й۲쵽��MBeans���еĸ���ֵ
								Number highThreshold = gm.getHighThreshold();
				//				��ȡ���й۲쵽��MBeans���еĵ���ֵ��
								Number lowThreshold = gm.getLowThreshold();
								System.out.println("����ֵ->"+highThreshold+"  |����ֵ->"+lowThreshold+"  |��������->"+granularityPeriod);
				//				��ȡ���й۲쵽��MBeansͨ�õĸ�֪ͨ����ֵ��
								boolean notifyHigh = gm.getNotifyHigh();
				//				��ȡ���й۲쵽��MBeansͨ�õĵ�֪ͨ����ֵ��
								boolean notifyLow = gm.getNotifyLow();
								System.out.println("�Ƿ��λ����->"+notifyHigh+"  |�Ƿ��λ����->"+notifyLow);
				//				����һ��NotificationInfo���󣬸ö������֪ͨ��Java�����ƺͼ��������������͵�֪ͨ���͡�
								MBeanNotificationInfo[] notificationInfo = gm.getNotificationInfo();
								System.out.println(notificationInfo);
							}
						};
				//		�½�������
						NotificationFilterSupport lowfilter = new NotificationFilterSupport();
						lowfilter.enableType("jmx.monitor.gauge.low");
						gaugeMonitor.addNotificationListener(lowListen, lowfilter, "handback");
	}
}
