package executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import threadFactory.MyThreadFactory;

public class ScheduledThreadPoolExecutorTest {

	public static void main(String[] args) throws Exception {
		// ==ScheduledThreadPoolExecutor��ThreadPoolExecutor������=====���ǵ����������û�������ظ�

		MyThreadFactory threadFactory = new MyThreadFactory();
		AbortPolicy policy = new ThreadPoolExecutor.AbortPolicy();
		// �½�һ���ӳټ��ػ���ָ��ʱ�������ظ����̳߳�
		ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(15, threadFactory, policy);

		executeTask(pool);

		// ��ȡĿǰ�̳߳صĻ�߳�����
		int activeCount = pool.getActiveCount();
		// ��ȡ�̳߳ؿ�����Ŀǰλ�õ�������������
		long completedTaskCount = pool.getCompletedTaskCount();
		// ��ȡ�̳߳ؿ������������̷߳�ֵ
		int largestPoolSize = pool.getLargestPoolSize();
		// ��ȡ/�趨�̳߳ص�����߳�����
		int maximumPoolSize = pool.getMaximumPoolSize();
		pool.setMaximumPoolSize(50);
		// ��ȡĿǰ�̳߳صĴ�С
		int poolSize = pool.getPoolSize();
		// ��ȡĿǰ����������
		long taskCount = pool.getTaskCount();
		// �����߳�����
		int corePoolSize = pool.getCorePoolSize();
		pool.setCorePoolSize(15);
		// ��ȡ/�趨�̵߳Ĺ���
		pool.setThreadFactory(threadFactory);
		ThreadFactory threadFactory2 = pool.getThreadFactory();
		// �Ƿ���������߳̿��е�ʱ��ʱ
		pool.allowCoreThreadTimeOut(false);
		boolean allowsCoreThreadTimeOut = pool.allowsCoreThreadTimeOut();
		// �趨�̳߳ؿ��еĵȴ�ʱ�䣬��ʱ�����ж�
		pool.setKeepAliveTime(60, TimeUnit.SECONDS);
		long keepAliveTime = pool.getKeepAliveTime(TimeUnit.SECONDS);

		// �̳߳��յ�shutDown��ָ������趨�̳߳ػ����Թ�����ʱ�䣬��ʱ��ǿ���ж�
		boolean awaitTermination = pool.awaitTermination(50, TimeUnit.SECONDS);
		// ��ȡ/�趨��shutdownָ���ʱ���Ƿ�������й������У�Ĭ��false��shutdownNow���������ر�
		boolean continueExistingPeriodicTasksAfterShutdownPolicy = pool
				.getContinueExistingPeriodicTasksAfterShutdownPolicy();
		pool.setContinueExistingPeriodicTasksAfterShutdownPolicy(continueExistingPeriodicTasksAfterShutdownPolicy);
		// ��ȡ/�趨��shutdownָ���ʱ���Ƿ����������������
		boolean executeExistingDelayedTasksAfterShutdownPolicy = pool
				.getExecuteExistingDelayedTasksAfterShutdownPolicy();
		pool.setExecuteExistingDelayedTasksAfterShutdownPolicy(executeExistingDelayedTasksAfterShutdownPolicy);
		// ��ȡ/�趨���񱻾ܾ��Ĵ���ʽ
		RejectedExecutionHandler rejectedExecutionHandler = pool.getRejectedExecutionHandler();
		pool.setRejectedExecutionHandler(rejectedExecutionHandler);
		// ��ȡ/�趨������Ϊcancelled�������Ƿ���Ҫ�����Ƴ��������У�Ĭ����false
		boolean removeOnCancelPolicy = pool.getRemoveOnCancelPolicy();
		pool.setRemoveOnCancelPolicy(removeOnCancelPolicy);

		// Ԥ�ȿ���һ�������̣߳��Ա���������ܹ����촦��ֻ���ں����߳�����û�дﵽ�����߳�����������
		boolean prestartCoreThread = pool.prestartCoreThread();
		// Ԥ�ȿ������к����߳� ��������߳��Ѿ�ȫ�������������ظ����������ص��ǿ���������
		int prestartAllCoreThreads = pool.prestartAllCoreThreads();

		// ��ȡ�������е����񼯺�
		BlockingQueue<Runnable> queue = pool.getQueue();

		// ���Խ����������еı����Ϊcancel���������
		pool.purge();

		// �̳߳��Ƿ�رգ����ڽ��������񣬶��е���������ִ��
		boolean shutdown = pool.isShutdown();
		// �̳߳��Ƿ����ڹرգ����ڽ��������������жϳ��ڵ��߳�
		boolean terminating = pool.isTerminating();
		// �̳߳��Ƿ��Ѿ��رգ����ڽ��������񣬳��ڵ��߳��Ѿ��ж�
		boolean terminated = pool.isTerminated();
		// �ر��̳߳أ��̳߳ؽ��������µ�������������е������������
		pool.shutdown();
		// �����ر��̳߳أ��������е��߳̽����жϣ���������е����񽫱����ݳ�list
		List<Runnable> shutdownNow = pool.shutdownNow();
	}

