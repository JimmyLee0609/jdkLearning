package time;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

import java.text.Format;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.util.Locale;
import java.util.Set;

public class DateTimeFormatterTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 模式解析yyyy-MM-dd等
		pattern();

		TemporalQuery<Period> parsedExcessDays = DateTimeFormatter.parsedExcessDays();
		TemporalQuery<Boolean> parsedLeapSecond = DateTimeFormatter.parsedLeapSecond();
		DateTimeFormatter ofLocalizedTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
		Chronology chronology = ofLocalizedTime.getChronology();// ISO
		DecimalStyle decimalStyle = ofLocalizedTime.getDecimalStyle();// DecimalStyle[0+-.]
		Locale locale = ofLocalizedTime.getLocale();// zh_CN
		Set<TemporalField> resolverFields = ofLocalizedTime.getResolverFields();// null
		ResolverStyle resolverStyle = ofLocalizedTime.getResolverStyle();// SMART
		ZoneId zone = ofLocalizedTime.getZone();
		Format format = ofLocalizedTime.toFormat();
		String string = ofLocalizedTime.toString();

		DateTimeFormatter withZone = ofLocalizedTime.withZone(ZoneId.of("GMT+8"));

		String localizedDateTimePattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(FormatStyle.FULL,
				FormatStyle.FULL, Chronology.ofLocale(Locale.FRANCE), Locale.FRANCE);

		int v = 0;

		CharSequence text = "2017-12-03".subSequence(0, 10);
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
				.appendLiteral('-').appendValue(MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(DAY_OF_MONTH, 2)
				.toFormatter(Locale.CHINA);
		LocalDate parse2 = LocalDate.parse(text, formatter);

		/*
		 * CharSequence subSequence = "2011-12-03+01:00".subSequence(0, 16);
		 * DateTimeFormatter formatter3 = new
		 * DateTimeFormatterBuilder().parseCaseInsensitive().append(formatter)
		 * .appendOffsetId().toFormatter(Locale.CHINA); LocalDateTime parse4 =
		 * LocalDateTime.parse(subSequence,DateTimeFormatter.ISO_OFFSET_DATE);
		 */

		// 2017年12月03日
		DateTimeFormatter formatter2 = new DateTimeFormatterBuilder().appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
				.appendLiteral('年').appendValue(MONTH_OF_YEAR, 2).appendLiteral('月').appendValue(DAY_OF_MONTH, 2)
				.appendLiteral('日').toFormatter(Locale.CHINA);
		String format3 = parse2.format(formatter2);

		CharSequence subSequence = "18:20:35".subSequence(0, 8);
		LocalTime parse4 = LocalTime.parse(subSequence, DateTimeFormatter.ISO_LOCAL_TIME);

		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy MM dd HH mm ss");
		String text2 = "2015 12 05 12 25 35";
		LocalDateTime parse3 = LocalDateTime.parse(text2.subSequence(0, text2.length()), ofPattern);

		String ios = "20111203Z";// 末尾的Z可加可不加就是因为.optionalStart().appendOffset("+HHMMss",
									// "Z")
		DateTimeFormatter basicIsoDate2 = DateTimeFormatter.BASIC_ISO_DATE;
		LocalDate parse6 = LocalDate.parse(ios, basicIsoDate2);

		// 'Tue, 3 Jun 2008 11:05:30 GMT'
		String time = "Tue, 3 Jun 2008 11:05:30 GMT";
		DateTimeFormatter basicIsoDate = DateTimeFormatter.RFC_1123_DATE_TIME;
		LocalDateTime parse5 = LocalDateTime.parse(time, basicIsoDate);

		myStyle();

	}

	@SuppressWarnings("unused")
	private static void myStyle() {
		String text = "15-05-23";
		String text23 = "15-05-23T12:23:05.5647";
		String []time3 = text23.split("T");
		DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
				.appendValue(ChronoField.YEAR, 2, 2, SignStyle.EXCEEDS_PAD).appendLiteral("-")
				.appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral("-").appendValue(ChronoField.DAY_OF_MONTH, 2)
				.toFormatter();

		DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
				 .appendValue(HOUR_OF_DAY, 2)
	                .appendLiteral(':')
	                .appendValue(MINUTE_OF_HOUR, 2)
	                .optionalStart()
	                .appendLiteral(':')
	                .appendValue(SECOND_OF_MINUTE, 2)
	                .optionalStart()
	                .appendLiteral('.')
	                .appendFraction(NANO_OF_SECOND, 0, 9, true)
	                .toFormatter(Locale.CHINA);

		LocalDate parse = LocalDate.parse(time3[0],dateFormatter);
		LocalTime parse2 = LocalTime.parse(time3[1], timeFormatter);//Text '12:23:05:5647' could not be parsed, unparsed text found at index 8
		pattern();
		int c = 0;
	}

	@SuppressWarnings("unused")
	private static void pattern() {
		String text = "15-05-23T12:23:05:5647";
		String time = text.split("T")[1];
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("HH:mm:ss:n");
		LocalTime parse = LocalTime.parse(time, ofPattern); // 12:23:05.000005647
		
		datePattern();
		timePattern();
		dateTimePattern();
		String date="AD 2015/MAY/26/MonDay";
		
		
		int c=0;
	}

	private static void dateTimePattern() {
		ZoneId of3 = ZoneId.of("GMT+07:00");
		ZonedDateTime of4 = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), of3);
		format(of4, "MM/dd/yyyy HH:mm:ssXXX");// 10/14/2017 16:20:05+07:00(ZoneOffset)
		format(of4, "MM/dd/yyyy VV");// 10/14/2017 GMT+07:00(TimeZoneId)
