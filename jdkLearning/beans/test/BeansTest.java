package test;

import java.beans.Beans;
import java.beans.beancontext.BeanContextServicesSupport;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class BeansTest {

	public static void main(String[] args) throws Exception{
//		�Ƿ������ʱ��GUI���ã�������Ƶı�ʶ�������ں���set
		boolean designTime = Beans.isDesignTime();
		boolean guiAvailable = Beans.isGuiAvailable();
//		�½�һ��������֧��
		BeanContextServicesSupport context = new BeanContextServicesSupport();
//		ʵ����һ��bean
		Object instantiate = Beans.instantiate(BeansTest.class.getClassLoader(), "bean.MyBean", context, null);
//		��һ��beanʵ���л�ȡָ�����͵�ʵ�����������Ϳ��ܲ�ͬ
		Object instanceOf = Beans.getInstanceOf(instantiate, bean.MyBean.class);
		
		Object instantiate2 = Beans.instantiate(BeansTest.class.getClassLoader(), "bean.ChieldBean");
		Object instanceOf2 = Beans.getInstanceOf(instantiate, bean.ChieldBean.class);
		
		System.out.println();
	}

}
