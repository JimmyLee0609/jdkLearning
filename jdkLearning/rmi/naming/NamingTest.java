package naming;

import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import remoteObj.MyBook;
import remoteObj.RemoteMyBook;

public class NamingTest {

	public static void main(String[] args) throws RemoteException, Exception, AlreadyBoundException {
//		����һ�����ص�ע���һ��Map����remote��������ֵ�ӳ�䣩��
//		��ʵRMI����ʹ�������ʵ�ֵģ�RMI��Ĭ�϶˿���1099����������Լ�ָ���˿�
		Registry createRegistry = LocateRegistry.createRegistry(8989);//�����൱�ڿ���һ����������������ñ�����������������ر�
		
		/*
		 * RemoteMyBookʵ����UnicastRemoteObject������ֱ����Ϊremote����ע�ᵽ�������С�
		 * MyBookû��ʵ��UnicastRemoteObject����Ҫʹ��UnicastRemoteObject����ת��������ע�ᵽ������
		 * 
		 * */
//		��һ��remote����󶨵�����RMI�У��ڰ�ǰ��Ҫ����rmi���� start rmiregistry���൱��LocateRegistry.createRegistry��
		Naming.bind("name", new RemoteMyBook());//RMI�д��ھͻ��׳��쳣
		
//		��remoteת��Ϊ��ע��Ķ���
		Remote remoteBook = UnicastRemoteObject.exportObject(new MyBook(),1099);//���ʹ��Registry����󶨵������˿ڣ�������Ҫ������¶����Ӧ�Ķ˿�
//		ע��remote����
		Naming.bind("MyBook", remoteBook);
		
		
//		�ӱ���RMI�н�ָ���Ķ�������
		Naming.unbind("name");
//		���ر�����RMI�е�ƥ������ּ���
		String[] list = Naming.list("name");
//		���°󶨣����RMI�д��ھͻḲ��ԭ���Ķ���
		Naming.rebind("name", new RemoteMyBook());
//		�������ֻ�ȡremote����
		Remote lookup = Naming.lookup("rmi://localhost:1098/name");
		
		
		
		
	}

}
