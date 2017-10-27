package nio;

import java.nio.Buffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.util.stream.IntStream;

public class CharBufferTest {

//	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		创建对象
		createObj();
//		单个操作
		singleOperation();
//		标志位操作
		flagOperation();
//		标志状态
		flagState();
//		转换为其他流
		changeBuffer();
		
	}

	@SuppressWarnings("unused")
	private static void changeBuffer() {
		CharBuffer allocate = CharBuffer.allocate(500);
//		jdk1.8    返回这个序列的code point  
		IntStream codePoints = allocate.codePoints();
//		流？？
		IntStream chars = allocate.chars();
//		返回一个只读的缓冲区
		CharBuffer asReadOnlyBuffer = allocate.asReadOnlyBuffer();
//		共享缓存区，标志位是初始的
		Buffer flip = allocate.slice();
//		共享缓冲区，标志位的值也赋值过去
		CharBuffer duplicate = allocate.duplicate();
	}

	@SuppressWarnings("unused")
	private static void flagState() {
		CharBuffer allocate = CharBuffer.allocate(500);
//		缓冲区容量
		int capacity = allocate.capacity();
//		direct才有缓冲区偏移量
		int arrayOffset = allocate.arrayOffset();
//		position---limit之间的值
		boolean hasRemaining = allocate.hasRemaining();
//		是否是直接的
		boolean direct = allocate.isDirect();
//		是否只读
		boolean readOnly = allocate.isReadOnly();
//		limit的值
		int limit = allocate.limit();
//		大端，小端？
		ByteOrder order = allocate.order();
//		当前position的位置
		int position = allocate.position();
	}

	@SuppressWarnings("unused")
	private static void flagOperation() {
		CharBuffer allocate = CharBuffer.allocate(500);
//		指定position的位置
		Buffer position = allocate.position(50);//position=50
		
//		翻动      一般就是数据处理好了，将数据放到流前要做的    limit = position;        position = 0;        mark = -1;
		Buffer flip = allocate.flip();
//		将限制设定在  指定的数值  limit=450
		Buffer limit = allocate.limit(450);
//		将当前position的值记录到mark
		Buffer mark = allocate.mark();
//		将position的值设置为mark的值    positoion=mark
		Buffer reset = allocate.reset();
//		倒带          从新回到缓冲区的开头               position = 0;        mark = -1;
		Buffer rewind = allocate.rewind();
//		将数据压缩    将缓冲区的     position到  limit之间的数据     移到缓冲区的开头
		CharBuffer compact = allocate.compact();
		
		String string = allocate.toString();
	}

	@SuppressWarnings("unused")
	private static void singleOperation() {
		CharBuffer allocate = CharBuffer.allocate(500);
		
//		=================append====其实就是put==========================
		CharBuffer append = allocate.append('g');
		CharBuffer append2 = allocate.append("test");
		CharBuffer append3 = allocate.append("just append", 2, 2);
//		================put 会改变position的值===================================
		CharBuffer put = allocate.put('z');
		CharBuffer put2 = allocate.put("put char array".toCharArray());
		CharBuffer put6 = allocate.put("put char array length".toCharArray(), 3, 14);
		CharBuffer put4 = allocate.put("put string");
		CharBuffer put5 = allocate.put("put string length", 3, 10);
//============从缓冲区读取数据写到本缓冲区也是put===================
		CharBuffer put8 = CharBuffer.allocate(50).put("put charBuffer test");Buffer position = put8.position(0);
		CharBuffer put3 = allocate.put(put8);
//		===============put不会改变position的值=======================
		CharBuffer put7 = allocate.put(1, 'k');
		
//		先将位置设置到头，来读取才可以读取数据
		allocate.position(0);
//		===================get==改变position的值===============================
//		根据position的位置获取当前的char
		char c = allocate.get();
		char []temp=new char[20];
//		将缓存数组中的数据写到目标数组 position 每获取一个就右移一位
		CharBuffer charBuffer = allocate.get(temp);
		CharBuffer charBuffer2 = allocate.get(temp, 1, 5);
		
//		读取缓冲区指定位置的值，不改变position的值
		char d = allocate.get(5);
	}

	@SuppressWarnings("unused")
	private static void createObj() {
//		指定容量创建HeapCharBuffer。不能直接创建Derict
		CharBuffer allocate = CharBuffer.allocate(500);
		
		String temp="this is test word";
//		通过包装一个char[] ;
		CharBuffer wrap = CharBuffer.wrap(temp.toCharArray());
		CharBuffer.wrap(temp.toCharArray(),1,3);
//		通过包装一个CharSequence
		CharBuffer wrap2 = CharBuffer.wrap(temp);
		CharBuffer wrap3 = CharBuffer.wrap(temp, 2, 3);
		
//		CharBuffer本身就是CharSequence的子类，所以可以直接包装
		CharBuffer wrap4 = CharBuffer.wrap(wrap3, 1, 1);
		
	}

}
