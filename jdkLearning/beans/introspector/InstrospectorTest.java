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
		// ʹ����ʡ��ָ�����࿪ʼ��ȡbeanInfoֱ��ָ���������������ָ����ȡ�����Ϣ����
		BeanInfo beanInfo = Introspector.getBeanInfo(ChieldBean.class, MyBean.class, Introspector.USE_ALL_BEANINFO);
		String[] beanInfoSearchPath = Introspector.getBeanInfoSearchPath();
		Introspector.setBeanInfoSearchPath(beanInfoSearchPath);
		String decapitalize = Introspector.decapitalize("abdTer");// ��������ַ��������շ�ʽת��
		Introspector.flushCaches();// ������д��
		readBeanInfo(beanInfo);
	}

	// ====��beanInfo============
	@SuppressWarnings("unused")
	private static void readBeanInfo(BeanInfo beanInfo) throws IntrospectionException {
		// ��ȡ���Ե�������
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		readProperty(propertyDescriptors[0]);
		// ��ȡ�¼���������
		EventSetDescriptor[] eventSetDescriptors = beanInfo.getEventSetDescriptors();
		readEventDescriptor(eventSetDescriptors[0]);
		// ��ȡ�����������
		BeanInfo[] additionalBeanInfo = beanInfo.getAdditionalBeanInfo();
		readBeanInfo(additionalBeanInfo[0]);
		// ��ȡ���������
		BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
		readBeanDescriptor(beanDescriptor);
		// ��ȡĬ�ϵ��¼�����
		int defaultEventIndex = beanInfo.getDefaultEventIndex();
		// ��ȡĬ�ϵ���������
		int defaultPropertyIndex = beanInfo.getDefaultPropertyIndex();
		// ��ȡ������������
		MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
		readMethodDescriptor(methodDescriptors[0]);
		String string = beanInfo.toString();
	}

	// =====������===========
	private static void readMethodDescriptor(MethodDescriptor MethodDesc) {
		// ��ȡ��������ķ���������
		Method method = MethodDesc.getMethod();
		// ��ȡ����������
		String name = MethodDesc.getName();
		MethodDesc.setName(name);
		// ��ȡ������ʾ������
		String displayName = MethodDesc.getDisplayName();
		MethodDesc.setDisplayName(displayName);
		// ��ȡ�����Ĳ�������
		ParameterDescriptor[] paraDesc = MethodDesc.getParameterDescriptors();
		readParaDesc(paraDesc[0]);
		// ��ȡ�����Ķ�����
		String shortDescription = MethodDesc.getShortDescription();
		MethodDesc.setShortDescription(shortDescription);

		// ��ȡ���������е��������ּ�����һ����
		Enumeration<String> attributeNames = MethodDesc.attributeNames();
		while (attributeNames.hasMoreElements()) {
			String nextElement = attributeNames.nextElement();
			// �����������ֻ�ȡ��������������ֵ
			MethodDesc.getValue(nextElement);
		}
	}

	// ========�����������������Ǹ������ͣ�����һ����===========
	private static void readParaDesc(ParameterDescriptor paraDesc) {
		// ��ȡ��ʾ������
		String displayName = paraDesc.getDisplayName();
		paraDesc.setDisplayName(displayName);
		// ��ȡ����
		String name = paraDesc.getName();
		paraDesc.setName(name);
		// ��ȡ������
		String shortDescription = paraDesc.getShortDescription();
		paraDesc.setShortDescription(shortDescription);
		// ��ȡ��������������Լ�
		Enumeration<String> attributeNames = paraDesc.attributeNames();
		while (attributeNames.hasMoreElements()) {
			String nextElement = attributeNames.nextElement();
			Object value = paraDesc.getValue(nextElement);
			paraDesc.setValue(nextElement, value);
		}
	}

	// ======����������===========
	@SuppressWarnings("unused")
	private static void readBeanDescriptor(BeanDescriptor beanDescriptor) {
		// ��ȡbean��Ӧ����
		Class<?> beanClass = beanDescriptor.getBeanClass();
		// ��ȡ���������Զ����࣬û���趨����Null
		Class<?> customizerClass = beanDescriptor.getCustomizerClass();
		// ��ȡ��ʾ������
		String displayName = beanDescriptor.getDisplayName();
		// ��ȡ����
		String name = beanDescriptor.getName();
		// ��ȡ������
		String shortDescription = beanDescriptor.getShortDescription();
		// ������������������
		beanDescriptor.setName(name);
		// �������������Ķ�����
		beanDescriptor.setShortDescription(shortDescription);
		// ����������������ʾ����
		beanDescriptor.setDisplayName(displayName);

		// ��ȡ���������������ּ�
		Enumeration<String> attributeNames = beanDescriptor.attributeNames();
		while (attributeNames.hasMoreElements()) {
			String nextElement = attributeNames.nextElement();
			// �������Ե����ֻ�ȡֵ
			Object value = beanDescriptor.getValue(nextElement);
			// ʹ��������Ϊָ�����ֵ���������ָ����ֵ
			beanDescriptor.setValue(nextElement, value);
		}
	}

	// ==========���¼�============
	private static void readEventDescriptor(EventSetDescriptor eventSetDescriptor) {
		// ��ȡ�¼�������������
		String name = eventSetDescriptor.getName();
		// ��ȡ�¼�����������ʾ����
		String displayName = eventSetDescriptor.getDisplayName();
		// ��ȡ�¼��������Ķ�����
		String shortDescription = eventSetDescriptor.getShortDescription();
		// ��ȡ�¼��������ļ�������
		Class<?> listenerType = eventSetDescriptor.getListenerType();
		// ��ȡ�¼�����������Ӽ����ķ���
		Method addListenerMethod = eventSetDescriptor.getAddListenerMethod();
		// ��ȡ�¼����������Ƴ������ķ���
		Method removeListenerMethod = eventSetDescriptor.getRemoveListenerMethod();
		// ��ȡ�¼���������ȡ�������ķ���
		Method getListenerMethod = eventSetDescriptor.getGetListenerMethod();
		Method[] listenerMethods = eventSetDescriptor.getListenerMethods();
		// ��ȡ�¼���������ȡ��������������
		MethodDescriptor[] listenerMethodDescriptors = eventSetDescriptor.getListenerMethodDescriptors();
	}

	// ===========������======================
	@SuppressWarnings("unused")
	private static void readProperty(PropertyDescriptor propertyDescriptor) throws IntrospectionException {
		// ��ȡ���Ե�д����
		Method writeMethod = propertyDescriptor.getWriteMethod();
		// ��ȡ���ԵĶ�����
		Method readMethod = propertyDescriptor.getReadMethod();

		propertyDescriptor.isConstrained();// Լ����
		// ��ȡ���Ե�����
		Class<?> propertyType = propertyDescriptor.getPropertyType();
		// ��ȡ���Ե���ʾ����
		String displayName = propertyDescriptor.getDisplayName();
		// �趨���Ե���ʾ����
		propertyDescriptor.setDisplayName(displayName);
		// ��ȡ���ԵĶ�����
		String shortDescription = propertyDescriptor.getShortDescription();
		// �趨���ԵĶ�����
		propertyDescriptor.setShortDescription(shortDescription);
		// ��ȡ���Ե�����
		String name = propertyDescriptor.getName();
		// �趨���Ե�����
		propertyDescriptor.setName(name);
		// ��ȡ���Ա༭����
		Class<?> propertyEditorClass = propertyDescriptor.getPropertyEditorClass();
		// �趨���Ա༭����
		propertyDescriptor.setPropertyEditorClass(propertyEditorClass);
		// ��ȡ���Ե����ּ�
		Enumeration<String> attributeNames = propertyDescriptor.attributeNames();
		while (attributeNames.hasMoreElements()) {
			String nextElement = attributeNames.nextElement();
			// �������Ե����ֻ�ȡֵ
			Object value = propertyDescriptor.getValue(nextElement);
			// �趨ָ�����Ե�ֵ
			propertyDescriptor.setValue(nextElement, value);
		}
		// �趨���ԵĶ�д��Ӧ�ķ�������
		propertyDescriptor.setReadMethod(readMethod);
		propertyDescriptor.setWriteMethod(writeMethod);
	}

}
