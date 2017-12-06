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
//		获取包的字节数据
		byte[] data = datagramPacket.getData();
//		获取包的长度
		int length = datagramPacket.getLength();//100
//		获取包的偏移量
		int offset = datagramPacket.getOffset();//0
//		获取包的目的地址
		InetAddress address = datagramPacket.getAddress();//DESKTOP-0MCNN4Q/192.168.1.50
//		获取包的目的端口
		int port = datagramPacket.getPort();//8080
//		获取包的目的socket地址
		SocketAddress socketAddress = datagramPacket.getSocketAddress();//DESKTOP-0MCNN4Q/192.168.1.50:8080
//		设置包的目的socket地址
		datagramPacket.setSocketAddress(socketAddress);
//		设置包的目的ip地址
		datagramPacket.setAddress(address);
//		设置包的目的端口
		datagramPacket.setPort(port);
//		设置包的长度
		datagramPacket.setLength(length);
//		设置包的数据内容
		datagramPacket.setData(data, 2, data.length-2);
//		offset只根自己的包相关
		int offset2 = datagramPacket.getOffset();//2
	}

	private static DatagramPacket construct() throws UnknownHostException {
		byte[]buf=new byte[1024];
		byte[] bytes = "测试一下包".getBytes();
		System.arraycopy(bytes, 0, buf, 0, bytes.length);
//		构造方法
//		使用字节缓冲区，    InetAddress  指定端口号
		DatagramPacket packet = new DatagramPacket(buf,0,100,InetAddress.getLocalHost(),8080);
//		使用字节缓冲区。   SocketAddress  (就是InetAddress 和端口号)
		DatagramPacket datagramPacket = new DatagramPacket(buf,0,100,new InetSocketAddress(InetAddress.getLocalHost(),8080) );		
		return packet;
	}

}
