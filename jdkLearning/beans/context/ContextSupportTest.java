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
		// 新建上下文
		BeanContextSupport support = new BeanContextSupport();
		BeanContextChild beanContextChildPeer = support.beanContextChildPeer;// 这样是自己
		// 新建嵌套上下文 dTime:true在designMode false在rentime visible :是否可见
		BeanContextSupport nest = new BeanContextSupport(support, Locale.CHINA, false, false);// 这样的childPeer是support

		BeanContextSupport add = new BeanContextSupport();
		// 添加嵌套上下文 add的beanContext是nest nest的children中添加了add, support不在这层嵌套中
		boolean add2 = nest.add(add);

		BeanContext beanContext = nest.getBeanContext();// null,他没有被add到某上下文的children中没有上文
		BeanContextChild beanContextChildPeer2 = nest.getBeanContextChildPeer();// 使用support来创建这个上下文所以，BeanContextChildPeer是support，没有用其他上下文就是自己
		BeanContext beanContextPeer = nest.getBeanContextPeer();// 拿到的和上一个是一样的

		BeanContext beanContext2 = add.getBeanContext();// support,因为被nest上下文add了，而且nest的BeanContextChildPeer是support,所以上文就变成了support
		BeanContextChild beanContextChildPeer3 = add.getBeanContextChildPeer();// 自己，因为没有其他的上下文来构建
		BeanContext beanContextPeer2 = add.getBeanContextPeer();// 自己和上面是一样的
		// 直接变更自己上下文，会发出通知，对应的support里面会移除这个child
		// add.setBeanContext(nest);

		// 由于nest是由support构建出来的，nest的beanContextChildPeer是support,所以这里bean直接添加到suport中
		Object instantiateChild = nest.instantiateChild("bean.MyBean");// 使用的是Beans的方法来实例化一个bean,
		
		Object instantiateChild2 = add.instantiateChild("bean.MyBean");// 因为add的BeanContextChildPeer是自己，所以这个bean会添加到add的children中

		MyPropertyChangeListener li = new MyPropertyChangeListener();
		nest.addPropertyChangeListener("name",li); 
		support.addPropertyChangeListener("name", li);
		add.addPropertyChangeListener("name", li);

		nest.firePropertyChange("name", "asd", "saf");//是support监听到了事件,就是BeanContextChildPeer对监听的事件进行处理
		add.firePropertyChange("name", "asd", "saf");//是add监听到了事件
		support.firePropertyChange("name", "asd", "saf");//是support监听到了事件
		
		System.out.println();
	}

}
