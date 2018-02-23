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
		 * ����һ�����Բ������ʵ�map�ٶȱ�HashMap��,���ҿ���ʹ��lambda,��ͨ�����Ϻ�Hash����
		 * ����ļ�ֵ����һ��Node,Ȼ�����key��hash�����,�Ͳ���,Ҳ����key��hashֵ��Ҫ����ź�,��Ȼ����һ��Ͱ,Ӱ��Ч��
		 */

		// 12 map������ 0.85�����������̻߳��������� 2����������
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>(12, 0.85f, 2);
		// �½�һ������
		KeySetView<Object, Boolean> newKeySet = ConcurrentHashMap.newKeySet();
		KeySetView<Object, Boolean> newKeySet2 = ConcurrentHashMap.newKeySet(50);

		lambdaOption();

		// ���Ԫ�أ���������ھ͸���
		String put = map.put("A", "a");
		String put2 = map.put("A", "a1");
		// ����������ھ����
		String putIfAbsent = map.putIfAbsent("A", "A new value");
		// ��ȡkey��Ӧ��value
		String string = map.get("A");
		// ��ȡ����Ӧ��ֵ,�������ھͷ��ش����Ĭ��ֵ
		String orDefault = map.getOrDefault("B", "Default");

		// ����
		Enumeration<String> keys = map.keys();
		// ����
		KeySetView<String, String> keySet = map.keySet();
		// ��ָ��value��key�ļ���
		KeySetView<String, String> keySet2 = map.keySet("a");
		// ���ؼ�ֵ�Եļ���
		Set<Entry<String, String>> entrySet = map.entrySet();

		// �Ƿ����value
		boolean contains = map.contains("a1");// containsValue
		boolean containsValue = map.containsValue("a1");
		// �Ƿ���ڼ�
		boolean containsKey = map.containsKey("A");

		// map�ļ�ֵ������
		long mappingCount = map.mappingCount();

		// ����value��ö��
		Enumeration<String> elements = map.elements();
		Collection<String> values = map.values();
		// ��ָ���ļ���ֵ�滻Ϊ�����value
		String replace = map.replace("A", "bcz");
		// �鵽����Ӧ��ֵΪ�����ֵ,�ͽ���ֵ���Ǿ�ֵ.������ֵ,�Ͳ�����
		boolean replace2 = map.replace("A", "bcz", "new Value A");

		// �Ƴ�������ļ�ֵ��
		String remove = map.remove("B");
		// �Ƴ�ƥ�䴫���ֵ�Ե� ��ֵ��,���ڶ��̵߳Ļ���,����Ӧ��ֵ���Ի���,����ȷ��ɾ��ָ���ļ�ֵ��
		boolean remove2 = map.remove("A", "a1");

		// �������Ԫ��
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
		// ��������ھͽ�����Ӧ��ֵʹ��BiFunction�ķ���ֵ����
		String compute = map.compute("key", biFunction);// ����BiFunction�ķ���ֵ
		String string3 = map.get("key");
		// �����������,�ͽ�����biFunction�ķ���ֵ��ɼ�ֵ����ӵ�����
		String compute2 = map.compute("key2", biFunction);// ����BiFunction�ķ���ֵ
		String string2 = map.get("key2");
		String computeIfPresent = map.computeIfPresent("key", biFunction);// ����biFunction�ķ���ֵ
		String string = map.get("key");
		// �����������,�ͼ�����Function�ķ���ֵ��ɼ�ֵ��,��ӵ�map
		String computeIfAbsent = map.computeIfAbsent("bb", function);// ����function�ķ���ֵ
		String string4 = map.get("bb");

		// =====merge=========
		// �������ļ�����map�оͽ�����ֵ����µļ�ֵ����ӽ�map��ִ��bifunction
		String merge = map.merge("cde", "CDE", biFunction);
		// ���������,�ͽ�bifunction�ķ���ֵ��Ϊֵ,�����ɶԴ浽map
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
				// ��ȡ�����ϵļ�ֵ�Ե�ֵ.���Խ���ɸѡ,���ز�ͬ��ֵ��Comsumer����ComSumer�����в�ͬ�Ĵ���
				System.out.println(key + "---------------" + value);
				return "key";// ��Ϊֵ���ݸ�Consumer,
			}
		};
		// ���������еļ�ֵ��,����ı��ֵ��,ʹ��BiFunction���й���,Ȼ���Consumer����һ��ֵ,Consumer���ݴ�����ֵ�ٽ��д���
		// map.forEach(2, biFunction2, consumer);

		BiConsumer<String, String> biConsumer = new BiConsumer<String, String>() {
			@Override
			public void accept(String key, String value) {
				// ��ȡ�����ϵ�ÿһ�Լ�ֵ��
				System.out.println(key + "====" + value);
			}
		};
		// ������ֵ��,���ݼ�ֵ�Ե�ֵ����ֱ�Ӵ���,����ı伯��,�������A,ֵ��B,�Ҿ����C,û�оͲ����.��������
		// map.forEach(2, biConsumer);

		// =====search=====
		BiFunction<String, String, String> biFunction3 = new BiFunction<String, String, String>() {
			@Override
			public String apply(String key, String value) {
				System.out.println(key + "====" + value);
				return "1561";// ��Ϊ������ظ�search
			}
		};
		// �������Ҽ����е�Ԫ��,����bifunction�ķ���ֵ
