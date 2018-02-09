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
//		����һ����׼Mbean  ����,   ��׼��MBean ����Ҫ�нӿڣ���ʵ���࣬ʵ������Ҫ��bean��������һ�������Ĺ�����
//		�൱�ڽ�һ����׼MBeanת��Ϊ��̬MBean
StandardMBean standardMBean = new StandardMBean(new StandardD(),StandardDMBean.class,false);
standardMBean(standardMBean);
//	����ֱ�Ӽ̳�StandardMBean ���ǻ�Ҫʵ��MBean�Ľӿڡ����£�
StandardMBeanOtherImpl standardMBeanOtherImpl = new StandardMBeanOtherImpl();
	}

	@SuppressWarnings("unused")
	private static void standardMBean(StandardMBean standardMBean) throws Exception {
//		��ȡ�����׼MBean��ʵ��
		Object implementation = standardMBean.getImplementation();
//		��ȡ�����׼MBean����
		Class<?> implementationClass = standardMBean.getImplementationClass();
//		��ȡ�����׼MBean��MBeanInfo
		MBeanInfo mBeanInfo = standardMBean.getMBeanInfo();
//		��ȡ���MBean�Ĺ���ӿ�
		Class<?> mBeanInterface = standardMBean.getMBeanInterface();
//		����bean�е�ָ������,��Ҫ��������,�����б�,     signature??
		Object invoke = standardMBean.invoke("disableDebugging", null, null);
		
//		����MBean��MBean��������ע��֮��ִ��������κβ�����
		standardMBean.postDeregister();
//		����MBean��MBean������ȡ��ע��֮ǰִ��������κβ�����
		standardMBean.preDeregister();

//		��ȡDynamic MBean���ض����Ե�ֵ��	
		Object attribute = standardMBean.getAttribute("TraceOn");//false
//		�趨����,��Ҫ����ӿ���set�ķ����������׳��쳣
//		standardMBean.setAttribute(new Attribute("TraceOn",false));//����ӿ�û��set�ķ���,������
//		�趨ʵ����
		standardMBean.setImplementation(implementation);
		
		String string = standardMBean.toString();
	}

}
