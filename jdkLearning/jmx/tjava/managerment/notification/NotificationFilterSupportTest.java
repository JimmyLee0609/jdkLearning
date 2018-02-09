package tjava.managerment.notification;

import java.util.Vector;

import javax.management.Notification;
import javax.management.NotificationFilterSupport;

public class NotificationFilterSupportTest {

	public static void main(String[] args) {
//		�½�һ��������
		NotificationFilterSupport support = new NotificationFilterSupport();
		/*������ָ��ǰ׺��ͷ���������͵�֪ͨ���͸���������
		���ָ����ǰ׺�Ѿ�������֪ͨ�����б��У���˷����������á�
		���ݳ����Э����д�����ǰ׺		*/
		support.enableType("vendor[.application][.component][.eventGroup].event");
//		�������е�����
		support.disableAllTypes();
//		����ָ������
		support.disableType("vendor[.application][.component][.eventGroup].event");
		
//		��ȡ���õ�����
		Vector<String> enabledTypes = support.getEnabledTypes();
//		���ָ���������Ƿ񱻹��ˣ�  ����enable�о�true�������false
		support.isNotificationEnabled(new Notification("type","source",651565l));
	}

}
