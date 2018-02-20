package queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueTest {

	public static void main(String[] args) throws InterruptedException {
		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(50);
		boolean add = queue.add(15);//offer
//		把元素放到队列的尾部,如果添加成功就唤醒因为take等待的线程
//		如果队列满了,等待指定的时间再添加一次,队列还是满就返回false
		boolean offer = queue.offer(21);
		boolean offer2 = queue.offer(21, 1, TimeUnit.SECONDS);
//		把元素添加到队列的末尾,如果添加成功就唤醒因为take等待的线程,
//		如果队列满了,就阻塞线程等待,收到唤醒通知再次添加,还是满就再次阻塞线程
		queue.put(5);
	
		Integer element = queue.element();//peek
		Integer peek = queue.peek();//查看首元素,队列为空返回null
//		获取首元素,,获取成功唤醒因为队列满了put阻塞的线程.
//		如果队列为空等待指定的时间再获取,队列还是空,返回Null
		Integer poll = queue.poll();
		Integer poll2 = queue.poll(1, TimeUnit.SECONDS);
		Integer remove = queue.remove();//poll抛空指针异常
		boolean remove2 = queue.remove(5);//从头开始查,查到第一个移除,查不到返回false
		//获取头元素,获取成功就唤醒因为队列满put等待的线程.
//		获取失败阻塞当前线程,直到被唤醒,再获取一遍,队列还是空就再次阻塞
		Integer take = queue.take();
	}

}
