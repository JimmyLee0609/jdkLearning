package time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.Locale;

public class DayOfWeekTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 类方法 获取实例方法
		DayOfWeek friday = DayOfWeek.FRIDAY;// 直接用枚举类型 FRIDAY
		DayOfWeek of = DayOfWeek.of(5);// 根据枚举的位置-1获取 周一就是 1 FRIDAY
		DayOfWeek valueOf = DayOfWeek.valueOf("FRIDAY");// 根据枚举类型的名字获取 FRIDAY
		DayOfWeek valueOf2 = DayOfWeek.valueOf(DayOfWeek.class, "FRIDAY");// 根据指定枚举类型的名字获取
																			// FRIDAY
		DayOfWeek from = DayOfWeek.from(of);// FRIDAY
		DayOfWeek[] values = DayOfWeek.values();// [MONDAY, TUESDAY, WEDNESDAY,
												// THURSDAY, FRIDAY, SATURDAY,
												// SUNDAY]
		// 实例方法
		int compareTo = friday.compareTo(of);// 两个比较相差 0
		int i = friday.get(ChronoField.DAY_OF_WEEK);// 根据单位获取值 5
		long long1 = friday.getLong(ChronoField.DAY_OF_WEEK);// 根据单位获取值 5
		Class<DayOfWeek> declaringClass = friday.getDeclaringClass();// 获取声明的类
																		// class
																		// java.time.DayOfWeek
		String displayName = friday.getDisplayName(TextStyle.FULL, Locale.CHINA);// 根据区域属性获取显示的格式
																					// 星期五
		int value = friday.getValue();// 获取值 5
		// 支持那些单位
		ChronoField[] values2 = ChronoField.values();
		for (ChronoField chronoField : values2) {
			// DayOfWeek- 是否支持 - true
			boolean supported = friday.isSupported(chronoField);
			System.out.println(chronoField + "- 是否支持 -  " + supported);
		}
		DayOfWeek minus = friday.minus(3);// 目前枚举的值减传入的数，转换成枚举是 TUESDAY
		DayOfWeek plus = friday.plus(2);// 目前枚举的值加上传入的数，转换成枚举是 SUNDAY
		String name = friday.name();// 枚举的名字是 FRIDAY
		int ordinal = friday.ordinal();// 枚举的序号是 4
		ValueRange range = friday.range(ChronoField.DAY_OF_WEEK);// 枚举的范围是 1 - 7
		// 就是将传入的日期按照本属性调整，不管是周几，现在变成周五，是几号
		Temporal adjustInto = friday.adjustInto(LocalDate.now());// 根据星期调整传入的日期
																	// 2017-10-13

		String string = friday.toString();// FRIDAY

		// 用法不明
		Object query = friday.query((TemporalAccessor b) -> {
			return null;
		});

	}

}
