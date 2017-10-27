package util.calendar;

import java.time.Instant;
import java.util.Date;

public class DateTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Date date = new Date();
		// Date的方法基本由Calendar类替代了。
		int date2 = date.getDate();
		int month = date.getMonth();
		int year = date.getYear();
		String gmtString = date.toGMTString();
		long utc = date.UTC(1999, 12, 01, 2, 30, 50);
		String localeString = date.toLocaleString();

		long time = date.getTime(); // 获取当前时间的毫秒值，与1970年基准的偏移量
		date.setTime(System.currentTimeMillis()); // 根据传入的毫秒值 设置类的时间
		Instant instant = date.toInstant(); // 转换为Instant类
		boolean before = date.before(new Date()); // 比较是否在日期之前
		boolean after = date.after(new Date()); // 比较是否在日期之后
		Date from = Date.from(instant); // 从传入的Instant类中转换成Date
		// 根据字符串转换成时间
		long parse = Date.parse("Sat, 12 Aug 1995 13:30:00 GMT");
		System.out.println(parse);
	}

}
