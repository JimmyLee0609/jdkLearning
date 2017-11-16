package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

public class NonBlockingNIO {
	@Test
	public void Client() throws IOException {
		// ��ȡͨ��
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8899));
		// �л���������ģʽ
		sChannel.configureBlocking(false);
		// ����ָ����С�Ļ�����
		ByteBuffer buf = ByteBuffer.allocate(1024);
		// �������ݸ�������
		buf.put(LocalDateTime.now().toString().getBytes());
		buf.flip();
		sChannel.write(buf);
		buf.clear();
		// �ر�ͨ��
		sChannel.close();
	}

	@Test
	public void server() throws IOException {
		// ��ȡͨ��
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		// �л���������ģʽ
		ssChannel.configureBlocking(false);
		// ������
		ssChannel.bind(new InetSocketAddress(8899));
		// ��ȡѡ����
		Selector selector = Selector.open();
		// ��ͨ��ע�ᵽѡ������ ѡ������ͨ���ļ����¼�
		ssChannel.register(selector, SelectionKey.OP_ACCEPT );
		// ��ѯʽ�ػ�ȡѡ�������Ѿ�׼���������¼�
		while (selector.select() > 0) {
			// ��ȡ��ǰѡ����������ע���ѡ������Ѿ����ļ����¼���
			 Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			// ����׼���������¼�
			for (;iterator.hasNext();) {
				SelectionKey selectionKey = iterator.next();
				// �жϾ�����ʲôʱ����׼������
				if (selectionKey.isAcceptable()) {
					SocketChannel accept = ssChannel.accept();// ���վ���
					// �л�Ϊδ����ģʽ
					accept.configureBlocking(false);
					// ����ͨ��ע�ᵽѡ������ ������״̬
					accept.register(selector, SelectionKey.OP_READ);
				} else if (selectionKey.isReadable()) {
					// ��ȡ��ǰѡ�����ϡ�������״̬��ͨ����
					SocketChannel readChannel = (SocketChannel) selectionKey.channel();

					// ��ȡ����
					ByteBuffer buf = ByteBuffer.allocate(1024);
					int len = 0;
					while ((len = readChannel.read(buf)) > 0) {
						buf.flip();
						// ��������bufת����Ϊ�ַ���
						System.out.println(new String(buf.array(), 0, len));
						buf.clear();
					}
				}
//				ȡ��ѡ���  selectionKey
				iterator.remove();
			}

		}
	}

}
