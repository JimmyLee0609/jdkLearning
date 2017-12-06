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
//    打开一个选择器		
		Selector se = Selector.open();
//		将通道注册到选择器
		regeist(se);
		while (true) {

			if (se.select() == 0) {
//				当选择到0个通道就打印一个。
				System.out.print(".");
				continue;
			}
//			遍历选择器获取到的准备好的通道
			Iterator<SelectionKey> iterator = se.selectedKeys().iterator();
			while (iterator.hasNext()) {
//				获取键
				SelectionKey next = iterator.next();
//				如果键是可接收类型就是   ServersocketChannle
				if (next.isAcceptable()) {
//					获取键对应的通道并强转
					ServerSocketChannel channel = (ServerSocketChannel)next.channel();
//					获取连接套接字通道
					SocketChannel accept = channel.accept();
//					将获取到的通道设置为非阻塞
					accept.configureBlocking(false);
//					将通道注册到选择器
					accept.register(se, SelectionKey.OP_READ);
					
					ByteBuffer buffer = ByteBuffer.allocate(500);
					accept.read(buffer);
					buffer.flip();
//					打印通道中的缓存数据
					System.out.println(new String(buffer.array(),"gbk"));
//					使用通道写出数据
					accept.write(ByteBuffer.wrap("收到了信息，现在恢复".getBytes()));
				}
				if (next.isReadable()) {
					readDataFromChannle(next);
				}
				if (next.isWritable()) {

				}
//				每次处理完的键都要移除，不然下一次还会获取到就重复了
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

		// 返回该选择器选择的键集
		Set<SelectionKey> selectedKeys = se.selectedKeys();

		// 立即返回一组响应通道应景准备好进行I/O操作的key.
		// 这个方法是非阻塞的.返回值是准备好的数量,没有准备好的就返回0
		// 该方法将清除之前调用的wakeup方法的效果
		int selectNow = se.selectNow();
		// 在指定的时间结束时返回一组相应通道已经准备好进行I/O操作的key
		// 该方法返回的方式 1.至少一个通道被选择 2.selector的wakeup方法被调用 3.当前线程被中断 4.超时
		// 那种情况先出现,就会先按照那种情况返回
		int select2 = se.select(50);

		// 返回注册到该选择器的键集. 不能直接修改键,只能取消注册
		Set<SelectionKey> keys = se.keys();

		// 返回创建这个选择器的提供者
		SelectorProvider provider = se.provider();// sun.nio.ch.WindowsSelectorProvider

		// 线程的key中select阻塞等待的方式立即返回。
		Selector wakeup = se.wakeup();

		// 选择器是否开启状态
		boolean open = se.isOpen();

		// 关闭这个选择器，如果注册的key还在阻塞等待，会取消这个key的关联，并释放资源
		// 如果这个选择器已经close。再次close将不起作用。
		se.close();
		boolean close = se.isOpen();
	}

}
