package nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		Thread t = new Thread(()->{
			try {
				DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET);
				dc.connect(new InetSocketAddress("127.0.0.1", 8895));
				DatagramSocket socket = dc.socket();
				System.out.println(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET);
		DatagramChannel bind = dc.bind(new InetSocketAddress("127.0.0.1",8895));
		t.start();
		dc.send(ByteBuffer.wrap("测试发送数据包".getBytes()), new InetSocketAddress("127.0.0.1", 8895));
		SocketAddress localAddress = dc.getLocalAddress();
		SocketAddress remoteAddress = dc.getRemoteAddress();
	}

}
