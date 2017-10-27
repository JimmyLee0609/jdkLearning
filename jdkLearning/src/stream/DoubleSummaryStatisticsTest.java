package stream;

import java.util.DoubleSummaryStatistics;
import java.util.function.DoubleConsumer;

public class DoubleSummaryStatisticsTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 这个类一般和stream一起使用
		/*
		 * 例子 DoubleSummaryStatistics stats =
		 * doubleStream.collect(DoubleSummaryStatistics::new,
		 * DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
		 */

		DoubleSummaryStatistics statistics = new DoubleSummaryStatistics();
		DoubleSummaryStatistics statistics2 = new DoubleSummaryStatistics();
		statistics2.accept(2.0); // 接收一个值
		statistics.accept(5.0); // 接收一个值
		statistics.accept(0x30);
		double average = statistics.getAverage(); // 获取接收的值的平均值
		long count = statistics.getCount(); // 获取接收的数量
		double max = statistics.getMax(); // 获取接收的最大的值
		double min = statistics.getMin(); // 获取接收的最小的值
		double sum = statistics.getSum(); // 获取接收值的和

		// 两个对象的计数加起来，最大值，最小值取两者之大， 简单总和
		statistics.combine(statistics2);
		// 返回一个组合的DoubleConsumer，依次执行此操作
		DoubleConsumer andThen = statistics.andThen(statistics2);
		DoubleConsumer andThen2 = andThen.andThen(statistics2);
		andThen2.accept(8.0);
		String string = statistics.toString();
	}

}
