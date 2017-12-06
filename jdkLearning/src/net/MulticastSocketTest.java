package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSocketTest {

	public static void main(String[] args) throws IOException {
		// 组播接收端
		MulticastSocket multicastSocket;
		try {
			// 新建接收组播对象，端口10000
			multicastSocket = new MulticastSocket(10000);
			InetAddress address = InetAddress.getByName("224.0.0.199");
			// 加入到组播
			multicastSocket.joinGroup(address);
			byte[] buf = new byte[1024];

			while (true) {
				// 新建数据包
				DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);

				boolean loopbackMode = multicastSocket.getLoopbackMode();//默认false
				// multicastSocket.setLoopbackMode(true);//目前只能这样实现，lunix和windows的设定是不一致的
				// 设置路由转发最大次数
				multicastSocket.setTimeToLive(2);
				multicastSocket.setTrafficClass(0x02);//0x02低成本   0x04高可靠   0x08最高吞吐量   x010最小延迟
				multicastSocket.receive(datagramPacket); // 接收数据，同样会进入阻塞状态
				Thread.sleep(100);
				
//				处理数据包
				byte[] message = new byte[datagramPacket.getLength()]; // 从buffer中截取收到的数据
				System.arraycopy(buf, 0, message, 0, datagramPacket.getLength());
				System.out.println(datagramPacket.getAddress());
				System.out.println(tooString() + new String(message));
				int leng = buf.length + tooString().length();
				
//				新建发送的数据包
				DatagramPacket sent = new DatagramPacket(new byte[leng], leng);
				sent.setAddress(address);//设置目的地址
				sent.setPort(10000);//设置目的端口
				
				multicastSocket.send(sent);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 接收数据时需要指定监听的端口号
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
