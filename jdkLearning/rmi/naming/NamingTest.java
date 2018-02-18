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
//		创建一个本地的注册表（一个Map保存remote对象和名字的映射），
//		其实RMI就是使用这个来实现的，RMI的默认端口是1099，这里可以自己指定端口
		Registry createRegistry = LocateRegistry.createRegistry(8989);//这里相当于开启一个服务器，如果引用被清除，服务器将被关闭
		
		/*
		 * RemoteMyBook实现了UnicastRemoteObject，可以直接作为remote对象注册到服务器中。
		 * MyBook没有实现UnicastRemoteObject，需要使用UnicastRemoteObject将其转换，才能注册到服务中
		 * 
		 * */
//		将一个remote对象绑定到本地RMI中，在绑定前需要启动rmi服务 start rmiregistry，相当于LocateRegistry.createRegistry，
		Naming.bind("name", new RemoteMyBook());//RMI中存在就会抛出异常
		
//		将remote转换为可注册的对象
		Remote remoteBook = UnicastRemoteObject.exportObject(new MyBook(),1099);//如果使用Registry对象绑定的其他端口，这里需要将对象暴露给对应的端口
//		注册remote对象
		Naming.bind("MyBook", remoteBook);
		
		
//		从本地RMI中将指定的对象解除绑定
		Naming.unbind("name");
//		返回保存在RMI中的匹配的名字集合
		String[] list = Naming.list("name");
//		重新绑定，如果RMI中存在就会覆盖原来的对象
		Naming.rebind("name", new RemoteMyBook());
//		根据名字获取remote对象
		Remote lookup = Naming.lookup("rmi://localhost:1098/name");
		
		
		
		
	}

}
