package net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

public class DatagramSocketServerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		 normalReceive();
//		testBroadcast();
		
	}

	private static void testBroadcast() {
		Thread thread = new Thread(() -> {
			try {
				DatagramPacket p = new DatagramPacket(new byte[50], 50);
				DatagramSocket datagramSocket = new DatagramSocket(6060,InetAddress.getByName("192.16.3.103"));
				
				System.out.println("���ض˿ڣ� "+datagramSocket.getLocalPort()+"  ����IP ��"+datagramSocket.getLocalAddress().toString());
				datagramSocket.setBroadcast(true);
				datagramSocket.receive(p);
				
				System.out.println("��ȡ����Ϣ    "+new String(p.getData(),"gbk"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread.start();
	}

	@SuppressWarnings("unused")
	private static void normalReceive() throws IOException {
		DatagramSocket server = new DatagramSocket(5959);
		// �˿ڰ��˾Ͳ��ܸı䣬���׳��쳣
		// server.bind(new InetSocketAddress(5959));
		// ���ӵ�Զ��IP�˿ڣ����Ӻ��ֻ�ܸ����IP�������ݣ������׳��쳣
		server.connect(new InetSocketAddress("192.16.3.103", 5757));

		// �㲥��״̬��Ĭ�Ͽ��������Խ��գ����͹㲥
		boolean broadcast = server.getBroadcast();
		// ��ȡ�ܵ���ֻ��channel���򿪵�ͨ���Ż���
		DatagramChannel channel = server.getChannel();// null


		// ��ȡ���ӳ�ʱ��0������������
		int soTimeout = server.getSoTimeout();// 0
		// ��ȡIP������״̬����
		int trafficClass = server.getTrafficClass();// 0


		byte[] buf = new byte[1024];
		// �½��������ݵİ���ÿ�λ����������Ĵ�С���ضϽ��յ������ݡ�
		DatagramPacket datagramPacket = new DatagramPacket(buf, 1024);
		server.setBroadcast(true);

		// ���հ�������timeout�������ȴ�ʱ�䡣��ʱ�׳��쳣
		server.receive(datagramPacket);
		String string2 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string3 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string4 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string5 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string6 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string7 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string8 = new String(datagramPacket.getData(),"gbk");
		server.receive(datagramPacket);
		String string9 = new String(datagramPacket.getData(),"gbk");
				
		
		

		address(server);
		buffer(server);
		// ��ȡ���е�����
		byte[] data = datagramPacket.getData();// ���ǽ��յ�������д������Ļ����С�
		// �����յ��İ��Ĳ�������ת��Ϊ�ַ�����
		String string = new String(data, 0, 23, "gbk");// 11111111���Է��͵�����?
		// ���յ����ֽڵĳ���
		int length = datagramPacket.getLength();// 23 ���¼�յ������ݵĳ���
		// ����ƫ�������뷢�Ͱ���ƫ�����޹أ��������Լ��İ�
		int offset = datagramPacket.getOffset();// 0
		// ��ȡ���Ͷ˵Ķ˿ں�
		int port2 = datagramPacket.getPort();// 5757
		// ��ȡ���Ͷ˵�IP
		InetAddress address = datagramPacket.getAddress();/// 192.168.1.50

		server.close();
	}

	private static void buffer(DatagramSocket server) throws SocketException {
		// ��ȡ���ջ�������С
		int receiveBufferSize = server.getReceiveBufferSize();// 65536

		// ���ӹر�ʱ�Ƿ�ܾ�
		boolean reuseAddress = server.getReuseAddress();// false
		// ��ȡ���ͻ����С
		int sendBufferSize = server.getSendBufferSize();// 65536
		// ���û�������С��ֻӰ���ٶȰɡ�
		server.setReceiveBufferSize(500);
	}

	private static void address(DatagramSocket server) {
		// ��ȡ���ض˿�
		int localPort = server.getLocalPort();// 959
		// ��ȡԶ�˵�ַ
		InetAddress inetAddress = server.getInetAddress();
		// ��ȡ���ص�ַ
		InetAddress localAddress = server.getLocalAddress();// 0.0.0.0/0.0.0.0
		// ��ȡ���ص�socket��ַ
		SocketAddress localSocketAddress = server.getLocalSocketAddress();// 0.0.0.0/0.0.0.0:5959
		// ��ȡ���ӵĶ˿�
		int port = server.getPort();// 5757
		// ��ȡԶ�˵�socket
		SocketAddress remoteSocketAddress = server.getRemoteSocketAddress();// 0.0.0.0/0.0.0.0:5757
	}

}
