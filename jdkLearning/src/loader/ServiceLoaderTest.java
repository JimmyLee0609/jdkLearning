package loader;

import java.util.Iterator;
import java.util.ServiceLoader;

import util.Domain;

public class ServiceLoaderTest {

	public static void main(String[] args) {
		// ʹ�õ�ǰ�߳��������������Ϊ�����ķ������ʹ���һ���µķ��������
		ServiceLoader<ServiceInterface> load2 = ServiceLoader.load(loader.ServiceInterface.class);
		//���Ը��������ļ�����ʵ���࣬������ʵ������в�����
//		�����ļ���·���� ָ��ClassLoader�µ�  META-INF/service/��Ҫ���ص�ȫ�������ļ�����Ҫ�����׺
//		�ļ�������   #��ͷ����ע��     ÿ��ʵ��������ȫ����дһ�У�   �ļ���Ҫutf-8����
//		������ģʽ����ʹ�������ķ�ʽ   Hadoop FileSystem ʹ�õ�Ҳ�������ʽ
		Iterator<ServiceInterface> iterator2 = load2.iterator();
		while(iterator2.hasNext()){
			ServiceInterface next = iterator2.next();
			next.sayHello();
			next.sayHelloAgain();
		}
		
		// ��ͬ��	 ServiceLoader.load(service, Thread.currentThread().getContextClassLoader())
		
//		Ϊ�����ķ������ͺ������������һ���µķ����������
		ServiceLoader<Domain> load = ServiceLoader.load(Domain.class, ClassLoader.getSystemClassLoader());
		
		/*
		 * ʹ����չ�������Ϊ�����ķ������ʹ���һ���µķ��������. ���ַ���ķ���ֻ�Ƕ�λ��չ�����������֮ΪextClassLoader��Ȼ�󷵻�
		 * ServiceLoader.load��service��extClassLoader�� ����޷��ҵ���չ�����������ʹ��ϵͳ�������;
		 * ���û��ϵͳ�����������ʹ�������������. �˷���������Ҫ��װ���ṩ����ʱʹ�á�
		 * �������ķ��񽫽����Ҳ������Ѱ�װ����ǰJava������е��ṩ����; Ӧ�ó������·���ϵ��ṩ���򽫱�����.
		 */

		ServiceLoader<Domain> loadInstalled = ServiceLoader.loadInstalled(Domain.class);

		/*����˼��س�����ṩ���򻺴棬�Ա������ṩ���򶼽������¼��ء�
		���ô˷����󣬵����������ĺ������ý���ͷ��ʼ���ɲ��Һ�ʵ�����ṩ���򣬾����´����ļ��س���һ����
		�˷��������ڽ����ṩ����װ���������е�Java������е������*/
		load.reload();
		Iterator<Domain> iterator = load.iterator();
		
		load.forEach((Domain domm)->{
			//do  something
		});
	}

}
