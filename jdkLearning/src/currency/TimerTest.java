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
		// 计时器类 并发包有的SchedulePool替代了 不过这个方法简单易懂
		Timer timer = new Timer(); // 新建一个计时器 名称系统默认 this("Timer-" +
									// serialNumber());
		// Timer timer2 = new Timer("first"); //新建一个自定义名称的计时器
		// Timer timer3 = new Timer(false); //根据传入的布尔值 新建计时器是否是后台线程 名字系统默认
		// this("Timer-" + serialNumber(), isDaemon);
		// Timer second = new Timer("second", true); //自定义计时器的名字， 根据布尔值确定是否是后台线程
		// 自定义一个任务， 继承Runnable
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				System.out.println("Task Run " + new Date((System.currentTimeMillis())));
				// 计划任务的执行时间间隔
				long scheduledExecutionTime = scheduledExecutionTime();
				Date date = new Date(scheduledExecutionTime);
				// 将任务设置为cancel状态,设置成cancel状态就会不会再次被执行了,等待被移除队列,但是这一次的执行还会继续到结束
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
		// 在运行时的指定延迟后执行任务
		// timer.schedule(task, 500);
		// 在运行时的指定延迟后 开始执行任务， 并在任务完成后指定间隔后再次执行
		// timer.schedule(empty, 500, 1000);

		// 安排在指定时间执行指定任务，只要时间到了就执行任务
		Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT-8"), Locale.CHINESE);

		instance.setTime(date2);
		instance.set(Calendar.YEAR, 2017);
		instance.set(Calendar.MONTH, 9); // 月份从0开始
		instance.set(Calendar.DAY_OF_MONTH, 9);
		instance.set(Calendar.HOUR_OF_DAY, 20);
		instance.set(Calendar.MINUTE, 25);
		instance.set(Calendar.SECOND, 20);

		timer.schedule(task, instance.getTime());

		// 在指定的时间执行指定任务， 在任务执行后指定间隔再次执行
		timer.schedule(task2, instance.getTime(), 5000);

		// 在运行时的指定延迟后开始执行任务， 并在指定间隔后再次执行 不管之前的任务是否完成
		timer.scheduleAtFixedRate(task, 50000, 100);
		// 在指定的时间开始执行任务, 并在指定间隔后再次执行, 不管之前的任务是否完成
		timer.scheduleAtFixedRate(task, instance.getTime(), 100);

		timer.cancel();// 取消 计时器任务 可以重复调用,但是 第二次和之后的调用都没有效果
		int purge = timer.purge(); // 从计时器的任务队列表中移除所有已取消的任务
	}

}
