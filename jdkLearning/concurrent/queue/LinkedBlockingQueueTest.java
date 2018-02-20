package queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueTest {

	public static void main(String[] args) throws InterruptedException {
		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(50);
		boolean add = queue.add(15);//offer
//		��Ԫ�طŵ����е�β��,�����ӳɹ��ͻ�����Ϊtake�ȴ����߳�
//		�����������,�ȴ�ָ����ʱ�������һ��,���л������ͷ���false
		boolean offer = queue.offer(21);
		boolean offer2 = queue.offer(21, 1, TimeUnit.SECONDS);
//		��Ԫ����ӵ����е�ĩβ,�����ӳɹ��ͻ�����Ϊtake�ȴ����߳�,
//		�����������,�������̵߳ȴ�,�յ�����֪ͨ�ٴ����,���������ٴ������߳�
		queue.put(5);
	
		Integer element = queue.element();//peek
		Integer peek = queue.peek();//�鿴��Ԫ��,����Ϊ�շ���null
//		��ȡ��Ԫ��,,��ȡ�ɹ�������Ϊ��������put�������߳�.
//		�������Ϊ�յȴ�ָ����ʱ���ٻ�ȡ,���л��ǿ�,����Null
		Integer poll = queue.poll();
		Integer poll2 = queue.poll(1, TimeUnit.SECONDS);
		Integer remove = queue.remove();//poll�׿�ָ���쳣
		boolean remove2 = queue.remove(5);//��ͷ��ʼ��,�鵽��һ���Ƴ�,�鲻������false
		//��ȡͷԪ��,��ȡ�ɹ��ͻ�����Ϊ������put�ȴ����߳�.
//		��ȡʧ��������ǰ�߳�,ֱ��������,�ٻ�ȡһ��,���л��ǿվ��ٴ�����
		Integer take = queue.take();
	}

}