//		String search = map.search(2, biFunction3);

		// ====reduce========��һ�������ۼ���
		Function<String, String> transformer = new Function<String, String>() {
			@Override
			public String apply(String key) {
//				��Ϊkey��ɸѡ��,�������˼��,ֱ�ӽ�key��ֵ���ݸ��ۼ���
				System.out.println("----------"+key);
				return key;
			}
		};
		BiFunction<String, String, String> reducer = new BiFunction<String, String, String>() {
			@Override
			public String apply(String t, String u) {
//				������transformer���ݹ��������ݽ����ۼ�
				System.out.println(t+"===="+u);
				return t+u;//���ֵ���ڴ��ݸ�����ֵ�����ۼ�,�ַ������ۼӾ�������,num�ľ�������
			}
		};
//		�����ۼӵ������,��һ��������ȡ2�����ϵ�ֵ,�ڶ����ۼ�����ֻ���ȡһ�����ϵ�ֵ
//		String reduceKeys = map.reduceKeys(2, transformer, reducer);
		
		
		ToIntFunction<String> toIntFunction = new ToIntFunction<String>() {
			@Override
			public int applyAsInt(String key) {
//				�������е�keyת��Ϊint,�ȷ�˵,key����ĳ��������������n
				if(key.equals("key"))return 10;
				return 2;
			}
		};
		IntBinaryOperator intBinaryOperator = new IntBinaryOperator() {
			@Override
			public int applyAsInt(int left, int right) {
//				left�ǻ�����ۼ�ֵ,   right�Ǵ��ݹ�������ֵ
//				���ֱ�׾������,�����Ҫ����ʵ�ʵ�ҵ���������д
				return left+right;//����ۼ�ֵ�᷵�ظ�������
			}
		};
//		basis  5  �����ۼ����㿪ʼ��ʱ��ĵ�һ��ֵ,�൱�ڻ���ĵ�һ��ֵ
//		���ȴ�map�л�ȡһ��ֵ,����toIntFunction����,�ͻ��basis��������,operator��ֵӦ����volatile��,
//		debug�п�����operation����ͣ���̵߳�left��right��ֵ��ʵ�����е�ʱ������ͬ��,�����߳��Ѿ��ı��ֵ,�����Կ��ĵ�
		int reduceKeysToInt = map.reduceKeysToInt(2, toIntFunction, 5, intBinaryOperator);
		
		System.out.println();
	}

}
