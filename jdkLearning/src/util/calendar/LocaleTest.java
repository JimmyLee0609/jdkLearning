package util.calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Locale.Category;
import java.util.Locale.LanguageRange;
import java.util.Set;

public class LocaleTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		defaultLocal();
		lookup();
		filter();
		
		
		
		Locale[] availableLocales = Locale.getAvailableLocales();
		for (Locale locale : availableLocales) {
			String string = locale.toString();
			System.out.println(string);
		}
		ArrayList<Locale> list = new ArrayList<Locale>();
		// �������Ի�ȡLocale
		Locale forLanguageTag = Locale.forLanguageTag("zh");
		// ����Ŀ¼��ȡLocale
		Locale default1 = Locale.getDefault(Category.FORMAT);
		Locale default9 = Locale.getDefault(Category.DISPLAY);
		list.add(default1);
		list.add(forLanguageTag);
		// ��ȡLocale�ı�׼����
		String[] isoLanguages = Locale.getISOLanguages();
		// ��ȡLocale�Ĺ���
		String[] isoCountries = Locale.getISOCountries();
		// ����Locale�ı�ǩ
//		priorityList - �û����������ȼ��б�����ÿ�����Ա�ǩ�������ȼ���Ȩ�ذ���������
//		��ǩ - ����ƥ��������ַ���
		String lookupTag = Locale.lookupTag(
				Locale.LanguageRange.parse("en-US;q=1.0,en-GB;q=0.5,fr-FR;q=0.0"), 
				Arrays.asList("zh", "de", "jp", "en"));
		// ����RCF4647����
		List<Locale> filter = Locale.filter(Locale.LanguageRange.parse(""), list,
				Locale.FilteringMode.REJECT_EXTENDED_RANGES);
		// ʹ��RFC 4647�ж���Ļ������˻��Ʒ���ƥ�����Ա�ǩ���б�
		List<String> filterTags = Locale.filterTags(Locale.LanguageRange.parse(""), Arrays.asList("", "", ""),
				Locale.FilteringMode.AUTOSELECT_FILTERING);
	}

	@SuppressWarnings("unused")
	private static void filter() {
		//		���漯��		
		ArrayList<Locale> list = new ArrayList<Locale>();
		ArrayList<String> tags = new ArrayList<String>();

		Locale canada = Locale.CANADA;
		String languageTag = canada.toLanguageTag();
		String language2 = canada.getLanguage();
		Locale chinese = Locale.CHINESE;
		String languageTag2 = chinese.toLanguageTag();
		String language = chinese.getLanguage();
		Locale canadaFrench = Locale.CANADA_FRENCH;
		String languageTag3 = canadaFrench.toLanguageTag();
		Locale german = Locale.GERMAN;
		String languageTag4 = german.toLanguageTag();
		
		list.add(german);list.add(canadaFrench);list.add(chinese);list.add(canada);
		tags.add(languageTag4);tags.add(languageTag3);tags.add(language2);
		
		List<LanguageRange> parse = Locale.LanguageRange.parse(languageTag+";q=0.5,"+languageTag2+";q=1.0");
//		��list�з��ط���LanguageRange�Ľ�������ݹ���ģʽ�����Ƿ������չ�����ԡ�ֻҪ�������Ͼͷ���
		List<Locale> filter = Locale.filter(parse, list, Locale.FilteringMode.IGNORE_EXTENDED_RANGES);
//		��tag�з��ط���LanguageRange�Ľ�������ݹ���ģʽ�����Ƿ������չ�����ԡ�ֻҪ�������Ͼͷ���
		List<String> filterTags = Locale.filterTags(parse, tags, Locale.FilteringMode.IGNORE_EXTENDED_RANGES);
		
		
	}

	@SuppressWarnings("unused")
	private static void lookup() {
//		���漯��		
		ArrayList<Locale> list = new ArrayList<Locale>();
		ArrayList<String> tags = new ArrayList<String>();
//		�½�Locale����ȡ���Ա�ǩ   ,  ���ԣ����Ա�ǩ��������Ϊ��Χ����
		Locale canada = Locale.CANADA;
		String languageTag = canada.toLanguageTag();
		String language2 = canada.getLanguage();
		Locale chinese = Locale.CHINESE;
		String languageTag2 = chinese.toLanguageTag();
		String language = chinese.getLanguage();
		Locale canadaFrench = Locale.CANADA_FRENCH;
		String languageTag3 = canadaFrench.toLanguageTag();
		Locale german = Locale.GERMAN;
		String languageTag4 = german.toLanguageTag();
		
		list.add(german);list.add(canadaFrench);list.add(chinese);list.add(canada);
		tags.add(languageTag4);tags.add(languageTag3);tags.add(language2);
		
//		�������Է�Χ��������Ȩ��ֵ
		List<LanguageRange> parse = Locale.LanguageRange.parse(languageTag+";q=0.5,"+languageTag2+";q=1.0");
//		����Locale		��list�з��ط���LanguageRange�����Ľ��������Ȩ��ֵ����һ��
		Locale lookup = Locale.lookup(parse,list);
//     ����Tag      ��tags�з��ط���LanguageRange�����Ľ��������Ȩ��ֵ����һ��
		String lookupTag = Locale.lookupTag(parse, tags);
		
	}

	@SuppressWarnings("unused")
	private static void defaultLocal() {
		Locale default1 = Locale.getDefault();
		//��ȡ����
		String language = default1.getLanguage();          										//zh
		String displayLanguage = default1.getDisplayLanguage();							//����
		String iso3Language = default1.getISO3Language();									//zho
		String displayLanguage2 = default1.getDisplayLanguage(Locale.FRANCE);//chinois
//		��ȡ����
		String country = default1.getCountry();													//CN
		String displayCountry = default1.getDisplayCountry();							//�й�
		String displayCountry2 = default1.getDisplayCountry(Locale.FRANCE);//Chine
		String iso3Country = default1.getISO3Country();									//CHN
//		��ȡ����
		String variant = default1.getVariant();//""û��ֵ
		String displayVariant = default1.getDisplayVariant();//""
		String displayVariant2 = default1.getDisplayVariant(Locale.FRANCE);//""
//		��ȡ��ʽ����
		String displayName = default1.getDisplayName();//���ģ��й���
		String displayName2 = default1.getDisplayName(Locale.FRANCE);//chinois(Chine)
//		�������Locale��ص��ַ���           ���ϲ��ɸı�
		Set<Character> extensionKeys = default1.getExtensionKeys();//[]��
		
//		���ر���������չ���ĸ���Locale��û����չ���ͷ��ر���
		Locale stripExtensions = default1.stripExtensions();//zh_CN
		boolean hasExtensions = default1.hasExtensions();//false
//		���ش���ֵ����charsetת���õ���ֵ�����û������ַ������ͷ���null
		String extension = default1.getExtension('z');
		
//		����һ����ʽ��ȷ��IETF BCP 47���Ա�ǣ���������Ի�����
//		���ԣ��������Ϊ�գ�������ʽ�����������硰a����e2���������ԡ�und����δȷ��������ʽ������
//		���ң�������Ҹ�ֲ���ȷ�����硰12������������������ȥ��
//		���壺���������ʽ���ã���ÿ���ӶΣ���' - '��'_'�ָ�����Ϊ�ӱ�ǩ������
//		��������Ӷ�ƥ��[0-9a-zA-Z] {1,8}
		String languageTag = default1.toLanguageTag();//zh-CN
		
//		�������Ի����Ľű����ջ���ISO 15924 ����ĸ�ű�������Latn��Cyrl��
		String script = default1.getScript();//""��
		String displayScript = default1.getDisplayScript();//""
		String displayScript2 = default1.getDisplayScript(Locale.FRANCE);//""
		
		
//		����������������������unicode�����������Լ������û�������򷵻ؿռ���
		Set<String> unicodeLocaleAttributes = default1.getUnicodeLocaleAttributes();//[]��
//		���ش��������ö����Unicode�������ü��ļ��ϣ��������������û�У��򷵻ؿռ���
		Set<String> unicodeLocaleKeys = default1.getUnicodeLocaleKeys();//[]��
//		��������������õ�ָ��Unicode�������ü��������Unicode�����������͡�ʹ�������key
//		String unicodeLocaleType = default1.getUnicodeLocaleType("");

	}
}
