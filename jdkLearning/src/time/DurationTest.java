package time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.List;

public class DurationTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// ��λ֧��������
		// ��̬����
		Duration ofSeconds = Duration.ofSeconds(10, 500); // ����һ��10��500�����Duration
		Duration ofSeconds2 = Duration.ofSeconds(5); // ����һ��5��
		Duration ofNanos = Duration.ofNanos(490); // ����һ��490����
		Duration ofMinutes = Duration.ofMinutes(32); // ����һ��32����
		Duration ofMillis = Duration.ofMillis(4923); // ����һ��4923����
		Duration ofHours = Duration.ofHours(2); // ����һ��2Сʱ
		Duration ofDays = Duration.ofDays(1); // ����һ��1��
		// ���ݴ���ĵ�λ��ʱ�䴴��Duration
		Duration of = Duration.of(5, ChronoUnit.HOURS); // ����һ��5Сʱ

		// Ŀǰֻ���õ� Duration�Ķ���
		Duration from = Duration.from(Duration.ofHours(3)); // 10800��
		// ʵ������
		minus();
		plus();

		// ��ȡ
		int nano = from.getNano(); // ��ȡ��Duration �������� ���0
		long seconds = from.getSeconds(); // ��ȡ��Duration������ ���10800
		// ֻ������������λ����������
		long l = from.get(ChronoUnit.SECONDS); // ���ݴ���ĵ�λת����� ���10800
		long h = from.get(ChronoUnit.NANOS); // ���ݴ���ĵ�λת����� ���10800
		// ת��
		long nanos = from.toNanos(); // ��Duration ת���� ���� ���10800000000000
		long days = from.toDays(); // ����ת������ ���0
		long hours = from.toHours(); // ����ת����Сʱ ���3
		long millis = from.toMillis(); // ����ת���ɺ��� ���10800000
		long minutes = from.toMinutes(); // ����ת���ɷ��� ���180

		List<TemporalUnit> units2 = from.getUnits();// [Seconds, Nanos]

		String string = from.toString(); // PT3H

		// ��Ҫ����Ϊ��λ��LocalDate������
		Duration between = Duration.between(Instant.now(), Instant.now().plus(10, ChronoUnit.SECONDS)); // ��������Instant
																										// ֮��ļ��
																										// ���
																										// 10��
		Duration between2 = Duration.between(LocalDateTime.now(), // ��������LocalDateTime֮��ļ����
																	// ���36000��
				LocalDateTime.now().plus(10, ChronoUnit.HOURS));

		Duration abs = from.abs(); // ��ȡ����ֵ ���10800
		Temporal addTo = from.addTo(Instant.now()); // ��Duration�ӵ�Instant ʵ��
													// ���2017-10-10T10:36:51.909Z

		int compareTo = from.compareTo(ofDays); // �봫���Duration �Ƚ� ���-1
		Duration dividedBy = from.dividedBy(7); // ���Դ����ֵ 1542�� 857142857����
		List<TemporalUnit> units = from.getUnits(); // ��ȡDuration�ĵ�λ Seconds
													// Nanos
		boolean negative = from.isNegative(); // �Ƿ��Ǹ�ֵ false
		boolean zero = from.isZero(); // �Ƿ���0 false

		// ���ݴ������ֵ��ԭ��������ֵ�����µ�Duration���� ��� 700��
		Duration withSeconds = from.withSeconds(700);
		// ���ݴ��������ֵ��ԭ������ֵ�����µ�Duration���� ���10800�� 900����
		Duration withNanos = from.withNanos(900);
		Temporal subtractFrom = from.subtractFrom(Instant.now());// ���ݴ����Instant
																	// ժҪ������
																	// �ͺ�����

		Duration negated = from.negated(); // ȡ��-10800��

		// from.parse(text)
	}

	@SuppressWarnings("unused")
	private static void minus() {
		Duration from = Duration.from(Duration.ofHours(3));
		// ��ԭ���Ļ����� ���� minus

		Duration minus2 = from.minus(7, ChronoUnit.MINUTES);// ���ݴ������ �͵�λ ����
															// ���-14400��
		Duration minus = from.minus(Duration.ofMillis(7)); // ���ݴ����Duration ����
															// ���8880��
		Duration minusDays = from.minusDays(1); // ����1�� ���-75600��
		Duration minusHours = from.minusHours(13); // ����13Сʱ ���-36000�� 10Сʱ
		Duration minusMinutes = from.minusMinutes(300); // ����300���� ���-7200��
		Duration minusSeconds = from.minusSeconds(5830); // ����5380�� ���4970��
		Duration minusMillis = from.minusMillis(7000); // ����7000���� ���10793��
		Duration minusNanos = from.minusNanos(9000); // ����9000���� ���10799��
														// 999991000����

	}

	@SuppressWarnings("unused")
	private static void plus() {
		Duration from = Duration.from(Duration.ofHours(3));
		// ��Ӵ����ƫ��
		Duration plus = from.plus(Duration.ofHours(3)); // �봫���Duration���
														// ofHours������Сʱ ��� 18000
		Duration plus2 = from.plus(5, ChronoUnit.HOURS); // �봫���ָ����λʱ�������
															// ���28800
		Duration plusDays = from.plusDays(1); // ��һ����� ���97200
		Duration plusHours = from.plusHours(7); // ��7Сʱ���� ���36000
		Duration plusMinutes = from.plusMinutes(80); // ��80���ӵ��� 15600
		Duration plusSeconds = from.plusSeconds(535); // ��535���ӵ��� ���11335
		Duration plusMillis = from.plusMillis(5000); // ��5000������� ���10805
		Duration plusNanos = from.plusNanos(9000); // ��9000������� ���10800�� 9000����

	}

}
