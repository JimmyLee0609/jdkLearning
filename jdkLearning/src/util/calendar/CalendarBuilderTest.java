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
		// ���ù���ֵ�����ظ��趨�������쳣
		// builder2.setInstant(System.nanoTime());//���ݺ���ֵ����
		Calendar build2 = builder2.setInstant(new Date()).build();// ���������ཨ��
		// builder2.setWeekDate(2, 20, 30);//��������
		// Calendar setFields =
		// builder2.setFields(Calendar.HOUR_OF_DAY,20).build();
		Date time = build2.getTime();
		System.out.println(build.getTime());
	}

}
