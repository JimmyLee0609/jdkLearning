package io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectOutputStream.PutField;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.util.Locale;

public class OutputStreamTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
//		字节输出流
//		fileOutputStream();
//		字节缓冲流
//		bufferedOutputStream();
//		字节数组缓冲
//		byteArrayOutputStream();
//		数据输出流
//		dataOutputStream();
//		获取系统的控制台
		Console console = System.console();//默认是Null
		printStream();
	}


	@SuppressWarnings("unused")
	private static void printStream() throws IOException {
//		=================字节输出流PrintStream======================================
//		=================构造函数===============================================
		PrintStream printStream = new PrintStream("d:/temp/abc.t");
		PrintStream printStream4 = new PrintStream("d:/temp/abc.t","utf-8");
		printStream.close();
		printStream4.close();
		PrintStream printStream2 = new PrintStream(new File("d:/temp/abc.t"));
		PrintStream printStream5 = new PrintStream(new File("d:/temp/abc.t"),"utf-8");
		printStream2.close();
		printStream5.close();
		PrintStream printStream3 = new PrintStream(new FileOutputStream("d:/temp/abc.t"));
		PrintStream printStream6 = new PrintStream(new FileOutputStream("d:/temp/abc.t"),false);
		PrintStream printStream7 = new PrintStream(new FileOutputStream("d:/temp/abc.t"),false,"utf-8");
		printStream3.close();
		printStream6.close();
		printStream7.close();
		
		
		PrintStream stream = new PrintStream("d:/temp/abc.t");
		
		PrintStream append2 = stream.append('a');//调用的是 print - ->write
		PrintStream append = stream.append("char");
		PrintStream append3 = stream.append("charset", 2, 2);
		
//		检查是否有出错，这个类不抛异常
		boolean checkError = stream.checkError();
		
//		将传入的数据转换成String的形式，写到流中
		stream.print(false);
		stream.print(7);
		stream.print(76f);
		stream.print(565d);
		stream.print(56l);
		stream.print('s');
		stream.print("print chars".toCharArray());
		stream.print("print String");
		stream.print(new SerializableDomain());
		
//		将传入的数据转换成String的形式，然后添加换行符，写到流中
		stream.println();
		stream.println(false);
		stream.println(7);
		stream.println(71f);
		stream.println(86l);
		stream.println(799d);
		stream.println('f');
		stream.println("println chars".toCharArray());
		stream.println("println String");
		stream.println(new SerializableDomain());

		stream.write(7);
		stream.write("write byte".getBytes());
		stream.write("write bytes on".getBytes(), 1, 3);
		
		
//		使用格式化器    格式化输出
		PrintStream printf = stream.printf("String format", 2,1);//使用的是format
		PrintStream printf2 = stream.printf(Locale.CHINA, "String format", 1,2);//使用的是format
		
		PrintStream format = stream.format("String for format", 1);//使用的是Formatter格式化器
		PrintStream format2 = stream.format(Locale.CHINA	,"String for format locale", 1);//使用的是Formatter格式化器
		
//		将缓存写入流
		stream.flush();
//		关闭流
		stream.close();
	}


	private static void dataOutputStream() throws IOException {
//		=================DataOutputStream============================
//		构造函数
		DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("d:/temp/abc.t", true));
