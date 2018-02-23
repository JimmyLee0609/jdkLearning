package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ReentrantReadWriteLockTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		/*
		 * 可重入的读写锁
		 * 当read锁lock的时候, write锁不能获取锁对象,会导致获取write锁的线程阻塞,不管是自己的线程还是其他线程
		 * 当read锁lock的时候可以再被read锁lock,不管是自己的read锁还是其他读写锁的read锁都可以.但是就不能被write锁lock
		 * 
		 * 当write锁lock的时候,自己的线程可以使用read/write(重入锁)锁来lock,
		 * 但是其他线程就不能使用这个write锁来lock,否则阻塞线程
		 * 并且,当write锁lock后,其他线程不能使用这个锁的read锁来lock,否则阻塞线程.
		 * 
		 * 简答来说就是read锁lock就不让write锁lock,但是read锁可以lock
		 * write锁lock就不让read/write锁lock
		 * condition和就和ReentrantLock是一样的,就有一点,read锁没有condition,
		 * 只有write锁有condition
		 * */
		ReentrantReadWriteLock oneLock = new ReentrantReadWriteLock();
		ReentrantReadWriteLock twoLock = new ReentrantReadWriteLock();

		ReadLock oneReadLock = oneLock.readLock();
		WriteLock oneWriteLock = oneLock.writeLock();
		Condition oneWriteLockCondition = oneWriteLock.newCondition();
		
		ReadLock twoReadLock2 = twoLock.readLock();
		WriteLock twoWriteLock = twoLock.writeLock();
		
		Runnable task = new Runnable() {
			@Override
			public void run() {
				oneWriteLock.lock();
				try {
					oneWriteLockCondition.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("oneWriteLock");
			}};
			Runnable mission = new Runnable() {
				@Override
				public void run() {
					twoWriteLock.lock();
					Condition newCondition = twoWriteLock.newCondition();
					System.out.println("twoWriteLock");
					oneReadLock.lock();
					System.out.println("twoWriteLock->oneWriteLock->oneReadLock");
					oneWriteLock.lock();
					System.out.println("twoWriteLock->oneWriteLock");
					
				}};
		new Thread(task).start();
		new Thread(mission).start();
		System.out.println();
		
		
//		operationExplain(lock);
	}

	private static void operationExplain(ReentrantReadWriteLock lock) {
		/*
		 * 返回等待获取读取或写入锁定的线程数的估计值。 该值仅为估计值， 因为在此方法遍历内部数据结构时，线程数可能会动态变化。
		 * 此方法设计用于监视系统状态，而不是用于同步控制。
		 */
		int queueLength = lock.getQueueLength();
		// 查询当前线程在此锁上存在的重入读取次数。
		// 读卡器线程对每个锁定动作都有一个锁定，但与解锁动作不匹配。
		int readHoldCount = lock.getReadHoldCount();
		// 查询为此锁持有的读锁的数量。 此方法设计用于监视系统状态，而不是用于同步控制。
		int readLockCount = lock.getReadLockCount();
		// 查询当前线程在此锁上保留的重入写入的数量。
		// 编写器线程对每个未与解锁操作匹配的锁定操作锁定一个锁定。
		int writeHoldCount = lock.getWriteHoldCount();
		// 查询写锁是否被任何线程保存。 此方法设计用于监视系统状态，而不是用于同步控制。
		boolean writeLocked = lock.isWriteLocked();
		// 查询是否有线程正在等待获取读取或写入锁定。 请注意，因为取消可能随时发生，
		// 所以真正的回报并不能保证任何其他线程都会获得锁定。 此方法主要用于监视系统状态。
		boolean hasQueuedThreads = lock.hasQueuedThreads();
		// 查询当前线程是否保持写入锁定。
		boolean writeLockedByCurrentThread = lock.isWriteLockedByCurrentThread();
		// 如果此锁的公平设置为true，则返回true。
		boolean fair = lock.isFair();
		// 查询给定线程是否正在等待获取读锁或写锁。 请注意，因为取消可能随时发生，
		// 所以真正的回报并不能保证此线程会获得锁定。 此方法主要用于监视系统状态。
		boolean hasQueuedThread = lock.hasQueuedThread(Thread.currentThread());

		WriteLock writeLock = lock.writeLock();
		Condition newCondition = writeLock.newCondition();

		boolean hasWaiters = lock.hasWaiters(newCondition);
		int waitQueueLength = lock.getWaitQueueLength(newCondition);

		ReadLock readLock = lock.readLock();
		ReadLock readLock2 = lock.readLock();
		Condition newCondition2 = readLock.newCondition();
	}

}
