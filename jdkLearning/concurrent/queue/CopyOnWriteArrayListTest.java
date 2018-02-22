package queue;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// JDK1.5本质是一个volatile Object数组，保存一个ReentrantLock，让每次变更(添加，移除，更改)元素都锁住，
//		但是读取不会锁住，就是重进入锁，锁写
//		由于volatile的字段，线程可以并发访问，不过好像使用Atomic的好些吧
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();

		boolean add = list.add(15);
		list.add(3, 16);
		int size = list.size();

		int indexOf = list.indexOf(15);

		Integer integer = list.get(3);

		int indexOf2 = list.indexOf(16, 2);
		String string = list.toString();

	}

}
