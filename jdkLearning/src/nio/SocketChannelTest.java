package nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Set;

public class SocketChannelTest {

	public static void main(String[] args) throws IOException {

//		connect();
		
//		打开一个连接并连接到一个远程服务器
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("Localhost",1959));
//		statue(socketChannel);
		readWrite(socketChannel);
		
	}

	@SuppressWarnings("unused")
	private static void readWrite(SocketChannel socketChannel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(50);
		int read = socketChannel.read(buffer);	
//		关闭输入流后，再次read（）将不会读取数据，但是不会抛出异常
		socketChannel.shutdownInput();
		int read2 = socketChannel.read(buffer);
		
		socketChannel.write(ByteBuffer.wrap("测试输出".getBytes()));
//		关闭输出流后，再次写出数据将抛出异常
		socketChannel.shutdownOutput();
		socketChannel.write(ByteBuffer.wrap("再次输出".getBytes()));
		
	}

	@SuppressWarnings("unused")
	private static void statue(SocketChannel socketChannel) throws IOException {
//		判断通道是否是阻塞模式
		boolean blocking = socketChannel.isBlocking();
//		设置通道为非阻塞模式，用于注册到选择器上，只有非阻塞模式才可以注册
		socketChannel.configureBlocking(false);
//		通道是否连接到服务器通道
		boolean connected = socketChannel.isConnected();
//		连接上了，没有finishConnect。这段期间就是
		boolean connectionPending = socketChannel.isConnectionPending();
//		通道是否打开
		boolean open = socketChannel.isOpen();
//		通道是否注册到选择器上
		boolean registered = socketChannel.isRegistered();
//		获取通道的本地地址
		SocketAddress localAddress = socketChannel.getLocalAddress();
//		通道的阻塞锁
		Object blockingLock = socketChannel.blockingLock();
//		通道连接的远程服务器地址
		SocketAddress remoteAddress = socketChannel.getRemoteAddress();
		
//		通道支持的选择器，操作集
		int validOps = socketChannel.validOps();//SelectionKey.OP_READ   | SelectionKey.OP_WRITE  | SelectionKey.OP_CONNECT
		
//		通道支持的套接字选项
		Set<SocketOption<?>> supportedOptions = socketChannel.supportedOptions();
//		[TCP_NODELAY,    禁用Nagle算法，会将数据包整合起来发送，增到缓冲区的利用率，需要在不同的环境，启用，禁用这个算法
//		SO_RCVBUF, SO_SNDBUF,        发送，接收的缓冲区大小 
//		SO_REUSEADDR,    重用IP地址
//		IP_TOS,                  表示数据包的IP优先等级   
//		SO_OOBINLINE, 	紧急数据的优先接收，没遇到过
//		SO_LINGER,  				在close之后，如果还有数据交换，这里设置的时间，就是最大等待时间 
//		SO_KEEPALIVE        保持连接，没隔2小时就发送一个数据给对方，看对方有无响应。
		
//		根据套接字属性的名字获取属性对应的值
		Integer option = socketChannel.getOption(StandardSocketOptions.SO_SNDBUF);
//		根据套接字指定的属性名字设置套接字属性
		socketChannel.setOption(StandardSocketOptions.SO_SNDBUF	, 1024*5);
//		套接字通道的选择器
		SelectorProvider provider = socketChannel.provider();
		
//		将通道设置为非阻塞模式
		socketChannel.configureBlocking(false);
//		打开一个选择器
		Selector selector = Selector.open();
//		将通道注册选择器中，并写上关注的事件，随附的一个对象
		SelectionKey register = socketChannel.register(selector	,SelectionKey.OP_READ, "attach");
//		通道注册到选择器时，对应的键
		SelectionKey keyFor = socketChannel.keyFor(selector);
		
//		通道包装的套接字
		Socket socket = socketChannel.socket();
				
//		通道关闭输入流
		SocketChannel shutdownInput = socketChannel.shutdownInput();
//		通道关闭输出流
		SocketChannel shutdownOutput = socketChannel.shutdownOutput();
//		通道关闭
		socketChannel.close();
		
		
		
		
	}

	private static void connect() throws IOException {
		// 测试连接到一个指定的远程服务器地址，多个连接一起去链接同一个远程服务器地址时，
		// 如果远程服务器有限定每个accept的最大连接数量，超出的连接将被拒绝
		// 直到那些连接了的socket断开连接，如finishConnect。其他连接才可以链得上
		// 打开一个套接字通道
		SocketChannel open = SocketChannel.open();
		// 将套接字通道绑定到指定的地址
		open.bind(new InetSocketAddress("localhost", 5958));
		// 将通道连接到指定的远程地址
		boolean connect = open.connect(new InetSocketAddress("localhost", 1959));
		// 打开一个套接字通道
		SocketChannel open2 = SocketChannel.open();
		// 将通道链接到一个指定的远程地址
		boolean connect2 = open2.connect(new InetSocketAddress("localhost", 1959));
		// 完成连接
		open2.finishConnect();
		// 打开一个套接字
		SocketChannel open3 = SocketChannel.open();
		// 连接到一个指定的远程地址
		boolean connect3 = open3.connect(new InetSocketAddress("localhost", 1959));

		System.out.println(connect);
		open.write(ByteBuffer.wrap("测试以下".getBytes()));
		ByteBuffer buffer = ByteBuffer.allocate(500);
		open.finishConnect();
		open.read(buffer);
		buffer.flip();
		CharsetDecoder newDecoder = Charset.forName("gbk").newDecoder();
		System.out.println(newDecoder.decode(buffer).toString());

		open.close();
		open3.close();
	}

}
