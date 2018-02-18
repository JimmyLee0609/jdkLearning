package introspector;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Enumeration;

import bean.ChieldBean;
import bean.MyBean;

public class InstrospectorTest {

	public static void main(String[] args) throws IntrospectionException {
		// 使用内省从指定的类开始获取beanInfo直到指定的类结束，并且指定获取类的信息种类
		BeanInfo beanInfo = Introspector.getBeanInfo(ChieldBean.class, MyBean.class, Introspector.USE_ALL_BEANINFO);
		String[] beanInfoSearchPath = Introspector.getBeanInfoSearchPath();
		Introspector.setBeanInfoSearchPath(beanInfoSearchPath);
		String decapitalize = Introspector.decapitalize("abdTer");// 将传入的字符串进行驼峰式转换
		Introspector.flushCaches();// 将缓存写出
		readBeanInfo(beanInfo);
	}

	// ====读beanInfo============
	@SuppressWarnings("unused")
	private static void readBeanInfo(BeanInfo beanInfo) throws IntrospectionException {
		// 获取属性的描述器
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		readProperty(propertyDescriptors[0]);
		// 获取事件的描述器
		EventSetDescriptor[] eventSetDescriptors = beanInfo.getEventSetDescriptors();
		readEventDescriptor(eventSetDescriptors[0]);
		// 获取额外的描述器
		BeanInfo[] additionalBeanInfo = beanInfo.getAdditionalBeanInfo();
		readBeanInfo(additionalBeanInfo[0]);
		// 获取类的描述器
		BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
		readBeanDescriptor(beanDescriptor);
		// 获取默认的事件索引
		int defaultEventIndex = beanInfo.getDefaultEventIndex();
		// 获取默认的属性索引
		int defaultPropertyIndex = beanInfo.getDefaultPropertyIndex();
		// 获取方法的描述器
		MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
		readMethodDescriptor(methodDescriptors[0]);
		String string = beanInfo.toString();
	}

	// =====读方法===========
	private static void readMethodDescriptor(MethodDescriptor MethodDesc) {
		// 获取反射出来的方法的引用
		Method method = MethodDesc.getMethod();
		// 获取方法的名字
		String name = MethodDesc.getName();
		MethodDesc.setName(name);
		// 获取方法显示的名字
		String displayName = MethodDesc.getDisplayName();
		MethodDesc.setDisplayName(displayName);
		// 获取方法的参数描述
		ParameterDescriptor[] paraDesc = MethodDesc.getParameterDescriptors();
		readParaDesc(paraDesc[0]);
		// 获取方法的短描述
		String shortDescription = MethodDesc.getShortDescription();
		MethodDesc.setShortDescription(shortDescription);

		// 获取方法描述中的属性名字集。。一般无
		Enumeration<String> attributeNames = MethodDesc.attributeNames();
		while (attributeNames.hasMoreElements()) {
			String nextElement = attributeNames.nextElement();
			// 根据属性名字获取方法描述的属性值
			MethodDesc.getValue(nextElement);
		}
	}

	// ========读参数，参数可以是复杂类型，就是一个类===========
	private static void readParaDesc(ParameterDescriptor paraDesc) {
		// 获取显示的名字
		String displayName = paraDesc.getDisplayName();
		paraDesc.setDisplayName(displayName);
		// 获取名字
		String name = paraDesc.getName();
		paraDesc.setName(name);
		// 获取短描述
		String shortDescription = paraDesc.getShortDescription();
		paraDesc.setShortDescription(shortDescription);
		// 获取参数描述其的属性集
		Enumeration<String> attributeNames = paraDesc.attributeNames();
		while (attributeNames.hasMoreElements()) {
			String nextElement = attributeNames.nextElement();
			Object value = paraDesc.getValue(nextElement);
			paraDesc.setValue(nextElement, value);
		}
	}

