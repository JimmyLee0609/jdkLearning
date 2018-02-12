package modelMbean.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;
import javax.management.openmbean.OpenDataException;

import modelMbean.beans.MySimpleModel;

public class SimpleModelMBeanServer {

	public static void main(String[] args)
			throws NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException,
			MBeanException, ReflectionException, InstanceNotFoundException, OpenDataException,
			RuntimeOperationsException, MalformedObjectNameException, ClassNotFoundException, NoSuchMethodException,
			SecurityException, IntrospectionException, InstantiationException, IllegalAccessException,
			InvalidTargetObjectTypeException, InvalidAttributeValueException, AttributeNotFoundException {
		createServer();
		buildModelMBeanInfo();

	}

	@SuppressWarnings("unused")
	private static void buildModelMBeanInfo() throws OpenDataException, RuntimeOperationsException, MBeanException,
			MalformedObjectNameException, InstanceAlreadyExistsException, NotCompliantMBeanException,
			ClassNotFoundException, NoSuchMethodException, SecurityException, IntrospectionException,
			InstanceNotFoundException, InstantiationException, IllegalAccessException, InvalidTargetObjectTypeException,
			ReflectionException, InvalidAttributeValueException, AttributeNotFoundException {
		// ===========��������===============
		Class<?> myBean = modelMbean.beans.MySimpleModel.class;
		Class<?> rmm = RequiredModelMBean.class;
		// =========bean�ķ���===============
		Method getNameMethod = myBean.getMethod("getName", new Class[0]);
		Method setNameMethod = myBean.getMethod("setName", new Class[] { String.class });
		Method getMmpMethod = myBean.getMethod("getMmp", new Class[0]);
		Method setMmpMethod = myBean.getMethod("setMmp", new Class[] { Integer[].class });
		Method resetMethod = myBean.getMethod("reset", new Class[0]);

		Method setManagedResource = rmm.getMethod("setManagedResource", new Class[] { Object.class, String.class });

		Method sendNotification = rmm.getMethod("sendNotification", new Class[] { Notification.class });
		Method addAttributeChangeNL = rmm.getMethod("addAttributeChangeNotificationListener",
				new Class[] { NotificationListener.class, String.class, Object.class });
		// ==============name����====================
		DescriptorSupport nameDesc = new DescriptorSupport();
		nameDesc.setField("name", "name");
		nameDesc.setField("descriptorType", "attribute");
		// nameDesc.setField("default","��modelMBean����name������");
		nameDesc.setField("displayName", "��ʾname���Ե�����");
		nameDesc.setField("getMethod", "getName");
		nameDesc.setField("setMethod", "setName");
		ModelMBeanAttributeInfo nameAttrInfo = new ModelMBeanAttributeInfo("name", "modelMBean�� name������Ϣ",
				getNameMethod, setNameMethod, nameDesc);
		// ==============mmp����======================
		DescriptorSupport mmpDesc = new DescriptorSupport();
		mmpDesc.setField("name", "mmp");
		mmpDesc.setField("descriptorType", "attribute");
		nameDesc.setField("default", "��modelMBean����name������");
		mmpDesc.setField("displayName", "displayName:mmp");
		mmpDesc.setField("getMethod", "getMmp");
		mmpDesc.setField("setMethod", "setMmp");
		ModelMBeanAttributeInfo mmpAttrInfo = new ModelMBeanAttributeInfo("mmp", "modelMBean��mmp������Ϣ", getMmpMethod,
				setMmpMethod, mmpDesc);
		// ===========myFieldԴ���в����ڵ�����===============
		DescriptorSupport newAttr = new DescriptorSupport();
		newAttr.setField("name", "myField");
		newAttr.setField("descriptorType", "attribute");
		newAttr.setField("displayName", "displayName:myField");
		newAttr.setField("currencyTimeLimit", "" + Integer.MAX_VALUE);
		ModelMBeanAttributeInfo newAttrInfo = new ModelMBeanAttributeInfo("myField", "java.lang.String", "�ҵ���������", true,
				true, false, newAttr);

		ModelMBeanAttributeInfo[] attrs = new ModelMBeanAttributeInfo[] { nameAttrInfo, mmpAttrInfo, newAttrInfo };

		// =======������============
		// ===�ղι�����==================
		Constructor<?> constructor = myBean.getConstructor(new Class[0]);
		ModelMBeanConstructorInfo con0 = new ModelMBeanConstructorInfo("MySimpleModel-model�ղι�����������", constructor);
		// =========�вι�����=========
		MBeanParameterInfo namePara = new MBeanParameterInfo("name", String.class.getName(), "�вι�����name����");
		MBeanParameterInfo mmpPara = new MBeanParameterInfo("mmp", Integer[].class.getName(), "�вι�����mmp����");
		MBeanParameterInfo[] paras = new MBeanParameterInfo[] { mmpPara, namePara };
		DescriptorSupport conDesc = new DescriptorSupport();
		conDesc.setField("name", "MySimpleModel(String name, Integer[] mmp)");
		conDesc.setField("descriptorType", "operation");
		conDesc.setField("role", "constructor");
		conDesc.setField("displayName", "��������ʾ������");
		ModelMBeanConstructorInfo pCons = new ModelMBeanConstructorInfo("MySimpleModel(String name, Integer[] mmp)",
				"�вι�����", paras, conDesc);

		ModelMBeanConstructorInfo[] conss = new ModelMBeanConstructorInfo[] { con0, pCons };
		// ==================operations==================
		ModelMBeanOperationInfo getName = new ModelMBeanOperationInfo("getName", getNameMethod);

		DescriptorSupport setMmpDesc = new DescriptorSupport();
		setMmpDesc.setField("name", "setName");
		setMmpDesc.setField("descriptorType", "operation");
		setMmpDesc.setField("displayName", "setName�ķ���");
		setMmpDesc.setField("role", "setter");
		setMmpDesc.setField("targetType", "ObjectReference");
		MBeanParameterInfo setNamePara = new MBeanParameterInfo("name", String.class.getName(), "������������");
		MBeanParameterInfo[] setNameParas = new MBeanParameterInfo[] { setNamePara };
		ModelMBeanOperationInfo setName = new ModelMBeanOperationInfo("setName", "�����µ�����", setNameParas,
				void.class.getName(), MBeanOperationInfo.ACTION, setMmpDesc);

		ModelMBeanOperationInfo getMmp = new ModelMBeanOperationInfo("getMmp", getMmpMethod);
		ModelMBeanOperationInfo setMmp = new ModelMBeanOperationInfo("setMmp", setMmpMethod);

		ModelMBeanOperationInfo reset = new ModelMBeanOperationInfo("resetMethod", resetMethod);
		// ==============mulԴ�����һ������===================
		DescriptorSupport mulDesc = new DescriptorSupport();
		mulDesc.setField("name", "mul");
		mulDesc.setField("descriptorType", "operation");
		mulDesc.setField("displayName", "mul:�ӷ�");
		mulDesc.setField("calss", "modelMbean.beans.MySimpleModel");
		mulDesc.setField("role", "operation");
		mulDesc.setField("targetType", "ObjectReference");
		// �����б�ֻҪ�Ƕ�Ӧ�Ĳ����Ϳ�����,����Ҫ����double
		MBeanParameterInfo one = new MBeanParameterInfo("one", double.class.getName(), "��һ������");
		MBeanParameterInfo two = new MBeanParameterInfo("two", double.class.getName(), "�ڶ�������");
		MBeanParameterInfo[] pars = new MBeanParameterInfo[] { one, one };
		ModelMBeanOperationInfo mul = new ModelMBeanOperationInfo("mul", "���������", pars, int.class.getName(),
				MBeanOperationInfo.UNKNOWN, mulDesc);

		// ===========requireModelMBean�ķ�����������ע�ᵽserver�У�invoke���ķ�����Ҫ��ע��=====================
		ModelMBeanOperationInfo operInfoSendNotification = new ModelMBeanOperationInfo("sendNotification descr",
				sendNotification);
		ModelMBeanOperationInfo operInfoAddAttributeChangeNL = new ModelMBeanOperationInfo("AddAttributeChangeNL descr",
				addAttributeChangeNL);
		ModelMBeanOperationInfo operInfoSetManagedResource = new ModelMBeanOperationInfo("setManagedResource descr",
				setManagedResource);
		ModelMBeanOperationInfo[] operations = new ModelMBeanOperationInfo[] { operInfoSendNotification,
				operInfoAddAttributeChangeNL, operInfoSetManagedResource, getName, setName, getMmp, setMmp, reset,
				mul };

		ModelMBeanNotificationInfo[] notis = new ModelMBeanNotificationInfo[0];

		ModelMBeanInfoSupport modelMBeanInfo = new ModelMBeanInfoSupport(modelMbean.beans.MySimpleModel.class.getName(),
				"�ҵĵ�һ��ModelMBean������", attrs, conss, operations, notis);
		// �½�mbean��Ӧ������������
		ObjectName objectName = new ObjectName("myFirstModelMBean:name=MySimpleModel");
		// ע�ᵽ������

		MySimpleModel model = new MySimpleModel();
		RequiredModelMBean bean = new RequiredModelMBean(modelMBeanInfo);
		bean.setManagedResource(model, "objectReference");
		// �½�һ�����Լ�����
		NotificationListener lister = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object handback) {
				Object source = notification.getSource();
				System.out.println(Thread.currentThread().getName() + "---myListener--"+handback+"--" + source);
			}
		};
		// ���֪ͨ������
		NotificationFilterSupport filter = new NotificationFilterSupport();
		//���Ա��֪ͨ�����ǿ�filter�Ƿ������type�����Ǿ�������Ծ�û�����,
		filter.enableType("jmx.attribute.change");//����lister���ܼ������Եı��
		bean.addNotificationListener(lister, filter, "handBack");
		// ������Ա��֪ͨ������,ֻҪ���Է���������ͻ���RequiredModelMBean�ķ���senAttributeChangeNotification
		bean.addAttributeChangeNotificationListener(lister, "name", "inhandback");
		// ��beanע�ᵽ������
		ObjectInstance registerMBean = server.registerMBean(bean, objectName);

		// �ڷ�����ע���requireModelMBean�еĽӿڷ�������invoke
		Object invoke = server.invoke(objectName, "mul", new Object[] { 3.5, 5.6 },
				new String[] { double.class.getName(), double.class.getName() });
		System.out.println(invoke);


		// ������Ա���������󣬱�����Ծͻᴥ���¼�
		server.setAttribute(objectName, new Attribute("name", "server set new name"));
