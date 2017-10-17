package lang;

import java.io.FileDescriptor;
import java.io.Reader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Permission;

public class SecurityManagerTest {

	public static void main(String[] args) throws UnknownHostException {
//		ȫ�Ǽ���   $ javahome$ \jre\lib\security\java.policy  �ļ���¼�Ƿ���Ȩִ��,�����޸��ļ����޸�Ȩ��
//		���setSecurityManager(..)����,�Ϳ����������ð�ȫ������.     
//		JVM����ʱ Ĭ���ǲ�������ȫ��������...-Djava.security.manager ��ָ�������ļ�,δָ�����������Ŀ¼
//		����ļ�������Ȩ�޾ͼ���,ûȨ�޾��װ�ȫ�쳣
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
