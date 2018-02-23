package queue;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueTest {

	public static void main(String[] args) {
		// 就像线程抢夺模式那样,并不是FIFO LIFO的样子,它有自己的算法.传入的对象,需要可以比较,自然顺序或者有比较器
		// 每次添加从末尾开始查,如果元素比它前面size-1>>2的大就直接将元素放到队尾,如果比它小,就继续上面那个值的-1>>2直到找到存放的位置,就是比这个数大,
		// 所以它的位置是有规律但是不固定,可以说是动态的, 就像线程 即使的10等级的优先级,有时候也比7等级的优先级慢就是这个道理
		PriorityBlockingQueue<Object> queue = new PriorityBlockingQueue<>(15);
//		====添加元素=======
		boolean add = queue.add("");// offer
		queue.put("");// offer,不会阻塞
		boolean offer = queue.offer("");
		boolean offer2 = queue.offer("", 1, TimeUnit.SECONDS);
//		=======获取元素=======
		Object element = queue.element();//peek  抛空指针
		Object peek = queue.peek();//获取首元素
		try {
			Object take = queue.take();// 会阻塞等待
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object poll = queue.poll();
		try {
			Object poll2 = queue.poll(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		是否包含指定值
		boolean contains = queue.contains("");
	}

}
