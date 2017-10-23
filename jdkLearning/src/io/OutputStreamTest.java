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
//		�ֽ������
//		fileOutputStream();
//		�ֽڻ�����
//		bufferedOutputStream();
//		�ֽ����黺��
//		byteArrayOutputStream();
//		���������
//		dataOutputStream();
//		��ȡϵͳ�Ŀ���̨
		Console console = System.console();//Ĭ����Null
		printStream();
	}


	@SuppressWarnings("unused")
	private static void printStream() throws IOException {
//		=================�ֽ������PrintStream======================================
//		=================���캯��===============================================
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
		
		PrintStream append2 = stream.append('a');//���õ��� print - ->write
		PrintStream append = stream.append("char");
		PrintStream append3 = stream.append("charset", 2, 2);
		
//		����Ƿ��г�������಻���쳣
		boolean checkError = stream.checkError();
		
//		�����������ת����String����ʽ��д������
		stream.print(false);
		stream.print(7);
		stream.print(76f);
		stream.print(565d);
		stream.print(56l);
		stream.print('s');
		stream.print("print chars".toCharArray());
		stream.print("print String");
		stream.print(new SerializableDomain());
		
//		�����������ת����String����ʽ��Ȼ����ӻ��з���д������
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
		
		
//		ʹ�ø�ʽ����    ��ʽ�����
		PrintStream printf = stream.printf("String format", 2,1);//ʹ�õ���format
		PrintStream printf2 = stream.printf(Locale.CHINA, "String format", 1,2);//ʹ�õ���format
		
		PrintStream format = stream.format("String for format", 1);//ʹ�õ���Formatter��ʽ����
		PrintStream format2 = stream.format(Locale.CHINA	,"String for format locale", 1);//ʹ�õ���Formatter��ʽ����
		
//		������д����
		stream.flush();
//		�ر���
		stream.close();
	}


	private static void dataOutputStream() throws IOException {
//		=================DataOutputStream============================
//		���캯��
		DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("d:/temp/abc.t", true));
//		д��������������
		dataOutputStream.writeBoolean(false);//д��һ������ֵ1byte
		dataOutputStream.writeByte(2);//д��һ��byteֵ1��byte
		dataOutputStream.writeShort(79);//д��һ��shortֵ2��byte
		dataOutputStream.writeInt(5648);//д��һ��intֵ4��byte
		dataOutputStream.writeLong(5556884l);//д��һ��longֵ8��byte

		dataOutputStream.writeChar('g');//д��һ��charֵ��2��byte
		dataOutputStream.writeChars("chars");//��ȡ�ַ�����ÿһ��char���������2��int����һ������8λ��Ϊ��λ��¼
		dataOutputStream.writeBytes("writeBytes");//���ַ�������ֽ��������

		dataOutputStream.writeFloat(5.3f);//д��һ��floatֵ��4���ֽ�
		dataOutputStream.writeDouble(53.3d);//д��һ��doubleֵ��8��byte

		//дutf����ʱ���Ὣ��Ҫд�����ּ���д��ͷ�����ֽڣ����ڼ�¼��Ҫ��ȡ���ֽ���	
		dataOutputStream.writeUTF("utf8");//����utf�ı���Ҫ��д���ַ���

		byte[] b = new byte[] { 79, 80, 81, 82, 83, 84 };
		dataOutputStream.write(5);//д��һ��ָ�����ֽ�
		dataOutputStream.write(b);//��ָ�����������д��
		dataOutputStream.write(b, 0, 5);//��ָ�������ָ��λ�ÿ�ʼ��д��ָ�����ȵ�����
		dataOutputStream.flush();//�������flush

		dataOutputStream.size();//д�����ֽڵ���Ŀ
		dataOutputStream.close();//�ر���
	}

	@SuppressWarnings("unused")
	private static void fileOutputStream() throws IOException {
		// ========================FileOutputStream=================================
		// ���캯��
		FileOutputStream stream = new FileOutputStream("d:/temp/abc.t");
		// �Ƿ����ļ�ĩβ��ʼд����
		FileOutputStream stream2 = new FileOutputStream("d:/temp/abc.t", true);
		stream2.close();
		FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/temp/abc.t"));
		fileOutputStream.close();
		FileOutputStream fileOutputStream2 = new FileOutputStream(new File("d:/temp/abc.t"), true);
		fileOutputStream2.close();
		// �����ļ�������������
		FileOutputStream fileOutputStream3 = new FileOutputStream(new FileDescriptor());
		fileOutputStream3.close();

		// ��ȡ�����ļ�������
		FileDescriptor fd = stream.getFD();
		new FileInputStream(fd).close();// �ļ��������������������,���Դ���.����һ��·��

		byte[] b = new byte[100];
		// ������д���ļ���
		stream.write(b);// дһ�����������
		stream.write(b, 0, 40);// �������ָ��λ�ÿ�ʼ,дָ������
		stream.write(5);// дһ���ֽ�
		// ��ʵ��
		stream.flush();
		// ��ȡ�ܵ�
		FileChannel channel = stream.getChannel();

		stream.toString();
		// �ر���
		stream.close();
	}

	@SuppressWarnings("unused")
	private static void byteArrayOutputStream() throws IOException {
		// ===================ByteArrayOutputStream=========================================
		// �����ά��һ������,���л���.û�й�����
		// ���캯��,����ָ����С,Ҳ���Բ�ָ�� Ĭ�ϴ�С��32
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
		// ����������ݸ���һ��
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		byte[] b = new byte[1024];
		// ��һ��byteд������
		byteArrayOutputStream.write(5);
		// ��һ�����������д������
		byteArrayOutputStream.write(b);
		// ��һ�������ָ��λ�ÿ�ʼ,��ָ�����ȵ�����д������
		byteArrayOutputStream.write(b, 0, 5);
		// ��ǰ��������Ĵ�С
		int size = byteArrayOutputStream.size();

		// �����������д�������
		byteArrayOutputStream.writeTo(new FileOutputStream("d:/temp/abc.t"));
		// û�й�������ʵ��
		byteArrayOutputStream.flush();
		// ��count��Ϊ0,���ǽ�ԭ����������ݶ���Ҫ��,�µ���Ҫ��������ݴ�����Ŀ�ͷ��ʼ����
		byteArrayOutputStream.reset();
		// �����������ת��Ϊ�ַ���
		String string = byteArrayOutputStream.toString();
		// �����������ת��Ϊָ��������ַ���
		String string3 = byteArrayOutputStream.toString("utf-8");
		// ��ʵ��
		byteArrayOutputStream.close();
		// String string2 = byteArrayOutputStream.toString(5);//��ʱ
	}

	private static void bufferedOutputStream() throws IOException {
		// ===================BufferedOutputStream====================================
		// ��װ�ֽ������,������������ֽ������, ����Ĭ�ϴ�С8192 ����ָ����1024�Ĵ�С
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream("d:/temp/testOUT.txt"), 1024);
		byte[] b = new byte[1024];
		// д��һ��byte
		stream.write(5);
		// д��һ�����������
		stream.write(b);
		// д������ָ�����ȵ�����
		stream.write(b, 0, 15);

		// д�������ݻỺ���������Ļ���������.��Ҫflush�Ż�������д��Ӳ���ļ�����Ӳ�̲���,�ӿ�ϵͳ����
		// ������ݻ��浽һ����С.��Ҫд������ݴ治��ʱ,�ͻὫ����д���ļ�,��ջ���,��׼��д�����ݻ���

		// ������д���ļ���
		stream.flush();
		// �ر������
		stream.close();
	}

}
