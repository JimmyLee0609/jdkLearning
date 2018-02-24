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
		 * ά��һ��volatile��long�ֶΣ���Ϊ���ı�ʶ�� ÿ�λ�ȡ������ı�����ֶε�ֵ�����ͷ���֮ǰ�������߳��޷��ı��ֵ���߳̽���������
		 * ���ͷ����󣬽����ѱ��������̣߳����»�ȡ�������������ķ�ʽ��ͬ������
		 * 
		 * ��ȡread��Ȼ��lock����ȡread�����̲߳��ᱻ��ϣ����Ҹ��µ�����stamp�� ����һ����ȡwrite�����߳̾ͻᱻ����,�����Լ����߳�Ҳ�ᱻ����
		 * �� ���������������50���̣߳�����read��lock,������߳�����read,��write��һ��write�����ˣ�������read�߳�Ҳ�������ˡ�
		 * ��Ҫ�ȴ���һ����ȡread���ͷŲŻᱻ����.
		 * 
		 * 
		 * ����ȡwrite��Ȼ��lock,���ͷ�ǰ�����߳�/�����̻߳�ȡread/write����������
		 * 
		 * ������stamp����volatile�ľ�̬�ֶΣ�����ֻҪ���stamp��Ҫ���£�ֻҪһ���߳�lock����read/write�������̶߳���������
		 * ��Ϊ�޷����stamp��ֵ ����������µ�convert�Ͳ����ܵ������̵߳�Ӱ�졣 �������stampֻ���Լ����߳̿ɼ�,
		 * 
		 * ��read��convert��write����ʱ������ֻ��read��������ɻ���ֶ���߳�ͬʱ���stamp��
		 * ��ת��Ϊwrite��ʱ�򣬽���������read���̣߳�������̿��ܻ���һЩread���̼߳���������һЩ�̱߳�������
		 * һ��ת�ɵ�write�����ͻὫ�����ȡ�����߳�����
		 * 
		 * ��write��convert��read����ʱ��Ὣ�������̻߳��ѣ���ת���ɹ�ǰ���ж���̻߳�ȡ���ͷ�����
		 * ת���ɹ����ٴλ�ȡwrite���ͱ������ˣ������write��������������ȡread����Ҳ������
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
		// convert��ʱ���п��ܻᱻ�����߳������������������ʽ�Ƚϰ�ȫ
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
		// ���ǰ�װ��һ�½ӿڣ�������������ʵ���õ�����StampedLock
		Lock asReadLock = lock.asReadLock();
		ReadWriteLock asReadWriteLock = lock.asReadWriteLock();
		Lock asWriteLock = lock.asWriteLock();

		// �ж��Ǳ����������ķ�ʽ��ȡ�˶����ڵ�ǰ�߳��жϿ�������ָ��unLock�ķ�ʽ
		boolean readLocked = lock.isReadLocked();
		boolean writeLocked = lock.isWriteLocked();

		// ���������̳߳ص��߳��У����̱߳�����жϵ�ʱ���ȡ��������׳��쳣
		// long readLockInterruptibly = lock.readLockInterruptibly();
		// long writeLockInterruptibly = lock.writeLockInterruptibly();
		// �ж�һ�����Ƿ���Ч����Ϊoptimistic��ģʽ�²��������̣߳���������߳���ס��ʱ�����������̻߳�ȡ��stamp�ͻ�������������ж����Ĳ����Ƿ���Ч
		boolean validate = lock.validate(lock.tryOptimisticRead());
		// ���Խ�һ����ת����ָ����ģʽ�������ת���ɹ��ͻ᷵��stamp��ת��ʧ�ܾͷ���0L
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
						// ���Խ���ת��Ϊд��,ʵ�ʲ��������߳�,ֻ�Ὣstamp���
						long tryConvertToWriteLock = lock.tryConvertToWriteLock(stamp);

						if (tryConvertToWriteLock != 0) {// ���д���Ƿ���Ч
							stamp = tryConvertToWriteLock;
							x = currX + 8;
							y = currY + 8;
							break;
						} else {
							// д��ûЧ����Ҫ���ɻ�������д��
							lock.unlock(stamp);
							stamp = lock.writeLock();// ��ȡд��,�������߳�
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
				// ���Ի�ȡ����,������������������߳�,��������һ��lock�ĵ�ǰ���
				long stamp = lock.tryOptimisticRead();
				// ��ȡ��ǰ���ݵĸ���
				double currX = x, currY = y;
				if (!lock.validate(stamp)) {// ������Ƿ���Ч,����������̳߳��Ի�ȡ�����κ���ʽ�����ͻᵼ��stampֵ���,
					// ���stamp��Ч��.�������̻߳�ȡ������.����Ҫʹ�û���������,�����̲߳��ܻ�ȡ��
					stamp = lock.readLock();// û�л�ȡ������ʱ��������߳�
					try {
						currX = x;
						currY = y;
					} finally {
						lock.unlockRead(stamp);// �ͷ���,����ʹ��unlock()��ȫ����Ҫ֪����������
					}
				}
			}
		};
		Thread t1 = new Thread(readTask);
		t1.start();
	}

}
