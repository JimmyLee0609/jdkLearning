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
//	��ˣ�С�˵ı�־
	byteOrder();
//	��������
	createObj();
//	������λ����
	cacheFlag(wrap);
//	����״̬����
	cacheState(wrap);
	//	ת��Ϊ�������͵Ļ���
	toOtherBuffer(wrap);
//	������
	shareCache();
//	�������������
	
//	=========�����Ķ�ȡ��д�����===========
	singlePutGet(wrap);
}

@SuppressWarnings("unused")
private static void shareCache() {
	
//	  ��������˻��������ݵ��µ��ֽڻ������� 
//	���������飬����һ���޸����鶼��Ӱ����һ�������ǲ���Ӱ���־λ
	ByteBuffer allocate = ByteBuffer.allocate(100);
	
	ByteBuffer duplicate = allocate.duplicate();//����ǰ�����position��mark��һ����µĶ��󴴽���
	
	Buffer position4 = duplicate.position(1);
	Buffer position5 = allocate.position(1);
	ByteBuffer putLong = duplicate.putLong(999999999l);
//	 �����µ��ֽڻ��������������Ǵ˻��������ݵĹ��������С�
	ByteBuffer slice = allocate.slice();//�������ڵĻ����½�һ������  
	ByteBuffer putDouble = slice.putDouble(7.35);//ֻҪ����һ��ȥ�޸Ļ��棬����ı�����
//							position=0;mark=-1;limt=this.remaining();offset=position+offset
//	���鹲��״̬λ�ø��Ա�����Ҫ�����̲߳����޸��쳣��
	
}

@SuppressWarnings("unused")
private static void cacheState(ByteBuffer wrap) {

//	=================����ĵ�ǰ״̬===============================
//	��ǰλ����������ֵ
	int position2 = wrap.position();
//	�������Ƿ�������
	boolean hasRemaining = wrap.hasRemaining();
//	���������
	int capacity = wrap.capacity();//��������
//	�ֽڵ����з�ʽ  ���С��
	ByteOrder order2 = wrap.order();
//	��������ƴ�С��һ���ڴ��������ݣ������ݽ�������channelǰ�趨��limit
	int limit = wrap.limit();//���ƵĴ�С
//	��ǰ����λ�ã������汣������ݵĽ�β��֮����ֽ�����
	int remaining = wrap.remaining();//limt-position
//	�Ƿ���ֱ�ӵ��ڴ滺��
	boolean direct = wrap.isDirect();
//	�Ƿ���ֻ����
	boolean readOnly = wrap.isReadOnly();
//	���ش˻������еĵ�һ��Ԫ���ڻ������ĵײ�ʵ�������е�ƫ����
	int arrayOffset = wrap.arrayOffset();//һ����0
	
}

@SuppressWarnings("unused")
private static void cacheFlag(ByteBuffer wrap) {
	
//	=====================��������־λ�Ĳ���===============================
//	�����ֽڵ�����ʽ   ��λ��ǰ�����ں�
//	�ı�ByteOrder����ȡ��д��Ĳ������ϻᰴ���µ�Ҫ��������֮ǰ�Ĳ���ȥ�ı�
	ByteBuffer order = wrap.order(ByteOrder.LITTLE_ENDIAN);
	
//	��ǵ�ǰλ�õ�����
	Buffer mark = wrap.mark();
//	���˻�������λ������Ϊ��ǰ��ǵ�λ�á� 	���ô˷���������Ҳ��������ǵ�ֵ�� 
//	���ô˻����������ơ����λ�ô����µ����ƣ���������Ϊ�����ơ��������Ѷ����Ҵ��������ƣ������ñ�ǡ�
	Buffer limit2 = wrap.limit(5);
//	����ǰλ���趨Ϊ�����ֵ ,   ����ȡ��д�������趨Ϊָ��ֵ
	Buffer position = wrap.position(50);
	
//	Buffer reset = wrap.reset();//position=mark
//	���ƴ˻���������λ������Ϊ 0 ��������ǡ�һ������Щ������ʹ��  out.write(buf);    buf.get(array);    
	Buffer rewind = wrap.rewind();//����   position = 0;    mark = -1;һ�����Ѿ���Limit��
	
//	��ת�˻����������Ƚ���������Ϊ��ǰλ�ã�Ȼ��λ������Ϊ 0��
//	����Ѷ����˱�ǣ������ñ�ǡ�
	Buffer flip = wrap.flip();//����        ����д��ǰ ��limit=position;  position=0;  mark=-1;  ׼����Ϊ�����ܵ�
//	buf.put(...);  in.read(buf);   buf.flip();   out.write(buf);  
	
}

