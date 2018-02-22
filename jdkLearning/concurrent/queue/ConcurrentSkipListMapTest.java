package queue;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Set;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ConcurrentSkipListMapTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// ��������ݱ����ǿ��ԱȽϵģ���������Ȼ����ģ�Ҳ������ʵ��Comparatable
		// ÿһ����ֵ�Ծ���һ��Node��Node�ļ�����Ҫ���ԱȽϵġ���������������Node���ݼ���ֵ�����浽������
		// ʹ��CAS�ķ�ʽ�ڶ��̵߳�����±�֤���ݵİ�ȫ�����Ǳ�����ݵ�ʱ��Ż�ͬ��,
		// ʹ����Ȼ����ķ�ʽ������
		ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<String, String>();
		// ===�������=====
		map.put("key", "value");// �����ݱ��浽���ϣ�����Ѿ����ڵ�Node����ͬ��key�������µ�ֵ���Ǿɵ�ֵ
		map.put("key1", "value2");
		map.put("key", "value3");
		map.put("key3", "value4");
		// ����ֵ�Դ浽�����У�����������ھͱ��棬�����ھͲ�����
		String putIfAbsent = map.putIfAbsent("kkk", "absent");// ���ؼ�ԭ����Ӧ��ֵ���㿴����ֵ�Ƿ���null��֪�����Ƿ����
		String putIfAbsent2 = map.putIfAbsent("key1", "newValue");

		newMethod(map);
		// ��¡���϶���,���Ǽ�ֵ�Բ��Ḵ�ƹ�ȥ
		ConcurrentSkipListMap<String, String> clone = map.clone();

		// �����봫�����ȵļ��������������ϴ��ڴ��������ӽ��ļ�
		String ceilingKey = map.ceilingKey("key2");
		// �������ļ��ڼ��ϵķ�Χ�⣬�ͷ���null��������������ݣ������3����4�����ң���Ԫ�ؾͷ���
		String ceilingKey2 = map.ceilingKey("key4");

		// ���� ���� ������� ��ӽ��� ��ֵ�ԣ�����ȡ��ֵ �������������Ӧ�ļ�ֵ��(���պ������ֵ)����������ڼ����ͷ���null
		Entry<String, String> ceilingEntry = map.ceilingEntry("key");
		Entry<String, String> ceilingEntry2 = map.ceilingEntry("key2");// �����ڵļ�ֻҪ�ܹ��ʹ��ڵļ��ȽϾ���
		// ��ȡ����Ӧ��ֵ
		String string = map.get("key");
		// ��ȡ����Ӧ��ֵ�����û������������߻�ȡ�������ͷ��ش����Ĭ��ֵ
		String orDefault2 = map.getOrDefault("key1", "default");// �м��ͷ��ؼ���Ӧ��ֵ
		String orDefault = map.getOrDefault("key2", "default");// ÿ���ͷ��ش����Ĭ��ֵ
		// ���ؼ��ϵĵ�һ�����������������൱��tree������Ԫ��
		String firstKey = map.firstKey();
		Entry<String, String> firstEntry = map.firstEntry();

		// ����С�ڵ��ڼ��ļ�ֵ�ԣ����ȵ��ڣ������൱���ڼ�����ߵ�Ԫ��
		Entry<String, String> floorEntry = map.floorEntry("key1");
		Entry<String, String> floorEntry2 = map.floorEntry("key2");
		// ����С�ڵ��ڼ��� ����
		String floorEntry3 = map.floorKey("key1");
		String floorEntry4 = map.floorKey("key2");

		// ���رȸպñȴ�������Ԫ�أ����û��������Ԫ�ط���null
		String higherKey = map.higherKey("key2");
		String higherKey2 = map.higherKey("key1");
		// ���ظպñȴ������ļ�ֵ�ԡ����û�з���null
		Entry<String, String> higherEntry = map.higherEntry("key2");
		Entry<String, String> higherEntry2 = map.higherEntry("key1");

		// ���ظպñȼ�С�ļ�ֵ�ԣ����û�оͷ���Null
		Entry<String, String> lowerEntry2 = map.lowerEntry("key2");
		Entry<String, String> lowerEntry = map.lowerEntry("key1");
		// ���ظպñȽ�С�ļ������û�оͷ���null
		String lowerKey2 = map.lowerKey("key2");
		String lowerKey = map.lowerKey("key1");
		// �������һ�������������ұߵļ�
		String lastKey = map.lastKey();
		Entry<String, String> lastEntry = map.lastEntry();
		// ��ȡ�Ƴ���һ����ֵ��
		Entry<String, String> pollFirstEntry = map.pollFirstEntry();
		// ��ȡ�Ƴ����һ����ֵ��
		Entry<String, String> pollLastEntry = map.pollLastEntry();
		// �Ƴ�ָ���ļ����������ڷ���null
		String remove = map.remove("key2");
		// �Ƴ�ָ���ļ�ֵ�ԣ�����ƥ�����ֵ��ɾ��,�м���ֵ��ͬ�ͷ���false
		boolean remove2 = map.remove("kkk", "absent");
		// �Ƴ���ǰ���е�Ԫ��,������һ��˲ʱ�������������ڵ㻹��ͷ��������Ϣ�û�����
		// map.clear();

		// �滻ָ������ֵ�������ڣ���ֵ�滻�����ؾ�ֵ
		String replace = map.replace("key1", "new Key1 Value");
		// �滻ָ������ֵ������ȷ������ֵ��ָ��ֵ��Ȼ�����滻������ָ��ֵ���Ͳ��滻
		boolean replace2 = map.replace("key1", "new Key1 Value", "change value");// �ɹ��滻����true��ʧ�ܷ���false

		// ��Ȼ����û�м��ϱȽ���null
		Comparator<? super String> comparator = map.comparator();
		// ���ϵĴ�С
		int size = map.size();

		// =====��map��ʵ�൱��һ����������============
		// ����Դ���ϵĽ�������
		NavigableSet<String> descendingKeySet = map.descendingKeySet();
		ConcurrentNavigableMap<String, String> descendingMap = map.descendingMap();
		// ���ؼ���
		Set<Entry<String, String>> entrySet = map.entrySet();
		// ���ر�ָ��Ԫ��С���Ӽ��ϣ��Ƿ����ָ��Ԫ��
		ConcurrentNavigableMap<String, String> headMap = map.headMap("key3", false);
		// ���ر�ָ��Ԫ�ش���Ӽ��ϣ��Ƿ����ָ��Ԫ��
		ConcurrentNavigableMap<String, String> tailMap = map.tailMap("key2", false);
		// ��ȡָ����Χ���Ӽ�
		ConcurrentNavigableMap<String, String> subMap = map.subMap("key1", false, "key3", false);

		// ֵ�ļ���
		Collection<String> values = map.values();

		// �Զ���Ƚ�������ƥ��������ʵ�ֵıȽ���
		Comparator<String> com = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int a1 = o1.indexOf('a');
				int a2 = o2.indexOf('a');
				return a1 - a2 > 0 ? 5 : -1;
			}
		};
		ConcurrentSkipListMap<String, String> listMap = new ConcurrentSkipListMap<String, String>(com);

	}

	@SuppressWarnings("unused")
	private static void newMethod(ConcurrentSkipListMap<String, String> map) {
		BiFunction<String, String, String> function = new BiFunction<String,String,String>() {
			@Override
			public String apply(String key, String value) {
				System.out.println("--key--" + key + "--value--" + value);
				return "yes value"+"--key--" + key + "--value--" + value;
			}
		};
		Function<String,String> function2 = new Function<String,String>() {
			@Override
			public String apply(String value) {
				System.out.println("value--"+value);
				return "value--"+value;
			}};
//		����key1��map�е�ֵ,�滻ΪBiFunction�ķ���ֵ
		String compute = map.compute("key1", function);//����function�ķ���ֵ
		String string = map.get("key1");
//		�����key1����,��ʹ��BiFunction�ķ���ֵȥ����,key1ԭ����Ӧ��ֵ
		String computeIfPresent = map.computeIfPresent("key1", function);//����function�ķ���ֵ
		String string2 = map.get("key1");
//		���key1�����ھ͵���Function�ķ���,���ھͲ�����
		String computeIfAbsent = map.computeIfAbsent("key1", function2);//����function�ķ���ֵ
		String string3 = map.get("key1");
//		����������ھ͵���Function�ķ���,���� key2��function�ķ���ֵ�浽������.
		String computeIfAbsent2 = map.computeIfAbsent("key2", function2);//����function�ķ���ֵ
		String string6 = map.get("key2");
//		���û�м���Ӧ��ֵ�ͽ� �� �� function�ķ���ֵ������ֵ�Դ��뼯��,������ڼ���Ӧ��ֵ,�ͽ�function�ķ���ֵȥ����key3��Ӧ��ֵ 
		String merge = map.merge("key3", "value4", function);//����function�ķ���ֵ
		String string5 = map.get("key3");
		System.out.println();
	}

}
