package tjava.managerment.srever;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

public class MBeanServerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
MBeanServer server = MBeanServerFactory.newMBeanServer();
//	获取默认的主机名字
String defaultDomain = server.getDefaultDomain();//DefaultDomain
//返回任何MBean当前注册的域的列表。 一个字符串在返回的数组中，当且仅当至少有一个MBean注册了一个ObjectName，
//它的getDomain（）等于该字符串。 没有定义返回数组中字符串的顺序。
String[] domains = server.getDomains();//默认添加的JMImplementation
//	获取当前注册的MBean的数量
Integer mBeanCount = server.getMBeanCount();//1

String string = server.toString();
	}

}
