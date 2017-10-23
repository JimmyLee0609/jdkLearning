package textlearning;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;

public class DecimalFormatTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws ParseException {
//		十进制数格式化
		NumberFormat instance = DecimalFormat.getInstance();
		
		DecimalFormat decimalFormat = new DecimalFormat();
		String localizedPattern = decimalFormat.toLocalizedPattern();//#,##0.###

		String format = decimalFormat.format(-5315165616.56516);//-5,315,165,616.565
//		获取样式
		DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
		
		
		formatSymbol();
		
		
		int multiplier = decimalFormat.getMultiplier();//倍数   1
		Currency currency = decimalFormat.getCurrency();//货币 CNY
		int groupingSize = decimalFormat.getGroupingSize();//多少个分一组  3
		String negativePrefix = decimalFormat.getNegativePrefix();//负数前缀   可以设定为其他值  -
		String negativeSuffix = decimalFormat.getNegativeSuffix();//负数后缀    可以设定为其他值""
		String positivePrefix = decimalFormat.getPositivePrefix();//正数前缀    可以设定为其他值""
		String positiveSuffix = decimalFormat.getPositiveSuffix();//整数后缀   可以设定为其他值""
//		设置正数的前缀
		decimalFormat.setPositivePrefix("++");
//		设置正数的后缀
		decimalFormat.setPositiveSuffix("~~");
//		设置负数的前缀
		decimalFormat.setNegativePrefix("---");
//		设置负数的后缀
		decimalFormat.setNegativeSuffix("+-+-");
		String format2 = decimalFormat.format(-561356.16165);//---561,356.162+-+-
		String format3 = decimalFormat.format(651651.116130);//++651,651.116~~
//		设置倍数   将要格式化的数乘以  倍数  再输出
//		解析的数就会除以  倍数  解析结果
		decimalFormat.setMultiplier(5);
		String format4 = decimalFormat.format(5);//++25~~
		
//		由于设置了前缀和后缀,      要符合格式的才能被解析
		Number parse = decimalFormat.parse("++25~~");//5
//		Number parse2 = decimalFormat.parse("55");这个不符合就不能解析
		String pattern = decimalFormat.toPattern();//++#,##0.###~~;'---'#,##0.###'+-+-'
		
//		百分号的含义就是将数字乘以100再加上%
		decimalFormat.applyPattern("#,##0.0#%");
		String format5 = decimalFormat.format(531684.16516);//53,168,416.52%
//		分号  ;   是正数   负数  的分隔
		decimalFormat.applyPattern("#,##0.0#%;-#,##0.0#%");
		String format6 = decimalFormat.format(-531684.16516);//-53,168,416.52%
		
//		单引号范围的前缀或后缀会被记录
		decimalFormat.applyPattern("'#,##0.0#%;-'#,##0.0#%");
		String format7 = decimalFormat.format(-531684.16516);//-#,##0.0#%;-53,168,416.52%
		
		decimalFormat.applyPattern("%#,##0.0#");
		String format8 = decimalFormat.format(-531684.16516);
		
//		直接变成前缀
		decimalFormat.applyPattern("E#,##0.0##");
		String format9 = decimalFormat.format(-531684.16516);//-E531,684.165
		
//		科学计数法   E0表示  科学计数法,  
		decimalFormat.applyPattern("#,##0.0##E0");
		String format10 = decimalFormat.format(-531684.16516);//-53.16842E4
		
		decimalFormat.applyPattern("#,##0.0##E00");
		String format11 = decimalFormat.format(-531684.16516);//-53.16842E04
//		\u00A4表示货币字符
		decimalFormat.applyPattern("\u00A4#,##0.0##");
		String format12 = decimalFormat.format(-531684.16516);//-￥531,684.165
		decimalFormat.applyPattern("\u00A4#,##0.\u00A40##");
		String format13 = decimalFormat.format(-531684.16516);//-￥531,684.165
		decimalFormat.applyPattern("\u00A4#,##0.\u00A40##\u00A4");
		String format14 = decimalFormat.format(-531684.16516);//￥531,684.165
		int v=0;
	}

	@SuppressWarnings("unused")
	private static void formatSymbol() {
		DecimalFormat df =new  DecimalFormat();
		DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
		
		Currency currency = dfs.getCurrency();//当前货币   CNY
		String currencySymbol = dfs.getCurrencySymbol();//当前货币符号  ￥
		char decimalSeparator = dfs.getDecimalSeparator();//十进制整数小数分隔符  .
		char digit = dfs.getDigit(); //表示数字的字符不能为0    #
		String exponentSeparator = dfs.getExponentSeparator();//指数的字符   E
		char groupingSeparator = dfs.getGroupingSeparator();  //分组的标识  ,  
		String internationalCurrencySymbol = dfs.getInternationalCurrencySymbol();//当前货币的字符 国际标准  CNY
		String infinity = dfs.getInfinity();//正无穷的表示    ∞
		char monetaryDecimalSeparator = dfs.getMonetaryDecimalSeparator();//金钱的整数小数分割  .  
		String naN = dfs.getNaN();//�
		char patternSeparator = dfs.getPatternSeparator();//模式分割符  ; 
		char percent = dfs.getPercent();//百分数模式    数值乘以100加上符号     %
		char perMill = dfs.getPerMill();//千分数模式      数值乘以1000加上符号   ‰
		char zeroDigit = dfs.getZeroDigit();//用于代表0-9任意数的符号    0
		
		int c=0;
	}

}
