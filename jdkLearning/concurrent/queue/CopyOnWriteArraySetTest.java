package queue;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArraySetTest {

	public static void main(String[] args) {
//		保存一个ReentrantLock，让每次变更元素都锁住，但是读取不会锁住，就是重进入锁，锁写
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();

boolean add = list.add(15);


	}

}
