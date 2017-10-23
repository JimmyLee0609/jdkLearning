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
		
		FileDescriptor fileDescriptor = new FileDescriptor();
		fileDescriptor.sync();
		boolean valid = fileDescriptor.valid();
		
		
	}

}
