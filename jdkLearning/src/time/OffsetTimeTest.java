package time;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;

public class OffsetTimeTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 类方法
		OffsetTime now = OffsetTime.now();// 12:17:02.077+08:00
											// now(Clock.systemDefaultZone());
		OffsetTime now2 = OffsetTime.now(Clock.systemUTC());// 04:17:02.077Z
		OffsetTime now3 = OffsetTime.now(ZoneId.of("GMT-8"));// 20:17:02.077-08:00
		OffsetTime of = OffsetTime.of(11, 59, 30, 500, ZoneOffset.ofHours(5));// 11:59:30.000000500+05:00
		OffsetTime of2 = OffsetTime.of(LocalTime.now(), ZoneOffset.ofHours(8));// 12:17:02.077+08:00

		minus();
		plus();
		with();
		// 根据给定的时间日期和对象原来的时区信息创建
		OffsetDateTime atDate = now.atDate(LocalDate.of(2017, 9, 3));// 2017-09-03T12:17:02.077+08:00
		int compareTo = now.compareTo(of);// -1 比较两个OffsetDateTime之间的差 正就是比他大，
											// 负就是比他小
		int hour = now.getHour();// 12
		int minute = now.getMinute();// 17
		int nano = now.getNano();// 77000000
		int second = now.getSecond();// 2
		int i = now.get(ChronoField.HOUR_OF_DAY);// 根据单位获取值12
		long long1 = now.getLong(ChronoField.SECOND_OF_MINUTE);// 根据单位获取值2
		boolean after = now.isAfter(of);// 判断对象是否在传入对象之后false
		boolean before = now.isBefore(of);// 判断对象是否在传入对象之前true
		boolean equal = now.isEqual(of2);// 判断两个对象是否相等true

		ChronoField[] values = ChronoField.values();
		for (ChronoField chronoField : values) {
			boolean supported = now.isSupported(chronoField);
			// 支持的字段 天以内的单位
			// OffsetSeconds NanoOfSecond NanoOfDay MicroOfSecond
			// MicroOfDay MilliOfSecond MilliOfDay SecondOfMinute
			// SecondOfDay MinuteOfHour MinuteOfDay HourOfAmPm
			// ClockHourOfAmPm HourOfDay ClockHourOfDay AmPmOfDay
			System.out.println(chronoField + "-是否支持-" + supported);
		}
		ChronoUnit[] values2 = ChronoUnit.values();
		for (ChronoUnit chronoUnit : values2) {
			boolean supported = now.isSupported(chronoUnit);
			// Nanos Micros Millis Seconds Minutes Hours HalfDays 支持的单位
			System.out.println(chronoUnit + "=是否支持=" + supported);
		}

		ValueRange range = now.range(ChronoField.MINUTE_OF_HOUR);// 指定单位的范围0 -
																	// 59
		OffsetTime truncatedTo = now.truncatedTo(ChronoUnit.SECONDS);// 按照传入的单位保留，小于单位的去除12:17:02+08:00
		LocalTime localTime = now.toLocalTime();// 获取对象的LocalTime部分信息12:17:02.077
		ZoneOffset offset = now.getOffset();// 获取对象的时间与UTC时间的偏移量+08:00

		Object query = now.query((TemporalAccessor ta) -> {
			return null;
		});

		// 比较两个OffsetTime的插值，单位按照传入的单位
		long until = now.until(of, ChronoUnit.HOURS);// 1

		String string = now.toString();// 12:17:02.077+08:00
		Temporal adjustInto = now.adjustInto(now);// 需要调整的时间
													// 结果13:31:17.964+08:00
		// now.format(formatter)
		// OffsetTime.parse(text)
		// OffsetTime.parse(text, formatter)
	}

	@SuppressWarnings("unused")
	private static void with() {
		// 根据传入的值改变对象的对应单位的值
		OffsetTime now = OffsetTime.now();// 12:36:46.035+08:00
		OffsetTime withHour = now.withHour(5);// 05:36:46.035+08:00
		OffsetTime withMinute = now.withMinute(45);// 12:45:46.035+08:00
		OffsetTime withSecond = now.withSecond(32);// 12:36:32.035+08:00
		OffsetTime withNano = now.withNano(8000);// 12:36:46.000008+08:00
		OffsetTime withOffsetSameLocal = now.withOffsetSameLocal(ZoneOffset.of("+08:00"));// 12:36:46.035+08:00
		OffsetTime withOffsetSameInstant = now.withOffsetSameInstant(ZoneOffset.of("+10:00"));// 14:36:46.035+10:00
		OffsetTime with = now.with(ChronoField.MINUTE_OF_HOUR, 25);// 12:25:46.035+08:00
		OffsetTime with2 = now.with(withHour);// 05:36:46.035+08:00

		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void plus() {
		OffsetTime now = OffsetTime.now();// 12:32:53.943+08:00
		OffsetTime plusSeconds = now.plusSeconds(500);// 12:41:13.943+08:00
		OffsetTime plusMinutes = now.plusMinutes(80);// 13:52:53.943+08:00
		OffsetTime plusHours = now.plusHours(3);// 15:32:53.943+08:00
		OffsetTime plusNanos = now.plusNanos(8000);// 12:32:53.943008+08:00
		OffsetTime plus = now.plus(5, ChronoUnit.HOURS);// 17:32:53.943+08:00
		OffsetTime plus2 = now.plus(Duration.ofSeconds(50));// 12:33:43.943+08:00
		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void minus() {
		OffsetTime now = OffsetTime.now();// 12:29:05.395+08:00
		OffsetTime minusSeconds = now.minusSeconds(20);// 12:28:45.395+08:00
		OffsetTime minusHours = now.minusHours(3);// 09:29:05.395+08:00
		OffsetTime minusMinutes = now.minusMinutes(25);// 12:04:05.395+08:00
		OffsetTime minusNanos = now.minusNanos(5000);// 12:29:05.394995+08:00
		OffsetTime minus = now.minus(50, ChronoUnit.MINUTES);// 11:39:05.395+08:00
		OffsetTime minus2 = now.minus(Duration.ofDays(5));// 12:29:05.395+08:00
		int b = 0;
	}

}