	// ======读类描述器===========
	@SuppressWarnings("unused")
	private static void readBeanDescriptor(BeanDescriptor beanDescriptor) {
		// 获取bean对应的类
		Class<?> beanClass = beanDescriptor.getBeanClass();
		// 获取类描述的自定义类，没有设定就是Null
		Class<?> customizerClass = beanDescriptor.getCustomizerClass();
		// 获取显示的名字
		String displayName = beanDescriptor.getDisplayName();
		// 获取名字
		String name = beanDescriptor.getName();
		// 获取短描述
		String shortDescription = beanDescriptor.getShortDescription();
		// 设置类描述器的名字
		beanDescriptor.setName(name);
		// 设置类描述器的短描述
		beanDescriptor.setShortDescription(shortDescription);
		// 设置类描述器的显示名字
		beanDescriptor.setDisplayName(displayName);

		// 获取类描述的属性名字集
		Enumeration<String> attributeNames = beanDescriptor.attributeNames();
		while (attributeNames.hasMoreElements()) {
			String nextElement = attributeNames.nextElement();
			// 根据属性的名字获取值
			Object value = beanDescriptor.getValue(nextElement);
			// 使用类描述为指定名字的属性设置指定的值
			beanDescriptor.setValue(nextElement, value);
		}
	}

	// ==========读事件============
	private static void readEventDescriptor(EventSetDescriptor eventSetDescriptor) {
		// 获取事件描述器的名字
		String name = eventSetDescriptor.getName();
		// 获取事件描述器的显示名字
		String displayName = eventSetDescriptor.getDisplayName();
		// 获取事件描述器的短描述
		String shortDescription = eventSetDescriptor.getShortDescription();
		// 获取事件描述器的监听类型
		Class<?> listenerType = eventSetDescriptor.getListenerType();
		// 获取事件描述器的添加监听的方法
		Method addListenerMethod = eventSetDescriptor.getAddListenerMethod();
		// 获取事件描述器的移除监听的方法
		Method removeListenerMethod = eventSetDescriptor.getRemoveListenerMethod();
		// 获取事件描述器获取监听器的方法
		Method getListenerMethod = eventSetDescriptor.getGetListenerMethod();
		Method[] listenerMethods = eventSetDescriptor.getListenerMethods();
		// 获取事件描述器获取监听方法描述器
		MethodDescriptor[] listenerMethodDescriptors = eventSetDescriptor.getListenerMethodDescriptors();
	}

	// ===========读属性======================
	@SuppressWarnings("unused")
	private static void readProperty(PropertyDescriptor propertyDescriptor) throws IntrospectionException {
		// 获取属性的写方法
		Method writeMethod = propertyDescriptor.getWriteMethod();
		// 获取属性的读方法
		Method readMethod = propertyDescriptor.getReadMethod();

		propertyDescriptor.isConstrained();// 约束的
		// 获取属性的类型
		Class<?> propertyType = propertyDescriptor.getPropertyType();
		// 获取属性的显示名字
		String displayName = propertyDescriptor.getDisplayName();
		// 设定属性的显示名字
		propertyDescriptor.setDisplayName(displayName);
		// 获取属性的短描述
		String shortDescription = propertyDescriptor.getShortDescription();
		// 设定属性的短描述
		propertyDescriptor.setShortDescription(shortDescription);
		// 获取属性的名字
		String name = propertyDescriptor.getName();
		// 设定属性的名字
		propertyDescriptor.setName(name);
		// 获取属性编辑的类
		Class<?> propertyEditorClass = propertyDescriptor.getPropertyEditorClass();
		// 设定属性编辑的类
		propertyDescriptor.setPropertyEditorClass(propertyEditorClass);
		// 获取属性的名字集
		Enumeration<String> attributeNames = propertyDescriptor.attributeNames();
		while (attributeNames.hasMoreElements()) {
			String nextElement = attributeNames.nextElement();
			// 根据属性的名字获取值
			Object value = propertyDescriptor.getValue(nextElement);
			// 设定指定属性的值
			propertyDescriptor.setValue(nextElement, value);
		}
		// 设定属性的读写对应的方法引用
		propertyDescriptor.setReadMethod(readMethod);
		propertyDescriptor.setWriteMethod(writeMethod);
	}

}
