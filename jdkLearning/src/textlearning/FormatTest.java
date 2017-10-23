package textlearning;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FormatTest {

	public static void main(String[] args) throws ParseException {
		Attribute language = Format.Field.LANGUAGE;
		numberformat();
		dateFormat();
		simpleDateFormat();
	}

	@SuppressWarnings("unused")
	private static void simpleDateFormat() throws ParseException {
//		��ȡ���õ�����
		Locale[] availableLocales = SimpleDateFormat.getAvailableLocales();
//		��ȡ����ʵ��
		DateFormat dateInstance = SimpleDateFormat.getDateInstance();
//		���������ȡ����ʵ��
		DateFormat dateInstance2 = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, Locale.CHINA);
//		��ȡʱ��ʵ��
		DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
//		��������   ��ȡʱ��ʵ��
		DateFormat dateTimeInstance2 = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.LONG, SimpleDateFormat.LONG, Locale.CHINA);
//		��ȡʱ������ʵ��
		DateFormat instance = SimpleDateFormat.getInstance();//yy-M-d ah:mm
//		��ȡʱ��ʵ��
		DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
//		��������      ��ȡʱ��ʵ��
		DateFormat timeInstance2 = SimpleDateFormat.getTimeInstance(SimpleDateFormat.LONG, Locale.CHINA);
		
		
		
