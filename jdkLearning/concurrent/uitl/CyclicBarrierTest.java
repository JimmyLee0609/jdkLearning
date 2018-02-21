package uitl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierTest {

	public static void main(String[] args) throws Exception {
//		normal();
		
		action();
	}

	private static void action() throws InterruptedException {
		/*
		 * CyclicBarrier自动重复屏障，可以设定每次屏障被突破(达到临界值)，将会触发传入的runnable任务一次
		 * 
		 * */
		Runnable action = new Runnable() {
			@Override
			public void run() {
				System.out.println("action");
			}
		};
		
		int num = 5;
		CyclicBarrier barrier = new CyclicBarrier(num, action);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName()+"开启");
					Thread.sleep(1000);
					
					barrier.await();
					
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"结束");
			}
		};
		for (int i = 0; i < num*2; i++) {
			new Thread(task).start();;
		}
		Thread.sleep(5000);
	}

	private static void normal() throws InterruptedException {
		/*
		 * 这是一个会自动回复的屏障。每次线程await就会将临界-1,相同线程不能await两次，第二次是不会生效的
		 * 每当到达临界，就会唤醒await的线程，然后回复屏障，需要再次别的线程await去削减趋向临界值，才会发生第二次await
		 * 
		 */
		int num = 5;
		CyclicBarrier barrier = new CyclicBarrier(num);

		Runnable task = new Runnable() {
			@Override
			public void run() {
				int numberWaiting = barrier.getNumberWaiting();
				int parties = barrier.getParties();
				boolean broken = barrier.isBroken();

				System.out.println(Thread.currentThread().getName() + "开启" + "  | getNumberWaiting->" + numberWaiting
						+ "  | getParties->" + parties + "| isBroken->" + broken);
				try {
					// int await = barrier.await(3,
					// TimeUnit.SECONDS);//这个方法在等待指定的时间还没达到临界就会抛出异常。中断线程
					int await2 = barrier.await();
					Thread.sleep(1000);
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} /*
					 * catch (TimeoutException e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); return ; }
					 */
				int numberWaiting2 = barrier.getNumberWaiting();
				int parties2 = barrier.getParties();
				boolean broken2 = barrier.isBroken();

				System.out.println(Thread.currentThread().getName() + "开启" + "  | getNumberWaiting->" + numberWaiting2
						+ "  | getParties->" + parties2 + "| isBroken->" + broken2);
				System.out.println(Thread.currentThread().getName() + "finish");
				try {
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("another await");
			}
		};
		for (int i = 0; i < num + 1; i++) {
			new Thread(task).start();
		}

		Thread.sleep(5000);
	}
}
