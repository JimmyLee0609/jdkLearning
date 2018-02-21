package uitl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

	public static void main(String[] args) {
		/*ConutDownLatch���ڶ��߳��½���ͬ����һ�����ߡ�
		num��ֻ��Ҫ���ٽ��ж��ٴ�countDown���ܵ��ﱻ���ѵ��ٽ磬ÿ�ε���countDown����ʹ�ٽ�ֵ-1
		����һ���򵥵����ӡ���ҪcountDown5�β��ܵ����ٽ磬
		�������ȵȴ�1��Ȼ��countDown,Ȼ�������߳�
		ֱ���׸��߳�countDown�ﵽ�ٽ磬�������̱߳����ѡ�һͬ����.
		��Ҫע����ǣ�����һ��һ���ԵĹ��ߣ�countDown����0�Ͳ���reset��֮����߳̾Ͳ���������
		�÷����½�����ÿ����countDownȻ��await(),�����߳̽��������������̼߳���countDown��await.ֱ���ٽ��
		*/
		int num = 5;
		CountDownLatch latch = new CountDownLatch(num);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "�߳̿���");
				try {
					latch.await(1,TimeUnit.SECONDS);//�����߳�1��
					long count = latch.getCount();
					System.out.println(count);
					latch.countDown();
					latch.await();//�����߳�ֱ��������
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() +"���CountDown");
			}
		};
		for (int i = 0; i < num+3; i++) {
			new Thread(task).start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
