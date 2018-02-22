package uitl;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PhaserTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		// arrive();
		// nestArrive();
		// arriveAndAwaitAdvance();
		// mixArriveAndAwait();
//		awaitAdvance();
		 mixone();
	}

	private static void awaitAdvance() throws Exception {
		//阻塞线程等待phaser突破指定阶段，只要突破了，那个屏障就不会阻塞线程了
//		它不对arrive和parties有影响只是再指定阶段的突破会有相应
//		下面的例子1，0阶段突破了，再用它来屏障，就不起作用了  
		int num = 3;
		Phaser phaser = new Phaser(3);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				phaser.awaitAdvance(0);// 等待phaser到达 0阶段的突破
				phaserStatue("before -1-", phaser);
				phaser.awaitAdvance(1);// 等待phaser到达 1阶段的突破
				phaser.awaitAdvance(1);// 等待phaser到达 1阶段的突破
				phaserStatue("after-1-", phaser);
					
				phaserStatue("before -2-", phaser);
					phaser.awaitAdvance(0);// 等待phaser到达 0阶段的突破
					phaser.awaitAdvance(2);//等待phaser到达2阶段的突破
					phaserStatue("after-2-", phaser);
					try {
//						这里是限定了等待的时间，超时自动抛出异常
						phaser.awaitAdvanceInterruptibly(3, 1, TimeUnit.SECONDS);
						phaser.awaitAdvanceInterruptibly(3);//使用线程池的时候应该使用这个
					} catch (InterruptedException | TimeoutException e) {
						e.printStackTrace();
					}
			}
		};
		for (int i = 0; i < num; i++) {
			new Thread(task).start();
			;
		}
