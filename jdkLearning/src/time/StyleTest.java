package time;

import java.time.format.DecimalStyle;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Set;

public class StyleTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		textStyle();
		stringSingle();
		formatStyle();
		resolverStyle();
		decimalStyle();
	}

	@SuppressWarnings("unused")
	private static void decimalStyle() {
		DecimalStyle standard = DecimalStyle.STANDARD;// DecimalStyle[0+-.]
		Set<Locale> availableLocales = DecimalStyle.getAvailableLocales();
		DecimalStyle of = DecimalStyle.of(Locale.CHINA);
		DecimalStyle ofDefaultLocale = DecimalStyle.ofDefaultLocale();
		int v = 0;
	}

	@SuppressWarnings("unused")
	private static void resolverStyle() {
		ResolverStyle lenient = ResolverStyle.LENIENT;
		ResolverStyle smart = ResolverStyle.SMART;
		ResolverStyle strict = ResolverStyle.STRICT;
	}

	@SuppressWarnings("unused")
	private static void formatStyle() {
		FormatStyle full = FormatStyle.FULL;
		FormatStyle l = FormatStyle.LONG;
		FormatStyle medium = FormatStyle.MEDIUM;
		FormatStyle s = FormatStyle.SHORT;
		FormatStyle[] values = FormatStyle.values();
		FormatStyle valueOf = FormatStyle.valueOf("FULL");
	}

	@SuppressWarnings("unused")
	private static void stringSingle() {
		SignStyle always = SignStyle.ALWAYS;
		SignStyle exceedsPad = SignStyle.EXCEEDS_PAD;
		SignStyle never = SignStyle.NEVER;
		SignStyle normal = SignStyle.NORMAL;
		SignStyle notNegative = SignStyle.NOT_NEGATIVE;
		SignStyle[] values = SignStyle.values();
		int v = 0;
	}

	@SuppressWarnings("unused")
	private static void textStyle() {
		TextStyle full = TextStyle.FULL;// FULL
		TextStyle fullStandalone = TextStyle.FULL_STANDALONE;// FULL_STANDALONE
		TextStyle narrow = TextStyle.NARROW;// NARROW
		TextStyle narrowStandalone = TextStyle.NARROW_STANDALONE;// NARROW_STANDALONE
		TextStyle s = TextStyle.SHORT;// SHORT
		TextStyle shortStandalone = TextStyle.SHORT_STANDALONE;// SHORT_STANDALONE
		TextStyle[] values = TextStyle.values();
		TextStyle valueOf2 = TextStyle.valueOf("FULL");// FULL
		TextStyle valueOf = TextStyle.valueOf(TextStyle.class, "FULL");// FULL
		TextStyle asNormal = full.asNormal();// FULL
		TextStyle asStandalone = full.asStandalone();// FULL_STANDALONE
		int compareTo = full.compareTo(fullStandalone);// -1
		boolean standalone = full.isStandalone();// false
		String name = full.name();// FULL
		int ordinal = full.ordinal();// 0
		String string = valueOf.toString();
	}

}
