package dynamic.beans;

import javax.management.Notification;
import javax.management.NotificationListener;

public class DyListener implements NotificationListener {

	@Override
	public void handleNotification(Notification notification, Object handback) {
		System.out.println(Thread.currentThread().getName());
		System.out.println(notification+"===="+handback);
	}

}
