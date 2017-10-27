package stream;

import java.util.DoubleSummaryStatistics;
import java.util.function.DoubleConsumer;

public class DoubleSummaryStatisticsTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// �����һ���streamһ��ʹ��
		/*
		 * ���� DoubleSummaryStatistics stats =
		 * doubleStream.collect(DoubleSummaryStatistics::new,
		 * DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
		 */

		DoubleSummaryStatistics statistics = new DoubleSummaryStatistics();
		DoubleSummaryStatistics statistics2 = new DoubleSummaryStatistics();
		statistics2.accept(2.0); // ����һ��ֵ
		statistics.accept(5.0); // ����һ��ֵ
		statistics.accept(0x30);
		double average = statistics.getAverage(); // ��ȡ���յ�ֵ��ƽ��ֵ
		long count = statistics.getCount(); // ��ȡ���յ�����
		double max = statistics.getMax(); // ��ȡ���յ�����ֵ
		double min = statistics.getMin(); // ��ȡ���յ���С��ֵ
		double sum = statistics.getSum(); // ��ȡ����ֵ�ĺ�

		// ��������ļ��������������ֵ����Сֵȡ����֮�� ���ܺ�
		statistics.combine(statistics2);
		// ����һ����ϵ�DoubleConsumer������ִ�д˲���
		DoubleConsumer andThen = statistics.andThen(statistics2);
		DoubleConsumer andThen2 = andThen.andThen(statistics2);
		andThen2.accept(8.0);
		String string = statistics.toString();
	}

}
