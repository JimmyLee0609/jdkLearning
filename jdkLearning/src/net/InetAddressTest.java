package net;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class InetAddressTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		 inetAddress();
		 inet4Address();
		// inet6Address();
		// inetSocketAddress();
//		scanLocalPort();
//		multicastSocket();
		networkInterface();
	}

	private static void scanLocalPort() {
		String host="127.0.0.1";
		Socket socket=null;
		for(int port=1;port<1024;port++){
			try {
				socket = new Socket(host,port);
				System.out.println("There is a server on port"+port);
			} catch (IOException e) {
//				System.out.println(port+"is not availiabe");
			}finally{
				if(socket!=null){
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

	@SuppressWarnings("unused")
	private static void multicastSocket() throws IOException {
//		多点组播
//		IP协议为多点广播提供了这批特殊的IP地址，这些IP地址的范围是224.0.0.0至239.255.255.255
		MulticastSocket multicastSocket = new MulticastSocket();
		multicastSocket.close();
		MulticastSocket multicastSocket2 = new MulticastSocket(8989);
		multicastSocket2.close();
		MulticastSocket multicastSocket3 = new MulticastSocket(InetSocketAddress.createUnresolved("224.5.5.5", 5566));
		multicastSocket3.close();
	}

	@SuppressWarnings("unused")
	private static void inetSocketAddress() throws UnknownHostException {
		// 这个类实现一个IP套接字地址（IP地址+端口号）它也可以是一对（主机名+端口号），在这种情况下将尝试解析主机名。
		// 如果解析失败，那么地址被认为是未解决的，但仍然可以在某些情况下使用，如通过代理连接。
		// 它提供了一个由套接字用来绑定，连接或作为返回值的不可变对象。
		// 通配符是一个特殊的本地IP地址。 它通常意味着“任何”，只能用于绑定操作。
		// 构造函数
		// 创建套接字地址，其中 IP 地址为通配符地址，端口号为指定值。
		InetSocketAddress inetSocketAddress = new InetSocketAddress(8989);
		// 根据 IP 地址和端口号创建套接字地址。
		InetSocketAddress inetSocketAddress2 = new InetSocketAddress(Inet4Address.getLocalHost(), 5656);
		// 根据主机名和端口号创建套接字地址。
		InetSocketAddress inetSocketAddress3 = new InetSocketAddress("localhost", 8989);
		// 根据主机名和端口号创建未解析的套接字地址。
		InetSocketAddress createUnresolved = InetSocketAddress.createUnresolved("127.1.1.1", 8093);
		// 通配符地址，可以是匹配任意地址
		InetAddress address = inetSocketAddress.getAddress();// 0.0.0.0/0.0.0.0
		int port = inetSocketAddress.getPort();// 8989
		boolean unresolved = inetSocketAddress.isUnresolved();// false
		String hostName = inetSocketAddress.getHostName();// 0.0.0.0
		String hostString = inetSocketAddress.getHostString();// 0.0.0.0
	}

	@SuppressWarnings("unused")
	private static void networkInterface() throws SocketException, UnknownHostException {
		NetworkInterface byIndex = NetworkInterface.getByIndex(1);
		// name:lo (Software Loopback Interface 1)

		// 一个便捷方法，搜索绑定了指定 Internet 协议 (IP) 地址的网络接口。
		NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(Inet4Address.getByName("www.baidu.com"));// null
		// 搜索具有指定名称的网络接口。
		NetworkInterface byName = NetworkInterface.getByName("www.baidu.com");// null
		// 返回此机器上的所有接口。
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		while (networkInterfaces.hasMoreElements()) {
			NetworkInterface nextElement = networkInterfaces.nextElement();
			byte[] hardwareAddress = nextElement.getHardwareAddress();
			String displayName = nextElement.getDisplayName();
			Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
			List<InterfaceAddress> interfaceAddresses = nextElement.getInterfaceAddresses();
			boolean supportsMulticast = nextElement.supportsMulticast();
			System.out.println("name : " + displayName + "   HardwareAddress:" + hardwareAddress
					+ "  issupportsMulticast:  " + supportsMulticast);
			System.out.println("InterfaceAddress : " + Arrays.toString(interfaceAddresses.toArray()));
			System.out.println("sub inetAddresses");
			while(inetAddresses.hasMoreElements()){
				InetAddress nextElement2 = inetAddresses.nextElement();
				System.out.println(nextElement2);
			}
		}

		// 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
		byte[] hardwareAddress = byIndex.getHardwareAddress();// null
		// 获取此网络接口的显示名称
		String displayName = byIndex.getDisplayName();// Software Loopback
														// Interface 1
		int index = byIndex.getIndex();// 1
		// 一个便捷方法，返回一个具有绑定到此网络接口全部或部分 InetAddress 的 Enumeration。
		Enumeration<InetAddress> inetAddresses = byIndex.getInetAddresses();
		while (inetAddresses.hasMoreElements())
			System.out.println(inetAddresses.nextElement());
		// 获取此网络接口的全部或部分 InterfaceAddresses 所组成的列表。
		List<InterfaceAddress> interfaceAddresses = byIndex.getInterfaceAddresses();
		for (InterfaceAddress interfaceAddress : interfaceAddresses) {
			System.out.println(interfaceAddress);
		}
		// 返回此接口的最大传输单元（Maximum Transmission Unit，MTU）。
		int mtu = byIndex.getMTU();// -1
		// 获取此网络接口的名称。
		String name = byIndex.getName();// lo
		// 如果此接口是子接口，则返回它的父 NetworkInterface；如果它是物理（非虚拟）接口或没有父接口，则返回 null。
		NetworkInterface parent = byIndex.getParent();// null
		// 获取具有连接到此网络接口的所有子接口（也称虚拟接口）的 Enumeration。
		Enumeration<NetworkInterface> subInterfaces = byIndex.getSubInterfaces();
		while (subInterfaces.hasMoreElements())
			System.out.println(subInterfaces);
		// 返回网络接口是否是回送接口。
		boolean loopback = byIndex.isLoopback();// true
		// 返回网络接口是否是点对点接口。
		boolean pointToPoint = byIndex.isPointToPoint();// false
		// 返回网络接口是否已经开启并运行。
		boolean up = byIndex.isUp();// true
		// 返回此接口是否是虚拟接口（也称为子接口）。
		boolean virtual = byIndex.isVirtual();// false
		// 返回网络接口是否支持多址广播。
		boolean supportsMulticast = byIndex.supportsMulticast();// true
		String string = byIndex.toString();

	}

	@SuppressWarnings("unused")
	private static void inet6Address() throws IOException {
		// 获取本机回环地址
		// alhost/127.0.0.1
		InetAddress loopbackAddress = Inet6Address.getLoopbackAddress();
		// 获取本机，主机地址
		// DESKTOP-0MCNN4Q/169.254.133.200
		InetAddress localHost = Inet6Address.getLocalHost();
		// 根据域名获取地址
		// www.bing.com/202.89.233.101
		InetAddress byName = Inet6Address.getByName("www.bing.com");
		// 根据域名获取地址
		// [www.baidu.com/14.215.177.38]
		InetAddress[] allByName = Inet6Address.getAllByName("www.baidu.com");
		// 根据IP创建地址 /127.0.0.1
		InetAddress byAddress = Inet6Address.getByAddress(new byte[] { 127, 0, 0, 1 });

		InetAddress byAddress2 = Inet6Address.getByAddress("www.bing.com", new byte[] { 127, 0, 0, 1 });
		/*
		 * Inet6Address byAddress3 = Inet6Address.getByAddress("www.bing.com",
		 * new byte[]{14,(byte)215,(byte)177,38}, 80);
		 */
		String hostAddress = byName.getHostAddress();// 202.89.233.100
		String hostName = byName.getHostName();// www.bing.com
		String canonicalHostName = byName.getCanonicalHostName();// www.bing.com
		byte[] address = byName.getAddress();// [-54, 89, -23, 100]
		boolean anyLocalAddress = byName.isAnyLocalAddress();// false
		boolean linkLocalAddress = byName.isLinkLocalAddress();// false
		boolean loopbackAddress2 = byName.isLoopbackAddress();// false
		boolean mcGlobal = byName.isMCGlobal();// false
		boolean mcLinkLocal = byName.isMCLinkLocal();// false
		boolean mcNodeLocal = byName.isMCNodeLocal();// false
		boolean mcOrgLocal = byName.isMCOrgLocal();// false
		boolean mcSiteLocal = byName.isMCSiteLocal();// false
		boolean multicastAddress = byName.isMulticastAddress();// false
		boolean siteLocalAddress = byName.isSiteLocalAddress();// false
		boolean reachable = byName.isReachable(1);// false
		boolean reachable2 = byName.isReachable(NetworkInterface.getByName("www.baidu.com"), 1, 1);// false
	}

	@SuppressWarnings("unused")
	private static void inet4Address() throws IOException {
		// 根据主机名字获取地址[www.bing.com/202.89.233.101, www.bing.com/202.89.233.100]
		// 会去网络找
		InetAddress[] allByName = Inet4Address.getAllByName("www.bing.com");
		// 根据IP地址，创建地址 不会去网上找 /14.215.177.38
		InetAddress byAddress = Inet4Address.getByAddress(new byte[] { 14, (byte) 215, (byte) 177, 38 });
		// 根据IP和和主机名字创建地址，不会上网找
		InetAddress byAddress2 = Inet4Address.getByAddress("www.bing.com",
				new byte[] { 14, (byte) 215, (byte) 177, 38 });
		// 根据主机名字获取地址
		// www.17173.com/117.27.230.80
		InetAddress byName = Inet4Address.getByName("www.17173.com");
		// DESKTOP-0MCNN4Q/169.254.133.200
		// 获取本机地址
		InetAddress localHost = Inet4Address.getLocalHost();
		// 获取回环地址
		// localhost/127.0.0.1
		InetAddress loopbackAddress = Inet4Address.getLoopbackAddress();

		// 主机地址 117.27.230.80
		String hostAddress = byName.getHostAddress();
		// www.17173.com 主机名
		String hostName = byName.getHostName();
		// www.17173.com 获取完全限定名
		String canonicalHostName = byName.getCanonicalHostName();
		// 获取IP [117, 27, -26, 80]
		byte[] address = byName.getAddress();
		// 是否是通配符地址的实用例行程序
		boolean anyLocalAddress = byName.isAnyLocalAddress();// false
		// 检查 InetAddress 是否是链接本地地址的实用例行程序。
		boolean linkLocalAddress = byName.isLinkLocalAddress();// false
		// 检查 InetAddress 是否是回送地址的实用例行程序。
		boolean loopbackAddress2 = byName.isLoopbackAddress();// false
		// 检查多播地址是否具有全局范围的实用例行程序。
		boolean mcGlobal = byName.isMCGlobal();// false
		// 检查多播地址是否具有链接范围的实用例行程序。
		boolean mcLinkLocal = byName.isMCLinkLocal();// false
		// 检查多播地址是否具有节点范围的实用例行程序。
		boolean mcNodeLocal = byName.isMCNodeLocal();// false
		// 检查多播地址是否具有组织范围的实用例程。
		boolean mcOrgLocal = byName.isMCOrgLocal();// false
		// 检查多播地址是否具有站点范围的实用例行程序。
		boolean mcSiteLocal = byName.isMCSiteLocal();// false
		// 检查 InetAddress 是否是 IP 多播地址的实用例行程序。
		boolean multicastAddress = byName.isMulticastAddress();// false
		// 检查 InetAddress 是否是站点本地地址的实用例行程序。
		boolean siteLocalAddress = byName.isSiteLocalAddress();// false
		// 测试是否可以达到该地址。
		boolean reachable = byName.isReachable(1);// false
		// 测试是否可以达到该地址。
		boolean reachable2 = byName.isReachable(NetworkInterface.getByName("www.baidu.com"), 5, 1);// false

	}

	@SuppressWarnings("unused")
	private static void inetAddress() throws IOException {
		
		/*link-local scope 224.0.0.0/24
		 * 管理组播的范围239.0.0.0 --- 239.255.255.255.
		 * 224.1.0.0-224.1.255.255         ST Multicast Groups
        224.2.0.0-224.2.127.253         Multimedia Conference Calls
        224.2.127.254                   SAPv1 Announcements
        224.2.127.255                   SAPv0 Announcements (deprecated)
        224.2.128.0-224.2.255.255       SAP Dynamic Assignments
        224.252.0.0-224.255.255.255     DIS transient groups
        232.0.0.0-232.255.255.255       VMTP transient groups
*/
		// 跟定主机的名称，根据系统上配置的名称服务返回其IP地址组。
		// 如果主机为空，就返回Lookback接口地址的InetAddress
		// www.bing.com/202.89.233.101 www.bing.com/202.89.233.100
		// 会去互联网查找
//		InetAddress[] allByName = InetAddress.getAllByName("www.bing.com");

		// /127.0.0.1 给出给定原始IP地址的InetAddress对象。该方法不阻止，即不执行反向名称服务查找
		InetAddress byAddress = InetAddress.getByAddress(
				new byte[] { Byte.valueOf("127"), Byte.valueOf("0"), Byte.valueOf("0"), Byte.valueOf("1") });

		InetAddress byAddress2 = InetAddress.getByAddress("www.bing.com",
				new byte[] { (byte) 202, 89, (byte) 233, 101 });
		InetAddress byAddress3 = InetAddress.getByAddress(new byte[]{(byte)202,5,5,(byte)252});
		
		// 确定主机名称的IP地址。 www.bing.com/202.89.233.101
		// 会去互联网查找
		InetAddress byName = InetAddress.getByName("www.bing.com");
		InetAddress byName2 = InetAddress.getByName("192.168.1.1");
		// 返回本机ip地址，通过从系统中检索主机的名称，然后将名称解析为InetAddress来实现
		// DESKTOP-0MCNN4Q/192.168.11.1
		InetAddress localHost = InetAddress.getLocalHost();
		// 回环地址，ipv4的回环地址127.0.0.1，ipv6的回环地址：：1 也可以写成 0：0：0：1
		InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
		// localhost/127.0.0.1

		// 返回 IP 地址字符串（以文本表现形式）。
		String hostAddress = byName.getHostAddress();// 202.89.233.101
		// 获取此 IP 地址的主机名。
		String hostName = byName.getHostName();// www.bing.com
		// 获取此 IP 地址的完全限定域名。最大努力方法，意味着根据底层系统配置可能不能返回 FQDN。
		String canonicalHostName = byName.getCanonicalHostName();// cn.bing.com
		// 返回此 InetAddress 对象的原始 IP 地址。结果按网络字节顺序：地址的高位字节位于
		byte[] address = byName.getAddress();// [-54, 89, -23, 101]
												// 由于202超过127就是表现形式变了
		
		
		// 是否是通配符地址的实用例行程序.  任意的本地通配地址，当不知道IP时使用   ipv4  0.0.0.0 ipv6 0::0  
		boolean anyLocalAddress = byName.isAnyLocalAddress();// false
		boolean anyLocalAddress2 = InetAddress.getByName("0.0.0.0").isAnyLocalAddress();//true
		boolean anyLocalAddress3 = InetAddress.getByName("0::0").isAnyLocalAddress();//true
		boolean anyLocalAddress4 = InetAddress.getByName("FE::0").isAnyLocalAddress();//FALSE
		
		// 检查 InetAddress 是否是链接本地地址的实用例行程序。
//		在计算机网络中,链路本地地址是一个网络地址, 仅对网络段(链接) 内的通信或主机连接到的广播域有效。.
//		链路本地地址不能保证在单个网络段之外是唯一的。因此,路由器不会转发带有链接本地地址的数据包。
//		ipv4  169.254/16  开头
//		ipv6   fe80: 开头
		boolean linkLocalAddress = byName.isLinkLocalAddress();// false
		boolean linkLocalAddress2 = InetAddress.getByName("169.254.0.0").isLinkLocalAddress();//true
		boolean linkLocalAddress4 = InetAddress.getByName("169.254.1.5").isLinkLocalAddress();//true
		boolean linkLocalAddress3 = InetAddress.getByName("fe80::10").isLinkLocalAddress();//true
		boolean linkLocalAddress5 = InetAddress.getByName("fe80:32::10").isLinkLocalAddress();//true
		
		// 检查 InetAddress 是否是回环地址。
//		ipv4 127开头
//		ipv6  0::1
		boolean loopbackAddress2 = byName.isLoopbackAddress();// false
		boolean loopbackAddress3 = InetAddress.getByName("127.0.0.1").isLoopbackAddress();//true
		boolean loopbackAddress5 = InetAddress.getByName("127.3.0.1").isLoopbackAddress();//true
		boolean loopbackAddress4 = InetAddress.getByName("0::1").isLoopbackAddress();//true
		
		// 检查多播地址是否具有全局域的实用例行程序。
//		IPV4  224.0.1.0 to 238.255.255.255
//		IPV6   FF0E::0
		boolean mcGlobal = byName.isMCGlobal();// false
		boolean mcGlobal3 = InetAddress.getByName("224.0.0.1").isMCGlobal();//false
		boolean mcGlobal2 = InetAddress.getByName("224.0.1.1").isMCGlobal();//true
		boolean mcGlobal5 = InetAddress.getByName("238.255.255.254").isMCGlobal();//true
		boolean mcGlobal4 = InetAddress.getByName("ff0e::1").isMCGlobal();//true
		
		
		// 检查多播地址是否具有链接范围的实用例行程序。  用于局域网的多播地址
//		IPV4   224.0.0/24 prefix and ttl == 1  范围224.0.0.0~224.0.0.255
//		IPV6    FF02::0
		boolean mcLinkLocal = byName.isMCLinkLocal();// false
		boolean mcLinkLocal2 = InetAddress.getByName("224.0.0.5").isMCLinkLocal();//true
		boolean mcLinkLocal3 = InetAddress.getByName("ff02::1").isMCLinkLocal();//true
		
		
		
		// 检查多播地址是否具有节点范围的实用例行程序。这个类的实现就是返回false
//		IPV4    返回false   unless ttl == 0
//		IPV6     FF01   开头
		boolean mcNodeLocal = byName.isMCNodeLocal();// false
		boolean mcNodeLocal3 = InetAddress.getByName("224.0.0.5").isMCNodeLocal();//false
		boolean mcNodeLocal2 = InetAddress.getByName("ff01::1").isMCNodeLocal();//true
		
		// 检查多播地址是否具有组织范围的实用例行程序。这个类的实现就是返回false
//		ipv4    239.192 - 239.195    用于org的多播地址
//		ipv6    ff08开头
		boolean mcOrgLocal = byName.isMCOrgLocal();// false
		boolean mcOrgLocal2 = InetAddress.getByName("ff08::1").isMCOrgLocal();//true
		boolean mcOrgLocal3 = InetAddress.getByName("239.192.5.5").isMCOrgLocal();//true
		boolean mcOrgLocal4 = InetAddress.getByName("239.195.195.5").isMCOrgLocal();//true
		
		
		// 检查多播地址是否具有站点范围的实用例行程序。，这个类的实现就是返回false
//		ipv4   239.255/16 prefix or ttl < 32
//		ipv6   ff05::0  开头
		boolean mcSiteLocal = byName.isMCSiteLocal();// false
		boolean mcSiteLocal3 = InetAddress.getByName("239.255.0.5").isMCSiteLocal();//true
		boolean mcSiteLocal4 = InetAddress.getByName("239.255.1.5").isMCSiteLocal();//true
		boolean mcSiteLocal2 = InetAddress.getByName("ff05::1").isMCSiteLocal();//true
		
		// 检查地址是否是IP多播地址的实用例行程序
//		ipv4   D类地址 1110开头  0xe0000000
//		ipv6   0xff 开头 就是FF01-FF02等
		boolean multicastAddress = byName.isMulticastAddress();// false
		boolean multicastAddress2 = InetAddress.getByName("224.0.0.0").isMulticastAddress();//true
		boolean multicastAddress3 = InetAddress.getByName("ff01::1").isMulticastAddress();//true
		// 检查 InetAddress 是否是站点本地地址的实用例行程序。这个类的实现就是返回false
		  //ipv4   私有地址     10/8 prefix  172.16/12 prefix   192.168/16 prefix
//		ipv6  私有地址 fec0:  开头
		boolean siteLocalAddress = byName.isSiteLocalAddress();// false
		boolean siteLocalAddress2 = InetAddress.getByName("10.0.01").isSiteLocalAddress();//true
		boolean siteLocalAddress3 = InetAddress.getByName("172.16.53.23").isSiteLocalAddress();//true
		boolean siteLocalAddress4 = InetAddress.getByName("192.168.1.5").isSiteLocalAddress();//true
		boolean siteLocalAddress5 = InetAddress.getByName("fec0:01::1").isSiteLocalAddress();//true

		// 测试是否可以达到该地址。实现尽最大努力试图到达主机，但防火墙和服务器配置可能阻塞请求，
		// 使其在某些特定的端口可以访问时处于不可到达状态。
		// 如果可以获得权限，则典型实现将使用 ICMP ECHO REQUEST；

		// 否则它将试图在目标主机的端口 7 (Echo) 上建立 TCP 连接。
		// 看目标的echo端口是否可达，在局域网，可用。外网一般有防护墙
		boolean reachable = byName.isReachable(5);// false
		boolean reachable2 = byName.isReachable(NetworkInterface.getByName("www.bing.com"), 8, 1);// false
	}

}
