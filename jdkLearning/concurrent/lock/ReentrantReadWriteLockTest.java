package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ReentrantReadWriteLockTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		/*
		 * ������Ķ�д��
		 * ��read��lock��ʱ��, write�����ܻ�ȡ������,�ᵼ�»�ȡwrite�����߳�����,�������Լ����̻߳��������߳�
		 * ��read��lock��ʱ������ٱ�read��lock,�������Լ���read������������д����read��������.���ǾͲ��ܱ�write��lock
		 * 
		 * ��write��lock��ʱ��,�Լ����߳̿���ʹ��read/write(������)����lock,
		 * ���������߳̾Ͳ���ʹ�����write����lock,���������߳�
		 * ����,��write��lock��,�����̲߳���ʹ���������read����lock,���������߳�.
		 * 
		 * �����˵����read��lock�Ͳ���write��lock,����read������lock
		 * write��lock�Ͳ���read/write��lock
		 * condition�;ͺ�ReentrantLock��һ����,����һ��,read��û��condition,
		 * ֻ��write����condition
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
		 * ���صȴ���ȡ��ȡ��д���������߳����Ĺ���ֵ�� ��ֵ��Ϊ����ֵ�� ��Ϊ�ڴ˷��������ڲ����ݽṹʱ���߳������ܻᶯ̬�仯��
		 * �˷���������ڼ���ϵͳ״̬������������ͬ�����ơ�
		 */
		int queueLength = lock.getQueueLength();
		// ��ѯ��ǰ�߳��ڴ����ϴ��ڵ������ȡ������
		// �������̶߳�ÿ��������������һ���������������������ƥ�䡣
		int readHoldCount = lock.getReadHoldCount();
		// ��ѯΪ�������еĶ����������� �˷���������ڼ���ϵͳ״̬������������ͬ�����ơ�
		int readLockCount = lock.getReadLockCount();
		// ��ѯ��ǰ�߳��ڴ����ϱ���������д���������
		// ��д���̶߳�ÿ��δ���������ƥ���������������һ��������
		int writeHoldCount = lock.getWriteHoldCount();
		// ��ѯд���Ƿ��κ��̱߳��档 �˷���������ڼ���ϵͳ״̬������������ͬ�����ơ�
		boolean writeLocked = lock.isWriteLocked();
		// ��ѯ�Ƿ����߳����ڵȴ���ȡ��ȡ��д�������� ��ע�⣬��Ϊȡ��������ʱ������
		// ���������Ļر������ܱ�֤�κ������̶߳����������� �˷�����Ҫ���ڼ���ϵͳ״̬��
		boolean hasQueuedThreads = lock.hasQueuedThreads();
		// ��ѯ��ǰ�߳��Ƿ񱣳�д��������
		boolean writeLockedByCurrentThread = lock.isWriteLockedByCurrentThread();
		// ��������Ĺ�ƽ����Ϊtrue���򷵻�true��
		boolean fair = lock.isFair();
		// ��ѯ�����߳��Ƿ����ڵȴ���ȡ������д���� ��ע�⣬��Ϊȡ��������ʱ������
		// ���������Ļر������ܱ�֤���̻߳��������� �˷�����Ҫ���ڼ���ϵͳ״̬��
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
