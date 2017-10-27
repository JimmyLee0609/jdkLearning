package util.calendar;

import java.time.Instant;
import java.util.Date;

public class DateTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Date date = new Date();
		// Date�ķ���������Calendar������ˡ�
		int date2 = date.getDate();
		int month = date.getMonth();
		int year = date.getYear();
		String gmtString = date.toGMTString();
		long utc = date.UTC(1999, 12, 01, 2, 30, 50);
		String localeString = date.toLocaleString();

		long time = date.getTime(); // ��ȡ��ǰʱ��ĺ���ֵ����1970���׼��ƫ����
		date.setTime(System.currentTimeMillis()); // ���ݴ���ĺ���ֵ �������ʱ��
		Instant instant = date.toInstant(); // ת��ΪInstant��
		boolean before = date.before(new Date()); // �Ƚ��Ƿ�������֮ǰ
		boolean after = date.after(new Date()); // �Ƚ��Ƿ�������֮��
		Date from = Date.from(instant); // �Ӵ����Instant����ת����Date
		// �����ַ���ת����ʱ��
		long parse = Date.parse("Sat, 12 Aug 1995 13:30:00 GMT");
		System.out.println(parse);
	}

}
