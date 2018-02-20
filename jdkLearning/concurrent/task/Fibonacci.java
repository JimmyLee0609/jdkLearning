package task;

import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1872787304014915976L;
	final int n;

	public Fibonacci(int n) {
		super();
		this.n = n;
	}

	@Override
	protected Integer compute() {
		if (n <= 1)
			return n;
		Fibonacci f1 = new Fibonacci(n - 1);
		f1.fork();//每次fork就任务添加到当前工作线程的WorkQueue中，不在工作线程的，提交到CommonPool的Workqueue中。
//		由于添加了任务会notify工作线程去接任务，如果没有空闲线程就会去新建线程
//		每一个工作线程，就会有一个WorkQueue，新开的线程将会执行任务
		Fibonacci f2 = new Fibonacci(n - 2);
		return f2.compute() + f1.join();
	}

}
