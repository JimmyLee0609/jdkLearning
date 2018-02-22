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
		//�����̵߳ȴ�phaserͻ��ָ���׶Σ�ֻҪͻ���ˣ��Ǹ����ϾͲ��������߳���
//		������arrive��parties��Ӱ��ֻ����ָ���׶ε�ͻ�ƻ�����Ӧ
//		���������1��0�׶�ͻ���ˣ������������ϣ��Ͳ���������  
		int num = 3;
		Phaser phaser = new Phaser(3);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				phaser.awaitAdvance(0);// �ȴ�phaser���� 0�׶ε�ͻ��
				phaserStatue("before -1-", phaser);
				phaser.awaitAdvance(1);// �ȴ�phaser���� 1�׶ε�ͻ��
				phaser.awaitAdvance(1);// �ȴ�phaser���� 1�׶ε�ͻ��
				phaserStatue("after-1-", phaser);
					
				phaserStatue("before -2-", phaser);
					phaser.awaitAdvance(0);// �ȴ�phaser���� 0�׶ε�ͻ��
					phaser.awaitAdvance(2);//�ȴ�phaser����2�׶ε�ͻ��
					phaserStatue("after-2-", phaser);
					try {
//						�������޶��˵ȴ���ʱ�䣬��ʱ�Զ��׳��쳣
						phaser.awaitAdvanceInterruptibly(3, 1, TimeUnit.SECONDS);
						phaser.awaitAdvanceInterruptibly(3);//ʹ���̳߳ص�ʱ��Ӧ��ʹ�����
					} catch (InterruptedException | TimeoutException e) {
						e.printStackTrace();
					}
			}
		};
		for (int i = 0; i < num; i++) {
			new Thread(task).start();
			;
		}
