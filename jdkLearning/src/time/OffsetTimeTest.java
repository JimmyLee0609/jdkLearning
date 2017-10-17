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
		// �෽��
		OffsetTime now = OffsetTime.now();// 12:17:02.077+08:00
											// now(Clock.systemDefaultZone());
		OffsetTime now2 = OffsetTime.now(Clock.systemUTC());// 04:17:02.077Z
		OffsetTime now3 = OffsetTime.now(ZoneId.of("GMT-8"));// 20:17:02.077-08:00
		OffsetTime of = OffsetTime.of(11, 59, 30, 500, ZoneOffset.ofHours(5));// 11:59:30.000000500+05:00
		OffsetTime of2 = OffsetTime.of(LocalTime.now(), ZoneOffset.ofHours(8));// 12:17:02.077+08:00

		minus();
		plus();
		with();
		// ���ݸ�����ʱ�����ںͶ���ԭ����ʱ����Ϣ����
		OffsetDateTime atDate = now.atDate(LocalDate.of(2017, 9, 3));// 2017-09-03T12:17:02.077+08:00
		int compareTo = now.compareTo(of);// -1 �Ƚ�����OffsetDateTime֮��Ĳ� �����Ǳ�����
											// �����Ǳ���С
		int hour = now.getHour();// 12
		int minute = now.getMinute();// 17
		int nano = now.getNano();// 77000000
		int second = now.getSecond();// 2
		int i = now.get(ChronoField.HOUR_OF_DAY);// ���ݵ�λ��ȡֵ12
		long long1 = now.getLong(ChronoField.SECOND_OF_MINUTE);// ���ݵ�λ��ȡֵ2
		boolean after = now.isAfter(of);// �ж϶����Ƿ��ڴ������֮��false
		boolean before = now.isBefore(of);// �ж϶����Ƿ��ڴ������֮ǰtrue
		boolean equal = now.isEqual(of2);// �ж����������Ƿ����true

		ChronoField[] values = ChronoField.values();
		for (ChronoField chronoField : values) {
			boolean supported = now.isSupported(chronoField);
			// ֧�ֵ��ֶ� �����ڵĵ�λ
			// OffsetSeconds NanoOfSecond NanoOfDay MicroOfSecond
			// MicroOfDay MilliOfSecond MilliOfDay SecondOfMinute
			// SecondOfDay MinuteOfHour MinuteOfDay HourOfAmPm
			// ClockHourOfAmPm HourOfDay ClockHourOfDay AmPmOfDay
			System.out.println(chronoField + "-�Ƿ�֧��-" + supported);
		}
		ChronoUnit[] values2 = ChronoUnit.values();
		for (ChronoUnit chronoUnit : values2) {
			boolean supported = now.isSupported(chronoUnit);
			// Nanos Micros Millis Seconds Minutes Hours HalfDays ֧�ֵĵ�λ
			System.out.println(chronoUnit + "=�Ƿ�֧��=" + supported);
		}

		ValueRange range = now.range(ChronoField.MINUTE_OF_HOUR);// ָ����λ�ķ�Χ0 -
																	// 59
		OffsetTime truncatedTo = now.truncatedTo(ChronoUnit.SECONDS);// ���մ���ĵ�λ������С�ڵ�λ��ȥ��12:17:02+08:00
		LocalTime localTime = now.toLocalTime();// ��ȡ�����LocalTime������Ϣ12:17:02.077
		ZoneOffset offset = now.getOffset();// ��ȡ�����ʱ����UTCʱ���ƫ����+08:00

		Object query = now.query((TemporalAccessor ta) -> {
			return null;
		});

		// �Ƚ�����OffsetTime�Ĳ�ֵ����λ���մ���ĵ�λ
		long until = now.until(of, ChronoUnit.HOURS);// 1

		String string = now.toString();// 12:17:02.077+08:00
		Temporal adjustInto = now.adjustInto(now);// ��Ҫ������ʱ��
													// ���13:31:17.964+08:00
		// now.format(formatter)
		// OffsetTime.parse(text)
		// OffsetTime.parse(text, formatter)
	}

	@SuppressWarnings("unused")
	private static void with() {
		// ���ݴ����ֵ�ı����Ķ�Ӧ��λ��ֵ
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
