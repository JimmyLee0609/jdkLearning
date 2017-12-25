package net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

public class DatagramSocketServerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		 normalReceive();
//		testBroadcast();
		
	}

	private static void testBroadcast() {
		Thread thread = new Thread(() -> {
			try {
				DatagramPacket p = new DatagramPacket(new byte[50], 50);
				DatagramSocket datagramSocket = new DatagramSocket(6060,InetAddress.getByName("192.16.3.103"));
				
				System.out.println("本地端口： "+datagramSocket.getLocalPort()+"  本地IP ："+datagramSocket.getLocalAddress().toString());
				datagramSocket.setBroadcast(true);
				datagramSocket.receive(p);
				
				System.out.println("获取到信息    "+new String(p.getData(),"gbk"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread.start();
	}

	@SuppressWarnings("unused")
	private static void normalReceive() throws IOException {
		DatagramSocket server = new DatagramSocket(5959);
		// 端口绑定了就不能改变，会抛出异常
		// server.bind(new InetSocketAddress(5959));
		// 连接到远程IP端口，连接后就只能给这个IP发送数据，否则抛出异常
		server.connect(new InetSocketAddress("192.16.3.103", 5757));

		// 广播的状态，默认开启，可以接收，发送广播
		boolean broadcast = server.getBroadcast();
		// 获取管道，只有channel包打开的通道才会有
		DatagramChannel channel = server.getChannel();// null


		// 获取连接超时，0就是永久阻塞
		int soTimeout = server.getSoTimeout();// 0
		// 获取IP交流的状态码类
		int trafficClass = server.getTrafficClass();// 0


		byte[] buf = new byte[1024];
		// 新建接收数据的包，每次会根据这个包的大小来截断接收到的数据。
		DatagramPacket datagramPacket = new DatagramPacket(buf, 1024);
		server.setBroadcast(true);

		// 接收包，根据timeout来决定等待时间。超时抛出异常
		server.receive(datagramPacket);
		String string2 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string3 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string4 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string5 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string6 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string7 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string8 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string9 = new String(datagramPacket.getData(),"gbk");
				
		
		

		address(server);
		buffer(server);
		// 获取包中的内容
		byte[] data = datagramPacket.getData();// 就是将收到的数据写到传入的缓冲中。
		// 将接收到的包的部分内容转换为字符来看
		String string = new String(data, 0, 23, "gbk");// 11111111测试发送的数据?
		// 接收到的字节的长度
		int length = datagramPacket.getLength();// 23 会记录收到的数据的长度
		// 包的偏移量，与发送包的偏移量无关，仅关心自己的包
		int offset = datagramPacket.getOffset();// 0
		// 获取发送端的端口号
		int port2 = datagramPacket.getPort();// 5757
		// 获取发送端的IP
		InetAddress address = datagramPacket.getAddress();/// 192.168.1.50

		server.close();
	}

	private static void buffer(DatagramSocket server) throws SocketException {
		// 获取接收缓冲区大小
		int receiveBufferSize = server.getReceiveBufferSize();// 65536

		// 连接关闭时是否拒绝
		boolean reuseAddress = server.getReuseAddress();// false
		// 获取发送缓冲大小
		int sendBufferSize = server.getSendBufferSize();// 65536
		// 设置缓冲区大小，只影响速度吧。
		server.setReceiveBufferSize(500);
	}

	private static void address(DatagramSocket server) {
		// 获取本地端口
		int localPort = server.getLocalPort();// 959
		// 获取远端地址
		InetAddress inetAddress = server.getInetAddress();
		// 获取本地地址
		InetAddress localAddress = server.getLocalAddress();// 0.0.0.0/0.0.0.0
		// 获取本地的socket地址
		SocketAddress localSocketAddress = server.getLocalSocketAddress();// 0.0.0.0/0.0.0.0:5959
		// 获取连接的端口
		int port = server.getPort();// 5757
		// 获取远端的socket
		SocketAddress remoteSocketAddress = server.getRemoteSocketAddress();// 0.0.0.0/0.0.0.0:5757
	}

}
