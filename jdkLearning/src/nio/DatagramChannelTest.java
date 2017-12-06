package nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ProtocolFamily;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketOption;
import java.net.SocketOptions;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;
import java.nio.channels.SelectableChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Set;

public class DatagramChannelTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
//	simple();
//	tryMethod();
	tryConnect();
	}

	private static void tryConnect() throws IOException {
		Charset cs = Charset.forName("gbk");
		DatagramChannel open = DatagramChannel.open();
		open.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		open.bind(new InetSocketAddress("localhost",8080));
		InetSocketAddress remote = new InetSocketAddress("localhost",5757);
		
		open.connect(remote);
		CharsetDecoder newDecoder = cs.newDecoder();
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		buffer.put("GET / HTTP/1.1\r\n".getBytes());
		
		buffer.put("Accept: */*\r\n".getBytes());
		buffer.put("\r\n".getBytes());
		buffer.put("\r\n".getBytes());
		buffer.flip();
		
		CharBuffer decode2 = newDecoder.decode(buffer);
		System.out.println(decode2);
		buffer.flip();
		open.write(buffer);
		
		ByteBuffer dst = ByteBuffer.allocate(11119);
//		收不到数据。永久等待。操作系统绑定了端口
//		open.receive(dst);
		open.read(dst);
		byte[] array = dst.array();
		
		ByteBuffer[] dsts=new ByteBuffer[]{ByteBuffer.allocateDirect(11119),ByteBuffer.allocateDirect(11119)};
//		open.read(dsts, 1, 1);
		
		
		open.receive(dst);
		open.receive(dst);
		open.receive(dst);
		open.receive(dst);
		
		CharBuffer decode = cs.newDecoder().decode(dst);
		
		System.out.println(decode.toString());
	}

	@SuppressWarnings("unused")
	private static void tryMethod() throws UnknownHostException, SocketException, IOException {

//		打开一个UDP管道
		DatagramChannel open = DatagramChannel.open(StandardProtocolFamily.INET);
//		获取绑定的本机地址
		SocketAddress localAddress = open.getLocalAddress();//null
//		获取连接的远程地址
		SocketAddress remoteAddress = open.getRemoteAddress();//null
		SocketOption<Integer> ipTos = StandardSocketOptions.IP_TOS;
		
//		获取socket指定属性的值
		Integer option = open.getOption(ipTos);//0
		Set<SocketOption<?>> supportedOptions = open.supportedOptions();
//		[SO_REUSEADDR, IP_MULTICAST_IF, IP_MULTICAST_TTL, SO_BROADCAST, SO_SNDBUF, IP_TOS, IP_MULTICAST_LOOP, SO_RCVBUF]
		for(SocketOption so:supportedOptions){
			Object option2 = open.getOption(so);
			System.out.println(so+"......."+option2);
		}
		
		
		DatagramSocket socket = open.socket();//就是Channle中包装的DatagramSocket
		
		
		Object blockingLock = open.blockingLock();
//		是否阻塞
		boolean blocking = open.isBlocking();//true
//		是否连接
		boolean connected = open.isConnected();//false
//		是否打开
		boolean open2 = open.isOpen();//true
//		是否注册到选择器
		boolean registered = open.isRegistered();//false
//		设定是否阻塞
		SelectableChannel configureBlocking = open.configureBlocking(false);
//		管道的提供者
		SelectorProvider provider = open.provider();//WindowsSelectorProvider
		
//		返回管道支持的操作
		int validOps = open.validOps();//5
//		(SelectionKey.OP_READ | SelectionKey.OP_WRITE
		
		
//		加入组播     <224.239.25.25,lo>IP和硬件名称
		MembershipKey join = open.join(InetAddress.getByName("224.239.25.25"), 
				NetworkInterface.getByInetAddress(InetAddress.getByName("127.0.0.1")));
//		为Channle绑定本地端口
		DatagramChannel bind = open.bind(new InetSocketAddress("127.0.0.1", 5956));
//		为Channel连接到远程IP端口
		DatagramChannel connect = open.connect(new InetSocketAddress("127.0.0.1", 5689));
		
		DatagramChannel disconnect = open.disconnect();
		
		
		open.close();
				
	}

	@SuppressWarnings("unused")
	private static void simple() throws IOException {
		Charset cs = Charset.forName("gbk");
		Thread t = new Thread(()->{
			try {
				DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET);
				dc.bind(new InetSocketAddress("127.0.0.1", 8896));
//				dc.bind(new InetSocketAddress("127.0.0.1", 8899));//绑定一次就不能再次绑定
//				dc.connect(new InetSocketAddress("127.0.0.1", 8894));
//				dc.disconnect();
				
				dc.connect(new InetSocketAddress("127.0.0.1", 8895));
				SocketAddress localAddress = dc.getLocalAddress();
				SocketAddress remoteAddress = dc.getRemoteAddress();
				DatagramSocket socket = dc.socket();
				socket.setSoTimeout(1);
				
				ByteBuffer dst = ByteBuffer.allocate(500);
				SocketAddress receive = dc.receive(dst);
				dst.flip();
				CharBuffer decode = cs.newDecoder().decode(dst);
				System.out.println(socket);
				System.out.println(decode.toString());
				dst.flip();
				dc.read(dst);
				dst.flip();
				CharBuffer decod2e = cs.newDecoder().decode(dst);
				System.out.println(decod2e.toString());
				dc.send(ByteBuffer.wrap("测试发送".getBytes()), new InetSocketAddress("127.0.0.1", 8895));
//				connect之后只能发送数据给connect的端口
				dc.send(ByteBuffer.wrap("测试发送".getBytes()), new InetSocketAddress("127.0.0.1", 8895));
				
				dc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET);
		DatagramChannel bind = dc.bind(new InetSocketAddress("127.0.0.1",8895));
		t.start();
		InetSocketAddress remote = new InetSocketAddress("127.0.0.1", 8896);
		dc.send(ByteBuffer.wrap("测试发送数据包".getBytes()), remote);
		SocketAddress localAddress = dc.getLocalAddress();
		SocketAddress remoteAddress = dc.getRemoteAddress();
		ByteBuffer buf = ByteBuffer.allocate(1024);	
		
		dc.connect(remote);
		int write = dc.write(ByteBuffer.wrap("测试发送write".getBytes()));
		SocketAddress receive = dc.receive(buf);
		buf.flip();
		CharBuffer decode = cs.newDecoder().decode(buf);
		System.out.println(dc.getLocalAddress()+"------"+decode);
		dc.close();
		
	}

}
