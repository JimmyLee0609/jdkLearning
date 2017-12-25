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
//		��һ���������ܵ�
		ServerSocketChannel ssc = ServerSocketChannel.open();
//		��ͨ������һ����ַ                                          �޶�ÿ��accept����connect��IP����
		ssc.bind(new InetSocketAddress("localhost", 1959), 1);
		
		
		
		
		
//		�ܵ���������������accept����ʱ���������
		Object blockingLock = ssc.blockingLock();
//		��ȡ�󶨵ı�����ַ
		SocketAddress localAddress = ssc.getLocalAddress();
//		�ж�ͨ��״̬      �Ƿ�����ģʽ
		boolean blocking = ssc.isBlocking();
//		�Ƿ��
		boolean open = ssc.isOpen();
//		�Ƿ�ע��ѡ����
		boolean registered = ssc.isRegistered();
//		ͨ�����ṩ��
		SelectorProvider provider = ssc.provider();
//		ͨ��֧�ֵ�Socket����
		Set<SocketOption<?>> supportedOptions = ssc.supportedOptions();//[IP_TOS, SO_RCVBUF, SO_REUSEADDR]
//		����Socket���Ե����ֻ�ȡֵ
		Integer option = ssc.getOption(StandardSocketOptions.SO_RCVBUF);//65536
//		����Socket��������������ֵ
		ssc.setOption(StandardSocketOptions.SO_RCVBUF, option);
//		==========ѡ����========
		Selector sel = Selector.open();//��һ��ѡ����
//		ssc.configureBlocking(false);//��ͨ������Ϊ������ģʽ
		int validOps = ssc.validOps();//ͨ��֧�ֵ�ѡ�����
//		��ͨ��ע�ᵽѡ����              ��֧��accept     ������渽��һ���渽������������������Ķ���
//		ssc.register(sel, SelectionKey.OP_ACCEPT, "attach");
//		ͨ����ѡ�����еļ�
//		SelectionKey keyFor = ssc.keyFor(sel);
		
//		�ȴ�����һ���׽���ͨ��
		SocketChannel accept = ssc.accept();
//		ʹ��ͨ��д������
		accept.write(ByteBuffer.wrap("woshishui".getBytes()));
		
//		������ӣ�ֻ���ڷ�������ʹ�ã���ΪserverSocketÿ��accept���������������޵ġ�
//		�ͻ���ÿ���һ�����񣬾Ϳ����ڷ�������finishһ��connect��ʹ������IP����connect
		accept.finishConnect();
		
//		�����׽���
		SocketChannel accept2 = ssc.accept();
		ServerSocket socket = ssc.socket();
		ssc.close();

	}

}
