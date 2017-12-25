package net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.Instant;

public class ServerSocketTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
//		nobind();
		ServerSocket serverSocket = new ServerSocket(5656);
//		bind(serverSocket);
		
//		在accept的时候如果超过指定时间都没有接收到连接就抛出异常
//		serverSocket.setSoTimeout(5000);
		System.out.println(Instant.now());

		serverSocket.setReceiveBufferSize(65535);
//		在TCP连接关闭后的一段时间可能会保持一段时间的超时状态，这个设置这段时间是否拒绝连接
		serverSocket.setReuseAddress(false);
		
//		这个没有实现
		serverSocket.setPerformancePreferences(5000, 2, 2);
		Socket accept=null;
		try{
//		接收数据，永久等待, 获取连接到的套接字
		 
			Socket accept1 = serverSocket.accept();
//			accept1.getOutputStream().close();
			boolean closed = serverSocket.isClosed();
			boolean closed2 = accept1.isClosed();
//			永久阻塞等待
			Socket accept2 = serverSocket.accept();
			Socket accept3 = serverSocket.accept();
			Socket accept4 = serverSocket.accept();
			Socket accept5 = serverSocket.accept();
		 
		 accept.getOutputStream().write("测试输出字节".getBytes());
		 InputStreamReader inputStreamReader = new InputStreamReader(accept.getInputStream(),"gbk");
		 char[]cbuf=new char [1024];
		 inputStreamReader.read(cbuf);
		 Thread.sleep(5000);
		}catch(SocketTimeoutException  ste){
			System.out.println(Instant.now());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getIP(accept,serverSocket);
		
		
		
		
		
		
//		获取管道只有Channel的包中打开的管道才会有
		SocketChannel channel = accept.getChannel();//null
		
		serverSocket.close();
	}

	@SuppressWarnings("unused")
	private static void getIP(Socket accept, ServerSocket serverSocket) {

//		获取本地的IP
		InetAddress localAddress = accept.getLocalAddress();
//		获取本地的端口
		int localPort2 = serverSocket.getLocalPort();
//		获取连接的IP
		InetAddress inetAddress = serverSocket.getInetAddress();
//		获取本地的套接字地址
		SocketAddress localSocketAddress = serverSocket.getLocalSocketAddress();
		System.out.println("本机地址"+localSocketAddress+"连接到的地址"+accept);		
	}

	private static void bind(ServerSocket serverSocket) throws IOException {

//		可以绑定本机IP,和指定的端口，用于监听，不能监听子网内不存在的IP
		InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),5656);
//		绑定本机的地址，就是监听外网发送数据到这个地址，就能收到数据
		serverSocket.bind(inetSocketAddress);
		
//		绑定到本地的IP，如果是null就会由系统获取一个临时的有效IP和端口来绑定，  后面一个参数是请求最大连接数
//		serverSocket.bind(null, 50);		
	}

	@SuppressWarnings("unused")
	private static void nobind() throws IOException {
		ServerSocket serverSocket = new ServerSocket();
//		获取套接字正在监听的端口号，没有绑定就返回-1
		int localPort = serverSocket.getLocalPort();//-1
//		获取套接字关联的管道
		ServerSocketChannel channel = serverSocket.getChannel();//null
//		获取套接字的本地地址
		InetAddress inetAddress = serverSocket.getInetAddress();//null
//		获取套节点的绑定端口地址
		SocketAddress localSocketAddress = serverSocket.getLocalSocketAddress();//null
//		测试 SO_REUSEADDR 是否启用
		boolean reuseAddress = serverSocket.getReuseAddress();//false
//		返回ServerSocket的绑定状态。
		boolean bound = serverSocket.isBound();//false
//		返回ServerSocket的关闭状态。
		boolean closed = serverSocket.isClosed();//false
//		获取接收的缓冲区大小
		int receiveBufferSize = serverSocket.getReceiveBufferSize();//65536
//		获取超时的时间
		int soTimeout = serverSocket.getSoTimeout();//0
//		设置超时的时间
		serverSocket.setSoTimeout(5);
//		设置缓冲区的大小
		serverSocket.setReceiveBufferSize(2046);
//		设置此ServerSocket的性能首选项。int connectionTime,  int latency,   int bandwidth
		serverSocket.setPerformancePreferences(100, 50, 23);
//		启用禁用SO_REUSEADDR套接字选项
		serverSocket.setReuseAddress(true);
//		serverSocket.bind(endpoint, backlog);
//		serverSocket.bind(endpoint);
		Socket accept = serverSocket.accept();
		
		serverSocket.close();		
	}

}
