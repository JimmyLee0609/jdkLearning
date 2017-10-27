package io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PipedInputStreamTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {

		connectPipedStream();

		// ==========================构造函数==============================
		// 空参 创建尚未连接的
		/*PipedInputStream stream = new PipedInputStream();
		// 创建尚未连接的 指定缓冲区大小的
		PipedInputStream pipedInputStream = new PipedInputStream(1024);
		pipedInputStream.close();
		// 创建PipedInputStream，使其连接到管道输出流中
		PipedInputStream pipedInputStream2 = new PipedInputStream(new PipedOutputStream());
		pipedInputStream2.close();
		// 创建PipedInputStream，使其连接到管道输出流中，并设置缓冲区的大小
		PipedInputStream pipedInputStream3 = new PipedInputStream(new PipedOutputStream(), 1024);
		pipedInputStream3.close();

		// =====================方法===============================
		byte[] b = new byte[1024];
		int available = stream.available();// 0
		boolean markSupported = stream.markSupported();// false
		// 读取此管道输入流中的下一个数据字节。
		int read = stream.read();//永久阻塞线程，
		int read2 = stream.read(b);

		long skip = stream.skip(5);
		// stream.mark(1);不支持
		// stream.reset();不支持

		int read3 = stream.read(b);
		// 连接到输出流
		stream.connect(new PipedOutputStream());//只要一方连接了一次就行了，再次连接抛异常。
		stream.close();*/
	}

	static PipedInputStream pipedInputStream;
	static PipedOutputStream outputStream;

	@SuppressWarnings("unused")
	private static void connectPipedStream() throws IOException, InterruptedException {
		
//		先有输出流的对象，然后用输出流的对象建输入流的对象
ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		Thread thread = new Thread(threadGroup,()->{
			try {
				openOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread.start();
		Thread.sleep(20);
		Thread thread2 = new Thread(threadGroup,()->{
			try {
				openInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread2.start();
	}

	private static void openOutputStream() throws IOException, InterruptedException {
		outputStream = new PipedOutputStream();
		Thread.currentThread().wait();
		outputStream.write(4);
		outputStream.write(4);
		byte[] bytes = "今个怎么了".getBytes();
		outputStream.write(bytes, 0, bytes.length);
System.out.println("write complete");
		outputStream.close();
	}

	@SuppressWarnings("unused")
	private static void openInputStream() throws IOException, InterruptedException {
		pipedInputStream = new PipedInputStream(outputStream);
//		当前可以读取的数量
		int available = pipedInputStream.available();
//		读取一个字节
		int read = pipedInputStream.read();//线程永久阻塞
//		跳过一个字节
		long skip = pipedInputStream.skip(1);//线程永久阻塞
		byte[] b = new byte[20];
//		读取指定字节到指定数组
		int read2 = pipedInputStream.read(b, 0,20);//线程永久阻塞，不会读满这个数组
		System.out.println(Arrays.toString(b));
		System.out.println("read complete");
		pipedInputStream.close();
	}

}
