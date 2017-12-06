package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class SecectionKeyTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// ��һ��ѡ����
		Selector selector = Selector.open();
		// ��һ���ͻ���ͨ��
		SocketChannel sc = SocketChannel.open();
		sc.bind(new InetSocketAddress("localhost", 1598));
		sc.configureBlocking(false);

		// ��ͨ��ע�ᵽѡ��������ȡkey
		SelectionKey key = sc.register(selector, SelectionKey.OP_CONNECT);
		
		sc.connect(new InetSocketAddress("localhost", 1959));
		
		int select = selector.select();
		
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		selector.close();
		
		SelectionKey key2 = selectedKeys.iterator().next();
		// ��ȡ������Ӧ�Ĺܵ�,��Ҫǿת����Ӧ��channel
		SelectableChannel channel = key.channel();
		
		// key������Ч��cancel�����Ч�ˣ��ᱻ֮��remove
		boolean valid = key2.isValid();
		// ��ȡkey����ע���¼���&������ļ�
		int interestOps2 = key2.interestOps();
		// ���Ƿ��ע��
		boolean readable = key2.isReadable();
		// key�Ƿ��עд
		boolean writable = key2.isWritable();
		// key�Ƿ��עaccept
		boolean acceptable = key2.isAcceptable();
		// key�Ƿ��עconnect
		boolean connectable = key2.isConnectable();
		//���ش�ͨ������Ч����
		int readyOps = key2.readyOps();
		
		// ���key����ע���¼���selectorѡ��ʱ �����key��ע���¼���ѡ��׼���õ�ͨ��
				SelectionKey interestOps = key2.interestOps(SelectionKey.OP_ACCEPT);
		
		// keyע�ᵽ��selector
		Selector selector2 = key.selector();

		// Ϊkey����һ�����󣬷���ԭ�������Ķ���
		Object attach = key.attach("attach");
		// ��key�����Ķ�����������key�ġ�ע��ʱû��д����null
				Object attachment = key.attachment();
				
		// ȡ��ע��
		key.cancel();
		
		boolean valid2 = key.isValid();
	}

}
