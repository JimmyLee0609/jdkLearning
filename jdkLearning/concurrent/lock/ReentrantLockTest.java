package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
	static ReentrantLock lock;
	Condition c=lock.newCondition();
	
	public static void main(String[] args) {
		//排他锁，默认false， 就像线程抢夺那样唤醒全部线程，谁拿到就是谁的
//		true的情况是，根据线程获取锁的时间顺序依次让它们获取到锁，里面有一个queue来处理
		lock= new ReentrantLock(true);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				add(5,3);
			}
		};
		Thread t1 = new Thread(task);
		Thread t2 = new Thread(task);
		t1.start();
		t2.start();
	}
	
	public static  void add(int a,int b) {
		lock.lock();
		System.out.println(a+b);
		lock.unlock();
	}

}
