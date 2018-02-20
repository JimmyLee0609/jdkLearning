package queue;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeTest {

	public static void main(String[] args) throws Exception {
		LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(50);

		boolean add = deque.add(15);//addLast
		deque.addFirst(16);//offerFirst���������쳣
		deque.addLast(17);//offerLast���������쳣
		boolean offer = deque.offer(7);//offerLast
		boolean offer2 = deque.offer(4, 5, TimeUnit.SECONDS);//offerLast
//		���ͷ,��ӳɹ��ͻ�����Ϊ���п�take�ȴ����߳�,
//		�������������,�ͻ�ȡCondition���õȴ�ָ����ʱ�������һ��,��������ڼ������߳��޷���ȡCondition�����޷����
//		�������ͷ���false
		boolean offerFirst2 = deque.offerFirst(9);
		boolean offerFirst = deque.offerFirst(8, 1, TimeUnit.SECONDS);
//		���β,�����ͷһ��
		boolean offerLast = deque.offerLast(12);
		boolean offerLast2 = deque.offerLast(13, 1, TimeUnit.SECONDS);

		deque.push(5);//addFirst
		
		deque.put(21);//putLast
		//���ͷ,��ӳɹ���ͻ�����Ϊ���п�take�ȴ����߳�.
//		�����������,��������ǰ�߳�,�����Ѻ��ٴ����,��������,��������,ֱ���ɹ�.
		deque.putFirst(22);
		deque.putLast(23);

		Integer element = deque.element();// getFirst�׿�ָ��
		Integer first = deque.getFirst();// peekFirst�׿�ָ��
		Integer last = deque.getLast();// peekLast�׿�ָ��

		Integer peek = deque.peek();// peekFirst
		// �鿴��һ��Ԫ��
		Integer peekFirst = deque.peekFirst();
		// �鿴ĩβԪ��
		Integer peekLast = deque.peekLast();

		Integer poll = deque.poll();// pollFirst
		Integer poll2 = deque.poll(5, TimeUnit.SECONDS);// pollFirst
		// ��ȡͷԪ��,��������Ϊ������put�ȴ����߳�
		// ���ͷû��Ԫ�ؾͳ���Condition����ָ����ʱ���ٴλ�ȡ,���������ڼ������߳��޷���ȡ����
		// �������û��Ԫ��,�ͷ���null
		Integer pollFirst = deque.pollFirst();
		Integer pollFirst2 = deque.pollFirst(1, TimeUnit.SECONDS);
		// �Ƴ�β,��ȡβԪ��,��������Ϊ������,put�ȴ����߳�
		// ���βû��Ԫ�س���Condition����ָ��ʱ���ٻ�ȡһ��,
		// ���������ڼ������߳��޷���ȡCondition����,�����޷���ȡ����Ԫ��,������ǻ�ȡ����,�ͷ���null�������߳�
		Integer pollLast = deque.pollLast();
		Integer pollLast2 = deque.pollLast(1, TimeUnit.SECONDS);

		Integer take = deque.take();// takeFirst
		Integer takeFirst = deque.takeFirst();// �Ƴ�ͷ,��������Ϊ������put�ȴ����߳�,���ͷû��Ԫ�ؽ������̵߳ȴ�
		Integer takeLast = deque.takeLast();// �Ƴ�β,��������Ϊ������put�ȴ����߳�,���βû��Ԫ�ؽ������̵߳ȴ�

		Integer remove = deque.remove();// removeFirstָ��
		boolean remove2 = deque.remove(56);// removeFirstOccurrence
		Integer removeFirst = deque.removeFirst();// pollFirst�׿�ָ��
		boolean removeFirstOccurrence = deque.removeFirstOccurrence(7);// ��ͷ��ʼ��,��һ�����ֵ��Ƴ�
		Integer removeLast = deque.removeLast();// pollLast�׿�ָ��
		boolean removeLastOccurrence = deque.removeLastOccurrence(8);// ��β��ʼ��,��һ�����ֵ��Ƴ�

		Integer pop = deque.pop();// removeFirst�׿�ָ��

	}

}
