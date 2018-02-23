package queue;

import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class LinkedTransferQueueTest {
	static LinkedTransferQueue<Integer> queue;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		/*
		 * ������еĽṹ��һ������FIFO
		 * ÿ��add��offer��put��ʱ��Ὣ���ݴ�ŵ����е�ĩβ��
		 * ÿ��poll��ʱ��Ὣ���еĵ�һ��Ԫ�ػ�ȡ
		 * transfer,��Ԫ�ؽ���Consumer,��һֱ�����߳�ֱ����Consumer����ȡ
		 * tryTransfer���ָ���ȴ�ʱ�䣬�ͻ������ȴ����ȴ��ڼ佫Ԫ�طŵ����еĽ�β����ʱ�Ὣ����ڶ��е�Ԫ��ɾ��������false
		 * transfer����һ���ص���ǣ���������̻߳�ȡ���е�Ԫ�أ����ǻ�û�л�ȡ��transfer��Ԫ�أ����Ԫ��Ҳ�ᳬʱ
		 * ��ȡԪ�ص�poll(long timeout, TimeUnit unit)��ȴ�ָ����ʱ���������Consumer��getWaitingConsumerCountҲ�ǻ�ȡ�ȴ�������
		 * ��һ���ر���ǣ��ȴ����̶߳��еĽṹִ�е���LIFO,ֻҪqueue��Ԫ�صģ���һ�������������һ�������̡߳�
		 * */
		
		queue = new LinkedTransferQueue<Integer>();
		// Ԫ����ӵ���β�����ȱ�֤һ�����е�Ԫ��
		boolean add = queue.add(55);
		 queue.add(55);
		 queue.add(56);
		 queue.add(57);
		 queue.add(58);
		 queue.add(59);
		 queue.add(60);
		 queue.add(61);
		 queue.add(62);
		 queue.add(63);
		 queue.add(64);
		 queue.add(65);
		 queue.add(66);
		boolean offer = queue.offer(16);
		boolean offer2 = queue.offer(17, 1, TimeUnit.SECONDS);
		// ��Ԫ�ش浽��β
		queue.put(81);

		int size = queue.size();// 4
		int remainingCapacity = queue.remainingCapacity();// int.max-size
		int waitingConsumerCount = queue.getWaitingConsumerCount();// 0
		boolean hasWaitingConsumer = queue.hasWaitingConsumer();// false
//		���������̻߳�ȡԪ��
		createSimpleConsumer();
		
		// ��������ȡ��Ԫ�أ��������̵߳ȴ���Ԫ��׼���ã�ǰ�������Ԫ��
		Integer take = queue.take();
		// �������Ƴ���Ԫ��
		Integer peek = queue.peek();
		// ��ȡ��Ԫ��
		Integer poll = queue.poll();
		// ��ȡ��Ԫ�أ���ʱ���쳣����null������ڳ�ʱǰ���жϣ����׳��쳣
		Integer poll2 = queue.poll(1, TimeUnit.SECONDS);

		int size2 = queue.size();// 1

		LinkedTransferQueue<Integer> nest	=queue;//����debug������״̬�ı���
		// ���Խ�ָ��Ԫ�ش��ݸ��ȴ����ܴ��ݵ��̣߳�˲ʱû�ɹ��������ݴ浽��β�������̵߳ȴ��߳̽���
		
		Random random = new Random(80);//���������
		while (true) {//�����߳���poll(1,TimeUnit.SECONDS)��ʱ��Ż��еȴ����߳�
			int count = queue.getWaitingConsumerCount();
			if(count>100)break;
			System.out.println("�ȴ����߳�������"+count+"���еĴ�С"+queue.size());
//			�ṩ���Խ�����Ԫ�أ������һ���߳��ṩԪ�أ�����̻߳�ȡԪ�أ��෽��Ҫ���ó�һ��ĵȴ�ʱ��
//			����ҲҪ����һ���ĵȴ�ʱ��������CUP��ʱ���ת
			boolean tryTransfer3 = queue.tryTransfer(random.nextInt(),1,TimeUnit.MILLISECONDS);
			Thread.sleep(100);
		}
		// ���Խ�ָ��Ԫ�ش��ݸ��ȴ����ܴ��ݵ��߳�
		// ˲ʱû�ɹ��������ݴ浽��β����ָ����ʱ�仹û�������ģ��ͽ��浽�ӵ�Ԫ���Ƴ���������false���̱߳��ж��׳��쳣
		boolean tryTransfer2 = queue.tryTransfer(12, 1, TimeUnit.SECONDS);
		boolean tryTransfer = queue.tryTransfer(15);// ˲ʱ��û�ɹ�����false,
		System.out.println("end");
	}

	private static void createSimpleConsumer() throws Exception {
		Runnable task = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Integer take;
					try {
						//
						take = queue.poll(1000,TimeUnit.MILLISECONDS);//��ȡ��Ԫ�أ���������ǿվͷ���null
						Thread.sleep(800);
//						Integer take2 = queue.take();//��ȡ��Ԫ�أ��������Ϊ���׳��쳣
						if(null!=take)
						System.out.println("i got "+take);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}				
			}};
		Thread t1 = new Thread(task);
		Thread t2 = new Thread(task);
		Thread t3 = new Thread(task);
		Thread t4 = new Thread(task);
		Thread t5 = new Thread(task);
		Thread t6 = new Thread(task);
		t1.start();;
		t2.start();
		t3.start();
//		t4.start();
//		t5.start();
//		t6.start();
	}

}
