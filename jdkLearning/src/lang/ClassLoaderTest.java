package lang;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import org.w3c.dom.DOMException;

import observable.ObservableTest;

public class ClassLoaderTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		testLoader();
		testAssert();
		//  获取系统的类加载器
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
//		sun.misc.Launcher$AppClassLoader@18b4aac2
		
		//将资源包装成URL，可以让其他JVM读取这个URL
		URL systemResource = ClassLoader.getSystemResource("");
//		file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/
		
		//将资源包装成输入流  可以读取这个输入流到JVM中
		InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("");
//		java.io.ByteArrayInputStream@5a42bbf4
		
//		从用来加载类的搜索路径中查找所有具有指定名称的资源
		Enumeration<URL> systemResources = ClassLoader.getSystemResources("");
//		sun.misc.CompoundEnumeration@270421f5包含下面两个元素
//		java.net.URLClassLoader$3@4f4a7090
//		sun.misc.CompoundEnumeration@52d455b8这个元素中包含下面两个元素
//									java.lang.ClassLoader$2@18ef96
//									java.net.URLClassLoader$3@6956de9
		
//		使用Class的方式获取这个Class的ClassLoader
		ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
//		sun.misc.Launcher$AppClassLoader@18b4aac2
		
//		使用全类名加载类，现在这个ClassLoader的搜索路径下查找，再从类路径下查找，再从javahome路径下查找
		Class<?> loadClass = classLoader.loadClass("lang.ClassDomain");
//		class lang.ClassDomain
//		父加载器-->启动加载器--->本加载器中   在搜索路径下查找指定名称的资源
		URL resource = classLoader.getResource("");
//		file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/
		
//		获取委托的父类加载器，      如果父类是引导类加载器有些实现是null  ，如果存在安全管理器，会进行安全检查
		ClassLoader parent = classLoader.getParent();
//		sun.misc.Launcher$ExtClassLoader@64b8f8f4   这个是EXT加载器加载扩展包
		ClassLoader parent2 = parent.getParent();//null

		
//		返回读取指定资源的输入流
		InputStream resourceAsStream = classLoader.getResourceAsStream("");
//		java.io.ByteArrayInputStream@18e8568
		
//		在父加载器（没有父加载器就启动加载器）和本加载器查找所有给定名称的资源。
		Enumeration<URL> resources = classLoader.getResources("");
//		sun.misc.CompoundEnumeration@769c9116包含下面两个元素
//		java.net.URLClassLoader$3@2d6d8735
//		sun.misc.CompoundEnumeration@6aceb1a5元素中嵌套下面的元素
//							[java.lang.ClassLoader$2@ba4d54,    java.net.URLClassLoader$3@12bc6874]
	
		
//		设置在此类加载器及其包含的嵌套类中指定的最高层类所需的断言状态。
		classLoader.setClassAssertionStatus("", true);
//		 设置此类加载器的默认断言状态。
		classLoader.setDefaultAssertionStatus(true);
//		 为指定包设置默认断言状态。
		classLoader.setPackageAssertionStatus("lang", false);
//		将此类加载器的默认断言状态设置为 false，并放弃与此类加载器关联的所有默认包或类断言状态设置。
		classLoader.clearAssertionStatus();
		
		String string = classLoader.toString();
	}

	@SuppressWarnings("unused")
	private static void testAssert() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ClassLoader classLoader = ClassDomain.class.getClassLoader();
//		设置默认的断言状态   				true启动断言  false禁用断言
		classLoader.setDefaultAssertionStatus(false);//默认ClassLoader所搜索的级别
//		设置具体类的断言状态
//		classLoader.setClassAssertionStatus("lang.ClassDomain", false);
//		在包下设置包中所有类的断言状态
//		classLoader.setPackageAssertionStatus("lang", false);
		
		Class<?> loadClass = classLoader.loadClass("lang.ClassDomain");
		ClassDomain newInstance = (ClassDomain)loadClass.newInstance();
		newInstance.asserta();
		int b=0;
	}

	@SuppressWarnings("unused")
	private static void testLoader() {

		ClassLoader classLoader = System.class.getClassLoader();//null
		ClassLoader classLoader3 = DOMException.class.getClassLoader();//null
		
//		类加载器   在加载  本项目的类    关联项目的类   使用的都是同一个ClassLoader
		
		ClassLoader classLoader2 = ClassTest.class.getClassLoader();
//		sun.misc.Launcher$AppClassLoader@18b4aac2
		ClassLoader classLoader4 = ObservableTest.class.getClassLoader();
//		sun.misc.Launcher$AppClassLoader@18b4aac2
		URL resource = classLoader2.getResource("");
//		file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/
		URL resource2 = classLoader4.getResource("");
//		file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/
		
//		ClassLoader classLoader5 = jsoup.JsoupTest.class.getClassLoader();
//		sun.misc.Launcher$AppClassLoader@18b4aac2
		
//		URL resource3 = classLoader5.getResource("");
//		file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/
		int c=0;
	}

}
