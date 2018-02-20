package queue;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeTest {

	public static void main(String[] args) {
//		���˫�����ʹ��CAS���㷨��ʵ��ͬ��,���ǵ�����ӻ����Ƴ���ʱ��ᣬ׼�������/�Ƴ�������׼��������һ����ѭ����ֻҪԭ����ȡ�����������ڵ�����һ�����Ϳ��Ա�����ݣ����еĻ�������׼��һ��������ѭ��ֱ����ɱ��CompareAndSwap
//		��������ṹ��˫����У����ǿ��������ͷ�������β                                                 ��ʼ��Ӽ���Ԫ��
		ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<Integer>(new ArrayList<Integer>());
		boolean empty = deque.isEmpty();
		deque.clear();
		int size = deque.size();
//		=====���======
//		��ͬ���Ԫ��
		deque.addFirst(15);
		deque.push(56);//ͬaddFirst
		deque.add(8);//ͬaddLast
//		��ĩβ���Ԫ��
		deque.addLast(16);
//		��ͷ���Ԫ��
		boolean offerFirst = deque.offerFirst(78);
		boolean offer = deque.offer(47);//ͬofferLast
//		��ĩβ���Ԫ��
		deque.offerLast(59);
//		=====��ȡ=====
		Integer element = deque.element();//getFirst�׿�ָ��
		Integer peek = deque.peek();//peekFirst
//		����ͷԪ��ֵ������null
		Integer peekFirst = deque.peekFirst();
//		����ĩβԪ��ֵ������null
		Integer peekLast = deque.peekLast();
		Integer first = deque.getFirst();//peekFirst�׿�ָ��
		Integer last = deque.getLast();//peekLast�׿�ָ��
		Integer removeLast = deque.removeLast();//pollLast�׿�ָ��
		Integer remove = deque.remove();//removeFirst�׿�ָ��
		Integer removeFirst = deque.removeFirst();//pollFirst,�׿�ָ��
		
		Integer poll = deque.poll();//pollFirst
//		�ӵ�ͷ��ʼ�顣�鵽��һ���ͽ����������������ֵ
		Integer pollFirst = deque.pollFirst();
//		��ĩβ��ʼ�飬�鵽��һ���ͽ����������������ֵ
		Integer pollLast = deque.pollLast();
		Integer pop = deque.pop();//removeFirst
		
//		��ͷ�ڵ㿪ʼ�飬��һ����ͬ��Ԫ�ؾ��Ƴ���ͬ�������Ƴ���˲����
		boolean removeFirstOccurrence = deque.removeFirstOccurrence(15);
//		��β�ڵ㿪ʼ�飬��һ����ͬ��Ԫ�ؾ��Ƴ���ͬ�������Ƴ���˲����
		boolean removeLastOccurrence = deque.removeLastOccurrence(15);
		
		boolean remove2 = deque.remove(15);//removeFirstOccurrence
		
		
	}

}
