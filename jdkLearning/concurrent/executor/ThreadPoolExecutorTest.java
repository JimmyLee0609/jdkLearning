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
//		自定义线程池的工作队列的数据结构
		ArrayList list = new ArrayList();
//		50队列的最大存储数量,   true:FIFO  false:由队列的数据结构决定                  list:从指定的集合中复制任务到队列
		ArrayBlockingQueue workQueue = new ArrayBlockingQueue(50, true, list);
//		自定义线程工厂
		MyThreadFactory threadFactory = new MyThreadFactory();
//		构建被拒接任务的处理方式
		RejectedExecutionHandler handler=new ThreadPoolExecutor.AbortPolicy();
		
//		============构建对象==========================
//		指定核心线程数量15   线程池最大线程数量50  线程空闲存活时间60秒,
//		指定工作队列,就是线程池线程的存放结构,        自定义线程的初始化工厂        指定线程出现异常的处理方式,可以新建对象自定义
		ThreadPoolExecutor pool = new ThreadPoolExecutor(15, 50, 60, TimeUnit.SECONDS, workQueue, threadFactory, handler);
		
//		======线程池执行任务========
		exec(pool);
//		===========线程池状态相关========================================
		
		
//		核心线程是否会超时.如果可以,核心线程也会被清理
		pool.allowCoreThreadTimeOut(false);
		boolean allowsCoreThreadTimeOut = pool.allowsCoreThreadTimeOut();//核心线程是否会超时
//		获取已经完成的任务数量
		long completedTaskCount = pool.getCompletedTaskCount();
//		获取当前队列的任务数量,是一个瞬时值
		long taskCount = pool.getTaskCount();
//		获取当前的任务队列
		BlockingQueue<Runnable> queue = pool.getQueue();

//		当前线程存活的数量
		int activeCount = pool.getActiveCount();
//		线程池开启至今的最大线程峰值
		int largestPoolSize = pool.getLargestPoolSize();
//		获取当前线程池的线程数量
		int poolSize = pool.getPoolSize();
//		重新设定核心线程池的线程数量,运行时可用,
		pool.setCorePoolSize(15);
		int corePoolSize2 = pool.getCorePoolSize();
//		启动一个核心线程准备接下来的任务,如果核心线程数量达到最大将返回false
		boolean prestartCoreThread = pool.prestartCoreThread();
//		启动全部的核心线程,就是构建最大数量的核心线程,并保存到任务队列中,等待被使用
		int prestartAllCoreThreads = pool.prestartAllCoreThreads();
//		设置线程的最大空闲时间
		pool.setKeepAliveTime(60, TimeUnit.SECONDS);
		long keepAliveTime = pool.getKeepAliveTime(TimeUnit.SECONDS);
//		设置线程池的最大容量,
		pool.setMaximumPoolSize(50);
		int maximumPoolSize2 = pool.getMaximumPoolSize();
//		设定线程工厂
		pool.setThreadFactory(threadFactory);
		ThreadFactory threadFactory2 = pool.getThreadFactory();
//		设定被拒绝任务的处理方式
		pool.setRejectedExecutionHandler(handler);
		RejectedExecutionHandler rejectedExecutionHandler = pool.getRejectedExecutionHandler();
		
		
//		是否已经关闭
		boolean shutdown = pool.isShutdown();
//		是否已经完成终止,就是任务都做完了
		boolean terminated = pool.isTerminated();
//		是否正在终止,就是shutdown但是任务还在运行
		boolean terminating = pool.isTerminating();
		
//		线程池关闭的时候,最大的等待时间,过时,保存线程池的引用将被抛弃,其中线程任务内容将被清理,返回的内容也无法处理.
		pool.awaitTermination(60, TimeUnit.SECONDS);
//		关闭线程池,不再接受新的任务,在队列中的任务会继续执行
		pool.shutdown();
//		立即关闭线程池,不再接受新的任务,在队列中的任务都会被中断,并将没有完成的任务集返回
		List<Runnable> shutdownNow = pool.shutdownNow();
	}

	private static void exec(ThreadPoolExecutor pool) throws Exception {
		Runnable command = new Runnable() {
			@Override
			public void run() {
			}};
//			线程池执行一个任务,由于存在线程池的概念,首先,会找是否由空闲的线程,由的话,由空闲的线程来执行任务.
//			如果没有空闲的线程, 1.存在的线程没有超过核心线程数量,就新建核心线程,再获取空闲线程来执行
//											2.存在的线程超过核心线程数量,没有超过最大线程数量,新建线程,再获取空闲线程
//											3.存在的线程达到最大容量无法新增线程,将任务存放到任务队列中,
//											4.超出任务队列限制拒绝任务
		pool.execute(command);
//		移除任务
		pool.remove(command);
		
//		新建一个Callable对象，需要指定返回值的具体类型
		Callable<Object> callable = new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return null;
			}};
			
		ArrayList<Callable<Object>> tasks = new ArrayList();
		boolean add = tasks.add(callable);
//	    将集合中的任务传递到线程池的任务队列中，这个动作立即执行，之后修改tasks集合将不会改变线程池内的任务
//		在指定的时间内运算任务，如果任务完成或者超时，返回。超时的会cancle掉线程池任务队列中的任务，正在运算的任务线程将被中断
//		这里返回的是所有完成的任务的Future的集
		List<Future<Object>> invokeAll = pool.invokeAll(tasks, 30, TimeUnit.SECONDS);
//		将集合中的任务传递到线程池的任务队列中，立即执行。在指定的时间内运算任务集
//		一旦由线程返回任务的结果，或者超时，立即返回。存在线程池任务队列的任务将被cancle,正在运行的任务将被中断
		Object invokeAny = pool.invokeAny(tasks, 60, TimeUnit.SECONDS);
		
//		尝试移除线程池中cancelled的任务移除，被取消的任务就是标记了，还没从队列中移除，这个操作为队列清理，取消任务，取消的任务没取消的状态下将被处理的时候清除
		pool.purge();
		
//		ThreadPool的操作是和execute一样的
		Future<Object> submit = pool.submit(callable);
		
//		ThreadPool的操作是和execute一样的，执行runnable，返回的future是FutureTask的包装
		Future<?> submit2 = pool.submit(command);
		
//		ThreadPool的操作是和execute一样的，这里是执行runnable    并返回第二个参数的值，其他线程池实现可能不同
		Future<String> submit3 = pool.submit(command, "abcabc");
		
	}

}
