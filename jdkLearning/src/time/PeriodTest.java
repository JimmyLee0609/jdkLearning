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
		// 类方法
		Period zero = Period.ZERO;// 创建一个偏移量为0的对象P0D
		Period of = Period.of(2017, 10, 12);// 创建一个偏移量 P2017Y10M12D
		Period ofDays = Period.ofDays(5);// P5D
		Period ofMonths = Period.ofMonths(3);// P3M
		Period ofYears = Period.ofYears(2016);// P2016Y
		Period ofWeeks = Period.ofWeeks(13);// P91D
		// 根据偏移量创建新的偏移量 Duration不能用
		Period from = Period.from(Period.ofDays(2));// P2D
		// 根据两个日期的差值 创建偏移量
		Period between = Period.between(LocalDate.of(2017, 10, 03), LocalDate.of(2017, 10, 05));// P2D
		plus();
		with();
		minus();
		// 实例方法
		List<TemporalUnit> units2 = between.getUnits(); // [Years, Months, Days]
		Temporal addTo = ofDays.addTo(Instant.now());// 将传入的值按照偏移量，新建对象偏移
														// 2017-10-16T05:55:39.760Z
		long l = ofDays.get(ChronoUnit.DAYS);// 获取单位的量 5

		IsoChronology chronology = ofDays.getChronology();// 获取标准 ISO

		int days = ofDays.getDays();// 获取日 5
		int months = ofDays.getMonths();// 获取月 0
		List<TemporalUnit> units = ofDays.getUnits();// 获取单位 [Years, Months,
														// Days]
		int years = ofDays.getYears();// 获取 年 0
		boolean negative = ofDays.isNegative();// 是否是负 false
		boolean zero2 = ofDays.isZero();// 是否为0 false
		Period negated = ofDays.negated();// 取反 P-5D

		long totalMonths = ofDays.toTotalMonths();// 0
		Period normalized = ofDays.normalized();// P5D
		Period multipliedBy = of.multipliedBy(10);// 将对象的值增大10倍 P20170Y100M120D

		Temporal subtractFrom = ofDays.subtractFrom(LocalDate.of(2017, 10, 3));// 根据传入的日期，按照对象的偏移量偏移
																				// 新建时间对象2017-09-28
		int v = 0;
		// Period.parse(text)
	}

	@SuppressWarnings("unused")
	private static void minus() {
		Period of = Period.of(2017, 10, 30);// P2017Y10M30D
		// 对象与传入的量相减 根据单位
		Period minusDays = of.minusDays(10);// P2017Y10M20D
		Period minusMonths = of.minusMonths(2);// P2017Y8M30D
		Period minusYears = of.minusYears(5);// P2012Y10M30D

		Period minus = of.minus(minusYears);// 两个Period相减 差值P5Y

		int o = 0;
	}

	@SuppressWarnings("unused")
	private static void with() {
		Period of = Period.of(2017, 10, 30);// P2017Y10M30D
		// 对象按照传入的量进行更改， 不能超过该单位的最大值
		Period withDays = of.withDays(5);// P2017Y10M5D
		Period withMonths = of.withMonths(3);// P2017Y3M30D
		Period withYears = of.withYears(8);// P8Y10M30D

		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void plus() {
		Period of = Period.of(2017, 10, 30);// P2017Y10M30D
		// 对象按照传入的量进行相加
		Period plusDays = of.plusDays(5);// P2017Y10M35D
		Period plusMonths = of.plusMonths(2);// P2017Y12M30D
		Period plusYears = of.plusYears(3);// P2020Y10M30D

		Period plus = of.plus(plusYears);// 两个Period相加 P4037Y20M60D
											// Period.from(amountToAdd);

		int b = 0;
	}

}
