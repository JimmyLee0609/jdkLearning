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
		// 返回ForkJoinPool的默认线程池
		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		// 获取ForkJoinPool的并行数量
		int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism();

		// 获取ForkJoinPool的默认县城工厂
		ForkJoinWorkerThreadFactory defaultforkjoinworkerthreadfactory = ForkJoinPool.defaultForkJoinWorkerThreadFactory;
		// 新建一个为捕获异常的处理器
		MyUnCaughtExceptionHandler handler = new MyUnCaughtExceptionHandler();
		// 新建线程池 30线程池数量 线程池工厂 未捕获异常处理 asyncMode 默认false LIFO会有任务线程抢占，true就是FIFO没有join一说
		ForkJoinPool pool = new ForkJoinPool(30, defaultforkjoinworkerthreadfactory, handler, false);

		executeTask(pool);

		// 获取活动线程数量
		int activeThreadCount = pool.getActiveThreadCount();
		// 获取是否异步模式
		boolean asyncMode = pool.getAsyncMode();
		// 获取线程工厂
		ForkJoinWorkerThreadFactory factory = pool.getFactory();
		// 获取并行线程的数量
		int parallelism = pool.getParallelism();
		// 获取线程池的大小
		int poolSize = pool.getPoolSize();
		// 获取已经submit但是还没执行的任务数量
		int queuedSubmissionCount = pool.getQueuedSubmissionCount();
		// 获取工作线程中正在执行任务的数量
		long queuedTaskCount = pool.getQueuedTaskCount();
		// 返回正在活动的线程数量
		int runningThreadCount = pool.getRunningThreadCount();
		// 返回另一个线程的工作队列中偷取的任务总数
		long stealCount = pool.getStealCount();
		// 如果任务提交，但是没有执行，就返回true
		boolean hasQueuedSubmissions = pool.hasQueuedSubmissions();
		//
		boolean awaitQuiescence = pool.awaitQuiescence(20, TimeUnit.SECONDS);
		// 阻塞线程池所在线程指定的时间，让线程池内的线程可以继续工作。超时后，线程池将被清理
		boolean awaitTermination = pool.awaitTermination(20, TimeUnit.SECONDS);

		// 获取线程池对于为捕获异常的处理
		UncaughtExceptionHandler uncaughtExceptionHandler = pool.getUncaughtExceptionHandler();
		// 是否所有的线程都处于空闲的状态
		boolean quiescent = pool.isQuiescent();
		// 线程池是否发出关闭指令
		boolean shutdown = pool.isShutdown();
		// 线程池是否正在关闭
		boolean terminating = pool.isTerminating();
		// 线程池是否已经关闭
		boolean terminated = pool.isTerminated();
		// 关闭线程池
		pool.shutdown();
		// 立即关闭线程池
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
//		安排执行给定的任务
		pool.execute(Rtask);
		pool.execute(forkJoinTask);
		ForkJoinTask<Object> fork = forkJoinTask.fork();
		Object join = forkJoinTask.join();
		
//		提交一个ForkJoinTask任务用于执行
		ForkJoinTask<Object> submit3 = pool.submit(Ctask);
		ForkJoinTask<Object> submit2 = pool.submit(forkJoinTask);
		ForkJoinTask<?> submit = pool.submit(Rtask);
		ForkJoinTask<String> submit4 = pool.submit(Rtask, "返回值");
		
		
//		执行给定的任务，在完成后返回其结果，如果运行期间发生异常，将抛出异常并保存堆栈信息
		Object invoke = pool.invoke(forkJoinTask);

		List<Callable<Object>> list = new ArrayList();
		boolean add = list.add(Ctask);
		
//		执行给定的任务，返回一个可以获取运行后结果的预期，这里运行的结果其实和ThreadPoolExecutor是类似的
		List<Future<Object>> invokeAll = pool.invokeAll(list);
		List<Future<Object>> invokeAll2 = pool.invokeAll(list, 50, TimeUnit.SECONDS);
		
		Object invokeAny = pool.invokeAny(list);
		Object invokeAny2 = pool.invokeAny(list, 20, TimeUnit.SECONDS);
	}

}
