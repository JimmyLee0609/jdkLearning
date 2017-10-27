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
		// ����ͽӿ�
		EventListener listener = new EventListener() {
		};
		// ����������,�������������������Լ����ֶ��У����������͵ļ�������װ�Լ���
		EventListenerProxy proxy = new EventListenerProxy(listener) {
		};
		EventListener listener2 = proxy.getListener();

		// �����¼�״̬����Ӧ���䵼���ĸ��ࡣ
		// �����¼���������Ϊ��������ã���Դ�����߼��ϱ���Ϊ����������¼��Ķ���
		EventObject eventObject = new EventObject(listener2);
		Object source = eventObject.getSource();// ��ȡ�¼�Դ
		String string = eventObject.toString();

	}

}
