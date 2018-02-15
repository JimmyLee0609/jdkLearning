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
//		构建一个连接地址，需要与服务器开启连接后的地址一致
		JMXServiceURL serviceURL = new JMXServiceURL(rmiAddr);
		urlservice(serviceURL);
//		构建客户端连接器
		JMXConnector connect = JMXConnectorFactory.connect(serviceURL);
//		连接器添加监听器
//		connect.addConnectionNotificationListener(listener, filter, handback);
		/*使用参数连接
		HashMap env = new HashMap();
		connect.connect(env);*/
//		连接器连接地址
		connect.connect();
		
//		获取MBean服务器		
		MBeanServerConnection mBeanServerConnection = connect.getMBeanServerConnection();
//		获取连接ID
		String connectionId = connect.getConnectionId();
		
//		获取服务器的Mbean
		ObjectName name = new ObjectName("domain:type=test");
		MBeanInfo mBeanInfo = mBeanServerConnection.getMBeanInfo(name);
		MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
		
		System.out.println(attributes[0]);
//		连接关闭
		connect.close();
	}

	private static void urlservice(JMXServiceURL serviceURL) {
//		获取协议
		String protocol = serviceURL.getProtocol();
//		获取主机
		String host = serviceURL.getHost();
//		获取端口
		int port = serviceURL.getPort();
//		获取url路径   /stub/.....      /ior/IOR
		String urlPath = serviceURL.getURLPath();
		serviceURL.toString();
	}

}
