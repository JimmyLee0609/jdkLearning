package time;

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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.Comparator;
import java.util.Date;

public class OffsetDateTimeTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 类方法
		// 使用Clock根据当前时间 2017-10-11T10:02:03.699+08:00
		OffsetDateTime now = OffsetDateTime.now();
		// 根据传入Clock的时间 2017-10-11T02:02:03.777Z
		OffsetDateTime now2 = OffsetDateTime.now(Clock.systemUTC());
		// 使用Clock根据传入的时区 2017-10-11T10:02:03.777+08:00
		OffsetDateTime now3 = OffsetDateTime.now(ZoneId.of("GMT+8"));
		// 根据指定的日期时间 和偏移量 2017-10-11T09:30:20.000000013+01:00
		OffsetDateTime of = OffsetDateTime.of(2017, 10, 11, 9, 30, 20, 13, ZoneOffset.ofHours(1));
		// 根据指定的日期时间 和偏移量 2017-10-11T10:02:03.777+08:00
		OffsetDateTime of2 = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("+08:00"));
		// 根据指定的日期时间 和偏移量 2017-10-11T10:02:03.777+08:00
		OffsetDateTime of3 = OffsetDateTime.of(LocalDate.now(), LocalTime.now(), ZoneOffset.of("+08:00"));
		// 根据Instant的时间和偏移量2017-10-11T10:02:03.777+08:00
		OffsetDateTime ofInstant = OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("GMT+8"));
		// 根据传入的OffsetDateTime 2017-10-11T02:02:03.777Z
		OffsetDateTime from = OffsetDateTime.from(now2);// 传入对象？？？

		minus();
		plus();
		with();

		Comparator<OffsetDateTime> timeLineOrder = OffsetDateTime.timeLineOrder();// 比较器?
		int compare = timeLineOrder.compare(of, now);

		// ofInstant 根据保存的时间和 传入的时区信息 新建 对象
		// 2017-10-11T12:27:11.418+10:00[GMT+10:00]
		ZonedDateTime atZoneSameInstant = now.atZoneSameInstant(ZoneId.of("GMT+10"));//
		// 根据保存的时间 和传入的时区信息 新建 对象2017-10-11T10:02:03.699+08:00[GMT+08:00]
		ZonedDateTime atZoneSimilarLocal = now.atZoneSimilarLocal(ZoneId.of("GMT+8"));

		int compareTo = now.compareTo(of);// 两个OffsetDateTime 比较相差 -1

		int dayOfMonth = now.getDayOfMonth();// 获取当前OffsetDateTime的月份11
		DayOfWeek dayOfWeek = now.getDayOfWeek();// 获取当前OffsetDateTime是周几WEDNESDAY
		int dayOfYear = now.getDayOfYear();// 获取当前OffsetDateTime是年的第几天284
		int minute = now.getMinute();// 根据单位获取 当前是 2
		Month month = now.getMonth();// 根据单位获取 当前是 OCTOBER
		int monthValue = now.getMonthValue();// 根据单位获取 当前是 10
		int nano = now.getNano();// 获取当前的以纳秒计算的值 699000000
		ZoneOffset offset = now.getOffset();// 获取当前的 偏移量 +08:00
		int second = now.getSecond();// 获取现在的秒 3
		int year = now.getYear();// 获取现在的年 2017
		int hour = now.getHour();// 获取当前OffsetDateTime是天的第几个小时 10

		int i = now.get(ChronoField.DAY_OF_YEAR);// 根据单位获取 当前 是284
		long long1 = now.getLong(ChronoField.DAY_OF_MONTH);// 根据单位获取 当前是 11
		boolean after = now.isAfter(of);// 现在是否是传入对象之后 false
		boolean before = now.isBefore(of);// 当前对象是否是传入对象之前true

		ChronoField[] values = ChronoField.values();
		for (ChronoField chronoField : values) {
			boolean supported = now.isSupported(chronoField);// 全部支持
			System.out.println(chronoField + "是否支持" + supported);
		}
		ChronoUnit[] values2 = ChronoUnit.values();
		for (ChronoUnit chronoUnit : values2) {
			boolean supported = now.isSupported(chronoUnit);
			// Forever -support?- false 其他都是true
			System.out.println(chronoUnit + " -support?- " + supported);
		}
		// 转换
		long epochSecond = now.toEpochSecond();// 1507687323
		Instant instant = now.toInstant();// 当前对象转换为 2017-10-11T02:02:03.699Z
		LocalDate localDate = now.toLocalDate();// 当前对象转换为 日期 2017-10-11
		LocalDateTime localDateTime = now.toLocalDateTime();// 当前对象 转换为
															// 日期时间2017-10-11T10:02:03.699
		LocalTime localTime = now.toLocalTime();// 当前对象转换为 当地时间10:02:03.699
		OffsetTime offsetTime = now.toOffsetTime();// 当前对象转换为偏移量 10:02:03.699
		ZonedDateTime zonedDateTime = now.toZonedDateTime();// 当前对象转换为
															// 时区日期时间2017-10-11T10:02:03.699+08:00

		ValueRange range = now.range(ChronoField.SECOND_OF_MINUTE);// 传入单位的范围 0
																	// - 59

		Object query = now.query((TemporalAccessor ta) -> {
			return null;
		});

		// 使用当前对象调整传入的对象，让它的日期，时间，偏移量 都等于当前对象
		Temporal adjustInto = now.adjustInto(of);// ????2017-10-11T11:13:33.170+08:00

		long until = now.until(OffsetDateTime.of(2017, 10, 14, 13, 00, 00, 23, ZoneOffset.of("+08:00")),
				ChronoUnit.DAYS);
		// 结果 1
		Instant instant2 = LocalDateTime.of(2017, 10, 13, 20, 0, 1).toInstant(ZoneOffset.of("+08:00"));
		// 2017,10,13,15,0,1->2017-10-13T07:00:01Z
		// 2017,10,13,20,0,1->2017-10-13T12:00:01Z
		long until2 = now.until(ZonedDateTime.now(Clock.fixed(
				LocalDateTime.of(2017, 10, 13, 15, 0, 1).toInstant(ZoneOffset.of("+08:00")), ZoneId.of("GMT+08"))),
				ChronoUnit.HOURS);// FixedClock[2017-10-13T07:00:01Z,GMT+08:00]
									// 2017-10-13T13:10:41.976+08:00
		// 1
		// OffsetDateTime end = OffsetDateTime.from(endExclusive);
		OffsetDateTime truncatedTo = now.truncatedTo(ChronoUnit.HOURS);
		String string = now.toString();

		// now.format(formatter)
		// OffsetDateTime.parse(text)
		// OffsetDateTime.parse(text, formatter)
	}

	@SuppressWarnings("unused")
	private static void with() {
		// 传入的值不能大于单位的最大限制
		// 根据传入的值修改对应单位的值
		OffsetDateTime now = OffsetDateTime.now();// 2017-10-11T11:50:32.848+08:00
		// 根据单位
		OffsetDateTime withYear = now.withYear(1980);// 1980-10-11T11:50:32.848+08:00
		OffsetDateTime withMonth = now.withMonth(12);// 2017-12-11T11:50:32.848+08:00
		OffsetDateTime withDayOfYear = now.withDayOfYear(325);// 2017-11-21T11:50:32.848+08:00
		OffsetDateTime withDayOfMonth = now.withDayOfMonth(25);// 2017-10-25T11:50:32.848+08:00
		OffsetDateTime withHour = now.withHour(16);// 2017-10-11T16:50:32.848+08:00
		OffsetDateTime withMinute = now.withMinute(20);// 2017-10-11T11:20:32.848+08:00
		OffsetDateTime withSecond = now.withSecond(50);// 2017-10-11T11:50:50.848+08:00
		OffsetDateTime withNano = now.withNano(9000);// 2017-10-11T11:50:32.000009+08:00
		OffsetDateTime withOffsetSameInstant = now.withOffsetSameInstant(ZoneOffset.of("+08:00"));// 2017-10-11T11:50:32.848+08:00
		OffsetDateTime withOffsetSameLocal = now.withOffsetSameLocal(ZoneOffset.of("+10:00"));// 2017-10-11T11:53:04.338+10:00
		OffsetDateTime with = now.with(ChronoField.YEAR, 2017);// 2017-10-11T11:50:32.848+08:00
		// 根据时间日期
		OffsetDateTime with2 = now.with(withHour);// 2017-10-11T16:50:32.848+08:00
		OffsetDateTime with3 = now.with(LocalDateTime.now());// 2017-10-11T11:50:32.848+08:00
		OffsetDateTime with4 = now.with(LocalDate.now());// 2017-10-11T11:50:32.848+08:00
		OffsetDateTime with5 = now.with(Instant.now());// 2017-10-11T11:50:32.848+08:00

		int b = 0;
	}

	@SuppressWarnings("unused")
	private static void plus() {
		// 根据传入的偏移新建一个OffsetDateTime加上偏移
		OffsetDateTime now = OffsetDateTime.now();// 2017-10-11T11:39:54.586+08:00
		// 根据单位
		OffsetDateTime plusDays = now.plusDays(1);// 2017-10-12T11:39:54.586+08:00
		OffsetDateTime plusHours = now.plusHours(15);// 2017-10-12T02:39:54.586+08:00
		OffsetDateTime plusMinutes = now.plusMinutes(20);// 2017-10-11T11:59:54.586+08:00
		OffsetDateTime plusYears = now.plusYears(2);// 2019-10-11T11:39:54.586+08:00
		OffsetDateTime plusWeeks = now.plusWeeks(1);// 2017-10-18T11:39:54.586+08:00
		OffsetDateTime plusSeconds = now.plusSeconds(500);// 2017-10-11T11:48:14.586+08:00
		OffsetDateTime plusNanos = now.plusNanos(8000);// 2017-10-11T11:39:54.586008+08:00
		OffsetDateTime plusMonths = now.plusMonths(3);// 2018-01-11T11:39:54.586+08:00
		OffsetDateTime plus = now.plus(3, ChronoUnit.MONTHS);// 2018-01-11T11:39:54.586+08:00
		// 根据期间
		OffsetDateTime plus2 = now.plus(Duration.ofDays(3));// 2017-10-14T11:39:54.586+08:00
		OffsetDateTime plus3 = now.plus(Period.ofDays(4));// 2017-10-15T11:39:54.586+08:00
		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void minus() {
		// 根据传入的偏移新建一个OffsetDateTime减去偏移
		OffsetDateTime now = OffsetDateTime.now();// 2017-10-11T11:32:49.221+08:00
		// 根据单位
		OffsetDateTime minusYears = now.minusYears(1);// 2016-10-11T11:32:49.221+08:00
		OffsetDateTime minusMonths = now.minusMonths(1);// 2017-09-11T11:32:49.221+08:00
		OffsetDateTime minusDays = now.minusDays(3);// 2017-10-08T11:32:49.221+08:00
		OffsetDateTime minusHours = now.minusHours(4);// 2017-10-11T07:32:49.221+08:00
		OffsetDateTime minusWeeks = now.minusWeeks(1);// 2017-10-04T11:32:49.221+08:00
		OffsetDateTime minusMinutes = now.minusMinutes(300);// 2017-10-11T11:02:49.221+08:00
		OffsetDateTime minusSeconds = now.minusSeconds(45);// 2017-10-11T11:32:04.221+08:00
		OffsetDateTime minusNanos = now.minusNanos(5000);// 2017-10-11T11:32:49.220995+08:00
		OffsetDateTime minus = now.minus(5, ChronoUnit.MONTHS);// 2017-05-11T11:32:49.221+08:00
		// 根据期间
		OffsetDateTime minus2 = now.minus(Duration.ofDays(3));// 2017-10-08T11:32:49.221+08:00
		OffsetDateTime minus3 = now.minus(Period.ofDays(4));// 2017-10-07T11:34:42.203+08:00
		int c = 0;
	}

}
