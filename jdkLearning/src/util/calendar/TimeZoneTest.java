package util.calendar;

import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimeZoneTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		timeZoneMethod();
//		实现类其实跟抽象类的用法一样
		TimeZone zone = SimpleTimeZone.getDefault();//获取默认的TimeZone
		String displayName = zone.getDisplayName();   //获取显式的名字
		String id = zone.getID();									//获取ID
		String displayName2 = zone.getDisplayName(Locale.CANADA); //设定本地属性设置显示名
		String displayName3 = zone.getDisplayName(false, TimeZone.LONG, Locale.CANADA);
		int dstSavings = zone.getDSTSavings();      //华盛顿时间的偏移
		int offset = zone.getOffset(System.currentTimeMillis());   //获取指定日期的时区偏移
		int offset2 = zone.getOffset(Calendar.ERA, 2017, 10, 7, 6, 50);  
		zone.setRawOffset(18000);       //设定偏移量
		int rawOffset = zone.getRawOffset();//返回经过设定的偏移量
		String displayName4 = zone.getDisplayName();
	}

	@SuppressWarnings("unused")
	private static void timeZoneMethod() {
		//获取运行的系统所可以提供的TimeZoneId
				String[] availableIDs = TimeZone.getAvailableIDs();
				for (String string : availableIDs) {
					System.out.println(string);
				}
				//获取默认的TimeZone  根据系统设定获取
				TimeZone default1 = TimeZone.getDefault();
				//获取时区id
				String id = default1.getID();
				//根据时区id获取时区
				TimeZone timeZone = TimeZone.getTimeZone("America/Toronto");
				//获取时区名字
				String displayName = default1.getDisplayName();
//				获取时区名字                                                         是否夏令时       详写             本地化设置                          
				String displayName2 = timeZone.getDisplayName(false, TimeZone.LONG, Locale.CHINESE);
				String displayName3 = timeZone.getDisplayName(true, TimeZone.LONG, Locale.CHINESE);
				
				int dstSavings = default1.getDSTSavings();
				//是否有相同的规则
				boolean hasSameRules = default1.hasSameRules(TimeZone.getTimeZone("Asia/Taipei"));
				//从指定日期的标准时间返回此地区的偏移量，如果在夏令时，会调整
				int offset = default1.getOffset(System.currentTimeMillis());
//				或不此地区的偏移量
				int rawOffset = default1.getRawOffset();
//				地区是否使用夏令时
				boolean observesDaylightTime = timeZone.observesDaylightTime();
//				是否使用夏令时
				boolean useDaylightTime = timeZone.useDaylightTime();
		
	}

}
