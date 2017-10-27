package io;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Locale;

import serialization.SerializableDomain;

public class WriterTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		outputStreamWriter();
		charArrayWriter();
		bufferedWriter();
		new StringWriter();// 就是包装了一个StringBuffer
		printWriter();
	}

	@SuppressWarnings("unused")
	private static void printWriter() throws FileNotFoundException, UnsupportedEncodingException {
		// ===================PrintWriter==========================
		// 构造函数 根据文件名字 指定字符集
		PrintWriter printWriter = new PrintWriter("d:/temp/abc.t");
		PrintWriter printWriter5 = new PrintWriter("d:/temp/abc.t", "utf-8");
		printWriter.close();
		printWriter5.close();
		// 根据file 和 指定的字符集
		PrintWriter printWriter2 = new PrintWriter(new File("d:/temp/abc.t"));
		PrintWriter printWriter6 = new PrintWriter(new File("d:/temp/abc.t"), "utf-8");
		printWriter2.close();
		printWriter6.close();
		// 根据字节输出流 可以设定是否自动flush，默认false
		PrintWriter printWriter3 = new PrintWriter(new FileOutputStream("d:/temp/abc.t"));
		PrintWriter printWriter7 = new PrintWriter(new FileOutputStream("d:/temp/abc.t"), false);
		printWriter3.close();
		printWriter7.close();
		// 根据字符输出流 可以设定是否自动flush，默认false
		PrintWriter printWriter4 = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream("d:/temp/abc.t"), "utf-8"));
		PrintWriter printWriter8 = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream("d:/temp/abc.t"), "utf-8"), false);
		printWriter4.close();
		printWriter8.close();

		PrintWriter printWriter9 = new PrintWriter("d:/temp/abc.t");
//		将传入的内容写到缓冲，内部调用的还是write的方法，只是包装了一下将数据都变成String。方便看
		printWriter9.print(false);
		printWriter9.print('a');
		printWriter9.print(4);
		printWriter9.print(43.5f);
		printWriter9.print(5.3d);
		printWriter9.print(53516l);
		printWriter9.print("测试printChar".toCharArray());
		printWriter9.print("testPrint");
//		写入的是类全名+对应的地址@379619aa
		printWriter9.print(new SerializableDomain());
//		将传入的内容  末尾加上换行符   写到缓冲
		printWriter9.println();
		printWriter9.println(false);
		printWriter9.println('z');
		printWriter9.println(79);
		printWriter9.println(128f);
		printWriter9.println(7953341l);
		printWriter9.println(793.565d);
		printWriter9.println("测试printlnChar".toCharArray());
		printWriter9.println("换行字符串");
//		写入的是类全名+对应的地址@cac736f
		printWriter9.println(new SerializableDomain(5, false, "second"));

		boolean checkError = printWriter9.checkError();//检查流是否正常
		
//		将传入的内容写到内存，不自动加换行符
		PrintWriter append = printWriter9.append('z');
		PrintWriter append3 = printWriter9.append("测试append");
		PrintWriter append2 = printWriter9.append("测试append时", 3, 4);

		printWriter9.write(4);
		printWriter9.write("写出");
		printWriter9.write("测试写出的字母", 2, 3);
		printWriter9.write("测试write");
//		将缓冲区的内容写到硬盘
		printWriter9.flush();

//		格式化
		PrintWriter format2 = printWriter9.format("formatString", 1);//使用util的Formatter
		PrintWriter format = printWriter9.format(Locale.CHINA, "formatString", 1);
		PrintWriter printf = printWriter9.printf("String", 1);//就是使用format的方法
		printWriter9.printf(Locale.CHINA, "testStringh", 1);
		
//		将缓冲区的内容写到硬盘
		printWriter9.flush();
//		关闭输出流
		printWriter9.close();
	}

	@SuppressWarnings("unused")
	private static void bufferedWriter() throws IOException {
		// =================BufferedWriter======================================

		// 构造函数，包装一个字符输出流，可以指定缓存的大小
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("d:/temp/abc.t"), "utf-8"), 8120);
		char[] c = new char[] { 'c', 'f', 'r', 't', 'h' };
		// 写一个换行符到缓存
		writer.newLine();
		//
		Writer append = writer.append("csad");
		Writer append2 = writer.append('d');
		Writer append3 = writer.append("begintest", 2, 5);
		writer.write("测试用的字符串", 2, 3);
		writer.write("testString");
		writer.write(c);
		writer.write(8);

		String string = writer.toString();
		// 将缓存写到输出流，清空缓存
		writer.flush();
		// 将缓存写到输出流， 清空缓存，关闭输出流
		writer.close();

	}

	@SuppressWarnings("unused")
	private static void charArrayWriter() throws FileNotFoundException, IOException {
		// ====================CharArrayWriter=============================
		// CharArrayWriter本身没有关联输出流，自己维护一个数组，可以在处理好缓存的数据后，传到输出流中写出
		CharArrayWriter charArrayWriter = new CharArrayWriter();
		char[] charArray = charArrayWriter.toCharArray();
		charArrayWriter.writeTo(new OutputStreamWriter(new FileOutputStream("d:/temp/abc.t")));

		char[] c = new char[] { 'c', 'f', 'd', 't', 'p' };
		// 根下面的实现其实一样，就是会返回this。
		CharArrayWriter append = charArrayWriter.append('g');
		CharArrayWriter append2 = charArrayWriter.append("test");
		CharArrayWriter append3 = charArrayWriter.append("again", 2, 3);
		// 将数据写到缓存中
		charArrayWriter.write(75);
		charArrayWriter.write(c);
		charArrayWriter.write("测试");
		charArrayWriter.write("这是一个测试", 2, 3);
		charArrayWriter.write(c, 2, 2);

		// 将缓冲的数据丢弃，将指针返回到0位置，新增的缓存将从0位置开始覆盖原来的数据
		charArrayWriter.reset();
		// 缓冲的char数组长度
		int size = charArrayWriter.size();
		String string = charArrayWriter.toString();

		// 没有关联流 空实现
		charArrayWriter.flush();
		charArrayWriter.close();
	}

	@SuppressWarnings("unused")
	private static void outputStreamWriter() throws IOException {
		// =================字符输出流OutputStreamWriter====================================
		// 构造函数 包装一个输入流 交给StreamEncoder处理，将字节输出流 转换成字符输出流
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("d:/temp/testWriter"),
				"utf-8");
		// 获取编码字符集
		String encoding = outputStreamWriter.getEncoding();
		// 写出的都是使用的StreamEncoder的方式来写
		char[] c = new char[] { 'a', 'g', 't', 'h' };
		// 写出字符串
		outputStreamWriter.write("gogo");
		// 写出一个int值
		outputStreamWriter.write(4);
		// 写出char数组的数据
		outputStreamWriter.write(c);
		outputStreamWriter.write(c, 1, 2);
		// 写出字符串指定位置的数据
		outputStreamWriter.write("测试写出", 1, 2);

		// 写的方法是一样的就是将this返回过来。让输出流可以在多个对象中传递
		Writer append2 = outputStreamWriter.append('c');
		Writer append = outputStreamWriter.append(new String("fortest"));
		Writer append3 = outputStreamWriter.append("test", 2, 3);

		// 将缓冲的数据写到硬盘
		outputStreamWriter.flush();
		// 关闭输出流
		outputStreamWriter.close();
	}
}
