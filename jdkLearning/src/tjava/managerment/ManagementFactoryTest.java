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
		
//		返回实现给定的mxbeanInterface的平台MXBean，该mxbeanInterface指定在Java虚拟机中具有一个单一实例。
		OperatingSystemMXBean platformMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
//		OperatingSystemMXBean platformMXBean2 = ManagementFactory.getPlatformMXBean(MBeanServer Connection,OperatingSystemMXBean.class);
		
		
//		返回在Java虚拟机中实现给定mxbeanInterface的平台MXBean的列表。
		List<OperatingSystemMXBean> platformMXBeans = ManagementFactory.getPlatformMXBeans(OperatingSystemMXBean.class);
//		List<OperatingSystemMXBean> platformMXBeans2 = ManagementFactory.getPlatformMXBeans(MBeanServer Connection,OperatingSystemMXBean.class);
		
		
		runtimeMXBean();

		threadMXBean();

		platformLoggingMXBean();
		
		
		// 返回给定MXBean名称的平台MXBean接口的代理，该接口通过给定的MBeanServerConnection转发其方法调用。
		// ManagementFactory.newPlatformMXBeanProxy(connection, mxbeanName,
		// mxbeanInterface)

	}

	@SuppressWarnings("unused")
	private static void platformLoggingMXBean() {
		PlatformLoggingMXBean log = ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class);		
//		返回当前注册的记录器名称的列表。 此方法调用LogManager.getLoggerNames（）并返回记录器名称的列表。
		List<String> loggerNames = log.getLoggerNames();
//		获取bean的名字
		ObjectName objectName = log.getObjectName();//java.util.logging:type=Logging
		
//		获取与指定记录器关联的日志级别的名称。 如果指定的记录器不存在，则返回null。 
//		此方法首先查找给定名称的记录器，然后通过调用以下命令返回日志级别的名称：
		String loggerLevel = log.getLoggerLevel(loggerNames.get(1));
//		返回指定记录器的父项的名称。 如果指定的记录器不存在，则返回null。 
//		如果指定的记录器是名称空间中的根Logger，则结果将是一个空字符串。
		String parentLoggerName = log.getParentLoggerName(loggerNames.get(1));
