package currency;

import java.util.Currency;
import java.util.Locale;
import java.util.Set;

public class CurrencyTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		getInstance();
		Currency instance = Currency.getInstance(Locale.CHINA);
		String currencyCode = instance.getCurrencyCode();     									 //CNY
		int defaultFractionDigits = instance.getDefaultFractionDigits();						//2  获取与该货币一起使用的默认小数位数。100.00
		String displayName = instance.getDisplayName()	;										 //人民币
		String displayName2 = instance.getDisplayName(Locale.FRANCE);				//yuan renminbi chinois
		int numericCode = instance.getNumericCode();										//  156   Returns the ISO 4217 numeric code of this currency.
		String symbol = instance.getSymbol();															//￥
		String symbol2 = instance.getSymbol(Locale.FRANCE);								//CNY
		String string = instance.toString();															//CNY
	}

	@SuppressWarnings("unused")
	private static void getInstance() {
		// 获取货币实例。没有公共构造函数
		// 获取运行环境可以提供的货币集合
		Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
		// 根据地区属性获取当地的货币实体类
		Currency instance = Currency.getInstance(Locale.CHINA);
		// 根据货币名字获取货币实例, 大写英文字母 3个
		Currency instance2 = Currency.getInstance("CNY");

	}

}
