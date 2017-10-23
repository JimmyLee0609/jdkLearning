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
//		获取可用的区域
		Locale[] availableLocales = SimpleDateFormat.getAvailableLocales();
//		获取日期实例
		DateFormat dateInstance = SimpleDateFormat.getDateInstance();
//		根据区域获取日期实例
		DateFormat dateInstance2 = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, Locale.CHINA);
//		获取时间实例
		DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
//		根据区域   获取时间实例
		DateFormat dateTimeInstance2 = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.LONG, SimpleDateFormat.LONG, Locale.CHINA);
//		获取时间日期实例
		DateFormat instance = SimpleDateFormat.getInstance();//yy-M-d ah:mm
//		获取时间实例
		DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
//		根据区域      获取时间实例
		DateFormat timeInstance2 = SimpleDateFormat.getTimeInstance(SimpleDateFormat.LONG, Locale.CHINA);
		
		
		
//		解析日期
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
//		获取当前解析模式
		String pattern = simpleDateFormat.toPattern();//yyyy/MM/dd HH:mm:ss z
//		获取当前本地化解析模式
		String localizedPattern = simpleDateFormat.toLocalizedPattern();//aaaa/nn/jj HH:mm:ss z
		
//		simpleDateFormat.applyLocalizedPattern(localizedPattern);
//		获取日历
		Calendar calendar = simpleDateFormat.getCalendar();
		
//		获取时区
		TimeZone timeZone = simpleDateFormat.getTimeZone();
//		返回在 100 年周期内被解释的两位数字年份的开始日期。 
		Date get2DigitYearStart = simpleDateFormat.get2DigitYearStart();//Thu Oct 21 14:44:32 GMT+08:00 1937
//		获取数字格式  保留位数
		NumberFormat numberFormat = simpleDateFormat.getNumberFormat();
		
		boolean lenient = simpleDateFormat.isLenient();//true  判断日期/时间解析是否为不严格的。 
//	格式化日期
		StringBuffer format = simpleDateFormat.format(
				new Date(), new StringBuffer("test"), 
				new FieldPosition(SimpleDateFormat.YEAR_FIELD));//test2017/10/21 14:44:32 GMT+08:00
		
		Date parse = simpleDateFormat.parse("2017/09/10 08:20:20 GMT+08:00");
		calendar.roll(Calendar.YEAR, 2);//不会发生联动反映
