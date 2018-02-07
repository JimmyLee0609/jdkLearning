package tjavax.managerment;

import javax.management.StandardMBean;

import tjavax.managerment.standardbean.StandardBeanImpl;
import tjavax.managerment.standardbean.StandardBeanInterface;

public class StandardBeanTest {

	public static void main(String[] args) {
//		构建一个标准Mbean  对象,   标准的MBean 是需要有接口，有实现类，实现类需要是bean，至少有一个公开的构造器  
StandardMBean standardMBean = new StandardMBean(new StandardBeanImpl(),StandardBeanInterface.class,false);


	}

}
