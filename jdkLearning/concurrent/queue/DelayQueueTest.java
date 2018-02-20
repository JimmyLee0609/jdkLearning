package queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {
//		这是一个由延迟的队列,添加到队列的元素需要在指定的延迟后才能从队列中移除,
//		像下面的例子中,MyDelay设置5秒的延迟,在队列中的元素需要Delay的延迟走完,才能poll出来,例子第一次获取不到是直接等待5秒再获取
		DelayQueue<Delayed> queue = new DelayQueue<>();
		MyDelay myDalay = new MyDelay(5);
		boolean add = queue.add(myDalay);
		Delayed poll = queue.poll(5, TimeUnit.SECONDS);
		
		
		boolean add2 = queue.add(myDalay);//同offer
		queue.put(myDalay);//同offer
		boolean offer = queue.offer(poll);//在末尾添加一个元素,如果队列中没有元素,就唤醒因为take而wait的线程
		boolean offer2 = queue.offer(myDalay, 1,TimeUnit.SECONDS);//同offer,没实现再次添加
		Delayed element = queue.element();//同peek
		Delayed peek = queue.peek();//查看不移除第一个元素
		Delayed poll2 = queue.poll();//获取第一个元素
		Delayed poll3 = queue.poll(5, TimeUnit.SECONDS);
		Delayed remove = queue.remove();//同poll
		boolean remove2 = queue.remove(poll);//从头开始查,找到第一个移除
		Delayed take = queue.take();//获取第一个元素,如果队列中没有元素就阻塞等待,并唤醒因为添加wait的线程.
		int size = queue.size();
	System.out.println();
	}

}
