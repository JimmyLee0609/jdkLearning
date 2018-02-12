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
//		CounterMonitor���Լ��Ӷ�����󣬵���һ��������ֻ�ܼ��һ������ֵ
		
		// �½�MBean������
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		// ����HTML��ʱ��
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		Object invoke = server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);

		// ��һ����׼mbeanע�ᵽ������
		ObjectName stD = new ObjectName("domain:type=tjavax.managerment.standardbean.StandardD");
		ObjectInstance createMBean = server.createMBean("tjavax.managerment.standardbean.StandardD", stD);

		// �½�һ������������
		CounterMonitor monitor = new CounterMonitor();
		// �½�һ����������ֻ��עCounterMonitor������֪ͨ
		NotificationFilterSupport filter = new NotificationFilterSupport();
		filter.enableType("jmx.monitor.counter.threshold");// �����CounterMonitor����֪ͨ��type
		// �½�һ��������
		NotificationListener listener = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object handback) {
//				��ȡԴ���󣬾��Ǽ�����CounterMonitor
				CounterMonitor source = (CounterMonitor) notification.getSource();
//				��ȡ���ӵ�����
				String observedAttribute = source.getObservedAttribute();
//				��ȡ���ӵĶ���
				ObjectName[] observedObjects = source.getObservedObjects();
//				����˶�������ڹ۲쵽��MBeans�����У����ȡָ������������߶ȣ����򷵻�null��
				Number derivedGauge = source.getDerivedGauge(observedObjects[0]);
//				����˶�������ڹ۲쵽��MBeans�����У����ȡָ������������߶�ʱ���������Ϊ0��
				long derivedGaugeTimeStamp = source.getDerivedGaugeTimeStamp(observedObjects[0]);
				/*��ȡ�������ڣ��Ժ���Ϊ��λ����
				�������ڵ�Ĭ��ֵ��10�롣*/
				long granularityPeriod = source.getGranularityPeriod();
//				��ȡ��ʼ����ֵ
				Number initThreshold = source.getInitThreshold();
//				��ȡ���й۲쵽��MBeans��ͬ��ģ��ֵ��
				Number modulus = source.getModulus();
//				��ȡƫ����
				Number offset = source.getOffset();
//				��ֵ�Ƿ����ƫ����ƫ��   
				boolean differenceMode = source.getDifferenceMode();
				System.out.println("observedAttribute ->" + observedAttribute+"  |getDerivedGauge->"+derivedGauge+"  |getDerivedGaugeTimeStamp"+derivedGaugeTimeStamp);
				System.out.println(" | getGranularityPeriod ->" + granularityPeriod + " | getInitThreshold ->"
						+ initThreshold + "  |getModulus->" + modulus + " getOffset->" + offset);
			}
		};
		// Ϊ��������Ӽ�����
		monitor.addNotificationListener(listener, filter, "handBack");
		// ��������ע�ᵽ������
		ObjectName name = new ObjectName("domain:class=CounterMonitor");
		server.registerMBean(monitor, name);

		Thread.sleep(1000);
		// Ϊ��������Ӽ��Ӷ���
		monitor.addObservedObject(stD);
		// ����ָ��������
		monitor.setObservedAttribute("NumberOfResets");
		// �۲�ֵ�ı䳬����ֵ�Ƿ񷢳�֪ͨ
		monitor.setNotify(true);
		// �������й۲�����еĳ�ʼ��ֵ��
		monitor.setInitThreshold(5);

		// ���۲�ֵ�仯������ֵ�ͻ������ƫ�������㡣
		// setDifferenceMode��false���ǹ̶�ƫ����  ��ַ��  ��һ�� ��ֵ-��ֵ>0,ͬInitThreshold����ֵ-���ʵĲ����Threshold ��ַ=��ֵ  ����֪ͨ��  �Ժ� ��ֵ-��ֵ>0, ��ֵ-��ַ�Ĳ�>Offset����ַ=��ֵ������֪ͨ��
//		ֻҪ��һ����ֵ-��ֵ<0 , ��ַ=InitThreshold    ��ֵ> InitThreshold����֪ͨ ����ֵС��InitThreshold����InitThresholdΪ��һ����ֵ��Ȼ���������
		// setDifferenceMode��true��ʱ����һ�λ��߸�����λʱgetDerivedGauge=InitThreshold����     ��ֵ=��ֵ-��ַ�Ĳ�(ֵ>0)����ֵ>getDerivedGauge+offset,������ֵ������getDerivedGauge=��ֵ����ַ=��ֵ
//		��ֵ-��ֵ<0ʱ  ����ֵ-��ֵ+Modulus-getDerivedGauge��ֵ)(�����ֵ���Ϊ�µ�getDerivedGauge)>=getDerivedGauge ����֪ͨ    ��ֵ=��ַ
		monitor.setOffset(2);
		// �������й۲쵽��MBeansͨ�õĲ���ģʽ��־ֵ��
		monitor.setDifferenceMode(true);

		// �������й۲쵽��MBeans��ͬ��ģ��ֵ��
		// �۲�ֵ�ı仯 > = ��ֵ-Modulus �ͻᷢ��֪ͨ��
		// ���� 0 -->5 �仯5 5+2=7<8 �� setDifferenceMode(false)ģʽ�²��ᷢ��֪ͨ
		// 5--> 12 �仯��7 7+2=9>8��setDifferenceMode(false)ģʽ�¾ͻᷢ��֪ͨ
		monitor.setModulus(100);

		/*
		 * ��ȡ�������ڣ��Ժ���Ϊ��λ���� �������ڵ�Ĭ��ֵ��10�롣
		 */
		monitor.setGranularityPeriod(1);
		monitor.start();
		boolean containsObservedObject = monitor.containsObservedObject(stD);
		// ����˶�������ڹ۲쵽��MBeans�����У����ȡָ������������߶ȣ����򷵻�null��
		Number derivedGauge = monitor.getDerivedGauge(stD);
		// ����˶�������ڹ۲쵽��MBeans�����У����ȡָ������������߶�ʱ���������Ϊ0��
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
