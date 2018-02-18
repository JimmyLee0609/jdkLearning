package executor;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class ExecutorsTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		// ��ȡĬ���̳߳ع���
		ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
		// ��ȡ����̳߳ع��� AccessControl.doPrivileged
		ThreadFactory privilegedThreadFactory = Executors.privilegedThreadFactory();
//		�½�һ��runnable����
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println();
			}
		};
//		�̳߳�ִ��runnable���󣬲����ɷ���ֵ
		Executors.callable(task);
//		�ڲ�����һ��callable������������runnable��Ϊ�ֶ�, ֻҪrunnableִ�����˾ͻ᷵�� �ڶ�������������
		Callable<String> callable2 = Executors.callable(task, "www");
		String call = callable2.call();
		
		Callable callable = new Callable() {
			@Override
			public Object call() throws Exception {
				return null;
			}
		};
//		�ڲ�����AccessController.doPrivileged
		Callable privilegedCallable = Executors.privilegedCallable(callable);
		
		PrivilegedAction action = new PrivilegedAction() {
			@Override
			public Object run() {
				return null;
			}
		};
//		��AccessController.doPrivileged�ڲ�ʹ�õġ�һ�����������ʹ��privilegedCallable
		Callable<Object> callable3 = Executors.callable(action);

		PrivilegedExceptionAction privilegedExceptionAction = new PrivilegedExceptionAction() {
			@Override
			public Object run() throws Exception {
				return null;
			}
		};
//		��AccessController.doPrivileged�ڲ�ʹ�õģ����ⲿ����ʹ��privilegedCallable
		Callable<Object> callable4 = Executors.callable(privilegedExceptionAction);

//		ͨ����AccessController.doPrivileged�ڲ�ʹ�ã�ʹ���ڲ������������ִ��callable,���ⰲȫ����
		Callable loader = Executors.privilegedCallableUsingCurrentClassLoader(callable);
		
//		����һ��������Ҫ�����̵߳��̳߳أ�һ��ʼ�߳���������15���������������������߳�30��������پͼ���15
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
//		�Զ����̹߳��������������̳߳�
		ExecutorService newCachedThreadPool2 = Executors.newCachedThreadPool(defaultThreadFactory);
//		�½��̶������̳߳�
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(15);
//   �Զ����̹߳������½��̶������̳߳�
		ExecutorService newFixedThreadPool2 = Executors.newFixedThreadPool(15, defaultThreadFactory);
//		�½�һ���̶����������̣߳��ڸ����ӳٻ��߹̶�����ִ�е��̳߳أ�ÿ15����ִ��һ��
		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(15);
//		�Զ����̹߳���
		ScheduledExecutorService newScheduledThreadPool2 = Executors.newScheduledThreadPool(15, defaultThreadFactory);
//		�½����߳��̳߳�
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
//		�½������ӳٻ�̶�����ִ�еĵ��߳��̳߳�
		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors
				.newSingleThreadScheduledExecutor(defaultThreadFactory);
//		�½�fookJoin�Ļ����̳߳أ��߳����������������
		ExecutorService newWorkStealingPool = Executors.newWorkStealingPool();
//		�½��̶��߳�������fookJoin�̳߳�
		ExecutorService newWorkStealingPool2 = Executors.newWorkStealingPool(15);

//		��һ���̳߳ر�Ϊ���ɱ������
		ExecutorService unconfigurableExecutorService = Executors.unconfigurableExecutorService(newWorkStealingPool);
//		��һ�������ӳ�,���߹̶�����ִ�е��̳߳�����Ϊ���ɱ������
		ScheduledExecutorService unconfigurableScheduledExecutorService = Executors
				.unconfigurableScheduledExecutorService(newSingleThreadScheduledExecutor);

	}

}
