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

		// ����λ��
		Collections.swap(list, 0, 1);

		Comparator<Domain> com = (Domain first, Domain second) -> {
			return -1;
		};
		Collections.binarySearch(list, new Domain(), com);
		// ���ֲ��ҷ�
		Collections.binarySearch(list, new Domain());
		// �������
		Collections.checkedList(list, Domain.class);
		Collections.checkedMap(map, Domain.class, String.class);

		ArrayList<Domain> desc = new ArrayList<Domain>(list.size() + 2);

		// list�Ĵ�Сsizeû���ĸ���С�Ͳ��ܲ�������������� list�м䲻�ܿճ���λ
		Domain set2 = desc.set(list.size() + 1, new Domain());

		// fill �� copy ���Ǹ��� size��������
		Collections.fill(desc, new Domain());
		Collections.copy(desc, list);// list��size�Ǹ���Ԫ�ظ����ı�ġ�����ǰ��Ҫ���

		List<ArrayList<Domain>> nCopies = Collections.nCopies(1, list);

		List<ArrayList<Domain>> singletonList = Collections.singletonList(list);

		boolean disjoint = Collections.disjoint(list, new ArrayList<Domain>());
		// �����鷭�� 1��2��3��4��5��6--> 3��4��5��6��1��2
		Collections.rotate(list, 2);
		// ��ת������
		Collections.reverse(list);
		// ����
		Collections.shuffle(list);
		// ���ز��ɸı�ļ��ϣ����ɾ���ķ������׳��쳣
		List<Domain> unmodifiableList = Collections.unmodifiableList(list);
		Set<Domain> unmodifiableSet = Collections.unmodifiableSet(set);
		// ����ͬ���ļ��ϣ������ڸı����ݽṹ��ʱ�����ͬ������this
		Set<Domain> synchronizedSet = Collections.synchronizedSet(set);
		System.out.println();
	}

}
