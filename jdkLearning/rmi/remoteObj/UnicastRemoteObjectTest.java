package remoteObj;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class UnicastRemoteObjectTest {

	@SuppressWarnings({ "unused", "static-access" })
	public static void main(String[] args) throws Exception {
		//UnicastRemoteObject本身也是一个remoteServer。
//		Remote对象，继承这个类用于暴露自己让客户端能够调用接口的方法
		
		// 获取当前线程中正处于远程方法调用的客户端主机的字符串形式
		String clientHost = UnicastRemoteObject.getClientHost();
		// 获取日志的输出流
		PrintStream log = UnicastRemoteObject.getLog();

		// 返回作为参数传递的远程对象obj的存根。 此操作仅在对象已导出后才有效。
		Remote stub = UnicastRemoteObject.toStub(new RemoteMyBook());

		// 使用指定的端口，暴露remote对象，使得RMI的客户端可以调用指定的remote接口方法
		Remote exportObject = UnicastRemoteObject.exportObject(stub, 5959);

		// 从RMI的runtime中移除指定的对象
		UnicastRemoteObject.unexportObject(stub, false);

		RMIClientSocketFactory rsf = new RMIClientSocketFactory() {
			@Override
			public Socket createSocket(String host, int port) throws IOException {
				return null;
			}
		};
		RMIServerSocketFactory ssf = new RMIServerSocketFactory() {
			@Override
			public ServerSocket createServerSocket(int port) throws IOException {
				return null;
			}
		};
		// 指定端口， 客户端，服务器端的套接字工厂暴露remote对象
		Remote exportObject2 = UnicastRemoteObject.exportObject(stub, 5959, rsf, ssf);
	}

}
