package task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

public class TaskTest {

	public static void main(String[] args) throws Exception, ExecutionException {
		MyForkJoinAction action = new MyForkJoinAction();
		boolean inForkJoinPool = MyForkJoinAction.inForkJoinPool();
		MyForkJoinAction.helpQuiesce();
		int surplusQueuedTaskCount = MyForkJoinAction.getSurplusQueuedTaskCount();
		int queuedTaskCount = MyForkJoinAction.getQueuedTaskCount();
		ForkJoinPool pool = MyForkJoinAction.getPool();
		ForkJoinTask<?> adapt = MyForkJoinAction.adapt(new Runnable() {
			@Override
			public void run() {
			}});
		
//		取消任务，如果任务在工作的线程中，true:中断线程。
		action.cancel(true);//
		action.complete(null);
		ForkJoinTask<Void> fork = action.fork();
		Void void1 = action.get();
		Void void2 = action.get(5, TimeUnit.SECONDS);
		Void invoke = action.invoke();
		Void join = action.join();
		MyForkJoinAction.invokeAll(action, action);

		Throwable exception = action.getException();
		short forkJoinTaskTag = action.getForkJoinTaskTag();
		Void rawResult = action.getRawResult();
		boolean cancelled = action.isCancelled();
		boolean completedAbnormally = action.isCompletedAbnormally();
		boolean completedNormally = action.isCompletedNormally();
		boolean done = action.isDone();
		action.quietlyComplete();
		action.quietlyInvoke();
		action.quietlyJoin();
		action.reinitialize();
		boolean tryUnfork = action.tryUnfork();
		action.reinitialize();
	}
}
