package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSocketTest {

	public static void main(String[] args) throws IOException {
		// �鲥���ն�
		MulticastSocket multicastSocket;
		try {
			// �½������鲥���󣬶˿�10000
			multicastSocket = new MulticastSocket(10000);
			InetAddress address = InetAddress.getByName("224.0.0.199");
			// ���뵽�鲥
			multicastSocket.joinGroup(address);
			byte[] buf = new byte[1024];

			while (true) {
				// �½����ݰ�
				DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);

				boolean loopbackMode = multicastSocket.getLoopbackMode();//Ĭ��false
				// multicastSocket.setLoopbackMode(true);//Ŀǰֻ������ʵ�֣�lunix��windows���趨�ǲ�һ�µ�
				// ����·��ת��������
				multicastSocket.setTimeToLive(2);
				multicastSocket.setTrafficClass(0x02);//0x02�ͳɱ�   0x04�߿ɿ�   0x08���������   x010��С�ӳ�
				multicastSocket.receive(datagramPacket); // �������ݣ�ͬ�����������״̬
				Thread.sleep(100);
				
//				�������ݰ�
				byte[] message = new byte[datagramPacket.getLength()]; // ��buffer�н�ȡ�յ�������
				System.arraycopy(buf, 0, message, 0, datagramPacket.getLength());
				System.out.println(datagramPacket.getAddress());
				System.out.println(tooString() + new String(message));
				int leng = buf.length + tooString().length();
				
//				�½����͵����ݰ�
				DatagramPacket sent = new DatagramPacket(new byte[leng], leng);
				sent.setAddress(address);//����Ŀ�ĵ�ַ
				sent.setPort(10000);//����Ŀ�Ķ˿�
				
				multicastSocket.send(sent);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ��������ʱ��Ҫָ�������Ķ˿ں�
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static Object obj = new Object();

	public static String tooString() {
		return obj.toString();

	}
}
