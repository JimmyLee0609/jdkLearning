package executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ExecutorCompletionServiceTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		/*
		 * �������ĻὫ�ύ�����������������ʱ����̳߳�ȥ����,�̳߳ؽ����з��ص�Future���浽, ������������.��ʵ�����Ѿ�ִ������
		 * ��һ��poll����take��ʱ��,��ȡ������������ɵ�future, ֻ�������������Żᱣ��Future,������,�����쳣�жϵ��߳̽����ᱣ��,
		 * ��Щ���͵��߳���Ҫʹ���̳߳ص�����ȥ����Ӧ�Ĵ��� ������������ɵ�ʱ��,�Ὣ������浽Future��
		 * ������쳣���,�Ὣ�쳣���浽Future��,����ʹ��Future��get������ʱ��,�ͻὫ����쳣�׳�
		 */

		// �����̳߳�
		ExecutorService pool = Executors.newCachedThreadPool();
		// ��������Completion�Ķ���
		LinkedBlockingQueue<Future<String>> queue = new LinkedBlockingQueue<>();
		// ����һ��ʹ���̳߳�����������ķ���
		ExecutorCompletionService<String> service = new ExecutorCompletionService<>(pool, queue);
		// ����һ������
		Callable<String> task = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(1000);
				System.out.println("call");
				return "call";
			}
		};

		Callable<String> exceptionTask = new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("�쳣�����񱻵���");
				throw new Exception("��ʩһ");
			}
		};
		// ʹ�÷����ύһ������,����һ��Future,�ύ��������Զ�����,Ȼ�󱣴浽��ɶ�����
		Future<String> submit = service.submit(task);
		Future<String> submit2 = service.submit(exceptionTask);
		try {
			String string = submit.get();
			String string2 = submit2.get();
			System.out.println();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��������������ȴ�ָ����ʱ��,Ȼ�󷵻ض����е��������,�������������е�ʵ��������
		Future<String> poll = service.poll(1, TimeUnit.SECONDS);
		// Ҳ�Ǹ�����ɶ��е�ʵ��,�Ƿ�����
		Future<String> take = service.take();

	}

}
