package tjava.managerment.srever;

import java.util.ArrayList;

import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerBuilder;
import javax.management.MBeanServerDelegate;
import javax.management.MBeanServerFactory;
import javax.management.loading.ClassLoaderRepository;

public class CreateMBeanServer {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
factory();
builder();
	}

	@SuppressWarnings("unused")
	private static void builder() {

MBeanServerBuilder builder = new MBeanServerBuilder();
MBeanServerDelegate delegate = builder.newMBeanServerDelegate();
delegate(delegate);

MBeanServer newMBeanServer = builder.newMBeanServer("defaultDomain", MBeanServerFactory.newMBeanServer(), delegate);
				
	}

	@SuppressWarnings("unused")
	private static void delegate(MBeanServerDelegate delegate) {
//		从管理的角度来表示MBean服务器。 当MBean在MBean服务器中注册/取消注册时，MBeanServerDelegate MBean将发出MBeanServerNotifications。
		
		
//		返回JMX实现名称（此产品的名称）。
		String implementationName = delegate.getImplementationName();//JMX
//		返回JMX实现供应商（本产品的供应商）。
		String implementationVendor = delegate.getImplementationVendor();//Oracle Corporation
//		返回JMX实现版本（此产品的版本）。
		String implementationVersion = delegate.getImplementationVersion();//1.8.0_162-b12
//		返回MBean服务器代理标识。
		String mBeanServerId = delegate.getMBeanServerId();//DESKTOP-7SAG66J_1517733419365
		
//		返回由此产品实现的JMX规范的全名。
		String specificationName = delegate.getSpecificationName();//Java Management Extensions
//		返回由此产品实现的JMX规范的供应商。
		String specificationVendor = delegate.getSpecificationVendor();//Oracle Corporation
//		返回此产品实现的JMX规范的版本
		String specificationVersion = delegate.getSpecificationVersion();//1.4
		
//		获取注册到这个bean的所有监听器
		MBeanNotificationInfo[] notificationInfo = delegate.getNotificationInfo();//javax.management.MBeanNotificationInfo
//		添加一个监听次bean的监听器
//		delegate.addNotificationListener(listener, filter, handback);
//		使MBean服务器能够发送通知。 如果通过的通知的序列号小于或等于0，则将其替换为代表自己的序列号。
//		delegate.sendNotification(notification);
//		移除监听器
//		delegate.removeNotificationListener(listener, filter, handback);
	}

	private static void factory() {
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
