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

		// ==========================���캯��==============================
		// �ղ� ������δ���ӵ�
		/*PipedInputStream stream = new PipedInputStream();
		// ������δ���ӵ� ָ����������С��
		PipedInputStream pipedInputStream = new PipedInputStream(1024);
		pipedInputStream.close();
		// ����PipedInputStream��ʹ�����ӵ��ܵ��������
		PipedInputStream pipedInputStream2 = new PipedInputStream(new PipedOutputStream());
		pipedInputStream2.close();
		// ����PipedInputStream��ʹ�����ӵ��ܵ�������У������û������Ĵ�С
		PipedInputStream pipedInputStream3 = new PipedInputStream(new PipedOutputStream(), 1024);
		pipedInputStream3.close();

		// =====================����===============================
		byte[] b = new byte[1024];
		int available = stream.available();// 0
		boolean markSupported = stream.markSupported();// false
		// ��ȡ�˹ܵ��������е���һ�������ֽڡ�
		int read = stream.read();//���������̣߳�
		int read2 = stream.read(b);

		long skip = stream.skip(5);
		// stream.mark(1);��֧��
		// stream.reset();��֧��

		int read3 = stream.read(b);
		// ���ӵ������
		stream.connect(new PipedOutputStream());//ֻҪһ��������һ�ξ����ˣ��ٴ��������쳣��
		stream.close();*/
	}

	static PipedInputStream pipedInputStream;
	static PipedOutputStream outputStream;

	@SuppressWarnings("unused")
	private static void connectPipedStream() throws IOException, InterruptedException {
		
//		����������Ķ���Ȼ����������Ķ����������Ķ���
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
		byte[] bytes = "�����ô��".getBytes();
		outputStream.write(bytes, 0, bytes.length);
System.out.println("write complete");
		outputStream.close();
	}

	@SuppressWarnings("unused")
	private static void openInputStream() throws IOException, InterruptedException {
		pipedInputStream = new PipedInputStream(outputStream);
//		��ǰ���Զ�ȡ������
		int available = pipedInputStream.available();
//		��ȡһ���ֽ�
		int read = pipedInputStream.read();//�߳���������
//		����һ���ֽ�
		long skip = pipedInputStream.skip(1);//�߳���������
		byte[] b = new byte[20];
//		��ȡָ���ֽڵ�ָ������
		int read2 = pipedInputStream.read(b, 0,20);//�߳�������������������������
		System.out.println(Arrays.toString(b));
		System.out.println("read complete");
		pipedInputStream.close();
	}

}
