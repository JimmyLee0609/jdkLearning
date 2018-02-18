package test;

import java.beans.Beans;
import java.beans.beancontext.BeanContextServicesSupport;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class BeansTest {

	public static void main(String[] args) throws Exception{
//		是否是设计时，GUI可用，都是设计的标识，可以在后来set
		boolean designTime = Beans.isDesignTime();
		boolean guiAvailable = Beans.isGuiAvailable();
//		新建一个上下文支持
		BeanContextServicesSupport context = new BeanContextServicesSupport();
//		实例化一个bean
		Object instantiate = Beans.instantiate(BeansTest.class.getClassLoader(), "bean.MyBean", context, null);
//		从一个bean实例中获取指定类型的实例，两个类型可能不同
		Object instanceOf = Beans.getInstanceOf(instantiate, bean.MyBean.class);
		
		Object instantiate2 = Beans.instantiate(BeansTest.class.getClassLoader(), "bean.ChieldBean");
		Object instanceOf2 = Beans.getInstanceOf(instantiate, bean.ChieldBean.class);
		
		System.out.println();
	}

}