	private static void executeTask(ScheduledThreadPoolExecutor pool) throws Exception {
		Runnable Rtask = new Runnable() {
			@Override
			public void run() {
			}
		};
		Callable<Object> Ctask = new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return null;
			}
		};
//		�̳߳�ִ��ĳ�����񣬽��������ó�RunnableScheduledFuture��û���ӳٵ���ʽ����ӵ���������
//		�̳߳عرգ��ܾ�����
		pool.execute(Rtask);
		pool.submit(Rtask);//ͬexecute
		Future<Object> submit = pool.submit(Ctask);//ͬexecute
//		��������һ����ǣ�ֻҪ����˳����ɣ��ͻ᷵�صڶ�������
		Future<String> submit2 = pool.submit(Rtask, "����ֵ");//ʹ�ù�����Executors������ת��ΪCallable������֮��ͬexecute
		
//		�����ڿ�ʼ�ӳ�ָ����ʱ��30�룬Ȼ��������
		ScheduledFuture<Object> schedule = pool.schedule(Ctask, 30, TimeUnit.SECONDS);
		ScheduledFuture<?> schedule2 = pool.schedule(Rtask, 30, TimeUnit.SECONDS);
//		�ӳ�ָ����ʱ��30�롣��ָ���ļ��50�룬�ظ�ִ������
		ScheduledFuture<?> scheduleAtFixedRate = pool.scheduleAtFixedRate(Rtask, 30, 50, TimeUnit.SECONDS);
//		�����ڿ�ʼ���ӳ�60�������������������30���������ִ������ÿ���������30�����������
		ScheduledFuture<?> scheduleWithFixedDelay = pool.scheduleWithFixedDelay(Rtask, 60, 30, TimeUnit.SECONDS);
		
		List<Callable<Object>> list=new ArrayList<Callable<Object>>();
		
		boolean add = list.add(Ctask);
		
//		�̳߳�ִ��������У�����ִ�к�Ľ������
		List<Future<Object>> invokeAll2 = pool.invokeAll(list);
//		�̳߳�ִ��������У����޶���ʱ��40���ڣ�����ɵĽ�����أ����ȫ����ɣ���ȫ�����أ����������û����ɣ����ӹ����������Ƴ���Щ�����������е��߳̽�����ֹ
		List<Future<Object>> invokeAll = pool.invokeAll(list, 40, TimeUnit.SECONDS);
		
//		�̳߳�ִ��������У�������һ��ִ��������������أ������Ĺ������������񽫱����cancel���������е������߳̽�����ֹ
		Object invokeAny = pool.invokeAny(list);
//		�̳߳�ִ��������У������޶�ʱ���ڣ�����һ��ִ��������������أ���������޶�ʱ�䣬����ֹ���������̣߳��������е����񽫱����cancel
		Object invokeAny2 = pool.invokeAny(list, 80, TimeUnit.SECONDS);
	}
}
