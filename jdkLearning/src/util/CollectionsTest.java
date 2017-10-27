package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class CollectionsTest {

	public static void main(String[] args) {
		ArrayList<Domain> list = new ArrayList<Domain>();
		TreeMap<Domain, String> map = new TreeMap<Domain, String>();
		Set<Domain> set = map.keySet();
		Domain domain = new Domain(5, "domian5");
		Domain domain2 = new Domain(6, "domain6");
		list.add(domain);
		list.add(domain2);

		Collections.addAll(list, new Domain(3, "domain3"), new Domain(2, "domain2"));

		// 交换位置
		Collections.swap(list, 0, 1);

		Comparator<Domain> com = (Domain first, Domain second) -> {
			return -1;
		};
		Collections.binarySearch(list, new Domain(), com);
		// 二分查找法
		Collections.binarySearch(list, new Domain());
		// 检查类型
		Collections.checkedList(list, Domain.class);
		Collections.checkedMap(map, Domain.class, String.class);

		ArrayList<Domain> desc = new ArrayList<Domain>(list.size() + 2);

		// list的大小size没到哪个大小就不能操作后面的索引， list中间不能空出空位
		Domain set2 = desc.set(list.size() + 1, new Domain());

		// fill 和 copy 都是根据 size来操作，
		Collections.fill(desc, new Domain());
		Collections.copy(desc, list);// list的size是根据元素个数改变的。复制前需要填充

		List<ArrayList<Domain>> nCopies = Collections.nCopies(1, list);

		List<ArrayList<Domain>> singletonList = Collections.singletonList(list);

		boolean disjoint = Collections.disjoint(list, new ArrayList<Domain>());
		// 将数组翻滚 1，2，3，4，5，6--> 3，4，5，6，1，2
		Collections.rotate(list, 2);
		// 反转，倒序
		Collections.reverse(list);
		// 乱序
		Collections.shuffle(list);
		// 返回不可改变的集合，添加删除的方法会抛出异常
		List<Domain> unmodifiableList = Collections.unmodifiableList(list);
		Set<Domain> unmodifiableSet = Collections.unmodifiableSet(set);
		// 返回同步的集合，就是在改变数据结构的时候添加同步锁是this
		Set<Domain> synchronizedSet = Collections.synchronizedSet(set);
		System.out.println();
	}

}
