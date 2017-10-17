package time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.List;

public class PeriodTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// �෽��
		Period zero = Period.ZERO;// ����һ��ƫ����Ϊ0�Ķ���P0D
		Period of = Period.of(2017, 10, 12);// ����һ��ƫ���� P2017Y10M12D
		Period ofDays = Period.ofDays(5);// P5D
		Period ofMonths = Period.ofMonths(3);// P3M
		Period ofYears = Period.ofYears(2016);// P2016Y
		Period ofWeeks = Period.ofWeeks(13);// P91D
		// ����ƫ���������µ�ƫ���� Duration������
		Period from = Period.from(Period.ofDays(2));// P2D
		// �����������ڵĲ�ֵ ����ƫ����
		Period between = Period.between(LocalDate.of(2017, 10, 03), LocalDate.of(2017, 10, 05));// P2D
		plus();
		with();
		minus();
		// ʵ������
		List<TemporalUnit> units2 = between.getUnits(); // [Years, Months, Days]
		Temporal addTo = ofDays.addTo(Instant.now());// �������ֵ����ƫ�������½�����ƫ��
														// 2017-10-16T05:55:39.760Z
		long l = ofDays.get(ChronoUnit.DAYS);// ��ȡ��λ���� 5

		IsoChronology chronology = ofDays.getChronology();// ��ȡ��׼ ISO

		int days = ofDays.getDays();// ��ȡ�� 5
		int months = ofDays.getMonths();// ��ȡ�� 0
		List<TemporalUnit> units = ofDays.getUnits();// ��ȡ��λ [Years, Months,
														// Days]
		int years = ofDays.getYears();// ��ȡ �� 0
		boolean negative = ofDays.isNegative();// �Ƿ��Ǹ� false
		boolean zero2 = ofDays.isZero();// �Ƿ�Ϊ0 false
		Period negated = ofDays.negated();// ȡ�� P-5D

		long totalMonths = ofDays.toTotalMonths();// 0
		Period normalized = ofDays.normalized();// P5D
		Period multipliedBy = of.multipliedBy(10);// �������ֵ����10�� P20170Y100M120D

		Temporal subtractFrom = ofDays.subtractFrom(LocalDate.of(2017, 10, 3));// ���ݴ�������ڣ����ն����ƫ����ƫ��
																				// �½�ʱ�����2017-09-28
		int v = 0;
		// Period.parse(text)
	}

	@SuppressWarnings("unused")
	private static void minus() {
		Period of = Period.of(2017, 10, 30);// P2017Y10M30D
		// �����봫�������� ���ݵ�λ
		Period minusDays = of.minusDays(10);// P2017Y10M20D
		Period minusMonths = of.minusMonths(2);// P2017Y8M30D
		Period minusYears = of.minusYears(5);// P2012Y10M30D

		Period minus = of.minus(minusYears);// ����Period��� ��ֵP5Y

		int o = 0;
	}

	@SuppressWarnings("unused")
	private static void with() {
		Period of = Period.of(2017, 10, 30);// P2017Y10M30D
		// �����մ���������и��ģ� ���ܳ����õ�λ�����ֵ
		Period withDays = of.withDays(5);// P2017Y10M5D
		Period withMonths = of.withMonths(3);// P2017Y3M30D
		Period withYears = of.withYears(8);// P8Y10M30D

		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void plus() {
		Period of = Period.of(2017, 10, 30);// P2017Y10M30D
		// �����մ�������������
		Period plusDays = of.plusDays(5);// P2017Y10M35D
		Period plusMonths = of.plusMonths(2);// P2017Y12M30D
		Period plusYears = of.plusYears(3);// P2020Y10M30D

		Period plus = of.plus(plusYears);// ����Period��� P4037Y20M60D
											// Period.from(amountToAdd);

		int b = 0;
	}

}
