package time;

import static java.time.temporal.ChronoField.EPOCH_DAY;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoChronology;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQueries;
import java.time.temporal.ValueRange;

public class LocalDateTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 类方法
		LocalDate now = LocalDate.now();// 2017-10-13
		LocalDate now2 = LocalDate.now(Clock.systemDefaultZone());// 2017-10-13
		LocalDate now3 = LocalDate.now(ZoneId.of("GMT+8"));// 2017-10-13
		LocalDate of = LocalDate.of(2017, 11, 13);// 2017-11-13
		LocalDate ofEpochDay = LocalDate.ofEpochDay(13);// 1970-01-14
		LocalDate ofYearDay = LocalDate.ofYearDay(2016, 325);// 2016-11-20

		LocalDate from = LocalDate.from(LocalDate.of(2019, 12, 13));// 2019-12-13
		LocalDate from2 = LocalDate.from(LocalDateTime.of(2023, 2, 5, 13, 56));// 2023-02-05
		// 就是LocalDate date = temporal.query(TemporalQueries.localDate());

		long epochDay = now.toEpochDay();// 17452
		// 实例方法
		// 将传入对象的日期修改成本对象的日期 temporal.with(EPOCH_DAY, toEpochDay());
		Temporal adjustInto = now.adjustInto(LocalDateTime.of(2017, 2, 1, 23, 15));// 2017-10-13T23:15
		Temporal adjustInto2 = now.adjustInto(MinguoDate.of(53, 12, 3));// Minguo
																		// ROC
																		// 106-10-13
		// 就是调用temporal.with(EPOCH_DAY, toEpochDay());
		minus();
		plus();
		with();
		// 按照对象的值和传入的信息，重新组建新对象
		LocalDateTime atStartOfDay = now.atStartOfDay();// 2017-10-13T00:00
		ZonedDateTime atStartOfDay2 = now.atStartOfDay(ZoneId.of("GMT+12"));// 2017-10-13T00:00+12:00[GMT+12:00]
		LocalDateTime atTime = now.atTime(LocalTime.now());// 2017-10-13T15:00:27.408
		OffsetDateTime atTime2 = now.atTime(OffsetTime.now(ZoneId.of("GMT+8")));// 2017-10-13T15:00:27.408+08:00
		LocalDateTime atTime3 = now.atTime(5, 1);// 2017-10-13T05:01
		LocalDateTime atTime4 = now.atTime(2, 12, 50);// 2017-10-13T02:12:50
		LocalDateTime atTime5 = now.atTime(12, 13, 12, 156);// 2017-10-13T12:13:12.000000156
		// 比较两个ChronoLocalDate 之间的日期差值
		int compareTo = now.compareTo(from2);// -6

		int i = now.get(ChronoField.DAY_OF_MONTH);// 13
		IsoChronology chronology = now.getChronology();// ISO
		int dayOfMonth = now.getDayOfMonth();// 13
		DayOfWeek dayOfWeek = now.getDayOfWeek();// FRIDAY
		int dayOfYear = now.getDayOfYear();// 286
		Era era = now.getEra();// CE
		Month month = now.getMonth();// OCTOBER
		int monthValue = now.getMonthValue();// 10
		int year = now.getYear();// 2017
		int lengthOfMonth = now.lengthOfMonth();// 31
		int lengthOfYear = now.lengthOfYear();// 365
		long long1 = now.getLong(ChronoField.DAY_OF_WEEK);// 5

		boolean after = now.isAfter(from2);// false
		boolean before = now.isBefore(from2);// true
		boolean leapYear = now.isLeapYear();// 是否是闰年 false
		boolean equal = now.isEqual(from);// false

		ChronoField[] values = ChronoField.values();
		for (ChronoField chronoField : values) {
			boolean supported = now.isSupported(chronoField);
			// DayOfWeek AlignedDayOfWeekInMonth AlignedDayOfWeekInYear
			// DayOfMonth
			// DayOfYear EpochDay AlignedWeekOfMonth AlignedWeekOfYear
			// MonthOfYear
			// ProlepticMonth YearOfEra Year Era
			System.out.println(chronoField + "-是否支持-" + supported);
		}
		ChronoUnit[] values2 = ChronoUnit.values();
		for (ChronoUnit chronoUnit : values2) {
			boolean supported = now.isSupported(chronoUnit);
			// Days Weeks Months Years Decades Centuries Millennia Eras
			System.out.println(chronoUnit + "=是否支持=" + supported);
		}

		ValueRange range = now.range(ChronoField.DAY_OF_WEEK);// 1 - 7
		long until = now.until(LocalDate.of(2017, 10, 14), ChronoUnit.DAYS);// 1
		Period until2 = now.until(LocalDate.of(2017, 10, 14));// P1D

		// 查询自己的信息
		LocalTime query = now.query(TemporalQueries.localTime());// null
		LocalDate query3 = now.query(TemporalQueries.localDate());// 2017-10-13
		Object query2 = now.query((TemporalAccessor ta) -> {
			return null;
		});// null
		String string = now.toString();
		// now.format();
		// formatter.format(this);
		CharSequence text = "2017-12-03".subSequence(0, 10);
		LocalDate parse = LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
		String string2 = parse.toString();
		// LocalDate.parse(text, formatter)
	}

	@SuppressWarnings("unused")
	private static void with() {
		LocalDate now = LocalDate.now();// 2017-10-13
		LocalDate withYear = now.withYear(2015);// 2015-10-13
		LocalDate withMonth = now.withMonth(12);// 2017-12-13
		LocalDate withDayOfYear = now.withDayOfYear(365);// 2017-12-31
		LocalDate withDayOfMonth = now.withDayOfMonth(12);// 2017-10-12

		LocalDate with = now.with(ChronoField.DAY_OF_MONTH, 23);// 2017-10-23

		// public LocalDate with(TemporalAdjuster adjuster) { (LocalDate)
		// adjuster.adjustInto(this);
		LocalDate with2 = now.with(TemporalAdjusters.firstDayOfMonth());// 2017-10-01
		LocalDate with3 = now.with(LocalDate.of(2016, 2, 3));// 2016-02-03
		LocalDate with4 = now.with(Year.of(2013));// 2013-10-13
		// LocalDate with5 = now.with(Instant.now());

		int b = 0;
	}

	@SuppressWarnings("unused")
	private static void plus() {
		LocalDate now = LocalDate.now();// 2017-10-13
		LocalDate plusDays = now.plusDays(45);// 2017-11-27
		LocalDate plusMonths = now.plusMonths(13);// 2018-11-13
		LocalDate plusWeeks = now.plusWeeks(100);// 2019-09-13
		LocalDate plusYears = now.plusYears(2);// 2019-10-13
		LocalDate plus = now.plus(2, ChronoUnit.DECADES);// 2037-10-13
		// LocalDate plus2 = now.plus(Duration.between(LocalDate.of(3, 2,
		// 4),LocalDate.of(4, 2, 3)));//不能用Duration
		LocalDate plus2 = now.plus(Period.between(LocalDate.of(3, 2, 4), LocalDate.of(4, 2, 3)));// 2018-10-13
		int g = 0;
	}

	@SuppressWarnings("unused")
	private static void minus() {
		LocalDate now = LocalDate.now();// 2017-10-13
		// LocalDate minus = now.minus(Duration.ofDays(3));//以毫秒计算不能用
		LocalDate minus = now.minus(Period.ofDays(3));// 2017-10-10
		LocalDate minusDays = now.minusDays(23);// 2017-09-20
		LocalDate minusMonths = now.minusMonths(5);// 2017-09-20
		LocalDate minusWeeks = now.minusWeeks(7);// 2017-09-20
		LocalDate minusYears = now.minusYears(4);// 2013-10-13
		LocalDate minus2 = now.minus(7, ChronoUnit.MONTHS);// 2017-03-13

		int c = 0;
	}
}
