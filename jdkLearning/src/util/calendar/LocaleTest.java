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
		// 根据语言获取Locale
		Locale forLanguageTag = Locale.forLanguageTag("zh");
		// 根据目录获取Locale
		Locale default1 = Locale.getDefault(Category.FORMAT);
		Locale default9 = Locale.getDefault(Category.DISPLAY);
		list.add(default1);
		list.add(forLanguageTag);
		// 获取Locale的标准语言
		String[] isoLanguages = Locale.getISOLanguages();
		// 获取Locale的国家
		String[] isoCountries = Locale.getISOCountries();
		// 查找Locale的标签
//		priorityList - 用户的语言优先级列表，其中每个语言标签根据优先级或权重按降序排列
//		标签 - 用于匹配的语言字符串
		String lookupTag = Locale.lookupTag(
				Locale.LanguageRange.parse("en-US;q=1.0,en-GB;q=0.5,fr-FR;q=0.0"), 
				Arrays.asList("zh", "de", "jp", "en"));
		// 根据RCF4647过滤
		List<Locale> filter = Locale.filter(Locale.LanguageRange.parse(""), list,
				Locale.FilteringMode.REJECT_EXTENDED_RANGES);
		// 使用RFC 4647中定义的基本过滤机制返回匹配语言标签的列表。
		List<String> filterTags = Locale.filterTags(Locale.LanguageRange.parse(""), Arrays.asList("", "", ""),
				Locale.FilteringMode.AUTOSELECT_FILTERING);
	}

	@SuppressWarnings("unused")
	private static void filter() {
		//		保存集合		
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
//		在list中返回符合LanguageRange的结果，根据过滤模式返回是否包含扩展的语言。只要条件符合就返回
		List<Locale> filter = Locale.filter(parse, list, Locale.FilteringMode.IGNORE_EXTENDED_RANGES);
//		在tag中返回符合LanguageRange的结果，根据过滤模式返回是否包含扩展的语言。只要条件符合就返回
		List<String> filterTags = Locale.filterTags(parse, tags, Locale.FilteringMode.IGNORE_EXTENDED_RANGES);
		
		
	}

	@SuppressWarnings("unused")
	private static void lookup() {
//		保存集合		
		ArrayList<Locale> list = new ArrayList<Locale>();
		ArrayList<String> tags = new ArrayList<String>();
//		新建Locale，获取语言标签   ,  语言，语言标签都可以作为范围查找
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
		
//		设置语言范围，并设置权重值
		List<LanguageRange> parse = Locale.LanguageRange.parse(languageTag+";q=0.5,"+languageTag2+";q=1.0");
//		查找Locale		在list中返回符合LanguageRange条件的结果，根据权重值返回一个
		Locale lookup = Locale.lookup(parse,list);
//     查找Tag      在tags中返回符合LanguageRange条件的结果，根据权重值返回一个
		String lookupTag = Locale.lookupTag(parse, tags);
		
	}

	@SuppressWarnings("unused")
	private static void defaultLocal() {
		Locale default1 = Locale.getDefault();
		//获取语言
		String language = default1.getLanguage();          										//zh
		String displayLanguage = default1.getDisplayLanguage();							//中文
		String iso3Language = default1.getISO3Language();									//zho
		String displayLanguage2 = default1.getDisplayLanguage(Locale.FRANCE);//chinois
//		获取国家
		String country = default1.getCountry();													//CN
		String displayCountry = default1.getDisplayCountry();							//中国
		String displayCountry2 = default1.getDisplayCountry(Locale.FRANCE);//Chine
		String iso3Country = default1.getISO3Country();									//CHN
//		获取变体
		String variant = default1.getVariant();//""没有值
		String displayVariant = default1.getDisplayVariant();//""
		String displayVariant2 = default1.getDisplayVariant(Locale.FRANCE);//""
//		获取显式名字
		String displayName = default1.getDisplayName();//中文（中国）
		String displayName2 = default1.getDisplayName(Locale.FRANCE);//chinois(Chine)
//		返回与此Locale相关的字符集           集合不可改变
		Set<Character> extensionKeys = default1.getExtensionKeys();//[]空
		
//		返回本地区的扩展名的副本Locale。没有扩展名就返回本身
		Locale stripExtensions = default1.stripExtensions();//zh_CN
		boolean hasExtensions = default1.hasExtensions();//false
//		返回传入值经过charset转换得到的值，如果没有相关字符集，就返回null
		String extension = default1.getExtension('z');
		
//		返回一个格式正确的IETF BCP 47语言标记，代表此语言环境。
//		语言：如果语言为空，或者形式不完整（例如“a”或“e2”），则将以“und”（未确定）的形式发出。
//		国家：如果国家格局不正确（例如“12”或“美国”），将略去。
//		变体：如果变体形式良好，则每个子段（以' - '或'_'分隔）作为子标签发出。
//		如果所有子段匹配[0-9a-zA-Z] {1,8}
		String languageTag = default1.toLanguageTag();//zh-CN
		
//		返回语言环境的脚本，空或者ISO 15924 四字母脚本代码如Latn，Cyrl，
		String script = default1.getScript();//""空
		String displayScript = default1.getDisplayScript();//""
		String displayScript2 = default1.getDisplayScript(Locale.FRANCE);//""
		
		
//		返回与此区域设置相关联的unicode区域设置属性集，如果没有属性则返回空集合
		Set<String> unicodeLocaleAttributes = default1.getUnicodeLocaleAttributes();//[]空
//		返回此区域设置定义的Unicode区域设置键的集合，如果此区域设置没有，则返回空集合
		Set<String> unicodeLocaleKeys = default1.getUnicodeLocaleKeys();//[]空
//		返回与该区域设置的指定Unicode区域设置键相关联的Unicode区域设置类型。使用上面的key
//		String unicodeLocaleType = default1.getUnicodeLocaleType("");

	}
}
