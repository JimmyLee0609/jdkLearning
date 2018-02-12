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
//		StringMonitor��������������ֵ��ƥ���ֶβ�ͬ��ʱ��ᷢ��֪ͨ�����в�ͬ���ᷢ����ֱ��ƥ�䵽�Ǻϣ��Ż��ٴη��ͣ�
//		ƥ�䵽�ǺϺ���������Ǻϣ��Ͳ��ᷢ��֪ͨ��ֱ��ƥ�䵽���Ǻ��ٷ��Ͳ�ͬ��֪ͨ�����ѭ��
		
		
		// �½�MBean������
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		// ����HTML��ʱ��
		ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		Object invoke = server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);

		// ��һ����׼mbeanע�ᵽ������
		ObjectName box = new ObjectName("domain:type=dynamic.beans.Box");
		ObjectInstance boxBean = server.createMBean("dynamic.beans.Box", box);
		
//		�½��ַ���������
		StringMonitor stringMonitor = new StringMonitor();
		//��Ӽ�����
		addListener(stringMonitor);
//		ע�������
		ObjectName objectName = new ObjectName("domain:type=StringMonitor");
		ObjectInstance registerMBean = server.registerMBean(stringMonitor, objectName);
//		��Ӽ�������
		stringMonitor.addObservedObject(box);
//		��Ӽ�������
		stringMonitor.setObservedAttribute("name");
//		�趨��֪ͬͨ
		stringMonitor.setNotifyDiffer(true);
//		�趨�Ǻ�֪ͨ
		stringMonitor.setNotifyMatch(true);
//		�趨�ȽϵĻ�ַ
		stringMonitor.setStringToCompare("boxbox");
//		�趨��������
		stringMonitor.setGranularityPeriod(1000);
//		���ӷ���ʼ
		stringMonitor.start();
	}

	private static void addListener(StringMonitor stringMonitor) {
//		�½�֪ͨ������
		NotificationFilterSupport filter = new NotificationFilterSupport();
		filter.enableType(MonitorNotification.STRING_TO_COMPARE_VALUE_DIFFERED);//jmx.monitor.string.differs
		filter.enableType(MonitorNotification.STRING_TO_COMPARE_VALUE_MATCHED);//jmx.monitor.string.matches
//		�½�������
		NotificationListener listener = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object handback) {
				MonitorNotification no=(MonitorNotification)notification;
				Object trigger = no.getTrigger();
				String message = no.getMessage();
				String type = no.getType();
				System.out.println("����->"+type+"|  ��Ϣ->"+message+"|  ������->"+trigger);
//				��ȡԴ��������
				StringMonitor source =(StringMonitor) no.getSource();
//				�۲�Ķ���
				ObjectName[] observedObjects = source.getObservedObjects();
//				��ȡ�۲������
				String observedAttribute = source.getObservedAttribute();
				String derivedGauge = source.getDerivedGauge(observedObjects[0]);
//				��ȡ�۲�����
				long granularityPeriod = source.getGranularityPeriod();
//				��ͬ�Ƿ�֪ͨ
				boolean notifyDiffer = source.getNotifyDiffer();
//				�Ǻ��Ƿ�֪ͨ
				boolean notifyMatch = source.getNotifyMatch();
//				��Ҫƥ����ַ���
				String stringToCompare = source.getStringToCompare();
				
				String string = source.toString();
			}
		};
		
		stringMonitor.addNotificationListener(listener, filter, "handback");
	}

}
