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
		// 保存的数据必须是可以比较的，可以是自然排序的，也可以是实现Comparatable
		// 每一个键值对就是一个Node，Node的键是需要可以比较的。就像红黑树那样将Node根据键的值，保存到集合中
		// 使用CAS的方式在多线程的情况下保证数据的安全，就是变更数据的时候才会同步,
		// 使用自然排序的方式的数据
		ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<String, String>();
		// ===添加数据=====
		map.put("key", "value");// 将数据保存到集合，如果已经存在的Node有相同的key将会用新的值覆盖旧的值
		map.put("key1", "value2");
		map.put("key", "value3");
		map.put("key3", "value4");
		// 将键值对存到集合中，如果键不存在就保存，键存在就不保存
		String putIfAbsent = map.putIfAbsent("kkk", "absent");// 返回键原来对应的值，你看返回值是否是null就知道键是否存在
		String putIfAbsent2 = map.putIfAbsent("key1", "newValue");

		newMethod(map);
		// 克隆集合对象,但是键值对不会复制过去
		ConcurrentSkipListMap<String, String> clone = map.clone();

		// 返回与传入键相等的键，或者是排序上大于传入键的最接近的键
		String ceilingKey = map.ceilingKey("key2");
		// 如果传入的键在集合的范围外，就返回null，就像上面的数据，最大是3，找4往后找，有元素就返回
		String ceilingKey2 = map.ceilingKey("key4");

		// 返回 大于 传入键的 最接近的 键值对，优先取大值 或者是这个键对应的键值对(键刚好是最大值)，如果不存在键，就返回null
		Entry<String, String> ceilingEntry = map.ceilingEntry("key");
		Entry<String, String> ceilingEntry2 = map.ceilingEntry("key2");// 不存在的键只要能够和存在的键比较就行
		// 获取键对应的值
		String string = map.get("key");
		// 获取键对应的值，如果没有这个键，或者获取不到，就返回传入的默认值
		String orDefault2 = map.getOrDefault("key1", "default");// 有键就返回键对应的值
		String orDefault = map.getOrDefault("key2", "default");// 每键就返回传入的默认值
		// 返回集合的第一个键，集合有排序，相当于tree的最左元素
		String firstKey = map.firstKey();
		Entry<String, String> firstEntry = map.firstEntry();

		// 返回小于等于键的键值对，优先等于，次优相当于在集合左边的元素
		Entry<String, String> floorEntry = map.floorEntry("key1");
		Entry<String, String> floorEntry2 = map.floorEntry("key2");
		// 返回小于等于键的 键，
		String floorEntry3 = map.floorKey("key1");
		String floorEntry4 = map.floorKey("key2");

		// 返回比刚好比传入键大的元素，如果没有这样的元素返回null
		String higherKey = map.higherKey("key2");
		String higherKey2 = map.higherKey("key1");
		// 返回刚好比传入键大的键值对。如果没有返回null
		Entry<String, String> higherEntry = map.higherEntry("key2");
		Entry<String, String> higherEntry2 = map.higherEntry("key1");

		// 返回刚好比键小的键值对，如果没有就返回Null
		Entry<String, String> lowerEntry2 = map.lowerEntry("key2");
		Entry<String, String> lowerEntry = map.lowerEntry("key1");
		// 返回刚好比较小的键，如果没有就返回null
		String lowerKey2 = map.lowerKey("key2");
		String lowerKey = map.lowerKey("key1");
		// 返回最后一个键，就是最右边的键
		String lastKey = map.lastKey();
		Entry<String, String> lastEntry = map.lastEntry();
		// 获取移除第一个键值对
		Entry<String, String> pollFirstEntry = map.pollFirstEntry();
		// 获取移除最后一个键值对
		Entry<String, String> pollLastEntry = map.pollLastEntry();
		// 移除指定的键，键不存在返回null
		String remove = map.remove("key2");
		// 移除指定的键值对，必须匹配键跟值才删除,有键，值不同就返回false
		boolean remove2 = map.remove("kkk", "absent");
		// 移除当前所有的元素,就是在一个瞬时将数据清理，将节点还新头，其他信息置换成新
		// map.clear();

		// 替换指定键的值，键存在，将值替换，返回旧值
		String replace = map.replace("key1", "new Key1 Value");
		// 替换指定键的值，首先确定键的值是指定值，然后再替换，不是指定值，就不替换
		boolean replace2 = map.replace("key1", "new Key1 Value", "change value");// 成功替换返回true，失败返回false

		// 自然排序没有集合比较器null
		Comparator<? super String> comparator = map.comparator();
		// 集合的大小
		int size = map.size();

		// =====子map其实相当于一个迭代器，============
		// 返回源集合的降序排列
		NavigableSet<String> descendingKeySet = map.descendingKeySet();
		ConcurrentNavigableMap<String, String> descendingMap = map.descendingMap();
		// 返回键集
		Set<Entry<String, String>> entrySet = map.entrySet();
		// 返回比指定元素小的子集合，是否包含指定元素
		ConcurrentNavigableMap<String, String> headMap = map.headMap("key3", false);
		// 返回比指定元素大的子集合，是否包含指定元素
		ConcurrentNavigableMap<String, String> tailMap = map.tailMap("key2", false);
		// 获取指定范围的子集
		ConcurrentNavigableMap<String, String> subMap = map.subMap("key1", false, "key3", false);

		// 值的集合
		Collection<String> values = map.values();

		// 自定义比较器，来匹配数据所实现的比较器
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
//		将键key1在map中的值,替换为BiFunction的返回值
		String compute = map.compute("key1", function);//返回function的返回值
		String string = map.get("key1");
//		如果键key1存在,就使用BiFunction的返回值去覆盖,key1原来对应的值
		String computeIfPresent = map.computeIfPresent("key1", function);//返回function的返回值
		String string2 = map.get("key1");
//		如果key1不存在就调用Function的方法,存在就不调用
		String computeIfAbsent = map.computeIfAbsent("key1", function2);//返回function的返回值
		String string3 = map.get("key1");
//		如果键不存在就调用Function的方法,并将 key2和function的返回值存到集合中.
		String computeIfAbsent2 = map.computeIfAbsent("key2", function2);//返回function的返回值
		String string6 = map.get("key2");
//		如果没有键对应的值就将 键 和 function的返回值构建键值对存入集合,如果存在键对应的值,就将function的返回值去覆盖key3对应的值 
		String merge = map.merge("key3", "value4", function);//返回function的返回值
		String string5 = map.get("key3");
		System.out.println();
	}

}
