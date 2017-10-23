package io;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class RandomAccessFileTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// ����λ�÷����ļ� ģʽ�� r rw rws rwd
		/*
		 * "rws" �� "rwd" ģʽ�Ĺ�����ʽ�������� FileChannel ��� force(boolean) ������ �ֱ𴫵� true
		 * �� false ��������������ʼ��Ӧ����ÿ�� I/O �����������ͨ����Ϊ��Ч��
		 * ������ļ�λ�ڱ��ش洢�豸�ϣ���ô�����ش����һ�������ĵ���ʱ�� ���Ա�֤�ɸõ��öԴ��ļ����������и��ľ���д����豸��
		 * ���ȷ����ϵͳ����ʱ���ᶪʧ��Ҫ��Ϣ�ر����á�������ļ����ڱ����豸�ϣ����޷��ṩ�����ı�֤��
		 */
		RandomAccessFile file = new RandomAccessFile("d:/temp/abc.t", "rw");
		FileChannel channel = file.getChannel();
		FileDescriptor fd = file.getFD();
		long filePointer = file.getFilePointer();
//		����ƫ����
		file.seek(50l);//���ļ���ͷ����ָ�����ֽ���
		file.writeChars("just skip 50 words,just skip 50 words,just skip 50 words,just skip 50 words,just skip 50 words,just skip 50 words,just skip 50 words,");
		byte[] b = new byte[] { 45, 16, 87, 65, 35, 52, 5, 48, 68, 9, 6, 21, 35, 5, 15, 68, 48, 89, 65, 32, 21, 12 };
		int read = file.read();
		int read2 = file.read(b);
		int read3 = file.read(b, 2, 5);
		boolean readBoolean = file.readBoolean();
		byte readByte = file.readByte();
		char readChar = file.readChar();
		double readDouble = file.readDouble();
		float readFloat = file.readFloat();
		int readInt = file.readInt();
		String readLine = file.readLine();
		long readLong = file.readLong();
		short readShort = file.readShort();
		int readUnsignedShort = file.readUnsignedShort();
		int readUnsignedByte = file.readUnsignedByte();
//		String readUTF = file.readUTF();
		file.readFully(b);
		file.readFully(b, 12, 3);

//		��ȡ��д��ķ�ʽ��data�����������һ���Ĳ���
		file.write(b);
		file.write(4);
		file.write(b, 2, 3);
		file.writeBoolean(false);
		file.writeByte(1);
		file.writeBytes("test");
		file.writeChar(4);
		file.writeChars("charse test");//��char�ַ�ֵת���� 2�� byte  ��λҪ����8λ��Ϊ��λ ������
		file.writeDouble(7.3);
		file.writeFloat(156f);
		file.writeInt(45);
		file.writeLong(4757l);
		file.writeShort(46);
		file.writeUTF("utf string");
		
//		����ָ���������ֽ�
		file.skipBytes(16);
		
		file.toString();
//		�����ļ���С������Ĳ�0,  ��ԭ��С��Ҳֱ�Ӳ��������
		file.setLength(8651l);
		long length = file.length();
		file.setLength(800);
		
		file.close();
	}

}
