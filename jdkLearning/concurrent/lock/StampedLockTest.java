package lock;

import java.util.concurrent.Phaser;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {
	static double x = 5.5;
	static double y = 7.2;

	volatile static long lockStamp = 0L;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		/*
		 * 维护一个volatile的long字段，作为锁的标识。 每次获取锁将会改变这个字段的值，在释放锁之前，其他线程无法改变该值，线程将被阻塞。
		 * 在释放锁后，将唤醒被阻塞的线程，重新获取锁，基于这样的方式来同步数据
		 * 
		 * 获取read锁然后lock，获取read锁的线程不会被阻断，并且更新的锁的stamp， 但是一旦获取write锁的线程就会被阻塞,就连自己的线程也会被阻塞
		 * ， 下面的例子中设置50个线程，首先read锁lock,争夺的线程中有read,有write，一旦write阻塞了，其他的read线程也被阻塞了。
		 * 需要等待第一个获取read锁释放才会被唤醒.
		 * 
		 * 
		 * 当获取write锁然后lock,在释放前，本线程/其他线程获取read/write锁都阻塞，
		 * 
		 * 将锁的stamp做成volatile的静态字段，并且只要变更stamp就要更新，只要一个线程lock无论read/write。其他线程都会阻塞，
		 * 因为无法变更stamp的值 在这种情况下的convert就不会受到其他线程的影响。 如果锁的stamp只是自己的线程可见,
		 * 
		 * 在read锁convert成write锁的时候，由于只有read锁的情况可会出现多个线程同时变更stamp，
		 * 在转换为write的时候，将阻塞其他read的线程，这个过程可能会有一些read的线程继续工作，一些线程被阻塞。
		 * 一旦转成的write锁，就会将后面获取锁的线程阻塞
		 * 
		 * 在write锁convert成read锁的时候会将阻塞的线程唤醒，在转换成功前可有多个线程获取锁释放锁。
		 * 转换成功后，再次获取write锁就被阻塞了，如果有write锁阻塞，就连获取read锁的也会阻塞
		 */
		StampedLock lock = new StampedLock();
		Phaser phaser = new Phaser(50);

		convert(lock, phaser);
		;
		System.out.println();
		convertVolatile(lock, phaser);
		standardWay(lock);
		simpleMethod(lock);

		optimisticWay(lock);
		// convert的时候有可能会被其他线程抢到锁，所以这个方式比较安全
		convertWay(lock);

		System.out.println();
	}

	private static void convert(StampedLock lock, Phaser phaser) {
		Runnable writeTaskConvert = new Runnable() {
			@Override
			public void run() {
				long lockStamp = lock.writeLock();
				phaser.arrive();
				System.out.println(Thread.currentThread().getName() + lockStamp);
				lockStamp = lock.tryConvertToReadLock(lockStamp);
				boolean readLocked = lock.isReadLocked();
				System.out.println(Thread.currentThread().getName() + lockStamp + "--isReadLocked--" + readLocked);
				lockStamp = lock.tryConvertToWriteLock(lockStamp);
				boolean writeLocked = lock.isWriteLocked();
				System.out.println(Thread.currentThread().getName() + lockStamp + "--isWriteLocked--" + writeLocked);
				lock.unlock(lockStamp);
			}
		};
		Runnable readTaskConvert = new Runnable() {
			@Override
			public void run() {
				long lockStamp = lock.readLock();
				phaser.arrive();
				System.out.println(Thread.currentThread().getName() + lockStamp);
				// lock.unlock(lockStamp);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lockStamp = lock.tryConvertToWriteLock(lockStamp);
				boolean writeLocked = lock.isWriteLocked();
				System.out.println(
						Thread.currentThread().getName() + "==" + lockStamp + "--isWriteLocked--" + writeLocked);

				lockStamp = lock.tryConvertToReadLock(lockStamp);
				boolean readLocked = lock.isReadLocked();
				System.out
						.println(Thread.currentThread().getName() + "==" + lockStamp + "--isReadLocked--" + readLocked);
				lock.unlock(lockStamp);
			}
		};

		Runnable readTask = new Runnable() {
			@Override
			public void run() {
				phaser.arriveAndAwaitAdvance();
				System.out.println(Thread.currentThread().getName() + "--arrive--");
				long lockStamp = lock.readLock();
				boolean readLocked = lock.isReadLocked();
				boolean writeLocked = lock.isWriteLocked();
				System.out.println(Thread.currentThread().getName() + "==" + lockStamp + "--isWriteLocked--"
						+ writeLocked + "--isReadLocked--" + readLocked);
				lock.unlock(lockStamp);
			}
		};
		Runnable writeTask = new Runnable() {
			@Override
			public void run() {
				phaser.arriveAndAwaitAdvance();
				System.out.println(Thread.currentThread().getName() + "==arrive==");
				long lockStamp = lock.writeLock();
				boolean readLocked = lock.isReadLocked();
				boolean writeLocked = lock.isWriteLocked();
				System.out.println(Thread.currentThread().getName() + "==" + lockStamp + "--isWriteLocked--"
						+ writeLocked + "--isReadLocked--" + readLocked);
				lock.unlock(lockStamp);
			}
		};
		for (int i = 0; i < 24; i++) {
			new Thread(readTask).start();
			;
		}
		for (int i = 0; i < 25; i++) {
			new Thread(writeTask).start();
			;
		}

		;
		new Thread(writeTaskConvert).start();
		new Thread(readTaskConvert).start();
		;
	}

	private static void convertVolatile(StampedLock lock, Phaser phaser) {
		Runnable writeTaskConvert = new Runnable() {
			@Override
			public void run() {
				lockStamp = lock.writeLock();
				phaser.arrive();
				System.out.println(Thread.currentThread().getName() + lockStamp);
				lockStamp = lock.tryConvertToReadLock(lockStamp);
				boolean readLocked = lock.isReadLocked();
				System.out.println(Thread.currentThread().getName() + lockStamp + "--isReadLocked--" + readLocked);
				lockStamp = lock.tryConvertToWriteLock(lockStamp);
				boolean writeLocked = lock.isWriteLocked();
				System.out.println(Thread.currentThread().getName() + lockStamp + "--isWriteLocked--" + writeLocked);
			}
		};
		Runnable readTaskConvert = new Runnable() {
			@Override
			public void run() {
				lockStamp = lock.readLock();
				phaser.arrive();
				System.out.println(Thread.currentThread().getName() + lockStamp);
				// lock.unlock(lockStamp);
				lockStamp = lock.tryConvertToWriteLock(lockStamp);
				boolean writeLocked = lock.isWriteLocked();
				System.out.println(Thread.currentThread().getName() + lockStamp + "--isWriteLocked--" + writeLocked);

				lockStamp = lock.tryConvertToReadLock(lockStamp);
				boolean readLocked = lock.isReadLocked();
				System.out.println(Thread.currentThread().getName() + lockStamp + "--isReadLocked--" + readLocked);
				lock.unlock(lockStamp);
			}
		};

		Runnable readTask = new Runnable() {
			@Override
			public void run() {
				phaser.arriveAndAwaitAdvance();

				System.out.println(Thread.currentThread().getName() + "--arrive");
				lockStamp = lock.readLock();
				System.out.println(Thread.currentThread().getName() + lockStamp);
				lock.unlock(lockStamp);
			}
		};
		Runnable writeTask = new Runnable() {
			@Override
			public void run() {
				phaser.arriveAndAwaitAdvance();
				System.out.println(Thread.currentThread().getName() + "arrive");
				lockStamp = lock.writeLock();
				System.out.println(Thread.currentThread().getName() + lockStamp);
				lock.unlock(lockStamp);
			}
		};
		for (int i = 0; i < 25; i++) {
			new Thread(readTask).start();
			;
		}
		for (int i = 0; i < 24; i++) {
			new Thread(writeTask).start();
			;
		}
		// new Thread(writeTaskConvert).start();
		new Thread(readTaskConvert).start();
	}

	private static void simpleMethod(StampedLock lock) {
		// 就是包装了一下接口，就像适配器，实际用到还是StampedLock
		Lock asReadLock = lock.asReadLock();
		ReadWriteLock asReadWriteLock = lock.asReadWriteLock();
		Lock asWriteLock = lock.asWriteLock();

		// 判断是被那种种锁的方式获取了对象。在当前线程判断可以用来指定unLock的方式
		boolean readLocked = lock.isReadLocked();
		boolean writeLocked = lock.isWriteLocked();

		// 可以用在线程池的线程中，在线程被标记中断的时候获取锁对象会抛出异常
		// long readLockInterruptibly = lock.readLockInterruptibly();
		// long writeLockInterruptibly = lock.writeLockInterruptibly();
		// 判断一个锁是否有效。因为optimistic的模式下不会阻塞线程，但是如果线程锁住的时候，锁被其他线程获取了stamp就会变更，这里可以判断锁的部分是否有效
		boolean validate = lock.validate(lock.tryOptimisticRead());
		// 尝试将一个锁转换到指定的模式锁，如果转换成功就会返回stamp，转换失败就返回0L
		long tryConvertToWriteLock = lock.tryConvertToWriteLock(lock.readLock());
	}

	private static void standardWay(StampedLock lock) {
		Runnable readTask = new Runnable() {

			@SuppressWarnings("unused")
			@Override
			public void run() {
				long writeLock2 = lock.writeLock();
				System.out.println();
				long writeLock1 = lock.writeLock();
				long readLock = lock.readLock();
				long readLock2 = lock.readLock();
			}
		};
		Runnable writeTask = new Runnable() {
			@SuppressWarnings("unused")
			@Override
			public void run() {
				long writeLock = lock.writeLock();
				System.out.println();
				long readLock = lock.readLock();
				System.out.println();
			}
		};

		new Thread(readTask).start();
		;
		new Thread(writeTask).start();
		System.out.println();
	}

	private static void convertWay(StampedLock lock) {
		Runnable writeTask = new Runnable() {
			@Override
			public void run() {
				long stamp = lock.readLock();
				double currX = x, currY = y;
				try {
					while (currX == x && currY == y) {
						// 尝试将锁转换为写锁,实际不会阻塞线程,只会将stamp变更
						long tryConvertToWriteLock = lock.tryConvertToWriteLock(stamp);

						if (tryConvertToWriteLock != 0) {// 检测写锁是否有效
							stamp = tryConvertToWriteLock;
							x = currX + 8;
							y = currY + 8;
							break;
						} else {
							// 写锁没效就需要换成会阻塞的写锁
							lock.unlock(stamp);
							stamp = lock.writeLock();// 获取写锁,会阻塞线程
						}
					}
				} finally {
					lock.unlock(stamp);
				}
			}
		};
		Thread t2 = new Thread(writeTask);
		t2.start();
	}

	private static void optimisticWay(StampedLock lock) {
		Runnable readTask = new Runnable() {
			@Override
			public void run() {
				// 尝试获取读锁,但是这个并不会阻塞线程,仅仅返回一个lock的当前标记
				long stamp = lock.tryOptimisticRead();
				// 读取当前数据的副本
				double currX = x, currY = y;
				if (!lock.validate(stamp)) {// 检测锁是否有效,如果有其他线程尝试获取其他任何形式的锁就会导致stamp值变更,
					// 如果stamp无效了.就是有线程获取过锁了.就需要使用会阻塞的锁,其他线程不能获取锁
					stamp = lock.readLock();// 没有获取到锁的时候会阻塞线程
					try {
						currX = x;
						currY = y;
					} finally {
						lock.unlockRead(stamp);// 释放锁,可以使用unlock()安全不需要知道是那种锁
					}
				}
			}
		};
		Thread t1 = new Thread(readTask);
		t1.start();
	}

}
