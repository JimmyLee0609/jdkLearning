package permission;

import java.awt.AWTPermission;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.io.SerializablePermission;
import java.lang.management.ManagementPermission;
import java.lang.reflect.ReflectPermission;
import java.net.NetPermission;
import java.net.SocketPermission;
import java.net.URLPermission;
import java.nio.file.LinkPermission;
import java.security.AllPermission;
import java.security.SecurityPermission;
import java.security.UnresolvedPermission;
import java.sql.SQLPermission;
import java.util.PropertyPermission;
import java.util.logging.LoggingPermission;

import javax.management.MBeanPermission;
import javax.management.MBeanServerPermission;
import javax.management.MBeanTrustPermission;
import javax.management.remote.SubjectDelegationPermission;
import javax.security.auth.AuthPermission;
import javax.security.auth.PrivateCredentialPermission;
import javax.security.auth.kerberos.DelegationPermission;
import javax.security.auth.kerberos.ServicePermission;
import javax.xml.bind.JAXBPermission;
import javax.xml.ws.WebServicePermission;

import security.acc.AccessControlTest;

public class PermissionTest {

	@SuppressWarnings({ "unused" })
	public static void main(String[] args) throws IOException {
//		ȫ�����,��������
		AllPermission allPermission = new AllPermission();
			new PermissionTest().testAccessControl();
		
		FilePermission filePermission = new FilePermission("·��","read,write,delete,execute");
		URL();
		socket();
		PrivateCredential();
		basicPermission();
		service();
		Mbean();
		new UnresolvedPermission("", null, null, null);//������
	}

	private static void PrivateCredential() {
		new PrivateCredentialPermission("CredentialClass {PrincipalClass \"PrincipalName\"}* ","read");		
		new PrivateCredentialPermission("com.sun.PrivateCredential com.sun.Principal \"duke\"", "read");		
	}

	private static void socket() {
		new SocketPermission("puffin.eng.sun.com:7777","accept,listen");
		new SocketPermission("localhost:1024-","accept,listen");//�˿ں�1024-��������
		/*name
		 * host = (hostname | IPv4address | iPv6reference) [:portrange]
			    portrange = portnumber | -portnumber | portnumber-[portnumber]*/

		/*Action:
		  accept
		 connect
		 listen
		 resolve*/
		
	}

	private static void URL() {
		new URLPermission("scheme : // authority [ / path ]","POST");		
		new URLPermission("http://www.oracle.com/a/b/c.html","POST");	//ָ���ļ�	
		new URLPermission("http://www.oracle.com/a/b/*","POST");		//ָ��·���µ��ļ�
		new URLPermission("http://www.oracle.com/a/b/-","POST,GET");		//ָ��·���µ��ļ����ļ��еĵݹ�
		/* 		authority = [ userinfo @ ] hostrange [ : portrange ]
			     portrange = portnumber | -portnumber | portnumber-[portnumber] | *
			     hostrange = ([*.] dnsname) | IPv4address | IPv6address
			 */
	/*Action ������	"POST,GET,DELETE"
        "GET:X-Foo-Request,X-Bar-Request"
        "POST,GET:Header1,Header2"*/

	}

	private static void service() {
		new ServicePermission("krbtgt/EXAMPLE.COM@EXAMPLE.COM", "initiate");
		new ServicePermission("krbtgt/EXAMPLE.COM@EXAMPLE.COM", "accept");		
	}

	@SuppressWarnings("unused")
	private static void Mbean() {
		MBeanPermission mBeanPermission = new MBeanPermission("className#member[objectName] ","getAttribute");
		/*addNotificationListener
		getAttribute
		getClassLoader
		getClassLoaderFor
		getClassLoaderRepository
		getDomains
		getMBeanInfo
		getObjectInstance
		instantiate
		invoke
		isInstanceOf
		queryMBeans
		queryNames
		registerMBean
		removeNotificationListener
		setAttribute
		unregisterMBean*/		
//		basic
		MBeanServerPermission mBeanServerPermission = new MBeanServerPermission("findMBeanServer", "");//
		MBeanTrustPermission mBeanTrustPermission = new MBeanTrustPermission("register", null);
	}

