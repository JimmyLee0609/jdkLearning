package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

public class ClientSocketTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
//		serverSocketConnect();
		Socket socket = new Socket();
		InetSocketAddress bindAddress = new InetSocketAddress(5657);
//		�󶨱����ĵ�ַ,���԰�nullֵ���Ͳ�����������Ķ˿�
		socket.bind(bindAddress);
		InetSocketAddress connectAddress = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 5656);

//		���ӵ�һ��Զ�̵ĵ�ַ		
		socket.connect(connectAddress, 500);
		
		connect();
		connect();
		connect();
		connect();
		connect();
		connect();
		connect();
		
		
		
		InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream(),"gbk");
		char[] ch=new char[1024];
		inputStreamReader.read(ch);
		socket.getOutputStream().write("�ͻ���д������".getBytes());
//		�Ƿ񱣳ִ��
		boolean keepAlive = socket.getKeepAlive();//false
//		��ȡ�ܵ���ֻ��channel��򿪵��׽��ֲŻ���
		SocketChannel channel = socket.getChannel();//null
//		��ȡ���ӵ���IP
		InetAddress inetAddress = socket.getInetAddress();//192.16.3.103
//		��ȡ������IP
		InetAddress localAddress = socket.getLocalAddress();//192.16.3.103
//		��ȡ���ض˿�
		int localPort = socket.getLocalPort();//5657
//		��ȡ���ص�Socket��ַ
		SocketAddress localSocketAddress = socket.getLocalSocketAddress();///192.16.3.103:5657
//		��ȡ���ӵ��Ķ˿�
		int port = socket.getPort();//5656
		
//		 SO_OOBINLINE ��־λ�Ƿ�Ϊtrue
//		false  �������ݱ�ĬĬ������true ����ս�������
		boolean oobInline = socket.getOOBInline();//false
		
//		�Ӵ��׽��ַ��͵����ݰ���IPͷ�л�ȡ���������������
		int trafficClass = socket.getTrafficClass();//0
		
//		TCP_NODELAY ��־λ�Ƿ�Ϊtrue
//		�Ƿ�  ���ô����ӵ�Nagle�㷨�� д�����ݵ����粻�ᱻ���棬��ȷ��֮ǰд������ݡ�
		boolean tcpNoDelay = socket.getTcpNoDelay();//false
//		��ȡ���ӵ��ĵ�ַ
		SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();///192.16.3.103:5656
//		�Ƿ���˾ܾ���ַ
		boolean reuseAddress = socket.getReuseAddress();//false
		
//		SO_LINGER ��־λ�Ƿ�Ϊ
//		ָ����ʱ�رճ�ʱ�� ��ѡ�����/���ô�TCP�׽��ֵ�close������С��0��ֱ�ӹرգ�����0 �͵ȴ�ָ����ʱ���ٹرա�
		int soLinger = socket.getSoLinger();//-1
//		��ȡ���յĻ�������С
		int receiveBufferSize = socket.getReceiveBufferSize();//65536
//		��ȡ���͵Ļ�������С
		int sendBufferSize = socket.getSendBufferSize();//65536
//		��ȡ��ʱ��ʱ��
		int soTimeout = socket.getSoTimeout();//0

		
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();
		socket.close();
	}

	private static void serverSocketConnect() throws IOException {
ServerSocket serverSocket = new ServerSocket();		
InetSocketAddress inetSocketAddress = new InetSocketAddress(5656);
serverSocket.bind(inetSocketAddress, 2);
Socket accept = serverSocket.accept();


	}

	private static void connect() throws IOException {
		new Thread(()->{
		Socket socket = new Socket();	
		InetSocketAddress endpoint;
		try {
			endpoint = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 5656);
			socket.connect(endpoint);
			System.out.println(socket+"���ӳɹ�");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}).start();;
	}

}
