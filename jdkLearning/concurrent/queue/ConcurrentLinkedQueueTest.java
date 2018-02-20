package queue;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {

	public static void main(String[] args) {
/*
 * 链表结构的并发队列添加元素只能从末尾添加，获取元素只能从头获取就是FIFO
 * */
		ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>(new ArrayList<Integer>());
		int size = queue.size();
		queue.clear();
		boolean empty = queue.isEmpty();
		boolean add = queue.add(15);

	}

}