	@SuppressWarnings("unused")
	private static void basicPermission() {

		// ����Permission
		AuthPermission authPermission = new AuthPermission("createLoginContext.{name}", "setPolicy");//api��һ����ɣ����ڵ�½��
		AWTPermission awtPermission = new AWTPermission("watchMousePointer", "");//api����һ�����
		DelegationPermission delegationPermission = new DelegationPermission(
				"\"host/foo.example.com@EXAMPLE.COM\" \"krbtgt/EXAMPLE.COM@EXAMPLE.COM\"");
//		ת�����
		 
		// DynamicAccessPermission permission = new DynamicAccessPermission("");
		// ���ܽ�������
		// InquireSecContextPermission inquireSecContextPermission = new
		// InquireSecContextPermission("");���ܼ�
		
		JAXBPermission jaxbPermission = new JAXBPermission("setDatatypeConverter");//����javabean�󶨵�
		LinkPermission linkPermission = new LinkPermission("hard", null);//�ſ��ļ�ϵͳ�ķ������ƣ���Ҫ��������
		LoggingPermission loggingPermission = new LoggingPermission("control", null);//������־Ȩ�ޣ��鿴�޸�
		ManagementPermission managementPermission = new ManagementPermission("monitor", null);//���������Ȩ��
		
		NetPermission netPermission = new NetPermission("getResponseCache", "");//api��һ��
//		ϵͳ����
		PropertyPermission propertyPermission = new PropertyPermission("java.home", "read");//ϵͳ����
//		����
		ReflectPermission reflectPermission = new ReflectPermission("suppressAccessChecks");//˽������Ҳ�ܷ���
		ReflectPermission reflectPermission2= new ReflectPermission("newProxyInPackage.{������}");//�ڱ��������˽�еİ�Ҳ���Է���
//		Runtime
		RuntimePermission runtimePermission = new RuntimePermission("setIO");//api��һ�ѣ������﷨��Ȩ�����ȡ�
//		��ȫ���
		SecurityPermission securityPermission = new SecurityPermission("getPolicy");//api��һ��
//		���л�
		SerializablePermission serializablePermission = new SerializablePermission("enableSubclassImplementation");//ObjectInputStream ��ObjectOutputStream ��������д���л������л���ʵ��
		SerializablePermission serializablePermission2 = new SerializablePermission("enableSubstitution");//�����л��ڼ���������һ�������滻
		SerializablePermission serializablePermission3 = new SerializablePermission("serialFilter");//ΪObjectInputStreams����Filter
		
		// new SnmpPermission("","");������
//		SQL���ݿ�
		SQLPermission sqlPermission = new SQLPermission("setLog");//������־
		SQLPermission sqlPermission2 = new SQLPermission("callAbort");//�������abort����
		SQLPermission sqlPermission3 = new SQLPermission("setSyncFactory");//����ʹ��SyncFactory��������setJNDIContext ������־ setLogger
		SQLPermission sqlPermission4 = new SQLPermission("setNetworkTimeout");//����ʹ�����ӳ�ʱ
		SQLPermission sqlPermission5 = new SQLPermission("deregisterDriver");//�������ӹ���Աȡ��ע��Driver
		
		SubjectDelegationPermission subjectDelegationPermission = new SubjectDelegationPermission("javax.management.remote.JMXPrincipal.delegate", null);//û�㶮
		WebServicePermission webServicePermission = new WebServicePermission("publishEndpoint");//û�㶮
		
	}

	@SuppressWarnings("unused")
	private void testAccessControl() {
		// �������಻�������Ŀ�У���������л�Ŀ¼��һ�£����������������Ŀ������
		// ���Ե���AccessControlTest�е�����,����Ĵ����������
		AccessControlTest acc = new AccessControlTest();
		acc.testAcc();
		// û�и��������Ȩ�ޣ�����Ĵ���ͻ����쳣
		File file3 = new File("D:\\new\\testPolicy\\test1.txt");
		file3.isDirectory();
	}

}