//		写出基本数据类型
		dataOutputStream.writeBoolean(false);//写出一个布尔值1byte
		dataOutputStream.writeByte(2);//写出一个byte值1个byte
		dataOutputStream.writeShort(79);//写出一个short值2个byte
		dataOutputStream.writeInt(5648);//写出一个int值4个byte
		dataOutputStream.writeLong(5556884l);//写出一个long值8个byte

		dataOutputStream.writeChar('g');//写出一个char值，2个byte
		dataOutputStream.writeChars("chars");//读取字符串的每一个char，将它变成2个int，第一个右移8位成为高位记录
		dataOutputStream.writeBytes("writeBytes");//将字符串变成字节数组输出

		dataOutputStream.writeFloat(5.3f);//写出一个float值，4个字节
		dataOutputStream.writeDouble(53.3d);//写出一个double值，8个byte

		//写utf数据时，会将需要写出的字计数写在头两个字节，用于记录需要读取的字节数	
		dataOutputStream.writeUTF("utf8");//按照utf的编码要求写出字符串

		byte[] b = new byte[] { 79, 80, 81, 82, 83, 84 };
		dataOutputStream.write(5);//写出一个指定的字节
		dataOutputStream.write(b);//将指定数组的数据写出
		dataOutputStream.write(b, 0, 5);//从指定数组的指定位置开始，写出指定长度的数据
		dataOutputStream.flush();//输出流的flush

		dataOutputStream.size();//写出的字节的数目
		dataOutputStream.close();//关闭流
	}

	@SuppressWarnings("unused")
	private static void fileOutputStream() throws IOException {
		// ========================FileOutputStream=================================
		// 构造函数
		FileOutputStream stream = new FileOutputStream("d:/temp/abc.t");
		// 是否在文件末尾开始写数据
		FileOutputStream stream2 = new FileOutputStream("d:/temp/abc.t", true);
		stream2.close();
		FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/temp/abc.t"));
		fileOutputStream.close();
		FileOutputStream fileOutputStream2 = new FileOutputStream(new File("d:/temp/abc.t"), true);
		fileOutputStream2.close();
		// 根据文件描述符构建流
		FileOutputStream fileOutputStream3 = new FileOutputStream(new FileDescriptor());
		fileOutputStream3.close();

		// 获取流的文件描述符
		FileDescriptor fd = stream.getFD();
		new FileInputStream(fd).close();// 文件描述符不管输入输出流,可以创建.就是一个路径

		byte[] b = new byte[100];
		// 将数据写到文件上
		stream.write(b);// 写一个数组的数据
		stream.write(b, 0, 40);// 从数组的指定位置开始,写指定长度
		stream.write(5);// 写一个字节
		// 空实现
		stream.flush();
		// 获取管道
		FileChannel channel = stream.getChannel();

		stream.toString();
		// 关闭流
		stream.close();
	}

	@SuppressWarnings("unused")
	private static void byteArrayOutputStream() throws IOException {
		// ===================ByteArrayOutputStream=========================================
		// 这个类维护一个数组,进行缓存.没有关联流
		// 构造函数,可以指定大小,也可以不指定 默认大小是32
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
		// 将缓存的数据复制一份
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		byte[] b = new byte[1024];
		// 将一个byte写到缓存
		byteArrayOutputStream.write(5);
		// 将一个数组的数据写到缓存
		byteArrayOutputStream.write(b);
		// 从一个数组的指定位置开始,将指定长度的数据写到缓存
		byteArrayOutputStream.write(b, 0, 5);
		// 当前缓存数组的大小
		int size = byteArrayOutputStream.size();

		// 将缓存的数据写到输出流
		byteArrayOutputStream.writeTo(new FileOutputStream("d:/temp/abc.t"));
		// 没有关联流空实现
		byteArrayOutputStream.flush();
		// 将count变为0,就是将原来缓存的数据都不要了,新的需要缓存的数据从数组的开头开始缓存
		byteArrayOutputStream.reset();
		// 将缓存的数据转换为字符串
		String string = byteArrayOutputStream.toString();
		// 将缓存的数据转换为指定编码的字符串
		String string3 = byteArrayOutputStream.toString("utf-8");
		// 空实现
		byteArrayOutputStream.close();
		// String string2 = byteArrayOutputStream.toString(5);//过时
	}

	private static void bufferedOutputStream() throws IOException {
		// ===================BufferedOutputStream====================================
		// 包装字节输出流,构建带缓存的字节输出流, 缓存默认大小8192 这里指定的1024的大小
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream("d:/temp/testOUT.txt"), 1024);
		byte[] b = new byte[1024];
		// 写出一个byte
		stream.write(5);
		// 写出一个数组的数据
		stream.write(b);
		// 写出数组指定长度的数据
		stream.write(b, 0, 15);

		// 写出的数据会缓存在这个类的缓存数组中.需要flush才会真正的写到硬盘文件减少硬盘操作,加快系统运行
		// 如果数据缓存到一定大小.将要写入的数据存不下时,就会将缓存写到文件,清空缓存,将准备写的数据缓存

		// 将数据写到文件上
		stream.flush();
		// 关闭输出流
		stream.close();
	}

}
