package lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RunTimeTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {
		shutdownHook();
		Runtime runtime = Runtime.getRuntime();

		int availableProcessors = runtime.availableProcessors();// ���ý����� 4
		long maxMemory = runtime.maxMemory();// java �������ͼ��ȡ����ڴ�937951232
		long freeMemory = runtime.freeMemory();// ����������п��е��ڴ����� 62809216
		long totalMemory = runtime.totalMemory();// ������������ڴ����� 64487424

		runtime.traceInstructions(true);// ���ã�����ָ����١�
		runtime.traceMethodCalls(true);// ���ã����÷������ø��١�
		// ������  ��������  c  c++�й�
		// runtime.load("gdi32");//����ָ�������ı��ض�̬��
		// runtime.loadLibrary("gdi32");// ���ؾ���ָ�������Ķ�̬�⡣
		Process exec = runtime.exec("cmd nodepad");// ������SHELL����
		// runtime.exec(cmdarray, envp, dir)//ָ������ ,ָ�����л���, ָ��Ŀ¼
		String string = runtime.toString();
		exec.destroy();// ��������
		runtime.exit(0);// ������˳� �˳�ǰ�������й����߳� 0 RUNNING 1 HOOKS 2 FINALIZERS
		// runtime.halt(5);//ǿ����ֹ��ǰ�����
		runtime.gc();//
	}

	private static void shutdownHook() {
		Thread t = new Thread(() -> {
			try {
				Thread.sleep(3000);
				System.out.println("start");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Runtime runtime = Runtime.getRuntime();
		// ע��������رչ���
		runtime.addShutdownHook(t);
		// ȡ��ע��������رչ���
		// boolean removeShutdownHook =
		// runtime.removeShutdownHook(Thread.currentThread());
	}

}
