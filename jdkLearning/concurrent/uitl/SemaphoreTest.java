package uitl;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

	public static void main(String[] args) throws Exception {
		// normal();
		int num = 5;
		Semaphore semaphore = new Semaphore(num);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "开启");

				try {
					semaphore.tryAcquire(1, 3, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
					// 释放指定数量的信号量
					semaphore.release(2);
					// 释放全部的信号量
//					 int drainPermits = semaphore.drainPermits();
					return;
				}
//				获取等待的队列长度
				int queueLength = semaphore.getQueueLength();
//				获取可用的信号数量
				int availablePermits = semaphore.availablePermits();
				System.out.println("getQueueLength->" + queueLength + " | availablePermits->s" + availablePermits);
				System.out.println(Thread.currentThread().getName() + "关闭");
			}
		};

		for (int i = 0; i < num * 3; i++) {
			new Thread(task).start();
		}
		Thread.sleep(10000);
	}

	private static void normal() throws InterruptedException {
		/*
		 * 信号量，用于限定同时工作的线程的数量。 下面是一个简单的例子。线程acquire就会减少1个信号量 如果减少到0就无法获取到信号量，导致线程阻塞。
		 * 如果有线程release信号量，就会唤醒等待的线程，让它们可以获取到信号量，如果没有抢到，就再次阻塞，等待唤醒
		 * 例子中开启的线程比信号量多2，设计中不归还信号量不release。必然导致2个线程阻塞
		 * 正常情况下线程任务完成就release可以让其他等待线程被唤醒，是这个类的设计。
		 * 它还有一个faire的设计，就是唤醒的时候是否根据获取信号量的顺序唤醒线程。
		 * 实现是由一个队列保存阻塞的线程，公平的情况下就是将头元素唤醒，如果头元素有问题会向后一个元素操作。 不公平的情况就类似线程抢占
		 */
		int num = 5;
		Semaphore semaphore = new Semaphore(num);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "开启");
				try {
					semaphore.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "结束");
			}
		};
		// 由于开启的线程信号量多2个，所以必然有两个线程阻塞
		for (int i = 0; i < num + 2; i++) {
			new Thread(task).start();
		}

		Thread.sleep(5000);
	}

}
