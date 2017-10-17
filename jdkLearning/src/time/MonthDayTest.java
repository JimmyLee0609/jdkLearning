package time;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;

public class MonthDayTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// �෽��
		MonthDay now = MonthDay.now();// ��ȡĿǰ������--10-11
		MonthDay of = MonthDay.of(9, 5);// ���ݴ��������������Ӧ������--09-05
		MonthDay now2 = MonthDay.now(ZoneId.of("GMT+8"));// ����ʱ������Ŀǰ������--10-11
		MonthDay of2 = MonthDay.of(Month.OCTOBER, 11);// ���ݴ�����·ݺ���
														// ������Ӧ������--10-11
		MonthDay from = MonthDay.from(of2);// ���ݴ����MonthDay��Ϣ ������Ӧ������--10-11
		MonthDay now3 = MonthDay.now(Clock.systemUTC());// ���ݴ����ʱ�Ӵ������� --10-11
		// ʵ������
		Temporal adjustInto = of.adjustInto(LocalDate.now());// ���ݶ����ֵ��ֵ������
																// 2017-09-05
		LocalDate atYear = of.atYear(2018);// Ŀǰ������ָ�����Ǽ���2018-09-05
		int compareTo = of.compareTo(now);// Ŀǰ��MonthDay�봫��ıȽ���� -1
		int i = of.get(ChronoField.DAY_OF_MONTH);// ���ݴ���ĵ�λ��ȡֵ 5
		long long1 = of.getLong(ChronoField.DAY_OF_MONTH);// ���ݴ���ĵ�λ��ȡֵ 5
		int dayOfMonth = of.getDayOfMonth();// ��ȡ���������������Ǽ��� 5
		Month month = of.getMonth();// ��ȡ������·� SEPTEMBER
		int monthValue = of.getMonthValue();// ��ȡ�����·ݵ�ֵ 9
		boolean after = of.isAfter(of2);// �����Ƿ�ȴ������ false
		boolean before = of.isBefore(of2);// �����Ƿ�ȴ������ true
		ChronoField[] values = ChronoField.values();
		for (ChronoField chronoField : values) {
			boolean supported = of.isSupported(chronoField);
			// MonthOfYear-�Ƿ�֧��-true DayOfMonth-�Ƿ�֧��-true
			System.out.println(chronoField + "-�Ƿ�֧��-" + supported);
		}
		boolean validYear = of.isValidYear(2056);// ������ָ�������Ƿ���Ч true
		ValueRange range = of.range(ChronoField.DAY_OF_MONTH);// �����ڵ�λ�ķ�Χ1-30
		ValueRange range2 = of.range(ChronoField.MONTH_OF_YEAR);// �����ڵ�λ�ķ�Χ1-12

		Object query = of.query((TemporalAccessor ta) -> {
			return null;
		});
		MonthDay with = of.with(Month.FEBRUARY);// ʹ��Month�ı䵱ǰ�·�
		MonthDay withMonth = of.withMonth(2);// �ı䵱ǰ�·�
		MonthDay withDayOfMonth = of.withDayOfMonth(23);// �ı䵱ǰ����

		String string = of.toString();
		// of.format(formatter)
		// MonthDay.parse()
		// MonthDay.parse(text, formatter)

	}

}
