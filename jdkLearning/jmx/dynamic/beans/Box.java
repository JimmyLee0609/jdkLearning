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

	// =====get/set����=======
	public String getName() {
		return name;
	}

	public void setName(String name) {
//		����֪ͨ                                                                                   Դ����
		notifi.sendNotification(new AttributeChangeNotification(this, 
				651616l,//���к�
				System.currentTimeMillis(),//ʱ���
				"java.attribute.change",  //֪ͨ����  ���������enTypeƥ��
				"name", //��������
				"java.lang.String", //��������
				this.name, //ԭ����ֵ
				name));//�µ�ֵ
		this.name = name;
	}

	@Override
	public String toString() {
		return "Box [name=" + name + "]";
	}

	// ==========dynamic�Ľӿڷ���=========================
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
//				�������Լ�
				setAttribute(attr);
//				�������úõ����Լ�
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
//	===========������ķ���=======================
	MBeanInfo beanInfo;
	
	private void buildDynamicMBeanInfo() {
//		========����==============
		MBeanAttributeInfo nameInfo = new MBeanAttributeInfo("name",
												"java.lang.String",
												"��������name ,����String",
												true,
												true,
												false );
		MBeanAttributeInfo[] attrs=new MBeanAttributeInfo[] {nameInfo};
//		========������==========
		Constructor<?>[] constructors = this.getClass().getConstructors();
		MBeanConstructorInfo con0 = new MBeanConstructorInfo("�ղι�����", constructors[0]);
		MBeanConstructorInfo con1 = new MBeanConstructorInfo("�ղι�����", constructors[1]);
		MBeanConstructorInfo[] conns=new MBeanConstructorInfo[] {con0,con1};
//		========operation===========
		MBeanParameterInfo paraInfo = new MBeanParameterInfo("name", "java.lang.String", "����name���ԵĲ�����String����");
		MBeanParameterInfo[] paras=new MBeanParameterInfo[] {paraInfo};
		
		MBeanOperationInfo operationInfo = new MBeanOperationInfo("printN", "��ӡ", new MBeanParameterInfo[0], "��ӡ�ַ���", MBeanOperationInfo.ACTION);
		
		/*MBeanParameterInfo listener = new MBeanParameterInfo("listener","javax.management.NotificationListener","������");
		MBeanParameterInfo filter = new MBeanParameterInfo("filter","javax.management.NotificationFilter","������");
		MBeanParameterInfo handBack = new MBeanParameterInfo("handback","java.lang.Object","handBack");
		MBeanParameterInfo [] noParas=new MBeanParameterInfo[] {listener,filter,handBack};
		MBeanOperationInfo noInfo = new MBeanOperationInfo("addNotificationListener","��Ӽ�����",noParas,"objectRefrecce",MBeanOperationInfo.ACTION);*/
		
		
		MBeanOperationInfo[] operas=new MBeanOperationInfo[] {operationInfo};//,noInfo};
//		=======notification============
		MBeanNotificationInfo notificationInfo = new MBeanNotificationInfo(new String[] {"jmx.attribute.change"},"javax.management.Notification","֪ͨ");
		MBeanNotificationInfo[] notifi=new MBeanNotificationInfo[] {notificationInfo};
		
		beanInfo=new MBeanInfo(this.getClass().getName(),"Box��MBean����",attrs,conns,operas,notifi) ;
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
