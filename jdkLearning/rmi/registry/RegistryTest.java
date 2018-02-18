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
		// ����RMI������ע���
		Registry createRegistry = LocateRegistry.createRegistry(9595);
		
		RMIClientSocketFactory clientSocketFactory = new RMIClientSocketFactory() {
			@Override
			public Socket createSocket(String host, int port) throws IOException {
//				������򵥵�ģ��ʵ�֣�ʵ������Ҫ�Դ����host,port���м�飬�������ʹ�����ͬ��socket�����Ҷ�soket�������ƴ���
				return new Socket(Inet4Address.getByName(host),port);
			}
		};
		RMIServerSocketFactory ssf = new RMIServerSocketFactory() {
			@Override
			public ServerSocket createServerSocket(int port) throws IOException {
//				����һ����򵥵�ģ��
				return new ServerSocket(port);
			}
		};
//		ָ���˿ڣ��ͻ��ˣ����������׽��ֹ���    ����RMI������
		Registry server = LocateRegistry.createRegistry(5959, clientSocketFactory, ssf);
		
		/*
		 * RemoteMyBook�̳���UnicastRemoteObject�������ֱ��ע�ᵽRegistry��
		 * MyBookû�м̳�UnicastRemoteObject����Ҫ����ת��
		 * */
		
//		��������һ��remote���󣬿ͻ���ֻҪ��Book�ӿھͿ������ӵ����������ҵ�name�����ִӶ�ʹ��MyBook�ķ���
		server.bind("name", new RemoteMyBook());//�������˴���name�Ļ����׳��쳣
		
		Remote myBook = UnicastRemoteObject.exportObject(new MyBook(), 5959);
		server.bind("myBook", myBook);
		
		server.rebind("name", new RemoteMyBook());//����������name�͸���name����Ӧ�Ķ���
//		�г����а󶨵�����
		String[] list = server.list();
//		����ָ�����ֵ�remote����
		Remote lookup = server.lookup("name");
//		�����ָ�����ֵĶ���
		server.unbind("name");
		
		
		
		
		
		
		// ��ȡRMI������ע���,���ǿͻ�������ָ��������RMI����
		Registry r = LocateRegistry.getRegistry();// Ĭ�ϻ�ȡlocalhost:1099
		Registry registry = LocateRegistry.getRegistry(5959);// localhost:5959
		Registry registry2 = LocateRegistry.getRegistry("localhost", 5987);

		// �Զ���һ���׽��ֹ���
		RMIClientSocketFactory csf = new RMIClientSocketFactory() {
			@Override
			public Socket createSocket(String host, int port) throws IOException {
				return null;
			}
		};
		// ָ���׽��ֹ��������ӵ�ָ��������RMI����
		LocateRegistry.getRegistry("localhost", 5959, csf);

	}

}
