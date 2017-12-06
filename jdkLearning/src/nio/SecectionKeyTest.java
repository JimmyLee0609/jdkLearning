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
		// 打开一个选择器
		Selector selector = Selector.open();
		// 打开一个客户端通道
		SocketChannel sc = SocketChannel.open();
		sc.bind(new InetSocketAddress("localhost", 1598));
		sc.configureBlocking(false);

		// 将通道注册到选择器，获取key
		SelectionKey key = sc.register(selector, SelectionKey.OP_CONNECT);
		
		sc.connect(new InetSocketAddress("localhost", 1959));
		
		int select = selector.select();
		
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		selector.close();
		
		SelectionKey key2 = selectedKeys.iterator().next();
		// 获取键所对应的管道,需要强转到对应的channel
		SelectableChannel channel = key.channel();
		
		// key是有有效，cancel后就无效了，会被之后remove
		boolean valid = key2.isValid();
		// 获取key所关注的事件的&操作后的集
		int interestOps2 = key2.interestOps();
		// 键是否关注读
		boolean readable = key2.isReadable();
		// key是否关注写
		boolean writable = key2.isWritable();
		// key是否关注accept
		boolean acceptable = key2.isAcceptable();
		// key是否关注connect
		boolean connectable = key2.isConnectable();
		//返回此通道的有效操作
		int readyOps = key2.readyOps();
		
		// 变更key所关注的事件，selector选择时 会根据key关注的事件来选择准备好的通道
				SelectionKey interestOps = key2.interestOps(SelectionKey.OP_ACCEPT);
		
		// key注册到的selector
		Selector selector2 = key.selector();

		// 为key关联一个对象，返回原来关联的对象
		Object attach = key.attach("attach");
		// 与key关联的对象，用于区分key的。注册时没有写就是null
				Object attachment = key.attachment();
				
		// 取消注册
		key.cancel();
		
		boolean valid2 = key.isValid();
	}

}
