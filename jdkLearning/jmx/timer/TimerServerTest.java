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
		// ===========下面是最简单的时间服务====================
		// 构建时间计时器
		Timer timer = new Timer();
		// 开启服务
		timer.start();
		// 添加通知 通知类型 msg userData Date 这里是当前时间的 +3000毫秒
		timer.addNotification("test.notify", "tst", "", new Date(System.currentTimeMillis() + 3000));
		// 上面的通知将会在当前时间的+3000毫秒由Timer发出

		// 新建监听器
		NotificationListener listener = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object handback) {
				if( notification.getType().equals("test.newType")) {
					System.out.println("期间");
					return ;
				}
				System.out.println("触发监听器");
			}
		};
		// 新建过滤器
		NotificationFilterSupport filter = new NotificationFilterSupport();
		// 指定监听类型
		filter.enableType("test.notify");
		filter.enableType("test.newType");
		// 为bean添加监听器
		timer.addNotificationListener(listener, filter, "handback");

		// 这样的结果是当前时间的+3000毫秒 将触发监听器

		timerOperations(timer);
		String string = timer.toString();
	}

	private static void timerOperations(Timer timer) {
		//获取注册到通知列表中的所有计时器通知标识符。
		Vector<Integer> allNotificationIDs = timer.getAllNotificationIDs();
		Integer id = allNotificationIDs.get(0);
//		获取与计时器通知关联的日期的副本。
		Date date = timer.getDate(id);
		//获取指示定期通知是以固定延迟还是以固定速率执行的标志副本。
		Boolean fixedRate = timer.getFixedRate(id);
//		获取注册到通知列表中的计时器通知的数量。
		int nbNotifications = timer.getNbNotifications();
//		获取与计时器通知关联的剩余事件的副本。
		Long nbOccurences = timer.getNbOccurences(id);
		//获取与指定类型相对应的定时器通知的所有标识符。
		Vector<Integer> notificationIDs = timer.getNotificationIDs("test.notify");
//		返回一个数组，用于指示此MBean可能发送的每个通知，通知的Java类的名称和通知类型。
		MBeanNotificationInfo[] notificationInfo = timer.getNotificationInfo();
//		获取与指定的标识符对应的定时器通知详细消息。
		String notificationMessage = timer.getNotificationMessage(id);
//		获取与指定的标识符对应的计时器通知类型。
		String notificationType = timer.getNotificationType(id);
//		获取与指定标识对应的定时通知用户数据对象。
		Object notificationUserData = timer.getNotificationUserData(id);
//		/获取与计时器通知关联的时间段（以毫秒为单位）的副本。
		Long period = timer.getPeriod(id);
//		获取指示定时器是否发送过去通知的标志。
		boolean sendPastNotifications = timer.getSendPastNotifications();
//		测试计时器MBean是否处于活动状态。
		boolean active = timer.isActive();
//		返回标识的ID                                                           通知类型                      
		Integer addNotification = timer.addNotification("test.newType",
				"mymessage", //消息
				"userData", //userData
				new Date(), //发出通知的时刻
				5000,//发出通知的期间
				5, //发出的次数
				true);//如果属实，并且通知是周期性的，则通知按照fixrate执行方案进行安排。 如果错误并且通知是周期性的，则通知被安排为具有固定延迟执行方案。 如果通知不是周期性的，则忽略。
//																																													通知期间		
	}

}
