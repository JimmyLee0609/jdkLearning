package tjava.managerment.srever;

import java.util.ArrayList;

import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerBuilder;
import javax.management.MBeanServerDelegate;
import javax.management.MBeanServerFactory;
import javax.management.loading.ClassLoaderRepository;

public class CreateMBeanServer {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
factory();
builder();
	}

	@SuppressWarnings("unused")
	private static void builder() {

MBeanServerBuilder builder = new MBeanServerBuilder();
MBeanServerDelegate delegate = builder.newMBeanServerDelegate();
delegate(delegate);

MBeanServer newMBeanServer = builder.newMBeanServer("defaultDomain", MBeanServerFactory.newMBeanServer(), delegate);
				
	}

	@SuppressWarnings("unused")
	private static void delegate(MBeanServerDelegate delegate) {
//		�ӹ���ĽǶ�����ʾMBean�������� ��MBean��MBean��������ע��/ȡ��ע��ʱ��MBeanServerDelegate MBean������MBeanServerNotifications��
		
		
//		����JMXʵ�����ƣ��˲�Ʒ�����ƣ���
		String implementationName = delegate.getImplementationName();//JMX
//		����JMXʵ�ֹ�Ӧ�̣�����Ʒ�Ĺ�Ӧ�̣���
		String implementationVendor = delegate.getImplementationVendor();//Oracle Corporation
//		����JMXʵ�ְ汾���˲�Ʒ�İ汾����
		String implementationVersion = delegate.getImplementationVersion();//1.8.0_162-b12
//		����MBean�����������ʶ��
		String mBeanServerId = delegate.getMBeanServerId();//DESKTOP-7SAG66J_1517733419365
		
//		�����ɴ˲�Ʒʵ�ֵ�JMX�淶��ȫ����
		String specificationName = delegate.getSpecificationName();//Java Management Extensions
//		�����ɴ˲�Ʒʵ�ֵ�JMX�淶�Ĺ�Ӧ�̡�
		String specificationVendor = delegate.getSpecificationVendor();//Oracle Corporation
//		���ش˲�Ʒʵ�ֵ�JMX�淶�İ汾
		String specificationVersion = delegate.getSpecificationVersion();//1.4
		
//		��ȡע�ᵽ���bean�����м�����
		MBeanNotificationInfo[] notificationInfo = delegate.getNotificationInfo();//javax.management.MBeanNotificationInfo
//		���һ��������bean�ļ�����
//		delegate.addNotificationListener(listener, filter, handback);
//		ʹMBean�������ܹ�����֪ͨ�� ���ͨ����֪ͨ�����к�С�ڻ����0�������滻Ϊ�����Լ������кš�
//		delegate.sendNotification(notification);
//		�Ƴ�������
//		delegate.removeNotificationListener(listener, filter, handback);
	}

	private static void factory() {
		//		====================creat������server����������factory��,����relase����find=============================
				
				/*��һ����׼��Ĭ����������һ��ʵ��MBeanServer�ӿڵ��¶��� 
				 * ���û�ָ������Ϊ��ʱ��Ĭ������������MBean��ObjectName�е��򲿷֡�
				��׼��Ĭ��������DefaultDomain��
				MBeanServer�������ڲ����档 �⽫����findMBeanServer���ض����MBeanServer��������á�
				���������ͬ��createMBeanServer��null����DESKTOP-7SAG66J_1517731114463*/
				MBeanServer createMBeanServer = MBeanServerFactory.createMBeanServer();//JmxMBeanServer
				
				/*ʹ��ָ����Ĭ����������һ��ʵ��MBeanServer�ӿڵ��¶��� 
				 * ���û�ָ������Ϊ��ʱ������������������MBean��ObjectName�е��򲿷֡�
				MBeanServer�������ڲ����档 �⽫����findMBeanServer���ض����MBeanServer��������á�*/
				MBeanServer createMBeanServer2 = MBeanServerFactory.createMBeanServer("DefaultDomain");
				
		//		���ظ���MBeanServerʹ�õ�ClassLoaderRepository�� ��������൱��server.getClassLoaderRepository������
				/*�˽ӿڵ�ʵ�����ڱ�����MBean��������ע�������������б������ṩ��ʹ��ע���ClassLoaders������ı�Ҫ������
				ClassLoaderRepository�еĵ�һ��ClassLoaderʼ����MBean Server�Լ���ClassLoader��
				��MBean��MBean��������ע��ʱ���������ClassLoader�����࣬������û��ʵ�ֽӿ�PrivateClassLoader��
				��������ӵ�MBean��������ClassLoaderRepository��ĩβ��
				�������MBean Serverע��������ClassLoaderRepository��ɾ������
				ClassLoaderRepository��MBean��˳��ǳ���Ҫ��
				����ClassLoaderRepository�е��κ�����MBean X��Y�������Y��ע�Ὺʼ֮ǰ���X��ע�ᣬ��X���������Y֮ǰ��
				���X��Yͬʱע�ᣬ���ǵ�˳���ǲ�ȷ���ġ� 
				MBean��ע���Ӧ�ڶ�MBeanServer.registerMBean��java.lang.Object��javax.management.ObjectName��
				��MBeanServer.createMBean����֮һ�ĵ���*/
				ClassLoaderRepository classLoaderRepository = MBeanServerFactory.getClassLoaderRepository(createMBeanServer);
		//		ɾ���Դ�����MBeanServer���ڲ�MBeanServerFactory���á� �����������ռ���ɾ��MBeanServer����
				MBeanServerFactory.releaseMBeanServer(createMBeanServer);
				
				
				/*��һ����׼��Ĭ����������һ��ʵ��MBeanServer�ӿڵ��¶��󣬶�������������¶�����ڲ����á� 
				 * ���û�ָ������Ϊ��ʱ��Ĭ������������MBean��ObjectName�е��򲿷֡�
				��׼��Ĭ��������DefaultDomain��
				û�вο��������� findMBeanServer���޷����ضԴ�MBeanServer��������ã�
				�������ռ������ܹ��ڲ���������ʱɾ��MBeanServer����
				��������൱��newMBeanServer��null����*/
				MBeanServer newMBeanServer = MBeanServerFactory.newMBeanServer();
				
		/*	ʹ��ָ����Ĭ����������ʵ��MBeanServer�ӿڵ��¶��󣬶�Factory�������Ը��¶�����ڲ����á� 
		 * ���û�ָ������Ϊ��ʱ������������������MBean��ObjectName�е��򲿷֡�
		û�вο��������� findMBeanServer���޷����ضԴ�MBeanServer��������ã�
		�������ռ������ܹ��ڲ���������ʱɾ��MBeanServer����*/
				MBeanServer newMBeanServer2 = MBeanServerFactory.newMBeanServer("DefaultDomain");
				MBeanServer newMBeanServer3 = MBeanServerFactory.newMBeanServer("DefaultDomain");
				
		//		������ע���MBeanServer������б� ��ע���MBeanServer��������createMBeanServer����֮һ�����ģ���󲻻���releaseMBeanServerһ�𷢲���
				ArrayList<MBeanServer> findMBeanServer = MBeanServerFactory.findMBeanServer("DefaultDomain");
	}

}
