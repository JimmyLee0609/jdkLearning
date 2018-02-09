package tjava.managerment.notification;

import java.util.Vector;

import javax.management.Notification;
import javax.management.NotificationFilterSupport;

public class NotificationFilterSupportTest {

	public static void main(String[] args) {
//		新建一个监听器
		NotificationFilterSupport support = new NotificationFilterSupport();
		/*启用以指定前缀开头的所有类型的通知发送给侦听器。
		如果指定的前缀已经在启用通知类型列表中，则此方法不起作用。
		根据程序的协议来写具体的前缀		*/
		support.enableType("vendor[.application][.component][.eventGroup].event");
//		禁用所有的类型
		support.disableAllTypes();
//		禁用指定类型
		support.disableType("vendor[.application][.component][.eventGroup].event");
		
//		获取启用的类型
		Vector<String> enabledTypes = support.getEnabledTypes();
//		检测指定的类型是否被过滤，  存在enable中就true，否则就false
		support.isNotificationEnabled(new Notification("type","source",651565l));
	}

}
