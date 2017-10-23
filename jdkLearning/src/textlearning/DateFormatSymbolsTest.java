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
		String format = instance.format(new Date());//公元 2017-10-21 3 294 42 3 星期六 下午 15 03:33:56 76 
		//"SimpleDate 中的字母代码     G yyyy-MM-dd Y L W D w F E u a HH kk-KK hh:mm:ss S Z z X
//DateFormatSymbols中的字母代码  G aaaa-nn-   jj  Y L W D w F E u x HH kk-KK hh:mm:ss S Z z X
//		除了年月日 am/pm  其他都相同
//			yyyy-MM-dd a    simpleDate
//			aaaa-nn 	-jj  x    DateFormatSymbols
		String localizedPattern = instance.toLocalizedPattern();
		String localPatternChars = dateFormatSymbols.getLocalPatternChars();//GanjkHmsSEDFwWxhKzZ
		String[] weekdays = dateFormatSymbols.getWeekdays();
		
//		将simpleDate中的formatSymbols里边的元素进行修改会影响simpleDate的效果
		dateFormatSymbols.setWeekdays(new String[]{"","周1","周2","周3","周四","周五","周六","周日"});
		dateFormatSymbols.setShortWeekdays(new String[]{"","1","2","3","4","5","6","7"});
		
		instance.setDateFormatSymbols(dateFormatSymbols);
//		申请自定义样式的模式解析字符串
		instance.applyLocalizedPattern("G aaaa nn jj W D w F E x HH:mm:ss");
		String format2 = instance.format(new Date());//公元 2017 10 21 3 294 42 3 7 下午 15:49:10
//		这里的星期改变了    解析时   按照自定义的样式来解析
		Date parse = instance.parse("公元 2017 10 21 3 254 24 3 7 下午 15:50:10");//Sat Oct 21 15:50:10 GMT+08:00 2017
	
		instance.applyLocalizedPattern("aa nn jj HH:mm");
		Date parse2 = instance.parse("17 10 21 15:52");//Sat Oct 21 15:52:00 GMT+08:00 2017
		
//		根据自定义的区域属性特征和模式创建类   自定义的将星期变成 了  1 2 3 4 5 6 7 下面就是改变后的样式
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",dateFormatSymbols);
		simpleDateFormat.applyLocalizedPattern("G aaaa nn jj W D w F E x HH:mm:ss");
		String format3 = simpleDateFormat.format(new Date());//公元 2017 10 21 3 294 42 3 7 下午 15:49:10
		
		int c=0;
	}

}
