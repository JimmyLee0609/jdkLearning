package tjava.managerment.notification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;

public class NotificationBroadcasterSupportTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws ListenerNotFoundException {
		// =========���캯��====================
		// Ĭ��executor����MBeanNotificationInfo[]
		NotificationBroadcasterSupport support = new NotificationBroadcasterSupport();
		// ָ��executor,��MBeanNotificationInfo[]
		ExecutorService service = Executors.newCachedThreadPool();// �½�һ���̳߳�
		NotificationBroadcasterSupport notificationBroadcasterSupport = new NotificationBroadcasterSupport(service);

		/*
		 * ʹ�ù��ڿ��ܷ��͵�֪ͨ����Ϣ����NotificationBroadcasterSupport�� ÿ�����������ɷ���֪ͨ���̵߳��á�
		 * ������캯����ͬ��NotificationBroadcasterSupport��null��info����
		 * ���info���鲻�ǿյģ���ô���ɹ��캯����¡������ͨ��info.clone����һ����ÿ�ε���getNotificationInfo����
		 * ���᷵��һ���µĿ�¡��
		 */
		MBeanNotificationInfo mBeanNotificationInfo = new MBeanNotificationInfo(new String[] { "notifType" }, "name",
				"description");
		NotificationBroadcasterSupport notificationBroadcasterSupport2 = new NotificationBroadcasterSupport(
				new MBeanNotificationInfo[] { mBeanNotificationInfo });
		/*
		 * ����һ��NotificationBroadcasterSupport�����а����йؿ��ܷ��͵�֪ͨ����Ϣ��
		 * �Լ�ʹ�ø�����Executor����ÿ����������λ�á� ������sendNotificationʱ����������һ����NotificationFilter��
		 * ����isNotificationEnabled�������ڷ��͵�֪ͨ����true����ѡ��һ����������
		 * ��NotificationFilter.isNotificationEnabled�ĵ��÷����ڵ���sendNotification���߳��С�
		 * Ȼ�󣬶���ÿ��ѡ������������ʹ�õ���handleNotification�������������executor.execute��
		 * ���info���鲻�ǿյģ���ô���ɹ��캯����¡������ͨ��info.clone����һ����ÿ�ε���getNotificationInfo����
		 * ���᷵��һ���µĿ�¡��
		 */
		NotificationBroadcasterSupport notificationBroadcasterSupport3 = new NotificationBroadcasterSupport(service,
				new MBeanNotificationInfo[] {mBeanNotificationInfo});

		/*
		 * ����һ�����飬����ָʾ��MBean���ܷ��͵�ÿ��֪ͨ��֪ͨ��Java������ƺ�֪ͨ���͡� MBean����δ�ڴ�������������֪ͨ�ǷǷ��ġ�
		 * ���ǣ�MBean��������ĳЩ�ͻ��˿�����������������������ȷ���С� ���صĶ���clone��MBeanNotificationInfo
		 */
		MBeanNotificationInfo[] notificationInfo = support.getNotificationInfo();
		// �½�һ��������
		NotificationListener listener = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object handback) {
				System.out.println(notification);
				System.out.println(handback);
			}
		};
		// �½�һ��������
		NotificationFilterSupport notificationFilterSupport = new NotificationFilterSupport();
		// Ϊ���������ǰ׺
		notificationFilterSupport.enableType("domain");
		// ��Ӽ�����
		support.addNotificationListener(listener, notificationFilterSupport, "handback");
		// �����¼�
		support.sendNotification(new Notification("domain.test", "main", 86481l));
		// �Ƴ�������
		support.removeNotificationListener(listener, notificationFilterSupport, "handback");
	}

}
