package time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.Locale;

public class MonthTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Month of = Month.of(1);// 根据传入数字创建枚举 从1开始 JANUARY
		Month from = Month.from(of);// 根据Month枚举 创建新的枚举 JANUARY
		Month[] values = Month.values();// 枚举的所有值 [JANUARY, FEBRUARY, MARCH,
										// APRIL, MAY, JUNE, JULY, AUGUST,
										// SEPTEMBER, OCTOBER, NOVEMBER,
										// DECEMBER]
		Month valueOf = Month.valueOf("APRIL");// 根据传入的字符串创建枚举 APRIL
		Month valueOf2 = Month.valueOf(Month.class, "APRIL");// 根据枚举类型，和字符串，创建枚举
																// APRIL

		int compareTo = of.compareTo(valueOf);// 与传入的月份比较 相差 -3
		int firstDayOfYear = of.firstDayOfYear(false);// 枚举月的第一天是一年的第几天 1
														// 参数：是否闰年
		Month firstMonthOfQuarter = of.firstMonthOfQuarter();// 当月所在季度的第一个月的名字
																// JANUARY

		Class<Month> declaringClass = of.getDeclaringClass();// class
																// java.time.Month
		String displayName = of.getDisplayName(TextStyle.FULL, Locale.CHINA);// 一月

		// 单位只接收ChronoField.MONTH_OF_YEAR
		int i = of.get(ChronoField.MONTH_OF_YEAR);// 1
		long long1 = of.getLong(ChronoField.MONTH_OF_YEAR);// 2
		ValueRange range = of.range(ChronoField.MONTH_OF_YEAR);// 1-12
		ChronoField[] values2 = ChronoField.values();
		for (ChronoField chronoField : values2) {
			boolean supported = of.isSupported(chronoField);
			// MonthOfYear- 支持吗？ - true
			System.out.println(chronoField + "- 支持吗？ - " + supported);
		}

		int value = of.getValue();// 获取枚举对应的数字就是对应的数字月份1
		boolean supported = of.isSupported(ChronoField.SECOND_OF_MINUTE);// 是否支持指定的类型false
		int length = of.length(false);// 当月的日数 31 参数：是否闰年
		int maxLength = of.maxLength();// 当月的最大日数 31
		int minLength = of.minLength();// 当月的最小日数 31
		String name = of.name();// 枚举的名字 JANUARY
		Month minus = of.minus(3);// 减3个月是几月 OCTOBER
		Month plus = of.plus(5);// 加5个月是几月 JUNE
		int ordinal = of.ordinal();// 枚举的索引 0

		Month of2 = Month.of(5);
		Temporal adjustInto = of2.adjustInto(LocalDate.now());// 根据月份 调整传入的 日期
																// 2017-05-11

		String string = of.toString();// JANUARY
		// 未知用法？？？？？？？？？？？
		Object query = of.query((TemporalAccessor a) -> {
			return null;// JANUARY
		});
		int b = 0;
	}

}
