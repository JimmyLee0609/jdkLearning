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
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQueries;
import java.time.temporal.ValueRange;

public class LocalDateTimeTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 类方法
		LocalDateTime now = LocalDateTime.now();// 2017-10-13T18:15:11.996
		LocalDateTime now2 = LocalDateTime.now(Clock.systemDefaultZone());// 2017-10-13T18:15:12.012
		LocalDateTime now3 = LocalDateTime.now(ZoneId.of("GMT+12"));// 2017-10-13T22:15:12.012
		LocalDateTime of = LocalDateTime.of(LocalDate.of(2013, 2, 3), LocalTime.of(3, 2));// 2013-02-03T03:02
		LocalDateTime of2 = LocalDateTime.of(2017, 10, 13, 20, 20, 20, 20);// 2017-10-13T20:20:20.000000020
		LocalDateTime ofEpochSecond = LocalDateTime.ofEpochSecond(145757l, 1543, ZoneOffset.ofHoursMinutes(3, 2));// 1970-01-02T19:31:17.000001543
		LocalDateTime ofInstant = LocalDateTime.ofInstant(Instant.now(Clock.tickMinutes(ZoneId.of("GMT+10"))),
				ZoneId.of("GMT+11"));// 2017-10-13T21:15
		// 只能是LocalDateTime
		LocalDateTime from = LocalDateTime.from(LocalDateTime.now());// 2017-10-13T18:15:12.012

		ChronoUnit[] values = ChronoUnit.values();
		for (ChronoUnit chronoUnit : values) {
			boolean supported = now.isSupported(chronoUnit);
			// 全部支持
			System.out.println(chronoUnit + "-是否支持-" + supported);
		}
		ChronoField[] values2 = ChronoField.values();
		for (ChronoField chronoField : values2) {
			boolean supported = now.isSupported(chronoField);
			// 不支持InstantSeconds OffsetSeconds其他都支持
			System.out.println(chronoField + "=是否支持=" + supported);
		}

		plus();
		minus();
		with();
		// 获取存储的值
		Chronology chronology = now.getChronology();// ISO
		int dayOfMonth = now.getDayOfMonth();// 13
		DayOfWeek dayOfWeek = now.getDayOfWeek();// FRIDAY
		int dayOfYear = now.getDayOfYear();// 286
		Month month = now.getMonth();// OCTOBER
		int monthValue = now.getMonthValue();// 10
		int year = now.getYear();// 2017
		int hour = now.getHour();// 18
		int minute = now.getMinute();// 15
		int nano = now.getNano();// 996000000
		int second = now.getSecond();// 11
		int i = now.get(ChronoField.HOUR_OF_DAY);// 18
		long long1 = now.getLong(ChronoField.SECOND_OF_MINUTE);// 11
		// 转换
		LocalDate localDate = now.toLocalDate();// 2017-10-13
		LocalTime localTime = now.toLocalTime();// 18:15:11.996
		// 保留位数
		LocalDateTime truncatedTo = now.truncatedTo(ChronoUnit.HOURS);// 2017-10-13T18:00
		// 将时间和时区合并起来建新对象
		OffsetDateTime atOffset = now.atOffset(ZoneOffset.of("+04:00"));// 2017-10-13T18:15:11.996+04:00
		ZonedDateTime atZone = now.atZone(ZoneId.of("GMT+03"));// 2017-10-13T18:15:11.996+03:00[GMT+03:00]

		// 在之前之后
		boolean after = now.isAfter(of2);// false
		boolean before = now.isBefore(of2);// true

		ValueRange range = now.range(ChronoField.MINUTE_OF_HOUR);// 0 - 59
		Instant instant = now.toInstant(ZoneOffset.of("+08:00"));// 2017-10-13T10:15:11.996Z
		long epochSecond = now.toEpochSecond(ZoneOffset.ofHours(10));// 1507882511
		int compareTo = now.compareTo(of2);// -1

		LocalDate query = now.query(TemporalQueries.localDate());// 2017-10-13
		// 到传入的时间日期是按照单位算多长
		long until = now.until(LocalDateTime.of(2012, 3, 4, 5, 3, 5), ChronoUnit.MONTHS);// -67
		// 传入的对象按照本对象进行修改
		Temporal adjustInto = now.adjustInto(LocalDateTime.of(2017, 2, 3, 5, 3, 5));// 2017-10-13T18:36:44.068
		String string = now.toString();// 2017-10-13T18:15:11.996

		// now.format(formatter)
		// LocalDateTime.parse(text)
		// LocalDateTime.parse(text, formatter)

	}

	@SuppressWarnings("unused")
	private static void with() {
		// 根据对象原来的值 和传进来的值 合起来 新建对象
		LocalDateTime now = LocalDateTime.now();// 2017-10-13T18:12:50.435
		LocalDateTime withYear = now.withYear(3);// 0003-10-13T18:12:50.435
		LocalDateTime withMonth = now.withMonth(5);// 2017-05-13T18:12:50.435
		LocalDateTime withDayOfMonth = now.withDayOfMonth(23);// 2017-10-23T18:12:50.435
		LocalDateTime withDayOfYear = now.withDayOfYear(235);// 2017-08-23T18:12:50.435
		LocalDateTime withHour = now.withHour(13);// 2017-10-13T13:12:50.435
		LocalDateTime withMinute = now.withMinute(23);// 2017-10-13T18:23:50.435
		LocalDateTime withSecond = now.withSecond(52);// 2017-10-13T18:12:52.435
		LocalDateTime withNano = now.withNano(325);// 2017-10-13T18:12:50.000000325
		LocalDateTime with = now.with(LocalTime.of(5, 3, 2, 3));// 2017-10-13T05:03:02.000000003
		LocalDateTime with2 = now.with(ChronoField.HOUR_OF_DAY, 7);// 2017-10-13T07:12:50.435
		LocalDateTime with3 = now.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));// 2017-10-02T18:52:37.226
		int b = 0;
	}

	@SuppressWarnings("unused")
	private static void minus() {
		// 根据传入的值按照单位将对象对应的值进行处理
		LocalDateTime now = LocalDateTime.now();// 2017-10-13T18:10:54.122
		LocalDateTime minusDays = now.minusDays(23);// 2017-09-20T18:10:54.122
		LocalDateTime minusHours = now.minusHours(23);// 2017-10-12T19:10:54.122
		LocalDateTime minusMinutes = now.minusMinutes(55);// 2017-10-13T17:15:54.122
		LocalDateTime minusMonths = now.minusMonths(5);// 2017-05-13T18:10:54.122
		LocalDateTime minusSeconds = now.minusSeconds(43);// 2017-10-13T18:10:11.122
		LocalDateTime minusWeeks = now.minusWeeks(7);// 2017-08-25T18:10:54.122
		LocalDateTime minusYears = now.minusYears(5);// 2012-10-13T18:10:54.122
		LocalDateTime minusNanos = now.minusNanos(537);// 2017-10-13T18:10:54.121999463
		LocalDateTime minus = now.minus(53, ChronoUnit.MINUTES);// 2017-10-13T17:17:54.122
		LocalDateTime minus2 = now.minus(Duration.ofHours(23));// 2017-10-12T19:10:54.122
		LocalDateTime minus3 = now.minus(Period.ofDays(5));// 2017-10-08T18:10:54.122
		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void plus() {
		// 根据传入的值，按照单位将对象进行处理
		LocalDateTime now = LocalDateTime.now();// 2017-10-13T18:09:06.618
		LocalDateTime plusDays = now.plusDays(5);// 2017-10-18T18:09:06.618
		LocalDateTime plusHours = now.plusHours(23);// 2017-10-14T17:09:06.618
		LocalDateTime plusMinutes = now.plusMinutes(25);// 2017-10-13T18:34:06.618
		LocalDateTime plusMonths = now.plusMonths(1);// 2017-11-13T18:09:06.618
		LocalDateTime plusNanos = now.plusNanos(23);// 2017-10-13T18:09:06.618000023
		LocalDateTime plusSeconds = now.plusSeconds(41);// 2017-10-13T18:09:47.618
		LocalDateTime plusWeeks = now.plusWeeks(5);// 2017-11-17T18:09:06.618
		LocalDateTime plusYears = now.plusYears(1);// 2018-10-13T18:09:06.618
		LocalDateTime plus = now.plus(Duration.ofDays(5));// 2017-10-18T18:09:06.618
		LocalDateTime plus2 = now.plus(Period.ofDays(5));// 2017-10-18T18:09:06.618
		LocalDateTime plus3 = now.plus(5, ChronoUnit.DAYS);// 2017-10-18T18:09:06.618

		int b = 0;
	}

}
