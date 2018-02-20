package task;

import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1872787304014915976L;
	final int n;

	public Fibonacci(int n) {
		super();
		this.n = n;
	}

	@Override
	protected Integer compute() {
		if (n <= 1)
			return n;
		Fibonacci f1 = new Fibonacci(n - 1);
		f1.fork();//ÿ��fork��������ӵ���ǰ�����̵߳�WorkQueue�У����ڹ����̵߳ģ��ύ��CommonPool��Workqueue�С�
//		��������������notify�����߳�ȥ���������û�п����߳̾ͻ�ȥ�½��߳�
//		ÿһ�������̣߳��ͻ���һ��WorkQueue���¿����߳̽���ִ������
		Fibonacci f2 = new Fibonacci(n - 2);
		return f2.compute() + f1.join();
	}

}
