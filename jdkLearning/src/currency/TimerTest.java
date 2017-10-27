package currency;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

	@SuppressWarnings({ "deprecation", "unused" })
	public static void main(String[] args) {
		// ��ʱ���� �������е�SchedulePool����� ��������������׶�
		Timer timer = new Timer(); // �½�һ����ʱ�� ����ϵͳĬ�� this("Timer-" +
									// serialNumber());
		// Timer timer2 = new Timer("first"); //�½�һ���Զ������Ƶļ�ʱ��
		// Timer timer3 = new Timer(false); //���ݴ���Ĳ���ֵ �½���ʱ���Ƿ��Ǻ�̨�߳� ����ϵͳĬ��
		// this("Timer-" + serialNumber(), isDaemon);
		// Timer second = new Timer("second", true); //�Զ����ʱ�������֣� ���ݲ���ֵȷ���Ƿ��Ǻ�̨�߳�
		// �Զ���һ������ �̳�Runnable
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				System.out.println("Task Run " + new Date((System.currentTimeMillis())));
				// �ƻ������ִ��ʱ����
				long scheduledExecutionTime = scheduledExecutionTime();
				Date date = new Date(scheduledExecutionTime);
				// ����������Ϊcancel״̬,���ó�cancel״̬�ͻ᲻���ٴα�ִ����,�ȴ����Ƴ�����,������һ�ε�ִ�л������������
				// boolean cancel = cancel();
			}
		};
		TimerTask task2 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("Task2 Run " + new Date((System.currentTimeMillis())));
			}
		};
		TimerTask empty = new TimerTask() {
			@Override
			public void run() {
				// System.out.println(System.currentTimeMillis());
				// System.out.println(Thread.currentThread().getName());
			}
		};
		Date date = new Date(1510228770455l);
		System.out.println(date);
		Date date2 = new Date(1507550669895l);
		System.out.println(date2);
		// ������ʱ��ָ���ӳٺ�ִ������
		// timer.schedule(task, 500);
		// ������ʱ��ָ���ӳٺ� ��ʼִ������ ����������ɺ�ָ��������ٴ�ִ��
		// timer.schedule(empty, 500, 1000);

		// ������ָ��ʱ��ִ��ָ������ֻҪʱ�䵽�˾�ִ������
		Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT-8"), Locale.CHINESE);

		instance.setTime(date2);
		instance.set(Calendar.YEAR, 2017);
		instance.set(Calendar.MONTH, 9); // �·ݴ�0��ʼ
		instance.set(Calendar.DAY_OF_MONTH, 9);
		instance.set(Calendar.HOUR_OF_DAY, 20);
		instance.set(Calendar.MINUTE, 25);
		instance.set(Calendar.SECOND, 20);

		timer.schedule(task, instance.getTime());

		// ��ָ����ʱ��ִ��ָ������ ������ִ�к�ָ������ٴ�ִ��
		timer.schedule(task2, instance.getTime(), 5000);

		// ������ʱ��ָ���ӳٺ�ʼִ������ ����ָ��������ٴ�ִ�� ����֮ǰ�������Ƿ����
		timer.scheduleAtFixedRate(task, 50000, 100);
		// ��ָ����ʱ�俪ʼִ������, ����ָ��������ٴ�ִ��, ����֮ǰ�������Ƿ����
		timer.scheduleAtFixedRate(task, instance.getTime(), 100);

		timer.cancel();// ȡ�� ��ʱ������ �����ظ�����,���� �ڶ��κ�֮��ĵ��ö�û��Ч��
		int purge = timer.purge(); // �Ӽ�ʱ����������б����Ƴ�������ȡ��������
	}

}
