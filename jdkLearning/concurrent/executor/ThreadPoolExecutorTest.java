package executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import threadFactory.MyThreadFactory;

public class ThreadPoolExecutorTest {

	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) throws Exception {
//		�Զ����̳߳صĹ������е����ݽṹ
		ArrayList list = new ArrayList();
//		50���е����洢����,   true:FIFO  false:�ɶ��е����ݽṹ����                  list:��ָ���ļ����и������񵽶���
		ArrayBlockingQueue workQueue = new ArrayBlockingQueue(50, true, list);
//		�Զ����̹߳���
		MyThreadFactory threadFactory = new MyThreadFactory();
//		�������ܽ�����Ĵ���ʽ
		RejectedExecutionHandler handler=new ThreadPoolExecutor.AbortPolicy();
		
//		============��������==========================
//		ָ�������߳�����15   �̳߳�����߳�����50  �߳̿��д��ʱ��60��,
//		ָ����������,�����̳߳��̵߳Ĵ�Žṹ,        �Զ����̵߳ĳ�ʼ������        ָ���̳߳����쳣�Ĵ���ʽ,�����½������Զ���
		ThreadPoolExecutor pool = new ThreadPoolExecutor(15, 50, 60, TimeUnit.SECONDS, workQueue, threadFactory, handler);
		
//		======�̳߳�ִ������========
		exec(pool);
//		===========�̳߳�״̬���========================================
		
		
//		�����߳��Ƿ�ᳬʱ.�������,�����߳�Ҳ�ᱻ����
		pool.allowCoreThreadTimeOut(false);
		boolean allowsCoreThreadTimeOut = pool.allowsCoreThreadTimeOut();//�����߳��Ƿ�ᳬʱ
//		��ȡ�Ѿ���ɵ���������
		long completedTaskCount = pool.getCompletedTaskCount();
//		��ȡ��ǰ���е���������,��һ��˲ʱֵ
		long taskCount = pool.getTaskCount();
//		��ȡ��ǰ���������
		BlockingQueue<Runnable> queue = pool.getQueue();

//		��ǰ�̴߳�������
		int activeCount = pool.getActiveCount();
//		�̳߳ؿ������������̷߳�ֵ
		int largestPoolSize = pool.getLargestPoolSize();
//		��ȡ��ǰ�̳߳ص��߳�����
		int poolSize = pool.getPoolSize();
//		�����趨�����̳߳ص��߳�����,����ʱ����,
		pool.setCorePoolSize(15);
		int corePoolSize2 = pool.getCorePoolSize();
//		����һ�������߳�׼��������������,��������߳������ﵽ��󽫷���false
		boolean prestartCoreThread = pool.prestartCoreThread();
//		����ȫ���ĺ����߳�,���ǹ�����������ĺ����߳�,�����浽���������,�ȴ���ʹ��
		int prestartAllCoreThreads = pool.prestartAllCoreThreads();
//		�����̵߳�������ʱ��
		pool.setKeepAliveTime(60, TimeUnit.SECONDS);
		long keepAliveTime = pool.getKeepAliveTime(TimeUnit.SECONDS);
//		�����̳߳ص��������,
		pool.setMaximumPoolSize(50);
		int maximumPoolSize2 = pool.getMaximumPoolSize();
//		�趨�̹߳���
		pool.setThreadFactory(threadFactory);
		ThreadFactory threadFactory2 = pool.getThreadFactory();
//		�趨���ܾ�����Ĵ���ʽ
		pool.setRejectedExecutionHandler(handler);
		RejectedExecutionHandler rejectedExecutionHandler = pool.getRejectedExecutionHandler();
		
		
//		�Ƿ��Ѿ��ر�
		boolean shutdown = pool.isShutdown();
//		�Ƿ��Ѿ������ֹ,��������������
		boolean terminated = pool.isTerminated();
//		�Ƿ�������ֹ,����shutdown��������������
		boolean terminating = pool.isTerminating();
		
//		�̳߳عرյ�ʱ��,���ĵȴ�ʱ��,��ʱ,�����̳߳ص����ý�������,�����߳��������ݽ�������,���ص�����Ҳ�޷�����.
		pool.awaitTermination(60, TimeUnit.SECONDS);
//		�ر��̳߳�,���ٽ����µ�����,�ڶ����е���������ִ��
		pool.shutdown();
//		�����ر��̳߳�,���ٽ����µ�����,�ڶ����е����񶼻ᱻ�ж�,����û����ɵ����񼯷���
		List<Runnable> shutdownNow = pool.shutdownNow();
	}

	private static void exec(ThreadPoolExecutor pool) throws Exception {
		Runnable command = new Runnable() {
			@Override
			public void run() {
			}};
//			�̳߳�ִ��һ������,���ڴ����̳߳صĸ���,����,�����Ƿ��ɿ��е��߳�,�ɵĻ�,�ɿ��е��߳���ִ������.
//			���û�п��е��߳�, 1.���ڵ��߳�û�г��������߳�����,���½������߳�,�ٻ�ȡ�����߳���ִ��
//											2.���ڵ��̳߳��������߳�����,û�г�������߳�����,�½��߳�,�ٻ�ȡ�����߳�
//											3.���ڵ��̴߳ﵽ��������޷������߳�,�������ŵ����������,
//											4.��������������ƾܾ�����
		pool.execute(command);
//		�Ƴ�����
		pool.remove(command);
		
//		�½�һ��Callable������Ҫָ������ֵ�ľ�������
		Callable<Object> callable = new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return null;
			}};
			
		ArrayList<Callable<Object>> tasks = new ArrayList();
		boolean add = tasks.add(callable);
//	    �������е����񴫵ݵ��̳߳ص���������У������������ִ�У�֮���޸�tasks���Ͻ�����ı��̳߳��ڵ�����
//		��ָ����ʱ���������������������ɻ��߳�ʱ�����ء���ʱ�Ļ�cancle���̳߳���������е�������������������߳̽����ж�
//		���ﷵ�ص���������ɵ������Future�ļ�
		List<Future<Object>> invokeAll = pool.invokeAll(tasks, 30, TimeUnit.SECONDS);
//		�������е����񴫵ݵ��̳߳ص���������У�����ִ�С���ָ����ʱ������������
//		һ�����̷߳�������Ľ�������߳�ʱ���������ء������̳߳�������е����񽫱�cancle,�������е����񽫱��ж�
		Object invokeAny = pool.invokeAny(tasks, 60, TimeUnit.SECONDS);
		
//		�����Ƴ��̳߳���cancelled�������Ƴ�����ȡ����������Ǳ���ˣ���û�Ӷ������Ƴ����������Ϊ��������ȡ������ȡ��������ûȡ����״̬�½��������ʱ�����
		pool.purge();
		
//		ThreadPool�Ĳ����Ǻ�executeһ����
		Future<Object> submit = pool.submit(callable);
		
//		ThreadPool�Ĳ����Ǻ�executeһ���ģ�ִ��runnable�����ص�future��FutureTask�İ�װ
		Future<?> submit2 = pool.submit(command);
		
//		ThreadPool�Ĳ����Ǻ�executeһ���ģ�������ִ��runnable    �����صڶ���������ֵ�������̳߳�ʵ�ֿ��ܲ�ͬ
		Future<String> submit3 = pool.submit(command, "abcabc");
		
	}

}
