package queue;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {

	public static void main(String[] args) {
/*
 * ����ṹ�Ĳ����������Ԫ��ֻ�ܴ�ĩβ��ӣ���ȡԪ��ֻ�ܴ�ͷ��ȡ����FIFO
 * */
		ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>(new ArrayList<Integer>());
		int size = queue.size();
		queue.clear();
		boolean empty = queue.isEmpty();
		boolean add = queue.add(15);

	}

}
