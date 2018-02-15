package remote.client;

import java.util.HashMap;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXClient {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		String rmiAddr="service:jmx:rmi://localhost:9999/stub/rO0ABXNyAC5qYXZheC5tYW5hZ2VtZW50LnJlbW90ZS5ybWkuUk1JU2VydmVySW1wbF9TdHViAAAAAAAAAAICAAB4cgAaamF2YS5ybWkuc2VydmVyLlJlbW90ZVN0dWLp/tzJi+FlGgIAAHhyABxqYXZhLnJtaS5zZXJ2ZXIuUmVtb3RlT2JqZWN002G0kQxhMx4DAAB4cHc1AApVbmljYXN0UmVmAAwxOTIuMTYuMy4xMDIAACcP0cQ9tTxgHM8H44BuAAABYZnQRNiAAQB4";
		String iiop="service:jmx:iiop://localhost:9999/ior/IOR:000000000000003b524d493a6a617661782e6d616e6167656d656e742e72656d6f74652e726d692e524d495365727665723a30303030303030303030303030303030000000000001000000000000006e000102000000000d3139322e31362e332e3130320000c56400000019afabcb000000000299cbbda800000008000000000000000014000000000000020000000100000020000000000001000100000002050100010001002000010109000000010001010000000026000000020002";
//		����һ�����ӵ�ַ����Ҫ��������������Ӻ�ĵ�ַһ��
		JMXServiceURL serviceURL = new JMXServiceURL(rmiAddr);
		urlservice(serviceURL);
//		�����ͻ���������
		JMXConnector connect = JMXConnectorFactory.connect(serviceURL);
//		��������Ӽ�����
//		connect.addConnectionNotificationListener(listener, filter, handback);
		/*ʹ�ò�������
		HashMap env = new HashMap();
		connect.connect(env);*/
//		���������ӵ�ַ
		connect.connect();
		
//		��ȡMBean������		
		MBeanServerConnection mBeanServerConnection = connect.getMBeanServerConnection();
//		��ȡ����ID
		String connectionId = connect.getConnectionId();
		
//		��ȡ��������Mbean
		ObjectName name = new ObjectName("domain:type=test");
		MBeanInfo mBeanInfo = mBeanServerConnection.getMBeanInfo(name);
		MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
		
		System.out.println(attributes[0]);
//		���ӹر�
		connect.close();
	}

	private static void urlservice(JMXServiceURL serviceURL) {
//		��ȡЭ��
		String protocol = serviceURL.getProtocol();
//		��ȡ����
		String host = serviceURL.getHost();
//		��ȡ�˿�
		int port = serviceURL.getPort();
//		��ȡurl·��   /stub/.....      /ior/IOR
		String urlPath = serviceURL.getURLPath();
		serviceURL.toString();
	}

}
