package queue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {

	public static void main(String[] args) throws Exception {
//		simpleUse();
		/*�������һ������Ķ���   �����߳�   ÿ�ν���һ��Ԫ��  
		 * ����������ǵȴ��Ķ���Ԫ�أ���������һ�����ݵ�����
		 * 
		 * ÿ��put��takeһ��ƥ�䣬ֻҪ���߳�put�������ȴ�����take�ͻ����߳��໥��������
		 * 
		 * ÿ��put���Ԫ�أ��ỽ�ѻ�ȡ�ĵȴ��̣߳����б����������ݣ������ӵ�˲��û�еȴ���ȡ���У���������ӵ��̣߳������̱߳��浽�ȴ���Ӷ���
		 * ÿ��take��ȡԪ�أ��ỽ�ѵȴ���Ӷ��У������ȡ˲��û�еȴ���ӵ�Ԫ�أ���������ȡ���̣߳������̱߳��浽�ȴ���ȡ���С�
		 * 
		 * ��������ӻ�ȡ��������������һ��û�еȴ�����ֱ���׳��쳣����һЩ����ʱ�޵ģ���ʱ�ͻ��׳��쳣
		 * 
		 * */
		int num = 10;
		SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>(false);
		// ���е�ʣ����������0��
		int remainingCapacity2 = queue.remainingCapacity();
		Runnable putTask = new Runnable() {
			@Override
			public void run() {
				try {
					queue.put(15);
					int remainingCapacity = queue.remainingCapacity();
					System.out.println("finish put remainingCapacity"+remainingCapacity);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Runnable takeTask = new Runnable() {

			@Override
			public void run() {
				try {
					Integer take = queue.take();
					int remainingCapacity = queue.remainingCapacity();
					System.out.println("take-" + take+"-remainingCapacity-"+remainingCapacity);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		for (int i = 0; i < num / 2; i++) {
			new Thread(putTask).start();
			;
		}
		for (int i = 0; i < num / 2; i++) {
			new Thread(takeTask).start();
			;
		}
		System.out.println();
		
		
		
	}

	@SuppressWarnings("unused")
	private static void simpleUse() throws InterruptedException {
		// ʹ��tranfer��ͬ����ʵ��Ҳ��ʹ��CAS��ԭ������ӻ���ɾ��Ԫ�ص�ʱ��ʹ��CAS������
		// �ڶ��̵߳�����£��ͻ�ͬ������
		// true:FIFO false:��������ռʽ
		SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>(false);
//		queue.put(15);// 
		boolean offer2 = queue.offer(13, 1, TimeUnit.SECONDS);//��ӵ�ͷ����ʱ���أ�����̱߳��жϣ��׳��쳣��û���жϷ���false
//		==һ���Ƽ�������������==
		boolean add = queue.add(5);// offer
		boolean offer = queue.offer(11);// ��ӵ�ͷ�����û�еȴ���ȡ���У��ͻ��׳��쳣
		Integer element = queue.element();// peek return null���߳��£�˲ʱֵû���壬�����ó�����
		// peek return null
		Integer peek = queue.peek();// peek

		// ȡ��ͷ
		Integer poll2 = queue.poll(1, TimeUnit.SECONDS);// ��ȡͷԪ�أ����û�еȴ���Ӷ��У���ʱ���أ�����̱߳��жϣ��׳��쳣�����û���жϷ���false
		Integer take = queue.take();// ��ȡͷֵ�����û�еȴ���Ӷ��У��߳��������������ӵ���null�׳��쳣
//		===һ���Ƽ���������==
		Integer poll = queue.poll();//��ȡͷֵ�������ʱû�еȴ���Ӷ��н��׳��쳣
		Integer remove = queue.remove();// poll����������ǿյ�poll����null���׳��쳣
		boolean remove2 = queue.remove(16);// poll����������ǿյ�poll����null���׳��쳣
	}

}
