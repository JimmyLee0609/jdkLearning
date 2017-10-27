package nio;

import java.nio.Buffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.util.stream.IntStream;

public class CharBufferTest {

//	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		��������
		createObj();
//		��������
		singleOperation();
//		��־λ����
		flagOperation();
//		��־״̬
		flagState();
//		ת��Ϊ������
		changeBuffer();
		
	}

	@SuppressWarnings("unused")
	private static void changeBuffer() {
		CharBuffer allocate = CharBuffer.allocate(500);
//		jdk1.8    ����������е�code point  
		IntStream codePoints = allocate.codePoints();
//		������
		IntStream chars = allocate.chars();
//		����һ��ֻ���Ļ�����
		CharBuffer asReadOnlyBuffer = allocate.asReadOnlyBuffer();
//		������������־λ�ǳ�ʼ��
		Buffer flip = allocate.slice();
//		������������־λ��ֵҲ��ֵ��ȥ
		CharBuffer duplicate = allocate.duplicate();
	}

	@SuppressWarnings("unused")
	private static void flagState() {
		CharBuffer allocate = CharBuffer.allocate(500);
//		����������
		int capacity = allocate.capacity();
//		direct���л�����ƫ����
		int arrayOffset = allocate.arrayOffset();
//		position---limit֮���ֵ
		boolean hasRemaining = allocate.hasRemaining();
//		�Ƿ���ֱ�ӵ�
		boolean direct = allocate.isDirect();
//		�Ƿ�ֻ��
		boolean readOnly = allocate.isReadOnly();
//		limit��ֵ
		int limit = allocate.limit();
//		��ˣ�С�ˣ�
		ByteOrder order = allocate.order();
//		��ǰposition��λ��
		int position = allocate.position();
	}

	@SuppressWarnings("unused")
	private static void flagOperation() {
		CharBuffer allocate = CharBuffer.allocate(500);
//		ָ��position��λ��
		Buffer position = allocate.position(50);//position=50
		
//		����      һ��������ݴ�����ˣ������ݷŵ���ǰҪ����    limit = position;        position = 0;        mark = -1;
		Buffer flip = allocate.flip();
//		�������趨��  ָ������ֵ  limit=450
		Buffer limit = allocate.limit(450);
//		����ǰposition��ֵ��¼��mark
		Buffer mark = allocate.mark();
//		��position��ֵ����Ϊmark��ֵ    positoion=mark
		Buffer reset = allocate.reset();
//		����          ���»ص��������Ŀ�ͷ               position = 0;        mark = -1;
		Buffer rewind = allocate.rewind();
//		������ѹ��    ����������     position��  limit֮�������     �Ƶ��������Ŀ�ͷ
		CharBuffer compact = allocate.compact();
		
		String string = allocate.toString();
	}

	@SuppressWarnings("unused")
	private static void singleOperation() {
		CharBuffer allocate = CharBuffer.allocate(500);
		
//		=================append====��ʵ����put==========================
		CharBuffer append = allocate.append('g');
		CharBuffer append2 = allocate.append("test");
		CharBuffer append3 = allocate.append("just append", 2, 2);
//		================put ��ı�position��ֵ===================================
		CharBuffer put = allocate.put('z');
		CharBuffer put2 = allocate.put("put char array".toCharArray());
		CharBuffer put6 = allocate.put("put char array length".toCharArray(), 3, 14);
		CharBuffer put4 = allocate.put("put string");
		CharBuffer put5 = allocate.put("put string length", 3, 10);
//============�ӻ�������ȡ����д����������Ҳ��put===================
		CharBuffer put8 = CharBuffer.allocate(50).put("put charBuffer test");Buffer position = put8.position(0);
		CharBuffer put3 = allocate.put(put8);
//		===============put����ı�position��ֵ=======================
		CharBuffer put7 = allocate.put(1, 'k');
		
//		�Ƚ�λ�����õ�ͷ������ȡ�ſ��Զ�ȡ����
		allocate.position(0);
//		===================get==�ı�position��ֵ===============================
//		����position��λ�û�ȡ��ǰ��char
		char c = allocate.get();
		char []temp=new char[20];
//		�����������е�����д��Ŀ������ position ÿ��ȡһ��������һλ
		CharBuffer charBuffer = allocate.get(temp);
		CharBuffer charBuffer2 = allocate.get(temp, 1, 5);
		
//		��ȡ������ָ��λ�õ�ֵ�����ı�position��ֵ
		char d = allocate.get(5);
	}

	@SuppressWarnings("unused")
	private static void createObj() {
//		ָ����������HeapCharBuffer������ֱ�Ӵ���Derict
		CharBuffer allocate = CharBuffer.allocate(500);
		
		String temp="this is test word";
//		ͨ����װһ��char[] ;
		CharBuffer wrap = CharBuffer.wrap(temp.toCharArray());
		CharBuffer.wrap(temp.toCharArray(),1,3);
//		ͨ����װһ��CharSequence
		CharBuffer wrap2 = CharBuffer.wrap(temp);
		CharBuffer wrap3 = CharBuffer.wrap(temp, 2, 3);
		
//		CharBuffer�������CharSequence�����࣬���Կ���ֱ�Ӱ�װ
		CharBuffer wrap4 = CharBuffer.wrap(wrap3, 1, 1);
		
	}

}
