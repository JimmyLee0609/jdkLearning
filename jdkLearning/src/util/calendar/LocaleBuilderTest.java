package util.calendar;

import java.util.Locale;
import java.util.Locale.Builder;

public class LocaleBuilderTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Builder builder = new Locale.Builder();
		// �÷���������ʹ�ã���߲���̫��ѧ
		Locale build = builder.addUnicodeLocaleAttribute("testLocaleAttribute").setExtension('a', "ss")
				.setLanguage("my").setLanguageTag("myLt").setRegion("atx").setScript("null").setVariant("var")
				.setUnicodeLocaleKeyword("abs", "ggt").build();

		Builder builder2 = builder.removeUnicodeLocaleAttribute("");
		Builder clearExtensions = builder.clearExtensions();
		Builder clear = builder2.clear();
		String string = builder.toString();

	}

}
