package queue;

import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class LinkedTransferQueueTest {
	static LinkedTransferQueue<Integer> queue;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		/*
		 * 这个队列的结构是一个链表，FIFO
		 * 每当add和offer，put的时候会将数据存放到队列的末尾。
		 * 每当poll的时候会将队列的第一个元素获取
		 * transfer,将元素交给Consumer,会一直阻塞线程直到有Consumer来获取
		 * tryTransfer如果指定等待时间，就会阻塞等待，等待期间将元素放到队列的结尾，超时会将存放在队列的元素删除，返回false
		 * transfer还有一个特点就是，如果其他线程获取队列的元素，但是还没有获取到transfer的元素，这个元素也会超时
		 * 获取元素的poll(long timeout, TimeUnit unit)会等待指定的时间这个就是Consumer，getWaitingConsumerCount也是获取等待的数量
		 * 有一点特别的是，等待的线程队列的结构执行的是LIFO,只要queue有元素的，第一个交给的是最后一个来的线程。
		 * */
		
		queue = new LinkedTransferQueue<Integer>();
		// 元素添加到队尾，首先保证一个队列的元素
		boolean add = queue.add(55);
		 queue.add(55);
		 queue.add(56);
		 queue.add(57);
		 queue.add(58);
		 queue.add(59);
		 queue.add(60);
		 queue.add(61);
		 queue.add(62);
		 queue.add(63);
		 queue.add(64);
		 queue.add(65);
		 queue.add(66);
		boolean offer = queue.offer(16);
		boolean offer2 = queue.offer(17, 1, TimeUnit.SECONDS);
		// 将元素存到队尾
		queue.put(81);

		int size = queue.size();// 4
		int remainingCapacity = queue.remainingCapacity();// int.max-size
		int waitingConsumerCount = queue.getWaitingConsumerCount();// 0
		boolean hasWaitingConsumer = queue.hasWaitingConsumer();// false
//		开启若干线程获取元素
		createSimpleConsumer();
		
		// 检索并获取首元素，会阻塞线程等待首元素准备好，前提队列无元素
		Integer take = queue.take();
		// 检索不移除首元素
		Integer peek = queue.peek();
		// 获取首元素
		Integer poll = queue.poll();
		// 获取首元素，超时抛异常返回null，如果在超时前被中断，将抛出异常
		Integer poll2 = queue.poll(1, TimeUnit.SECONDS);

		int size2 = queue.size();// 1

		LinkedTransferQueue<Integer> nest	=queue;//用于debug看运行状态的变量
		// 尝试将指定元素传递给等待接受传递的线程，瞬时没成功，将数据存到队尾，阻塞线程等待线程接受
		
		Random random = new Random(80);//生成随机数
		while (true) {//当有线程是poll(1,TimeUnit.SECONDS)的时候才会有等待的线程
			int count = queue.getWaitingConsumerCount();
			if(count>100)break;
			System.out.println("等待的线程数量："+count+"队列的大小"+queue.size());
//			提供可以交换的元素，设计中一个线程提供元素，多个线程获取元素，多方需要设置长一点的等待时间
//			单方也要设置一定的等待时间来避免CUP长时间空转
			boolean tryTransfer3 = queue.tryTransfer(random.nextInt(),1,TimeUnit.MILLISECONDS);
			Thread.sleep(100);
		}
		// 尝试将指定元素传递给等待接受传递的线程
		// 瞬时没成功，将数据存到队尾，在指定的时间还没有来换的，就将存到队的元素移除，并返回false，线程被中断抛出异常
		boolean tryTransfer2 = queue.tryTransfer(12, 1, TimeUnit.SECONDS);
		boolean tryTransfer = queue.tryTransfer(15);// 瞬时，没成功返回false,
		System.out.println("end");
	}

	private static void createSimpleConsumer() throws Exception {
		Runnable task = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Integer take;
					try {
						//
						take = queue.poll(1000,TimeUnit.MILLISECONDS);//获取首元素，如果队列是空就返回null
						Thread.sleep(800);
//						Integer take2 = queue.take();//获取首元素，如果队列为空抛出异常
						if(null!=take)
						System.out.println("i got "+take);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}				
			}};
		Thread t1 = new Thread(task);
		Thread t2 = new Thread(task);
		Thread t3 = new Thread(task);
		Thread t4 = new Thread(task);
		Thread t5 = new Thread(task);
		Thread t6 = new Thread(task);
		t1.start();;
		t2.start();
		t3.start();
//		t4.start();
//		t5.start();
//		t6.start();
	}

}
