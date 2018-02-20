package queue;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeTest {

	public static void main(String[] args) {
//		这个双向队列使用CAS的算法来实现同步,就是当你添加或者移除的时候会，准备好添加/移除的条件准备，进入一个死循环，只要原来获取的条件和现在的条件一样，就可以变更数据，不行的话，就再准备一次条件，循环直到完成变更CompareAndSwap
//		基于链表结构的双向队列，就是可以添加在头，添加在尾                                                 初始添加集合元素
		ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<Integer>(new ArrayList<Integer>());
		boolean empty = deque.isEmpty();
		deque.clear();
		int size = deque.size();
//		=====添加======
//		从同添加元素
		deque.addFirst(15);
		deque.push(56);//同addFirst
		deque.add(8);//同addLast
//		从末尾添加元素
		deque.addLast(16);
//		在头添加元素
		boolean offerFirst = deque.offerFirst(78);
		boolean offer = deque.offer(47);//同offerLast
//		再末尾添加元素
		deque.offerLast(59);
//		=====获取=====
		Integer element = deque.element();//getFirst抛空指针
		Integer peek = deque.peek();//peekFirst
//		返回头元素值，可以null
		Integer peekFirst = deque.peekFirst();
//		返回末尾元素值，可以null
		Integer peekLast = deque.peekLast();
		Integer first = deque.getFirst();//peekFirst抛空指针
		Integer last = deque.getLast();//peekLast抛空指针
		Integer removeLast = deque.removeLast();//pollLast抛空指针
		Integer remove = deque.remove();//removeFirst抛空指针
		Integer removeFirst = deque.removeFirst();//pollFirst,抛空指针
		
		Integer poll = deque.poll();//pollFirst
//		从第头开始查。查到第一个就将其解链，并返回其值
		Integer pollFirst = deque.pollFirst();
//		从末尾开始查，查到第一个就将其解链，并返回其值
		Integer pollLast = deque.pollLast();
		Integer pop = deque.pop();//removeFirst
		
//		从头节点开始查，第一个相同的元素就移除，同步尽在移除的瞬间做
		boolean removeFirstOccurrence = deque.removeFirstOccurrence(15);
//		从尾节点开始查，第一个相同的元素就移除，同步仅在移除的瞬间做
		boolean removeLastOccurrence = deque.removeLastOccurrence(15);
		
		boolean remove2 = deque.remove(15);//removeFirstOccurrence
		
		
	}

}
