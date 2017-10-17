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
//		全部许可,谨慎赋予
		AllPermission allPermission = new AllPermission();
			new PermissionTest().testAccessControl();
		
		FilePermission filePermission = new FilePermission("路径","read,write,delete,execute");
		URL();
		socket();
		PrivateCredential();
		basicPermission();
		service();
		Mbean();
		new UnresolvedPermission("", null, null, null);//不会用
	}

	private static void PrivateCredential() {
		new PrivateCredentialPermission("CredentialClass {PrincipalClass \"PrincipalName\"}* ","read");		
		new PrivateCredentialPermission("com.sun.PrivateCredential com.sun.Principal \"duke\"", "read");		
	}

	private static void socket() {
		new SocketPermission("puffin.eng.sun.com:7777","accept,listen");
		new SocketPermission("localhost:1024-","accept,listen");//端口号1024-不设上限
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
		new URLPermission("http://www.oracle.com/a/b/c.html","POST");	//指定文件	
		new URLPermission("http://www.oracle.com/a/b/*","POST");		//指定路径下的文件
		new URLPermission("http://www.oracle.com/a/b/-","POST,GET");		//指定路径下的文件及文件夹的递归
		/* 		authority = [ userinfo @ ] hostrange [ : portrange ]
			     portrange = portnumber | -portnumber | portnumber-[portnumber] | *
			     hostrange = ([*.] dnsname) | IPv4address | IPv6address
			 */
	/*Action 可以是	"POST,GET,DELETE"
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

		// 创建Permission
		AuthPermission authPermission = new AuthPermission("createLoginContext.{name}", "setPolicy");//api中一堆许可，关于登陆的
		AWTPermission awtPermission = new AWTPermission("watchMousePointer", "");//api中有一堆许可
		DelegationPermission delegationPermission = new DelegationPermission(
				"\"host/foo.example.com@EXAMPLE.COM\" \"krbtgt/EXAMPLE.COM@EXAMPLE.COM\"");
//		转发许可
		 
		// DynamicAccessPermission permission = new DynamicAccessPermission("");
		// 不能建？？？
		// InquireSecContextPermission inquireSecContextPermission = new
		// InquireSecContextPermission("");不能键
		
		JAXBPermission jaxbPermission = new JAXBPermission("setDatatypeConverter");//关于javabean绑定的
		LinkPermission linkPermission = new LinkPermission("hard", null);//放开文件系统的访问限制，需要谨慎开放
		LoggingPermission loggingPermission = new LoggingPermission("control", null);//开放日志权限，查看修改
		ManagementPermission managementPermission = new ManagementPermission("monitor", null);//关于虚拟机权限
		
		NetPermission netPermission = new NetPermission("getResponseCache", "");//api中一堆
//		系统属性
		PropertyPermission propertyPermission = new PropertyPermission("java.home", "read");//系统属性
//		反射
		ReflectPermission reflectPermission = new ReflectPermission("suppressAccessChecks");//私有属性也能反射
		ReflectPermission reflectPermission2= new ReflectPermission("newProxyInPackage.{包名称}");//在保护域或者私有的包也可以反射
//		Runtime
		RuntimePermission runtimePermission = new RuntimePermission("setIO");//api中一堆，不过语法的权限优先。
//		安全许可
		SecurityPermission securityPermission = new SecurityPermission("getPolicy");//api中一堆
//		序列化
		SerializablePermission serializablePermission = new SerializablePermission("enableSubclassImplementation");//ObjectInputStream 或ObjectOutputStream 的子类重写序列化或反序列化的实现
		SerializablePermission serializablePermission2 = new SerializablePermission("enableSubstitution");//在序列化期间允许用另一个对象替换
		SerializablePermission serializablePermission3 = new SerializablePermission("serialFilter");//为ObjectInputStreams设置Filter
		
		// new SnmpPermission("","");不能用
//		SQL数据库
		SQLPermission sqlPermission = new SQLPermission("setLog");//设置日志
		SQLPermission sqlPermission2 = new SQLPermission("callAbort");//允许调用abort方法
		SQLPermission sqlPermission3 = new SQLPermission("setSyncFactory");//允许使用SyncFactory方法设置setJNDIContext 设置日志 setLogger
		SQLPermission sqlPermission4 = new SQLPermission("setNetworkTimeout");//允许使用连接超时
		SQLPermission sqlPermission5 = new SQLPermission("deregisterDriver");//允许连接管理员取消注册Driver
		
		SubjectDelegationPermission subjectDelegationPermission = new SubjectDelegationPermission("javax.management.remote.JMXPrincipal.delegate", null);//没搞懂
		WebServicePermission webServicePermission = new WebServicePermission("publishEndpoint");//没搞懂
		
	}

	@SuppressWarnings("unused")
	private void testAccessControl() {
		// 如果这个类不在这个项目中，代码的运行基目录不一致，但是类似添加了项目依赖，
		// 可以调用AccessControlTest中的内容,下面的代码可以运行
		AccessControlTest acc = new AccessControlTest();
		acc.testAcc();
		// 没有赋予这个类权限，下面的代码就会抛异常
		File file3 = new File("D:\\new\\testPolicy\\test1.txt");
		file3.isDirectory();
	}

}
