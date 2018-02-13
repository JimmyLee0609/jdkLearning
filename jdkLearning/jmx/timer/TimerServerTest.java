package timer;

import java.util.Date;
import java.util.Vector;

import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;
import javax.management.timer.Timer;

public class TimerServerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// ===========��������򵥵�ʱ�����====================
		// ����ʱ���ʱ��
		Timer timer = new Timer();
		// ��������
		timer.start();
		// ���֪ͨ ֪ͨ���� msg userData Date �����ǵ�ǰʱ��� +3000����
		timer.addNotification("test.notify", "tst", "", new Date(System.currentTimeMillis() + 3000));
		// �����֪ͨ�����ڵ�ǰʱ���+3000������Timer����

		// �½�������
		NotificationListener listener = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object handback) {
				if( notification.getType().equals("test.newType")) {
					System.out.println("�ڼ�");
					return ;
				}
				System.out.println("����������");
			}
		};
		// �½�������
		NotificationFilterSupport filter = new NotificationFilterSupport();
		// ָ����������
		filter.enableType("test.notify");
		filter.enableType("test.newType");
		// Ϊbean��Ӽ�����
		timer.addNotificationListener(listener, filter, "handback");

		// �����Ľ���ǵ�ǰʱ���+3000���� ������������

		timerOperations(timer);
		String string = timer.toString();
	}

	private static void timerOperations(Timer timer) {
		//��ȡע�ᵽ֪ͨ�б��е����м�ʱ��֪ͨ��ʶ����
		Vector<Integer> allNotificationIDs = timer.getAllNotificationIDs();
		Integer id = allNotificationIDs.get(0);
//		��ȡ���ʱ��֪ͨ���������ڵĸ�����
		Date date = timer.getDate(id);
		//��ȡָʾ����֪ͨ���Թ̶��ӳٻ����Թ̶�����ִ�еı�־������
		Boolean fixedRate = timer.getFixedRate(id);
//		��ȡע�ᵽ֪ͨ�б��еļ�ʱ��֪ͨ��������
		int nbNotifications = timer.getNbNotifications();
//		��ȡ���ʱ��֪ͨ������ʣ���¼��ĸ�����
		Long nbOccurences = timer.getNbOccurences(id);
		//��ȡ��ָ���������Ӧ�Ķ�ʱ��֪ͨ�����б�ʶ����
		Vector<Integer> notificationIDs = timer.getNotificationIDs("test.notify");
//		����һ�����飬����ָʾ��MBean���ܷ��͵�ÿ��֪ͨ��֪ͨ��Java������ƺ�֪ͨ���͡�
		MBeanNotificationInfo[] notificationInfo = timer.getNotificationInfo();
//		��ȡ��ָ���ı�ʶ����Ӧ�Ķ�ʱ��֪ͨ��ϸ��Ϣ��
		String notificationMessage = timer.getNotificationMessage(id);
//		��ȡ��ָ���ı�ʶ����Ӧ�ļ�ʱ��֪ͨ���͡�
		String notificationType = timer.getNotificationType(id);
//		��ȡ��ָ����ʶ��Ӧ�Ķ�ʱ֪ͨ�û����ݶ���
		Object notificationUserData = timer.getNotificationUserData(id);
//		/��ȡ���ʱ��֪ͨ������ʱ��Σ��Ժ���Ϊ��λ���ĸ�����
		Long period = timer.getPeriod(id);
//		��ȡָʾ��ʱ���Ƿ��͹�ȥ֪ͨ�ı�־��
		boolean sendPastNotifications = timer.getSendPastNotifications();
//		���Լ�ʱ��MBean�Ƿ��ڻ״̬��
		boolean active = timer.isActive();
//		���ر�ʶ��ID                                                           ֪ͨ����                      
		Integer addNotification = timer.addNotification("test.newType",
				"mymessage", //��Ϣ
				"userData", //userData
				new Date(), //����֪ͨ��ʱ��
				5000,//����֪ͨ���ڼ�
				5, //�����Ĵ���
				true);//�����ʵ������֪ͨ�������Եģ���֪ͨ����fixrateִ�з������а��š� ���������֪ͨ�������Եģ���֪ͨ������Ϊ���й̶��ӳ�ִ�з����� ���֪ͨ���������Եģ�����ԡ�
//																																													֪ͨ�ڼ�		
	}

}
