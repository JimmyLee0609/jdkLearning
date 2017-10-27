package stream;

import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class LongSummaryStatisticsTest {

	public static void main(String[] args) {
		// 和double的一样的用法
		LongSummaryStatistics statistics = new LongSummaryStatistics();
		statistics.accept(5l);
		statistics.accept(12l);
		double average = statistics.getAverage();
		long count = statistics.getCount();
		long max = statistics.getMax();
		long min = statistics.getMin();
		long sum = statistics.getSum();
		statistics.combine(new LongSummaryStatistics());
		LongSummaryStatistics statistics2 = new LongSummaryStatistics();
		statistics2.accept(15l);
		IntSummaryStatistics intSummaryStatistics = new IntSummaryStatistics();
		LongConsumer andThen = statistics.andThen((LongConsumer) statistics2);
		// 可以接收int 和 long 两种 consumer
		IntConsumer andThen2 = statistics.andThen(intSummaryStatistics);

	}

}
