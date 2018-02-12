package dynamic.beans;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;

public class Box implements DynamicMBean {
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
		throw (new AttributeNotFoundException("Cannot find " + attribute + " attribute in" + this.getName()));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MBeanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReflectionException e) {
				// TODO Auto-generated catch block
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
				setAttribute(attr);
			} catch (AttributeNotFoundException | InvalidAttributeValueException | MBeanException
					| ReflectionException e) {
				// TODO Auto-generated catch block
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
		}else {
			throw new ReflectionException(new NoSuchMethodException(actionName),"Cannot find the operation "+actionName+" in "+ this.getName());
		}
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		// TODO Auto-generated method stub
		return null;
	}
//=====================
	public String printN() {
		return name+" method print";
	}
//	===========������ķ���=======================
	MBeanInfo beanInfo;
	
	private void buildDynamicMBeanInfo() {
		MBeanAttributeInfo nameInfo = new MBeanAttributeInfo("name",
												"java.lang.String",
												"��������name ,����String",
												true,
												true,
												false );
		MBeanAttributeInfo[] attrs=new MBeanAttributeInfo[] {nameInfo};
		
		Constructor<?>[] constructors = this.getClass().getConstructors();
		MBeanConstructorInfo con0 = new MBeanConstructorInfo("�ղι�����", constructors[0]);
		MBeanConstructorInfo con1 = new MBeanConstructorInfo("�ղι�����", constructors[1]);
		MBeanConstructorInfo[] conns=new MBeanConstructorInfo[] {con0,con1};
		
		MBeanParameterInfo paraInfo = new MBeanParameterInfo("name", "java.lang.String", "����name���ԵĲ�����String����");
		MBeanParameterInfo[] paras=new MBeanParameterInfo[] {paraInfo};
		
		MBeanOperationInfo operationInfo = new MBeanOperationInfo("printN", "��ӡ", new MBeanParameterInfo[0], "��ӡ�ַ���", MBeanOperationInfo.ACTION);
		MBeanOperationInfo[] operas=new MBeanOperationInfo[] {operationInfo};
		
		MBeanNotificationInfo[] notifi=new MBeanNotificationInfo[0];
		
		beanInfo=new MBeanInfo(this.getName(),"Box��MBean����",attrs,conns,operas,notifi) ;
	}
}
