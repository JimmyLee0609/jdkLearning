package tjavax.managerment;


import java.lang.management.RuntimeMXBean;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.JMX;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServerConnection;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;

public class JMXTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String originalTypeField = JMX.ORIGINAL_TYPE_FIELD;//originalType
		String defaultValueField = JMX.DEFAULT_VALUE_FIELD;//defaultValue
		String immutableInfoField = JMX.IMMUTABLE_INFO_FIELD;// immutableInfo 
		String interfaceClassNameField = JMX.INTERFACE_CLASS_NAME_FIELD;// interfaceClassName 
		String legalValuesField = JMX.LEGAL_VALUES_FIELD;// legalValues 
		String maxValueField = JMX.MAX_VALUE_FIELD;//  maxValue  
		String minValueField = JMX.MIN_VALUE_FIELD;// minValue 
		String mxbeanField = JMX.MXBEAN_FIELD;// minValue 
		String openTypeField = JMX.OPEN_TYPE_FIELD;// openType 
		String originalTypeField2 = JMX.ORIGINAL_TYPE_FIELD;// originalType 
		
		boolean mxBeanInterface = JMX.isMXBeanInterface(RuntimeMXBean.class);
//		JMX.newMXBeanProxy(MBeanServerConnection, objectName, interfaceClass, notificationEmitter);
		
	}

}
