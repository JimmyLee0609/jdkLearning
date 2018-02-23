package executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ExecutorCompletionServiceTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		/*
		 * 这个服务的会将提交的任务给到创建对象时候的线程池去运行,线程池将运行返回的Future保存到, 完成任务队列中.其实任务已经执行完了
		 * 当一个poll或者take的时候,获取到的是任务完成的future, 只有运行完的任务才会保存Future,运行中,或者异常中断的线程将不会保存,
		 * 这些类型的线程需要使用线程池的引用去做相应的处理 当任务正常完成的时候,会将结果保存到Future中
		 * 如果是异常完成,会将异常保存到Future中,当你使用Future的get方法的时候,就会将这个异常抛出
		 */

		// 创建线程池
		ExecutorService pool = Executors.newCachedThreadPool();
		// 创建保存Completion的队列
		LinkedBlockingQueue<Future<String>> queue = new LinkedBlockingQueue<>();
		// 创建一个使用线程池来处理任务的服务
		ExecutorCompletionService<String> service = new ExecutorCompletionService<>(pool, queue);
		// 创建一个任务
		Callable<String> task = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(1000);
				System.out.println("call");
				return "call";
			}
		};

		Callable<String> exceptionTask = new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("异常的任务被调用");
				throw new Exception("设施一");
			}
		};
		// 使用服务提交一个任务,返回一个Future,提交的任务会自动运行,然后保存到完成队列中
		Future<String> submit = service.submit(task);
		Future<String> submit2 = service.submit(exceptionTask);
		try {
			String string = submit.get();
			String string2 = submit2.get();
			System.out.println();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 这个方法会阻塞等待指定的时间,然后返回队列中的完成任务,根据完成任务队列的实现来操作
		Future<String> poll = service.poll(1, TimeUnit.SECONDS);
		// 也是根据完成队列的实现,是否阻塞
		Future<String> take = service.take();

	}

}
