package tjava.managerment;

import java.lang.Thread.State;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.lang.management.MonitorInfo;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.PlatformLoggingMXBean;
import java.lang.management.PlatformManagedObject;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ManagementFactoryTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// classloaderBean();
		// compliationMXBean();
		// garbageCollectorMXBean();
		// memoryManagerMXBean();
//		memoryMXBean();
		memoryPoolMXBean();
		operatingSystemMXBean();
		
		platformManagerInterface();
		
		platformMBeanServer();
		
//		����ʵ�ָ�����mxbeanInterface��ƽ̨MXBean����mxbeanInterfaceָ����Java������о���һ����һʵ����
		OperatingSystemMXBean platformMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
//		OperatingSystemMXBean platformMXBean2 = ManagementFactory.getPlatformMXBean(MBeanServer Connection,OperatingSystemMXBean.class);
		
		
//		������Java�������ʵ�ָ���mxbeanInterface��ƽ̨MXBean���б�
		List<OperatingSystemMXBean> platformMXBeans = ManagementFactory.getPlatformMXBeans(OperatingSystemMXBean.class);
//		List<OperatingSystemMXBean> platformMXBeans2 = ManagementFactory.getPlatformMXBeans(MBeanServer Connection,OperatingSystemMXBean.class);
		
		
		runtimeMXBean();

		threadMXBean();

		platformLoggingMXBean();
		
		
		// ���ظ���MXBean���Ƶ�ƽ̨MXBean�ӿڵĴ����ýӿ�ͨ��������MBeanServerConnectionת���䷽�����á�
		// ManagementFactory.newPlatformMXBeanProxy(connection, mxbeanName,
		// mxbeanInterface)

	}

	@SuppressWarnings("unused")
	private static void platformLoggingMXBean() {
		PlatformLoggingMXBean log = ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class);		
//		���ص�ǰע��ļ�¼�����Ƶ��б� �˷�������LogManager.getLoggerNames���������ؼ�¼�����Ƶ��б�
		List<String> loggerNames = log.getLoggerNames();
//		��ȡbean������
		ObjectName objectName = log.getObjectName();//java.util.logging:type=Logging
		
//		��ȡ��ָ����¼����������־��������ơ� ���ָ���ļ�¼�������ڣ��򷵻�null�� 
//		�˷������Ȳ��Ҹ������Ƶļ�¼����Ȼ��ͨ�����������������־��������ƣ�
		String loggerLevel = log.getLoggerLevel(loggerNames.get(1));
//		����ָ����¼���ĸ�������ơ� ���ָ���ļ�¼�������ڣ��򷵻�null�� 
//		���ָ���ļ�¼�������ƿռ��еĸ�Logger����������һ�����ַ�����
		String parentLoggerName = log.getParentLoggerName(loggerNames.get(1));
