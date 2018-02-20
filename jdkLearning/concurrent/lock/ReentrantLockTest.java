package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
	static ReentrantLock lock;
	Condition c=lock.newCondition();
	
	public static void main(String[] args) {
		//��������Ĭ��false�� �����߳�������������ȫ���̣߳�˭�õ�����˭��
//		true������ǣ������̻߳�ȡ����ʱ��˳�����������ǻ�ȡ������������һ��queue������
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
