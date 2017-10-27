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
		new StringWriter();// ���ǰ�װ��һ��StringBuffer
		printWriter();
	}

	@SuppressWarnings("unused")
	private static void printWriter() throws FileNotFoundException, UnsupportedEncodingException {
		// ===================PrintWriter==========================
		// ���캯�� �����ļ����� ָ���ַ���
		PrintWriter printWriter = new PrintWriter("d:/temp/abc.t");
		PrintWriter printWriter5 = new PrintWriter("d:/temp/abc.t", "utf-8");
		printWriter.close();
		printWriter5.close();
		// ����file �� ָ�����ַ���
		PrintWriter printWriter2 = new PrintWriter(new File("d:/temp/abc.t"));
		PrintWriter printWriter6 = new PrintWriter(new File("d:/temp/abc.t"), "utf-8");
		printWriter2.close();
		printWriter6.close();
		// �����ֽ������ �����趨�Ƿ��Զ�flush��Ĭ��false
		PrintWriter printWriter3 = new PrintWriter(new FileOutputStream("d:/temp/abc.t"));
		PrintWriter printWriter7 = new PrintWriter(new FileOutputStream("d:/temp/abc.t"), false);
		printWriter3.close();
		printWriter7.close();
		// �����ַ������ �����趨�Ƿ��Զ�flush��Ĭ��false
		PrintWriter printWriter4 = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream("d:/temp/abc.t"), "utf-8"));
		PrintWriter printWriter8 = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream("d:/temp/abc.t"), "utf-8"), false);
		printWriter4.close();
		printWriter8.close();

		PrintWriter printWriter9 = new PrintWriter("d:/temp/abc.t");
//		�����������д�����壬�ڲ����õĻ���write�ķ�����ֻ�ǰ�װ��һ�½����ݶ����String�����㿴
		printWriter9.print(false);
		printWriter9.print('a');
		printWriter9.print(4);
		printWriter9.print(43.5f);
		printWriter9.print(5.3d);
		printWriter9.print(53516l);
		printWriter9.print("����printChar".toCharArray());
		printWriter9.print("testPrint");
//		д�������ȫ��+��Ӧ�ĵ�ַ@379619aa
		printWriter9.print(new SerializableDomain());
//		�����������  ĩβ���ϻ��з�   д������
		printWriter9.println();
		printWriter9.println(false);
		printWriter9.println('z');
		printWriter9.println(79);
		printWriter9.println(128f);
		printWriter9.println(7953341l);
		printWriter9.println(793.565d);
		printWriter9.println("����printlnChar".toCharArray());
		printWriter9.println("�����ַ���");
//		д�������ȫ��+��Ӧ�ĵ�ַ@cac736f
		printWriter9.println(new SerializableDomain(5, false, "second"));

		boolean checkError = printWriter9.checkError();//������Ƿ�����
		
//		�����������д���ڴ棬���Զ��ӻ��з�
		PrintWriter append = printWriter9.append('z');
		PrintWriter append3 = printWriter9.append("����append");
		PrintWriter append2 = printWriter9.append("����appendʱ", 3, 4);

		printWriter9.write(4);
		printWriter9.write("д��");
		printWriter9.write("����д������ĸ", 2, 3);
		printWriter9.write("����write");
//		��������������д��Ӳ��
		printWriter9.flush();

//		��ʽ��
		PrintWriter format2 = printWriter9.format("formatString", 1);//ʹ��util��Formatter
		PrintWriter format = printWriter9.format(Locale.CHINA, "formatString", 1);
		PrintWriter printf = printWriter9.printf("String", 1);//����ʹ��format�ķ���
		printWriter9.printf(Locale.CHINA, "testStringh", 1);
		
//		��������������д��Ӳ��
		printWriter9.flush();
//		�ر������
		printWriter9.close();
	}

	@SuppressWarnings("unused")
	private static void bufferedWriter() throws IOException {
		// =================BufferedWriter======================================

		// ���캯������װһ���ַ������������ָ������Ĵ�С
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("d:/temp/abc.t"), "utf-8"), 8120);
		char[] c = new char[] { 'c', 'f', 'r', 't', 'h' };
		// дһ�����з�������
		writer.newLine();
		//
		Writer append = writer.append("csad");
		Writer append2 = writer.append('d');
		Writer append3 = writer.append("begintest", 2, 5);
		writer.write("�����õ��ַ���", 2, 3);
		writer.write("testString");
		writer.write(c);
		writer.write(8);

		String string = writer.toString();
		// ������д�����������ջ���
		writer.flush();
		// ������д��������� ��ջ��棬�ر������
		writer.close();

	}

	@SuppressWarnings("unused")
	private static void charArrayWriter() throws FileNotFoundException, IOException {
		// ====================CharArrayWriter=============================
		// CharArrayWriter����û�й�����������Լ�ά��һ�����飬�����ڴ���û�������ݺ󣬴����������д��
		CharArrayWriter charArrayWriter = new CharArrayWriter();
		char[] charArray = charArrayWriter.toCharArray();
		charArrayWriter.writeTo(new OutputStreamWriter(new FileOutputStream("d:/temp/abc.t")));

		char[] c = new char[] { 'c', 'f', 'd', 't', 'p' };
		// �������ʵ����ʵһ�������ǻ᷵��this��
		CharArrayWriter append = charArrayWriter.append('g');
		CharArrayWriter append2 = charArrayWriter.append("test");
		CharArrayWriter append3 = charArrayWriter.append("again", 2, 3);
		// ������д��������
		charArrayWriter.write(75);
		charArrayWriter.write(c);
		charArrayWriter.write("����");
		charArrayWriter.write("����һ������", 2, 3);
		charArrayWriter.write(c, 2, 2);

		// ����������ݶ�������ָ�뷵�ص�0λ�ã������Ļ��潫��0λ�ÿ�ʼ����ԭ��������
		charArrayWriter.reset();
		// �����char���鳤��
		int size = charArrayWriter.size();
		String string = charArrayWriter.toString();

		// û�й����� ��ʵ��
		charArrayWriter.flush();
		charArrayWriter.close();
	}

	@SuppressWarnings("unused")
	private static void outputStreamWriter() throws IOException {
		// =================�ַ������OutputStreamWriter====================================
		// ���캯�� ��װһ�������� ����StreamEncoder�������ֽ������ ת�����ַ������
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("d:/temp/testWriter"),
				"utf-8");
		// ��ȡ�����ַ���
		String encoding = outputStreamWriter.getEncoding();
		// д���Ķ���ʹ�õ�StreamEncoder�ķ�ʽ��д
		char[] c = new char[] { 'a', 'g', 't', 'h' };
		// д���ַ���
		outputStreamWriter.write("gogo");
		// д��һ��intֵ
		outputStreamWriter.write(4);
		// д��char���������
		outputStreamWriter.write(c);
		outputStreamWriter.write(c, 1, 2);
		// д���ַ���ָ��λ�õ�����
		outputStreamWriter.write("����д��", 1, 2);

		// д�ķ�����һ���ľ��ǽ�this���ع�����������������ڶ�������д���
		Writer append2 = outputStreamWriter.append('c');
		Writer append = outputStreamWriter.append(new String("fortest"));
		Writer append3 = outputStreamWriter.append("test", 2, 3);

		// �����������д��Ӳ��
		outputStreamWriter.flush();
		// �ر������
		outputStreamWriter.close();
	}
}
