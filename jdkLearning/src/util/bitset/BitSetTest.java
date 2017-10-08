package util.bitset;

import java.util.BitSet;

public class BitSetTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		构造Bitset的方式
		BitSet bitSet = new BitSet();
		BitSet valueOf = BitSet.valueOf(new byte[] { 0x1, 0x2, 0x3, 0x04 });
		
		valueOf.set(1000); // 不能设置负数
		int size2 = valueOf.size();
		String string = valueOf.toString();
		
		byte[] byteArray = valueOf.toByteArray();
		
		BitSet valueOf2 = BitSet.valueOf(new long[] { 0x01, 6, 5, 7 });
		//0号 位置的元素，返回true或false
		boolean b = valueOf2.get(0);
		
		valueOf2.set(50); // 在索引位,赋值为true
		valueOf2.set(2000, true);
		valueOf2.set(10, false); // 在索引位设置为false
		
		int size = valueOf2.size();
		boolean c = valueOf2.get(10);//获取10索引位的值
		
		boolean equals = valueOf2.equals(valueOf);//判断两个BitSet是否相同比较wordsInUse
		
		// valueOf2.and(valueOf); //取交集
		valueOf2.andNot(valueOf); // 去除交集
		valueOf2.or(valueOf); // 或 并集
		// valueOf2.xor(valueOf); //亦或，取并集
		
		valueOf2.flip(3, 10); // 将范围内的值反转，包头不包尾
		
		int previousSetBit = valueOf2.previousSetBit(3);// 返回索引为开始往前的第一个true位置
		int previousClearBit = valueOf2.previousClearBit(4);// 返回索引位开始往前的第一个false位置
		
		int nextClearBit = valueOf2.nextClearBit(5);// 返回索引为开始往后第一个false位置
		int nextSetBit = valueOf2.nextSetBit(6);// 返回索引位开始往后第一个true位置
		
		int cardinality = valueOf2.cardinality();//一共有多少位是true
		byte[] byteArray2 = valueOf2.toByteArray();//将true位的索引转换成数组输出
	}

}
