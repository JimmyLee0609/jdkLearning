package net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.Instant;

public class ServerSocketTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
//		nobind();
		ServerSocket serverSocket = new ServerSocket(5656);
//		bind(serverSocket);
		
//		��accept��ʱ���������ָ��ʱ�䶼û�н��յ����Ӿ��׳��쳣
//		serverSocket.setSoTimeout(5000);
		System.out.println(Instant.now());

		serverSocket.setReceiveBufferSize(65535);
//		��TCP���ӹرպ��һ��ʱ����ܻᱣ��һ��ʱ��ĳ�ʱ״̬������������ʱ���Ƿ�ܾ�����
		serverSocket.setReuseAddress(false);
		
//		���û��ʵ��
		serverSocket.setPerformancePreferences(5000, 2, 2);
		Socket accept=null;
		try{
//		�������ݣ����õȴ�, ��ȡ���ӵ����׽���
		 
			Socket accept1 = serverSocket.accept();
//			accept1.getOutputStream().close();
			boolean closed = serverSocket.isClosed();
			boolean closed2 = accept1.isClosed();
//			���������ȴ�
			Socket accept2 = serverSocket.accept();
			Socket accept3 = serverSocket.accept();
			Socket accept4 = serverSocket.accept();
			Socket accept5 = serverSocket.accept();
		 
		 accept.getOutputStream().write("��������ֽ�".getBytes());
		 InputStreamReader inputStreamReader = new InputStreamReader(accept.getInputStream(),"gbk");
		 char[]cbuf=new char [1024];
		 inputStreamReader.read(cbuf);
		 Thread.sleep(5000);
		}catch(SocketTimeoutException  ste){
			System.out.println(Instant.now());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getIP(accept,serverSocket);
		
		
		
		
		
		
//		��ȡ�ܵ�ֻ��Channel�İ��д򿪵Ĺܵ��Ż���
		SocketChannel channel = accept.getChannel();//null
		
		serverSocket.close();
	}

	@SuppressWarnings("unused")
	private static void getIP(Socket accept, ServerSocket serverSocket) {

//		��ȡ���ص�IP
		InetAddress localAddress = accept.getLocalAddress();
//		��ȡ���صĶ˿�
		int localPort2 = serverSocket.getLocalPort();
//		��ȡ���ӵ�IP
		InetAddress inetAddress = serverSocket.getInetAddress();
//		��ȡ���ص��׽��ֵ�ַ
		SocketAddress localSocketAddress = serverSocket.getLocalSocketAddress();
		System.out.println("������ַ"+localSocketAddress+"���ӵ��ĵ�ַ"+accept);		
	}

	private static void bind(ServerSocket serverSocket) throws IOException {

//		���԰󶨱���IP,��ָ���Ķ˿ڣ����ڼ��������ܼ��������ڲ����ڵ�IP
		InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),5656);
//		�󶨱����ĵ�ַ�����Ǽ��������������ݵ������ַ�������յ�����
		serverSocket.bind(inetSocketAddress);
		
//		�󶨵����ص�IP�������null�ͻ���ϵͳ��ȡһ����ʱ����ЧIP�Ͷ˿����󶨣�  ����һ���������������������
//		serverSocket.bind(null, 50);		
	}

	@SuppressWarnings("unused")
	private static void nobind() throws IOException {
		ServerSocket serverSocket = new ServerSocket();
//		��ȡ�׽������ڼ����Ķ˿ںţ�û�а󶨾ͷ���-1
		int localPort = serverSocket.getLocalPort();//-1
//		��ȡ�׽��ֹ����Ĺܵ�
		ServerSocketChannel channel = serverSocket.getChannel();//null
//		��ȡ�׽��ֵı��ص�ַ
		InetAddress inetAddress = serverSocket.getInetAddress();//null
//		��ȡ�׽ڵ�İ󶨶˿ڵ�ַ
		SocketAddress localSocketAddress = serverSocket.getLocalSocketAddress();//null
//		���� SO_REUSEADDR �Ƿ�����
		boolean reuseAddress = serverSocket.getReuseAddress();//false
//		����ServerSocket�İ�״̬��
		boolean bound = serverSocket.isBound();//false
//		����ServerSocket�Ĺر�״̬��
		boolean closed = serverSocket.isClosed();//false
//		��ȡ���յĻ�������С
		int receiveBufferSize = serverSocket.getReceiveBufferSize();//65536
//		��ȡ��ʱ��ʱ��
		int soTimeout = serverSocket.getSoTimeout();//0
//		���ó�ʱ��ʱ��
		serverSocket.setSoTimeout(5);
//		���û������Ĵ�С
		serverSocket.setReceiveBufferSize(2046);
//		���ô�ServerSocket��������ѡ�int connectionTime,  int latency,   int bandwidth
		serverSocket.setPerformancePreferences(100, 50, 23);
//		���ý���SO_REUSEADDR�׽���ѡ��
		serverSocket.setReuseAddress(true);
//		serverSocket.bind(endpoint, backlog);
//		serverSocket.bind(endpoint);
		Socket accept = serverSocket.accept();
		
		serverSocket.close();		
	}

}
