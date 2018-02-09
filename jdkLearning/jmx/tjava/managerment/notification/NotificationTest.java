package tjava.managerment.notification;

import javax.management.Notification;
import javax.management.NotificationListener;

public class NotificationTest {

	public static void main(String[] args) {
		// 相当于一个事件,用来传递给监听器 类型 [ ]内是可选的
		Notification notification = new Notification("vendor[.application][.component][.eventGroup].event",
																							"sourceName", // 源
																							2598712l, // 序列号
																							System.currentTimeMillis(), // 时间戳
																							"message");// 信息
		NotificationListener notificationListener = new NotificationListener() {
			@SuppressWarnings("unused")
			@Override
			public void handleNotification(Notification notification, Object handback) {
//				获取类型
				String type = notification.getType();
//				获取源，API中建议写源的全类名
				Object source = notification.getSource();
//				获取序列号
				long seq = notification.getSequenceNumber();
//				获取信息
				String message = notification.getMessage();
//				获取时间戳
				long timeStamp = notification.getTimeStamp();
				
//				获取用户数据。它被用于通知源希望传达给消费者的任何数据。
				Object userData = notification.getUserData();
			}
		};
		
//		设置时间传递的信息对象
		notification.setUserData("userData");
//		设置序列号
		notification.setSequenceNumber(9841691l);
//		设置源API推荐写全类名，你也可以传递对象this
		notification.setSource("source objcet");
//		设置时间戳
		notification.setTimeStamp(System.currentTimeMillis());
	}

}
