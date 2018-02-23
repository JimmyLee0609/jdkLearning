package lock;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	public static void main(String[] args) {
		/*
		 * 重进入锁,就是支持重进入的锁, 表示该锁能够支持一个线程对资源的重复加锁, lock里面可以加condition 该锁还支持获取锁的是否FIFO
		 * 其实concurrent包中的queue就是很好的例子, 下面使用一个简单的队列的例子来看效果
		 * debug模式下,只要将put的线程停止,队列没有数据,take的线程就会阻塞
		 * condition相当于将线程放在一个线程组中,可以将指定的组线程唤醒,
		 * 
		 * Lock 仅仅锁主某个操作的瞬间,被锁住的瞬间,其他线程无法访问
		 * 
		 * ,然后使用condition去操作线程的睡眠,和唤醒,
		 * 在Condition的状态中,线程是释放了Lock的锁对象,其他线程可以获取到锁,
		 * 
		 * 以这样的方式来达到同步的效果
		 */

		SimpleQueue queue = new SimpleQueue();

		Runnable takeTask = new Runnable() {
			@Override
			public void run() {
				while (true) {
					String take;
					try {
						take = queue.take();
//						System.out.println(Thread.currentThread().getName()+take);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		Runnable putTask = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						queue.put(UUID.randomUUID().toString());
//						System.out.println(Thread.currentThread().getName()+"put");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		// 获取数据的线程
		Thread t1 = new Thread(takeTask);
		Thread t2 = new Thread(takeTask);
		Thread t9 = new Thread(takeTask);
		t1.start();
		t2.start();
		t9.start();
		// 添加数据的线程
		Thread t3 = new Thread(putTask);
		Thread t4 = new Thread(putTask);
		t3.start();
		t4.start();

		System.out.println();
	}

}

class SimpleQueue {
	ReentrantLock lock = new ReentrantLock(false);;
	Condition notFull = lock.newCondition();
	Condition notEmpty = lock.newCondition();
	LinkedList<String> list = new LinkedList<String>();

	public SimpleQueue() {
	}

	public boolean add(String detail) {
		lock.lock();
		try {
			if (list.size() > 10) {
				return false;
			}
			list.addLast(detail);
			notEmpty.signalAll();
			System.out.println(Thread.currentThread().getName()+"notEmpty signall");
			return true;
		} finally {
			lock.unlock();
		}
	}

	public void put(String detail) throws InterruptedException {
		lock.lock();
		try {
			while (!add(detail)) {
				notFull.await();
				System.out.println(Thread.currentThread().getName()+"not Full await");
			}
		} finally {
			lock.unlock();
		}
	}

	public String take() throws InterruptedException {
		lock.lock();
		try {
			String get;
			while (null == (get = get())) {
				notEmpty.await();
				System.out.println(Thread.currentThread().getName()+"not Empty await");
			}
			return get;
		} finally {
			lock.unlock();
		}
	}

	public String get() {
		lock.lock();
		try {
			String first = list.poll();
			if (first == null)
				return null;
			notFull.signalAll();
			System.out.println(Thread.currentThread().getName()+"notFull signall");
			return first;
		} finally {
			lock.unlock();
		}
	}
}
