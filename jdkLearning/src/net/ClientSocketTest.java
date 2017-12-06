package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

public class ClientSocketTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
//		serverSocketConnect();
		Socket socket = new Socket();
		InetSocketAddress bindAddress = new InetSocketAddress(5657);
//		绑定本机的地址,可以绑定null值，就不会监听本机的端口
		socket.bind(bindAddress);
		InetSocketAddress connectAddress = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 5656);

//		连接到一个远程的地址		
		socket.connect(connectAddress, 500);
		
		connect();
		connect();
		connect();
		connect();
		connect();
		connect();
		connect();
		
		
		
		InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream(),"gbk");
		char[] ch=new char[1024];
		inputStreamReader.read(ch);
		socket.getOutputStream().write("客户端写会数据".getBytes());
//		是否保持存活
		boolean keepAlive = socket.getKeepAlive();//false
//		获取管道，只有channel类打开的套接字才会有
		SocketChannel channel = socket.getChannel();//null
//		获取链接到的IP
		InetAddress inetAddress = socket.getInetAddress();//192.16.3.103
//		获取本机的IP
		InetAddress localAddress = socket.getLocalAddress();//192.16.3.103
//		获取本地端口
		int localPort = socket.getLocalPort();//5657
//		获取本地的Socket地址
		SocketAddress localSocketAddress = socket.getLocalSocketAddress();///192.16.3.103:5657
//		获取连接到的端口
		int port = socket.getPort();//5656
		
//		 SO_OOBINLINE 标志位是否为true
//		false  紧急数据被默默丢弃。true 会接收紧急数据
		boolean oobInline = socket.getOOBInline();//false
		
//		从此套接字发送的数据包的IP头中获取流量类别或服务类型
		int trafficClass = socket.getTrafficClass();//0
		
//		TCP_NODELAY 标志位是否为true
//		是否  禁用此连接的Nagle算法。 写入数据到网络不会被缓存，以确认之前写入的数据。
		boolean tcpNoDelay = socket.getTcpNoDelay();//false
//		获取链接到的地址
		SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();///192.16.3.103:5656
//		是否打开了拒绝地址
		boolean reuseAddress = socket.getReuseAddress();//false
		
//		SO_LINGER 标志位是否为
//		指定延时关闭超时。 该选项禁用/启用从TCP套接字的close（）。小于0就直接关闭，大于0 就等待指定的时间再关闭。
		int soLinger = socket.getSoLinger();//-1
//		获取接收的缓冲区大小
		int receiveBufferSize = socket.getReceiveBufferSize();//65536
//		获取发送的缓冲区大小
		int sendBufferSize = socket.getSendBufferSize();//65536
//		获取超时的时间
		int soTimeout = socket.getSoTimeout();//0

		
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();
		socket.close();
	}

	private static void serverSocketConnect() throws IOException {
ServerSocket serverSocket = new ServerSocket();		
InetSocketAddress inetSocketAddress = new InetSocketAddress(5656);
serverSocket.bind(inetSocketAddress, 2);
Socket accept = serverSocket.accept();


	}

	private static void connect() throws IOException {
		new Thread(()->{
		Socket socket = new Socket();	
		InetSocketAddress endpoint;
		try {
			endpoint = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 5656);
			socket.connect(endpoint);
			System.out.println(socket+"连接成功");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}).start();;
	}

}
