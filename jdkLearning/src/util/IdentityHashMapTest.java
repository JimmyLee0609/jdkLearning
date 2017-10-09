package util;

import java.util.IdentityHashMap;

public class IdentityHashMapTest {

	public static void main(String[] args) {
		//维护一个数组。将key的hash值与数组长度求得索引值，如果索引位已经存在元素并且不等于传入的key，将往后移动存放。
//		这个结构存储的方式是    tab[i] = k;      tab[i + 1] = value;       key存在索引位，值存在key后面1位
		IdentityHashMap<Domain, String> map = new IdentityHashMap<Domain, String>();
		Domain dos = getDomain();
		String put = map.put(dos, "first");
//		int i = hash(k, len);
//		item = tab[i]   如果item与key相同  就覆盖key对应的值
//		tab[i + 1] = value
//		其他用法跟hashmap差不多
		
	}

	static int dex;

	private static Domain getDomain() {
		return new Domain(dex++, "domain" + dex);
	}

}
