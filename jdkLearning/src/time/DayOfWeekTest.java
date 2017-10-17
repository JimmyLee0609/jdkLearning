package time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.Locale;

public class DayOfWeekTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// �෽�� ��ȡʵ������
		DayOfWeek friday = DayOfWeek.FRIDAY;// ֱ����ö������ FRIDAY
		DayOfWeek of = DayOfWeek.of(5);// ����ö�ٵ�λ��-1��ȡ ��һ���� 1 FRIDAY
		DayOfWeek valueOf = DayOfWeek.valueOf("FRIDAY");// ����ö�����͵����ֻ�ȡ FRIDAY
		DayOfWeek valueOf2 = DayOfWeek.valueOf(DayOfWeek.class, "FRIDAY");// ����ָ��ö�����͵����ֻ�ȡ
																			// FRIDAY
		DayOfWeek from = DayOfWeek.from(of);// FRIDAY
		DayOfWeek[] values = DayOfWeek.values();// [MONDAY, TUESDAY, WEDNESDAY,
												// THURSDAY, FRIDAY, SATURDAY,
												// SUNDAY]
		// ʵ������
		int compareTo = friday.compareTo(of);// �����Ƚ���� 0
		int i = friday.get(ChronoField.DAY_OF_WEEK);// ���ݵ�λ��ȡֵ 5
		long long1 = friday.getLong(ChronoField.DAY_OF_WEEK);// ���ݵ�λ��ȡֵ 5
		Class<DayOfWeek> declaringClass = friday.getDeclaringClass();// ��ȡ��������
																		// class
																		// java.time.DayOfWeek
		String displayName = friday.getDisplayName(TextStyle.FULL, Locale.CHINA);// �����������Ի�ȡ��ʾ�ĸ�ʽ
																					// ������
		int value = friday.getValue();// ��ȡֵ 5
		// ֧����Щ��λ
		ChronoField[] values2 = ChronoField.values();
		for (ChronoField chronoField : values2) {
			// DayOfWeek- �Ƿ�֧�� - true
			boolean supported = friday.isSupported(chronoField);
			System.out.println(chronoField + "- �Ƿ�֧�� -  " + supported);
		}
		DayOfWeek minus = friday.minus(3);// Ŀǰö�ٵ�ֵ�����������ת����ö���� TUESDAY
		DayOfWeek plus = friday.plus(2);// Ŀǰö�ٵ�ֵ���ϴ��������ת����ö���� SUNDAY
		String name = friday.name();// ö�ٵ������� FRIDAY
		int ordinal = friday.ordinal();// ö�ٵ������ 4
		ValueRange range = friday.range(ChronoField.DAY_OF_WEEK);// ö�ٵķ�Χ�� 1 - 7
		// ���ǽ���������ڰ��ձ����Ե������������ܼ������ڱ�����壬�Ǽ���
		Temporal adjustInto = friday.adjustInto(LocalDate.now());// �������ڵ������������
																	// 2017-10-13

		String string = friday.toString();// FRIDAY

		// �÷�����
		Object query = friday.query((TemporalAccessor b) -> {
			return null;
		});

	}

}