@SuppressWarnings("unused")
private static void createObj() {
//	============ByteBuffer==========================
//	��������    ��������HeapByteBuffer  ��˽�е���
	ByteBuffer wrap = ByteBuffer.allocate(500);
//												position=0   capacity=500   limit=500   mark=-1
//	��˽�е�ʵ��DirectByteBuffer --->MappedByteBuffer--->ByteBuffer
	ByteBuffer allocateDirect = ByteBuffer.allocateDirect(500);
//													position=0  capacity=500   limit=500   mark=-1
	byte[] array="aaa����һ�������õ��ַ�����ת�����ֽ�����,������Ҫ�㹻�ĳ��ȡ��Ὣ��Щ�ֽڽ��и�������һ�������õ��ַ�����ת�����ֽ�����,������Ҫ�㹻�ĳ��ȡ��Ὣ��Щ�ֽڽ��и�������һ�������õ��ַ�����ת�����ֽ�����,������Ҫ�㹻�ĳ��ȡ��Ὣ��Щ�ֽڽ��и�������һ�������õ��ַ�����ת�����ֽ�����,������Ҫ�㹻�ĳ��ȡ��Ὣ��Щ�ֽڽ��и�������һ�������õ��ַ�����ת�����ֽ�����,������Ҫ�㹻�ĳ��ȡ��Ὣ��Щ�ֽڽ��и�������һ�������õ��ַ�����ת�����ֽ�����,������Ҫ�㹻�ĳ��ȡ��Ὣ��Щ�ֽڽ��и�������һ�������õ��ַ�����ת�����ֽ�����,������Ҫ�㹻�ĳ��ȡ��Ὣ��Щ�ֽڽ��и�������һ�������õ��ַ�����ת�����ֽ�����,������Ҫ�㹻�ĳ��ȡ��Ὣ��Щ�ֽڽ��и���".getBytes();
	int length = array.length;//635
	//��װһ��HeapByteBuffer
//	 �� byte �����װ���������С�
	ByteBuffer wrap3 = ByteBuffer.wrap(array);//HeapByteBuffer
//														position=0;   limit=510   capacity=635   mark=-1
//	��byte�����װ���������У��趨��ʼ  position   limit����=position+ƫ����
	ByteBuffer wrap2 = ByteBuffer.wrap(array, 10, 500);//HeapByteBuffer	
//																	position=10   limit=510   capacity=635  mark=-1
}

@SuppressWarnings("unused")
private static void byteBuffer() {

	ByteBuffer wrap = ByteBuffer.allocate(500);
//==============ByteBuffer�Ļ�������============================	
//	�ж��Ƿ��ͨ��һ���ɷ��ʵ� byte ����ʵ�ִ˻�������
//	�������鲻Ϊnull     ����ֻ��
	boolean hasArray = wrap.hasArray();//true
//	����ʵ�ִ˻������� byte ���飨��ѡ��������
	byte[] array2 = wrap.array();//��������
//	���ش˻������еĵ�һ��Ԫ���ڻ������ĵײ�ʵ�������е�ƫ��������ѡ��������
	int arrayOffset2 = wrap.arrayOffset();//0
	boolean hasArray2 = wrap.hasArray();//true
	

	
//	=============�Ƚ���������=========
//	�����������positionλ�ÿ�ʼ�����get()�Ƚ�����ֵ��
//	ֱ��С��remainΪֹ��Math.min(this.remaining(), that.remaining());���������һ��������compare��ֵ
	int compareTo = wrap.compareTo(wrap);
	
	byte[]dst=new byte[]{12,53,64,65,9,51,15,15,8,5,4,32,13,2,21,6,5,4,55,4,63,2,15,6,2,1,6,5,1,3,5,1};

	
//	 ������� put ����      �Ӵ���������ָ�����ݣ��ӻ���ĵ�ǰλ�ÿ�ʼд
	ByteBuffer put2 = wrap.put(dst);//д��28λ
	ByteBuffer put = wrap.put(dst, 12, 3);//��28λ��ʼд��34λ
	
//	 ������� get ������        ��ȡ���浱ǰλ�ÿ�ʼ�����ݣ�������������д�������У�д����ָ������
	ByteBuffer byteBuffer = wrap.get(dst);
	ByteBuffer byteBuffer2 = wrap.get(dst, 10, 2);
	
//	��һ�������remainingд����һ�����档
//	������ͬһ�����棬�����쳣��ִ��put�Ļ��棬������ֻ������,
//	ע�⻺��������Ĳ��ܴ���£���Ȼ���쳣
	ByteBuffer allocate = ByteBuffer.allocate(500);
	ByteBuffer put3 = allocate.put(wrap);

//	 ѹ���˻����������ǽ���������position֮ǰ��ֵ��Ҫ�ˣ�����position---limit֮���ֵ��0��λ��ʼд��ȥ
//							��������                 �����������ʣ������ǰ�Ƶ�0��λ
//	System.arraycopy(hb, ix(position()), hb, ix(0), remaining());
	ByteBuffer compact = wrap.compact();
//	position=remain    limit=capacity  mark=-1;
	
//	����˻���������λ������Ϊ 0������������Ϊ��������������ǡ�
//	position = 0;    limit = capacity;    mark = -1;
	Buffer clear = wrap.clear();
}

