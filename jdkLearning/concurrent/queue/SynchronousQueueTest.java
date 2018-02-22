package queue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {

	public static void main(String[] args) throws Exception {
//		simpleUse();
		/*这个类是一个特殊的队列   两个线程   每次交换一个元素  
		 * 它所保存的是等待的队列元素，并不保存一般数据的引用
		 * 
		 * 每次put和take一组匹配，只要由线程put就阻塞等待，有take就唤醒线程相互交换数据
		 * 
		 * 每次put添加元素，会唤醒获取的等待线程，队列本身不保存数据，如果添加的瞬间没有等待获取队列，将阻塞添加的线程，并将线程保存到等待添加队列
		 * 每次take获取元素，会唤醒等待添加队列，如果获取瞬间没有等待添加的元素，将阻塞获取的线程，并将线程保存到等待获取队列。
		 * 
		 * 其他的添加获取方法不会阻塞，一旦没有等待队列直接抛出异常，有一些是由时限的，过时就会抛出异常
		 * 
		 * */
		int num = 10;
		SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>(false);
		// 队列的剩余容量总是0；
		int remainingCapacity2 = queue.remainingCapacity();
		Runnable putTask = new Runnable() {
			@Override
			public void run() {
				try {
					queue.put(15);
					int remainingCapacity = queue.remainingCapacity();
					System.out.println("finish put remainingCapacity"+remainingCapacity);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Runnable takeTask = new Runnable() {

			@Override
			public void run() {
				try {
					Integer take = queue.take();
					int remainingCapacity = queue.remainingCapacity();
					System.out.println("take-" + take+"-remainingCapacity-"+remainingCapacity);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		for (int i = 0; i < num / 2; i++) {
			new Thread(putTask).start();
			;
		}
		for (int i = 0; i < num / 2; i++) {
			new Thread(takeTask).start();
			;
		}
		System.out.println();
		
		
		
	}

	@SuppressWarnings("unused")
	private static void simpleUse() throws InterruptedException {
		// 使用tranfer来同步，实际也是使用CAS的原理，当添加或者删除元素的时候，使用CAS来操作
		// 在多线程的情况下，就会同步数据
		// true:FIFO false:类似于抢占式
		SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>(false);
//		queue.put(15);// 
		boolean offer2 = queue.offer(13, 1, TimeUnit.SECONDS);//添加到头，超时返回，如果线程被中断，抛出异常，没被中断返回false
//		==一般推荐上面两个方法==
		boolean add = queue.add(5);// offer
		boolean offer = queue.offer(11);// 添加到头，如果没有等待获取队列，就会抛出异常
		Integer element = queue.element();// peek return null多线程下，瞬时值没意义，除非拿出来了
		// peek return null
		Integer peek = queue.peek();// peek

		// 取出头
		Integer poll2 = queue.poll(1, TimeUnit.SECONDS);// 获取头元素，如果没有等待添加队列，超时返回，如果线程被中断，抛出异常，如果没被中断返回false
		Integer take = queue.take();// 获取头值，如果没有等待添加队列，线程阻塞，如果，添加的是null抛出异常
//		===一般推荐上面两种==
		Integer poll = queue.poll();//获取头值，如果当时没有等待添加队列将抛出异常
		Integer remove = queue.remove();// poll，如果队列是空的poll出来null就抛出异常
		boolean remove2 = queue.remove(16);// poll，如果队列是空的poll出来null就抛出异常
	}

}
