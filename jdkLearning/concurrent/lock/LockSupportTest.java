package lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
/*
 * 线程阻塞的帮助类   park就会阻塞线程,unPark(指定线程)就会唤醒线程.
 * park的时候可一使用传入的Object对象,就是java类的对象都可以作为锁,null也可以.
 * 唤醒的时候必须要传入线程的引用
 * 
 * */
		Thread t1 = new Thread(() -> {
//			阻塞指定的nano秒
			LockSupport.parkNanos(Long.MAX_VALUE);
			System.out.println("parkNanos");

			LockSupport.parkNanos("myName", Long.MAX_VALUE);
			System.out.println("myName ,  parkNanos");
//			阻塞指定的毫秒
			LockSupport.parkUntil(Long.MAX_VALUE);
			System.out.println("parkUntil");

			LockSupport.parkUntil("  parkUntil", Long.MAX_VALUE);
			System.out.println("--->==  parkUntil");
//			一直阻塞直到有其他线程unPark
			LockSupport.park();
			System.out.println("finished park");
		});
		t1.start();

		Object blocker = LockSupport.getBlocker(t1);
		LockSupport.unpark(t1);
		Object blocker2 = LockSupport.getBlocker(t1);//myName
		LockSupport.unpark(t1);
		Object blocker3 = LockSupport.getBlocker(t1);
		LockSupport.unpark(t1);
		Object blocker4 = LockSupport.getBlocker(t1);//  parkUntil
		LockSupport.unpark(t1);
		Object blocker5 = LockSupport.getBlocker(t1);
		LockSupport.unpark(t1);

	}

}
