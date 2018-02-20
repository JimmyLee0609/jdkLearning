package queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {
//		����һ�����ӳٵĶ���,��ӵ����е�Ԫ����Ҫ��ָ�����ӳٺ���ܴӶ������Ƴ�,
//		�������������,MyDelay����5����ӳ�,�ڶ����е�Ԫ����ҪDelay���ӳ�����,����poll����,���ӵ�һ�λ�ȡ������ֱ�ӵȴ�5���ٻ�ȡ
		DelayQueue<Delayed> queue = new DelayQueue<>();
		MyDelay myDalay = new MyDelay(5);
		boolean add = queue.add(myDalay);
		Delayed poll = queue.poll(5, TimeUnit.SECONDS);
		
		
		boolean add2 = queue.add(myDalay);//ͬoffer
		queue.put(myDalay);//ͬoffer
		boolean offer = queue.offer(poll);//��ĩβ���һ��Ԫ��,���������û��Ԫ��,�ͻ�����Ϊtake��wait���߳�
		boolean offer2 = queue.offer(myDalay, 1,TimeUnit.SECONDS);//ͬoffer,ûʵ���ٴ����
		Delayed element = queue.element();//ͬpeek
		Delayed peek = queue.peek();//�鿴���Ƴ���һ��Ԫ��
		Delayed poll2 = queue.poll();//��ȡ��һ��Ԫ��
		Delayed poll3 = queue.poll(5, TimeUnit.SECONDS);
		Delayed remove = queue.remove();//ͬpoll
		boolean remove2 = queue.remove(poll);//��ͷ��ʼ��,�ҵ���һ���Ƴ�
		Delayed take = queue.take();//��ȡ��һ��Ԫ��,���������û��Ԫ�ؾ������ȴ�,��������Ϊ���wait���߳�.
		int size = queue.size();
	System.out.println();
	}

}
