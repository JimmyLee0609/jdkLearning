package registry;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import remoteObj.MyBook;
import remoteObj.RemoteMyBook;

public class RegistryTest {

	public static void main(String[] args) throws Exception {
		// 创建RMI服务器注册表
		Registry createRegistry = LocateRegistry.createRegistry(9595);
		
		RMIClientSocketFactory clientSocketFactory = new RMIClientSocketFactory() {
			@Override
			public Socket createSocket(String host, int port) throws IOException {
//				这是最简单的模拟实现，实际中需要对传入的host,port进行检查，根据类型创建不同的socket，并且对soket进行限制处理
				return new Socket(Inet4Address.getByName(host),port);
			}
		};
		RMIServerSocketFactory ssf = new RMIServerSocketFactory() {
			@Override
			public ServerSocket createServerSocket(int port) throws IOException {
//				这是一个最简单的模拟
				return new ServerSocket(port);
			}
		};
//		指定端口，客户端，服务器端套接字工厂    创建RMI服务器
		Registry server = LocateRegistry.createRegistry(5959, clientSocketFactory, ssf);
		
		/*
		 * RemoteMyBook继承了UnicastRemoteObject对象可以直接注册到Registry中
		 * MyBook没有继承UnicastRemoteObject，需要将其转换
		 * */
		
//		服务器绑定一个remote对象，客户端只要有Book接口就可以连接到服务器并找到name的名字从而使用MyBook的服务
		server.bind("name", new RemoteMyBook());//服务器端存在name的话就抛出异常
		
		Remote myBook = UnicastRemoteObject.exportObject(new MyBook(), 5959);
		server.bind("myBook", myBook);
		
		server.rebind("name", new RemoteMyBook());//服务器存在name就覆盖name所对应的对象
//		列出所有绑定的名字
		String[] list = server.list();
//		检索指定名字的remote对象
		Remote lookup = server.lookup("name");
//		解除绑定指定名字的对象
		server.unbind("name");
		
		
		
		
		
		
		// 获取RMI服务器注册表,就是客户端连接指定主机的RMI服务
		Registry r = LocateRegistry.getRegistry();// 默认获取localhost:1099
		Registry registry = LocateRegistry.getRegistry(5959);// localhost:5959
		Registry registry2 = LocateRegistry.getRegistry("localhost", 5987);

		// 自定义一个套接字工厂
		RMIClientSocketFactory csf = new RMIClientSocketFactory() {
			@Override
			public Socket createSocket(String host, int port) throws IOException {
				return null;
			}
		};
		// 指定套接字工厂，连接到指定主机的RMI服务
		LocateRegistry.getRegistry("localhost", 5959, csf);

	}

}
