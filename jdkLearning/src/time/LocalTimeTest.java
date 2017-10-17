package time;

import static java.time.temporal.ChronoField.NANO_OF_DAY;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQueries;
import java.time.temporal.ValueRange;

public class LocalTimeTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		LocalTime now = LocalTime.now();// 17:19:49.560
		LocalTime now2 = LocalTime.now(Clock.systemDefaultZone());// 17:19:49.565
		LocalTime now3 = LocalTime.now(ZoneId.of("GMT+8"));// 17:19:49.565
		LocalTime of = LocalTime.of(20, 13);// 20:13
		LocalTime of2 = LocalTime.of(23, 21, 55, 321);// 23:21:55.000000321

		// LocalTime time = temporal.query(TemporalQueries.localTime());
		// if (temporal.isSupported(NANO_OF_DAY)) {
		LocalTime from = LocalTime.from(LocalDateTime.of(2017, 10, 13, 23, 23));// 23:23
		LocalTime from2 = LocalTime.from(LocalTime.of(13, 20, 23));// 13:20:23

		minus();
		plus();
		with();

		ChronoUnit[] values = ChronoUnit.values();
		for (ChronoUnit chronoUnit : values) {
			boolean supported = from.isSupported(chronoUnit);
			// HalfDays Hours Minutes Seconds Millis Micros Nanos
			System.out.println(chronoUnit + "-是否支持-" + supported);
		}
		ChronoField[] values2 = ChronoField.values();
		for (ChronoField chronoField : values2) {
			boolean supported = from.isSupported(chronoField);
			// NanoOfSecond NanoOfDay MicroOfSecond MicroOfDay MilliOfSecond
			// MilliOfDay SecondOfMinute SecondOfDay MinuteOfHour MinuteOfDay
			// HourOfAmPm ClockHourOfAmPm HourOfDay ClockHourOfDay AmPmOfDay
			//
			System.out.println(chronoField + "是否支持" + supported);
		}
		// 将对象的时间作为偏移量 ，将 传入对象进行偏移
		LocalDateTime atDate = now.atDate(LocalDate.of(2016, 12, 3));// 2016-12-03T17:19:49.560
		OffsetTime atOffset = now.atOffset(ZoneOffset.of("+10:00"));// 17:19:49.560+08:00

		boolean after = now.isAfter(from2);// true
		boolean before = now.isBefore(from2);// false

		int compareTo = now.compareTo(from2);// 1

		int hour = now.getHour();// 17
		int minute = now.getMinute();// 19
		int nano = now.getNano();// 560000000
		int second = now.getSecond();// 49
		long long1 = now.getLong(ChronoField.HOUR_OF_DAY);// 17
		int i = now.get(ChronoField.SECOND_OF_MINUTE);// 49
		ValueRange range = now.range(ChronoField.MINUTE_OF_HOUR);// 0 - 59

		long nanoOfDay = now.toNanoOfDay();// 62389560000000
		int secondOfDay = now.toSecondOfDay();// 62389

		LocalTime truncatedTo = now.truncatedTo(ChronoUnit.MINUTES);// 17:22

		Object query = now.query((TemporalAccessor ta) -> {
			return null;
		});

		long until = now.until(LocalTime.of(13, 20), ChronoUnit.SECONDS);// -14523
		// LocalTime end =
		// LocalTime.from(endExclusive);只能是LocalTime和LocalDateTime

		Temporal adjustInto = now.adjustInto(LocalDateTime.of(2018, 2, 3, 1, 4));// 2018-02-03T17:22:03.372

		// now.format(formatter)
		// LocalTime.parse(text, formatter)
		// LocalTime.parse(text)

		String string = now.toString();// 17:19:49.560
	}

	@SuppressWarnings("unused")
	private static void with() {
		LocalTime now = LocalTime.now();// 17:15:03.119
		LocalTime withSecond = now.withSecond(23);// 17:15:23.119
		LocalTime withMinute = now.withMinute(55);// 17:55:03.119
		LocalTime withHour = now.withHour(5);// 05:15:03.119
		LocalTime withNano = now.withNano(561);// 17:15:03.000000561
		LocalTime with = now.with(ChronoField.MINUTE_OF_HOUR, 55);// 17:55:03.119
		LocalTime with2 = now.with(LocalTime.of(13, 22));// 13:22
		// (LocalTime) adjuster.adjustInto(this);
		// LocalTime with3 = now.with(Instant.now());

		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void plus() {
		LocalTime now = LocalTime.now();// 17:09:02.240
		LocalTime plusHours = now.plusHours(3);// 20:09:02.240
		LocalTime plusMinutes = now.plusMinutes(51);// 18:00:02.240
		LocalTime plusNanos = now.plusNanos(5641);// 17:09:02.240005641
		LocalTime plusSeconds = now.plusSeconds(156);// 17:11:38.240
		LocalTime plus = now.plus(516, ChronoUnit.MINUTES);// 01:45:02.240
		LocalTime plus2 = now.plus(Duration.ofHours(50));// 19:09:02.240

		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void minus() {
		LocalTime now = LocalTime.now();// 17:08:14.231
		LocalTime minusHours = now.minusHours(2);// 15:08:14.231
		LocalTime minusMinutes = now.minusMinutes(50);// 16:18:14.231
		LocalTime minusSeconds = now.minusSeconds(5000);// 15:44:54.231
		LocalTime minusNanos = now.minusNanos(5161);// 17:08:14.230994839
		LocalTime minus = now.minus(153, ChronoUnit.MINUTES);// 14:35:14.231
		LocalTime minus2 = now.minus(Duration.ofHours(5));// 12:08:14.231
		// LocalTime minus3 = now.minus(Period.ofDays(3));Period不能用
		int b = 0;
	}

}
