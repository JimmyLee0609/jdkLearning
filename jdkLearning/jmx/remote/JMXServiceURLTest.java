package remote;

import java.util.HashMap;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import tjavax.managerment.standardbean.StandardD;

public class JMXServiceURLTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		String rmiAddr="service:jmx:rmi://localhost:9999";
		String iiop="service:jmx:iiop://localhost:9999";
		
//		�½���������url�Թ����ӣ�������Ҫ�������Ӻ��ȡ�ͻ��˵����ӵ�ַ��
//		�ͻ��˲ſ�����������,�������㷨��ÿ̨�������̶���һ��ID
		JMXServiceURL serviceURL = new JMXServiceURL(rmiAddr);
//		����һ��MBean������
		MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer();
//		������������
		HashMap envm = new HashMap();
//		������1��ر�
//		envm.put("jmx.remote.x.server.cinnection.timeout", 1000);
		//��ֹ�ͻ���ping
		envm.put("jmx.remote.x.client.connection.check.period",0);
//		envm.put(jmx.remote.default.class.loader,classloader )
//		envm.put("jmx.remote.rim.client.socket.factoty", socketFactory)
//		����һ��JMX������������
		JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(serviceURL, envm,
				mBeanServer);
//		��������������
		connectorServer.start();
//		��ȡ�ͻ��˿��õ����ӵ�ַ
		JMXServiceURL address = connectorServer.getAddress();
		
		System.out.println(address);
//		ע��һ��Mbean
		ObjectName name = new ObjectName("domain:type=test");
		ObjectInstance createMBean = mBeanServer.createMBean(StandardD.class.getName(), name);
		
//		���ӹر�
		connectorServer.stop();
	}

}
