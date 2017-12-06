package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelTest {

	public static void main(String[] args) throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.bind(new InetSocketAddress("localhost", 1959),1);
		SocketChannel accept = ssc.accept();
		accept.write(ByteBuffer.wrap("woshishui".getBytes()));
		accept.finishConnect();
		SocketChannel accept2 = ssc.accept();
		ssc.close();
		
	}

}
