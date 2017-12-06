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
//		�鲥���Ͷ�
		MulticastSocket multicastSocket;  
		        try {  
//		        	�½��鲥����
		            multicastSocket = new MulticastSocket(10000);  
//		            �½���ַ
		            InetAddress address = InetAddress.getByName("224.0.0.199"); // ����ʹ��D���ַ  
		         // ��D���ַΪ��ʶ������ͬһ�������ʵ�ֹ㲥  
		            multicastSocket.joinGroup(address); 
		              
		            while (true) {  
//		            	�½�����
		                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());  
		                byte[] buf = time.getBytes();  
//		              �½�һ�����ݰ�
		                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);  
//		               // ���յ�ַ��group�ı�ʶ��ͬ  
		                datagramPacket.setAddress(address); 
		             // �������鲥�Ķ˿ں� ��ֻ�м�������鲥������˿ںŲŻ��յ����� 
		                datagramPacket.setPort(10000); 
		                
		                boolean loopbackMode = multicastSocket.getLoopbackMode();
//		                multicastSocket.setLoopbackMode(true);//Ŀǰֻ������ʵ��
		                
//		                ����·��ת��������
		                multicastSocket.setTimeToLive(2);
//		                  ��������
		                multicastSocket.send(datagramPacket);  
//		                �½�һ�����ݰ����ڽ�������
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


