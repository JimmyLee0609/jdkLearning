package queue;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// JDK1.5������һ��volatile Object���飬����һ��ReentrantLock����ÿ�α��(��ӣ��Ƴ�������)Ԫ�ض���ס��
//		���Ƕ�ȡ������ס�������ؽ���������д
//		����volatile���ֶΣ��߳̿��Բ������ʣ���������ʹ��Atomic�ĺ�Щ��
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
