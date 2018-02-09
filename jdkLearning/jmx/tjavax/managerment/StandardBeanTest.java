package tjavax.managerment;

import javax.management.Attribute;
import javax.management.MBeanInfo;
import javax.management.StandardMBean;

import tjavax.managerment.standardbean.StandardD;
import tjavax.managerment.standardbean.StandardDMBean;
import tjavax.managerment.standardbean.StandardMBeanOtherImpl;

public class StandardBeanTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
//		构建一个标准Mbean  对象,   标准的MBean 是需要有接口，有实现类，实现类需要是bean，至少有一个公开的构造器
//		相当于将一个标准MBean转换为动态MBean
StandardMBean standardMBean = new StandardMBean(new StandardD(),StandardDMBean.class,false);
standardMBean(standardMBean);
//	或者直接继承StandardMBean 但是还要实现MBean的接口。如下：
StandardMBeanOtherImpl standardMBeanOtherImpl = new StandardMBeanOtherImpl();
	}

	@SuppressWarnings("unused")
	private static void standardMBean(StandardMBean standardMBean) throws Exception {
//		获取这个标准MBean的实现
		Object implementation = standardMBean.getImplementation();
//		获取这个标准MBean的类
		Class<?> implementationClass = standardMBean.getImplementationClass();
//		获取这个标准MBean的MBeanInfo
		MBeanInfo mBeanInfo = standardMBean.getMBeanInfo();
//		获取这个MBean的管理接口
		Class<?> mBeanInterface = standardMBean.getMBeanInterface();
//		调用bean中的指定方法,需要方法名字,参数列表,     signature??
		Object invoke = standardMBean.invoke("disableDebugging", null, null);
		
//		允许MBean在MBean服务器中注销之后执行所需的任何操作。
		standardMBean.postDeregister();
//		允许MBean在MBean服务器取消注册之前执行所需的任何操作。
		standardMBean.preDeregister();

//		获取Dynamic MBean的特定属性的值。	
		Object attribute = standardMBean.getAttribute("TraceOn");//false
//		设定属性,需要管理接口有set的方法，否则抛出异常
//		standardMBean.setAttribute(new Attribute("TraceOn",false));//这个接口没有set的方法,不能用
//		设定实现类
		standardMBean.setImplementation(implementation);
		
		String string = standardMBean.toString();
	}

}
