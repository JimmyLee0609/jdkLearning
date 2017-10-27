package io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.SequenceInputStream;
import java.nio.channels.FileChannel;

public class InputStreamTest {

	public static void main(String[] args) throws IOException {
		// 字节码输入流
		// filInputStream();
//		没有关联流,只维护一个byte数组
		// byteArrayInputStream();
//		缓冲输入流
//		bufferedInputStream();
//		基本数据类型的读取
		dataInputStream();
//		利用缓存实现回退
		pushbackInputStream();
//		利用枚举实现多个流按照顺序读取
		sequenceInputStream();
	}

	@SuppressWarnings({ "resource", "unused" })
	private static void sequenceInputStream() throws IOException {
//===============SequenceInputStream==========================		
//		新建序列流,将多个流装进集合中,方便读取
		SequenceInputStream stream = new SequenceInputStream(new FileInputStream(""),new FileInputStream(""));
//		返回当前流的剩余大小
		int available = stream.available();
//		不支持标记
//		boolean markSupported = stream.markSupported();//false
//		stream.mark(5);//不支持
//		stream.reset();//不支持
		
		
//		跳过指定数量的字节数
		long skip = stream.skip(5);

		byte [] b=new byte[1024];
//		读取字节
		int read2 = stream.read();//读取一个字节
		int read = stream.read(b);//将指定数组大小的数据,转移到指定的数组,如果到一个流的结尾,就换一个流
		int read3 = stream.read(b, 0, b.length);//将指定长度的数据,转移到指定的数组,并指定在缓存数组开始保存的位置,如果到一个流的结尾,就换一个流
		
		String string = stream.toString();
//		关闭流,逐个关闭
		stream.close();
	}

	@SuppressWarnings("unused")
	private static void pushbackInputStream() throws IOException {
//		===================PushbackInputStream================================
//		构造函数   包装指定的输入流   根据传入的值指定缓存的大小,默认缓存的大小是1
		PushbackInputStream pushbackInputStream 
			= new PushbackInputStream(new FileInputStream("d:/temp/abc.t"),1024);
//		返回可以不受下一次调用此输入流的方法阻塞地从此输入流读取（或跳过）的估计字节数。
		int available = pushbackInputStream.available();
//		是否支持标记
		boolean markSupported = pushbackInputStream.markSupported();//false 不支持
//		在当前位置标记,参数本来是用来限定,读取指定的数量的字节就将标记失效,没有实现这个效果
//		pushbackInputStream.mark(5);不支持
//		pushbackInputStream.reset();不支持
		
		
		byte[] b=new byte[1024];
//		和普通输入流一样的
		pushbackInputStream.read();
		int read = pushbackInputStream.read(b);
		int read2 = pushbackInputStream.read(b, 0, b.length);
		
//		skip指定数量的字节数
		long skip = pushbackInputStream.skip(5);//首先从缓存的数据中skip
		
//		回退指定数量的字节
		pushbackInputStream.unread(b);//回退指定数组的数据
		pushbackInputStream.unread(b, 0, b.length);//回退指定数组从指定位置开始,指定长度的数据
		pushbackInputStream.unread(2);//回退一个字节,将回退的字节的int值保存到缓存
//		关闭输入流
		pushbackInputStream.close();
	}

