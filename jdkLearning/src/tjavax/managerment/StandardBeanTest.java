package tjavax.managerment;

import javax.management.StandardMBean;

import tjavax.managerment.standardbean.StandardBeanImpl;
import tjavax.managerment.standardbean.StandardBeanInterface;

public class StandardBeanTest {

	public static void main(String[] args) {
//		����һ����׼Mbean  ����,   ��׼��MBean ����Ҫ�нӿڣ���ʵ���࣬ʵ������Ҫ��bean��������һ�������Ĺ�����  
StandardMBean standardMBean = new StandardMBean(new StandardBeanImpl(),StandardBeanInterface.class,false);


	}

}
