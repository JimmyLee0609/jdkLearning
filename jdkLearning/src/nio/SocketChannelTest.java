package nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class SocketChannelTest {

	public static void main(String[] args) throws IOException {

		connect();
		
	}

	private static void connect() throws IOException {

		SocketChannel open = SocketChannel.open();
		open.bind(new InetSocketAddress("localhost", 5958));
		
		boolean connect = open.connect(new InetSocketAddress("localhost", 1959));
		
		SocketChannel open2 = SocketChannel.open();
		boolean connect2 = open2.connect(new InetSocketAddress("localhost", 1959));
		open2.finishConnect();
		SocketChannel open3 = SocketChannel.open();
		boolean connect3 = open3.connect(new InetSocketAddress("localhost", 1959));
		
		System.out.println(connect);
		open.write(ByteBuffer.wrap("╡Бйтртоб".getBytes()));
		ByteBuffer buffer = ByteBuffer.allocate(500);
		open.finishConnect();
		open.read(buffer);
		buffer.flip();
		CharsetDecoder newDecoder = Charset.forName("gbk").newDecoder();
		System.out.println(newDecoder.decode(buffer).toString());
		
		open.close();
	}

}
