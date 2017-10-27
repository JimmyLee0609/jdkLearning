package util.calendar;

import java.util.Calendar;
import java.util.Calendar.Builder;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarBuilderTest {

	public static void main(String[] args) {
		Builder builder = new Calendar.Builder();
		Calendar build = builder.setCalendarType("gregorian").setDate(2017, 10, 8).setTimeOfDay(20, 40, 32, 500)
				.setTimeZone(TimeZone.getTimeZone("Etc/GMT-8")).setLocale(Locale.CHINA).build();
		Builder builder2 = new Calendar.Builder();
		// 设置过的值不能重复设定，会抛异常
		// builder2.setInstant(System.nanoTime());//根据毫秒值建造
		Calendar build2 = builder2.setInstant(new Date()).build();// 根据日期类建造
		// builder2.setWeekDate(2, 20, 30);//设置周年
		// Calendar setFields =
		// builder2.setFields(Calendar.HOUR_OF_DAY,20).build();
		Date time = build2.getTime();
		System.out.println(build.getTime());
	}

}
