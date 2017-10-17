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
		//  ��ȡϵͳ���������
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
//		sun.misc.Launcher$AppClassLoader@18b4aac2
		
		//����Դ��װ��URL������������JVM��ȡ���URL
		URL systemResource = ClassLoader.getSystemResource("");
//		file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/
		
		//����Դ��װ��������  ���Զ�ȡ�����������JVM��
		InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("");
//		java.io.ByteArrayInputStream@5a42bbf4
		
//		�����������������·���в������о���ָ�����Ƶ���Դ
		Enumeration<URL> systemResources = ClassLoader.getSystemResources("");
//		sun.misc.CompoundEnumeration@270421f5������������Ԫ��
//		java.net.URLClassLoader$3@4f4a7090
//		sun.misc.CompoundEnumeration@52d455b8���Ԫ���а�����������Ԫ��
//									java.lang.ClassLoader$2@18ef96
//									java.net.URLClassLoader$3@6956de9
		
//		ʹ��Class�ķ�ʽ��ȡ���Class��ClassLoader
		ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
//		sun.misc.Launcher$AppClassLoader@18b4aac2
		
//		ʹ��ȫ���������࣬�������ClassLoader������·���²��ң��ٴ���·���²��ң��ٴ�javahome·���²���
		Class<?> loadClass = classLoader.loadClass("lang.ClassDomain");
//		class lang.ClassDomain
//		��������-->����������--->����������   ������·���²���ָ�����Ƶ���Դ
		URL resource = classLoader.getResource("");
//		file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/
		
//		��ȡί�еĸ����������      ����������������������Щʵ����null  ��������ڰ�ȫ������������а�ȫ���
		ClassLoader parent = classLoader.getParent();
//		sun.misc.Launcher$ExtClassLoader@64b8f8f4   �����EXT������������չ��
		ClassLoader parent2 = parent.getParent();//null

		
//		���ض�ȡָ����Դ��������
		InputStream resourceAsStream = classLoader.getResourceAsStream("");
//		java.io.ByteArrayInputStream@18e8568
		
//		�ڸ���������û�и����������������������ͱ��������������и������Ƶ���Դ��
		Enumeration<URL> resources = classLoader.getResources("");
//		sun.misc.CompoundEnumeration@769c9116������������Ԫ��
//		java.net.URLClassLoader$3@2d6d8735
//		sun.misc.CompoundEnumeration@6aceb1a5Ԫ����Ƕ�������Ԫ��
//							[java.lang.ClassLoader$2@ba4d54,    java.net.URLClassLoader$3@12bc6874]
	
		
//		�����ڴ�����������������Ƕ������ָ������߲�������Ķ���״̬��
		classLoader.setClassAssertionStatus("", true);
//		 ���ô����������Ĭ�϶���״̬��
		classLoader.setDefaultAssertionStatus(true);
//		 Ϊָ��������Ĭ�϶���״̬��
		classLoader.setPackageAssertionStatus("lang", false);
//		�������������Ĭ�϶���״̬����Ϊ false����������������������������Ĭ�ϰ��������״̬���á�
		classLoader.clearAssertionStatus();
		
		String string = classLoader.toString();
	}

	@SuppressWarnings("unused")
	private static void testAssert() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ClassLoader classLoader = ClassDomain.class.getClassLoader();
//		����Ĭ�ϵĶ���״̬   				true��������  false���ö���
		classLoader.setDefaultAssertionStatus(false);//Ĭ��ClassLoader�������ļ���
//		���þ�����Ķ���״̬
//		classLoader.setClassAssertionStatus("lang.ClassDomain", false);
//		�ڰ������ð���������Ķ���״̬
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
		
//		�������   �ڼ���  ����Ŀ����    ������Ŀ����   ʹ�õĶ���ͬһ��ClassLoader
		
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
