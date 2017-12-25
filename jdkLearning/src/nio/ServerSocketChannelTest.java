package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

public class ServerSocketChannelTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
//		打开一个服务器管道
		ServerSocketChannel ssc = ServerSocketChannel.open();
//		将通道绑定在一个地址                                          限定每个accept可以connect的IP数量
		ssc.bind(new InetSocketAddress("localhost", 1959), 1);
		
		
		
		
		
//		管道的阻塞锁，就是accept阻塞时候的锁对象
		Object blockingLock = ssc.blockingLock();
//		获取绑定的本机地址
		SocketAddress localAddress = ssc.getLocalAddress();
//		判断通道状态      是否阻塞模式
		boolean blocking = ssc.isBlocking();
//		是否打开
		boolean open = ssc.isOpen();
//		是否注到选择器
		boolean registered = ssc.isRegistered();
//		通道的提供者
		SelectorProvider provider = ssc.provider();
//		通道支持的Socket属性
		Set<SocketOption<?>> supportedOptions = ssc.supportedOptions();//[IP_TOS, SO_RCVBUF, SO_REUSEADDR]
//		根据Socket属性的名字获取值
		Integer option = ssc.getOption(StandardSocketOptions.SO_RCVBUF);//65536
//		根据Socket的属性名字设置值
		ssc.setOption(StandardSocketOptions.SO_RCVBUF, option);
//		==========选择器========
		Selector sel = Selector.open();//打开一个选择器
//		ssc.configureBlocking(false);//将通道设置为非阻塞模式
		int validOps = ssc.validOps();//通道支持的选择操作
//		将通道注册到选择器              仅支持accept     这个是随附的一般随附缓冲区，可以是任意的对象
//		ssc.register(sel, SelectionKey.OP_ACCEPT, "attach");
//		通道在选择器中的键
//		SelectionKey keyFor = ssc.keyFor(sel);
		
//		等待接收一个套接字通道
		SocketChannel accept = ssc.accept();
//		使用通道写出数据
		accept.write(ByteBuffer.wrap("woshishui".getBytes()));
		
//		完成连接，只有在服务器端使用，因为serverSocket每个accept的连接数量是有限的。
//		客户端每完成一个事务，就可以在服务器端finish一个connect，使得其他IP可以connect
		accept.finishConnect();
		
//		接收套接字
		SocketChannel accept2 = ssc.accept();
		ServerSocket socket = ssc.socket();
		ssc.close();

	}

}
