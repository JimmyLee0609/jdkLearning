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
//		����鲥
//		IPЭ��Ϊ���㲥�ṩ�����������IP��ַ����ЩIP��ַ�ķ�Χ��224.0.0.0��239.255.255.255
		MulticastSocket multicastSocket = new MulticastSocket();
		multicastSocket.close();
		MulticastSocket multicastSocket2 = new MulticastSocket(8989);
		multicastSocket2.close();
		MulticastSocket multicastSocket3 = new MulticastSocket(InetSocketAddress.createUnresolved("224.5.5.5", 5566));
		multicastSocket3.close();
	}

	@SuppressWarnings("unused")
	private static void inetSocketAddress() throws UnknownHostException {
		// �����ʵ��һ��IP�׽��ֵ�ַ��IP��ַ+�˿ںţ���Ҳ������һ�ԣ�������+�˿ںţ�������������½����Խ�����������
		// �������ʧ�ܣ���ô��ַ����Ϊ��δ����ģ�����Ȼ������ĳЩ�����ʹ�ã���ͨ���������ӡ�
		// ���ṩ��һ�����׽��������󶨣����ӻ���Ϊ����ֵ�Ĳ��ɱ����
		// ͨ�����һ������ı���IP��ַ�� ��ͨ����ζ�š��κΡ���ֻ�����ڰ󶨲�����
		// ���캯��
		// �����׽��ֵ�ַ������ IP ��ַΪͨ�����ַ���˿ں�Ϊָ��ֵ��
		InetSocketAddress inetSocketAddress = new InetSocketAddress(8989);
		// ���� IP ��ַ�Ͷ˿ںŴ����׽��ֵ�ַ��
		InetSocketAddress inetSocketAddress2 = new InetSocketAddress(Inet4Address.getLocalHost(), 5656);
		// �����������Ͷ˿ںŴ����׽��ֵ�ַ��
		InetSocketAddress inetSocketAddress3 = new InetSocketAddress("localhost", 8989);
		// �����������Ͷ˿ںŴ���δ�������׽��ֵ�ַ��
		InetSocketAddress createUnresolved = InetSocketAddress.createUnresolved("127.1.1.1", 8093);
		// ͨ�����ַ��������ƥ�������ַ
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

		// һ����ݷ�������������ָ�� Internet Э�� (IP) ��ַ������ӿڡ�
		NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(Inet4Address.getByName("www.baidu.com"));// null
		// ��������ָ�����Ƶ�����ӿڡ�
		NetworkInterface byName = NetworkInterface.getByName("www.baidu.com");// null
		// ���ش˻����ϵ����нӿڡ�
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

		// �������Ӳ����ַ������ʹ�ø����ĵ�ǰȨ�޷��ʣ��򷵻ظ�Ӳ����ַ��ͨ���� MAC����
		byte[] hardwareAddress = byIndex.getHardwareAddress();// null
		// ��ȡ������ӿڵ���ʾ����
		String displayName = byIndex.getDisplayName();// Software Loopback
														// Interface 1
		int index = byIndex.getIndex();// 1
		// һ����ݷ���������һ�����а󶨵�������ӿ�ȫ���򲿷� InetAddress �� Enumeration��
		Enumeration<InetAddress> inetAddresses = byIndex.getInetAddresses();
		while (inetAddresses.hasMoreElements())
			System.out.println(inetAddresses.nextElement());
		// ��ȡ������ӿڵ�ȫ���򲿷� InterfaceAddresses ����ɵ��б�
		List<InterfaceAddress> interfaceAddresses = byIndex.getInterfaceAddresses();
		for (InterfaceAddress interfaceAddress : interfaceAddresses) {
			System.out.println(interfaceAddress);
		}
		// ���ش˽ӿڵ�����䵥Ԫ��Maximum Transmission Unit��MTU����
		int mtu = byIndex.getMTU();// -1
		// ��ȡ������ӿڵ����ơ�
		String name = byIndex.getName();// lo
		// ����˽ӿ����ӽӿڣ��򷵻����ĸ� NetworkInterface������������������⣩�ӿڻ�û�и��ӿڣ��򷵻� null��
		NetworkInterface parent = byIndex.getParent();// null
		// ��ȡ�������ӵ�������ӿڵ������ӽӿڣ�Ҳ������ӿڣ��� Enumeration��
		Enumeration<NetworkInterface> subInterfaces = byIndex.getSubInterfaces();
		while (subInterfaces.hasMoreElements())
			System.out.println(subInterfaces);
		// ��������ӿ��Ƿ��ǻ��ͽӿڡ�
		boolean loopback = byIndex.isLoopback();// true
		// ��������ӿ��Ƿ��ǵ�Ե�ӿڡ�
		boolean pointToPoint = byIndex.isPointToPoint();// false
		// ��������ӿ��Ƿ��Ѿ����������С�
		boolean up = byIndex.isUp();// true
		// ���ش˽ӿ��Ƿ�������ӿڣ�Ҳ��Ϊ�ӽӿڣ���
		boolean virtual = byIndex.isVirtual();// false
		// ��������ӿ��Ƿ�֧�ֶ�ַ�㲥��
		boolean supportsMulticast = byIndex.supportsMulticast();// true
		String string = byIndex.toString();

	}

	@SuppressWarnings("unused")
	private static void inet6Address() throws IOException {
		// ��ȡ�����ػ���ַ
		// alhost/127.0.0.1
		InetAddress loopbackAddress = Inet6Address.getLoopbackAddress();
		// ��ȡ������������ַ
		// DESKTOP-0MCNN4Q/169.254.133.200
		InetAddress localHost = Inet6Address.getLocalHost();
		// ����������ȡ��ַ
		// www.bing.com/202.89.233.101
		InetAddress byName = Inet6Address.getByName("www.bing.com");
		// ����������ȡ��ַ
		// [www.baidu.com/14.215.177.38]
		InetAddress[] allByName = Inet6Address.getAllByName("www.baidu.com");
		// ����IP������ַ /127.0.0.1
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
		// �����������ֻ�ȡ��ַ[www.bing.com/202.89.233.101, www.bing.com/202.89.233.100]
		// ��ȥ������
		InetAddress[] allByName = Inet4Address.getAllByName("www.bing.com");
		// ����IP��ַ��������ַ ����ȥ������ /14.215.177.38
		InetAddress byAddress = Inet4Address.getByAddress(new byte[] { 14, (byte) 215, (byte) 177, 38 });
		// ����IP�ͺ��������ִ�����ַ������������
		InetAddress byAddress2 = Inet4Address.getByAddress("www.bing.com",
				new byte[] { 14, (byte) 215, (byte) 177, 38 });
		// �����������ֻ�ȡ��ַ
		// www.17173.com/117.27.230.80
		InetAddress byName = Inet4Address.getByName("www.17173.com");
		// DESKTOP-0MCNN4Q/169.254.133.200
		// ��ȡ������ַ
		InetAddress localHost = Inet4Address.getLocalHost();
		// ��ȡ�ػ���ַ
		// localhost/127.0.0.1
		InetAddress loopbackAddress = Inet4Address.getLoopbackAddress();

		// ������ַ 117.27.230.80
		String hostAddress = byName.getHostAddress();
		// www.17173.com ������
		String hostName = byName.getHostName();
		// www.17173.com ��ȡ��ȫ�޶���
		String canonicalHostName = byName.getCanonicalHostName();
		// ��ȡIP [117, 27, -26, 80]
		byte[] address = byName.getAddress();
		// �Ƿ���ͨ�����ַ��ʵ�����г���
		boolean anyLocalAddress = byName.isAnyLocalAddress();// false
		// ��� InetAddress �Ƿ������ӱ��ص�ַ��ʵ�����г���
		boolean linkLocalAddress = byName.isLinkLocalAddress();// false
		// ��� InetAddress �Ƿ��ǻ��͵�ַ��ʵ�����г���
		boolean loopbackAddress2 = byName.isLoopbackAddress();// false
		// ���ಥ��ַ�Ƿ����ȫ�ַ�Χ��ʵ�����г���
		boolean mcGlobal = byName.isMCGlobal();// false
		// ���ಥ��ַ�Ƿ�������ӷ�Χ��ʵ�����г���
		boolean mcLinkLocal = byName.isMCLinkLocal();// false
		// ���ಥ��ַ�Ƿ���нڵ㷶Χ��ʵ�����г���
		boolean mcNodeLocal = byName.isMCNodeLocal();// false
		// ���ಥ��ַ�Ƿ������֯��Χ��ʵ�����̡�
		boolean mcOrgLocal = byName.isMCOrgLocal();// false
		// ���ಥ��ַ�Ƿ����վ�㷶Χ��ʵ�����г���
		boolean mcSiteLocal = byName.isMCSiteLocal();// false
		// ��� InetAddress �Ƿ��� IP �ಥ��ַ��ʵ�����г���
		boolean multicastAddress = byName.isMulticastAddress();// false
		// ��� InetAddress �Ƿ���վ�㱾�ص�ַ��ʵ�����г���
		boolean siteLocalAddress = byName.isSiteLocalAddress();// false
		// �����Ƿ���Դﵽ�õ�ַ��
		boolean reachable = byName.isReachable(1);// false
		// �����Ƿ���Դﵽ�õ�ַ��
		boolean reachable2 = byName.isReachable(NetworkInterface.getByName("www.baidu.com"), 5, 1);// false

	}

	@SuppressWarnings("unused")
	private static void inetAddress() throws IOException {
		
		/*link-local scope 224.0.0.0/24
		 * �����鲥�ķ�Χ239.0.0.0 --- 239.255.255.255.
		 * 224.1.0.0-224.1.255.255         ST Multicast Groups
        224.2.0.0-224.2.127.253         Multimedia Conference Calls
        224.2.127.254                   SAPv1 Announcements
        224.2.127.255                   SAPv0 Announcements (deprecated)
        224.2.128.0-224.2.255.255       SAP Dynamic Assignments
        224.252.0.0-224.255.255.255     DIS transient groups
        232.0.0.0-232.255.255.255       VMTP transient groups
*/
		// �������������ƣ�����ϵͳ�����õ����Ʒ��񷵻���IP��ַ�顣
		// �������Ϊ�գ��ͷ���Lookback�ӿڵ�ַ��InetAddress
		// www.bing.com/202.89.233.101 www.bing.com/202.89.233.100
		// ��ȥ����������
//		InetAddress[] allByName = InetAddress.getAllByName("www.bing.com");

		// /127.0.0.1 ��������ԭʼIP��ַ��InetAddress���󡣸÷�������ֹ������ִ�з������Ʒ������
		InetAddress byAddress = InetAddress.getByAddress(
				new byte[] { Byte.valueOf("127"), Byte.valueOf("0"), Byte.valueOf("0"), Byte.valueOf("1") });

		InetAddress byAddress2 = InetAddress.getByAddress("www.bing.com",
				new byte[] { (byte) 202, 89, (byte) 233, 101 });
		InetAddress byAddress3 = InetAddress.getByAddress(new byte[]{(byte)202,5,5,(byte)252});
		
		// ȷ���������Ƶ�IP��ַ�� www.bing.com/202.89.233.101
		// ��ȥ����������
		InetAddress byName = InetAddress.getByName("www.bing.com");
		InetAddress byName2 = InetAddress.getByName("192.168.1.1");
		// ���ر���ip��ַ��ͨ����ϵͳ�м������������ƣ�Ȼ�����ƽ���ΪInetAddress��ʵ��
		// DESKTOP-0MCNN4Q/192.168.11.1
		InetAddress localHost = InetAddress.getLocalHost();
		// �ػ���ַ��ipv4�Ļػ���ַ127.0.0.1��ipv6�Ļػ���ַ����1 Ҳ����д�� 0��0��0��1
		InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
		// localhost/127.0.0.1

		// ���� IP ��ַ�ַ��������ı�������ʽ����
		String hostAddress = byName.getHostAddress();// 202.89.233.101
		// ��ȡ�� IP ��ַ����������
		String hostName = byName.getHostName();// www.bing.com
		// ��ȡ�� IP ��ַ����ȫ�޶����������Ŭ����������ζ�Ÿ��ݵײ�ϵͳ���ÿ��ܲ��ܷ��� FQDN��
		String canonicalHostName = byName.getCanonicalHostName();// cn.bing.com
		// ���ش� InetAddress �����ԭʼ IP ��ַ������������ֽ�˳�򣺵�ַ�ĸ�λ�ֽ�λ��
		byte[] address = byName.getAddress();// [-54, 89, -23, 101]
												// ����202����127���Ǳ�����ʽ����
		
		
		// �Ƿ���ͨ�����ַ��ʵ�����г���.  ����ı���ͨ���ַ������֪��IPʱʹ��   ipv4  0.0.0.0 ipv6 0::0  
		boolean anyLocalAddress = byName.isAnyLocalAddress();// false
		boolean anyLocalAddress2 = InetAddress.getByName("0.0.0.0").isAnyLocalAddress();//true
		boolean anyLocalAddress3 = InetAddress.getByName("0::0").isAnyLocalAddress();//true
		boolean anyLocalAddress4 = InetAddress.getByName("FE::0").isAnyLocalAddress();//FALSE
		
		// ��� InetAddress �Ƿ������ӱ��ص�ַ��ʵ�����г���
//		�ڼ����������,��·���ص�ַ��һ�������ַ, ���������(����) �ڵ�ͨ�Ż��������ӵ��Ĺ㲥����Ч��.
//		��·���ص�ַ���ܱ�֤�ڵ��������֮����Ψһ�ġ����,·��������ת���������ӱ��ص�ַ�����ݰ���
//		ipv4  169.254/16  ��ͷ
//		ipv6   fe80: ��ͷ
		boolean linkLocalAddress = byName.isLinkLocalAddress();// false
		boolean linkLocalAddress2 = InetAddress.getByName("169.254.0.0").isLinkLocalAddress();//true
		boolean linkLocalAddress4 = InetAddress.getByName("169.254.1.5").isLinkLocalAddress();//true
		boolean linkLocalAddress3 = InetAddress.getByName("fe80::10").isLinkLocalAddress();//true
		boolean linkLocalAddress5 = InetAddress.getByName("fe80:32::10").isLinkLocalAddress();//true
		
		// ��� InetAddress �Ƿ��ǻػ���ַ��
//		ipv4 127��ͷ
//		ipv6  0::1
		boolean loopbackAddress2 = byName.isLoopbackAddress();// false
		boolean loopbackAddress3 = InetAddress.getByName("127.0.0.1").isLoopbackAddress();//true
		boolean loopbackAddress5 = InetAddress.getByName("127.3.0.1").isLoopbackAddress();//true
		boolean loopbackAddress4 = InetAddress.getByName("0::1").isLoopbackAddress();//true
		
		// ���ಥ��ַ�Ƿ����ȫ�����ʵ�����г���
//		IPV4  224.0.1.0 to 238.255.255.255
//		IPV6   FF0E::0
		boolean mcGlobal = byName.isMCGlobal();// false
		boolean mcGlobal3 = InetAddress.getByName("224.0.0.1").isMCGlobal();//false
		boolean mcGlobal2 = InetAddress.getByName("224.0.1.1").isMCGlobal();//true
		boolean mcGlobal5 = InetAddress.getByName("238.255.255.254").isMCGlobal();//true
		boolean mcGlobal4 = InetAddress.getByName("ff0e::1").isMCGlobal();//true
		
		
		// ���ಥ��ַ�Ƿ�������ӷ�Χ��ʵ�����г���  ���ھ������Ķಥ��ַ
//		IPV4   224.0.0/24 prefix and ttl == 1  ��Χ224.0.0.0~224.0.0.255
//		IPV6    FF02::0
		boolean mcLinkLocal = byName.isMCLinkLocal();// false
		boolean mcLinkLocal2 = InetAddress.getByName("224.0.0.5").isMCLinkLocal();//true
		boolean mcLinkLocal3 = InetAddress.getByName("ff02::1").isMCLinkLocal();//true
		
		
		
		// ���ಥ��ַ�Ƿ���нڵ㷶Χ��ʵ�����г���������ʵ�־��Ƿ���false
//		IPV4    ����false   unless ttl == 0
//		IPV6     FF01   ��ͷ
		boolean mcNodeLocal = byName.isMCNodeLocal();// false
		boolean mcNodeLocal3 = InetAddress.getByName("224.0.0.5").isMCNodeLocal();//false
		boolean mcNodeLocal2 = InetAddress.getByName("ff01::1").isMCNodeLocal();//true
		
		// ���ಥ��ַ�Ƿ������֯��Χ��ʵ�����г���������ʵ�־��Ƿ���false
//		ipv4    239.192 - 239.195    ����org�Ķಥ��ַ
//		ipv6    ff08��ͷ
		boolean mcOrgLocal = byName.isMCOrgLocal();// false
		boolean mcOrgLocal2 = InetAddress.getByName("ff08::1").isMCOrgLocal();//true
		boolean mcOrgLocal3 = InetAddress.getByName("239.192.5.5").isMCOrgLocal();//true
		boolean mcOrgLocal4 = InetAddress.getByName("239.195.195.5").isMCOrgLocal();//true
		
		
		// ���ಥ��ַ�Ƿ����վ�㷶Χ��ʵ�����г��򡣣�������ʵ�־��Ƿ���false
//		ipv4   239.255/16 prefix or ttl < 32
//		ipv6   ff05::0  ��ͷ
		boolean mcSiteLocal = byName.isMCSiteLocal();// false
		boolean mcSiteLocal3 = InetAddress.getByName("239.255.0.5").isMCSiteLocal();//true
		boolean mcSiteLocal4 = InetAddress.getByName("239.255.1.5").isMCSiteLocal();//true
		boolean mcSiteLocal2 = InetAddress.getByName("ff05::1").isMCSiteLocal();//true
		
		// ����ַ�Ƿ���IP�ಥ��ַ��ʵ�����г���
//		ipv4   D���ַ 1110��ͷ  0xe0000000
//		ipv6   0xff ��ͷ ����FF01-FF02��
		boolean multicastAddress = byName.isMulticastAddress();// false
		boolean multicastAddress2 = InetAddress.getByName("224.0.0.0").isMulticastAddress();//true
		boolean multicastAddress3 = InetAddress.getByName("ff01::1").isMulticastAddress();//true
		// ��� InetAddress �Ƿ���վ�㱾�ص�ַ��ʵ�����г���������ʵ�־��Ƿ���false
		  //ipv4   ˽�е�ַ     10/8 prefix  172.16/12 prefix   192.168/16 prefix
//		ipv6  ˽�е�ַ fec0:  ��ͷ
		boolean siteLocalAddress = byName.isSiteLocalAddress();// false
		boolean siteLocalAddress2 = InetAddress.getByName("10.0.01").isSiteLocalAddress();//true
		boolean siteLocalAddress3 = InetAddress.getByName("172.16.53.23").isSiteLocalAddress();//true
		boolean siteLocalAddress4 = InetAddress.getByName("192.168.1.5").isSiteLocalAddress();//true
		boolean siteLocalAddress5 = InetAddress.getByName("fec0:01::1").isSiteLocalAddress();//true

		// �����Ƿ���Դﵽ�õ�ַ��ʵ�־����Ŭ����ͼ����������������ǽ�ͷ��������ÿ�����������
		// ʹ����ĳЩ�ض��Ķ˿ڿ��Է���ʱ���ڲ��ɵ���״̬��
		// ������Ի��Ȩ�ޣ������ʵ�ֽ�ʹ�� ICMP ECHO REQUEST��

		// ����������ͼ��Ŀ�������Ķ˿� 7 (Echo) �Ͻ��� TCP ���ӡ�
		// ��Ŀ���echo�˿��Ƿ�ɴ�ھ����������á�����һ���з���ǽ
		boolean reachable = byName.isReachable(5);// false
		boolean reachable2 = byName.isReachable(NetworkInterface.getByName("www.bing.com"), 8, 1);// false
	}

}
