package util.bitset;

import java.util.BitSet;

public class BitSetTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		����Bitset�ķ�ʽ
		BitSet bitSet = new BitSet();
		BitSet valueOf = BitSet.valueOf(new byte[] { 0x1, 0x2, 0x3, 0x04 });
		
		valueOf.set(1000); // �������ø���
		int size2 = valueOf.size();
		String string = valueOf.toString();
		
		byte[] byteArray = valueOf.toByteArray();
		
		BitSet valueOf2 = BitSet.valueOf(new long[] { 0x01, 6, 5, 7 });
		//0�� λ�õ�Ԫ�أ�����true��false
		boolean b = valueOf2.get(0);
		
		valueOf2.set(50); // ������λ,��ֵΪtrue
		valueOf2.set(2000, true);
		valueOf2.set(10, false); // ������λ����Ϊfalse
		
		int size = valueOf2.size();
		boolean c = valueOf2.get(10);//��ȡ10����λ��ֵ
		
		boolean equals = valueOf2.equals(valueOf);//�ж�����BitSet�Ƿ���ͬ�Ƚ�wordsInUse
		
		// valueOf2.and(valueOf); //ȡ����
		valueOf2.andNot(valueOf); // ȥ������
		valueOf2.or(valueOf); // �� ����
		// valueOf2.xor(valueOf); //���ȡ����
		
		valueOf2.flip(3, 10); // ����Χ�ڵ�ֵ��ת����ͷ����β
		
		int previousSetBit = valueOf2.previousSetBit(3);// ��������Ϊ��ʼ��ǰ�ĵ�һ��trueλ��
		int previousClearBit = valueOf2.previousClearBit(4);// ��������λ��ʼ��ǰ�ĵ�һ��falseλ��
		
		int nextClearBit = valueOf2.nextClearBit(5);// ��������Ϊ��ʼ�����һ��falseλ��
		int nextSetBit = valueOf2.nextSetBit(6);// ��������λ��ʼ�����һ��trueλ��
		
		int cardinality = valueOf2.cardinality();//һ���ж���λ��true
		byte[] byteArray2 = valueOf2.toByteArray();//��trueλ������ת�����������
	}

}
