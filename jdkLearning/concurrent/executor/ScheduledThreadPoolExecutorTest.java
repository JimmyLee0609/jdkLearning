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
		// ==ScheduledThreadPoolExecutor是ThreadPoolExecutor的子类=====它们的区别就是有没有周期重复

		MyThreadFactory threadFactory = new MyThreadFactory();
		AbortPolicy policy = new ThreadPoolExecutor.AbortPolicy();
		// 新建一个延迟加载或者指定时间周期重复的线程池
		ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(15, threadFactory, policy);

		executeTask(pool);

		// 获取目前线程池的活动线程数量
		int activeCount = pool.getActiveCount();
		// 获取线程池开启到目前位置的任务的完成数量
		long completedTaskCount = pool.getCompletedTaskCount();
		// 获取线程池开启至今的最大线程峰值
		int largestPoolSize = pool.getLargestPoolSize();
		// 获取/设定线程池的最大线程容量
		int maximumPoolSize = pool.getMaximumPoolSize();
		pool.setMaximumPoolSize(50);
		// 获取目前线程池的大小
		int poolSize = pool.getPoolSize();
		// 获取目前的任务数量
		long taskCount = pool.getTaskCount();
		// 核心线程容量
		int corePoolSize = pool.getCorePoolSize();
		pool.setCorePoolSize(15);
		// 获取/设定线程的工厂
		pool.setThreadFactory(threadFactory);
		ThreadFactory threadFactory2 = pool.getThreadFactory();
		// 是否允许核心线程空闲的时候超时
		pool.allowCoreThreadTimeOut(false);
		boolean allowsCoreThreadTimeOut = pool.allowsCoreThreadTimeOut();
		// 设定线程池空闲的等待时间，超时将被中断
		pool.setKeepAliveTime(60, TimeUnit.SECONDS);
		long keepAliveTime = pool.getKeepAliveTime(TimeUnit.SECONDS);

		// 线程池收到shutDown的指令，这里设定线程池还可以工作的时间，超时将强行中断
		boolean awaitTermination = pool.awaitTermination(50, TimeUnit.SECONDS);
		// 获取/设定在shutdown指令的时候是否继续进行工作队列，默认false，shutdownNow就是立即关闭
		boolean continueExistingPeriodicTasksAfterShutdownPolicy = pool
				.getContinueExistingPeriodicTasksAfterShutdownPolicy();
		pool.setContinueExistingPeriodicTasksAfterShutdownPolicy(continueExistingPeriodicTasksAfterShutdownPolicy);
		// 获取/设定在shutdown指令的时候是否继续继续工作队列
		boolean executeExistingDelayedTasksAfterShutdownPolicy = pool
				.getExecuteExistingDelayedTasksAfterShutdownPolicy();
		pool.setExecuteExistingDelayedTasksAfterShutdownPolicy(executeExistingDelayedTasksAfterShutdownPolicy);
		// 获取/设定任务被拒绝的处理方式
		RejectedExecutionHandler rejectedExecutionHandler = pool.getRejectedExecutionHandler();
		pool.setRejectedExecutionHandler(rejectedExecutionHandler);
		// 获取/设定被设置为cancelled的任务是否需要马上移除工作队列，默认是false
		boolean removeOnCancelPolicy = pool.getRemoveOnCancelPolicy();
		pool.setRemoveOnCancelPolicy(removeOnCancelPolicy);

		// 预先开启一个核心线程，以便加入任务能够尽快处理，只有在核心线程数量没有达到核心线程容量才能用
		boolean prestartCoreThread = pool.prestartCoreThread();
		// 预先开启所有核心线程 如果核心线程已经全部开启，不会重复开启，返回的是开启的数量
		int prestartAllCoreThreads = pool.prestartAllCoreThreads();

		// 获取工作队列的任务集合
		BlockingQueue<Runnable> queue = pool.getQueue();

		// 尝试将工作队列中的被标记为cancel的任务清楚
		pool.purge();

		// 线程池是否关闭，不在接受新任务，队列的任务仍在执行
		boolean shutdown = pool.isShutdown();
		// 线程池是否正在关闭，不在接受新任务，正在中断池内的线程
		boolean terminating = pool.isTerminating();
		// 线程池是否已经关闭，不在接受新任务，池内的线程已经中断
		boolean terminated = pool.isTerminated();
		// 关闭线程池，线程池将不接受新的任务，任务队列中的任务继续运行
		pool.shutdown();
		// 立即关闭线程池，正在运行的线程将被中断，任务队列中的任务将被传递成list
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
//		线程池执行某个任务，将任务配置成RunnableScheduledFuture，没有延迟的形式，添加到工作队列
//		线程池关闭，拒绝任务，
		pool.execute(Rtask);
		pool.submit(Rtask);//同execute
		Future<Object> submit = pool.submit(Ctask);//同execute
//		这里特殊一点就是，只要任务顺利完成，就会返回第二个参数
		Future<String> submit2 = pool.submit(Rtask, "返回值");//使用工具类Executors将任务转换为Callable的任务，之后同execute
		
//		从现在开始延迟指定的时间30秒，然后开启任务
		ScheduledFuture<Object> schedule = pool.schedule(Ctask, 30, TimeUnit.SECONDS);
		ScheduledFuture<?> schedule2 = pool.schedule(Rtask, 30, TimeUnit.SECONDS);
//		延迟指定的时间30秒。在指定的间隔50秒，重复执行任务
		ScheduledFuture<?> scheduleAtFixedRate = pool.scheduleAtFixedRate(Rtask, 30, 50, TimeUnit.SECONDS);
//		从现在开始，延迟60秒启动任务，在任务完成30秒后在重新执行任务，每个任务完成30秒后将重新启动
		ScheduledFuture<?> scheduleWithFixedDelay = pool.scheduleWithFixedDelay(Rtask, 60, 30, TimeUnit.SECONDS);
		
		List<Callable<Object>> list=new ArrayList<Callable<Object>>();
		
		boolean add = list.add(Ctask);
		
//		线程池执行任务队列，并将执行后的结果返回
		List<Future<Object>> invokeAll2 = pool.invokeAll(list);
//		线程池执行任务队列，在限定的时间40秒内，将完成的结果返回，如果全部完成，就全部返回，如果有任务没有完成，将从工作队列中移除这些任务，正在运行的线程将被终止
		List<Future<Object>> invokeAll = pool.invokeAll(list, 40, TimeUnit.SECONDS);
		
//		线程池执行任务队列，并将第一个执行完的任务结果返回，其他的工作队列中任务将被标记cancel，正在运行的任务线程将被终止
		Object invokeAny = pool.invokeAny(list);
//		线程池执行任务队列，并在限定时间内，将第一个执行完的任务结果返回，如果超出限定时间，将终止所有任务线程，工作队列的任务将被标记cancel
		Object invokeAny2 = pool.invokeAny(list, 80, TimeUnit.SECONDS);
	}
}
