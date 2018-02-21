package uitl;

import java.util.UUID;
import java.util.concurrent.Exchanger;

public class ExchangerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		sameThreadGroup();
//		�ڲ�ͬ��ThreadGroup��exchanger��һ�����õ�
		ThreadGroup g1 = new ThreadGroup("group1");
		ThreadGroup g2 = new ThreadGroup("group2");
		Exchanger<String> exchanger = new Exchanger<String>();
		Runnable task = new Runnable() {
			@Override
			public void run() {
				UUID uuid = UUID.randomUUID();
				String string = uuid.toString();
				try {
					System.out.println(Thread.currentThread().getThreadGroup().getName()+"->"+Thread.currentThread().getName());
					String exchange = exchanger.exchange(string);
					System.out.println(string+"->"+exchange);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Runnable run=new Runnable() {
			@Override
			public void run() {
				Thread t5 = new Thread(g1,task);
				Thread t6 = new Thread(g2,task);
				t5.start();
				t6.start();
			}};
		
		Thread t1 = new Thread(run);
		Thread t2 = new Thread(run);
		Thread t3 = new Thread(run);
		Thread t4 = new Thread(run);
		
		Thread t7 = new Thread(task);
		Thread t8 = new Thread(task);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t7.start();
		t8.start();
		System.out.println();
	}

	private static void sameThreadGroup() {
		/*
		 * ��ͬ�̵߳����ݽ����� ���̵߳���exchange��ʱ�򣬵�ǰ�߳�������ֱ������һ���߳�Ҳ����exchange����Ҫ���������ݾͻύ��
		 * ����������߳�Ҳ��Ҫexchange������ֻ�������ȴ����ĸ��̵߳�exchange��
		 */
		int num = 2;
		Exchanger<String> exchanger = new Exchanger<String>();

		Runnable task = new Runnable() {
			@Override
			public void run() {
				UUID randomUUID = UUID.randomUUID();
				String string = randomUUID.toString();
				try {
					String exchange = exchanger.exchange(string);
					System.out.println(Thread.currentThread().getName() + "->" + string + "->" + exchange);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			}
		};
		for (int i = 0; i < num + 1; i++)
			new Thread(task).start();
	}

}
