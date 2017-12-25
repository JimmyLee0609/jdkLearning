package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsynchronousServerSocketChannelTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
//		打开一个服务器套接字通道
		AsynchronousServerSocketChannel open = AsynchronousServerSocketChannel.open();
		
//		新建一个异步通道组，线程池
		AsynchronousChannelGroup group = 
								AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(5));
		
//		使用一部通道组，新建一个服务器套接字通道
		AsynchronousServerSocketChannel open2 = AsynchronousServerSocketChannel.open(group);
//		通道绑定本地地址前的地址
		SocketAddress localAddress = open2.getLocalAddress();
		
//		将服务器套接字通道绑定到本地端口
		open2.bind(new InetSocketAddress(500), 20);
//		通道绑定后的地址
		SocketAddress localAddress2 = open2.getLocalAddress();
//		通道状态，是否开启
		boolean open3 = open2.isOpen();
//		通道支持的socket属性   SO_RCVBUF   SO_REUSEADDR 
		Set<SocketOption<?>> supportedOptions = open2.supportedOptions();
//		通道的提供者
		AsynchronousChannelProvider provider = open2.provider();
//		根据Socket属性名字，获取套接字对应的值
		Integer option = open2.getOption(StandardSocketOptions.SO_RCVBUF);
//		设置Socket属性名字对应的值
		open2.setOption(StandardSocketOptions.SO_RCVBUF, option);
		
//		Future<AsynchronousSocketChannel> accept = open2.accept();
		
//		AsynchronousSocketChannel asynchronousSocketChannel = accept.get();
		
		AsynchronousSocketChannel open4 = AsynchronousSocketChannel.open();
//		 accept(A, CompletionHandler<AsynchronousSocketChannel,? super A>) 
		open2.accept(open4, new CompletionHandler<AsynchronousSocketChannel, NetworkChannel>() {

			@Override
			public void completed(AsynchronousSocketChannel result, NetworkChannel attachment) {
				System.out.println("accept "+ result);
			}

			@Override
			public void failed(Throwable exc, NetworkChannel attachment) {
				// TODO Auto-generated method stub
				
			}
		});
		open2.accept(open2, new CompletionHandler<AsynchronousSocketChannel, NetworkChannel>() {
			@Override
			public void completed(AsynchronousSocketChannel result, NetworkChannel attachment) {
				System.out.println("completed");
			}
			@Override
			public void failed(Throwable exc, NetworkChannel attachment) {
				
			}
		});
		open2.close();
		
	}

}
