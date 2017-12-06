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
//		�ղ������ݡ����õȴ�������ϵͳ���˶˿�
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

//		��һ��UDP�ܵ�
		DatagramChannel open = DatagramChannel.open(StandardProtocolFamily.INET);
//		��ȡ�󶨵ı�����ַ
		SocketAddress localAddress = open.getLocalAddress();//null
//		��ȡ���ӵ�Զ�̵�ַ
		SocketAddress remoteAddress = open.getRemoteAddress();//null
		SocketOption<Integer> ipTos = StandardSocketOptions.IP_TOS;
		
//		��ȡsocketָ�����Ե�ֵ
		Integer option = open.getOption(ipTos);//0
		Set<SocketOption<?>> supportedOptions = open.supportedOptions();
//		[SO_REUSEADDR, IP_MULTICAST_IF, IP_MULTICAST_TTL, SO_BROADCAST, SO_SNDBUF, IP_TOS, IP_MULTICAST_LOOP, SO_RCVBUF]
		for(SocketOption so:supportedOptions){
			Object option2 = open.getOption(so);
			System.out.println(so+"......."+option2);
		}
		
		
		DatagramSocket socket = open.socket();//����Channle�а�װ��DatagramSocket
		
		
		Object blockingLock = open.blockingLock();
//		�Ƿ�����
		boolean blocking = open.isBlocking();//true
//		�Ƿ�����
		boolean connected = open.isConnected();//false
//		�Ƿ��
		boolean open2 = open.isOpen();//true
//		�Ƿ�ע�ᵽѡ����
		boolean registered = open.isRegistered();//false
//		�趨�Ƿ�����
		SelectableChannel configureBlocking = open.configureBlocking(false);
//		�ܵ����ṩ��
		SelectorProvider provider = open.provider();//WindowsSelectorProvider
		
//		���عܵ�֧�ֵĲ���
		int validOps = open.validOps();//5
//		(SelectionKey.OP_READ | SelectionKey.OP_WRITE
		
		
//		�����鲥     <224.239.25.25,lo>IP��Ӳ������
		MembershipKey join = open.join(InetAddress.getByName("224.239.25.25"), 
				NetworkInterface.getByInetAddress(InetAddress.getByName("127.0.0.1")));
//		ΪChannle�󶨱��ض˿�
		DatagramChannel bind = open.bind(new InetSocketAddress("127.0.0.1", 5956));
//		ΪChannel���ӵ�Զ��IP�˿�
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
//				dc.bind(new InetSocketAddress("127.0.0.1", 8899));//��һ�ξͲ����ٴΰ�
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
				dc.send(ByteBuffer.wrap("���Է���".getBytes()), new InetSocketAddress("127.0.0.1", 8895));
//				connect֮��ֻ�ܷ������ݸ�connect�Ķ˿�
				dc.send(ByteBuffer.wrap("���Է���".getBytes()), new InetSocketAddress("127.0.0.1", 8895));
				
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
		dc.send(ByteBuffer.wrap("���Է������ݰ�".getBytes()), remote);
		SocketAddress localAddress = dc.getLocalAddress();
		SocketAddress remoteAddress = dc.getRemoteAddress();
		ByteBuffer buf = ByteBuffer.allocate(1024);	
		
		dc.connect(remote);
		int write = dc.write(ByteBuffer.wrap("���Է���write".getBytes()));
		SocketAddress receive = dc.receive(buf);
		buf.flip();
		CharBuffer decode = cs.newDecoder().decode(buf);
		System.out.println(dc.getLocalAddress()+"------"+decode);
		dc.close();
		
	}

}
