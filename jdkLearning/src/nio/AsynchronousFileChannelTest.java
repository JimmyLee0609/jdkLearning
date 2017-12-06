package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.EnumSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsynchronousFileChannelTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, TimeoutException {
//		open();
//		statue();
//		read();
		lock();
	}

	@SuppressWarnings("unused")
	private static void lock() throws IOException, InterruptedException, ExecutionException {
		ExecutorService ftp = Executors.newFixedThreadPool(5);
//		ָ���ļ��Ĵ򿪷�ʽ���̳߳أ��ļ��Ķ�д����   
		AsynchronousFileChannel open = AsynchronousFileChannel.open(
																Paths.get("d:/temp/abc.txt"),
																EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE), 
																ftp, 
																new FileAttribute<?>[0]);
//		���ص���һ��future����
		Future<FileLock> lock = open.lock(10, 1, false);
		Future<FileLock> lock2 = open.lock(20, 1, false);
		Future<FileLock> lock3 = open.lock(30, 2, true);
		
		FileLock fileLock = lock.get();
		FileLock fileLock2 = lock2.get();
		FileLock fileLock3 = lock3.get();
		
		fileLock.release();
		fileLock2.release();
		fileLock3.release();
		
		CompletionHandler<FileLock, FileLock> handler = new CompletionHandler<FileLock, FileLock>() {

			@Override
			public void completed(FileLock result, FileLock attachment) {
				System.out.println("���ɹ�");
			}

			@Override
			public void failed(Throwable exc, FileLock attachment) {
				System.out.println("��ʧ��");
			}
		};
		open.lock(1000,1,false,fileLock3, handler);
//		�������ֱ�ӷ���������
		FileLock tryLock = open.tryLock();
		
		Channel acquiredBy = fileLock3.acquiredBy();
		fileLock3.release();
		open.close();
		ftp.shutdown();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private static void read() throws IOException, InterruptedException, ExecutionException, TimeoutException {
		ExecutorService ftp = Executors.newFixedThreadPool(5);
//		ָ���ļ��Ĵ򿪷�ʽ���̳߳أ��ļ��Ķ�д����   
		AsynchronousFileChannel open = AsynchronousFileChannel.open(
																Paths.get("d:/temp/abc.txt"),
																EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE), 
																ftp, 
																new FileAttribute<?>[0]);
		
		ByteBuffer dst = ByteBuffer.allocate(26);
//		ͨ���첽��ȡ�ļ������棬ָ�����ļ���position��ʼ��ȡ����ȡ����Ĵ�С��������ļ���β�ͽ���
		Future<Integer> read = open.read(dst, 0);
		dst.rewind();
		Future<Integer> read2 = open.read(dst, 2);
		dst.rewind();
		Future<Integer> read3 = open.read(dst, 2);
		dst.rewind();
		Future<Integer> read4 = open.read(dst, 2);
		dst.rewind();
		
		ByteBuffer next = ByteBuffer.allocate(26);
		open.read(dst, 10, dst, new CompletionHandler<Integer,ByteBuffer>(){
			@Override
			public void completed(Integer result, ByteBuffer attachment) {
//				io�����ɹ����������result��Integer���ͣ����ڼ�¼��ȡ�˶��ٸ��ֽ�
				
			}
			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
//				IO����ʧ�ܣ��������
				
			}
		});
		
		Integer integer = read.get(50, TimeUnit.MILLISECONDS);
		Integer integer2 = read2.get();
		Integer integer3 = read3.get();
		Integer integer4 = read4.get();
		ByteBuffer src = ByteBuffer.wrap("����д��".getBytes());
//		���������е�����д���ļ������ļ���positionλ�ÿ�ʼ���ǣ�
		open.write(src, 0);
		AsynchronousFileChannel truncate = open.truncate(5);
		ByteBuffer src1 = ByteBuffer.wrap("����completionHandler".getBytes());
		open.close();
//		��������������д���ļ�����positionλ�ÿ�ʼ���ǣ�
		open.write(src1, 0, src1, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {
//			д��ɹ������������			
				System.out.println("succed write "+result);
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
//			д�벻�ɹ������������
				System.out.println(exc.getMessage());
			}
		});
		ftp.shutdown();
		open.close();
	}

	private static void statue() throws IOException {
		AsynchronousFileChannel afc = AsynchronousFileChannel.open(
																	Paths.get("d:/temp/abc.txt"),
																	StandardOpenOption.READ, StandardOpenOption.WRITE);
//		ǿ�ƻ�������Ӳ���ļ�ͬ��		
		afc.force(true);
//		ͨ���Ƿ��
		boolean open = afc.isOpen();
//		ͨ���Ĵ�С
		long size = afc.size();
//		�ض��ļ�
		AsynchronousFileChannel truncate = afc.truncate(50);
//		�ر�ͨ��
		afc.close();
	}

	@SuppressWarnings("unused")
	private static void open() throws IOException {
		AsynchronousFileChannel afc = AsynchronousFileChannel.open(Paths.get("d:/temp/abc.txt"),
				StandardOpenOption.READ, StandardOpenOption.WRITE);

		ExecutorService ftp = Executors.newFixedThreadPool(5);
//		ָ���ļ��Ĵ򿪷�ʽ���̳߳أ��ļ��Ķ�д����   
		AsynchronousFileChannel open = AsynchronousFileChannel.open(
																Paths.get("d:/temp/abc.txt"),
																EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE), 
																ftp, 
																new FileAttribute<?>[0]);
		afc.close();
		open.close();
		
	}

}
