package util;

import java.util.IdentityHashMap;

public class IdentityHashMapTest {

	public static void main(String[] args) {
		//ά��һ�����顣��key��hashֵ�����鳤���������ֵ���������λ�Ѿ�����Ԫ�ز��Ҳ����ڴ����key���������ƶ���š�
//		����ṹ�洢�ķ�ʽ��    tab[i] = k;      tab[i + 1] = value;       key��������λ��ֵ����key����1λ
		IdentityHashMap<Domain, String> map = new IdentityHashMap<Domain, String>();
		Domain dos = getDomain();
		String put = map.put(dos, "first");
//		int i = hash(k, len);
//		item = tab[i]   ���item��key��ͬ  �͸���key��Ӧ��ֵ
//		tab[i + 1] = value
//		�����÷���hashmap���
		
	}

	static int dex;

	private static Domain getDomain() {
		return new Domain(dex++, "domain" + dex);
	}

}
