package io;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class RandomAccessFileTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// 任意位置访问文件 模式有 r rw rws rwd
		/*
		 * "rws" 和 "rwd" 模式的工作方式极其类似 FileChannel 类的 force(boolean) 方法， 分别传递 true
		 * 和 false 参数，除非它们始终应用于每个 I/O 操作，并因此通常更为高效。
		 * 如果该文件位于本地存储设备上，那么当返回此类的一个方法的调用时， 可以保证由该调用对此文件所做的所有更改均被写入该设备。
		 * 这对确保在系统崩溃时不会丢失重要信息特别有用。如果该文件不在本地设备上，则无法提供这样的保证。
		 */
		RandomAccessFile file = new RandomAccessFile("d:/temp/abc.t", "rw");
		FileChannel channel = file.getChannel();
		FileDescriptor fd = file.getFD();
		long filePointer = file.getFilePointer();
//		设置偏移量
		file.seek(50l);//从文件开头跳过指定的字节数
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

//		读取，写入的方式跟data的输入输出流一样的操作
		file.write(b);
		file.write(4);
		file.write(b, 2, 3);
		file.writeBoolean(false);
		file.writeByte(1);
		file.writeBytes("test");
		file.writeChar(4);
		file.writeChars("charse test");//将char字符值转换成 2个 byte  首位要右移8位作为低位 来保存
		file.writeDouble(7.3);
		file.writeFloat(156f);
		file.writeInt(45);
		file.writeLong(4757l);
		file.writeShort(46);
		file.writeUTF("utf string");
		
//		跳过指定数量的字节
		file.skipBytes(16);
		
		file.toString();
//		设置文件大小，多余的补0,  比原来小的也直接不读后面的
		file.setLength(8651l);
		long length = file.length();
		file.setLength(800);
		
		file.close();
	}

}
