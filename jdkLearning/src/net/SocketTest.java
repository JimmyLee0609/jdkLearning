package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

public class SocketTest {

	public static void main(String[] args) throws IOException {
		communicate();
		ipAndPort();
	}

	private static void communicate() throws UnknownHostException, IOException {
		Socket socket = new Socket("www.baidu.com", 80);
		boolean connected = socket.isConnected();
		StringBuilder out = new StringBuilder();
		out.append("GET www.baidu.com HTTP:/1.1/r/n");
		out.append("Accept: */*/r/n");
		out.append(
				"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 Edge/15.15063/r/n");
		out.append("Accept-Language: zh-cn/r/n");
		out.append("Accept-Encoding: gzip,deflate/r/n");
		out.append("/r/n");
		out.append("/r/n");
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(out.toString().getBytes());
		socket.shutdownOutput();
		InputStream inputStream = socket.getInputStream();
		byte[] b = new byte[1024];
		int c = 0;
		while ((c = inputStream.read(b)) != -1) {
			System.out.println(Arrays.toString(b));
		}
		System.out.println("处理完了");
		socket.close();
	}

	@SuppressWarnings("unused")
	private static void ipAndPort() throws IOException {
		Thread thread = new Thread(() -> {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(8080, 2);
				Thread.sleep(6000);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (serverSocket != null) {
					try {
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		Thread client = new Thread(() -> {
			Socket cc = null;
			try {
				InetAddress remote = Inet4Address.getByName("127.0.0.1");
				InetAddress local = Inet4Address.getByName("127.0.0.1");
				cc = new Socket(remote, 8080, local, 6565);
				System.out.println(cc + "连接成功");
			} catch (IOException e) {
				System.out.println(cc + "连接失败");
			} finally {
				if (cc != null) {
					try {
						cc.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		thread.start();
		client.start();

	}

}
