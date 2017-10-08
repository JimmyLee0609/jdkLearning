package util.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class CalendarTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//��ȡ������ʵ��     
		getInstance();
		Calendar instance = Calendar.getInstance();
		Calendar instance2 = Calendar.getInstance(Locale.TAIWAN);
		
//		��ȡ��ǰʱ��   ����Date����
		Date time = instance.getTime();
//		��ȡ��������ǰ����
		int i = instance.get(Calendar.DAY_OF_WEEK);        //�������ܼ�   �����ֶη������ڵ�ֵ
		int j = instance.get(Calendar.HOUR_OF_DAY);       //�����Ǽ���
		long timeInMillis = instance.getTimeInMillis();       //���ھ���ԭʼ��ʱ��ĺ�����
		int k = instance.get(Calendar.DAY_OF_WEEK_IN_MONTH);//��ȡ���µĵ�һ�����ٶ�����
		String calendarType = instance.getCalendarType();//��ȡ��ǰ���ʵ������
		Locale[] availableLocales = instance.getAvailableLocales();//���ؿ��õ���������
//		��ȡ���л����п��õ���������
		Set<String> availableCalendarTypes = instance.getAvailableCalendarTypes();
		
		int weekYear = instance.getWeekYear();      //��ǰ�ǵڼ�����
		int actualMinimum = instance.getActualMinimum(Calendar.DAY_OF_MONTH);//��ȡ�ֶε�ʵ����Сֵ
																		//	��ǰʵ����  �����ֶε�����������  ��ʾ���͵���ʾֵ  //ʮ��
		String displayName = instance.getDisplayName(
				Calendar.MONTH, Calendar.LONG_FORMAT, Locale.getDefault());
//		��ȡ�ֶε����ֵ����Сֵ
		int greatestMinimum = instance.getGreatestMinimum(Calendar.DAY_OF_MONTH);
//		��ȡ��ĵ�һ����Ҫ������
		int minimalDaysInFirstWeek = instance.getMinimalDaysInFirstWeek();
		int weeksInWeekYear = instance.getWeeksInWeekYear(); //��ǰ����ĵڼ���
//		��ȡһ�ܵĵ�һ�����ܼ�   SUNDAY?   MONDAY?
		int firstDayOfWeek = instance.getFirstDayOfWeek();
//		��ȡʱ��
		TimeZone timeZone = instance.getTimeZone();
		//��ȡ�ֶε���Сֵ
		int minimum = instance.getMinimum(Calendar.AM);
		//��ȡ�ֶε����ֵ
		int maximum = instance.getMaximum(Calendar.DAY_OF_WEEK);
		//��ȡ��Сֵ�����ֵ
		int leastMaximum = instance.getLeastMaximum(Calendar.DAY_OF_WEEK_IN_MONTH);
		
		//		��ʱ�䰴��Ҫ��λ��
		instance.add(Calendar.HOUR_OF_DAY, 3);   //ԭ�������3�죬���ᳬ����ǰ�·ݵ����ֵ
		instance.roll(Calendar.MONTH, false);			//���·����ع�  8��->7�� true�ͻ���ǰ������������·����ƣ�������
		instance.roll(Calendar.MONTH, 5);			//���·�����  3��->8�� ��������ǰ��������ÿ����·����ƣ���������
		
//		�ж�����ʵ�������ʱ���Ǹ���ǰ����
		boolean after = instance2.after(instance);
		boolean before = instance2.before(instance);
		int compareTo = instance2.compareTo(instance);
		Date time2 = instance.getTime();
		boolean after2 = instance.after(instance2);
		
//		���ݴ�����ֶ����ã����ᳬ�����ֵ  �����������������
		instance.set(Calendar.DAY_OF_MONTH, 31);
		instance.set(1998, 12, 31);//����������   ʱ������ʱ������  ��ʱ������
		instance.set(1998, 12, 31,13,50,23);//����������  ʱ����
		instance.setFirstDayOfWeek(Calendar.MONDAY);   //����һ�ܵĵ�һ�����ܼ�
		instance.setLenient(false);    //�Ƿ����   Ĭ�Ϸ�
		instance.setMinimalDaysInFirstWeek(1);   //������ĵ�һ����Ҫ������   
		instance.setTimeInMillis(System.nanoTime());//���ݺ���ֵ����ʵ��ʱ��
		instance.setTimeZone(timeZone);     //����ʱ��
		
		
//		��������                   50���� 30��   ��5
		instance.setWeekDate(50, 30, 5);
		int weekYear2 = instance.getWeekYear();//50����
		Date time3 = instance.getTime();
//		�Ƿ�����
		boolean lenient = instance.isLenient();       //�Ƿ��ǿ��ɵ�   Ĭ�Ͽ���
		boolean set = instance.isSet(Calendar.DAY_OF_MONTH);   //�Ƿ����������е���  
		boolean weekDateSupported = instance.isWeekDateSupported(); //�Ƿ�֧������
//		������õ��ֶΡ�
		instance.clear(Calendar.MONTH);
//		���ȫ���ֶΣ���Ϊ��ʼ1970��
		instance.clear();
	}

	private static void getInstance() {
		Calendar instance = Calendar.getInstance();
		Calendar instance2 = Calendar.getInstance(Locale.TAIWAN);
		Calendar instance3 = Calendar.getInstance(TimeZone.getDefault());
		Calendar instance4 = Calendar.getInstance(TimeZone.getDefault(), Locale.TAIWAN);
	}

}