//		ModelMBean��setAttribute�����AttributeChangeNotificationListener��NotificationListener�����������������������name�����Ըı佫����������������
		
//		����invoke�ǿ���ʵ�ʵĸı��Ӧ��ֵ��û�д���������������Ϊû��sendNotification
		Object invoke2 = server.invoke(objectName, "setName", new Object[] { "safafadsf" },
				new String[] { String.class.getName() });
		System.out.println(invoke2);
		
//		ģ�ⷢ�����Ա��notification
		Notification notification = new Notification(AttributeChangeNotification.ATTRIBUTE_CHANGE,bean,8441l,"AttributeChangeDetected");
		bean.sendNotification(notification);//�����ᴥ��Notification������lister
		
		
		
		// =====������һ�ָ����õķ�����������з���������===============
		/*
		 * ObjectInstance createMBean =
		 * server.createMBean(RequiredModelMBean.class.getName(), objectName, new
		 * Object[] { modelMBeanInfo }, new String[] { ModelMBeanInfo.class.getName()
		 * }); server.invoke(createMBean.getObjectName(), "setManagedResource", new
		 * Object[] { model, "objectReference" }, new String[]
		 * {Object.class.getName(),String.class.getName()});
		 */

	}

	static MBeanServer server;

	private static void createServer() throws NotCompliantMBeanException, InstanceAlreadyExistsException,
			MBeanRegistrationException, MBeanException, ReflectionException, InstanceNotFoundException {
		server = MBeanServerFactory.createMBeanServer();
		ObjectInstance bean = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
		Object invoke = server.invoke(bean.getObjectName(), "start", new Object[0], new String[0]);
	}

}
