package lock;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	public static void main(String[] args) {
		/*
		 * �ؽ�����,����֧���ؽ������, ��ʾ�����ܹ�֧��һ���̶߳���Դ���ظ�����, lock������Լ�condition ������֧�ֻ�ȡ�����Ƿ�FIFO
		 * ��ʵconcurrent���е�queue���Ǻܺõ�����, ����ʹ��һ���򵥵Ķ��е���������Ч��
		 * debugģʽ��,ֻҪ��put���߳�ֹͣ,����û������,take���߳̾ͻ�����
		 * condition�൱�ڽ��̷߳���һ���߳�����,���Խ�ָ�������̻߳���,
		 * 
		 * Lock ��������ĳ��������˲��,����ס��˲��,�����߳��޷�����
		 * 
		 * ,Ȼ��ʹ��conditionȥ�����̵߳�˯��,�ͻ���,
		 * ��Condition��״̬��,�߳����ͷ���Lock��������,�����߳̿��Ի�ȡ����,
		 * 
		 * �������ķ�ʽ���ﵽͬ����Ч��
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
		// ��ȡ���ݵ��߳�
		Thread t1 = new Thread(takeTask);
		Thread t2 = new Thread(takeTask);
		Thread t9 = new Thread(takeTask);
		t1.start();
		t2.start();
		t9.start();
		// ������ݵ��߳�
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