//		新建模式用于解析实际的日期时间
		simpleDateFormat.applyPattern("yyyy/MM/dd 'at' hh:mm:ss");
		boolean lenient2 = simpleDateFormat.isLenient();
		String format2 = simpleDateFormat.format(parse);
		int v=0;
	}

	@SuppressWarnings("unused")
	private static void parseSimpleDate() throws ParseException {
//		指定解析模式    和区域   新建类
//		注意   解析时   比方说MONDAY   AM   下午  等有区域划分的表示，需要和指定的区域属性相符，才能解析。否则抛异常
//		获取对应区域的日期格式样式
		dateFormatSimple();
		SimpleDateFormat simpleDateFormat 
			= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z",Locale.CHINA);
		Date parse = simpleDateFormat.parse("2015/3/5 12:53:30 GMT+09:00");
		
//		指定解析模式    日期样式来创建类
		SimpleDateFormat simpleDateFormat2 
			= new SimpleDateFormat("yyyy-MM-dd hh-mm-ss a X",  
					DateFormatSymbols.getInstance(Locale.UK));
//		解析的字段需要符合解析模式      区域属性的特点    上面的UK  变成CHINA   PM就要变成  下午  才能解析
		Date parse2 = simpleDateFormat2.parse("2017-03-12 07-23-56 PM +09:00");//Sun Mar 12 06:23:56 GMT+08:00 2017
	
//		不标准的模式在建类时被忽略  'at'被忽略了   建类后  重新建模式就可以用.
		SimpleDateFormat simpleDateFormat3 
		= new SimpleDateFormat("yyyy-MM-dd 'at' hh-mm-ss a X",  
				DateFormatSymbols.getInstance(Locale.UK));
		Date parse3 = simpleDateFormat2.parse("2017-03-12 07-23-56 PM +09:00");
//		将目前的模式转化为字符串
		String pattern = simpleDateFormat2.toPattern();//yyyy-MM-dd hh-mm-ss a X
//		新建一个日期模式传给类
		simpleDateFormat2.applyPattern("yyyy-MM-dd 'at' hh-mm-ss a X");
//		格式化日期
		String format = simpleDateFormat2.format(parse3);//2017-03-12 at 06-23-56 PM +08
//		使用新模式解析日期
		Date parse4 = simpleDateFormat2.parse("2017-03-12 at 06-23-56 PM +08");//Sun Mar 12 18:23:56 GMT+08:00 2017
		
		System.out.println(parse2);		
	}

	@SuppressWarnings("unused")
	private static void dateFormatSimple() {
//		时间日期样式    星期   月份   区域不一样,表示不一样
		DateFormatSymbols instance2 = DateFormatSymbols.getInstance();
		DateFormatSymbols instance3 = DateFormatSymbols.getInstance(Locale.UK);
		
		String[] months = instance3.getMonths();//[January, February, March, April, May, June, July, August, September, October, November, December, ]
		String[] shortMonths = instance3.getShortMonths();//[Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec, ]

		String[] amPmStrings = instance3.getAmPmStrings();//[AM, PM]
		
		String[] shortWeekdays = instance3.getShortWeekdays();//[, Sun, Mon, Tue, Wed, Thu, Fri, Sat]
		String[] weekdays = instance3.getWeekdays();//[, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday]
	
		String[] eras = instance3.getEras();//[BC, AD]
		String[][] zoneStrings = instance3.getZoneStrings();
		String localPatternChars = instance3.getLocalPatternChars();//默认的 GyMdkHmsSEDFwWahKzZ
			
	}

	@SuppressWarnings("unused")
	private static void dateFormat() throws ParseException {
		Locale[] availableLocales = DateFormat.getAvailableLocales();	
		DateFormat instance = DateFormat.getInstance();//yy-M-d ah:mm
		DateFormat timeInstance = DateFormat.getTimeInstance();//pattern  H:mm:ss
		DateFormat dateInstance = DateFormat.getDateInstance();//pattern  yyyy-M-d
		DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();//pattern yyyy-M-d H:mm:ss

//		指定日期的模式  FULL、LONG、MEDIUM 和 SHORT，   指定日期的区域
//		获取的实例会有自己默认的Pattern.   解析需要符合内置的patter才能进行。而且不能改变pattern
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
				new StringBuffer("测试日期"), 
				new FieldPosition(1));//测试日期17-10-21
		
		int c=0;
	}

	@SuppressWarnings("unused")
	private static void testDateTime() {
		DateFormat dateTimeInstance2 = DateFormat.getDateTimeInstance(
				DateFormat.SHORT, DateFormat.SHORT, Locale.CHINA);		
		
		String format = dateTimeInstance2.format(new Date());//17-10-21 下午12:37
		
		StringBuffer format2 = dateTimeInstance2.format(
				Calendar.getInstance().getTime(),
				new StringBuffer("测试时间日期"), 
				new FieldPosition(1));//测试时间日期17-10-21 下午12:37
		int c=0;
	}

	@SuppressWarnings("unused")
	private static void timeTest() throws ParseException {
//		这个类获取的实例是SimpleDateFormat,   没有设定pattern没办法解析日期，只能格式化日期
		DateFormat timeInstance2 = DateFormat.getTimeInstance(
				DateFormat.SHORT, Locale.CHINA);		
		
		String format = timeInstance2.format(new Date());//下午12:21
		timeInstance2.setTimeZone(TimeZone.getTimeZone("GMT+09:00"));
		String format2 = timeInstance2.format(new Date());//下午1:21
		boolean lenient = timeInstance2.isLenient();//true
		NumberFormat numberFormat = timeInstance2.getNumberFormat();
		TimeZone timeZone = timeInstance2.getTimeZone();//GMT+09:00
		AttributedCharacterIterator formatToCharacterIterator 
			= timeInstance2.formatToCharacterIterator(new Date());//下午1:21
		StringBuffer format3 = timeInstance2.format(
				new Date(), new StringBuffer("测试时间"), new FieldPosition(1));//测试时间下午1:24
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
				new StringBuffer("测试百分比"), 
				new FieldPosition(1));//测试百分比446,600%
		Number parse = percentInstance.parse("53.5%");//0.535
		Number parse2 = percentInstance.parse("wo53%",new ParsePosition(2));//0.53
		
		int c=0;
	}

	@SuppressWarnings("unused")
	private static void integerFormat() throws ParseException {
//		获取整形数字格式化 
		NumberFormat integerInstance = NumberFormat.getIntegerInstance();
//		根据区域属性获取  整形数字格式化
		NumberFormat integerInstance2 = NumberFormat.getIntegerInstance(Locale.JAPAN);
		
		String format = integerInstance.format(5321);//5,321
		String format2 = integerInstance.format(53.2416);//53
		Number parse = integerInstance.parse("5618.1651");//5618
		Number parse2 = integerInstance.parse("adn4562.253", new ParsePosition(3));//4562
		
//		设置整形  最小保留位数
		integerInstance.setMinimumIntegerDigits(2);
//		设置整形  最大保留位数
		integerInstance.setMaximumIntegerDigits(3);
		
		String format1 = integerInstance.format(5321);//321
		String format3 = integerInstance.format(53.2416);//53
		StringBuffer format4 = integerInstance.format(
				new BigInteger("54916"), 
				new StringBuffer("这是一个测试"), 
				new FieldPosition(5));//这是一个测试916
		
//		设置了保留位数对解析没有影响
		Number parse1 = integerInstance.parse("5618.1651");//5618
		Number parse3 = integerInstance.parse("adn4562.253", new ParsePosition(3));//4562
		int c=0;
	}

	@SuppressWarnings("unused")
	private static void currencyFormat() throws ParseException {
//		获取货币的数字格式
		NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
//		根据区域属性获取货币的数字格式
		NumberFormat currencyInstance2 = NumberFormat.getCurrencyInstance(Locale.CHINA);		
//		将传入的数字进行格式化
		String format = currencyInstance.format(5.2);//将double 格式化￥5.20
		String format3 = currencyInstance.format(2);//将long 格式化 ￥2.00
//		将Number的子类进行格式化，并添加到一个缓冲区
		StringBuffer format2 = currencyInstance.format(
				5, new StringBuffer(".2"), new FieldPosition(1));//.2￥5.00
		
		StringBuffer format4 = currencyInstance.format(
				new BigInteger("12323"), new StringBuffer("测试添加格式"), 
				new FieldPosition(NumberFormat.Field.DECIMAL_SEPARATOR));//测试添加格式￥12,323.00
		
		AttributedCharacterIterator formatToCharacterIterator 
					= currencyInstance.formatToCharacterIterator(753861);//￥753,861.00
		
		
//		获取，设置  当前的   货币		
		Currency currency = currencyInstance.getCurrency();//CNY
		currencyInstance.setCurrency(Currency.getInstance(Locale.CHINA));
//		获取小数部分所允许的最大位数
		int maximumFractionDigits = currencyInstance.getMaximumFractionDigits();
//		设置小数部分所允许的最大位数
		currencyInstance.setMaximumFractionDigits(6);
//		获取小数部分所允许的最小位数
		int minimumFractionDigits = currencyInstance.getMinimumFractionDigits();
//		设置小数部分所允许的最小位数
		currencyInstance.setMinimumFractionDigits(5);
		String format5 = currencyInstance.format(2.2);//小数部分最小保留5位￥2.20000
		String format6 = currencyInstance.format(2.222222222222222);//小数部分最大保留6位其他舍去￥2.222222
		
//		返回整数部分所允许的最大位数
		int maximumIntegerDigits = currencyInstance.getMaximumIntegerDigits();
//		设置整数部分所允许的最大位数
		currencyInstance.setMaximumIntegerDigits(5);
//		返回整数部分所允许的最小位数
		int minimumIntegerDigits = currencyInstance.getMinimumIntegerDigits();
//		设置整数部分所允许的最小位数      1.1   -->001.1
		currencyInstance.setMinimumIntegerDigits(3);
		String format7 = currencyInstance.format(1.1);//整数部分最小保留3位数￥001.10000
		String format8 = currencyInstance.format(1111111111111.1);//整数部分最小保留5位数多余的舍去￥11,111.10000
		
//		获取设置    精确位的保留方式。 4舍5入还是其他
		RoundingMode roundingMode = currencyInstance.getRoundingMode();
		currencyInstance.setRoundingMode(RoundingMode.HALF_EVEN);
		String format9 = currencyInstance.format(5.2245235);//保留位数4舍5入   ￥005.224524
		currencyInstance.setRoundingMode(RoundingMode.DOWN);
		String format10 = currencyInstance.format(5.2245235);//保留位数后面小数舍去 ￥005.224523
		
		
		
		
//		设置此格式中是否使用分组。
//		默认 true如果使用了分组，则数 1234567 将被格式化为 "1,234,567"。
		boolean groupingUsed = currencyInstance.isGroupingUsed();
		currencyInstance.setGroupingUsed(true);
		currencyInstance.setGroupingUsed(false);
//		是否只为整数解析     English环境中1234.  遇到  .   就会停止截取
		boolean parseIntegerOnly = currencyInstance.isParseIntegerOnly();//默认  false
		currencyInstance.setParseIntegerOnly(false);
		
//		解析货币格式数字。
		Number parse = currencyInstance.parse("￥5.213");//默认从头开始解析
//		Number parse3 = currencyInstance.parse("$5.213");//检测到货币代码不对，会抛异常。
//		ParsePositon就是处理一个 int 的索引
		Number parse2 = currencyInstance.parse("￥52.2", new ParsePosition(0));//从指定索引位置开始截取
		
		Object parseObject = currencyInstance.parseObject("￥52.1135");//52.1135
		Object parseObject2 = currencyInstance.parseObject("asd￥53515.1561", new ParsePosition(3));//53515.1561
		
		currencyInstance.toString();
	}

}
