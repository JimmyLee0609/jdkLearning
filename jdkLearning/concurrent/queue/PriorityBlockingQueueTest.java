package queue;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueTest {

	public static void main(String[] args) {
		// �����߳�����ģʽ����,������FIFO LIFO������,�����Լ����㷨.����Ķ���,��Ҫ���ԱȽ�,��Ȼ˳������бȽ���
		// ÿ����Ӵ�ĩβ��ʼ��,���Ԫ�ر���ǰ��size-1>>2�Ĵ��ֱ�ӽ�Ԫ�طŵ���β,�������С,�ͼ��������Ǹ�ֵ��-1>>2ֱ���ҵ���ŵ�λ��,���Ǳ��������,
		// ��������λ�����й��ɵ��ǲ��̶�,����˵�Ƕ�̬��, �����߳� ��ʹ��10�ȼ������ȼ�,��ʱ��Ҳ��7�ȼ������ȼ��������������
		PriorityBlockingQueue<Object> queue = new PriorityBlockingQueue<>(15);
//		====���Ԫ��=======
		boolean add = queue.add("");// offer
		queue.put("");// offer,��������
		boolean offer = queue.offer("");
		boolean offer2 = queue.offer("", 1, TimeUnit.SECONDS);
//		=======��ȡԪ��=======
		Object element = queue.element();//peek  �׿�ָ��
		Object peek = queue.peek();//��ȡ��Ԫ��
		try {
			Object take = queue.take();// �������ȴ�
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object poll = queue.poll();
		try {
			Object poll2 = queue.poll(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		�Ƿ����ָ��ֵ
		boolean contains = queue.contains("");
	}

}
