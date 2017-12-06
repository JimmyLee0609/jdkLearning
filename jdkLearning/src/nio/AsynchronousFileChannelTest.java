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
//		指定文件的打开方式，线程池，文件的读写属性   
		AsynchronousFileChannel open = AsynchronousFileChannel.open(
																Paths.get("d:/temp/abc.txt"),
																EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE), 
																ftp, 
																new FileAttribute<?>[0]);
//		返回的是一个future对象
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
				System.out.println("锁成功");
			}

			@Override
			public void failed(Throwable exc, FileLock attachment) {
				System.out.println("锁失败");
			}
		};
		open.lock(1000,1,false,fileLock3, handler);
//		这个方法直接返回锁对象
		FileLock tryLock = open.tryLock();
		
		Channel acquiredBy = fileLock3.acquiredBy();
		fileLock3.release();
		open.close();
		ftp.shutdown();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private static void read() throws IOException, InterruptedException, ExecutionException, TimeoutException {
		ExecutorService ftp = Executors.newFixedThreadPool(5);
//		指定文件的打开方式，线程池，文件的读写属性   
		AsynchronousFileChannel open = AsynchronousFileChannel.open(
																Paths.get("d:/temp/abc.txt"),
																EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE), 
																ftp, 
																new FileAttribute<?>[0]);
		
		ByteBuffer dst = ByteBuffer.allocate(26);
//		通道异步读取文件到缓存，指定从文件的position开始读取，读取缓存的大小，如果到文件结尾就结束
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
//				io操作成功调用这个，result是Integer类型，用于记录读取了多少个字节
				
			}
			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
//				IO操作失败，调用这个
				
			}
		});
		
		Integer integer = read.get(50, TimeUnit.MILLISECONDS);
		Integer integer2 = read2.get();
		Integer integer3 = read3.get();
		Integer integer4 = read4.get();
		ByteBuffer src = ByteBuffer.wrap("测试写出".getBytes());
//		将缓冲区中的数据写到文件，从文件的position位置开始覆盖，
		open.write(src, 0);
		AsynchronousFileChannel truncate = open.truncate(5);
		ByteBuffer src1 = ByteBuffer.wrap("测试completionHandler".getBytes());
		open.close();
//		将缓冲区的数据写到文件，从position位置开始覆盖，
		open.write(src1, 0, src1, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {
//			写入成功调用这个方法			
				System.out.println("succed write "+result);
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
//			写入不成功调用这个方法
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
//		强制缓冲区与硬件文件同步		
		afc.force(true);
//		通道是否打开
		boolean open = afc.isOpen();
//		通道的大小
		long size = afc.size();
//		截断文件
		AsynchronousFileChannel truncate = afc.truncate(50);
//		关闭通道
		afc.close();
	}

	@SuppressWarnings("unused")
	private static void open() throws IOException {
		AsynchronousFileChannel afc = AsynchronousFileChannel.open(Paths.get("d:/temp/abc.txt"),
				StandardOpenOption.READ, StandardOpenOption.WRITE);

		ExecutorService ftp = Executors.newFixedThreadPool(5);
//		指定文件的打开方式，线程池，文件的读写属性   
		AsynchronousFileChannel open = AsynchronousFileChannel.open(
																Paths.get("d:/temp/abc.txt"),
																EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE), 
																ftp, 
																new FileAttribute<?>[0]);
		afc.close();
		open.close();
		
	}

}
