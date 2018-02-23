package lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
/*
 * �߳������İ�����   park�ͻ������߳�,unPark(ָ���߳�)�ͻỽ���߳�.
 * park��ʱ���һʹ�ô����Object����,����java��Ķ��󶼿�����Ϊ��,nullҲ����.
 * ���ѵ�ʱ�����Ҫ�����̵߳�����
 * 
 * */
		Thread t1 = new Thread(() -> {
//			����ָ����nano��
			LockSupport.parkNanos(Long.MAX_VALUE);
			System.out.println("parkNanos");

			LockSupport.parkNanos("myName", Long.MAX_VALUE);
			System.out.println("myName ,  parkNanos");
//			����ָ���ĺ���
			LockSupport.parkUntil(Long.MAX_VALUE);
			System.out.println("parkUntil");

			LockSupport.parkUntil("  parkUntil", Long.MAX_VALUE);
			System.out.println("--->==  parkUntil");
//			һֱ����ֱ���������߳�unPark
			LockSupport.park();
			System.out.println("finished park");
		});
		t1.start();

		Object blocker = LockSupport.getBlocker(t1);
		LockSupport.unpark(t1);
		Object blocker2 = LockSupport.getBlocker(t1);//myName
		LockSupport.unpark(t1);
		Object blocker3 = LockSupport.getBlocker(t1);
		LockSupport.unpark(t1);
		Object blocker4 = LockSupport.getBlocker(t1);//  parkUntil
		LockSupport.unpark(t1);
		Object blocker5 = LockSupport.getBlocker(t1);
		LockSupport.unpark(t1);

	}

}
