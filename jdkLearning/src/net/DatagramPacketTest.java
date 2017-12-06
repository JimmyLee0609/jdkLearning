package net;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class DatagramPacketTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws UnknownHostException {
		DatagramPacket datagramPacket = construct();
//		��ȡ�����ֽ�����
		byte[] data = datagramPacket.getData();
//		��ȡ���ĳ���
		int length = datagramPacket.getLength();//100
//		��ȡ����ƫ����
		int offset = datagramPacket.getOffset();//0
//		��ȡ����Ŀ�ĵ�ַ
		InetAddress address = datagramPacket.getAddress();//DESKTOP-0MCNN4Q/192.168.1.50
//		��ȡ����Ŀ�Ķ˿�
		int port = datagramPacket.getPort();//8080
//		��ȡ����Ŀ��socket��ַ
		SocketAddress socketAddress = datagramPacket.getSocketAddress();//DESKTOP-0MCNN4Q/192.168.1.50:8080
//		���ð���Ŀ��socket��ַ
		datagramPacket.setSocketAddress(socketAddress);
//		���ð���Ŀ��ip��ַ
		datagramPacket.setAddress(address);
//		���ð���Ŀ�Ķ˿�
		datagramPacket.setPort(port);
//		���ð��ĳ���
		datagramPacket.setLength(length);
//		���ð�����������
		datagramPacket.setData(data, 2, data.length-2);
//		offsetֻ���Լ��İ����
		int offset2 = datagramPacket.getOffset();//2
	}

	private static DatagramPacket construct() throws UnknownHostException {
		byte[]buf=new byte[1024];
		byte[] bytes = "����һ�°�".getBytes();
		System.arraycopy(bytes, 0, buf, 0, bytes.length);
//		���췽��
//		ʹ���ֽڻ�������    InetAddress  ָ���˿ں�
		DatagramPacket packet = new DatagramPacket(buf,0,100,InetAddress.getLocalHost(),8080);
//		ʹ���ֽڻ�������   SocketAddress  (����InetAddress �Ͷ˿ں�)
		DatagramPacket datagramPacket = new DatagramPacket(buf,0,100,new InetSocketAddress(InetAddress.getLocalHost(),8080) );		
		return packet;
	}

}