//		将指定的记录器设置为指定的新级别。 如果levelName不为空，则指定的记录器的级别设置为与levelName匹配的已分析级别。 
//		如果levelName为null，则指定的记录器的级别设置为null，并且记录器的有效级别从具有特定（非null）级别值的最近的祖先继承。
		log.setLoggerLevel("global", "INFO");
	   /* 日志级别	SEVERE（最高值）
						    WARNING
						    INFO
						    CONFIG
						    FINE
						    FINER
						    FINEST（最低值） */
	String string = log.toString();
	}

	@SuppressWarnings("unused")
	private static void threadMXBean() {
//		返回Java虚拟机的线程系统的托管bean。
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		
//		返回所有活动的线程ID
		long[] allThreadIds = threadMXBean.getAllThreadIds();
		
//		查找处于死锁状态的线程ID
		long[] findDeadlockedThreads = threadMXBean.findDeadlockedThreads();
//		查找处于监视器死锁的线程DI,例子a 有监视器J锁定, 等待B释放监视器J,  但是B有监视器K,线程锁定,等待A释放K   
		long[] findMonitorDeadlockedThreads = threadMXBean.findMonitorDeadlockedThreads();
		
//		返回当前线程的总CPU时间（以纳秒为单位）。
		long currentThreadCpuTime = threadMXBean.getCurrentThreadCpuTime();
//		返回当前线程在用户模式下以毫微秒执行的CPU时间。
		long currentThreadUserTime = threadMXBean.getCurrentThreadUserTime();
//		获取活动的后台线程数量
		int daemonThreadCount = threadMXBean.getDaemonThreadCount();
//		获取bean的名字
		ObjectName objectName = threadMXBean.getObjectName();//java.lang:type=Threading
		
//		返回自Java虚拟机启动或峰值重置以来的活动线程数的最高峰值。
		int peakThreadCount = threadMXBean.getPeakThreadCount();
//		返回当前活动线程的数量，包括守护线程和非守护线程。
		int threadCount = threadMXBean.getThreadCount();
		
//		测试Java虚拟机是否支持当前线程的CPU时间测量。
		boolean currentThreadCpuTimeSupported = threadMXBean.isCurrentThreadCpuTimeSupported();
//		测试是否启用线程CPU时间测量。
		boolean threadCpuTimeEnabled = threadMXBean.isThreadCpuTimeEnabled();
//		测试Java虚拟机是否支持线程争用监视。
		boolean threadContentionMonitoringSupported = threadMXBean.isThreadContentionMonitoringSupported();
//		测试是否启用线程争用监视。
		boolean threadContentionMonitoringEnabled = threadMXBean.isThreadContentionMonitoringEnabled();
//		测试Java虚拟机是否支持监视可拥有的同步器使用情况。
		boolean synchronizerUsageSupported = threadMXBean.isSynchronizerUsageSupported();
//		测试Java虚拟机是否支持监视对象监视器的使用情况。
		boolean objectMonitorUsageSupported = threadMXBean.isObjectMonitorUsageSupported();
//		测试Java虚拟机实现是否支持任何线程的CPU时间测量。
		boolean threadCpuTimeSupported = threadMXBean.isThreadCpuTimeSupported();
//		将峰值线程数重置为当前活动线程数。
		threadMXBean.resetPeakThreadCount();
//		启用或禁用线程争用监视。
		threadMXBean.setThreadContentionMonitoringEnabled(threadContentionMonitoringEnabled);
//		启用或禁用线程CPU时间测量
		threadMXBean.setThreadCpuTimeEnabled(threadCpuTimeEnabled);
		
//		返回自Java虚拟机启动以来创建并启动的线程总数。
		long totalStartedThreadCount = threadMXBean.getTotalStartedThreadCount();
		
//		返回指定ID的线程的总CPU时间（以纳秒为单位）。
		long threadCpuTime = threadMXBean.getThreadCpuTime(Thread.currentThread().getId());
		
//		使用堆栈跟踪和同步信息返回所有活动线程的线程信息。 返回的数组中包含的某些线程可能在此方法返回时被终止。
//此方法返回getThreadInfo（long []，boolean，boolean）方法中指定的ThreadInfo对象的数组。
		ThreadInfo[] dumpAllThreads = threadMXBean.dumpAllThreads(false, false);
		
//		根据线程ID获取线程info,
		ThreadInfo threadInfo2 = threadMXBean.getThreadInfo(Thread.currentThread().getId(), 50);
		threadInfo(threadInfo2);
		
		
		ThreadInfo[] threadInfo = threadMXBean.getThreadInfo(new long[] {}, false, false);
		
		
	}

	@SuppressWarnings("unused")
	private static void threadInfo(ThreadInfo threadInfo2) {
//		===ThreadInfo 的具体信息===
//		返回与此ThreadInfo关联的线程被阻止进入或重新进入监视器的总次数。
		long blockedCount = threadInfo2.getBlockedCount();//0
//		返回与此ThreadInfo关联的线程阻塞以进入或重新进入监视器的近似累积已用时间（以毫秒为单位），因为已启用线程争用监视。
		long blockedTime = threadInfo2.getBlockedTime();//-1
//		返回拥有与此ThreadInfo关联的线程被阻止等待的对象的线程的名称。
		String lockOwnerName = threadInfo2.getLockOwnerName();//null
//		返回与此ThreadInfo关联的线程的ID。
		long threadId = threadInfo2.getThreadId();//1
//		返回与此ThreadInfo关联的线程的名称。
		String threadName = threadInfo2.getThreadName();//main
//		返回与此ThreadInfo关联的线程被阻止等待的对象的字符串表示形式。
		String lockName = threadInfo2.getLockName();//null
//		返回拥有与此ThreadInfo关联的线程被阻止等待的对象的线程的ID。
		long lockOwnerId = threadInfo2.getLockOwnerId();//-1
//		返回与此ThreadInfo关联的线程的状态。
		State threadState = threadInfo2.getThreadState();//RUNNABLE
//		测试与此ThreadInfo关联的线程是否通过Java Native Interface（JNI）执行本机代码。
		boolean inNative = threadInfo2.isInNative();//false
//		返回一个MonitorInfo对象数组，每个对象表示一个对象监视器，当前由与此ThreadInfo关联的线程锁定
		MonitorInfo[] lockedMonitors = threadInfo2.getLockedMonitors();
//		返回一个LockInfo对象数组，每个对象表示一个当前由与此ThreadInfo关联的线程锁定的可拥有的同步器。
		LockInfo[] lockedSynchronizers = threadInfo2.getLockedSynchronizers();
//		返回与此ThreadInfo关联的线程被阻止等待的对象的LockInfo。
		LockInfo lockInfo = threadInfo2.getLockInfo();//null
//		返回与此ThreadInfo关联的线程等待通知的总次数。
		long waitedCount = threadInfo2.getWaitedCount();//0
//		从线程争用监视启用后，返回与此ThreadInfo关联的线程已等待通知的近似累积已用时间（以毫秒为单位）。
		long waitedTime = threadInfo2.getWaitedTime();//-1
//		测试与此ThreadInfo关联的线程是否挂起。
		boolean suspended = threadInfo2.isSuspended();//false
//		返回与此ThreadInfo关联的线程的堆栈跟踪。
		StackTraceElement[] stackTrace = threadInfo2.getStackTrace();
		
//		从一个复杂的Bean中获取线程信息
//		ThreadInfo.from(compositeData)
	}

	@SuppressWarnings("unused")
	private static void runtimeMXBean() {
//		返回Java虚拟机的运行时系统的托管bean。
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();	
//		获取JVM的运行时参数,eclipse 的debug模式下,参数如下,可以在启动参数中添加
//		[-agentlib:jdwp=transport=dt_socket,suspend=y,address=localhost:54719, -Dfile.encoding=GBK]
		List<String> inputArguments = runtimeMXBean.getInputArguments();
//		启动的路径
//		D:\Program Files\JRE\lib\resources.jar;D:\Program Files\JRE\lib\rt.jar;D:\Program Files\JRE\lib\sunrsasign.jar;D:\Program Files\JRE\lib\jsse.jar;D:\Program Files\JRE\lib\jce.jar;D:\Program Files\JRE\lib\charsets.jar;D:\Program Files\JRE\lib\jfr.jar;D:\Program Files\JRE\classes
		String bootClassPath = runtimeMXBean.getBootClassPath();
//		类路径
//		D:\Program Files\JRE\lib\resources.jar;D:\Program Files\JRE\lib\rt.jar;D:\Program Files\JRE\lib\jsse.jar;D:\Program Files\JRE\lib\jce.jar;D:\Program Files\JRE\lib\charsets.jar;D:\Program Files\JRE\lib\jfr.jar;D:\Program Files\JRE\lib\ext\access-bridge-64.jar;D:\Program Files\JRE\lib\ext\cldrdata.jar;D:\Program Files\JRE\lib\ext\dnsns.jar;D:\Program Files\JRE\lib\ext\jaccess.jar;D:\Program Files\JRE\lib\ext\jfxrt.jar;D:\Program Files\JRE\lib\ext\localedata.jar;D:\Program Files\JRE\lib\ext\nashorn.jar;D:\Program Files\JRE\lib\ext\sunec.jar;D:\Program Files\JRE\lib\ext\sunjce_provider.jar;D:\Program Files\JRE\lib\ext\sunmscapi.jar;D:\Program Files\JRE\lib\ext\sunpkcs11.jar;D:\Program Files\JRE\lib\ext\zipfs.jar;D:\eclipse-workspace\JMXLearning\bin;D:\eclipse-workspace\JMXLearning\lib\jmxri.jar;D:\eclipse-workspace\JMXLearning\lib\jmxtools.jar
		String classPath = runtimeMXBean.getClassPath();
//		类库路径,包括native等的代码的路径都要包括
//		D:\Program Files\JRE\bin;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;D:\Program Files\JRE\bin;D:/Program Files/JRE/bin/server;D:/Program Files/JRE/bin;D:/Program Files/JRE/lib/amd64;C:\ProgramData\Oracle\Java\javapath;C:\Program Files (x86)\Intel\iCLS Client\;C:\Program Files\Intel\iCLS Client\;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files\Intel\IntelSGXPSW\bin\x64\Release\;C:\Program Files\Intel\IntelSGXPSW\bin\win32\Release\;c:\Program Files (x86)\ATI Technologies\ATI.ACE\Core-Static;%JAVA_HOME%\bin;C:\Users\JimmyLee\AppData\Local\Microsoft\WindowsApps;C:\Users\JimmyLee\AppData\Local\GitHubDesktop\bin;D:\java tool\apache-maven-3.5.2\bin;D:\java tool\apache-tomcat-8.5.27\bin;D:\java tool\gradle-4.5-all\gradle-4.5\bin;;D:\eclipse\eclipse;;
		String libraryPath = runtimeMXBean.getLibraryPath();
//		JVM的启动时间
		long startTime = runtimeMXBean.getStartTime();
//		JVM的在线时间
		long uptime = runtimeMXBean.getUptime();
//		返回正在运行的Java虚拟机实现的管理接口的规范版本。
		String managementSpecVersion = runtimeMXBean.getManagementSpecVersion();//1.2
//		返回表示正在运行的Java虚拟机的名称。 返回的名称字符串可以是任意的字符串，Java虚拟机实现可以选择将特定于平台的有用信息嵌入到返回的名称字符串中。 每个正在运行的虚拟机可以有不同的名称。
		String name = runtimeMXBean.getName();//1528@DESKTOP-7SAG66J
//		返回bean的名字
		ObjectName objectName = runtimeMXBean.getObjectName();//java.lang:type=Runtime
//		返回JVM的标准名字
		String specName = runtimeMXBean.getSpecName();//Java Virtual Machine Specification
//		返回java虚拟机规范供应商
		String specVendor = runtimeMXBean.getSpecVendor();//Oracle Corporation
//		返回JVM的版本
		String specVersion = runtimeMXBean.getSpecVersion();//1.8
//		系统属性
//		{sun.desktop=windows, awt.toolkit=sun.awt.windows.WToolkit, file.encoding.pkg=sun.io, java.specification.version=1.8, sun.cpu.isalist=amd64, sun.jnu.encoding=GBK, java.class.path=D:\Program Files\JRE\lib\resources.jar;D:\Program Files\JRE\lib\rt.jar;D:\Program Files\JRE\lib\jsse.jar;D:\Program Files\JRE\lib\jce.jar;D:\Program Files\JRE\lib\charsets.jar;D:\Program Files\JRE\lib\jfr.jar;D:\Program Files\JRE\lib\ext\access-bridge-64.jar;D:\Program Files\JRE\lib\ext\cldrdata.jar;D:\Program Files\JRE\lib\ext\dnsns.jar;D:\Program Files\JRE\lib\ext\jaccess.jar;D:\Program Files\JRE\lib\ext\jfxrt.jar;D:\Program Files\JRE\lib\ext\localedata.jar;D:\Program Files\JRE\lib\ext\nashorn.jar;D:\Program Files\JRE\lib\ext\sunec.jar;D:\Program Files\JRE\lib\ext\sunjce_provider.jar;D:\Program Files\JRE\lib\ext\sunmscapi.jar;D:\Program Files\JRE\lib\ext\sunpkcs11.jar;D:\Program Files\JRE\lib\ext\zipfs.jar;D:\eclipse-workspace\JMXLearning\bin;D:\eclipse-workspace\JMXLearning\lib\jmxri.jar;D:\eclipse-workspace\JMXLearning\lib\jmxtools.jar, java.vm.vendor=Oracle Corporation, sun.arch.data.model=64, user.variant=, java.vendor.url=http://java.oracle.com/, user.timezone=, os.name=Windows 10, java.vm.specification.version=1.8, user.country=CN, sun.java.launcher=SUN_STANDARD, sun.boot.library.path=D:\Program Files\JRE\bin, sun.java.command=com.factory.ManagementFactoryTest, sun.cpu.endian=little, user.home=C:\Users\JimmyLee, user.language=zh, java.specification.vendor=Oracle Corporation, java.home=D:\Program Files\JRE, file.separator=\, line.separator=
		Map<String, String> systemProperties = runtimeMXBean.getSystemProperties();
//		获取JVM实现名字
		String vmName = runtimeMXBean.getVmName();//Java HotSpot(TM) 64-Bit Server VM
//		获取实现JVM的供应商
		String vmVendor = runtimeMXBean.getVmVendor();//Oracle Corporation
//		返回JVM的版本
		String vmVersion = runtimeMXBean.getVmVersion();//25.162-b12
		String string = runtimeMXBean.toString();
		
	}

	private static void platformMBeanServer() {
		/*返回平台MBeanServer。在第一次调用此方法时，它首先通过调用MBeanServerFactory.createMBeanServer方法创建平台MBeanServer，
		 * 并使用其ObjectName在此平台MBeanServer中注册每个平台的MXBean。
		 * 这个方法在后续的调用中，只会返回最初创建的平台MBeanServer。

		将动态创建和销毁的MXBean（例如内存池和管理器）将自动注册到MBeanServer平台并取消注册。

		如果设置了系统属性javax.management.builder.initial，则平台MBeanServer的创建将由指定的MBeanServerBuilder完成。

		建议此平台MBeanServer也用于注册除平台MXBeans之外的其他应用程序托管的Bean。
		这将允许所有MBean通过相同的MBeanServer发布，因此可以更轻松地进行网络发布和发现。
		名称与平台MXBeans的冲突应该避免。*/
		MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();	
		String defaultDomain = platformMBeanServer.getDefaultDomain();
		
		
		
	}

	@SuppressWarnings("unused")
	private static void platformManagerInterface() {
//		返回PlatformManagedObject的Class对象的子接口集合，表示用于监视和管理Java平台的所有管理接口。
		Set<Class<? extends PlatformManagedObject>> platformManagementInterfaces = ManagementFactory.getPlatformManagementInterfaces();		
	for (Class<? extends PlatformManagedObject> class1 : platformManagementInterfaces) {
		String name = class1.getName();
		String genericString = class1.toGenericString();
	}
	
	}

	@SuppressWarnings("unused")
	private static void operatingSystemMXBean() {
//		返回运行Java虚拟机的操作系统的托管Bean。
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		
//		返回操作系统体系结构。 这个方法等同于System.getProperty（“os.arch”）。
		String arch = operatingSystemMXBean.getArch();//amd64
		
//		返回操作系统名称。 这个方法等同于System.getProperty（“os.name”）。
		String name = operatingSystemMXBean.getName();//Windows 10
		
//		返回操作系统版本。 这个方法等同于System.getProperty（“os.version”）。
		String version = operatingSystemMXBean.getVersion();//10.1
		
		
//		返回可用于Java虚拟机的处理器数量。 这个方法等同于Runtime.availableProcessors（）方法。
//在特定的虚拟机调用期间，这个值可能会改变。
		int availableProcessors = operatingSystemMXBean.getAvailableProcessors();//4
		
//		返回bean的名字
		ObjectName objectName = operatingSystemMXBean.getObjectName();//java.lang:type=OperatingSystem
		
		
		/*返回最后一分钟的系统平均负载。 系统负载平均值是排队到可用处理器的可运行实体的数量与在一段时间内平均在可用处理器上运行的可运行实体的数量的总和。 计算负载平均值的方式是特定于操作系统的，但通常是受时间影响的平均值。
		如果平均载荷不可用，则返回负值。
		此方法旨在提供有关系统负载的提示，并可能会经常查询。 在实施这种方法的昂贵平台上，平均负载可能不可用*/
		double systemLoadAverage = operatingSystemMXBean.getSystemLoadAverage();//-1.0

		String string = operatingSystemMXBean.toString();
	}

	@SuppressWarnings("unused")
	private static void memoryPoolMXBean() {
		List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
			// 返回Java虚拟机最近花费的努力来回收此内存池中未使用的对象之后的内存使用情况。
			// 此方法不要求Java虚拟机执行除正常自动内存管理以外的任何垃圾回收。 如果Java虚拟机不支持此方法，则此方法返回null。
			MemoryUsage collectionUsage = memoryPoolMXBean.getCollectionUsage();

			// 测试此内存池是否支持集合使用阈值。
			boolean usageThresholdSupported = memoryPoolMXBean.isUsageThresholdSupported();
			if (usageThresholdSupported) {
//			以字节为单位返回此内存池的使用阈值。 每个内存池都有一个依赖于平台的默认阈值。 
//			当前使用阈值可以通过setUsageThreshold方法更改。
				long usageThreshold = memoryPoolMXBean.getUsageThreshold();
//			如果此内存池支持使用阈值，则将此内存池的阈值设置为给定阈值。 
//			如果阈值设置为正值，则在此内存池中启用使用率阈值交叉检查。 如果设置为零，则禁用使用率阈值交叉检查。
				memoryPoolMXBean.setUsageThreshold(usageThreshold);
				// 测试此内存池的内存使用量是否达到或超过其使用阈值。
				boolean usageThresholdExceeded = memoryPoolMXBean.isUsageThresholdExceeded();
//			返回内存使用率超过使用率阈值的次数。
				long usageThresholdCount = memoryPoolMXBean.getUsageThresholdCount();
				String string = memoryPoolMXBean.toString();
			}
			
			
			// 测试此Java内存池最近收集的内存使用情况是否达到或超过了其收集使用阈值。
			// 此方法不要求Java虚拟机执行除正常自动内存管理以外的任何垃圾回收。
			boolean collectionUsageThresholdSupported = memoryPoolMXBean.isCollectionUsageThresholdSupported();
			if(collectionUsageThresholdSupported) {
//				以字节为单位返回此内存池的集合使用阈值。 默认值是零。 集合使用阈值可以通过setCollectionUsageThreshold方法进行更改。
				long collectionUsageThreshold = memoryPoolMXBean.getCollectionUsageThreshold();
				memoryPoolMXBean.setCollectionUsageThreshold(collectionUsageThreshold);
//				返回Java虚拟机检测到内存使用量已达到或超过集合使用阈值的次数。
				long collectionUsageThresholdCount = memoryPoolMXBean.getCollectionUsageThresholdCount();
//			测试此Java内存池最近收集的内存使用情况是否达到或超过了其收集使用阈值。
//			此方法不要求Java虚拟机执行除正常自动内存管理以外的任何垃圾回收。
				boolean collectionUsageThresholdExceeded = memoryPoolMXBean.isCollectionUsageThresholdExceeded();
				String string = memoryPoolMXBean.toString();
			}
			
			
			// 测试此内存池在Java虚拟机中是否有效。 一旦Java虚拟机将其从内存系统中删除，内存池将变为无效。
			boolean valid = memoryPoolMXBean.isValid();
//			返回管理此内存池的内存管理器的名称。 每个内存池将由至少一个内存管理器进行管理。
			String[] memoryManagerNames = memoryPoolMXBean.getMemoryManagerNames();
//			返回表示此内存池的名称。
			String name = memoryPoolMXBean.getName();
//			返回bean的名字
			ObjectName objectName = memoryPoolMXBean.getObjectName();
			
//			从Java虚拟机启动或峰值重置以来，返回此内存池的峰值内存使用量。 如果此内存池无效（即不再存在），则此方法返回null。
//			MBeanServer访问：
//			MemoryUsage的映射类型是具有MemoryUsage中指定的属性的CompositeData。
			MemoryUsage peakUsage = memoryPoolMXBean.getPeakUsage();
			
//			将该内存池的峰值内存使用量统计值重置为当前的内存使用量。
			memoryPoolMXBean.resetPeakUsage();
			
//			返回此内存池的类型。heap?
			MemoryType type = memoryPoolMXBean.getType();
			
			
/*返回此内存池的内存使用情况的估计值。 如果此内存池无效（即不再存在），则此方法返回null。
此方法请求Java虚拟机尽力估计此内存池的当前内存使用情况。 
对于一些内存池，这种方法可能是一个昂贵的操作，需要一些计算来确定估计。 一个实现应该记录在这种情况下。
此方法用于监视系统内存使用情况并检测内存不足情况。
MBeanServer访问：
MemoryUsage的映射类型是具有MemoryUsage中指定的属性的CompositeData。*/
			MemoryUsage usage = memoryPoolMXBean.getUsage();
			
			

			String string = memoryPoolMXBean.toString();
		}
	}

	@SuppressWarnings("unused")
	private static void memoryMXBean() {
		// 获取JVM的内存系统的MXBean
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		// 获取heap堆内存的使用情况
		// init = 67108864(65536K) used = 1678264(1638K) committed = 64487424(62976K)
		// max = 937951232(915968K)
		MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		// 获取非堆内存的使用情况
		// init = 2555904(2496K) used = 4502824(4397K) committed = 8060928(7872K) max =
		// -1(-1K)
		MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
		// 获取 MXBean的名字 java.lang:type=Memory
		ObjectName objectName = memoryMXBean.getObjectName();
		// 返回正在完成的对象的近似数量。
		int objectPendingFinalizationCount = memoryMXBean.getObjectPendingFinalizationCount();// 0
		// 是否设置verbose模式
		boolean verbose = memoryMXBean.isVerbose();// false
		memoryMXBean.setVerbose(verbose);
		// 执行GC
		memoryMXBean.gc();

		// 添加监听器,需要用到实现类,实现类在sun实现中,用不了

	}

	@SuppressWarnings("unused")
	private static void memoryManagerMXBean() {
		List<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();
		for (MemoryManagerMXBean memoryManagerMXBean : memoryManagerMXBeans) {
			// 获取管理器所管理的内存的名字
			String[] memoryPoolNames = memoryManagerMXBean.getMemoryPoolNames();
			// 获取内存管理器的名字
			String name = memoryManagerMXBean.getName();
			// 获取mxbean的名字
			ObjectName objectName = memoryManagerMXBean.getObjectName();
			// 测试此内存管理器在Java虚拟机中是否有效。 一旦Java虚拟机将其从内存系统中删除，内存管理器就会失效。
			boolean valid = memoryManagerMXBean.isValid();
			String string = memoryManagerMXBean.toString();

		}
	}

	@SuppressWarnings("unused")
	private static void garbageCollectorMXBean() {
		// 获取垃圾收集器的MXBean
		List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
			// 自JVM开始后,获取垃圾收集的次数
			long collectionCount = garbageCollectorMXBean.getCollectionCount();
			// 获取收集的累计时间
			long collectionTime = garbageCollectorMXBean.getCollectionTime();
			// 获取垃圾收集器 的内存名称 [PS Eden Space, PS Survivor Space, PS Old Gen]
			String[] memoryPoolNames = garbageCollectorMXBean.getMemoryPoolNames();
			// 返回垃圾收集器的名称 PS MarkSweep
			String name = garbageCollectorMXBean.getName();
			// 返回MXBean的名称 java.lang:type=GarbageCollector,name=PS MarkSweep
			ObjectName objectName = garbageCollectorMXBean.getObjectName();
			// 测试此内存管理器在Java虚拟机中是否有效。 一旦Java虚拟机将其从内存系统中删除，内存管理器就会失效。
			boolean valid = garbageCollectorMXBean.isValid();// true
			garbageCollectorMXBean.toString();
		}
	}

	@SuppressWarnings("unused")
	private static void compliationMXBean() {
		// 返回java虚拟机编译系统的托管bean,如果java虚拟机没有编译系统,就返回null
		CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
		// 返回JVM的编译器名称 HotSpot 64-Bit Tiered Compilers,
		String name = compilationMXBean.getName();
		// 返回累计的编译时间
		long totalCompilationTime = compilationMXBean.getTotalCompilationTime();
		// 返回bean 的名字 java.lang:type=Compilation
		ObjectName objectName = compilationMXBean.getObjectName();
		// 测试JVM是否支持监视编译 true
		boolean compilationTimeMonitoringSupported = compilationMXBean.isCompilationTimeMonitoringSupported();
	}

	@SuppressWarnings("unused")
	private static void classloaderBean() {
		// 返回java虚拟机的类加载系统的托管bean
		ClassLoadingMXBean classLoading = ManagementFactory.getClassLoadingMXBean();
		// 获取加载的类的计数
		int loadedClassCount = classLoading.getLoadedClassCount();
		// 获取 bean的名字 java.lang:type=ClassLoading
		ObjectName objectName = classLoading.getObjectName();
		// 获取累计加载类的计数
		long totalLoadedClassCount = classLoading.getTotalLoadedClassCount();
		// 获取卸载类的计数
		long unloadedClassCount = classLoading.getUnloadedClassCount();
		// verbose模式是否打开
		boolean verbose = classLoading.isVerbose();
		classLoading.setVerbose(false);
	}

}
