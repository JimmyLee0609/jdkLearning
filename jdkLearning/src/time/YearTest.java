package time;

import static java.time.temporal.ChronoField.YEAR;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;

public class YearTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Year now = Year.now();// 2017
		Year now2 = Year.now(Clock.systemDefaultZone());// 2017
		Year now3 = Year.now(ZoneId.systemDefault());// 2017
		Year of = Year.of(3);// 3
		boolean leap = Year.isLeap(50);// �Ƿ����� false
		Year from = Year.from(now3);// LocaleDate.from() 2017

		minus();
		plus();
		with();

		ValueRange range = now.range(ChronoField.YEAR);// ��ȡ��λ֧�ֵķ�Χ -999999999 -
														// 999999999
		LocalDate atDay = now.atDay(50);// ���ڵ������ ָ�������Ǽ��¼��� 2017-02-19
		YearMonth atMonth = now.atMonth(2);// ���������ָ���·� 2017-02
		YearMonth atMonth2 = now.atMonth(Month.AUGUST);// 2017-08
		LocalDate atMonthDay = now.atMonthDay(MonthDay.now());// �����·���ָ�� ��������
																// 2017-10-11
		int compareTo = now.compareTo(of);// ����һ��Year�Ƚ���� ���� 2014
		int i = now.get(ChronoField.YEAR);// ���ݵ�λ��ȡ 2017
		long long1 = now.getLong(ChronoField.YEAR);// ���ݵ�λ��ȡ 2017
		int value = now.getValue();// ��ȡֵ 2017
		boolean after = now.isAfter(of);// �봫���Year �Ƚ��ں��� true
		boolean before = now.isBefore(of);// �봫���Year �Ƚ���ǰ�� false
		boolean leap2 = now.isLeap();// �Ƿ�������false

		ChronoField[] values = ChronoField.values();
		for (ChronoField chronoField : values) {
			boolean supported = now.isSupported(chronoField);
			boolean supported2 = atMonth2.isSupported(chronoField);
			// ֧�ֵ��ֶ� YearOfEra Year Era
			System.out.println(chronoField + "-�Ƿ�֧��-" + supported);
			// YearMonth ֧�� YearOfEra Year Era ProlepticMonth MonthOfYear
			System.out.println(chronoField + "-�Ƿ�֧��-" + supported2);

		}
		ChronoUnit[] values2 = ChronoUnit.values();
		for (ChronoUnit chronoUnit : values2) {
			boolean supported = now.isSupported(chronoUnit);
			boolean supported2 = atMonth2.isSupported(chronoUnit);
			// ֧�ֵĵ�λ Years Decades Centuries Millennia Eras
			System.out.println(chronoUnit + "=�Ƿ�֧��=" + supported);
			// YearMonth ֧�� Months Years Decades Centuries Millennia Eras
			System.out.println(chronoUnit + "=�Ƿ�֧��=" + supported2);
		}

		boolean validMonthDay = now.isValidMonthDay(MonthDay.of(2, 28));// ����������Ƿ���Чtrue
		int length = now.length();// ��ĳ��� 365
		ValueRange range2 = now.range(ChronoField.YEAR);// ����֧�ֵ��ֶλ�ȡ����
														// -999999999 -
														// 999999999

		Object query = now.query((TemporalAccessor ta) -> {
			// ta����now������������ǲ�ѯ�����������Ϣ
			int j = ta.get(ChronoField.YEAR);
			return null;
		});

		// ��ǰ����ָ�������ڻ��ж೤��λ�ļ��
		long until = now.until(LocalDate.of(2018, 2, 3), ChronoUnit.YEARS);// 1
		long until2 = now.until(LocalDateTime.of(2019, 2, 3, 8, 23), ChronoUnit.YEARS);// 2
		long until3 = now.until(Year.of(5), ChronoUnit.YEARS);// -2012
		long until4 = now.until(YearMonth.of(2015, 5), ChronoUnit.YEARS);// -2

		// ����������ڸ���Year������
		Temporal adjustInto = now.adjustInto(LocalDate.of(2018, 2, 3));// temporal.with(YEAR,
																		// year);

		String string = now.toString();
		// now.format(formatter)
		// Year.parse(text)
		// Year.parse(text, formatter)

	}

	@SuppressWarnings("unused")
	private static void with() {
		Year now = Year.now();// 2017
		// ����
		Year with = now.with(ChronoField.YEAR, 5);// 5
		Year with2 = now.with(Year.of(4));// 4
		Year with3 = now.with(with2);// 4 ����ֻ�ܴ�Year���� return (Year)
										// adjuster.adjustInto(this);
		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void plus() {
		Year now = Year.now();// 2017
		// ���
		Year plusYears = now.plusYears(12);// 2029
		Year plus = now.plus(9, ChronoUnit.YEARS);// 2026
		Year plus2 = now.plus(Period.ofYears(6));// 2023

		int bb = 0;
	}

	@SuppressWarnings("unused")
	private static void minus() {
		Year now = Year.now();// 2017
		// ���
		Year minusYears = now.minusYears(5);// 2012
		Year minus = now.minus(1, ChronoUnit.YEARS);// 2016
		Year minus3 = now.minus(Period.ofYears(7));// 2010
		int c = 0;
	}

}
