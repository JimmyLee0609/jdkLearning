package tjava.managerment.srever;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

public class MBeanServerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
MBeanServer server = MBeanServerFactory.newMBeanServer();
//	��ȡĬ�ϵ���������
String defaultDomain = server.getDefaultDomain();//DefaultDomain
//�����κ�MBean��ǰע�������б� һ���ַ����ڷ��ص������У����ҽ���������һ��MBeanע����һ��ObjectName��
//����getDomain�������ڸ��ַ����� û�ж��巵���������ַ�����˳��
String[] domains = server.getDomains();//Ĭ����ӵ�JMImplementation
//	��ȡ��ǰע���MBean������
Integer mBeanCount = server.getMBeanCount();//1

String string = server.toString();
	}

}
