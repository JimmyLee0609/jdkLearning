package loader;

import java.util.Iterator;
import java.util.ServiceLoader;

import util.Domain;

public class ServiceLoaderTest {

	public static void main(String[] args) {
		// 使用当前线程上下文类加载器为给定的服务类型创建一个新的服务加载器
		ServiceLoader<ServiceInterface> load2 = ServiceLoader.load(loader.ServiceInterface.class);
		//可以根据配置文件加载实现类，并迭代实现类进行操作。
//		配置文件的路径在 指定ClassLoader下的  META-INF/service/需要加载的全类名的文件不需要额外后缀
//		文件的内容   #开头的是注释     每个实现类名的全类名写一行，   文件需要utf-8编码
//		责任联模式可以使用这样的方式   Hadoop FileSystem 使用的也是这个形式
		Iterator<ServiceInterface> iterator2 = load2.iterator();
		while(iterator2.hasNext()){
			ServiceInterface next = iterator2.next();
			next.sayHello();
			next.sayHelloAgain();
		}
		
		// 等同于	 ServiceLoader.load(service, Thread.currentThread().getContextClassLoader())
		
//		为给定的服务类型和类加载器创建一个新的服务加载器。
		ServiceLoader<Domain> load = ServiceLoader.load(Domain.class, ClassLoader.getSystemClassLoader());
		
		/*
		 * 使用扩展类加载器为给定的服务类型创建一个新的服务加载器. 这种方便的方法只是定位扩展类加载器，称之为extClassLoader，然后返回
		 * ServiceLoader.load（service，extClassLoader） 如果无法找到扩展类加载器，则使用系统类加载器;
		 * 如果没有系统类加载器，则使用引导类加载器. 此方法仅在需要安装的提供程序时使用。
		 * 所产生的服务将仅查找并加载已安装到当前Java虚拟机中的提供程序; 应用程序的类路径上的提供程序将被忽略.
		 */

		ServiceLoader<Domain> loadInstalled = ServiceLoader.loadInstalled(Domain.class);

		/*清除此加载程序的提供程序缓存，以便所有提供程序都将被重新加载。
		调用此方法后，迭代器方法的后续调用将从头开始轻松查找和实例化提供程序，就像新创建的加载程序一样。
		此方法适用于将新提供程序安装到正在运行的Java虚拟机中的情况。*/
		load.reload();
		Iterator<Domain> iterator = load.iterator();
		
		load.forEach((Domain domm)->{
			//do  something
		});
	}

}
