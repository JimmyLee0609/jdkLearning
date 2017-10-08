package util.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class CalendarTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//获取日历类实例     
		getInstance();
		Calendar instance = Calendar.getInstance();
		Calendar instance2 = Calendar.getInstance(Locale.TAIWAN);
		
//		获取当前时间   返回Date类型
		Date time = instance.getTime();
//		获取日历当当前属性
		int i = instance.get(Calendar.DAY_OF_WEEK);        //现在是周几   根据字段返回现在的值
		int j = instance.get(Calendar.HOUR_OF_DAY);       //现在是几点
		long timeInMillis = instance.getTimeInMillis();       //现在距离原始的时间的毫秒数
		int k = instance.get(Calendar.DAY_OF_WEEK_IN_MONTH);//获取当月的第一周最少多少天
		String calendarType = instance.getCalendarType();//获取当前类的实例类型
		Locale[] availableLocales = instance.getAvailableLocales();//返回可用的区域属性
//		获取运行环境中可用的日历类行
		Set<String> availableCalendarTypes = instance.getAvailableCalendarTypes();
		
		int weekYear = instance.getWeekYear();      //当前是第几周年
		int actualMinimum = instance.getActualMinimum(Calendar.DAY_OF_MONTH);//获取字段的实际最小值
																		//	当前实例的  传入字段的区域属性中  显示类型的显示值  //十月
		String displayName = instance.getDisplayName(
				Calendar.MONTH, Calendar.LONG_FORMAT, Locale.getDefault());
//		获取字段的最大值的最小值
		int greatestMinimum = instance.getGreatestMinimum(Calendar.DAY_OF_MONTH);
//		获取年的第一周需要多少天
		int minimalDaysInFirstWeek = instance.getMinimalDaysInFirstWeek();
		int weeksInWeekYear = instance.getWeeksInWeekYear(); //当前是年的第几周
//		获取一周的第一天是周几   SUNDAY?   MONDAY?
		int firstDayOfWeek = instance.getFirstDayOfWeek();
//		获取时区
		TimeZone timeZone = instance.getTimeZone();
		//获取字段的最小值
		int minimum = instance.getMinimum(Calendar.AM);
		//获取字段的最大值
		int maximum = instance.getMaximum(Calendar.DAY_OF_WEEK);
		//获取最小值的最大值
		int leastMaximum = instance.getLeastMaximum(Calendar.DAY_OF_WEEK_IN_MONTH);
		
		//		将时间按照要求位移
		instance.add(Calendar.HOUR_OF_DAY, 3);   //原来的天加3天，不会超过当前月份的最大值
		instance.roll(Calendar.MONTH, false);			//将月份向后回滚  8月->7月 true就会往前滚，超过年的月份限制，调整年
		instance.roll(Calendar.MONTH, 5);			//将月份向后滚  3月->8月 负数就往前滚。超过每年的月份限制，将调整年
		
//		判断日历实例代表的时间那个在前，后
		boolean after = instance2.after(instance);
		boolean before = instance2.before(instance);
		int compareTo = instance2.compareTo(instance);
		Date time2 = instance.getTime();
		boolean after2 = instance.after(instance2);
		
//		根据传入的字段设置，不会超出最大值  ，超过不会调整其他
		instance.set(Calendar.DAY_OF_MONTH, 31);
		instance.set(1998, 12, 31);//设置年月日   时间会根据时区调整  有时候会跨日
		instance.set(1998, 12, 31,13,50,23);//设置年月日  时分秒
		instance.setFirstDayOfWeek(Calendar.MONDAY);   //设置一周的第一天是周几
		instance.setLenient(false);    //是否宽松   默认否
		instance.setMinimalDaysInFirstWeek(1);   //设置年的第一周需要多少天   
		instance.setTimeInMillis(System.nanoTime());//根据毫秒值设置实例时间
		instance.setTimeZone(timeZone);     //设置时区
		
		
//		设置周年                   50周年 30周   周5
		instance.setWeekDate(50, 30, 5);
		int weekYear2 = instance.getWeekYear();//50周年
		Date time3 = instance.getTime();
//		是否被设置
		boolean lenient = instance.isLenient();       //是否是宽松的   默认宽松
		boolean set = instance.isSet(Calendar.DAY_OF_MONTH);   //是否设置了月中的日  
		boolean weekDateSupported = instance.isWeekDateSupported(); //是否支持周年
//		清除设置的字段。
		instance.clear(Calendar.MONTH);
//		清除全部字段，变为初始1970年
		instance.clear();
	}

	private static void getInstance() {
		Calendar instance = Calendar.getInstance();
		Calendar instance2 = Calendar.getInstance(Locale.TAIWAN);
		Calendar instance3 = Calendar.getInstance(TimeZone.getDefault());
		Calendar instance4 = Calendar.getInstance(TimeZone.getDefault(), Locale.TAIWAN);
	}

}