//		��������
		parseSimpleDate();
		
		simpleDateMethod();
		

		int v=0;
	}

	@SuppressWarnings("unused")
	private static void simpleDateMethod() throws ParseException {
		SimpleDateFormat simpleDateFormat 
		= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z",Locale.CHINA);
		
		
//		simpleDateFormat.applyLocalizedPattern("yyyy-MM-dd aa hh:mm:ss z");
		DateFormatSymbols dateFormatSymbols 
			= simpleDateFormat.getDateFormatSymbols();
//		��ȡ��ǰ����ģʽ
		String pattern = simpleDateFormat.toPattern();//yyyy/MM/dd HH:mm:ss z
//		��ȡ��ǰ���ػ�����ģʽ
		String localizedPattern = simpleDateFormat.toLocalizedPattern();//aaaa/nn/jj HH:mm:ss z
		
//		simpleDateFormat.applyLocalizedPattern(localizedPattern);
//		��ȡ����
		Calendar calendar = simpleDateFormat.getCalendar();
		
//		��ȡʱ��
		TimeZone timeZone = simpleDateFormat.getTimeZone();
//		������ 100 �������ڱ����͵���λ������ݵĿ�ʼ���ڡ� 
		Date get2DigitYearStart = simpleDateFormat.get2DigitYearStart();//Thu Oct 21 14:44:32 GMT+08:00 1937
//		��ȡ���ָ�ʽ  ����λ��
		NumberFormat numberFormat = simpleDateFormat.getNumberFormat();
		
		boolean lenient = simpleDateFormat.isLenient();//true  �ж�����/ʱ������Ƿ�Ϊ���ϸ�ġ� 
//	��ʽ������
		StringBuffer format = simpleDateFormat.format(
				new Date(), new StringBuffer("test"), 
				new FieldPosition(SimpleDateFormat.YEAR_FIELD));//test2017/10/21 14:44:32 GMT+08:00
		
		Date parse = simpleDateFormat.parse("2017/09/10 08:20:20 GMT+08:00");
		calendar.roll(Calendar.YEAR, 2);//���ᷢ��������ӳ
//		�½�ģʽ���ڽ���ʵ�ʵ�����ʱ��
		simpleDateFormat.applyPattern("yyyy/MM/dd 'at' hh:mm:ss");
		boolean lenient2 = simpleDateFormat.isLenient();
		String format2 = simpleDateFormat.format(parse);
		int v=0;
	}

	@SuppressWarnings("unused")
	private static void parseSimpleDate() throws ParseException {
//		ָ������ģʽ    ������   �½���
//		ע��   ����ʱ   �ȷ�˵MONDAY   AM   ����  �������򻮷ֵı�ʾ����Ҫ��ָ��������������������ܽ������������쳣
//		��ȡ��Ӧ��������ڸ�ʽ��ʽ
		dateFormatSimple();
		SimpleDateFormat simpleDateFormat 
			= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z",Locale.CHINA);
		Date parse = simpleDateFormat.parse("2015/3/5 12:53:30 GMT+09:00");
		
//		ָ������ģʽ    ������ʽ��������
		SimpleDateFormat simpleDateFormat2 
			= new SimpleDateFormat("yyyy-MM-dd hh-mm-ss a X",  
					DateFormatSymbols.getInstance(Locale.UK));
//		�������ֶ���Ҫ���Ͻ���ģʽ      �������Ե��ص�    �����UK  ���CHINA   PM��Ҫ���  ����  ���ܽ���
		Date parse2 = simpleDateFormat2.parse("2017-03-12 07-23-56 PM +09:00");//Sun Mar 12 06:23:56 GMT+08:00 2017
	
//		����׼��ģʽ�ڽ���ʱ������  'at'��������   �����  ���½�ģʽ�Ϳ�����.
		SimpleDateFormat simpleDateFormat3 
		= new SimpleDateFormat("yyyy-MM-dd 'at' hh-mm-ss a X",  
				DateFormatSymbols.getInstance(Locale.UK));
		Date parse3 = simpleDateFormat2.parse("2017-03-12 07-23-56 PM +09:00");
//		��Ŀǰ��ģʽת��Ϊ�ַ���
		String pattern = simpleDateFormat2.toPattern();//yyyy-MM-dd hh-mm-ss a X
//		�½�һ������ģʽ������
		simpleDateFormat2.applyPattern("yyyy-MM-dd 'at' hh-mm-ss a X");
//		��ʽ������
		String format = simpleDateFormat2.format(parse3);//2017-03-12 at 06-23-56 PM +08
//		ʹ����ģʽ��������
		Date parse4 = simpleDateFormat2.parse("2017-03-12 at 06-23-56 PM +08");//Sun Mar 12 18:23:56 GMT+08:00 2017
		
		System.out.println(parse2);		
	}

	@SuppressWarnings("unused")
	private static void dateFormatSimple() {
//		ʱ��������ʽ    ����   �·�   ����һ��,��ʾ��һ��
		DateFormatSymbols instance2 = DateFormatSymbols.getInstance();
		DateFormatSymbols instance3 = DateFormatSymbols.getInstance(Locale.UK);
		
		String[] months = instance3.getMonths();//[January, February, March, April, May, June, July, August, September, October, November, December, ]
		String[] shortMonths = instance3.getShortMonths();//[Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec, ]

		String[] amPmStrings = instance3.getAmPmStrings();//[AM, PM]
		
		String[] shortWeekdays = instance3.getShortWeekdays();//[, Sun, Mon, Tue, Wed, Thu, Fri, Sat]
		String[] weekdays = instance3.getWeekdays();//[, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday]
	
		String[] eras = instance3.getEras();//[BC, AD]
		String[][] zoneStrings = instance3.getZoneStrings();
		String localPatternChars = instance3.getLocalPatternChars();//Ĭ�ϵ� GyMdkHmsSEDFwWahKzZ
			
	}

	@SuppressWarnings("unused")
	private static void dateFormat() throws ParseException {
		Locale[] availableLocales = DateFormat.getAvailableLocales();	
		DateFormat instance = DateFormat.getInstance();//yy-M-d ah:mm
		DateFormat timeInstance = DateFormat.getTimeInstance();//pattern  H:mm:ss
		DateFormat dateInstance = DateFormat.getDateInstance();//pattern  yyyy-M-d
		DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();//pattern yyyy-M-d H:mm:ss

//		ָ�����ڵ�ģʽ  FULL��LONG��MEDIUM �� SHORT��   ָ�����ڵ�����
//		��ȡ��ʵ�������Լ�Ĭ�ϵ�Pattern.   ������Ҫ�������õ�patter���ܽ��С����Ҳ��ܸı�pattern
		DateFormat timeInstance2 = DateFormat.getTimeInstance(
				DateFormat.SHORT, Locale.UK);//pattern     ah:mm
		DateFormat dateInstance2 = DateFormat.getDateInstance(
				DateFormat.SHORT, Locale.UK);//pattern     yy-M-d
		DateFormat dateTimeInstance2 = DateFormat.getDateTimeInstance(
				DateFormat.SHORT, DateFormat.SHORT, Locale.UK);//pattern  yy-M-d ah:mm

		Date parse = timeInstance2.parse("4:50");//Thu Jan 01 04:50:00 GMT+08:00 1970
		Date parse2 = dateInstance2.parse("03/12/15");//Sun Jul 03 00:00:00 GMT+08:00 2005
		Date parse3 = dateTimeInstance2.parse("12/05/12 02:20");//Sat May 12 02:20:00 GMT+08:00 2012
		timeTest();
		DateTest();
		testDateTime();
		
	int c=0;
	
	}

	@SuppressWarnings("unused")
	private static void DateTest() {
		DateFormat dateInstance2 = DateFormat.getDateInstance(
				DateFormat.SHORT, Locale.CHINA);		
		String format = dateInstance2.format(new Date());//17-10-21
		StringBuffer format2 = dateInstance2.format(
				new Date(),
				new StringBuffer("��������"), 
				new FieldPosition(1));//��������17-10-21
		
		int c=0;
	}

	@SuppressWarnings("unused")
	private static void testDateTime() {
		DateFormat dateTimeInstance2 = DateFormat.getDateTimeInstance(
				DateFormat.SHORT, DateFormat.SHORT, Locale.CHINA);		
		
		String format = dateTimeInstance2.format(new Date());//17-10-21 ����12:37
		
		StringBuffer format2 = dateTimeInstance2.format(
				Calendar.getInstance().getTime(),
				new StringBuffer("����ʱ������"), 
				new FieldPosition(1));//����ʱ������17-10-21 ����12:37
		int c=0;
	}

	@SuppressWarnings("unused")
	private static void timeTest() throws ParseException {
//		������ȡ��ʵ����SimpleDateFormat,   û���趨patternû�취�������ڣ�ֻ�ܸ�ʽ������
		DateFormat timeInstance2 = DateFormat.getTimeInstance(
				DateFormat.SHORT, Locale.CHINA);		
		
		String format = timeInstance2.format(new Date());//����12:21
		timeInstance2.setTimeZone(TimeZone.getTimeZone("GMT+09:00"));
		String format2 = timeInstance2.format(new Date());//����1:21
		boolean lenient = timeInstance2.isLenient();//true
		NumberFormat numberFormat = timeInstance2.getNumberFormat();
		TimeZone timeZone = timeInstance2.getTimeZone();//GMT+09:00
		AttributedCharacterIterator formatToCharacterIterator 
			= timeInstance2.formatToCharacterIterator(new Date());//����1:21
		StringBuffer format3 = timeInstance2.format(
				new Date(), new StringBuffer("����ʱ��"), new FieldPosition(1));//����ʱ������1:24
		int c=0;
	}

	@SuppressWarnings("unused")
	private static void numberformat() throws ParseException {
		Locale[] availableLocales = NumberFormat.getAvailableLocales();
		Class <? > c=NumberFormat.class;
		currencyFormat();
		integerFormat();
		percentFormat();
	
	}

	@SuppressWarnings("unused")
	private static void percentFormat() throws ParseException {
		NumberFormat percentInstance = NumberFormat.getPercentInstance();
		NumberFormat percentInstance2 = NumberFormat.getPercentInstance(Locale.UK);	
		
		String format = percentInstance.format(53.5);//5,350%
		String format2 = percentInstance.format(54894l);//5,489,400%
		
		StringBuffer format3 = percentInstance.format(
				new BigInteger("4466"), 
				new StringBuffer("���԰ٷֱ�"), 
				new FieldPosition(1));//���԰ٷֱ�446,600%
		Number parse = percentInstance.parse("53.5%");//0.535
		Number parse2 = percentInstance.parse("wo53%",new ParsePosition(2));//0.53
		
		int c=0;
	}

	@SuppressWarnings("unused")
	private static void integerFormat() throws ParseException {
//		��ȡ�������ָ�ʽ�� 
		NumberFormat integerInstance = NumberFormat.getIntegerInstance();
//		�����������Ի�ȡ  �������ָ�ʽ��
		NumberFormat integerInstance2 = NumberFormat.getIntegerInstance(Locale.JAPAN);
		
		String format = integerInstance.format(5321);//5,321
		String format2 = integerInstance.format(53.2416);//53
		Number parse = integerInstance.parse("5618.1651");//5618
		Number parse2 = integerInstance.parse("adn4562.253", new ParsePosition(3));//4562
		
//		��������  ��С����λ��
		integerInstance.setMinimumIntegerDigits(2);
//		��������  �����λ��
		integerInstance.setMaximumIntegerDigits(3);
		
		String format1 = integerInstance.format(5321);//321
		String format3 = integerInstance.format(53.2416);//53
		StringBuffer format4 = integerInstance.format(
				new BigInteger("54916"), 
				new StringBuffer("����һ������"), 
				new FieldPosition(5));//����һ������916
		
//		�����˱���λ���Խ���û��Ӱ��
		Number parse1 = integerInstance.parse("5618.1651");//5618
		Number parse3 = integerInstance.parse("adn4562.253", new ParsePosition(3));//4562
		int c=0;
	}

	@SuppressWarnings("unused")
	private static void currencyFormat() throws ParseException {
//		��ȡ���ҵ����ָ�ʽ
		NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
//		�����������Ի�ȡ���ҵ����ָ�ʽ
		NumberFormat currencyInstance2 = NumberFormat.getCurrencyInstance(Locale.CHINA);		
//		����������ֽ��и�ʽ��
		String format = currencyInstance.format(5.2);//��double ��ʽ����5.20
		String format3 = currencyInstance.format(2);//��long ��ʽ�� ��2.00
//		��Number��������и�ʽ��������ӵ�һ��������
		StringBuffer format2 = currencyInstance.format(
				5, new StringBuffer(".2"), new FieldPosition(1));//.2��5.00
		
		StringBuffer format4 = currencyInstance.format(
				new BigInteger("12323"), new StringBuffer("������Ӹ�ʽ"), 
				new FieldPosition(NumberFormat.Field.DECIMAL_SEPARATOR));//������Ӹ�ʽ��12,323.00
		
		AttributedCharacterIterator formatToCharacterIterator 
					= currencyInstance.formatToCharacterIterator(753861);//��753,861.00
		
		
//		��ȡ������  ��ǰ��   ����		
		Currency currency = currencyInstance.getCurrency();//CNY
		currencyInstance.setCurrency(Currency.getInstance(Locale.CHINA));
//		��ȡС����������������λ��
		int maximumFractionDigits = currencyInstance.getMaximumFractionDigits();
//		����С����������������λ��
		currencyInstance.setMaximumFractionDigits(6);
//		��ȡС���������������Сλ��
		int minimumFractionDigits = currencyInstance.getMinimumFractionDigits();
//		����С���������������Сλ��
		currencyInstance.setMinimumFractionDigits(5);
		String format5 = currencyInstance.format(2.2);//С��������С����5λ��2.20000
		String format6 = currencyInstance.format(2.222222222222222);//С�����������6λ������ȥ��2.222222
		
//		����������������������λ��
		int maximumIntegerDigits = currencyInstance.getMaximumIntegerDigits();
//		����������������������λ��
		currencyInstance.setMaximumIntegerDigits(5);
//		���������������������Сλ��
		int minimumIntegerDigits = currencyInstance.getMinimumIntegerDigits();
//		���������������������Сλ��      1.1   -->001.1
		currencyInstance.setMinimumIntegerDigits(3);
		String format7 = currencyInstance.format(1.1);//����������С����3λ����001.10000
		String format8 = currencyInstance.format(1111111111111.1);//����������С����5λ���������ȥ��11,111.10000
		
//		��ȡ����    ��ȷλ�ı�����ʽ�� 4��5�뻹������
		RoundingMode roundingMode = currencyInstance.getRoundingMode();
		currencyInstance.setRoundingMode(RoundingMode.HALF_EVEN);
		String format9 = currencyInstance.format(5.2245235);//����λ��4��5��   ��005.224524
		currencyInstance.setRoundingMode(RoundingMode.DOWN);
		String format10 = currencyInstance.format(5.2245235);//����λ������С����ȥ ��005.224523
		
		
		
		
//		���ô˸�ʽ���Ƿ�ʹ�÷��顣
//		Ĭ�� true���ʹ���˷��飬���� 1234567 ������ʽ��Ϊ "1,234,567"��
		boolean groupingUsed = currencyInstance.isGroupingUsed();
		currencyInstance.setGroupingUsed(true);
		currencyInstance.setGroupingUsed(false);
//		�Ƿ�ֻΪ��������     English������1234.  ����  .   �ͻ�ֹͣ��ȡ
		boolean parseIntegerOnly = currencyInstance.isParseIntegerOnly();//Ĭ��  false
		currencyInstance.setParseIntegerOnly(false);
		
//		�������Ҹ�ʽ���֡�
		Number parse = currencyInstance.parse("��5.213");//Ĭ�ϴ�ͷ��ʼ����
//		Number parse3 = currencyInstance.parse("$5.213");//��⵽���Ҵ��벻�ԣ������쳣��
//		ParsePositon���Ǵ���һ�� int ������
		Number parse2 = currencyInstance.parse("��52.2", new ParsePosition(0));//��ָ������λ�ÿ�ʼ��ȡ
		
		Object parseObject = currencyInstance.parseObject("��52.1135");//52.1135
		Object parseObject2 = currencyInstance.parseObject("asd��53515.1561", new ParsePosition(3));//53515.1561
		
		currencyInstance.toString();
	}

}
