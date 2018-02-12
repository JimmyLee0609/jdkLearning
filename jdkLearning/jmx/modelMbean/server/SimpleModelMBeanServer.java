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
		// ===========构建属性===============
		Class<?> myBean = modelMbean.beans.MySimpleModel.class;
		Class<?> rmm = RequiredModelMBean.class;
		// =========bean的方法===============
		Method getNameMethod = myBean.getMethod("getName", new Class[0]);
		Method setNameMethod = myBean.getMethod("setName", new Class[] { String.class });
		Method getMmpMethod = myBean.getMethod("getMmp", new Class[0]);
		Method setMmpMethod = myBean.getMethod("setMmp", new Class[] { Integer[].class });
		Method resetMethod = myBean.getMethod("reset", new Class[0]);

		Method setManagedResource = rmm.getMethod("setManagedResource", new Class[] { Object.class, String.class });

		Method sendNotification = rmm.getMethod("sendNotification", new Class[] { Notification.class });
		Method addAttributeChangeNL = rmm.getMethod("addAttributeChangeNotificationListener",
				new Class[] { NotificationListener.class, String.class, Object.class });
		// ==============name属性====================
		DescriptorSupport nameDesc = new DescriptorSupport();
		nameDesc.setField("name", "name");
		nameDesc.setField("descriptorType", "attribute");
		// nameDesc.setField("default","我modelMBean属性name的名字");
		nameDesc.setField("displayName", "显示name属性的名字");
		nameDesc.setField("getMethod", "getName");
		nameDesc.setField("setMethod", "setName");
		ModelMBeanAttributeInfo nameAttrInfo = new ModelMBeanAttributeInfo("name", "modelMBean的 name属性信息",
				getNameMethod, setNameMethod, nameDesc);
		// ==============mmp属性======================
		DescriptorSupport mmpDesc = new DescriptorSupport();
		mmpDesc.setField("name", "mmp");
		mmpDesc.setField("descriptorType", "attribute");
		nameDesc.setField("default", "我modelMBean属性name的名字");
		mmpDesc.setField("displayName", "displayName:mmp");
		mmpDesc.setField("getMethod", "getMmp");
		mmpDesc.setField("setMethod", "setMmp");
		ModelMBeanAttributeInfo mmpAttrInfo = new ModelMBeanAttributeInfo("mmp", "modelMBean的mmp属性信息", getMmpMethod,
				setMmpMethod, mmpDesc);
		// ===========myField源类中不存在的属性===============
		DescriptorSupport newAttr = new DescriptorSupport();
		newAttr.setField("name", "myField");
		newAttr.setField("descriptorType", "attribute");
		newAttr.setField("displayName", "displayName:myField");
		newAttr.setField("currencyTimeLimit", "" + Integer.MAX_VALUE);
		ModelMBeanAttributeInfo newAttrInfo = new ModelMBeanAttributeInfo("myField", "java.lang.String", "我的新造属性", true,
				true, false, newAttr);

		ModelMBeanAttributeInfo[] attrs = new ModelMBeanAttributeInfo[] { nameAttrInfo, mmpAttrInfo, newAttrInfo };

		// =======构造器============
		// ===空参构造器==================
		Constructor<?> constructor = myBean.getConstructor(new Class[0]);
		ModelMBeanConstructorInfo con0 = new ModelMBeanConstructorInfo("MySimpleModel-model空参构造器的描述", constructor);
		// =========有参构造器=========
		MBeanParameterInfo namePara = new MBeanParameterInfo("name", String.class.getName(), "有参构造器name参数");
		MBeanParameterInfo mmpPara = new MBeanParameterInfo("mmp", Integer[].class.getName(), "有参构造器mmp参数");
		MBeanParameterInfo[] paras = new MBeanParameterInfo[] { mmpPara, namePara };
		DescriptorSupport conDesc = new DescriptorSupport();
		conDesc.setField("name", "MySimpleModel(String name, Integer[] mmp)");
		conDesc.setField("descriptorType", "operation");
		conDesc.setField("role", "constructor");
		conDesc.setField("displayName", "构造器显示的名字");
		ModelMBeanConstructorInfo pCons = new ModelMBeanConstructorInfo("MySimpleModel(String name, Integer[] mmp)",
				"有参构造器", paras, conDesc);

		ModelMBeanConstructorInfo[] conss = new ModelMBeanConstructorInfo[] { con0, pCons };
		// ==================operations==================
		ModelMBeanOperationInfo getName = new ModelMBeanOperationInfo("getName", getNameMethod);

		DescriptorSupport setMmpDesc = new DescriptorSupport();
		setMmpDesc.setField("name", "setName");
		setMmpDesc.setField("descriptorType", "operation");
		setMmpDesc.setField("displayName", "setName的方法");
		setMmpDesc.setField("role", "setter");
		setMmpDesc.setField("targetType", "ObjectReference");
		MBeanParameterInfo setNamePara = new MBeanParameterInfo("name", String.class.getName(), "输入最新名字");
		MBeanParameterInfo[] setNameParas = new MBeanParameterInfo[] { setNamePara };
		ModelMBeanOperationInfo setName = new ModelMBeanOperationInfo("setName", "设置新的名字", setNameParas,
				void.class.getName(), MBeanOperationInfo.ACTION, setMmpDesc);

		ModelMBeanOperationInfo getMmp = new ModelMBeanOperationInfo("getMmp", getMmpMethod);
		ModelMBeanOperationInfo setMmp = new ModelMBeanOperationInfo("setMmp", setMmpMethod);

		ModelMBeanOperationInfo reset = new ModelMBeanOperationInfo("resetMethod", resetMethod);
		// ==============mul源对象的一个方法===================
		DescriptorSupport mulDesc = new DescriptorSupport();
		mulDesc.setField("name", "mul");
		mulDesc.setField("descriptorType", "operation");
		mulDesc.setField("displayName", "mul:加法");
		mulDesc.setField("calss", "modelMbean.beans.MySimpleModel");
		mulDesc.setField("role", "operation");
		mulDesc.setField("targetType", "ObjectReference");
		// 参数列表只要是对应的参数就可以了,不需要两个double
		MBeanParameterInfo one = new MBeanParameterInfo("one", double.class.getName(), "第一个参数");
		MBeanParameterInfo two = new MBeanParameterInfo("two", double.class.getName(), "第二个参数");
		MBeanParameterInfo[] pars = new MBeanParameterInfo[] { one, one };
		ModelMBeanOperationInfo mul = new ModelMBeanOperationInfo("mul", "两个数相加", pars, int.class.getName(),
				MBeanOperationInfo.UNKNOWN, mulDesc);

		// ===========requireModelMBean的方法，由于他注册到server中，invoke它的方法需要先注册=====================
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
				"我的第一个ModelMBean的描述", attrs, conss, operations, notis);
		// 新建mbean对应服务器的名字
		ObjectName objectName = new ObjectName("myFirstModelMBean:name=MySimpleModel");
		// 注册到服务器

		MySimpleModel model = new MySimpleModel();
		RequiredModelMBean bean = new RequiredModelMBean(modelMBeanInfo);
		bean.setManagedResource(model, "objectReference");
		// 新建一个属性监听器
		NotificationListener lister = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object handback) {
				Object source = notification.getSource();
				System.out.println(Thread.currentThread().getName() + "---myListener--"+handback+"--" + source);
			}
		};
		// 添加通知监听器
		NotificationFilterSupport filter = new NotificationFilterSupport();
		//属性变更通知器就是看filter是否有这个type。但是具体的属性就没有甄别,
		filter.enableType("jmx.attribute.change");//这样lister就能监听属性的变更
		bean.addNotificationListener(lister, filter, "handBack");
		// 添加属性变更通知监听器,只要属性发生变更，就会有RequiredModelMBean的方法senAttributeChangeNotification
		bean.addAttributeChangeNotificationListener(lister, "name", "inhandback");
		// 将bean注册到服务器
		ObjectInstance registerMBean = server.registerMBean(bean, objectName);

		// 在服务器注册的requireModelMBean中的接口方法可以invoke
		Object invoke = server.invoke(objectName, "mul", new Object[] { 3.5, 5.6 },
				new String[] { double.class.getName(), double.class.getName() });
		System.out.println(invoke);


		// 添加属性变更监听器后，变更属性就会触发事件
		server.setAttribute(objectName, new Attribute("name", "server set new name"));
//		ModelMBean的setAttribute会遍历AttributeChangeNotificationListener和NotificationListener。所以像上面的做法，对于name的属性改变将引起这两个监听器
		
//		这里invoke是可以实际的改变对应的值，没有触发监听器，是因为没有sendNotification
		Object invoke2 = server.invoke(objectName, "setName", new Object[] { "safafadsf" },
				new String[] { String.class.getName() });
		System.out.println(invoke2);
		
//		模拟发送属性变更notification
		Notification notification = new Notification(AttributeChangeNotification.ATTRIBUTE_CHANGE,bean,8441l,"AttributeChangeDetected");
		bean.sendNotification(notification);//这样会触发Notification监听器lister
		
		
		
		// =====下面是一种更常用的方法，对象均有服务器管理===============
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
