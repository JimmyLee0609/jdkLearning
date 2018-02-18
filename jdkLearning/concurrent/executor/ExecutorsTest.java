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
		// 获取默认线程池工厂
		ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
		// 获取许可线程池工厂 AccessControl.doPrivileged
		ThreadFactory privilegedThreadFactory = Executors.privilegedThreadFactory();
//		新建一个runnable对象
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println();
			}
		};
//		线程池执行runnable对象，不会由返回值
		Executors.callable(task);
//		内部做了一个callable的适配器，将runnable作为字段, 只要runnable执行完了就会返回 第二个参数的内容
		Callable<String> callable2 = Executors.callable(task, "www");
		String call = callable2.call();
		
		Callable callable = new Callable() {
			@Override
			public Object call() throws Exception {
				return null;
			}
		};
//		内部是用AccessController.doPrivileged
		Callable privilegedCallable = Executors.privilegedCallable(callable);
		
		PrivilegedAction action = new PrivilegedAction() {
			@Override
			public Object run() {
				return null;
			}
		};
//		在AccessController.doPrivileged内部使用的。一般在外面可以使用privilegedCallable
		Callable<Object> callable3 = Executors.callable(action);

		PrivilegedExceptionAction privilegedExceptionAction = new PrivilegedExceptionAction() {
			@Override
			public Object run() throws Exception {
				return null;
			}
		};
//		在AccessController.doPrivileged内部使用的，在外部可以使用privilegedCallable
		Callable<Object> callable4 = Executors.callable(privilegedExceptionAction);

//		通常在AccessController.doPrivileged内部使用，使用内部的类加载器来执行callable,避免安全问题
		Callable loader = Executors.privilegedCallableUsingCurrentClassLoader(callable);
		
//		创建一个根据需要创建线程的线程池，一开始线程数量例如15条，需求增长，就增加线程30，需求减少就减少15
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
//		自定义线程工厂，创建缓存线程池
		ExecutorService newCachedThreadPool2 = Executors.newCachedThreadPool(defaultThreadFactory);
//		新建固定数量线程池
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(15);
//   自定义线程工厂，新建固定数量线程池
		ExecutorService newFixedThreadPool2 = Executors.newFixedThreadPool(15, defaultThreadFactory);
//		新建一个固定核心数量线程，在给定延迟或者固定周期执行的线程池，每15分钟执行一次
		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(15);
//		自定义线程工厂
		ScheduledExecutorService newScheduledThreadPool2 = Executors.newScheduledThreadPool(15, defaultThreadFactory);
//		新建单线程线程池
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
//		新建给定延迟或固定周期执行的单线程线程池
		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors
				.newSingleThreadScheduledExecutor(defaultThreadFactory);
//		新建fookJoin的缓存线程池，线程数量会根据需求变更
		ExecutorService newWorkStealingPool = Executors.newWorkStealingPool();
//		新建固定线程数量的fookJoin线程池
		ExecutorService newWorkStealingPool2 = Executors.newWorkStealingPool(15);

//		将一个线程池变为不可变更配置
		ExecutorService unconfigurableExecutorService = Executors.unconfigurableExecutorService(newWorkStealingPool);
//		将一个给定延迟,或者固定周期执行的线程池设置为不可变更配置
		ScheduledExecutorService unconfigurableScheduledExecutorService = Executors
				.unconfigurableScheduledExecutorService(newSingleThreadScheduledExecutor);

	}

}
