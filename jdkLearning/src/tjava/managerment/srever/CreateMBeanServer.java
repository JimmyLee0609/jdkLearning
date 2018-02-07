package tjava.managerment.srever;

import java.util.ArrayList;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.loading.ClassLoaderRepository;

public class CreateMBeanServer {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		====================creat出来的server有引用留着factory中,可以relase或者find=============================
		
		/*用一个标准的默认域名返回一个实现MBeanServer接口的新对象。 
		 * 当用户指定的域为空时，默认域名被用作MBean的ObjectName中的域部分。
		标准的默认域名是DefaultDomain。
		MBeanServer引用在内部保存。 这将允许findMBeanServer返回对这个MBeanServer对象的引用。
		这个方法等同于createMBeanServer（null）。DESKTOP-7SAG66J_1517731114463*/
		MBeanServer createMBeanServer = MBeanServerFactory.createMBeanServer();//JmxMBeanServer
		
		/*使用指定的默认域名返回一个实现MBeanServer接口的新对象。 
		 * 当用户指定的域为空时，给定的域名被用作MBean的ObjectName中的域部分。
		MBeanServer引用在内部保存。 这将允许findMBeanServer返回对这个MBeanServer对象的引用。*/
		MBeanServer createMBeanServer2 = MBeanServerFactory.createMBeanServer("DefaultDomain");
		
//		返回给定MBeanServer使用的ClassLoaderRepository。 这个方法相当于server.getClassLoaderRepository（）。
		/*此接口的实例用于保持在MBean服务器中注册的类加载器的列表。他们提供了使用注册的ClassLoaders加载类的必要方法。
		ClassLoaderRepository中的第一个ClassLoader始终是MBean Server自己的ClassLoader。
		当MBean在MBean服务器中注册时，如果它是ClassLoader的子类，并且它没有实现接口PrivateClassLoader，
		则它被添加到MBean服务器的ClassLoaderRepository的末尾。
		如果随后从MBean Server注销，则会从ClassLoaderRepository中删除它。
		ClassLoaderRepository中MBean的顺序非常重要。
		对于ClassLoaderRepository中的任何两个MBean X和Y，如果在Y的注册开始之前完成X的注册，则X必须出现在Y之前。
		如果X和Y同时注册，他们的顺序是不确定的。 
		MBean的注册对应于对MBeanServer.registerMBean（java.lang.Object，javax.management.ObjectName）
		或MBeanServer.createMBean方法之一的调用*/
		ClassLoaderRepository classLoaderRepository = MBeanServerFactory.getClassLoaderRepository(createMBeanServer);
//		删除对创建的MBeanServer的内部MBeanServerFactory引用。 这允许垃圾收集器删除MBeanServer对象。
		MBeanServerFactory.releaseMBeanServer(createMBeanServer);
		
		
		/*用一个标准的默认域名返回一个实现MBeanServer接口的新对象，而不保留对这个新对象的内部引用。 
		 * 当用户指定的域为空时，默认域名被用作MBean的ObjectName中的域部分。
		标准的默认域名是DefaultDomain。
		没有参考被保留。 findMBeanServer将无法返回对此MBeanServer对象的引用，
		但垃圾收集器将能够在不再引用它时删除MBeanServer对象。
		这个方法相当于newMBeanServer（null）。*/
		MBeanServer newMBeanServer = MBeanServerFactory.newMBeanServer();
		
/*	使用指定的默认域名返回实现MBeanServer接口的新对象，而Factory不保留对该新对象的内部引用。 
 * 当用户指定的域为空时，给定的域名被用作MBean的ObjectName中的域部分。
没有参考被保留。 findMBeanServer将无法返回对此MBeanServer对象的引用，
但垃圾收集器将能够在不再引用它时删除MBeanServer对象。*/
		MBeanServer newMBeanServer2 = MBeanServerFactory.newMBeanServer("DefaultDomain");
		MBeanServer newMBeanServer3 = MBeanServerFactory.newMBeanServer("DefaultDomain");
		
//		返回已注册的MBeanServer对象的列表。 已注册的MBeanServer对象是由createMBeanServer方法之一创建的，随后不会随releaseMBeanServer一起发布。
		ArrayList<MBeanServer> findMBeanServer = MBeanServerFactory.findMBeanServer("DefaultDomain");
		
		
	}

}
