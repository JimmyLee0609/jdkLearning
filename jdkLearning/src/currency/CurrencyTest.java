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
		int defaultFractionDigits = instance.getDefaultFractionDigits();						//2  ��ȡ��û���һ��ʹ�õ�Ĭ��С��λ����100.00
		String displayName = instance.getDisplayName()	;										 //�����
		String displayName2 = instance.getDisplayName(Locale.FRANCE);				//yuan renminbi chinois
		int numericCode = instance.getNumericCode();										//  156   Returns the ISO 4217 numeric code of this currency.
		String symbol = instance.getSymbol();															//��
		String symbol2 = instance.getSymbol(Locale.FRANCE);								//CNY
		String string = instance.toString();															//CNY
	}

	@SuppressWarnings("unused")
	private static void getInstance() {
		// ��ȡ����ʵ����û�й������캯��
		// ��ȡ���л��������ṩ�Ļ��Ҽ���
		Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
		// ���ݵ������Ի�ȡ���صĻ���ʵ����
		Currency instance = Currency.getInstance(Locale.CHINA);
		// ���ݻ������ֻ�ȡ����ʵ��, ��дӢ����ĸ 3��
		Currency instance2 = Currency.getInstance("CNY");

	}

}
