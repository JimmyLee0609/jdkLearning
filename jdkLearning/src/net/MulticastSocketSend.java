package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MulticastSocketSend {

	public static void main(String[] args) throws IOException {
//		组播发送端
		MulticastSocket multicastSocket;  
		        try {  
//		        	新建组播对象
		            multicastSocket = new MulticastSocket(10000);  
//		            新建地址
		            InetAddress address = InetAddress.getByName("224.0.0.199"); // 必须使用D类地址  
		         // 以D类地址为标识，加入同一个组才能实现广播  
		            multicastSocket.joinGroup(address); 
		              
		            while (true) {  
//		            	新建日期
		                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());  
		                byte[] buf = time.getBytes();  
//		              新建一个数据包
		                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);  
//		               // 接收地址和group的标识相同  
		                datagramPacket.setAddress(address); 
		             // 发送至组播的端口号 ，只有监听这个组播的这个端口号才会收到数据 
		                datagramPacket.setPort(10000); 
		                
		                boolean loopbackMode = multicastSocket.getLoopbackMode();
//		                multicastSocket.setLoopbackMode(true);//目前只能这样实现
		                
//		                设置路由转发最大次数
		                multicastSocket.setTimeToLive(2);
//		                  发送数据
		                multicastSocket.send(datagramPacket);  
//		                新建一个数据包用于接收数据
		                DatagramPacket packet = new DatagramPacket(new byte[buf.length], buf.length);
		               
		                multicastSocket.receive(packet);
		             
		                System.out.println(new String(packet.getData(),"gbk"));
		                Thread.sleep(1000);  
		            }  
		        } catch (IOException e) {  
		            // TODO Auto-generated catch block  
		            e.printStackTrace();  
		        } catch (InterruptedException e) {  
		            // TODO Auto-generated catch block  
		            e.printStackTrace();  
		        }  
		          
		    }  
	}


