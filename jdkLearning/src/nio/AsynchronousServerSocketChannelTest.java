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
//		��һ���������׽���ͨ��
		AsynchronousServerSocketChannel open = AsynchronousServerSocketChannel.open();
		
//		�½�һ���첽ͨ���飬�̳߳�
		AsynchronousChannelGroup group = 
								AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(5));
		
//		ʹ��һ��ͨ���飬�½�һ���������׽���ͨ��
		AsynchronousServerSocketChannel open2 = AsynchronousServerSocketChannel.open(group);
//		ͨ���󶨱��ص�ַǰ�ĵ�ַ
		SocketAddress localAddress = open2.getLocalAddress();
		
//		���������׽���ͨ���󶨵����ض˿�
		open2.bind(new InetSocketAddress(500), 20);
//		ͨ���󶨺�ĵ�ַ
		SocketAddress localAddress2 = open2.getLocalAddress();
//		ͨ��״̬���Ƿ���
		boolean open3 = open2.isOpen();
//		ͨ��֧�ֵ�socket����   SO_RCVBUF   SO_REUSEADDR 
		Set<SocketOption<?>> supportedOptions = open2.supportedOptions();
//		ͨ�����ṩ��
		AsynchronousChannelProvider provider = open2.provider();
//		����Socket�������֣���ȡ�׽��ֶ�Ӧ��ֵ
		Integer option = open2.getOption(StandardSocketOptions.SO_RCVBUF);
//		����Socket�������ֶ�Ӧ��ֵ
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
