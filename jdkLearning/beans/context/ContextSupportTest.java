package context;

import java.beans.beancontext.BeanContext;
import java.beans.beancontext.BeanContextChild;
import java.beans.beancontext.BeanContextSupport;
import java.io.IOException;
import java.util.Locale;

import bean.MyBean;
import listener.MyPropertyChangeListener;

public class ContextSupportTest {

	public static void main(String[] args) throws Exception, IOException {
		// �½�������
		BeanContextSupport support = new BeanContextSupport();
		BeanContextChild beanContextChildPeer = support.beanContextChildPeer;// �������Լ�
		// �½�Ƕ�������� dTime:true��designMode false��rentime visible :�Ƿ�ɼ�
		BeanContextSupport nest = new BeanContextSupport(support, Locale.CHINA, false, false);// ������childPeer��support

		BeanContextSupport add = new BeanContextSupport();
		// ���Ƕ�������� add��beanContext��nest nest��children�������add, support�������Ƕ����
		boolean add2 = nest.add(add);

		BeanContext beanContext = nest.getBeanContext();// null,��û�б�add��ĳ�����ĵ�children��û������
		BeanContextChild beanContextChildPeer2 = nest.getBeanContextChildPeer();// ʹ��support������������������ԣ�BeanContextChildPeer��support��û�������������ľ����Լ�
		BeanContext beanContextPeer = nest.getBeanContextPeer();// �õ��ĺ���һ����һ����

		BeanContext beanContext2 = add.getBeanContext();// support,��Ϊ��nest������add�ˣ�����nest��BeanContextChildPeer��support,�������ľͱ����support
		BeanContextChild beanContextChildPeer3 = add.getBeanContextChildPeer();// �Լ�����Ϊû��������������������
		BeanContext beanContextPeer2 = add.getBeanContextPeer();// �Լ���������һ����
		// ֱ�ӱ���Լ������ģ��ᷢ��֪ͨ����Ӧ��support������Ƴ����child
		// add.setBeanContext(nest);

		// ����nest����support���������ģ�nest��beanContextChildPeer��support,��������beanֱ����ӵ�suport��
		Object instantiateChild = nest.instantiateChild("bean.MyBean");// ʹ�õ���Beans�ķ�����ʵ����һ��bean,
		
		Object instantiateChild2 = add.instantiateChild("bean.MyBean");// ��Ϊadd��BeanContextChildPeer���Լ����������bean����ӵ�add��children��

		MyPropertyChangeListener li = new MyPropertyChangeListener();
		nest.addPropertyChangeListener("name",li); 
		support.addPropertyChangeListener("name", li);
		add.addPropertyChangeListener("name", li);

		nest.firePropertyChange("name", "asd", "saf");//��support���������¼�,����BeanContextChildPeer�Լ������¼����д���
		add.firePropertyChange("name", "asd", "saf");//��add���������¼�
		support.firePropertyChange("name", "asd", "saf");//��support���������¼�
		
		System.out.println();
	}

}
