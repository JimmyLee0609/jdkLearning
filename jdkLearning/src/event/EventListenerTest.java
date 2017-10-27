package event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.EventObject;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.Set;

public class EventListenerTest {

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// 标记型接口
		EventListener listener = new EventListener() {
		};
		// 监听器代理,将其他监听器保存在自己的字段中，将其他类型的监听器包装自己的
		EventListenerProxy proxy = new EventListenerProxy(listener) {
		};
		EventListener listener2 = proxy.getListener();

		// 所有事件状态对象应从其导出的根类。
		// 所有事件都被构造为对象的引用，“源”在逻辑上被认为是最初发生事件的对象。
		EventObject eventObject = new EventObject(listener2);
		Object source = eventObject.getSource();// 获取事件源
		String string = eventObject.toString();

	}

}
