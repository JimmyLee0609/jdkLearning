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
		// �෽��
		// ʹ��Clock���ݵ�ǰʱ�� 2017-10-11T10:02:03.699+08:00
		OffsetDateTime now = OffsetDateTime.now();
		// ���ݴ���Clock��ʱ�� 2017-10-11T02:02:03.777Z
		OffsetDateTime now2 = OffsetDateTime.now(Clock.systemUTC());
		// ʹ��Clock���ݴ����ʱ�� 2017-10-11T10:02:03.777+08:00
		OffsetDateTime now3 = OffsetDateTime.now(ZoneId.of("GMT+8"));
		// ����ָ��������ʱ�� ��ƫ���� 2017-10-11T09:30:20.000000013+01:00
		OffsetDateTime of = OffsetDateTime.of(2017, 10, 11, 9, 30, 20, 13, ZoneOffset.ofHours(1));
		// ����ָ��������ʱ�� ��ƫ���� 2017-10-11T10:02:03.777+08:00
		OffsetDateTime of2 = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("+08:00"));
		// ����ָ��������ʱ�� ��ƫ���� 2017-10-11T10:02:03.777+08:00
		OffsetDateTime of3 = OffsetDateTime.of(LocalDate.now(), LocalTime.now(), ZoneOffset.of("+08:00"));
		// ����Instant��ʱ���ƫ����2017-10-11T10:02:03.777+08:00
		OffsetDateTime ofInstant = OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("GMT+8"));
		// ���ݴ����OffsetDateTime 2017-10-11T02:02:03.777Z
		OffsetDateTime from = OffsetDateTime.from(now2);// ������󣿣���

		minus();
		plus();
		with();

		Comparator<OffsetDateTime> timeLineOrder = OffsetDateTime.timeLineOrder();// �Ƚ���?
		int compare = timeLineOrder.compare(of, now);

		// ofInstant ���ݱ����ʱ��� �����ʱ����Ϣ �½� ����
		// 2017-10-11T12:27:11.418+10:00[GMT+10:00]
		ZonedDateTime atZoneSameInstant = now.atZoneSameInstant(ZoneId.of("GMT+10"));//
		// ���ݱ����ʱ�� �ʹ����ʱ����Ϣ �½� ����2017-10-11T10:02:03.699+08:00[GMT+08:00]
		ZonedDateTime atZoneSimilarLocal = now.atZoneSimilarLocal(ZoneId.of("GMT+8"));

		int compareTo = now.compareTo(of);// ����OffsetDateTime �Ƚ���� -1

		int dayOfMonth = now.getDayOfMonth();// ��ȡ��ǰOffsetDateTime���·�11
		DayOfWeek dayOfWeek = now.getDayOfWeek();// ��ȡ��ǰOffsetDateTime���ܼ�WEDNESDAY
		int dayOfYear = now.getDayOfYear();// ��ȡ��ǰOffsetDateTime����ĵڼ���284
		int minute = now.getMinute();// ���ݵ�λ��ȡ ��ǰ�� 2
		Month month = now.getMonth();// ���ݵ�λ��ȡ ��ǰ�� OCTOBER
		int monthValue = now.getMonthValue();// ���ݵ�λ��ȡ ��ǰ�� 10
		int nano = now.getNano();// ��ȡ��ǰ������������ֵ 699000000
		ZoneOffset offset = now.getOffset();// ��ȡ��ǰ�� ƫ���� +08:00
		int second = now.getSecond();// ��ȡ���ڵ��� 3
		int year = now.getYear();// ��ȡ���ڵ��� 2017
		int hour = now.getHour();// ��ȡ��ǰOffsetDateTime����ĵڼ���Сʱ 10

		int i = now.get(ChronoField.DAY_OF_YEAR);// ���ݵ�λ��ȡ ��ǰ ��284
		long long1 = now.getLong(ChronoField.DAY_OF_MONTH);// ���ݵ�λ��ȡ ��ǰ�� 11
		boolean after = now.isAfter(of);// �����Ƿ��Ǵ������֮�� false
		boolean before = now.isBefore(of);// ��ǰ�����Ƿ��Ǵ������֮ǰtrue

		ChronoField[] values = ChronoField.values();
		for (ChronoField chronoField : values) {
			boolean supported = now.isSupported(chronoField);// ȫ��֧��
			System.out.println(chronoField + "�Ƿ�֧��" + supported);
		}
		ChronoUnit[] values2 = ChronoUnit.values();
		for (ChronoUnit chronoUnit : values2) {
			boolean supported = now.isSupported(chronoUnit);
			// Forever -support?- false ��������true
			System.out.println(chronoUnit + " -support?- " + supported);
		}
		// ת��
		long epochSecond = now.toEpochSecond();// 1507687323
		Instant instant = now.toInstant();// ��ǰ����ת��Ϊ 2017-10-11T02:02:03.699Z
		LocalDate localDate = now.toLocalDate();// ��ǰ����ת��Ϊ ���� 2017-10-11
		LocalDateTime localDateTime = now.toLocalDateTime();// ��ǰ���� ת��Ϊ
															// ����ʱ��2017-10-11T10:02:03.699
		LocalTime localTime = now.toLocalTime();// ��ǰ����ת��Ϊ ����ʱ��10:02:03.699
		OffsetTime offsetTime = now.toOffsetTime();// ��ǰ����ת��Ϊƫ���� 10:02:03.699
		ZonedDateTime zonedDateTime = now.toZonedDateTime();// ��ǰ����ת��Ϊ
															// ʱ������ʱ��2017-10-11T10:02:03.699+08:00

		ValueRange range = now.range(ChronoField.SECOND_OF_MINUTE);// ���뵥λ�ķ�Χ 0
																	// - 59

		Object query = now.query((TemporalAccessor ta) -> {
			return null;
		});

		// ʹ�õ�ǰ�����������Ķ������������ڣ�ʱ�䣬ƫ���� �����ڵ�ǰ����
		Temporal adjustInto = now.adjustInto(of);// ????2017-10-11T11:13:33.170+08:00

		long until = now.until(OffsetDateTime.of(2017, 10, 14, 13, 00, 00, 23, ZoneOffset.of("+08:00")),
				ChronoUnit.DAYS);
		// ��� 1
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
		// �����ֵ���ܴ��ڵ�λ���������
		// ���ݴ����ֵ�޸Ķ�Ӧ��λ��ֵ
		OffsetDateTime now = OffsetDateTime.now();// 2017-10-11T11:50:32.848+08:00
		// ���ݵ�λ
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
		// ����ʱ������
		OffsetDateTime with2 = now.with(withHour);// 2017-10-11T16:50:32.848+08:00
		OffsetDateTime with3 = now.with(LocalDateTime.now());// 2017-10-11T11:50:32.848+08:00
		OffsetDateTime with4 = now.with(LocalDate.now());// 2017-10-11T11:50:32.848+08:00
		OffsetDateTime with5 = now.with(Instant.now());// 2017-10-11T11:50:32.848+08:00

		int b = 0;
	}

	@SuppressWarnings("unused")
	private static void plus() {
		// ���ݴ����ƫ���½�һ��OffsetDateTime����ƫ��
		OffsetDateTime now = OffsetDateTime.now();// 2017-10-11T11:39:54.586+08:00
		// ���ݵ�λ
		OffsetDateTime plusDays = now.plusDays(1);// 2017-10-12T11:39:54.586+08:00
		OffsetDateTime plusHours = now.plusHours(15);// 2017-10-12T02:39:54.586+08:00
		OffsetDateTime plusMinutes = now.plusMinutes(20);// 2017-10-11T11:59:54.586+08:00
		OffsetDateTime plusYears = now.plusYears(2);// 2019-10-11T11:39:54.586+08:00
		OffsetDateTime plusWeeks = now.plusWeeks(1);// 2017-10-18T11:39:54.586+08:00
		OffsetDateTime plusSeconds = now.plusSeconds(500);// 2017-10-11T11:48:14.586+08:00
		OffsetDateTime plusNanos = now.plusNanos(8000);// 2017-10-11T11:39:54.586008+08:00
		OffsetDateTime plusMonths = now.plusMonths(3);// 2018-01-11T11:39:54.586+08:00
		OffsetDateTime plus = now.plus(3, ChronoUnit.MONTHS);// 2018-01-11T11:39:54.586+08:00
		// �����ڼ�
		OffsetDateTime plus2 = now.plus(Duration.ofDays(3));// 2017-10-14T11:39:54.586+08:00
		OffsetDateTime plus3 = now.plus(Period.ofDays(4));// 2017-10-15T11:39:54.586+08:00
		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void minus() {
		// ���ݴ����ƫ���½�һ��OffsetDateTime��ȥƫ��
		OffsetDateTime now = OffsetDateTime.now();// 2017-10-11T11:32:49.221+08:00
		// ���ݵ�λ
		OffsetDateTime minusYears = now.minusYears(1);// 2016-10-11T11:32:49.221+08:00
		OffsetDateTime minusMonths = now.minusMonths(1);// 2017-09-11T11:32:49.221+08:00
		OffsetDateTime minusDays = now.minusDays(3);// 2017-10-08T11:32:49.221+08:00
		OffsetDateTime minusHours = now.minusHours(4);// 2017-10-11T07:32:49.221+08:00
		OffsetDateTime minusWeeks = now.minusWeeks(1);// 2017-10-04T11:32:49.221+08:00
		OffsetDateTime minusMinutes = now.minusMinutes(300);// 2017-10-11T11:02:49.221+08:00
		OffsetDateTime minusSeconds = now.minusSeconds(45);// 2017-10-11T11:32:04.221+08:00
		OffsetDateTime minusNanos = now.minusNanos(5000);// 2017-10-11T11:32:49.220995+08:00
		OffsetDateTime minus = now.minus(5, ChronoUnit.MONTHS);// 2017-05-11T11:32:49.221+08:00
		// �����ڼ�
		OffsetDateTime minus2 = now.minus(Duration.ofDays(3));// 2017-10-08T11:32:49.221+08:00
		OffsetDateTime minus3 = now.minus(Period.ofDays(4));// 2017-10-07T11:34:42.203+08:00
		int c = 0;
	}

}
