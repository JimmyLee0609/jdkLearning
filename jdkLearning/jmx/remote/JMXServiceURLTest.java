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
		
//		新建服务器的url以供连接，但是需要创建连接后获取客户端的连接地址。
//		客户端才可以用于连接,好像有算法，每台机器都固定有一个ID
		JMXServiceURL serviceURL = new JMXServiceURL(rmiAddr);
//		创建一个MBean服务器
		MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer();
//		创建环境参数
		HashMap envm = new HashMap();
//		连接在1秒关闭
//		envm.put("jmx.remote.x.server.cinnection.timeout", 1000);
		//禁止客户端ping
		envm.put("jmx.remote.x.client.connection.check.period",0);
//		envm.put(jmx.remote.default.class.loader,classloader )
//		envm.put("jmx.remote.rim.client.socket.factoty", socketFactory)
//		创建一个JMX服务器连接器
		JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(serviceURL, envm,
				mBeanServer);
//		开启服务器连接
		connectorServer.start();
//		获取客户端可用的连接地址
		JMXServiceURL address = connectorServer.getAddress();
		
		System.out.println(address);
//		注册一个Mbean
		ObjectName name = new ObjectName("domain:type=test");
		ObjectInstance createMBean = mBeanServer.createMBean(StandardD.class.getName(), name);
		
//		连接关闭
		connectorServer.stop();
	}

}
