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
//		构造函数已经绑定了端口就不能再改
//		client.bind(new InetSocketAddress(7070));
		
//		创建一个地址，是远程的地址
		InetSocketAddress connect = new InetSocketAddress("localhost",8080);
		client.setBroadcast(true);
		client.connect(connect);
		InetAddress byName = InetAddress.getLocalHost();
		
		
		byte[]buf=new byte[100];
		byte[] tex = "第一次发送数据包".getBytes();
		System.arraycopy(tex, 0, buf, 0, tex.length);
		DatagramPacket packet = new DatagramPacket(buf,0,buf.length,connect);
		client.send(packet);
		
		byte[] tex2 = "第二次发送数据包".getBytes();
		System.arraycopy(tex2, 0, buf, 0, tex2.length);
		packet.setData(buf);
		client.send(packet);
		
		byte[] tex3 = "再一次发送数据包".getBytes();
		System.arraycopy(tex3, 0, buf, 0, tex3.length);
		packet.setData(buf);
		client.send(packet);
		
		byte[] tex4 = "还发送数据包".getBytes();
		System.arraycopy(tex4, 0, buf, 0, tex4.length);
		packet.setData(buf);
		client.send(packet);
		
		
		byte[] tex5 = "接着送数据包".getBytes();
		System.arraycopy(tex5, 0, buf, 0, tex5.length);
		packet.setData(buf);
		client.send(packet);
		byte[] tex6 = "第6次接着送数据包".getBytes();
		System.arraycopy(tex6, 0, buf, 0, tex6.length);
		packet.setData(buf);
		client.send(packet);
		byte[] tex7 = "第7次接着送数据包".getBytes();
		System.arraycopy(tex7, 0, buf, 0, tex7.length);
		packet.setData(buf);
		client.send(packet);
		byte[] tex8 = "凑够爆缓存接着送数据包".getBytes();
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
