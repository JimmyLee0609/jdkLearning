package stream;

import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class LongSummaryStatisticsTest {

	public static void main(String[] args) {
		// ��double��һ�����÷�
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
		// ���Խ���int �� long ���� consumer
		IntConsumer andThen2 = statistics.andThen(intSummaryStatistics);

	}

}
