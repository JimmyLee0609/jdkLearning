package queue;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntFunction;

public class ConcurrentHashMapTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		/*
		 * 这是一个可以并发访问的map速度比HashMap快,而且可以使用lambda,普通方法上和Hash类似
		 * 这里的键值对是一个Node,然后根据key的hash来存放,和查找,也就是key的hash值需要相异才好,不然都挂一个桶,影响效率
		 */

		// 12 map的容量 0.85到达容量的线程会增加容量 2并发的数量
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>(12, 0.85f, 2);
		// 新建一个键集
		KeySetView<Object, Boolean> newKeySet = ConcurrentHashMap.newKeySet();
		KeySetView<Object, Boolean> newKeySet2 = ConcurrentHashMap.newKeySet(50);

		lambdaOption();

		// 存放元素，如果键存在就覆盖
		String put = map.put("A", "a");
		String put2 = map.put("A", "a1");
		// 如果键不存在就添加
		String putIfAbsent = map.putIfAbsent("A", "A new value");
		// 获取key对应的value
		String string = map.get("A");
		// 获取键对应的值,键不存在就返回传入的默认值
		String orDefault = map.getOrDefault("B", "Default");

		// 键集
		Enumeration<String> keys = map.keys();
		// 键集
		KeySetView<String, String> keySet = map.keySet();
		// 有指定value的key的集合
		KeySetView<String, String> keySet2 = map.keySet("a");
		// 返回键值对的集合
		Set<Entry<String, String>> entrySet = map.entrySet();

		// 是否存在value
		boolean contains = map.contains("a1");// containsValue
		boolean containsValue = map.containsValue("a1");
		// 是否存在键
		boolean containsKey = map.containsKey("A");

		// map的键值对数量
		long mappingCount = map.mappingCount();

		// 返回value的枚举
		Enumeration<String> elements = map.elements();
		Collection<String> values = map.values();
		// 将指定的键的值替换为传入的value
		String replace = map.replace("A", "bcz");
		// 查到键对应的值为传入的值,就将新值覆盖旧值.不是新值,就不覆盖
		boolean replace2 = map.replace("A", "bcz", "new Value A");

		// 移除传入键的键值对
		String remove = map.remove("B");
		// 移除匹配传入键值对的 键值对,由于多线程的环境,键对应的值可以会变更,这里确保删除指定的键值对
		boolean remove2 = map.remove("A", "a1");

		// 清除所有元素
		map.clear();

	}

	private static void lambdaOption() {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("key", "56789");
		BiFunction<String, String, String> biFunction = new BiFunction<String, String, String>() {
			@Override
			public String apply(String key, String value) {
				return "8989" + value;
			}
		};
		Function<String, String> function = new Function<String, String>() {
			@Override
			public String apply(String value) {
				return "123456";
			}
		};
		// =======compute===========
		// 如果键存在就将键对应的值使用BiFunction的返回值覆盖
		String compute = map.compute("key", biFunction);// 返回BiFunction的返回值
		String string3 = map.get("key");
		// 如果键不存在,就将键和biFunction的返回值组成键值对添加到集合
		String compute2 = map.compute("key2", biFunction);// 返回BiFunction的返回值
		String string2 = map.get("key2");
		String computeIfPresent = map.computeIfPresent("key", biFunction);// 返回biFunction的返回值
		String string = map.get("key");
		// 如果键不存在,就键键和Function的返回值组成键值对,添加到map
		String computeIfAbsent = map.computeIfAbsent("bb", function);// 返回function的返回值
		String string4 = map.get("bb");

		// =====merge=========
		// 如果传入的键不在map中就将键和值组成新的键值对添加进map不执行bifunction
		String merge = map.merge("cde", "CDE", biFunction);
		// 如果键存在,就将bifunction的返回值作为值,与键组成对存到map
		String merge2 = map.merge("bb", "123456", biFunction);
		String string5 = map.get("cde");
		String string6 = map.get("bb");
		int size = map.size();
		// ==foreach==
		Consumer<String> consumer = new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println("===========" + t);
			}
		};
		BiFunction<String, String, String> biFunction2 = new BiFunction<String, String, String>() {
			@Override
			public String apply(String key, String value) {
				// 获取到集合的键值对的值.可以进行筛选,返回不同的值给Comsumer来让ComSumer来进行不同的处理
				System.out.println(key + "---------------" + value);
				return "key";// 作为值传递给Consumer,
			}
		};
		// 遍历集合中的键值对,不会改变键值对,使用BiFunction进行过滤,然后给Consumer传递一个值,Consumer根据传来的值再进行处理
		// map.forEach(2, biFunction2, consumer);

		BiConsumer<String, String> biConsumer = new BiConsumer<String, String>() {
			@Override
			public void accept(String key, String value) {
				// 获取到集合的每一对键值对
				System.out.println(key + "====" + value);
			}
		};
		// 遍历键值对,根据键值对的值进行直接处理,不会改变集合,比如键是A,值是B,我就输出C,没有就不输出.类似这种
		// map.forEach(2, biConsumer);

		// =====search=====
		BiFunction<String, String, String> biFunction3 = new BiFunction<String, String, String>() {
			@Override
			public String apply(String key, String value) {
				System.out.println(key + "====" + value);
				return "1561";// 作为结果返回给search
			}
		};
		// 遍历查找集合中的元素,返回bifunction的返回值
//		String search = map.search(2, biFunction3);

		// ====reduce========有一个缓存累加器
		Function<String, String> transformer = new Function<String, String>() {
			@Override
			public String apply(String key) {
//				作为key的筛选器,这里的意思是,直接将key的值传递给累加器
				System.out.println("----------"+key);
				return key;
			}
		};
		BiFunction<String, String, String> reducer = new BiFunction<String, String, String>() {
			@Override
			public String apply(String t, String u) {
//				接受由transformer传递过来的数据进行累加
				System.out.println(t+"===="+u);
				return t+u;//这个值用于传递给缓存值进行累加,字符串的累加就是连接,num的就是运算
			}
		};
//		返回累加的最后结果,第一次运算会获取2个集合的值,第二次累计运算只会获取一个集合的值
//		String reduceKeys = map.reduceKeys(2, transformer, reducer);
		
		
		ToIntFunction<String> toIntFunction = new ToIntFunction<String>() {
			@Override
			public int applyAsInt(String key) {
//				处理集合中的key转换为int,比方说,key符合某个条件就是数字n
				if(key.equals("key"))return 10;
				return 2;
			}
		};
		IntBinaryOperator intBinaryOperator = new IntBinaryOperator() {
			@Override
			public int applyAsInt(int left, int right) {
//				left是缓存的累计值,   right是传递过来的新值
//				最简单直白就是相加,如果需要其他实际的业务运算可以写
				return left+right;//最后累计值会返回给调用者
			}
		};
//		basis  5  就是累计运算开始的时候的第一个值,相当于缓存的第一个值
//		首先从map中获取一个值,进行toIntFunction处理,就会和basis进行运算,operator的值应该是volatile的,
//		debug中看到再operation中暂停的线程的left和right的值在实际运行的时候会进行同步,其他线程已经改变的值,它可以看的到
		int reduceKeysToInt = map.reduceKeysToInt(2, toIntFunction, 5, intBinaryOperator);
		
		System.out.println();
	}

}