//		ģ����򵥵�arrive
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
		 * ��ͬһphaser��arriveAndAwaitAdvance��Ϊphaser��arrived+1���û�е���ͻ��״̬�������̵߳ȴ�
		 * ֱ��phaser��arrive=parties ͻ�ƣ������߳̾ͱ����ѣ�����һ�־���phaser���жϣ����������̱߳�����
		 */
		int num = 3;// ����3���߳�ִ��3��arrive
		Phaser phaser = new Phaser(3);// ��Ҫ3��arrive
		phaser.arrive();// ����һ��arrive
		phaserStatue("ready", phaser);
		Runnable task = new Runnable() {
			@Override
			public void run() {

				phaserStatue("before", phaser);
				// arrived+1,���û�е�ͻ�Ƶ�״̬���������̵߳ȴ�arrived
				phaser.arriveAndAwaitAdvance();// 2���߳�ͨ����ͻ�ƣ�ʣ��һ������
				phaserStatue("atfer", phaser);
			}
		};
		for (int i = 0; i < num; i++) {
			new Thread(task).start();
			;
		}

		phaserStatue("end", phaser);
		phaser.arrive();// arrive+1
		phaser.arrive();// arrive+1���ۼ�3��arrive��ͻ�ƣ�����������һ���߳�
		Thread.sleep(3000);
	}

	@SuppressWarnings("unused")
	private static void nestArrive() throws Exception {
		/*
		 * ��Ƕ�׵������,�൱���ڸ�Phaser��register��һ��parties ����֮���и��Ե�arrived��registered��������һ����ǣ�
		 * ������Ҫͬ�������統��Phaser�ڽ׶�0����Phaser�ڽ׶�0�� ��Phaser���ܿ�ʼ�׶�1�������׳��쳣������ͻ��Ȼ���񸸵�arrived+1
		 * 
		 * Ҳ����˵��Phaser(4)��Ҫ4���ʵ��Ҳ����4��arrive���ͻ��񸸵�arrived+1,���ǲ��ܿ����½׶Σ�����arrived�ͻ��׳��쳣
		 * ��Ҫ�ȴ���ͻ�ƣ�Ҳ���Ǹ���arrived=parites�����������ܿ�����һ�׶� ����˵����״̬�����⣬����ͻ�ƣ���״̬true,
		 * Ȼ���ӾͿ���ͻ���ˣ�����״̬false�� ���ž���Ҫ���ٴ�ͻ�ƣ��Ӳ���ͻ�ơ�������ѭ��
		 * 
		 */
		Phaser outter = new Phaser(2);// ��ע��2��parties
		Phaser inner = new Phaser(outter, 4);// Ƕ�׵��¸���ע��һ��parties
		phaserStatue("", outter);
		phaserStatue("", inner);
		outter.arrive();// phaser=0 arrive=1 parties=3
		outter.arrive();// phaser=0 arrive=2 parties=3
		outter.arrive();// phaser�պ�0-1 arrive=0 parties=3 ������һ��ͻ�ƣ��ӵĽ׶�����Ҳ���ű�Ϊ��һ�׶�
		phaserStatue("", outter);
		phaserStatue("", inner);
		// Ϊ���ĵڶ���ͻ��׼��
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
		int arrive = inner.arrive();// �����ӵ�ͻ���ø�����һ��ͻ�ơ��ӿ��Խ�����һ�׶Ρ�
		phaserStatue("end", outter);
		phaserStatue("end", inner);
	}

	@SuppressWarnings("unused")
	private static void mixone() throws Exception {
//		��nest������֪��in��Ҫ��outͬ��,���ʹ�������ע�ͷ����ͻ��׳��쳣������Ҳ��һ�����Ӱ�
//		���߳�������׼����������Ҫ��phaserͻ�Ʋ��ܱ�����
//		��phaser������arrive��ͻ�ƣ�������Ҫͬoutͬ��������arrive�������߳̽��׳��쳣��������arrive����await������
//		������phaserͻ�Ƶ�ʱ���ѣ���phaser,���̼߳������������ǲ���ϵ��ϸ���״̬���ӳ�����arrive��Ȼ�׳��쳣��ʹ���̳߳ؾ���������ˡ��������߳�Ӧ�ñ������쳣�жϣ��߳̾���Ȼ�ᱻ�����������Ҳ��ϣ���������ǵķ���ֵ
		Phaser out = new Phaser(2);// parites=2
		// ������������ �൱��register��һ��
		// out��parties=3,       in ��parties=4
		Phaser in = new Phaser(out, 4);
		int arriveNum = 4;
		int awaitNum = 4;

		Runnable taskArrive = new Runnable() {
			@Override
			public void run() {
				phaserStatue("before --arrive--", in);
				int arrive = in.arrive();//Ƕ���ڵ�phaser��arrive�����������ͻ��Ϊ��arrive+1������Ҫ���������µ�״̬�����ܼ���arrive����Ȼ���쳣
				phaserStatue("after -arrive-", in);
			}
		};
		Runnable taskAwait = new Runnable() {
			@Override
			public void run() {
				phaserStatue("before --await--", in);
				//Ƕ���ڵ�phaser��arriveAndAwaitAdvance�����������ͻ��Ϊ��arrive+1������Ҫ���������µ�״̬�����ܼ���arrive����Ȼ���쳣
				int arriveAndAwaitAdvance = in.arriveAndAwaitAdvance();
				phaserStatue("after -await-", in);
			}
		};
		//׼���̣߳�  ��Ҫ��await��Ϊ���ǻ�ȴ�������8���̱߳�Ȼ���׳��쳣
		for (int i = 0; i < awaitNum; i++) {
			new Thread(taskAwait).start();
			;
		}
		for (int i = 0; i < arriveNum; i++) {
			new Thread(taskArrive).start();;
		}
		Thread.sleep(1000);
//		Ϊͻ��׼��
		out.arrive();
		out.arriveAndAwaitAdvance();//���������ȴ�ͻ��
		phaserStatue("end", out);
		Thread.sleep(5000);
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	private static void arrive() throws InterruptedException {
		// ===����arrive======
		// �ڽ�����arrive������£�ֻҪarrivedParties==registeredParties�ͻ������һ�׶Σ�����phaser+1
		// ���arrivedParties=0��registeredParties����ԭ���ġ�ֻҪphaser.register�ͻ�ʹregister+1
		// �����÷��������쳣���������������Ǻ���������һ���þͻ����쳣��
		int num = 18;
		Phaser phaser = new Phaser(4);
		Runnable task = new Runnable() {
			@Override
			public void run() {

				// ���������߳�,֪ͨ�̵߳��ﵱǰphaser ��
				// ����phaserǶ�׻���arriveAndAwaitAdvance��ʹphaser�����ʹ����Ҫ����
				phaser.arrive();// �ɹ� arrive �൱�� arrive+1
				PhaserTest.phaserStatue("", phaser);

				/* arrive���Զ��ʹ�ã��������һ���ٴ�arrive�����ӡ� */
				 phaser.register();// register���൱��parites+1
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
		int arrivedParties = phaser.getArrivedParties(); // ���������
		int registeredParties = phaser.getRegisteredParties();// ע�ᵽphaser ������
		int unarrivedParties = phaser.getUnarrivedParties(); // ��û���������
		int phase = phaser.getPhase();// ��ȡ��ǰ�Ľ׶�
		System.out.println(Thread.currentThread().getName() + "=" + string + "=phaser->" + phase + " ==="
				+ "arrivedParties->" + arrivedParties + "===" + "registeredParties" + registeredParties + "==="
				+ "unarrivedParties->" + unarrivedParties);
	}

	@SuppressWarnings("unused")
	private static void arriveAndAwaitAdvance() {
		/*
		 * �����÷�����CyclcBarrier ������ǰ�̣߳��ȴ�phaser��arrive=parties,ͻ�Ƶ�ʱ�����������߳�
		 * 
		 */
		int num = 6;
		final Phaser phaser = new Phaser(6);// parties=6 ��Ҫ��6��֪ͨ����Ż����

		Runnable task = new Runnable() {
			@Override
			public void run() {
				phaserStatue("before", phaser);
				// �����̵߳ȴ���������Ҫͻ�Ʋ��ܱ����ѣ�һ�����ѣ�phaser�ͻ�+1
				int arriveAndAwaitAdvance = phaser.arriveAndAwaitAdvance();
				phaserStatue("after", phaser);
			}
		};
		for (int i = 0; i < num; i++) {
			new Thread(task).start();
		}

		// phaser.forceTermination();//ֻҪǿ���жϣ�����Ϊ���phaser�������̻߳ᱻ����
	}

}
