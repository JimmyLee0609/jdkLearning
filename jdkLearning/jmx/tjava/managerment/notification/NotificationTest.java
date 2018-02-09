package tjava.managerment.notification;

import javax.management.Notification;
import javax.management.NotificationListener;

public class NotificationTest {

	public static void main(String[] args) {
		// �൱��һ���¼�,�������ݸ������� ���� [ ]���ǿ�ѡ��
		Notification notification = new Notification("vendor[.application][.component][.eventGroup].event",
																							"sourceName", // Դ
																							2598712l, // ���к�
																							System.currentTimeMillis(), // ʱ���
																							"message");// ��Ϣ
		NotificationListener notificationListener = new NotificationListener() {
			@SuppressWarnings("unused")
			@Override
			public void handleNotification(Notification notification, Object handback) {
//				��ȡ����
				String type = notification.getType();
//				��ȡԴ��API�н���дԴ��ȫ����
				Object source = notification.getSource();
//				��ȡ���к�
				long seq = notification.getSequenceNumber();
//				��ȡ��Ϣ
				String message = notification.getMessage();
//				��ȡʱ���
				long timeStamp = notification.getTimeStamp();
				
//				��ȡ�û����ݡ���������֪ͨԴϣ������������ߵ��κ����ݡ�
				Object userData = notification.getUserData();
			}
		};
		
//		����ʱ�䴫�ݵ���Ϣ����
		notification.setUserData("userData");
//		�������к�
		notification.setSequenceNumber(9841691l);
//		����ԴAPI�Ƽ�дȫ��������Ҳ���Դ��ݶ���this
		notification.setSource("source objcet");
//		����ʱ���
		notification.setTimeStamp(System.currentTimeMillis());
	}

}
