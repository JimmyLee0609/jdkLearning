package time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.Locale;

public class MonthTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Month of = Month.of(1);// ���ݴ������ִ���ö�� ��1��ʼ JANUARY
		Month from = Month.from(of);// ����Monthö�� �����µ�ö�� JANUARY
		Month[] values = Month.values();// ö�ٵ�����ֵ [JANUARY, FEBRUARY, MARCH,
										// APRIL, MAY, JUNE, JULY, AUGUST,
										// SEPTEMBER, OCTOBER, NOVEMBER,
										// DECEMBER]
		Month valueOf = Month.valueOf("APRIL");// ���ݴ�����ַ�������ö�� APRIL
		Month valueOf2 = Month.valueOf(Month.class, "APRIL");// ����ö�����ͣ����ַ���������ö��
																// APRIL

		int compareTo = of.compareTo(valueOf);// �봫����·ݱȽ� ��� -3
		int firstDayOfYear = of.firstDayOfYear(false);// ö���µĵ�һ����һ��ĵڼ��� 1
														// �������Ƿ�����
		Month firstMonthOfQuarter = of.firstMonthOfQuarter();// �������ڼ��ȵĵ�һ���µ�����
																// JANUARY

		Class<Month> declaringClass = of.getDeclaringClass();// class
																// java.time.Month
		String displayName = of.getDisplayName(TextStyle.FULL, Locale.CHINA);// һ��

		// ��λֻ����ChronoField.MONTH_OF_YEAR
		int i = of.get(ChronoField.MONTH_OF_YEAR);// 1
		long long1 = of.getLong(ChronoField.MONTH_OF_YEAR);// 2
		ValueRange range = of.range(ChronoField.MONTH_OF_YEAR);// 1-12
		ChronoField[] values2 = ChronoField.values();
		for (ChronoField chronoField : values2) {
			boolean supported = of.isSupported(chronoField);
			// MonthOfYear- ֧���� - true
			System.out.println(chronoField + "- ֧���� - " + supported);
		}

		int value = of.getValue();// ��ȡö�ٶ�Ӧ�����־��Ƕ�Ӧ�������·�1
		boolean supported = of.isSupported(ChronoField.SECOND_OF_MINUTE);// �Ƿ�֧��ָ��������false
		int length = of.length(false);// ���µ����� 31 �������Ƿ�����
		int maxLength = of.maxLength();// ���µ�������� 31
		int minLength = of.minLength();// ���µ���С���� 31
		String name = of.name();// ö�ٵ����� JANUARY
		Month minus = of.minus(3);// ��3�����Ǽ��� OCTOBER
		Month plus = of.plus(5);// ��5�����Ǽ��� JUNE
		int ordinal = of.ordinal();// ö�ٵ����� 0

		Month of2 = Month.of(5);
		Temporal adjustInto = of2.adjustInto(LocalDate.now());// �����·� ��������� ����
																// 2017-05-11

		String string = of.toString();// JANUARY
		// δ֪�÷�����������������������
		Object query = of.query((TemporalAccessor a) -> {
			return null;// JANUARY
		});
		int b = 0;
	}

}