@SuppressWarnings("unused")
private static void toOtherBuffer(ByteBuffer wrap) {
//	������ת��Ϊ��������
	CharBuffer asCharBuffer = wrap.asCharBuffer();
	DoubleBuffer asDoubleBuffer = wrap.asDoubleBuffer();
	FloatBuffer asFloatBuffer = wrap.asFloatBuffer();
	IntBuffer asIntBuffer = wrap.asIntBuffer();
	LongBuffer asLongBuffer = wrap.asLongBuffer();
	ShortBuffer asShortBuffer = wrap.asShortBuffer();	
//	ֻ��
	ByteBuffer asReadOnlyBuffer = wrap.asReadOnlyBuffer();
}

@SuppressWarnings("unused")
private static void singlePutGet(ByteBuffer wrap) {

//	д�����ֱ�Ӵӵ�ǰλ�ÿ�ʼ����󸲸����ݣ��ı�position��ֵ
	ByteBuffer put3 = wrap.put((byte)0x4C);
	ByteBuffer putChar = wrap.putChar((char)131);
	ByteBuffer putInt = wrap.putInt(56);
	ByteBuffer putDouble = wrap.putDouble(7.0d);
	ByteBuffer putFloat = wrap.putFloat(5.3f);
	ByteBuffer putLong = wrap.putLong(53l);
	ByteBuffer putShort = wrap.putShort((short)56);
	
	//��ָ������λ�ÿ�ʼд�봫������ݣ����ı�position��ֵ
	ByteBuffer put4 = wrap.put(5,(byte)0x4C);
	ByteBuffer putChar2 = wrap.putChar(6,(char)131);
	ByteBuffer putInt2 = wrap.putInt(3,56);
	ByteBuffer putDouble2 = wrap.putDouble(2,7.0d);
	ByteBuffer putFloat2 = wrap.putFloat(5,5.3f);
	ByteBuffer putLong2 = wrap.putLong(9,53l);
	ByteBuffer putShort2 = wrap.putShort(5,(short)56);
	
	
//	�����ֽ������   ����ָ��������ʼ��ȡ���������ݡ�
//	��positionλ�ÿ�ʼ��ȡ���ı�position��ֵ
	byte c = wrap.get();//	�ڵ�ǰλ������ȡһ��byte
	char char2 = wrap.getChar();// �ڵ�ǰλ�ö�ȡһ��char	
	int int1 = wrap.getInt();//	�ڵ�ǰλ�ö�ȡһ��int
	double double1 = wrap.getDouble();//	�ڵ�ǰλ�ö�ȡһ��double
	float float1 = wrap.getFloat();//	�ڵ�ǰλ�ö�ȡһ��float
	long long1 = wrap.getLong();//	�ڵ�ǰλ�ö�ȡһ��long
	short short1 = wrap.getShort();//	�ڵ�ǰλ�ö�ȡһ��short

//	��ָ����������ʼ��ȡ�� ���ı�position��ֵ
	byte b = wrap.get(1);//	��ָ����������ʼ��ȡһ��byte
	char char1 = wrap.getChar(1);//	��ָ����������ʼ��ȡһ��char
	int int2 = wrap.getInt(1);//	��ָ����������ʼ��ȡһ��int
	double double2 = wrap.getDouble(2);//	��ָ��λ�ÿ�ʼ��ȡһ��double
	float float2 = wrap.getFloat(3);//	��ָ������λ�ö�ȡһ��float
	long long2 = wrap.getLong(1);//	��ָ������λ�ö�ȡһ��long
	short short2 = wrap.getShort(2);//	��ָ������λ�ö�ȡһ��short
	
}

@SuppressWarnings("unused")
private static void byteOrder() {
//	����һ�����ְ�װ����
	ByteOrder bigEndian = ByteOrder.BIG_ENDIAN;
	String string = bigEndian.toString();
	
	ByteOrder littleEndian = ByteOrder.LITTLE_ENDIAN;
	String string2 = littleEndian.toString();
	ByteOrder nativeOrder = ByteOrder.nativeOrder();
	String string3 = nativeOrder.toString();	
}
}
