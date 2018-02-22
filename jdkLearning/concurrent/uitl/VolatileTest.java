package uitl;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class VolatileTest {
	int nomal = 0;
//	volatile int syn = 0;

	public static void main(String[] args) throws Exception {
//		�ؼ���Volatile��һ��java�Ĺؼ��֣����������ֶΡ�
//		�������ڶ��̷߳��ʣ��޸�ͬһ���ֶε�ʱ�����������дһ�������ö���д�Ĳ�����ָ���ֿ���
//		������ָ��д�Ĺ��̲�����;������;����ָ��Դﵽ�ֶε�ͬ��Ч����������ʵ����ʹ��Actom���İɡ����װ�ȫ
		new VolatileTest().test();

	}

	private void test() throws Exception {
		normal();
//		volatileTest();
	}

	private void volatileTest() {
		int num = 20;
		CyclicBarrier barrier = new CyclicBarrier(5);
		Runnable task = new Runnable() {
			@Override
			public void run() {
//				System.out.println(syn++);
				/*try {
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

			}
		};
		for (int i = 0; i < num; i++) {
			new Thread(task).start();
			;
		}
	}

	private void normal() throws Exception {
		int num = 2;
//		CyclicBarrier barrier = new CyclicBarrier(5);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				int time=100;
				while(time-->0) {
				System.out.println(nomal++);
				}
				/*try {
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		};
		/*ArrayList<Thread> list = new ArrayList();
		for (int i = 0; i < num; i++) {
			list.add(new Thread(task));
		}
		Thread.sleep(2000);
		for (Thread thread : list) {
			thread.start();
		}*/
		Thread t1 = new Thread(task);
		Thread t2 = new Thread(task);
		t1.start();
		t2.start();
	}
}
