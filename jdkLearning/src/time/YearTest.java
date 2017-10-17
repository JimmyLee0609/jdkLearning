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
		boolean leap = Year.isLeap(50);// 是否闰年 false
		Year from = Year.from(now3);// LocaleDate.from() 2017

		minus();
		plus();
		with();

		ValueRange range = now.range(ChronoField.YEAR);// 获取单位支持的范围 -999999999 -
														// 999999999
		LocalDate atDay = now.atDay(50);// 现在的年份中 指定日期是几月几日 2017-02-19
		YearMonth atMonth = now.atMonth(2);// 现在年份中指定月份 2017-02
		YearMonth atMonth2 = now.atMonth(Month.AUGUST);// 2017-08
		LocalDate atMonthDay = now.atMonthDay(MonthDay.now());// 现在月份中指定 月中天数
																// 2017-10-11
		int compareTo = now.compareTo(of);// 与另一个Year比较相差 多少 2014
		int i = now.get(ChronoField.YEAR);// 根据单位获取 2017
		long long1 = now.getLong(ChronoField.YEAR);// 根据单位获取 2017
		int value = now.getValue();// 获取值 2017
		boolean after = now.isAfter(of);// 与传入的Year 比较在后面 true
		boolean before = now.isBefore(of);// 与传入的Year 比较在前面 false
		boolean leap2 = now.isLeap();// 是否是闰年false

		ChronoField[] values = ChronoField.values();
		for (ChronoField chronoField : values) {
			boolean supported = now.isSupported(chronoField);
			boolean supported2 = atMonth2.isSupported(chronoField);
			// 支持的字段 YearOfEra Year Era
			System.out.println(chronoField + "-是否支持-" + supported);
			// YearMonth 支持 YearOfEra Year Era ProlepticMonth MonthOfYear
			System.out.println(chronoField + "-是否支持-" + supported2);

		}
		ChronoUnit[] values2 = ChronoUnit.values();
		for (ChronoUnit chronoUnit : values2) {
			boolean supported = now.isSupported(chronoUnit);
			boolean supported2 = atMonth2.isSupported(chronoUnit);
			// 支持的单位 Years Decades Centuries Millennia Eras
			System.out.println(chronoUnit + "=是否支持=" + supported);
			// YearMonth 支持 Months Years Decades Centuries Millennia Eras
			System.out.println(chronoUnit + "=是否支持=" + supported2);
		}

		boolean validMonthDay = now.isValidMonthDay(MonthDay.of(2, 28));// 传入的日期是否有效true
		int length = now.length();// 年的长度 365
		ValueRange range2 = now.range(ChronoField.YEAR);// 根据支持的字段获取界限
														// -999999999 -
														// 999999999

		Object query = now.query((TemporalAccessor ta) -> {
			// ta就是now对象，这个方法是查询对象里面的信息
			int j = ta.get(ChronoField.YEAR);
			return null;
		});

		// 当前对象到指定的日期还有多长单位的间隔
		long until = now.until(LocalDate.of(2018, 2, 3), ChronoUnit.YEARS);// 1
		long until2 = now.until(LocalDateTime.of(2019, 2, 3, 8, 23), ChronoUnit.YEARS);// 2
		long until3 = now.until(Year.of(5), ChronoUnit.YEARS);// -2012
		long until4 = now.until(YearMonth.of(2015, 5), ChronoUnit.YEARS);// -2

		// 将传入的日期根据Year来调整
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
		// 设置
		Year with = now.with(ChronoField.YEAR, 5);// 5
		Year with2 = now.with(Year.of(4));// 4
		Year with3 = now.with(with2);// 4 这里只能传Year对象 return (Year)
										// adjuster.adjustInto(this);
		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void plus() {
		Year now = Year.now();// 2017
		// 相加
		Year plusYears = now.plusYears(12);// 2029
		Year plus = now.plus(9, ChronoUnit.YEARS);// 2026
		Year plus2 = now.plus(Period.ofYears(6));// 2023

		int bb = 0;
	}

	@SuppressWarnings("unused")
	private static void minus() {
		Year now = Year.now();// 2017
		// 相减
		Year minusYears = now.minusYears(5);// 2012
		Year minus = now.minus(1, ChronoUnit.YEARS);// 2016
		Year minus3 = now.minus(Period.ofYears(7));// 2010
		int c = 0;
	}

}
