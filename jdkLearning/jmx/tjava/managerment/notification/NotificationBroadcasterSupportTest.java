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
		// =========构造函数====================
		// 默认executor，空MBeanNotificationInfo[]
		NotificationBroadcasterSupport support = new NotificationBroadcasterSupport();
		// 指定executor,空MBeanNotificationInfo[]
		ExecutorService service = Executors.newCachedThreadPool();// 新建一个线程池
		NotificationBroadcasterSupport notificationBroadcasterSupport = new NotificationBroadcasterSupport(service);

		/*
		 * 使用关于可能发送的通知的信息构造NotificationBroadcasterSupport。 每个侦听器都由发送通知的线程调用。
		 * 这个构造函数等同于NotificationBroadcasterSupport（null，info）。
		 * 如果info数组不是空的，那么它由构造函数克隆，就像通过info.clone（）一样，每次调用getNotificationInfo（）
		 * 都会返回一个新的克隆。
		 */
		MBeanNotificationInfo mBeanNotificationInfo = new MBeanNotificationInfo(new String[] { "notifType" }, "name",
				"description");
		NotificationBroadcasterSupport notificationBroadcasterSupport2 = new NotificationBroadcasterSupport(
				new MBeanNotificationInfo[] { mBeanNotificationInfo });
		/*
		 * 构造一个NotificationBroadcasterSupport，其中包含有关可能发送的通知的信息，
		 * 以及使用给定的Executor调用每个侦听器的位置。 当调用sendNotification时，如果添加了一个空NotificationFilter，
		 * 或者isNotificationEnabled对于正在发送的通知返回true，则选择一个侦听器。
		 * 对NotificationFilter.isNotificationEnabled的调用发生在调用sendNotification的线程中。
		 * 然后，对于每个选定的侦听器，使用调用handleNotification方法的命令调用executor.execute。
		 * 如果info数组不是空的，那么它由构造函数克隆，就像通过info.clone（）一样，每次调用getNotificationInfo（）
		 * 都会返回一个新的克隆。
		 */
		NotificationBroadcasterSupport notificationBroadcasterSupport3 = new NotificationBroadcasterSupport(service,
				new MBeanNotificationInfo[] {mBeanNotificationInfo});

		/*
		 * 返回一个数组，用于指示此MBean可能发送的每个通知，通知的Java类的名称和通知类型。 MBean发送未在此数组中描述的通知是非法的。
		 * 但是，MBean服务器的某些客户端可能依赖于完整的阵列来正确运行。 返回的都是clone的MBeanNotificationInfo
		 */
		MBeanNotificationInfo[] notificationInfo = support.getNotificationInfo();
		// 新建一个监听器
		NotificationListener listener = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object handback) {
				System.out.println(notification);
				System.out.println(handback);
			}
		};
		// 新建一个过滤器
		NotificationFilterSupport notificationFilterSupport = new NotificationFilterSupport();
		// 为过滤器添加前缀
		notificationFilterSupport.enableType("domain");
		// 添加监听器
		support.addNotificationListener(listener, notificationFilterSupport, "handback");
		// 发送事件
		support.sendNotification(new Notification("domain.test", "main", 86481l));
		// 移除监听器
		support.removeNotificationListener(listener, notificationFilterSupport, "handback");
	}

}
