package uitl;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

	public static void main(String[] args) throws Exception {
		// normal();
		int num = 5;
		Semaphore semaphore = new Semaphore(num);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "����");

				try {
					semaphore.tryAcquire(1, 3, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
					// �ͷ�ָ���������ź���
					semaphore.release(2);
					// �ͷ�ȫ�����ź���
//					 int drainPermits = semaphore.drainPermits();
					return;
				}
//				��ȡ�ȴ��Ķ��г���
				int queueLength = semaphore.getQueueLength();
//				��ȡ���õ��ź�����
				int availablePermits = semaphore.availablePermits();
				System.out.println("getQueueLength->" + queueLength + " | availablePermits->s" + availablePermits);
				System.out.println(Thread.currentThread().getName() + "�ر�");
			}
		};

		for (int i = 0; i < num * 3; i++) {
			new Thread(task).start();
		}
		Thread.sleep(10000);
	}

	private static void normal() throws InterruptedException {
		/*
		 * �ź����������޶�ͬʱ�������̵߳������� ������һ���򵥵����ӡ��߳�acquire�ͻ����1���ź��� ������ٵ�0���޷���ȡ���ź����������߳�������
		 * ������߳�release�ź������ͻỽ�ѵȴ����̣߳������ǿ��Ի�ȡ���ź��������û�����������ٴ��������ȴ�����
		 * �����п������̱߳��ź�����2������в��黹�ź�����release����Ȼ����2���߳�����
		 * ����������߳�������ɾ�release�����������ȴ��̱߳����ѣ�����������ơ�
		 * ������һ��faire����ƣ����ǻ��ѵ�ʱ���Ƿ���ݻ�ȡ�ź�����˳�����̡߳�
		 * ʵ������һ�����б����������̣߳���ƽ������¾��ǽ�ͷԪ�ػ��ѣ����ͷԪ������������һ��Ԫ�ز����� ����ƽ������������߳���ռ
		 */
		int num = 5;
		Semaphore semaphore = new Semaphore(num);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "����");
				try {
					semaphore.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "����");
			}
		};
		// ���ڿ������߳��ź�����2�������Ա�Ȼ�������߳�����
		for (int i = 0; i < num + 2; i++) {
			new Thread(task).start();
		}

		Thread.sleep(5000);
	}

}