	@SuppressWarnings("unused")
	private static void dataInputStream() throws IOException {
//		================DataInputStream=========================
		DataInputStream dis = new DataInputStream(new FileInputStream("d:/temp/abc.t"));
//		读取布尔值   1个byte 
		boolean readBoolean = dis.readBoolean();
		//读取输入流中的1个byte,  读到的数值大于0就是 true  读到的数值=0就是false. 读到负数就是到了文件的结尾,抛出异常
		
//		读取输入流的4个byte,然后根据顺序移位,形成一个32位的整体 ,使用浮点数解析32位的bit形成一个float
		float readFloat = dis.readFloat();
		
//		读取输入流的2个byte,根据顺序移位形成一个16位的整体,char方式解析下16bit的值,形成char值
		char readChar = dis.readChar();
		
//		读取4个byte的数据,根据顺序移位形成1个32位的整体,int方式解析32bit的值,形成int值
		int readInt = dis.readInt();
		
//		读取8个byte的数据,根据顺序移位形成1个32位的整体,double浮点数的方式解析64bit的数据,形成double值
		double readDouble = dis.readDouble();
//		读取8个byte的数据,根据顺序移位形成1个32位的整体,long方式解析64bit的值,形成long值
		long readLong = dis.readLong();
		
//		读取输入流中的一个byte数据
		byte readByte = dis.readByte();
//		读取输入流中的一个byte数据
		int readUnsignedByte = dis.readUnsignedByte();
//		由于java系统中每读取一个数据都是以32位一个int的形式来读取.
//		读取8位的数据也会按照32位来读.本来第9位的符号位也变成成了一位.
//		所以需要将数据进行强制类型转换才能获取到负数的byte.
//		如果没有转换就是一个9位的bit的形成int值,
		
//		读取输入流中的2个byte数据
		short readShort = dis.readShort();
//		读取输入流中的2个byte数据
		int readUnsignedShort = dis.readUnsignedShort();
//		short是16位的数据,由于java的特性,需要将数据进行强制类型转换才能有正负数.
//		否则,就是将第17位符号位作为第17bit转换成int值
		

		int available = dis.available();//剩余输入流的大小,FileInputStream的原来大小就是文件的大小  in.available
//		跳过指定数量的byte
		long skip = dis.skip(5);
		int skipBytes = dis.skipBytes(5);
//		InputStream中有字面量规定单次最大skip的数量是2048.如果需要skip大量的byte请使用第二个方法.
		
//		boolean markSupported = dis.markSupported();//false  锟斤拷支锟斤拷
//		dis.mark(53);锟斤拷支锟斤拷
//		dis.reset();锟斤拷支锟斤拷
		
//		根FileInputStream的一样,就是将数据转移到缓存数组
		byte[] b = new byte[8];
		dis.read(b);
		int read = dis.read();//in.read()
		int read2 = dis.read(b, 0, b.length);//in.read(b,0,b.length)
		
//		这个方法会将按照数组的获取足够的数据.如果遇到文件末尾还没有狗长度,会抛异常.
		dis.readFully(b);//
//		这个方法会按照传入的长度,将输入流数据转移到指定的数组,并指定数组开始存储的位置
		dis.readFully(b, 0, b.length);//
		

//		String readLine = dis.readLine();//方法已被弃用
		String string = dis.toString();

		dis.close();//关闭输入流
		
//		目前只能够从这个类的OutputStream写的文件中读取正确的值,否则抛异常.
//		在写utf文件时，第一个byte值是需要读取的位数 ,编码需要是utf-8非dom    一般操作系统的文本输入不能够直接操作16进制级别的操作.需要编程写的文件.
		DataInputStream dataInputStream = new DataInputStream(new FileInputStream("d:/temp/utftemp.txt"));
		DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("d:/temp/u8test.txt"));
		dataOutputStream.writeUTF("你是谁啊?");
		String readUTF = dataInputStream.readUTF();
		dataInputStream.close();
	}

	@SuppressWarnings("unused")
	private static void bufferedInputStream() throws IOException {
		// ========================bufferedInputStream================================
		// 包装传入的输入流,使用自己的byte[]进行缓存.    
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream("d:/temp/abc.t")); // 默认缓存大小8192
		stream.close();
		// 包装传入的输入流,根据传入的缓存大小,新建缓存
		BufferedInputStream stream2 = new BufferedInputStream(
				new FileInputStream("D:\\BaiduYunDownload\\MySQLpub.com\\MySQL Training\\MySQL锟脚伙拷锟斤拷训锟教筹拷全锟斤拷19.rar"), 1024);
		byte[] b = new byte[1024];
		// 输入流的剩下的大小,FileInputStream的原来大小就是文件的大小
		int available = stream2.available();// 3079
		// 是否支持标识    markpos
		boolean markSupported = stream2.markSupported();// true
		// 读取一个单位,就是一个字节
		int read = stream2.read();// byte=57
		// skip指定数量的字节数,遇到缓存的结尾,就在结尾结束.
		long skip = stream2.skip(50);// pos的位置会跟着skip的数量增加 

		// 在输入流当前的位置就是pos的位置标记(就是记录pos的索引)   参数本来是用于限定,读取多少字节后标记失效,没有实装
		stream2.mark(3);// pos 51
		//将指定长度的输入流数据,转移到指定的数组,并从数组的指定位置开始保存
		int read2 = stream2.read(b, 0, 600);// pos 651

		// 将pos的值变回markPos标记的值
		stream2.reset();// pos 51    markpos的值在缓存更新的时候就会清空

		// 读取输入流的一个字节
		int read4 = stream2.read();
		// 将输入流的指定长度的数据400,转移到指定的数组b,并从b的指定位置602开始记录
		int read3 = stream2.read(b, 602, 400);// pos 452
		stream2.close();
	}

	@SuppressWarnings("unused")
	private static void byteArrayInputStream() throws IOException {
		// ========================byteArrayInputStream================================
		// 这个类本身没有关联输入流,  自己维护一个byte[] 作为缓存   由于方法与输入流类似,便于理解代码
		byte[] b = new byte[1024];
		ByteArrayInputStream bais = new ByteArrayInputStream(b, 0, 1024);
		int available = bais.available();
		// 和输入流的方式一样的
		int read = bais.read();
		int read2 = bais.read(b);
		bais.mark(100);
		// 是否支持标记
		boolean markSupported = bais.markSupported();
		bais.reset();
		int read3 = bais.read(b, 0, 1024);
		long skip = bais.skip(3);
		// 没有关联流,没有实现
		bais.close();
	}

	@SuppressWarnings({ "unused", "resource" })
	private static void filInputStream() throws IOException {
		// ========================filInputStream===============================
		// 文件输入流的构造函数   字节输入流  单位  byte      根据路径的字符串创建类
		FileInputStream fileInputStream = new FileInputStream("d:/temp/abc.t");
		fileInputStream.close();
		// 根据File类创建
		FileInputStream fis = new FileInputStream(new File("d:/temp/abc.t"));
		fis.close();

		// 根据FileDescriptor创建   字节输入流
		FileInputStream fileInputStream2 = new FileInputStream(FileDescriptor.in);// Consol的输入流
		int read = fileInputStream2.read();
		fileInputStream2.close();
		// ==============================================================
		FileInputStream fi = new FileInputStream("d:/temp/HelloWorld.jar");
		//获取文件描述符,强制机器访问的资源是同步的
		FileDescriptor fd = fi.getFD();		fd.sync();
		// 读取一个单位  就是1个byte
		int read2 = fi.read();
		// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数。
		int available = fi.available();

		// 是否支持标记
		boolean markSupported = fi.markSupported();// 这个类不支持
		// 
//		fi.mark(10);
		//将pos设置为markpos的位置 
		// fi.reset();
		// skip输入流指定的数量
		fi.skip(123);// 默认最大的skip量是2048

		byte[] b = new byte[1024];
		// 此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
		// 将输入流的指定长度的数据转移到  指定的数组   并指定数组开始记录的位置
		int read3 = fi.read(b, 0, 10);// read3 
		int read4 = fi.read(b, 11, 3);
		FileChannel channel = fi.getChannel();// 获取输入流的channel
		boolean open = channel.isOpen();// true
		//关闭输入流
		fi.close();
	}

}
