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
		
//		��һ�����Ӳ����ӵ�һ��Զ�̷�����
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("Localhost",1959));
//		statue(socketChannel);
		readWrite(socketChannel);
		
	}

	@SuppressWarnings("unused")
	private static void readWrite(SocketChannel socketChannel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(50);
		int read = socketChannel.read(buffer);	
//		�ر����������ٴ�read�����������ȡ���ݣ����ǲ����׳��쳣
		socketChannel.shutdownInput();
		int read2 = socketChannel.read(buffer);
		
		socketChannel.write(ByteBuffer.wrap("�������".getBytes()));
//		�ر���������ٴ�д�����ݽ��׳��쳣
		socketChannel.shutdownOutput();
		socketChannel.write(ByteBuffer.wrap("�ٴ����".getBytes()));
		
	}

	@SuppressWarnings("unused")
	private static void statue(SocketChannel socketChannel) throws IOException {
//		�ж�ͨ���Ƿ�������ģʽ
		boolean blocking = socketChannel.isBlocking();
//		����ͨ��Ϊ������ģʽ������ע�ᵽѡ�����ϣ�ֻ�з�����ģʽ�ſ���ע��
		socketChannel.configureBlocking(false);
//		ͨ���Ƿ����ӵ�������ͨ��
		boolean connected = socketChannel.isConnected();
//		�������ˣ�û��finishConnect������ڼ����
		boolean connectionPending = socketChannel.isConnectionPending();
//		ͨ���Ƿ��
		boolean open = socketChannel.isOpen();
//		ͨ���Ƿ�ע�ᵽѡ������
		boolean registered = socketChannel.isRegistered();
//		��ȡͨ���ı��ص�ַ
		SocketAddress localAddress = socketChannel.getLocalAddress();
//		ͨ����������
		Object blockingLock = socketChannel.blockingLock();
//		ͨ�����ӵ�Զ�̷�������ַ
		SocketAddress remoteAddress = socketChannel.getRemoteAddress();
		
//		ͨ��֧�ֵ�ѡ������������
		int validOps = socketChannel.validOps();//SelectionKey.OP_READ   | SelectionKey.OP_WRITE  | SelectionKey.OP_CONNECT
		
//		ͨ��֧�ֵ��׽���ѡ��
		Set<SocketOption<?>> supportedOptions = socketChannel.supportedOptions();
//		[TCP_NODELAY,    ����Nagle�㷨���Ὣ���ݰ������������ͣ������������������ʣ���Ҫ�ڲ�ͬ�Ļ��������ã���������㷨
//		SO_RCVBUF, SO_SNDBUF,        ���ͣ����յĻ�������С 
//		SO_REUSEADDR,    ����IP��ַ
//		IP_TOS,                  ��ʾ���ݰ���IP���ȵȼ�   
//		SO_OOBINLINE, 	�������ݵ����Ƚ��գ�û������
//		SO_LINGER,  				��close֮������������ݽ������������õ�ʱ�䣬�������ȴ�ʱ�� 
//		SO_KEEPALIVE        �������ӣ�û��2Сʱ�ͷ���һ�����ݸ��Է������Է�������Ӧ��
		
//		�����׽������Ե����ֻ�ȡ���Զ�Ӧ��ֵ
		Integer option = socketChannel.getOption(StandardSocketOptions.SO_SNDBUF);
//		�����׽���ָ�����������������׽�������
		socketChannel.setOption(StandardSocketOptions.SO_SNDBUF	, 1024*5);
//		�׽���ͨ����ѡ����
		SelectorProvider provider = socketChannel.provider();
		
//		��ͨ������Ϊ������ģʽ
		socketChannel.configureBlocking(false);
//		��һ��ѡ����
		Selector selector = Selector.open();
//		��ͨ��ע��ѡ�����У���д�Ϲ�ע���¼����渽��һ������
		SelectionKey register = socketChannel.register(selector	,SelectionKey.OP_READ, "attach");
//		ͨ��ע�ᵽѡ����ʱ����Ӧ�ļ�
		SelectionKey keyFor = socketChannel.keyFor(selector);
		
//		ͨ����װ���׽���
		Socket socket = socketChannel.socket();
				
//		ͨ���ر�������
		SocketChannel shutdownInput = socketChannel.shutdownInput();
//		ͨ���ر������
		SocketChannel shutdownOutput = socketChannel.shutdownOutput();
//		ͨ���ر�
		socketChannel.close();
		
		
		
		
	}

	private static void connect() throws IOException {
		// �������ӵ�һ��ָ����Զ�̷�������ַ���������һ��ȥ����ͬһ��Զ�̷�������ַʱ��
		// ���Զ�̷��������޶�ÿ��accept������������������������ӽ����ܾ�
		// ֱ����Щ�����˵�socket�Ͽ����ӣ���finishConnect���������Ӳſ���������
		// ��һ���׽���ͨ��
		SocketChannel open = SocketChannel.open();
		// ���׽���ͨ���󶨵�ָ���ĵ�ַ
		open.bind(new InetSocketAddress("localhost", 5958));
		// ��ͨ�����ӵ�ָ����Զ�̵�ַ
		boolean connect = open.connect(new InetSocketAddress("localhost", 1959));
		// ��һ���׽���ͨ��
		SocketChannel open2 = SocketChannel.open();
		// ��ͨ�����ӵ�һ��ָ����Զ�̵�ַ
		boolean connect2 = open2.connect(new InetSocketAddress("localhost", 1959));
		// �������
		open2.finishConnect();
		// ��һ���׽���
		SocketChannel open3 = SocketChannel.open();
		// ���ӵ�һ��ָ����Զ�̵�ַ
		boolean connect3 = open3.connect(new InetSocketAddress("localhost", 1959));

		System.out.println(connect);
		open.write(ByteBuffer.wrap("��������".getBytes()));
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