//		format(of4, "MM/dd/yyyy VVV");// 三个V不行
//		format(of4, "MM/dd/yyyy V");// 一个V不行
		format(of4, "MM/dd/yyyy VV z");// 10/14/2017 GMT+07:00 GMT+07:00(TimeZoneName)
		format(of4, "MM/dd/yyyy VV Z");// 10/14/2017 GMT+07:00 +0700(ZoneOffset)
		format(of4, "MM/dd/yyyy VV O");// 10/14/2017 GMT+07:00 GMT+7(LocalZoneOffset)
		format(of4, "MM/dd/yyyy VV OOOO");//O只能是1或4个 10/14/2017 GMT+07:00 GMT+07:00
		format(of4, "MM/dd/yyyy VV X");// 10/14/2017 GMT+07:00 +07
		format(of4, "MM/dd/yyyy VV XX");// 10/14/2017 GMT+07:00 +0700
		format(of4, "MM/dd/yyyy VV XXX");// 10/14/2017 GMT+07:00 +0700
		format(of4, "MM/dd/yyyy VV x");// 10/14/2017 GMT+07:00 +07
		format(of4, "MM/dd/yyyy VV xx");//10/14/2017 GMT+07:00 +0700
		format(of4, "MM/dd/yyyy VV xxx");//10/14/2017 GMT+07:00 +0700
		format(of4, "d MMMM,    yyyy h:mm a");// 14 十月, 2017 4:20 下午
		format(of4, "[MM-dd-yyyy][ HH:mm:ss]");// 10-14-2017 16:20:05		
	}

	private static void timePattern() {
		LocalTime of2 = LocalTime.of(16, 20, 5, 785);
		LocalDateTime of = LocalDateTime.of(2016, 3, 4, 12, 52);
		format(of2, "HH:mm:ss");// 16:20:05
		format(of2, "KK:mm:ss a");// 04:20:05 下午
		format(of2, "kk:mm:ss a");// 16:20:05 下午
		format(of2, "hh:mm:ss a");// 04:20:05 下午
		format(of2, "hh:mm:ss-S:A:N a");// 04:20:05-0:58805000:58805000000785 下午
		format(of2, "A");// 58805000 现在的时间是一天的第几毫秒
		format(of2, "N");// 58805000000785 现在的时间是一天的第几纳秒
		format(of2, "hh:mm:ss:nnnn a");// 04:20:05:0785 下午
		format(of2, "hh:mm:ss:nnnnnnnn a");// 04:20:05:00000785 下午
		format(of2, "S");// 0 ??
		format(of2, "[MM-dd-yyyy][  HH:mm:ss]");// 16:20:05
		format(of, "[MM-dd-yyyy][  HH:mm:ss]");// 03-04-2016  12:52:00
		format(of, "[MM-dd-yyyy][ 'at' HH:mm:ss]");//03-04-2016 at 12:52:00
	}

	private static void datePattern() {
		LocalDate of = LocalDate.of(2016, 5, 30);
		format(of, "M/d/yyyy");// 5/30/2016
		format(of, "MM/dd/yyyy");// 05/30/2016
		format(of, "yyy/LL/dd");// 2016/05/30
		format(of, "yyy/LLL/dd");// 2016/五月/30
		format(of, "MMM   dd,   yyyy");// 五月 30, 2016
		format(of, "dd MMM uuuu");// 30 五月 2016
		format(of, "dd MMM u");// 30 五月 2016
		format(of, "dd MM u G");// 30 05 2016 公元
		format(of, " G dd MM u G");// 公元 30 05 2016 公元
		format(of, " G dd MM u G");// AD 30 05 2016 AD Locale.UK
		format(of, " uu D");// 16 151 16年 第151天

		format(of, "EEEE, MMMM    dd,      yyyy");// 星期一, 五月 30, 2016
		format(of, "EEE, MMMM    dd   ,F,      yyyy");// 星期一, 五月 30 ,2(本月第几周),
														// 2016
		format(of, "EEE, MMMM    dd   ,F,      yyyy");// Mon, May 30 ,2, 2016
														// Locale.UK
		format(of, "EEEE, MMMM    dd   ,F,      yyyy");// Monday, May 30 ,2,
														// 2016 Locale.UK

		format(of, "ee, MMMM    dd   ,F,      yyyy");// 01, May 30 ,2, 2016
														// Locale.UK
		format(of, "eee, MMMM    dd   ,F,      yyyy");// Mon, May 30 ,2, 2016
														// Locale.UK
		format(of, "eeee, MMMM    dd   ,F,      yyyy");// Monday, May 30 ,2,
														// 2016 Locale.UK

		format(of, "yyyy/Y/Q  ");// 2016/2016/2 2016年/第二季度
		format(of, "Y/Q  ");// 2016/2 2016年/第二季度
		format(of, "yyyy/w/QQQQ  ");// 2016/23/第2季度
		format(of, "yyyy/w/QQ  ");// 2016/23/2 2016年/23周(当年的第几周)/第二季度
		format(of, "yyyy/w/W/QQ  ");// 2016/23/5/02 2016年23周（当年的第几周）/月的第5周/第二季度
		format(of, "yyyy/ww/W/QQ  ");// 2016/23/5/02
										// 2016年/23周（当年的第几周）/月的第5周/第二季度

	}

	private static void format(Temporal of, String string) {
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(string, Locale.CHINA);
		String format = ofPattern.format(of);
		System.out.println(string + "-----" + format);

	}

}
