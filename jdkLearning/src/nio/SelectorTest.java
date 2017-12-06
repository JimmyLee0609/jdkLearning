package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// method();
		demo();
	}

	@SuppressWarnings("unused")
	private static void demo() throws IOException {
//    ��һ��ѡ����		
		Selector se = Selector.open();
//		��ͨ��ע�ᵽѡ����
		regeist(se);
		while (true) {

			if (se.select() == 0) {
//				��ѡ��0��ͨ���ʹ�ӡһ����
				System.out.print(".");
				continue;
			}
//			����ѡ������ȡ����׼���õ�ͨ��
			Iterator<SelectionKey> iterator = se.selectedKeys().iterator();
			while (iterator.hasNext()) {
//				��ȡ��
				SelectionKey next = iterator.next();
//				������ǿɽ������;���   ServersocketChannle
				if (next.isAcceptable()) {
//					��ȡ����Ӧ��ͨ����ǿת
					ServerSocketChannel channel = (ServerSocketChannel)next.channel();
//					��ȡ�����׽���ͨ��
					SocketChannel accept = channel.accept();
//					����ȡ����ͨ������Ϊ������
					accept.configureBlocking(false);
//					��ͨ��ע�ᵽѡ����
					accept.register(se, SelectionKey.OP_READ);
					
					ByteBuffer buffer = ByteBuffer.allocate(500);
					accept.read(buffer);
					buffer.flip();
//					��ӡͨ���еĻ�������
					System.out.println(new String(buffer.array(),"gbk"));
//					ʹ��ͨ��д������
					accept.write(ByteBuffer.wrap("�յ�����Ϣ�����ڻָ�".getBytes()));
				}
				if (next.isReadable()) {
					readDataFromChannle(next);
				}
				if (next.isWritable()) {

				}
//				ÿ�δ�����ļ���Ҫ�Ƴ�����Ȼ��һ�λ����ȡ�����ظ���
				iterator.remove();
			}
		}
	}
static ByteBuffer dst=ByteBuffer.allocate(500);
	private static void readDataFromChannle(SelectionKey next) throws IOException {
		SocketChannel channel =(SocketChannel) next.channel();
		while(channel.read(dst)>0){
			
			
		}
	}

	@SuppressWarnings("unused")
	private static void regeist(Selector se) throws IOException {

		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.bind(new InetSocketAddress("localhost", 5959));
		channel.configureBlocking(false);

		SelectionKey register = channel.register(se, SelectionKey.OP_ACCEPT);

	}

	private static void method() throws IOException {

		Selector se = Selector.open();
		// int select = se.select();

		// ���ظ�ѡ����ѡ��ļ���
		Set<SelectionKey> selectedKeys = se.selectedKeys();

		// ��������һ����Ӧͨ��Ӧ��׼���ý���I/O������key.
		// ��������Ƿ�������.����ֵ��׼���õ�����,û��׼���õľͷ���0
		// �÷��������֮ǰ���õ�wakeup������Ч��
		int selectNow = se.selectNow();
		// ��ָ����ʱ�����ʱ����һ����Ӧͨ���Ѿ�׼���ý���I/O������key
		// �÷������صķ�ʽ 1.����һ��ͨ����ѡ�� 2.selector��wakeup���������� 3.��ǰ�̱߳��ж� 4.��ʱ
		// ��������ȳ���,�ͻ��Ȱ��������������
		int select2 = se.select(50);

		// ����ע�ᵽ��ѡ�����ļ���. ����ֱ���޸ļ�,ֻ��ȡ��ע��
		Set<SelectionKey> keys = se.keys();

		// ���ش������ѡ�������ṩ��
		SelectorProvider provider = se.provider();// sun.nio.ch.WindowsSelectorProvider

		// �̵߳�key��select�����ȴ��ķ�ʽ�������ء�
		Selector wakeup = se.wakeup();

		// ѡ�����Ƿ���״̬
		boolean open = se.isOpen();

		// �ر����ѡ���������ע���key���������ȴ�����ȡ�����key�Ĺ��������ͷ���Դ
		// ������ѡ�����Ѿ�close���ٴ�close���������á�
		se.close();
		boolean close = se.isOpen();
	}

}
