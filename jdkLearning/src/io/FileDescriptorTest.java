package io;

import java.io.FileDescriptor;
import java.io.SyncFailedException;

public class FileDescriptorTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws SyncFailedException {
//		标准错误流
		FileDescriptor err = FileDescriptor.err;
//		标准输入流
		FileDescriptor in = FileDescriptor.in;
//		标准输出流  与系统相关的   consol
		FileDescriptor out = FileDescriptor.out;
//		文件描述符  其实和Path差不多
		FileDescriptor fileDescriptor = new FileDescriptor();
//		强制方位设备的资源同步。。？
		fileDescriptor.sync();
		boolean valid = fileDescriptor.valid();
		
		
	}

}
