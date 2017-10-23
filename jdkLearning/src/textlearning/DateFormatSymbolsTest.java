package textlearning;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatSymbolsTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat instance = new SimpleDateFormat();
		DateFormatSymbols dateFormatSymbols = instance.getDateFormatSymbols();
		instance.applyPattern("G yyyy-MM-dd Y L W D w F E u a HH kk-KK hh:mm:ss S Z z X");
		String format = instance.format(new Date());//��Ԫ 2017-10-21 3 294 42 3 ������ ���� 15 03:33:56 76 
		//"SimpleDate �е���ĸ����     G yyyy-MM-dd Y L W D w F E u a HH kk-KK hh:mm:ss S Z z X
//DateFormatSymbols�е���ĸ����  G aaaa-nn-   jj  Y L W D w F E u x HH kk-KK hh:mm:ss S Z z X
//		���������� am/pm  ��������ͬ
//			yyyy-MM-dd a    simpleDate
//			aaaa-nn 	-jj  x    DateFormatSymbols
		String localizedPattern = instance.toLocalizedPattern();
		String localPatternChars = dateFormatSymbols.getLocalPatternChars();//GanjkHmsSEDFwWxhKzZ
		String[] weekdays = dateFormatSymbols.getWeekdays();
		
//		��simpleDate�е�formatSymbols��ߵ�Ԫ�ؽ����޸Ļ�Ӱ��simpleDate��Ч��
		dateFormatSymbols.setWeekdays(new String[]{"","��1","��2","��3","����","����","����","����"});
		dateFormatSymbols.setShortWeekdays(new String[]{"","1","2","3","4","5","6","7"});
		
		instance.setDateFormatSymbols(dateFormatSymbols);
//		�����Զ�����ʽ��ģʽ�����ַ���
		instance.applyLocalizedPattern("G aaaa nn jj W D w F E x HH:mm:ss");
		String format2 = instance.format(new Date());//��Ԫ 2017 10 21 3 294 42 3 7 ���� 15:49:10
//		��������ڸı���    ����ʱ   �����Զ������ʽ������
		Date parse = instance.parse("��Ԫ 2017 10 21 3 254 24 3 7 ���� 15:50:10");//Sat Oct 21 15:50:10 GMT+08:00 2017
	
		instance.applyLocalizedPattern("aa nn jj HH:mm");
		Date parse2 = instance.parse("17 10 21 15:52");//Sat Oct 21 15:52:00 GMT+08:00 2017
		
//		�����Զ������������������ģʽ������   �Զ���Ľ����ڱ�� ��  1 2 3 4 5 6 7 ������Ǹı�����ʽ
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",dateFormatSymbols);
		simpleDateFormat.applyLocalizedPattern("G aaaa nn jj W D w F E x HH:mm:ss");
		String format3 = simpleDateFormat.format(new Date());//��Ԫ 2017 10 21 3 294 42 3 7 ���� 15:49:10
		
		int c=0;
	}

}
