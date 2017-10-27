package nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public class ByteBufferTest {
@SuppressWarnings("unused")
public static void main(String[] args) {
	ByteBuffer wrap = ByteBuffer.allocate(500);
	byteBuffer();
//	大端，小端的标志
	byteOrder();
//	创建对象
	createObj();
//	缓存标记位操作
	cacheFlag(wrap);
//	缓存状态操作
	cacheState(wrap);
	//	转换为其他类型的缓冲
	toOtherBuffer(wrap);
//	共享缓存
	shareCache();
//	缓存的其他操作
	
//	=========单个的读取，写入操作===========
	singlePutGet(wrap);
}

@SuppressWarnings("unused")
private static void shareCache() {
	
//	  创建共享此缓冲区内容的新的字节缓冲区。 
//	共享缓存数组，其中一个修改数组都会影响另一个，但是不会影响标志位
	ByteBuffer allocate = ByteBuffer.allocate(100);
	
	ByteBuffer duplicate = allocate.duplicate();//将当前缓存的position和mark都一起给新的对象创建。
	
	Buffer position4 = duplicate.position(1);
	Buffer position5 = allocate.position(1);
	ByteBuffer putLong = duplicate.putLong(999999999l);
//	 创建新的字节缓冲区，其内容是此缓冲区内容的共享子序列。
	ByteBuffer slice = allocate.slice();//根据现在的缓存新建一个缓存  
	ByteBuffer putDouble = slice.putDouble(7.35);//只要其中一个去修改缓存，都会改变数组
//							position=0;mark=-1;limt=this.remaining();offset=position+offset
//	数组共享，状态位置各自保留。要做好线程并发修改异常。
	
}

@SuppressWarnings("unused")
private static void cacheState(ByteBuffer wrap) {

//	=================缓存的当前状态===============================
//	当前位置索引的数值
	int position2 = wrap.position();
//	缓存中是否还有数据
	boolean hasRemaining = wrap.hasRemaining();
//	缓存的容量
	int capacity = wrap.capacity();//就是容量
//	字节的排列方式  大端小端
	ByteOrder order2 = wrap.order();
//	缓存的限制大小，一般在处理完数据，将数据交给流或channel前设定好limit
	int limit = wrap.limit();//限制的大小
//	当前索引位置，到缓存保存的数据的结尾，之间的字节数量
	int remaining = wrap.remaining();//limt-position
//	是否是直接的内存缓存
	boolean direct = wrap.isDirect();
//	是否是只读的
	boolean readOnly = wrap.isReadOnly();
//	返回此缓冲区中的第一个元素在缓冲区的底层实现数组中的偏移量
	int arrayOffset = wrap.arrayOffset();//一般是0
	
}

@SuppressWarnings("unused")
private static void cacheFlag(ByteBuffer wrap) {
	
//	=====================缓存区标志位的操作===============================
//	设置字节的排序方式   高位在前还是在后。
//	改变ByteOrder，读取，写入的操作马上会按照新的要求来做，之前的不会去改变
	ByteBuffer order = wrap.order(ByteOrder.LITTLE_ENDIAN);
	
//	标记当前位置的索引
	Buffer mark = wrap.mark();
//	将此缓冲区的位置重置为以前标记的位置。 	调用此方法不更改也不丢弃标记的值。 
//	设置此缓冲区的限制。如果位置大于新的限制，则将它设置为新限制。如果标记已定义且大于新限制，则丢弃该标记。
	Buffer limit2 = wrap.limit(5);
//	将当前位置设定为传入的值 ,   将读取，写入索引设定为指定值
	Buffer position = wrap.position(50);
	
//	Buffer reset = wrap.reset();//position=mark
//	重绕此缓冲区。将位置设置为 0 并丢弃标记。一般在这些方法后使用  out.write(buf);    buf.get(array);    
	Buffer rewind = wrap.rewind();//倒带   position = 0;    mark = -1;一般是已经有Limit的
	
//	反转此缓冲区。首先将限制设置为当前位置，然后将位置设置为 0。
//	如果已定义了标记，则丢弃该标记。
	Buffer flip = wrap.flip();//翻动        用在写出前 ，limit=position;  position=0;  mark=-1;  准备好为流，管道
//	buf.put(...);  in.read(buf);   buf.flip();   out.write(buf);  
	
}

@SuppressWarnings("unused")
private static void createObj() {
//	============ByteBuffer==========================
//	构建对象    它的子类HeapByteBuffer  包私有的类
	ByteBuffer wrap = ByteBuffer.allocate(500);
//												position=0   capacity=500   limit=500   mark=-1
//	包私有的实现DirectByteBuffer --->MappedByteBuffer--->ByteBuffer
	ByteBuffer allocateDirect = ByteBuffer.allocateDirect(500);
//													position=0  capacity=500   limit=500   mark=-1
	byte[] array="aaa这是一个测试用的字符串，转换成字节数组,由于需要足够的长度。会将这些字节进行复制这是一个测试用的字符串，转换成字节数组,由于需要足够的长度。会将这些字节进行复制这是一个测试用的字符串，转换成字节数组,由于需要足够的长度。会将这些字节进行复制这是一个测试用的字符串，转换成字节数组,由于需要足够的长度。会将这些字节进行复制这是一个测试用的字符串，转换成字节数组,由于需要足够的长度。会将这些字节进行复制这是一个测试用的字符串，转换成字节数组,由于需要足够的长度。会将这些字节进行复制这是一个测试用的字符串，转换成字节数组,由于需要足够的长度。会将这些字节进行复制这是一个测试用的字符串，转换成字节数组,由于需要足够的长度。会将这些字节进行复制".getBytes();
	int length = array.length;//635
	//包装一个HeapByteBuffer
//	 将 byte 数组包装到缓冲区中。
	ByteBuffer wrap3 = ByteBuffer.wrap(array);//HeapByteBuffer
//														position=0;   limit=510   capacity=635   mark=-1
//	将byte数组包装到缓冲区中，设定起始  position   limit限制=position+偏移量
	ByteBuffer wrap2 = ByteBuffer.wrap(array, 10, 500);//HeapByteBuffer	
//																	position=10   limit=510   capacity=635  mark=-1
}

@SuppressWarnings("unused")
private static void byteBuffer() {

	ByteBuffer wrap = ByteBuffer.allocate(500);
//==============ByteBuffer的缓存数组============================	
//	判断是否可通过一个可访问的 byte 数组实现此缓冲区。
//	缓存数组不为null     不是只读
	boolean hasArray = wrap.hasArray();//true
//	返回实现此缓冲区的 byte 数组（可选操作）。
	byte[] array2 = wrap.array();//返回数组
//	返回此缓冲区中的第一个元素在缓冲区的底层实现数组中的偏移量（可选操作）。
	int arrayOffset2 = wrap.arrayOffset();//0
	boolean hasArray2 = wrap.hasArray();//true
	

	
//	=============比较两个缓存=========
//	从两个缓存的position位置开始，逐个get()比较两个值，
//	直到小的remain为止（Math.min(this.remaining(), that.remaining());），如果不一样，返回compare的值
	int compareTo = wrap.compareTo(wrap);
	
	byte[]dst=new byte[]{12,53,64,65,9,51,15,15,8,5,4,32,13,2,21,6,5,4,55,4,63,2,15,6,2,1,6,5,1,3,5,1};

	
//	 相对批量 put 方法      从传入的数组的指定数据，从缓存的当前位置开始写
	ByteBuffer put2 = wrap.put(dst);//写到28位
	ByteBuffer put = wrap.put(dst, 12, 3);//从28位开始写到34位
	
//	 相对批量 get 方法。        获取缓存当前位置开始的数据，将读到的数据写到数组中，写数组指定长度
	ByteBuffer byteBuffer = wrap.get(dst);
	ByteBuffer byteBuffer2 = wrap.get(dst, 10, 2);
	
//	将一个缓存的remaining写到另一个缓存。
//	不能是同一个缓存，会抛异常，执行put的缓存，不能是只读缓存,
//	注意缓存容量大的才能存的下，不然抛异常
	ByteBuffer allocate = ByteBuffer.allocate(500);
	ByteBuffer put3 = allocate.put(wrap);

//	 压缩此缓冲区，就是将操作过的position之前的值不要了，将，position---limit之间的值从0号位开始写过去
//							缓冲数组                 将缓冲数组的剩余数据前移到0号位
//	System.arraycopy(hb, ix(position()), hb, ix(0), remaining());
	ByteBuffer compact = wrap.compact();
//	position=remain    limit=capacity  mark=-1;
	
//	清除此缓冲区。将位置设置为 0，将限制设置为容量，并丢弃标记。
//	position = 0;    limit = capacity;    mark = -1;
	Buffer clear = wrap.clear();
}

@SuppressWarnings("unused")
private static void toOtherBuffer(ByteBuffer wrap) {
//	将缓冲转换为其他类型
	CharBuffer asCharBuffer = wrap.asCharBuffer();
	DoubleBuffer asDoubleBuffer = wrap.asDoubleBuffer();
	FloatBuffer asFloatBuffer = wrap.asFloatBuffer();
	IntBuffer asIntBuffer = wrap.asIntBuffer();
	LongBuffer asLongBuffer = wrap.asLongBuffer();
	ShortBuffer asShortBuffer = wrap.asShortBuffer();	
//	只读
	ByteBuffer asReadOnlyBuffer = wrap.asReadOnlyBuffer();
}

@SuppressWarnings("unused")
private static void singlePutGet(ByteBuffer wrap) {

//	写入操作直接从当前位置开始，向后覆盖数据，改变position的值
	ByteBuffer put3 = wrap.put((byte)0x4C);
	ByteBuffer putChar = wrap.putChar((char)131);
	ByteBuffer putInt = wrap.putInt(56);
	ByteBuffer putDouble = wrap.putDouble(7.0d);
	ByteBuffer putFloat = wrap.putFloat(5.3f);
	ByteBuffer putLong = wrap.putLong(53l);
	ByteBuffer putShort = wrap.putShort((short)56);
	
	//在指定索引位置开始写入传入的数据，不改变position的值
	ByteBuffer put4 = wrap.put(5,(byte)0x4C);
	ByteBuffer putChar2 = wrap.putChar(6,(char)131);
	ByteBuffer putInt2 = wrap.putInt(3,56);
	ByteBuffer putDouble2 = wrap.putDouble(2,7.0d);
	ByteBuffer putFloat2 = wrap.putFloat(5,5.3f);
	ByteBuffer putLong2 = wrap.putLong(9,53l);
	ByteBuffer putShort2 = wrap.putShort(5,(short)56);
	
	
//	进行字节码操作   可以指定索引开始读取，存入数据。
//	从position位置开始读取，改变position的值
	byte c = wrap.get();//	在当前位置向后读取一个byte
	char char2 = wrap.getChar();// 在当前位置读取一个char	
	int int1 = wrap.getInt();//	在当前位置读取一个int
	double double1 = wrap.getDouble();//	在当前位置读取一个double
	float float1 = wrap.getFloat();//	在当前位置读取一个float
	long long1 = wrap.getLong();//	在当前位置读取一个long
	short short1 = wrap.getShort();//	在当前位置读取一个short

//	从指定的索引开始读取， 不改变position的值
	byte b = wrap.get(1);//	在指定的索引开始读取一个byte
	char char1 = wrap.getChar(1);//	在指定的索引开始读取一个char
	int int2 = wrap.getInt(1);//	在指定的索引开始读取一个int
	double double2 = wrap.getDouble(2);//	在指定位置开始读取一个double
	float float2 = wrap.getFloat(3);//	在指定索引位置读取一个float
	long long2 = wrap.getLong(1);//	在指定索引位置读取一个long
	short short2 = wrap.getShort(2);//	在指定索引位置读取一个short
	
}

@SuppressWarnings("unused")
private static void byteOrder() {
//	就是一个名字包装成类
	ByteOrder bigEndian = ByteOrder.BIG_ENDIAN;
	String string = bigEndian.toString();
	
	ByteOrder littleEndian = ByteOrder.LITTLE_ENDIAN;
	String string2 = littleEndian.toString();
	ByteOrder nativeOrder = ByteOrder.nativeOrder();
	String string3 = nativeOrder.toString();	
}
}
