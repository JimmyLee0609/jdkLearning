package uitl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierTest {

	public static void main(String[] args) throws Exception {
//		normal();
		
		action();
	}

	private static void action() throws InterruptedException {
		/*
		 * CyclicBarrier�Զ��ظ����ϣ������趨ÿ�����ϱ�ͻ��(�ﵽ�ٽ�ֵ)�����ᴥ�������runnable����һ��
		 * 
		 * */
		Runnable action = new Runnable() {
			@Override
			public void run() {
				System.out.println("action");
			}
		};
		
		int num = 5;
		CyclicBarrier barrier = new CyclicBarrier(num, action);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName()+"����");
					Thread.sleep(1000);
					
					barrier.await();
					
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"����");
			}
		};
		for (int i = 0; i < num*2; i++) {
			new Thread(task).start();;
		}
		Thread.sleep(5000);
	}

	private static void normal() throws InterruptedException {
		/*
		 * ����һ�����Զ��ظ������ϡ�ÿ���߳�await�ͻὫ�ٽ�-1,��ͬ�̲߳���await���Σ��ڶ����ǲ�����Ч��
		 * ÿ�������ٽ磬�ͻỽ��await���̣߳�Ȼ��ظ����ϣ���Ҫ�ٴα���߳�awaitȥ���������ٽ�ֵ���Żᷢ���ڶ���await
		 * 
		 */
		int num = 5;
		CyclicBarrier barrier = new CyclicBarrier(num);

		Runnable task = new Runnable() {
			@Override
			public void run() {
				int numberWaiting = barrier.getNumberWaiting();
				int parties = barrier.getParties();
				boolean broken = barrier.isBroken();

				System.out.println(Thread.currentThread().getName() + "����" + "  | getNumberWaiting->" + numberWaiting
						+ "  | getParties->" + parties + "| isBroken->" + broken);
				try {
					// int await = barrier.await(3,
					// TimeUnit.SECONDS);//��������ڵȴ�ָ����ʱ�仹û�ﵽ�ٽ�ͻ��׳��쳣���ж��߳�
					int await2 = barrier.await();
					Thread.sleep(1000);
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} /*
					 * catch (TimeoutException e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); return ; }
					 */
				int numberWaiting2 = barrier.getNumberWaiting();
				int parties2 = barrier.getParties();
				boolean broken2 = barrier.isBroken();

				System.out.println(Thread.currentThread().getName() + "����" + "  | getNumberWaiting->" + numberWaiting2
						+ "  | getParties->" + parties2 + "| isBroken->" + broken2);
				System.out.println(Thread.currentThread().getName() + "finish");
				try {
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("another await");
			}
		};
		for (int i = 0; i < num + 1; i++) {
			new Thread(task).start();
		}

		Thread.sleep(5000);
	}
}
