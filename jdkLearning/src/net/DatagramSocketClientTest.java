package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class DatagramSocketClientTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		DatagramSocket client = new DatagramSocket(5757);
//		���캯���Ѿ����˶˿ھͲ����ٸ�
//		client.bind(new InetSocketAddress(7070));
		
//		����һ����ַ����Զ�̵ĵ�ַ
		InetSocketAddress connect = new InetSocketAddress("localhost",8080);
		client.setBroadcast(true);
		client.connect(connect);
		InetAddress byName = InetAddress.getLocalHost();
		
		
		byte[]buf=new byte[100];
		byte[] tex = "��һ�η������ݰ�".getBytes();
		System.arraycopy(tex, 0, buf, 0, tex.length);
		DatagramPacket packet = new DatagramPacket(buf,0,buf.length,connect);
		client.send(packet);
		
		byte[] tex2 = "�ڶ��η������ݰ�".getBytes();
		System.arraycopy(tex2, 0, buf, 0, tex2.length);
		packet.setData(buf);
		client.send(packet);
		
		byte[] tex3 = "��һ�η������ݰ�".getBytes();
		System.arraycopy(tex3, 0, buf, 0, tex3.length);
		packet.setData(buf);
		client.send(packet);
		
		byte[] tex4 = "���������ݰ�".getBytes();
		System.arraycopy(tex4, 0, buf, 0, tex4.length);
		packet.setData(buf);
		client.send(packet);
		
		
		byte[] tex5 = "���������ݰ�".getBytes();
		System.arraycopy(tex5, 0, buf, 0, tex5.length);
		packet.setData(buf);
		client.send(packet);
		byte[] tex6 = "��6�ν��������ݰ�".getBytes();
		System.arraycopy(tex6, 0, buf, 0, tex6.length);
		packet.setData(buf);
		client.send(packet);
		byte[] tex7 = "��7�ν��������ݰ�".getBytes();
		System.arraycopy(tex7, 0, buf, 0, tex7.length);
		packet.setData(buf);
		client.send(packet);
		byte[] tex8 = "�չ���������������ݰ�".getBytes();
		System.arraycopy(tex8, 0, buf, 0, tex8.length);
		packet.setData(buf);
		client.send(packet);
		
		
		
		int port = packet.getPort();
		InetAddress address = packet.getAddress();//DESKTOP-0MCNN4Q/192.168.1.50
		for(int c=0;c<5;c++){
		client.send(packet);
		try {
			Thread.currentThread().sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
		client.close();
	}

}
