package io;

import java.io.FileDescriptor;
import java.io.SyncFailedException;

public class FileDescriptorTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws SyncFailedException {
//		��׼������
		FileDescriptor err = FileDescriptor.err;
//		��׼������
		FileDescriptor in = FileDescriptor.in;
//		��׼�����  ��ϵͳ��ص�   consol
		FileDescriptor out = FileDescriptor.out;
//		�ļ�������  ��ʵ��Path���
		FileDescriptor fileDescriptor = new FileDescriptor();
//		ǿ�Ʒ�λ�豸����Դͬ��������
		fileDescriptor.sync();
		boolean valid = fileDescriptor.valid();
		
		
	}

}
