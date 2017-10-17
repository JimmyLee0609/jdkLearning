package lang;

import java.io.FileDescriptor;
import java.io.Reader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Permission;

public class SecurityManagerTest {

	public static void main(String[] args) throws UnknownHostException {
//		全是检查的   $ javahome$ \jre\lib\security\java.policy  文件记录是否有权执行,可以修改文件来修改权限
//		如果setSecurityManager(..)可用,就可以重新设置安全管理器.     
//		JVM启动时 默认是不启动安全管理器的...-Djava.security.manager 来指定策略文件,未指定就在上面的目录
//		下面的检查如果有权限就继续,没权限就抛安全异常
		SecurityManager securityManager = System.getSecurityManager();
		SecurityManager manager = new SecurityManager();
		boolean inCheck = manager.getInCheck();
		ThreadGroup threadGroup = manager.getThreadGroup();
		Object securityContext = manager.getSecurityContext();
		manager.checkWrite("file");
		manager.checkWrite(FileDescriptor.in);//?
		boolean checkTopLevelWindow = manager.checkTopLevelWindow(null);
		manager.checkSystemClipboardAccess();
		manager.checkSetFactory();
		manager.checkSecurityAccess("target");
		manager.checkRead("file", securityContext);
		manager.checkRead("file");
		manager.checkRead(FileDescriptor.in);
		manager.checkPropertyAccess("key");
		manager.checkPropertiesAccess();
		manager.checkPrintJobAccess();
		manager.checkPermission(new RuntimePermission(""), null);
		manager.checkPackageDefinition("pkg");
		manager.checkPackageAccess("pkg");
		manager.checkMulticast(InetAddress.getLocalHost(), (byte)5);
		manager.checkMulticast(InetAddress.getLocalHost());
		manager.checkMemberAccess(Reader.class, 5);
		manager.checkListen(596);
		manager.checkLink("lib");
		manager.checkExit(0);
		manager.checkExec("cmd");
		manager.checkDelete("file");
		manager.checkCreateClassLoader();
		manager.checkConnect("host", 8080, securityContext);
		manager.checkConnect("host", 8080);
		manager.checkAwtEventQueueAccess();
		manager.checkAccess(Thread.currentThread().getThreadGroup());
		manager.checkAccess(Thread.currentThread());
		manager.checkAccept("Host", 8080);
		System.setSecurityManager(manager);
	}

}
