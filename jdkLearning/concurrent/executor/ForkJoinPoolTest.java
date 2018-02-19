package executor;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import exception.MyUnCaughtExceptionHandler;

public class ForkJoinPoolTest {

	public static void main(String[] args) throws Exception {
		// ����ForkJoinPool��Ĭ���̳߳�
		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		// ��ȡForkJoinPool�Ĳ�������
		int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism();

		// ��ȡForkJoinPool��Ĭ���سǹ���
		ForkJoinWorkerThreadFactory defaultforkjoinworkerthreadfactory = ForkJoinPool.defaultForkJoinWorkerThreadFactory;
		// �½�һ��Ϊ�����쳣�Ĵ�����
		MyUnCaughtExceptionHandler handler = new MyUnCaughtExceptionHandler();
		// �½��̳߳� 30�̳߳����� �̳߳ع��� δ�����쳣���� asyncMode Ĭ��false LIFO���������߳���ռ��true����FIFOû��joinһ˵
		ForkJoinPool pool = new ForkJoinPool(30, defaultforkjoinworkerthreadfactory, handler, false);

		executeTask(pool);

		// ��ȡ��߳�����
		int activeThreadCount = pool.getActiveThreadCount();
		// ��ȡ�Ƿ��첽ģʽ
		boolean asyncMode = pool.getAsyncMode();
		// ��ȡ�̹߳���
		ForkJoinWorkerThreadFactory factory = pool.getFactory();
		// ��ȡ�����̵߳�����
		int parallelism = pool.getParallelism();
		// ��ȡ�̳߳صĴ�С
		int poolSize = pool.getPoolSize();
		// ��ȡ�Ѿ�submit���ǻ�ûִ�е���������
		int queuedSubmissionCount = pool.getQueuedSubmissionCount();
		// ��ȡ�����߳�������ִ�����������
		long queuedTaskCount = pool.getQueuedTaskCount();
		// �������ڻ���߳�����
		int runningThreadCount = pool.getRunningThreadCount();
		// ������һ���̵߳Ĺ���������͵ȡ����������
		long stealCount = pool.getStealCount();
		// ��������ύ������û��ִ�У��ͷ���true
		boolean hasQueuedSubmissions = pool.hasQueuedSubmissions();
		//
		boolean awaitQuiescence = pool.awaitQuiescence(20, TimeUnit.SECONDS);
		// �����̳߳������߳�ָ����ʱ�䣬���̳߳��ڵ��߳̿��Լ�����������ʱ���̳߳ؽ�������
		boolean awaitTermination = pool.awaitTermination(20, TimeUnit.SECONDS);

		// ��ȡ�̳߳ض���Ϊ�����쳣�Ĵ���
		UncaughtExceptionHandler uncaughtExceptionHandler = pool.getUncaughtExceptionHandler();
		// �Ƿ����е��̶߳����ڿ��е�״̬
		boolean quiescent = pool.isQuiescent();
		// �̳߳��Ƿ񷢳��ر�ָ��
		boolean shutdown = pool.isShutdown();
		// �̳߳��Ƿ����ڹر�
		boolean terminating = pool.isTerminating();
		// �̳߳��Ƿ��Ѿ��ر�
		boolean terminated = pool.isTerminated();
		// �ر��̳߳�
		pool.shutdown();
		// �����ر��̳߳�
		List<Runnable> shutdownNow = pool.shutdownNow();
	}

	private static void executeTask(ForkJoinPool pool) throws Exception {

		Runnable Rtask = new Runnable() {
			@Override
			public void run() {
			}
		};

		ForkJoinTask<Object> forkJoinTask = new ForkJoinTask<Object>() {
			private static final long serialVersionUID = 60292451846942106L;

			@Override
			public Object getRawResult() {
				return null;
			}

			@Override
			protected void setRawResult(Object value) {
			}

			@Override
			protected boolean exec() {
				return false;
			}
		};
		Callable<Object> Ctask = new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return null;
			}
		};
//		����ִ�и���������
		pool.execute(Rtask);
		pool.execute(forkJoinTask);
		ForkJoinTask<Object> fork = forkJoinTask.fork();
		Object join = forkJoinTask.join();
		
//		�ύһ��ForkJoinTask��������ִ��
		ForkJoinTask<Object> submit3 = pool.submit(Ctask);
		ForkJoinTask<Object> submit2 = pool.submit(forkJoinTask);
		ForkJoinTask<?> submit = pool.submit(Rtask);
		ForkJoinTask<String> submit4 = pool.submit(Rtask, "����ֵ");
		
		
//		ִ�и�������������ɺ󷵻���������������ڼ䷢���쳣�����׳��쳣�������ջ��Ϣ
		Object invoke = pool.invoke(forkJoinTask);

		List<Callable<Object>> list = new ArrayList();
		boolean add = list.add(Ctask);
		
//		ִ�и��������񣬷���һ�����Ի�ȡ���к�����Ԥ�ڣ��������еĽ����ʵ��ThreadPoolExecutor�����Ƶ�
		List<Future<Object>> invokeAll = pool.invokeAll(list);
		List<Future<Object>> invokeAll2 = pool.invokeAll(list, 50, TimeUnit.SECONDS);
		
		Object invokeAny = pool.invokeAny(list);
		Object invokeAny2 = pool.invokeAny(list, 20, TimeUnit.SECONDS);
	}

}
