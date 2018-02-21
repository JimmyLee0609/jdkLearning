package uitl;

import java.util.UUID;
import java.util.concurrent.Exchanger;

public class ExchangerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		sameThreadGroup();
//		在不同的ThreadGroup中exchanger是一样可用的
		ThreadGroup g1 = new ThreadGroup("group1");
		ThreadGroup g2 = new ThreadGroup("group2");
		Exchanger<String> exchanger = new Exchanger<String>();
		Runnable task = new Runnable() {
			@Override
			public void run() {
				UUID uuid = UUID.randomUUID();
				String string = uuid.toString();
				try {
					System.out.println(Thread.currentThread().getThreadGroup().getName()+"->"+Thread.currentThread().getName());
					String exchange = exchanger.exchange(string);
					System.out.println(string+"->"+exchange);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Runnable run=new Runnable() {
			@Override
			public void run() {
				Thread t5 = new Thread(g1,task);
				Thread t6 = new Thread(g2,task);
				t5.start();
				t6.start();
			}};
		
		Thread t1 = new Thread(run);
		Thread t2 = new Thread(run);
		Thread t3 = new Thread(run);
		Thread t4 = new Thread(run);
		
		Thread t7 = new Thread(task);
		Thread t8 = new Thread(task);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t7.start();
		t8.start();
		System.out.println();
	}

	private static void sameThreadGroup() {
		/*
		 * 不同线程的数据交换器 当线程调用exchange的时候，当前线程阻塞，直到有另一个线程也调用exchange，需要交换的内容就会交换
		 * 如果第三个线程也需要exchange，那它只能阻塞等待第四个线程的exchange了
		 */
		int num = 2;
		Exchanger<String> exchanger = new Exchanger<String>();

		Runnable task = new Runnable() {
			@Override
			public void run() {
				UUID randomUUID = UUID.randomUUID();
				String string = randomUUID.toString();
				try {
					String exchange = exchanger.exchange(string);
					System.out.println(Thread.currentThread().getName() + "->" + string + "->" + exchange);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			}
		};
		for (int i = 0; i < num + 1; i++)
			new Thread(task).start();
	}

}
