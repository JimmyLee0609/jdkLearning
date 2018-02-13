package dynamic.beans;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;

public class Box implements DynamicMBean ,NotificationEmitter{
	String name;

	public Box() {
		buildDynamicMBeanInfo();
	}


	public Box(String name) {
		super();
		buildDynamicMBeanInfo();
		this.name = name;
	}

	// =====get/set方法=======
	public String getName() {
		return name;
	}

	public void setName(String name) {
//		发送通知                                                                                   源对象
		notifi.sendNotification(new AttributeChangeNotification(this, 
				651616l,//序列号
				System.currentTimeMillis(),//时间戳
				"java.attribute.change",  //通知类型  与过滤器的enType匹配
				"name", //属性名字
				"java.lang.String", //属性类型
				this.name, //原来的值
				name));//新的值
		this.name = name;
	}

	@Override
	public String toString() {
		return "Box [name=" + name + "]";
	}

	// ==========dynamic的接口方法=========================
	@Override
	public Object getAttribute(String attribute)
			throws AttributeNotFoundException, MBeanException, ReflectionException {
		if (null == attribute) {
			throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot ne null"),
					"Cannont invoke a getter of " + this.getName() + " with null attribute name");
		}
		if (attribute.equals("name"))
			return getName();
		throw (new AttributeNotFoundException("Cannot find " + attribute + " attribute in" + this.getClass().getName()));
	}

	@Override
	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
		if (null == attribute) {
			throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot bu null"),
					"Cannot invoke a setter of " + this.getName() + " with null attribute");
		}
		String name = attribute.getName();
		Object value = attribute.getValue();
		if (null == name)
			throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"),
					"Cannot invoke the setter of " + this.name + " with null attribute name");

		if (name.equals("name")) {
			if (null == value) {
				try {
					setName(null);
				} catch (Exception e) {
					throw new InvalidAttributeValueException("Cannot set attribute " + name + " to null");
				}
			} else {
				if (String.class.isAssignableFrom(value.getClass())) {
					setName((String) value);
				}else {
					throw new InvalidAttributeValueException("Cannot set attribute "+name+" to a "+value.getClass().getName()+" object,String expected");
				}
			}
		}else {
			throw new AttributeNotFoundException("Attribute "+name+"not fount in "+this.getName());
		}
	}

	

	@Override
	public AttributeList getAttributes(String[] attributes) {
		AttributeList  list = new AttributeList();
		for (String attribute : attributes) {
			
			try {
				Object value = getAttribute(attribute);
				list.add(new Attribute(attribute,value));
			} catch (AttributeNotFoundException e) {
				e.printStackTrace();
			} catch (MBeanException e) {
				e.printStackTrace();
			} catch (ReflectionException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		AttributeList returnList = new AttributeList();
		for(Iterator<?> it=attributes.iterator();it.hasNext();) {
			Attribute attr=(Attribute)it.next();
			try {
//				设置属性集
				setAttribute(attr);
//				返回设置好的属性集
				returnList.add(attr);
			} catch (AttributeNotFoundException | InvalidAttributeValueException | MBeanException
					| ReflectionException e) {
				e.printStackTrace();
			}
		}
		return returnList;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {
		if(null==actionName) {
			throw new RuntimeOperationsException(new IllegalArgumentException("Operation name cannot be null"), "Cannot invoke a null operation in "+this.getName());
		}else if(actionName.equals("printN")) {
			return printN();
		}else if(actionName.equals("addNotificationListener")) {
			
			addNotificationListener((NotificationListener)params[0],(NotificationFilter)params[1],params[2]);
			return "addlisten";
		}
		else {
			throw new ReflectionException(new NoSuchMethodException(actionName),"Cannot find the operation "+actionName+" in "+ this.getName());
		}
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		return beanInfo;
	}
//=====================
	public String printN() {
		System.out.println(name+" method print");
		return name+" method print";
	}
//	===========构建类的方法=======================
	MBeanInfo beanInfo;
	
	private void buildDynamicMBeanInfo() {
//		========属性==============
		MBeanAttributeInfo nameInfo = new MBeanAttributeInfo("name",
												"java.lang.String",
												"属性名字name ,类型String",
												true,
												true,
												false );
		MBeanAttributeInfo[] attrs=new MBeanAttributeInfo[] {nameInfo};
//		========构造器==========
		Constructor<?>[] constructors = this.getClass().getConstructors();
		MBeanConstructorInfo con0 = new MBeanConstructorInfo("空参构造器", constructors[0]);
		MBeanConstructorInfo con1 = new MBeanConstructorInfo("空参构造器", constructors[1]);
		MBeanConstructorInfo[] conns=new MBeanConstructorInfo[] {con0,con1};
//		========operation===========
		MBeanParameterInfo paraInfo = new MBeanParameterInfo("name", "java.lang.String", "设置name属性的参数，String类型");
		MBeanParameterInfo[] paras=new MBeanParameterInfo[] {paraInfo};
		
		MBeanOperationInfo operationInfo = new MBeanOperationInfo("printN", "打印", new MBeanParameterInfo[0], "打印字符串", MBeanOperationInfo.ACTION);
		
		/*MBeanParameterInfo listener = new MBeanParameterInfo("listener","javax.management.NotificationListener","监听器");
		MBeanParameterInfo filter = new MBeanParameterInfo("filter","javax.management.NotificationFilter","过滤器");
		MBeanParameterInfo handBack = new MBeanParameterInfo("handback","java.lang.Object","handBack");
		MBeanParameterInfo [] noParas=new MBeanParameterInfo[] {listener,filter,handBack};
		MBeanOperationInfo noInfo = new MBeanOperationInfo("addNotificationListener","添加监听器",noParas,"objectRefrecce",MBeanOperationInfo.ACTION);*/
		
		
		MBeanOperationInfo[] operas=new MBeanOperationInfo[] {operationInfo};//,noInfo};
//		=======notification============
		MBeanNotificationInfo notificationInfo = new MBeanNotificationInfo(new String[] {"jmx.attribute.change"},"javax.management.Notification","通知");
		MBeanNotificationInfo[] notifi=new MBeanNotificationInfo[] {notificationInfo};
		
		beanInfo=new MBeanInfo(this.getClass().getName(),"Box的MBean描述",attrs,conns,operas,notifi) ;
	}

//==========notification=======================
	NotificationBroadcasterSupport notifi=new NotificationBroadcasterSupport();
	@Override
	public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
			throws IllegalArgumentException {
		notifi.addNotificationListener(listener, filter, handback);
	}


	@Override
	public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
		notifi.removeNotificationListener(listener);
	}


	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		return notifi.getNotificationInfo();
	}


	@Override
	public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
			throws ListenerNotFoundException {
		notifi.removeNotificationListener(listener, filter, handback);
	}
}
