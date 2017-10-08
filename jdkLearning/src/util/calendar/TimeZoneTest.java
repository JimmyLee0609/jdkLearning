package util.calendar;

import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimeZoneTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		timeZoneMethod();
//		ʵ������ʵ����������÷�һ��
		TimeZone zone = SimpleTimeZone.getDefault();//��ȡĬ�ϵ�TimeZone
		String displayName = zone.getDisplayName();   //��ȡ��ʽ������
		String id = zone.getID();									//��ȡID
		String displayName2 = zone.getDisplayName(Locale.CANADA); //�趨��������������ʾ��
		String displayName3 = zone.getDisplayName(false, TimeZone.LONG, Locale.CANADA);
		int dstSavings = zone.getDSTSavings();      //��ʢ��ʱ���ƫ��
		int offset = zone.getOffset(System.currentTimeMillis());   //��ȡָ�����ڵ�ʱ��ƫ��
		int offset2 = zone.getOffset(Calendar.ERA, 2017, 10, 7, 6, 50);  
		zone.setRawOffset(18000);       //�趨ƫ����
		int rawOffset = zone.getRawOffset();//���ؾ����趨��ƫ����
		String displayName4 = zone.getDisplayName();
	}

	@SuppressWarnings("unused")
	private static void timeZoneMethod() {
		//��ȡ���е�ϵͳ�������ṩ��TimeZoneId
				String[] availableIDs = TimeZone.getAvailableIDs();
				for (String string : availableIDs) {
					System.out.println(string);
				}
				//��ȡĬ�ϵ�TimeZone  ����ϵͳ�趨��ȡ
				TimeZone default1 = TimeZone.getDefault();
				//��ȡʱ��id
				String id = default1.getID();
				//����ʱ��id��ȡʱ��
				TimeZone timeZone = TimeZone.getTimeZone("America/Toronto");
				//��ȡʱ������
				String displayName = default1.getDisplayName();
//				��ȡʱ������                                                         �Ƿ�����ʱ       ��д             ���ػ�����                          
				String displayName2 = timeZone.getDisplayName(false, TimeZone.LONG, Locale.CHINESE);
				String displayName3 = timeZone.getDisplayName(true, TimeZone.LONG, Locale.CHINESE);
				
				int dstSavings = default1.getDSTSavings();
				//�Ƿ�����ͬ�Ĺ���
				boolean hasSameRules = default1.hasSameRules(TimeZone.getTimeZone("Asia/Taipei"));
				//��ָ�����ڵı�׼ʱ�䷵�ش˵�����ƫ���������������ʱ�������
				int offset = default1.getOffset(System.currentTimeMillis());
//				�򲻴˵�����ƫ����
				int rawOffset = default1.getRawOffset();
//				�����Ƿ�ʹ������ʱ
				boolean observesDaylightTime = timeZone.observesDaylightTime();
//				�Ƿ�ʹ������ʱ
				boolean useDaylightTime = timeZone.useDaylightTime();
		
	}

}
