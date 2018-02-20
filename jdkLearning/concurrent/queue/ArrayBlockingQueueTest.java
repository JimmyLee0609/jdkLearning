package queue;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueTest {

	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) throws Exception {
		// 数组工作队列 capacity：容量 fair: true->FIFO头取尾加，false不确定
		// C：初始需要添加的元素，注意容量不能超过Queue的容量否则抛出异常
		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(50, true, new ArrayList<Integer>());

		int size = queue.size();
		queue.add(5);
		queue.add(6);
		ArrayList<Integer> list = new ArrayList<>();
		// 将queue中的有效元素转移到指定集合中
		int drainTo = queue.drainTo(list);
		queue.add(7);
		queue.add(8);
		queue.add(9);// 就是offer

		boolean offer = queue.offer(10);// 将元素添加到queue的末尾，如果达到queue的容量将返回false，不会将元素加到queue中
		boolean offer2 = queue.offer(80, 1, TimeUnit.SECONDS);//将元素添加到queue的末尾，如果达到容量将等待指定时间再添加一次，还是满就返回false,添加不了
		queue.put(15);//将元素添加到queue的末尾，如果queue达到容量就阻塞当前线程，直到其他线程唤醒
		// 是否包含指定元素
		boolean contains = queue.contains(10);

		Integer peek = queue.peek();//返回当前元素的值不会移除元素，如果queue没有元素就返回null
		Integer element = queue.element();//就是peek，但是如果，queue没有元素就抛出异常

		Integer poll = queue.poll();//移除当前元素,当前没有元素就返回null

		Integer poll2 = queue.poll(1, TimeUnit.SECONDS);//移除当前元素，如果当前没有元素就等待指定时间，时间到了再获取一次，还没有就返回Null
		
		Integer take = queue.take();//移除当前元素，如果当前没有元素，阻塞线程等待，等待其他线程唤醒
		Integer remove = queue.remove();//就是poll
		boolean remove2 = queue.remove(12);//移除指定元素，元素角标后的元素前移
		//queue的剩余容量
		int remainingCapacity = queue.remainingCapacity();
		
	}

}
