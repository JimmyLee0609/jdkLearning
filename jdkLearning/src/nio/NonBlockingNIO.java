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
		// 获取通道
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8899));
		// 切换到非阻塞模式
		sChannel.configureBlocking(false);
		// 分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		// 发送数据给服务器
		buf.put(LocalDateTime.now().toString().getBytes());
		buf.flip();
		sChannel.write(buf);
		buf.clear();
		// 关闭通道
		sChannel.close();
	}

	@Test
	public void server() throws IOException {
		// 获取通道
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		// 切换到非阻塞模式
		ssChannel.configureBlocking(false);
		// 绑定连接
		ssChannel.bind(new InetSocketAddress(8899));
		// 获取选择器
		Selector selector = Selector.open();
		// 将通道注册到选择器上 选择器对通道的监听事件
		ssChannel.register(selector, SelectionKey.OP_ACCEPT );
		// 轮询式地获取选择器上已经准备就绪的事件
		while (selector.select() > 0) {
			// 获取当前选择器中所有注册的选择键（已就绪的监听事件）
			 Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			// 迭代准备就绪的事件
			for (;iterator.hasNext();) {
				SelectionKey selectionKey = iterator.next();
				// 判断具体是什么时间内准备就绪
				if (selectionKey.isAcceptable()) {
					SocketChannel accept = ssChannel.accept();// 接收就绪
					// 切换为未阻塞模式
					accept.configureBlocking(false);
					// 将该通道注册到选择器上 监听读状态
					accept.register(selector, SelectionKey.OP_READ);
				} else if (selectionKey.isReadable()) {
					// 获取当前选择器上“读就绪状态的通道”
					SocketChannel readChannel = (SocketChannel) selectionKey.channel();

					// 读取数据
					ByteBuffer buf = ByteBuffer.allocate(1024);
					int len = 0;
					while ((len = readChannel.read(buf)) > 0) {
						buf.flip();
						// 将读到的buf转换成为字符串
						System.out.println(new String(buf.array(), 0, len));
						buf.clear();
					}
				}
//				取消选择键  selectionKey
				iterator.remove();
			}

		}
	}

}
