package time;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;

public class MonthDayTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 类方法
		MonthDay now = MonthDay.now();// 获取目前的日期--10-11
		MonthDay of = MonthDay.of(9, 5);// 根据传入的量，创建对应的日期--09-05
		MonthDay now2 = MonthDay.now(ZoneId.of("GMT+8"));// 根据时区调整目前的日期--10-11
		MonthDay of2 = MonthDay.of(Month.OCTOBER, 11);// 根据传入的月份和量
														// 创建对应的日期--10-11
		MonthDay from = MonthDay.from(of2);// 根据传入的MonthDay信息 创建对应的日期--10-11
		MonthDay now3 = MonthDay.now(Clock.systemUTC());// 根据传入的时钟创建日期 --10-11
		// 实例方法
		Temporal adjustInto = of.adjustInto(LocalDate.now());// 根据对象的值赋值给日期
																// 2017-09-05
		LocalDate atYear = of.atYear(2018);// 目前日期在指定年是几号2018-09-05
		int compareTo = of.compareTo(now);// 目前的MonthDay与传入的比较相差 -1
		int i = of.get(ChronoField.DAY_OF_MONTH);// 根据传入的单位获取值 5
		long long1 = of.getLong(ChronoField.DAY_OF_MONTH);// 根据传入的单位获取值 5
		int dayOfMonth = of.getDayOfMonth();// 获取对象现在在月中是几号 5
		Month month = of.getMonth();// 获取对象的月份 SEPTEMBER
		int monthValue = of.getMonthValue();// 获取对象月份的值 9
		boolean after = of.isAfter(of2);// 对象是否比传入的晚 false
		boolean before = of.isBefore(of2);// 对象是否比传入的早 true
		ChronoField[] values = ChronoField.values();
		for (ChronoField chronoField : values) {
			boolean supported = of.isSupported(chronoField);
			// MonthOfYear-是否支持-true DayOfMonth-是否支持-true
			System.out.println(chronoField + "-是否支持-" + supported);
		}
		boolean validYear = of.isValidYear(2056);// 对象在指定的年是否有效 true
		ValueRange range = of.range(ChronoField.DAY_OF_MONTH);// 对象在单位的范围1-30
		ValueRange range2 = of.range(ChronoField.MONTH_OF_YEAR);// 对象在单位的范围1-12

		Object query = of.query((TemporalAccessor ta) -> {
			return null;
		});
		MonthDay with = of.with(Month.FEBRUARY);// 使用Month改变当前月份
		MonthDay withMonth = of.withMonth(2);// 改变当前月份
		MonthDay withDayOfMonth = of.withDayOfMonth(23);// 改变当前号数

		String string = of.toString();
		// of.format(formatter)
		// MonthDay.parse()
		// MonthDay.parse(text, formatter)

	}

}
