package time;

import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.time.temporal.ValueRange;

public class InstantTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Instant 保留 一个从1970年到现在的毫秒值，作为对象的基础
		// 静态方法
		Instant now = Instant.now();// 2017-10-13T03:56:42.796Z
		Instant now2 = Instant.now(Clock.systemDefaultZone());// 2017-10-13T03:56:42.812Z
		Instant ofEpochMilli = Instant.ofEpochMilli(9535);// 根据1970年为基础，以传入的值为偏移，创建新的对象，1970-01-01T00:00:09.535Z
		Instant ofEpochSecond = Instant.ofEpochSecond(78935);// 根据传入的秒值，以1970年的基准建一个新的对象
																// 1970-01-01T21:55:35Z
		Instant ofEpochSecond2 = Instant.ofEpochSecond(5351, 35163);// 根据1970年的基础，以传入的值作为偏移
																	// 1970-01-01T01:29:11.000035163Z

		// public static Instant from(TemporalAccessor temporal) {
		// 接收Instant实例 或者支持temporal.getLong(INSTANT_SECONDS);的实例
		Instant from = Instant.from(OffsetDateTime.now());// 2017-10-13T07:42:50.114Z
		Instant from2 = Instant.from(Instant.now());// 2017-10-13T07:42:50.176Z
		minus();
		adjustInto();
		with();
		plus();

		boolean after = now.isAfter(now2);// false
		boolean before = now.isBefore(now2);// true

		int compareTo = now.compareTo(now2);// 比较秒值的差距，秒值相同就比较nanos的差距
											// 结果-16000000

		ChronoField[] values = ChronoField.values();
		for (ChronoField chronoField : values) {
			boolean supported = now.isSupported(chronoField);
			// NanoOfSecond - MilliOfSecond- InstantSeconds- 是否支持-true
			System.out.println(chronoField + "-是否支持-" + supported);
		}
		ChronoUnit[] values2 = ChronoUnit.values();
		for (ChronoUnit chronoUnit : values2) {
			boolean supported = now.isSupported(chronoUnit);
			// Nanos-Micros-Millis-Seconds-Minutes-Hours-HalfDays-Days
			System.out.println(chronoUnit + "=是否支持=" + supported);
		}
		long epochSecond = now.getEpochSecond();// 获取当前存储的秒值 1507867002
		int nano = now.getNano();// 获取当前存储的nona值 796000000
		int i = now.get(ChronoField.MILLI_OF_SECOND);// 获取当前的毫秒值 796
		long long1 = now.getLong(ChronoField.INSTANT_SECONDS);// 根据单位获取当前时间的值
																// 1507867002

		OffsetDateTime atOffset = now.atOffset(ZoneOffset.of("+10:00"));// 根据对象的时间和传入的时区偏差，新建一个OffsetDateTime对象
																		// 进行偏移2017-10-13T14:07:40.778+10:00
		ZonedDateTime atZone = now.atZone(ZoneId.of("GMT+11"));// 根据现在的时间和传入的时区，新建一个ZonedDateTime对象
																// 2017-10-13T15:07:40.778+11:00[GMT+11:00]
		long epochMilli = now.toEpochMilli();// 获取当前的毫秒值 1507867002796

		Instant truncatedTo = now.truncatedTo(ChronoUnit.DAYS);
		// 根据传入的单位对现在的时间进行精确位数 2017-10-13T00:00:00Z

		ValueRange range = now.range(ChronoField.INSTANT_SECONDS);// 单位的取值范围
																	// -9223372036854775808
																	// -
																	// 9223372036854775807

		// 从对象到传入对象之间的单位时间差 0
		// Instant end = Instant.from(endExclusive);
		long until = now.until(Instant.now(), ChronoUnit.HOURS);

		Object query = now.query((TemporalAccessor ta) -> {
			return null;
		});

		String string = now.toString();// 2017-10-13T03:56:42.796Z
		// Instant.parse(text)
	}

	@SuppressWarnings("unused")
	private static void plus() {
		// plus（TemporalAmount）--> (Instant) amountToAdd.addTo(this)
		// 传入的值支持秒运算的才可以
		Instant now = Instant.now();// 2017-10-13T03:02:52.891Z
		Instant plusMillis = now.plusMillis(2315);// 2017-10-13T03:02:55.206Z
		Instant plusNanos = now.plusNanos(55513);// 2017-10-13T03:02:52.891055513Z
		Instant plusSeconds = now.plusSeconds(156);// 2017-10-13T03:05:28.891Z
		Instant plus = now.plus(55, ChronoUnit.MINUTES);// 2017-10-13T03:57:52.891Z
		Instant plus2 = now.plus(Duration.ofDays(5));// 2017-10-18T03:02:52.891Z
		Instant plus3 = now.plus(Period.ofDays(1));// 2017-10-14T03:02:52.891Z

		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void with() {
		// MILLI_OF_SECOND MICRO_OF_SECOND NANO_OF_SECOND INSTANT_SECONDS
		// with上面的值，会根据新值的大小新建一个Instant，并且将对应的单位上的值进行修正。
		Instant now = Instant.now();// 2017-10-13T02:56:14.185Z
		Instant with = now.with(Instant.now(Clock.systemDefaultZone()));// 2017-10-13T02:56:14.185Z
		Instant with2 = now.with(ChronoField.INSTANT_SECONDS, 45);// 1970-01-01T00:00:45.185Z
		Instant with3 = now.with(ChronoField.MICRO_OF_SECOND, 50000);// 2017-10-13T02:57:24.005Z
		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void minus() {
		Instant now = Instant.now();// 格林威治时间 当地时间需要加8 2017-10-11T10:53:58.766Z
		Instant minusMillis = now.minusMillis(4753);// 2017-10-11T10:53:54.013Z
		Instant minusNanos = now.minusNanos(165);// 2017-10-11T10:53:58.765999835Z
		Instant minusSeconds = now.minusSeconds(516);// 2017-10-11T10:45:22.766Z
		Instant minus = now.minus(16, ChronoUnit.HOURS);// 2017-10-10T18:53:58.766Z
		Instant minus2 = now.minus(Duration.ofHours(2));// 2017-10-11T08:53:58.766Z
		Instant minus3 = now.minus(Period.ofDays(1));// 2017-10-10T10:55:49.046Z
		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void adjustInto() {
		Instant now = Instant.now();// 2017-10-13T02:48:44.081Z
		// Temporal adjustInto = now.adjustInto(LocalTime.of(5, 3));
		// Temporal adjustInto3 = now.adjustInto(Year.of(3));
		// Temporal adjustInto5 = now.adjustInto(YearMonth.of(1, 2));
		// Temporal adjustInto = now.adjustInto(LocalDateTime.of(2017, 11, 14,
		// 5, 3));
		// Temporal adjustInto3 = now.adjustInto(ZonedDateTime.of(2017, 11, 5,
		// 12, 53, 52, 516, ZoneId.of("Gmt+8")));
		// adjustInto(temporal)就是temporal.with(INSTANT_SECONDS,
		// seconds).with(NANO_OF_SECOND, nanos);
		// 根据自身的毫秒值，将传入的temporal进行重新赋值。
		// temporal.with(INSTANT_SECONDS, seconds).with(NANO_OF_SECOND, nanos);
		Temporal adjustInto4 = now.adjustInto(OffsetDateTime.of(2017, 3, 5, 6, 2, 1, 13, ZoneOffset.of("+10:00")));// 2017-10-13T12:48:44.081+10:00
		Instant now2 = Instant.now(Clock.system(ZoneId.of("GMT+10:00")));// 2017-10-13T02:52:55.524Z
		Temporal adjustInto2 = now.adjustInto(now2);// 2017-10-13T02:52:55.523Z
		int b = 0;
	}

}
