package uitl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

	public static void main(String[] args) {
		/*ConutDownLatch是在多线程下进行同步的一个工具。
		num是只需要多少进行多少次countDown才能到达被唤醒的临界，每次调用countDown都会使临界值-1
		这是一个简单的例子。需要countDown5次才能到达临界，
		任务中先等待1秒然后countDown,然后阻塞线程
		直到底个线程countDown达到临界，阻塞的线程被唤醒。一同运行.
		需要注意的是，这是一个一次性的工具，countDown到了0就不会reset，之后的线程就不会阻塞了
		用法是新建对象，每次先countDown然后await(),当先线程将被阻塞，其他线程继续countDown和await.直到临界点
		*/
		int num = 5;
		CountDownLatch latch = new CountDownLatch(num);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "线程开启");
				try {
					latch.await(1,TimeUnit.SECONDS);//阻塞线程1秒
					long count = latch.getCount();
					System.out.println(count);
					latch.countDown();
					latch.await();//阻塞线程直到被唤醒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() +"完成CountDown");
			}
		};
		for (int i = 0; i < num+3; i++) {
			new Thread(task).start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
