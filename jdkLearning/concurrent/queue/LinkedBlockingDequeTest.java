package queue;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeTest {

	public static void main(String[] args) throws Exception {
		LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(50);

		boolean add = deque.add(15);//addLast
		deque.addFirst(16);//offerFirst队列满抛异常
		deque.addLast(17);//offerLast队列满抛异常
		boolean offer = deque.offer(7);//offerLast
		boolean offer2 = deque.offer(4, 5, TimeUnit.SECONDS);//offerLast
//		添加头,添加成功就唤醒因为队列空take等待的线程,
//		如果到容量限制,就获取Condition引用等待指定的时间再添加一次,获得引用期间其他线程无法获取Condition就是无法添加
//		还是满就返回false
		boolean offerFirst2 = deque.offerFirst(9);
		boolean offerFirst = deque.offerFirst(8, 1, TimeUnit.SECONDS);
//		添加尾,道理和头一样
		boolean offerLast = deque.offerLast(12);
		boolean offerLast2 = deque.offerLast(13, 1, TimeUnit.SECONDS);

		deque.push(5);//addFirst
		
		deque.put(21);//putLast
		//添加头,添加成功后就唤醒因为队列空take等待的线程.
//		如果队列满了,就阻塞当前线程,被唤醒后再次添加,还是满了,就再阻塞,直到成功.
		deque.putFirst(22);
		deque.putLast(23);

		Integer element = deque.element();// getFirst抛空指针
		Integer first = deque.getFirst();// peekFirst抛空指针
		Integer last = deque.getLast();// peekLast抛空指针

		Integer peek = deque.peek();// peekFirst
		// 查看第一个元素
		Integer peekFirst = deque.peekFirst();
		// 查看末尾元素
		Integer peekLast = deque.peekLast();

		Integer poll = deque.poll();// pollFirst
		Integer poll2 = deque.poll(5, TimeUnit.SECONDS);// pollFirst
		// 获取头元素,并唤醒因为队列满put等待的线程
		// 如果头没有元素就持有Condition引用指定的时间再次获取,持有引用期间其他线程无法获取引用
		// 如果还是没有元素,就返回null
		Integer pollFirst = deque.pollFirst();
		Integer pollFirst2 = deque.pollFirst(1, TimeUnit.SECONDS);
		// 移除尾,获取尾元素,并唤醒因为队列满,put等待的线程
		// 如果尾没有元素持有Condition引用指定时间再获取一次,
		// 持有引用期间其他线程无法获取Condition对象,就是无法获取队列元素,如果还是获取不到,就返回null不阻塞线程
		Integer pollLast = deque.pollLast();
		Integer pollLast2 = deque.pollLast(1, TimeUnit.SECONDS);

		Integer take = deque.take();// takeFirst
		Integer takeFirst = deque.takeFirst();// 移除头,并唤醒因为队列满put等待的线程,如果头没有元素将阻塞线程等待
		Integer takeLast = deque.takeLast();// 移除尾,并唤醒因为队列满put等待的线程,如果尾没有元素将阻塞线程等待

		Integer remove = deque.remove();// removeFirst指针
		boolean remove2 = deque.remove(56);// removeFirstOccurrence
		Integer removeFirst = deque.removeFirst();// pollFirst抛空指针
		boolean removeFirstOccurrence = deque.removeFirstOccurrence(7);// 从头开始查,第一个出现的移除
		Integer removeLast = deque.removeLast();// pollLast抛空指针
		boolean removeLastOccurrence = deque.removeLastOccurrence(8);// 从尾开始查,第一个出现的移除

		Integer pop = deque.pop();// removeFirst抛空指针

	}

}
