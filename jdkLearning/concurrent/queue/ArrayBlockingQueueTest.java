package queue;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueTest {

	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) throws Exception {
		// ���鹤������ capacity������ fair: true->FIFOͷȡβ�ӣ�false��ȷ��
		// C����ʼ��Ҫ��ӵ�Ԫ�أ�ע���������ܳ���Queue�����������׳��쳣
		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(50, true, new ArrayList<Integer>());

		int size = queue.size();
		queue.add(5);
		queue.add(6);
		ArrayList<Integer> list = new ArrayList<>();
		// ��queue�е���ЧԪ��ת�Ƶ�ָ��������
		int drainTo = queue.drainTo(list);
		queue.add(7);
		queue.add(8);
		queue.add(9);// ����offer

		boolean offer = queue.offer(10);// ��Ԫ����ӵ�queue��ĩβ������ﵽqueue������������false�����ὫԪ�ؼӵ�queue��
		boolean offer2 = queue.offer(80, 1, TimeUnit.SECONDS);//��Ԫ����ӵ�queue��ĩβ������ﵽ�������ȴ�ָ��ʱ�������һ�Σ��������ͷ���false,��Ӳ���
		queue.put(15);//��Ԫ����ӵ�queue��ĩβ�����queue�ﵽ������������ǰ�̣߳�ֱ�������̻߳���
		// �Ƿ����ָ��Ԫ��
		boolean contains = queue.contains(10);

		Integer peek = queue.peek();//���ص�ǰԪ�ص�ֵ�����Ƴ�Ԫ�أ����queueû��Ԫ�ؾͷ���null
		Integer element = queue.element();//����peek�����������queueû��Ԫ�ؾ��׳��쳣

		Integer poll = queue.poll();//�Ƴ���ǰԪ��,��ǰû��Ԫ�ؾͷ���null

		Integer poll2 = queue.poll(1, TimeUnit.SECONDS);//�Ƴ���ǰԪ�أ������ǰû��Ԫ�ؾ͵ȴ�ָ��ʱ�䣬ʱ�䵽���ٻ�ȡһ�Σ���û�оͷ���Null
		
		Integer take = queue.take();//�Ƴ���ǰԪ�أ������ǰû��Ԫ�أ������̵߳ȴ����ȴ������̻߳���
		Integer remove = queue.remove();//����poll
		boolean remove2 = queue.remove(12);//�Ƴ�ָ��Ԫ�أ�Ԫ�ؽǱ���Ԫ��ǰ��
		//queue��ʣ������
		int remainingCapacity = queue.remainingCapacity();
		
	}

}