//		模拟最简单的arrive
		phaserStatue("ready", phaser);
		phaser.arrive();
		phaser.arrive();
		phaser.arrive();
		phaserStatue("ready-1-", phaser);
		phaser.arrive();
		phaser.arrive();
		phaser.arrive();
		phaserStatue("ready-2-", phaser);
		phaser.arrive();
		phaser.arrive();
		phaser.arrive();
		
		Thread.sleep(3000);
	}

	private static void mixArriveAndAwait() throws Exception {
		/*
		 * 在同一phaser中arriveAndAwaitAdvance会为phaser的arrived+1如果没有到达突破状态就阻塞线程等待
		 * 直到phaser的arrive=parties 突破，阻塞线程就被唤醒，还有一种就是phaser被中断，所有阻塞线程被唤醒
		 */
		int num = 3;// 设置3个线程执行3个arrive
		Phaser phaser = new Phaser(3);// 需要3次arrive
		phaser.arrive();// 设置一次arrive
		phaserStatue("ready", phaser);
		Runnable task = new Runnable() {
			@Override
			public void run() {

				phaserStatue("before", phaser);
				// arrived+1,如果没有到突破的状态，就阻塞线程等待arrived
				phaser.arriveAndAwaitAdvance();// 2个线程通过，突破，剩下一个阻塞
				phaserStatue("atfer", phaser);
			}
		};
		for (int i = 0; i < num; i++) {
			new Thread(task).start();
			;
		}

		phaserStatue("end", phaser);
		phaser.arrive();// arrive+1
		phaser.arrive();// arrive+1，累计3个arrive，突破，唤醒阻塞的一个线程
		Thread.sleep(3000);
	}

	@SuppressWarnings("unused")
	private static void nestArrive() throws Exception {
		/*
		 * 在嵌套的情况下,相当于在父Phaser中register了一个parties 它们之间有各自的arrived和registered，但是有一点就是，
		 * 它们需要同步，比如当父Phaser在阶段0，子Phaser在阶段0， 子Phaser不能开始阶段1，否则抛出异常，允许突破然后像父的arrived+1
		 * 
		 * 也就是说子Phaser(4)需要4到达，实际也到了4次arrive，就会像父的arrived+1,但是不能开启新阶段，再有arrived就会抛出异常
		 * 需要等待父突破，也就是父的arrived=parites，这样父就能开启下一阶段 简单来说类似状态的问题，假象父突破，有状态true,
		 * 然后子就可以突破了，并将状态false， 接着就需要父再次突破，子才能突破。这样的循环
		 * 
		 */
		Phaser outter = new Phaser(2);// 父注册2个parties
		Phaser inner = new Phaser(outter, 4);// 嵌套导致父再注册一个parties
		phaserStatue("", outter);
		phaserStatue("", inner);
		outter.arrive();// phaser=0 arrive=1 parties=3
		outter.arrive();// phaser=0 arrive=2 parties=3
		outter.arrive();// phaser刚好0-1 arrive=0 parties=3 父进行一次突破，子的阶段名称也跟着变为这一阶段
		phaserStatue("", outter);
		phaserStatue("", inner);
		// 为父的第二次突破准备
		outter.arrive();// phaser=0 arrive=1 parties=3
		outter.arrive();// phaser=0 arrive=2 parties=3
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				phaserStatue("beforeArrive", inner);
				phaserStatue("beforeArrive", outter);
				inner.arrive();
				phaserStatue("beforeArrive", outter);
				phaserStatue("affterArrive", inner);
			}
		};
		for (int i = 0; i < 4; i++) {
			Thread t1 = new Thread(runnable);
			t1.start();
			t1.join();
		}
		int arrive = inner.arrive();// 由于子的突破让父进行一次突破。子可以进入下一阶段。
		phaserStatue("end", outter);
		phaserStatue("end", inner);
	}

	@SuppressWarnings("unused")
	private static void mixone() throws Exception {
//		由nest的例子知道in需要和out同步,如果使用下面的注释方法就会抛出异常，但是也是一个例子吧
//		主线程中做了准备阻塞，需要子phaser突破才能被唤醒
//		子phaser由两种arrive来突破，由于需要同out同步，超过arrive次数的线程将抛出异常，不论是arrive还是await方法的
//		到了子phaser突破的时候唤醒，父phaser,父线程继续工作，但是不会赶得上更新状态，子超出的arrive必然抛出异常，使用线程池就容易理解了。超出的线程应该被清理异常中断，线程就自然会被处理掉，我们也不希望处理它们的返回值
		Phaser out = new Phaser(2);// parites=2
		// 由于有子类了 相当于register了一次
		// out的parties=3,       in 的parties=4
		Phaser in = new Phaser(out, 4);
		int arriveNum = 4;
		int awaitNum = 4;

		Runnable taskArrive = new Runnable() {
			@Override
			public void run() {
				phaserStatue("before --arrive--", in);
				int arrive = in.arrive();//嵌套内的phaser的arrive，到达次数会突破为父arrive+1但是需要父开启了新的状态，才能继续arrive，不然抛异常
				phaserStatue("after -arrive-", in);
			}
		};
		Runnable taskAwait = new Runnable() {
			@Override
			public void run() {
				phaserStatue("before --await--", in);
				//嵌套内的phaser的arriveAndAwaitAdvance，到达次数会突破为父arrive+1但是需要父开启了新的状态，才能继续arrive，不然抛异常
				int arriveAndAwaitAdvance = in.arriveAndAwaitAdvance();
				phaserStatue("after -await-", in);
			}
		};
		//准备线程，  需要先await因为它们会等待，这里8条线程必然会抛出异常
		for (int i = 0; i < awaitNum; i++) {
			new Thread(taskAwait).start();
			;
		}
		for (int i = 0; i < arriveNum; i++) {
			new Thread(taskArrive).start();;
		}
		Thread.sleep(1000);
//		为突破准备
		out.arrive();
		out.arriveAndAwaitAdvance();//这里阻塞等待突破
		phaserStatue("end", out);
		Thread.sleep(5000);
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	private static void arrive() throws InterruptedException {
		// ===测试arrive======
		// 在仅仅有arrive的情况下，只要arrivedParties==registeredParties就会进入下一阶段，就是phaser+1
		// 变成arrivedParties=0，registeredParties还是原来的。只要phaser.register就会使register+1
		// 这种用法不会抛异常，不会阻塞，但是和其他方法一起用就会抛异常的
		int num = 18;
		Phaser phaser = new Phaser(4);
		Runnable task = new Runnable() {
			@Override
			public void run() {

				// 不会阻塞线程,通知线程到达当前phaser ，
				// 由于phaser嵌套或者arriveAndAwaitAdvance会使phaser变更，使用需要留意
				phaser.arrive();// 成功 arrive 相当于 arrive+1
				PhaserTest.phaserStatue("", phaser);

				/* arrive可以多次使用，下面就是一个再次arrive的例子。 */
				 phaser.register();// register就相当于parites+1
				 phaser.arrive();// arrivedParties+1
				PhaserTest.phaserStatue("", phaser);
			}

		};
		ArrayList<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < num; i++) {
			list.add(new Thread(task));
		}
		for (Thread thread : list) {
			thread.start();
			thread.join();
		}
		 phaser.arrive();
		phaserStatue("", phaser);
		Thread.sleep(3000);
	}

	private static void phaserStatue(String string, Phaser phaser) {
		int arrivedParties = phaser.getArrivedParties(); // 到达的数量
		int registeredParties = phaser.getRegisteredParties();// 注册到phaser 的数量
		int unarrivedParties = phaser.getUnarrivedParties(); // 还没到达的数量
		int phase = phaser.getPhase();// 获取当前的阶段
		System.out.println(Thread.currentThread().getName() + "=" + string + "=phaser->" + phase + " ==="
				+ "arrivedParties->" + arrivedParties + "===" + "registeredParties" + registeredParties + "==="
				+ "unarrivedParties->" + unarrivedParties);
	}

	@SuppressWarnings("unused")
	private static void arriveAndAwaitAdvance() {
		/*
		 * 这种用法很像CyclcBarrier 阻塞当前线程，等待phaser的arrive=parties,突破的时候唤醒阻塞的线程
		 * 
		 */
		int num = 6;
		final Phaser phaser = new Phaser(6);// parties=6 需要有6个通知到达才会放行

		Runnable task = new Runnable() {
			@Override
			public void run() {
				phaserStatue("before", phaser);
				// 阻塞线程等待，由于需要突破才能被唤醒，一旦唤醒，phaser就会+1
				int arriveAndAwaitAdvance = phaser.arriveAndAwaitAdvance();
				phaserStatue("after", phaser);
			}
		};
		for (int i = 0; i < num; i++) {
			new Thread(task).start();
		}

		// phaser.forceTermination();//只要强制中断，但因为这个phaser阻塞的线程会被唤醒
	}

}