//		��ָ���ļ�¼������Ϊָ�����¼��� ���levelName��Ϊ�գ���ָ���ļ�¼���ļ�������Ϊ��levelNameƥ����ѷ������� 
//		���levelNameΪnull����ָ���ļ�¼���ļ�������Ϊnull�����Ҽ�¼������Ч����Ӿ����ض�����null������ֵ����������ȼ̳С�
		log.setLoggerLevel("global", "INFO");
	   /* ��־����	SEVERE�����ֵ��
						    WARNING
						    INFO
						    CONFIG
						    FINE
						    FINER
						    FINEST�����ֵ�� */
	String string = log.toString();
	}

	@SuppressWarnings("unused")
	private static void threadMXBean() {
//		����Java��������߳�ϵͳ���й�bean��
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		
//		�������л���߳�ID
		long[] allThreadIds = threadMXBean.getAllThreadIds();
		
//		���Ҵ�������״̬���߳�ID
		long[] findDeadlockedThreads = threadMXBean.findDeadlockedThreads();
//		���Ҵ��ڼ������������߳�DI,����a �м�����J����, �ȴ�B�ͷż�����J,  ����B�м�����K,�߳�����,�ȴ�A�ͷ�K   
		long[] findMonitorDeadlockedThreads = threadMXBean.findMonitorDeadlockedThreads();
		
//		���ص�ǰ�̵߳���CPUʱ�䣨������Ϊ��λ����
		long currentThreadCpuTime = threadMXBean.getCurrentThreadCpuTime();
//		���ص�ǰ�߳����û�ģʽ���Ժ�΢��ִ�е�CPUʱ�䡣
		long currentThreadUserTime = threadMXBean.getCurrentThreadUserTime();
//		��ȡ��ĺ�̨�߳�����
		int daemonThreadCount = threadMXBean.getDaemonThreadCount();
//		��ȡbean������
		ObjectName objectName = threadMXBean.getObjectName();//java.lang:type=Threading
		
//		������Java������������ֵ���������Ļ�߳�������߷�ֵ��
		int peakThreadCount = threadMXBean.getPeakThreadCount();
//		���ص�ǰ��̵߳������������ػ��̺߳ͷ��ػ��̡߳�
		int threadCount = threadMXBean.getThreadCount();
		
//		����Java������Ƿ�֧�ֵ�ǰ�̵߳�CPUʱ�������
		boolean currentThreadCpuTimeSupported = threadMXBean.isCurrentThreadCpuTimeSupported();
//		�����Ƿ������߳�CPUʱ�������
		boolean threadCpuTimeEnabled = threadMXBean.isThreadCpuTimeEnabled();
//		����Java������Ƿ�֧���߳����ü��ӡ�
		boolean threadContentionMonitoringSupported = threadMXBean.isThreadContentionMonitoringSupported();
//		�����Ƿ������߳����ü��ӡ�
		boolean threadContentionMonitoringEnabled = threadMXBean.isThreadContentionMonitoringEnabled();
//		����Java������Ƿ�֧�ּ��ӿ�ӵ�е�ͬ����ʹ�������
		boolean synchronizerUsageSupported = threadMXBean.isSynchronizerUsageSupported();
//		����Java������Ƿ�֧�ּ��Ӷ����������ʹ�������
		boolean objectMonitorUsageSupported = threadMXBean.isObjectMonitorUsageSupported();
//		����Java�����ʵ���Ƿ�֧���κ��̵߳�CPUʱ�������
		boolean threadCpuTimeSupported = threadMXBean.isThreadCpuTimeSupported();
//		����ֵ�߳�������Ϊ��ǰ��߳�����
		threadMXBean.resetPeakThreadCount();
//		���û�����߳����ü��ӡ�
		threadMXBean.setThreadContentionMonitoringEnabled(threadContentionMonitoringEnabled);
//		���û�����߳�CPUʱ�����
		threadMXBean.setThreadCpuTimeEnabled(threadCpuTimeEnabled);
		
//		������Java��������������������������߳�������
		long totalStartedThreadCount = threadMXBean.getTotalStartedThreadCount();
		
//		����ָ��ID���̵߳���CPUʱ�䣨������Ϊ��λ����
		long threadCpuTime = threadMXBean.getThreadCpuTime(Thread.currentThread().getId());
		
//		ʹ�ö�ջ���ٺ�ͬ����Ϣ�������л�̵߳��߳���Ϣ�� ���ص������а�����ĳЩ�߳̿����ڴ˷�������ʱ����ֹ��
//�˷�������getThreadInfo��long []��boolean��boolean��������ָ����ThreadInfo��������顣
		ThreadInfo[] dumpAllThreads = threadMXBean.dumpAllThreads(false, false);
		
//		�����߳�ID��ȡ�߳�info,
		ThreadInfo threadInfo2 = threadMXBean.getThreadInfo(Thread.currentThread().getId(), 50);
		threadInfo(threadInfo2);
		
		
		ThreadInfo[] threadInfo = threadMXBean.getThreadInfo(new long[] {}, false, false);
		
		
	}

	@SuppressWarnings("unused")
	private static void threadInfo(ThreadInfo threadInfo2) {
//		===ThreadInfo �ľ�����Ϣ===
//		�������ThreadInfo�������̱߳���ֹ��������½�����������ܴ�����
		long blockedCount = threadInfo2.getBlockedCount();//0
//		�������ThreadInfo�������߳������Խ�������½���������Ľ����ۻ�����ʱ�䣨�Ժ���Ϊ��λ������Ϊ�������߳����ü��ӡ�
		long blockedTime = threadInfo2.getBlockedTime();//-1
//		����ӵ�����ThreadInfo�������̱߳���ֹ�ȴ��Ķ�����̵߳����ơ�
		String lockOwnerName = threadInfo2.getLockOwnerName();//null
//		�������ThreadInfo�������̵߳�ID��
		long threadId = threadInfo2.getThreadId();//1
//		�������ThreadInfo�������̵߳����ơ�
		String threadName = threadInfo2.getThreadName();//main
//		�������ThreadInfo�������̱߳���ֹ�ȴ��Ķ�����ַ�����ʾ��ʽ��
		String lockName = threadInfo2.getLockName();//null
//		����ӵ�����ThreadInfo�������̱߳���ֹ�ȴ��Ķ�����̵߳�ID��
		long lockOwnerId = threadInfo2.getLockOwnerId();//-1
//		�������ThreadInfo�������̵߳�״̬��
		State threadState = threadInfo2.getThreadState();//RUNNABLE
//		�������ThreadInfo�������߳��Ƿ�ͨ��Java Native Interface��JNI��ִ�б������롣
		boolean inNative = threadInfo2.isInNative();//false
//		����һ��MonitorInfo�������飬ÿ�������ʾһ���������������ǰ�����ThreadInfo�������߳�����
		MonitorInfo[] lockedMonitors = threadInfo2.getLockedMonitors();
//		����һ��LockInfo�������飬ÿ�������ʾһ����ǰ�����ThreadInfo�������߳������Ŀ�ӵ�е�ͬ������
		LockInfo[] lockedSynchronizers = threadInfo2.getLockedSynchronizers();
//		�������ThreadInfo�������̱߳���ֹ�ȴ��Ķ����LockInfo��
		LockInfo lockInfo = threadInfo2.getLockInfo();//null
//		�������ThreadInfo�������̵߳ȴ�֪ͨ���ܴ�����
		long waitedCount = threadInfo2.getWaitedCount();//0
//		���߳����ü������ú󣬷������ThreadInfo�������߳��ѵȴ�֪ͨ�Ľ����ۻ�����ʱ�䣨�Ժ���Ϊ��λ����
		long waitedTime = threadInfo2.getWaitedTime();//-1
//		�������ThreadInfo�������߳��Ƿ����
		boolean suspended = threadInfo2.isSuspended();//false
//		�������ThreadInfo�������̵߳Ķ�ջ���١�
		StackTraceElement[] stackTrace = threadInfo2.getStackTrace();
		
//		��һ�����ӵ�Bean�л�ȡ�߳���Ϣ
//		ThreadInfo.from(compositeData)
	}

	@SuppressWarnings("unused")
	private static void runtimeMXBean() {
//		����Java�����������ʱϵͳ���й�bean��
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();	
//		��ȡJVM������ʱ����,eclipse ��debugģʽ��,��������,�������������������
//		[-agentlib:jdwp=transport=dt_socket,suspend=y,address=localhost:54719, -Dfile.encoding=GBK]
		List<String> inputArguments = runtimeMXBean.getInputArguments();
//		������·��
//		D:\Program Files\JRE\lib\resources.jar;D:\Program Files\JRE\lib\rt.jar;D:\Program Files\JRE\lib\sunrsasign.jar;D:\Program Files\JRE\lib\jsse.jar;D:\Program Files\JRE\lib\jce.jar;D:\Program Files\JRE\lib\charsets.jar;D:\Program Files\JRE\lib\jfr.jar;D:\Program Files\JRE\classes
		String bootClassPath = runtimeMXBean.getBootClassPath();
//		��·��
//		D:\Program Files\JRE\lib\resources.jar;D:\Program Files\JRE\lib\rt.jar;D:\Program Files\JRE\lib\jsse.jar;D:\Program Files\JRE\lib\jce.jar;D:\Program Files\JRE\lib\charsets.jar;D:\Program Files\JRE\lib\jfr.jar;D:\Program Files\JRE\lib\ext\access-bridge-64.jar;D:\Program Files\JRE\lib\ext\cldrdata.jar;D:\Program Files\JRE\lib\ext\dnsns.jar;D:\Program Files\JRE\lib\ext\jaccess.jar;D:\Program Files\JRE\lib\ext\jfxrt.jar;D:\Program Files\JRE\lib\ext\localedata.jar;D:\Program Files\JRE\lib\ext\nashorn.jar;D:\Program Files\JRE\lib\ext\sunec.jar;D:\Program Files\JRE\lib\ext\sunjce_provider.jar;D:\Program Files\JRE\lib\ext\sunmscapi.jar;D:\Program Files\JRE\lib\ext\sunpkcs11.jar;D:\Program Files\JRE\lib\ext\zipfs.jar;D:\eclipse-workspace\JMXLearning\bin;D:\eclipse-workspace\JMXLearning\lib\jmxri.jar;D:\eclipse-workspace\JMXLearning\lib\jmxtools.jar
		String classPath = runtimeMXBean.getClassPath();
//		���·��,����native�ȵĴ����·����Ҫ����
//		D:\Program Files\JRE\bin;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;D:\Program Files\JRE\bin;D:/Program Files/JRE/bin/server;D:/Program Files/JRE/bin;D:/Program Files/JRE/lib/amd64;C:\ProgramData\Oracle\Java\javapath;C:\Program Files (x86)\Intel\iCLS Client\;C:\Program Files\Intel\iCLS Client\;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files\Intel\IntelSGXPSW\bin\x64\Release\;C:\Program Files\Intel\IntelSGXPSW\bin\win32\Release\;c:\Program Files (x86)\ATI Technologies\ATI.ACE\Core-Static;%JAVA_HOME%\bin;C:\Users\JimmyLee\AppData\Local\Microsoft\WindowsApps;C:\Users\JimmyLee\AppData\Local\GitHubDesktop\bin;D:\java tool\apache-maven-3.5.2\bin;D:\java tool\apache-tomcat-8.5.27\bin;D:\java tool\gradle-4.5-all\gradle-4.5\bin;;D:\eclipse\eclipse;;
		String libraryPath = runtimeMXBean.getLibraryPath();
//		JVM������ʱ��
		long startTime = runtimeMXBean.getStartTime();
//		JVM������ʱ��
		long uptime = runtimeMXBean.getUptime();
//		�����������е�Java�����ʵ�ֵĹ���ӿڵĹ淶�汾��
		String managementSpecVersion = runtimeMXBean.getManagementSpecVersion();//1.2
//		���ر�ʾ�������е�Java����������ơ� ���ص������ַ���������������ַ�����Java�����ʵ�ֿ���ѡ���ض���ƽ̨��������ϢǶ�뵽���ص������ַ����С� ÿ���������е�����������в�ͬ�����ơ�
		String name = runtimeMXBean.getName();//1528@DESKTOP-7SAG66J
//		����bean������
		ObjectName objectName = runtimeMXBean.getObjectName();//java.lang:type=Runtime
//		����JVM�ı�׼����
		String specName = runtimeMXBean.getSpecName();//Java Virtual Machine Specification
//		����java������淶��Ӧ��
		String specVendor = runtimeMXBean.getSpecVendor();//Oracle Corporation
//		����JVM�İ汾
		String specVersion = runtimeMXBean.getSpecVersion();//1.8
//		ϵͳ����
//		{sun.desktop=windows, awt.toolkit=sun.awt.windows.WToolkit, file.encoding.pkg=sun.io, java.specification.version=1.8, sun.cpu.isalist=amd64, sun.jnu.encoding=GBK, java.class.path=D:\Program Files\JRE\lib\resources.jar;D:\Program Files\JRE\lib\rt.jar;D:\Program Files\JRE\lib\jsse.jar;D:\Program Files\JRE\lib\jce.jar;D:\Program Files\JRE\lib\charsets.jar;D:\Program Files\JRE\lib\jfr.jar;D:\Program Files\JRE\lib\ext\access-bridge-64.jar;D:\Program Files\JRE\lib\ext\cldrdata.jar;D:\Program Files\JRE\lib\ext\dnsns.jar;D:\Program Files\JRE\lib\ext\jaccess.jar;D:\Program Files\JRE\lib\ext\jfxrt.jar;D:\Program Files\JRE\lib\ext\localedata.jar;D:\Program Files\JRE\lib\ext\nashorn.jar;D:\Program Files\JRE\lib\ext\sunec.jar;D:\Program Files\JRE\lib\ext\sunjce_provider.jar;D:\Program Files\JRE\lib\ext\sunmscapi.jar;D:\Program Files\JRE\lib\ext\sunpkcs11.jar;D:\Program Files\JRE\lib\ext\zipfs.jar;D:\eclipse-workspace\JMXLearning\bin;D:\eclipse-workspace\JMXLearning\lib\jmxri.jar;D:\eclipse-workspace\JMXLearning\lib\jmxtools.jar, java.vm.vendor=Oracle Corporation, sun.arch.data.model=64, user.variant=, java.vendor.url=http://java.oracle.com/, user.timezone=, os.name=Windows 10, java.vm.specification.version=1.8, user.country=CN, sun.java.launcher=SUN_STANDARD, sun.boot.library.path=D:\Program Files\JRE\bin, sun.java.command=com.factory.ManagementFactoryTest, sun.cpu.endian=little, user.home=C:\Users\JimmyLee, user.language=zh, java.specification.vendor=Oracle Corporation, java.home=D:\Program Files\JRE, file.separator=\, line.separator=
		Map<String, String> systemProperties = runtimeMXBean.getSystemProperties();
//		��ȡJVMʵ������
		String vmName = runtimeMXBean.getVmName();//Java HotSpot(TM) 64-Bit Server VM
//		��ȡʵ��JVM�Ĺ�Ӧ��
		String vmVendor = runtimeMXBean.getVmVendor();//Oracle Corporation
//		����JVM�İ汾
		String vmVersion = runtimeMXBean.getVmVersion();//25.162-b12
		String string = runtimeMXBean.toString();
		
	}

	private static void platformMBeanServer() {
		/*����ƽ̨MBeanServer���ڵ�һ�ε��ô˷���ʱ��������ͨ������MBeanServerFactory.createMBeanServer��������ƽ̨MBeanServer��
		 * ��ʹ����ObjectName�ڴ�ƽ̨MBeanServer��ע��ÿ��ƽ̨��MXBean��
		 * ��������ں����ĵ����У�ֻ�᷵�����������ƽ̨MBeanServer��

		����̬���������ٵ�MXBean�������ڴ�غ͹����������Զ�ע�ᵽMBeanServerƽ̨��ȡ��ע�ᡣ

		���������ϵͳ����javax.management.builder.initial����ƽ̨MBeanServer�Ĵ�������ָ����MBeanServerBuilder��ɡ�

		�����ƽ̨MBeanServerҲ����ע���ƽ̨MXBeans֮�������Ӧ�ó����йܵ�Bean��
		�⽫��������MBeanͨ����ͬ��MBeanServer��������˿��Ը����ɵؽ������緢���ͷ��֡�
		������ƽ̨MXBeans�ĳ�ͻӦ�ñ��⡣*/
		MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();	
		String defaultDomain = platformMBeanServer.getDefaultDomain();
		
		
		
	}

	@SuppressWarnings("unused")
	private static void platformManagerInterface() {
//		����PlatformManagedObject��Class������ӽӿڼ��ϣ���ʾ���ڼ��Ӻ͹���Javaƽ̨�����й���ӿڡ�
		Set<Class<? extends PlatformManagedObject>> platformManagementInterfaces = ManagementFactory.getPlatformManagementInterfaces();		
	for (Class<? extends PlatformManagedObject> class1 : platformManagementInterfaces) {
		String name = class1.getName();
		String genericString = class1.toGenericString();
	}
	
	}

	@SuppressWarnings("unused")
	private static void operatingSystemMXBean() {
//		��������Java������Ĳ���ϵͳ���й�Bean��
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		
//		���ز���ϵͳ��ϵ�ṹ�� ���������ͬ��System.getProperty����os.arch������
		String arch = operatingSystemMXBean.getArch();//amd64
		
//		���ز���ϵͳ���ơ� ���������ͬ��System.getProperty����os.name������
		String name = operatingSystemMXBean.getName();//Windows 10
		
//		���ز���ϵͳ�汾�� ���������ͬ��System.getProperty����os.version������
		String version = operatingSystemMXBean.getVersion();//10.1
		
		
//		���ؿ�����Java������Ĵ����������� ���������ͬ��Runtime.availableProcessors����������
//���ض�������������ڼ䣬���ֵ���ܻ�ı䡣
		int availableProcessors = operatingSystemMXBean.getAvailableProcessors();//4
		
//		����bean������
		ObjectName objectName = operatingSystemMXBean.getObjectName();//java.lang:type=OperatingSystem
		
		
		/*�������һ���ӵ�ϵͳƽ�����ء� ϵͳ����ƽ��ֵ���Ŷӵ����ô������Ŀ�����ʵ�����������һ��ʱ����ƽ���ڿ��ô����������еĿ�����ʵ����������ܺ͡� ���㸺��ƽ��ֵ�ķ�ʽ���ض��ڲ���ϵͳ�ģ���ͨ������ʱ��Ӱ���ƽ��ֵ��
		���ƽ���غɲ����ã��򷵻ظ�ֵ��
		�˷���ּ���ṩ�й�ϵͳ���ص���ʾ�������ܻᾭ����ѯ�� ��ʵʩ���ַ����İ���ƽ̨�ϣ�ƽ�����ؿ��ܲ�����*/
		double systemLoadAverage = operatingSystemMXBean.getSystemLoadAverage();//-1.0

		String string = operatingSystemMXBean.toString();
	}

	@SuppressWarnings("unused")
	private static void memoryPoolMXBean() {
		List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
			// ����Java�����������ѵ�Ŭ�������մ��ڴ����δʹ�õĶ���֮����ڴ�ʹ�������
			// �˷�����Ҫ��Java�����ִ�г������Զ��ڴ����������κ��������ա� ���Java�������֧�ִ˷�������˷�������null��
			MemoryUsage collectionUsage = memoryPoolMXBean.getCollectionUsage();

			// ���Դ��ڴ���Ƿ�֧�ּ���ʹ����ֵ��
			boolean usageThresholdSupported = memoryPoolMXBean.isUsageThresholdSupported();
			if (usageThresholdSupported) {
//			���ֽ�Ϊ��λ���ش��ڴ�ص�ʹ����ֵ�� ÿ���ڴ�ض���һ��������ƽ̨��Ĭ����ֵ�� 
//			��ǰʹ����ֵ����ͨ��setUsageThreshold�������ġ�
				long usageThreshold = memoryPoolMXBean.getUsageThreshold();
//			������ڴ��֧��ʹ����ֵ���򽫴��ڴ�ص���ֵ����Ϊ������ֵ�� 
//			�����ֵ����Ϊ��ֵ�����ڴ��ڴ��������ʹ������ֵ�����顣 �������Ϊ�㣬�����ʹ������ֵ�����顣
				memoryPoolMXBean.setUsageThreshold(usageThreshold);
				// ���Դ��ڴ�ص��ڴ�ʹ�����Ƿ�ﵽ�򳬹���ʹ����ֵ��
				boolean usageThresholdExceeded = memoryPoolMXBean.isUsageThresholdExceeded();
//			�����ڴ�ʹ���ʳ���ʹ������ֵ�Ĵ�����
				long usageThresholdCount = memoryPoolMXBean.getUsageThresholdCount();
				String string = memoryPoolMXBean.toString();
			}
			
			
			// ���Դ�Java�ڴ������ռ����ڴ�ʹ������Ƿ�ﵽ�򳬹������ռ�ʹ����ֵ��
			// �˷�����Ҫ��Java�����ִ�г������Զ��ڴ����������κ��������ա�
			boolean collectionUsageThresholdSupported = memoryPoolMXBean.isCollectionUsageThresholdSupported();
			if(collectionUsageThresholdSupported) {
//				���ֽ�Ϊ��λ���ش��ڴ�صļ���ʹ����ֵ�� Ĭ��ֵ���㡣 ����ʹ����ֵ����ͨ��setCollectionUsageThreshold�������и��ġ�
				long collectionUsageThreshold = memoryPoolMXBean.getCollectionUsageThreshold();
				memoryPoolMXBean.setCollectionUsageThreshold(collectionUsageThreshold);
//				����Java�������⵽�ڴ�ʹ�����Ѵﵽ�򳬹�����ʹ����ֵ�Ĵ�����
				long collectionUsageThresholdCount = memoryPoolMXBean.getCollectionUsageThresholdCount();
//			���Դ�Java�ڴ������ռ����ڴ�ʹ������Ƿ�ﵽ�򳬹������ռ�ʹ����ֵ��
//			�˷�����Ҫ��Java�����ִ�г������Զ��ڴ����������κ��������ա�
				boolean collectionUsageThresholdExceeded = memoryPoolMXBean.isCollectionUsageThresholdExceeded();
				String string = memoryPoolMXBean.toString();
			}
			
			
			// ���Դ��ڴ����Java��������Ƿ���Ч�� һ��Java�����������ڴ�ϵͳ��ɾ�����ڴ�ؽ���Ϊ��Ч��
			boolean valid = memoryPoolMXBean.isValid();
//			���ع�����ڴ�ص��ڴ�����������ơ� ÿ���ڴ�ؽ�������һ���ڴ���������й���
			String[] memoryManagerNames = memoryPoolMXBean.getMemoryManagerNames();
//			���ر�ʾ���ڴ�ص����ơ�
			String name = memoryPoolMXBean.getName();
//			����bean������
			ObjectName objectName = memoryPoolMXBean.getObjectName();
			
//			��Java������������ֵ�������������ش��ڴ�صķ�ֵ�ڴ�ʹ������ ������ڴ����Ч�������ٴ��ڣ�����˷�������null��
//			MBeanServer���ʣ�
//			MemoryUsage��ӳ�������Ǿ���MemoryUsage��ָ�������Ե�CompositeData��
			MemoryUsage peakUsage = memoryPoolMXBean.getPeakUsage();
			
//			�����ڴ�صķ�ֵ�ڴ�ʹ����ͳ��ֵ����Ϊ��ǰ���ڴ�ʹ������
			memoryPoolMXBean.resetPeakUsage();
			
//			���ش��ڴ�ص����͡�heap?
			MemoryType type = memoryPoolMXBean.getType();
			
			
/*���ش��ڴ�ص��ڴ�ʹ������Ĺ���ֵ�� ������ڴ����Ч�������ٴ��ڣ�����˷�������null��
�˷�������Java������������ƴ��ڴ�صĵ�ǰ�ڴ�ʹ������� 
����һЩ�ڴ�أ����ַ���������һ������Ĳ�������ҪһЩ������ȷ�����ơ� һ��ʵ��Ӧ�ü�¼����������¡�
�˷������ڼ���ϵͳ�ڴ�ʹ�����������ڴ治�������
MBeanServer���ʣ�
MemoryUsage��ӳ�������Ǿ���MemoryUsage��ָ�������Ե�CompositeData��*/
			MemoryUsage usage = memoryPoolMXBean.getUsage();
			
			

			String string = memoryPoolMXBean.toString();
		}
	}

	@SuppressWarnings("unused")
	private static void memoryMXBean() {
		// ��ȡJVM���ڴ�ϵͳ��MXBean
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		// ��ȡheap���ڴ��ʹ�����
		// init = 67108864(65536K) used = 1678264(1638K) committed = 64487424(62976K)
		// max = 937951232(915968K)
		MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		// ��ȡ�Ƕ��ڴ��ʹ�����
		// init = 2555904(2496K) used = 4502824(4397K) committed = 8060928(7872K) max =
		// -1(-1K)
		MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
		// ��ȡ MXBean������ java.lang:type=Memory
		ObjectName objectName = memoryMXBean.getObjectName();
		// ����������ɵĶ���Ľ���������
		int objectPendingFinalizationCount = memoryMXBean.getObjectPendingFinalizationCount();// 0
		// �Ƿ�����verboseģʽ
		boolean verbose = memoryMXBean.isVerbose();// false
		memoryMXBean.setVerbose(verbose);
		// ִ��GC
		memoryMXBean.gc();

		// ��Ӽ�����,��Ҫ�õ�ʵ����,ʵ������sunʵ����,�ò���

	}

	@SuppressWarnings("unused")
	private static void memoryManagerMXBean() {
		List<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();
		for (MemoryManagerMXBean memoryManagerMXBean : memoryManagerMXBeans) {
			// ��ȡ��������������ڴ������
			String[] memoryPoolNames = memoryManagerMXBean.getMemoryPoolNames();
			// ��ȡ�ڴ������������
			String name = memoryManagerMXBean.getName();
			// ��ȡmxbean������
			ObjectName objectName = memoryManagerMXBean.getObjectName();
			// ���Դ��ڴ��������Java��������Ƿ���Ч�� һ��Java�����������ڴ�ϵͳ��ɾ�����ڴ�������ͻ�ʧЧ��
			boolean valid = memoryManagerMXBean.isValid();
			String string = memoryManagerMXBean.toString();

		}
	}

	@SuppressWarnings("unused")
	private static void garbageCollectorMXBean() {
		// ��ȡ�����ռ�����MXBean
		List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
			// ��JVM��ʼ��,��ȡ�����ռ��Ĵ���
			long collectionCount = garbageCollectorMXBean.getCollectionCount();
			// ��ȡ�ռ����ۼ�ʱ��
			long collectionTime = garbageCollectorMXBean.getCollectionTime();
			// ��ȡ�����ռ��� ���ڴ����� [PS Eden Space, PS Survivor Space, PS Old Gen]
			String[] memoryPoolNames = garbageCollectorMXBean.getMemoryPoolNames();
			// ���������ռ��������� PS MarkSweep
			String name = garbageCollectorMXBean.getName();
			// ����MXBean������ java.lang:type=GarbageCollector,name=PS MarkSweep
			ObjectName objectName = garbageCollectorMXBean.getObjectName();
			// ���Դ��ڴ��������Java��������Ƿ���Ч�� һ��Java�����������ڴ�ϵͳ��ɾ�����ڴ�������ͻ�ʧЧ��
			boolean valid = garbageCollectorMXBean.isValid();// true
			garbageCollectorMXBean.toString();
		}
	}

	@SuppressWarnings("unused")
	private static void compliationMXBean() {
		// ����java���������ϵͳ���й�bean,���java�����û�б���ϵͳ,�ͷ���null
		CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
		// ����JVM�ı��������� HotSpot 64-Bit Tiered Compilers,
		String name = compilationMXBean.getName();
		// �����ۼƵı���ʱ��
		long totalCompilationTime = compilationMXBean.getTotalCompilationTime();
		// ����bean ������ java.lang:type=Compilation
		ObjectName objectName = compilationMXBean.getObjectName();
		// ����JVM�Ƿ�֧�ּ��ӱ��� true
		boolean compilationTimeMonitoringSupported = compilationMXBean.isCompilationTimeMonitoringSupported();
	}

	@SuppressWarnings("unused")
	private static void classloaderBean() {
		// ����java������������ϵͳ���й�bean
		ClassLoadingMXBean classLoading = ManagementFactory.getClassLoadingMXBean();
		// ��ȡ���ص���ļ���
		int loadedClassCount = classLoading.getLoadedClassCount();
		// ��ȡ bean������ java.lang:type=ClassLoading
		ObjectName objectName = classLoading.getObjectName();
		// ��ȡ�ۼƼ�����ļ���
		long totalLoadedClassCount = classLoading.getTotalLoadedClassCount();
		// ��ȡж����ļ���
		long unloadedClassCount = classLoading.getUnloadedClassCount();
		// verboseģʽ�Ƿ��
		boolean verbose = classLoading.isVerbose();
		classLoading.setVerbose(false);
	}

}
