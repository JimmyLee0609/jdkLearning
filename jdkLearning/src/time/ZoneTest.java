package time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

public class ZoneTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// zoneRulesProviderUsage();
		// zoneId();
		// zoneOffset();
		zonerules();

		int b = 0;
	}

	@SuppressWarnings("unused")
	private static void zonerules() {
		List<ZoneOffsetTransition> list = new ArrayList<ZoneOffsetTransition>();
		List<ZoneOffsetTransitionRule> listr = new ArrayList<ZoneOffsetTransitionRule>();
		// �෽��
		// ��ȡZoneRulesʵ���ķ���
		ZoneRules of = ZoneRules.of(ZoneOffset.of("+08:00"));// ����offset��ȡrules
		ZoneRules of2 = ZoneRules.of(ZoneOffset.of("+05:00"), ZoneOffset.of("+06:00"), list, list, listr);

		// ʵ������
		ZoneOffsetTransition nextTransition = of.nextTransition(Instant.now());
		ZoneOffsetTransition previousTransition = of.previousTransition(Instant.now());

		Duration daylightSavings = of.getDaylightSavings(Instant.now());// 0

		ZoneOffset offset = of.getOffset(Instant.now());// +08��00
		ZoneOffset offset2 = of.getOffset(LocalDateTime.of(2017, 9, 10, 21, 43, 30));// +08��00
		ZoneOffset standardOffset = of.getStandardOffset(Instant.now());// +08��00
		ZoneOffsetTransition transition = of.getTransition(LocalDateTime.of(2017, 9, 10, 21, 43, 30));
		List<ZoneOffsetTransition> transitions = of.getTransitions();
		List<ZoneOffsetTransitionRule> transitionRules = of.getTransitionRules();
		List<ZoneOffset> validOffsets = of.getValidOffsets(LocalDateTime.of(2017, 9, 10, 21, 43, 30));// +08��00
		boolean daylightSavings2 = of.isDaylightSavings(Instant.now());// false
		boolean fixedOffset = of.isFixedOffset();// true
		boolean validOffset = of.isValidOffset(LocalDateTime.of(2017, 9, 10, 21, 43, 30), ZoneOffset.of("+08:00"));// true
		ZoneOffsetTransition previousTransition2 = of.previousTransition(Instant.now());
		String string = of.toString();// ZoneRules[currentStandardOffset=+08:00]
	}

	@SuppressWarnings("unused")
	private static void zoneOffset() {
		// ��ȡʵ��
		// ��ʽ��Ҫ - +h, +hh, +hhmm, +hh:mm, +hhmmss, +hh:mm:ss
		ZoneOffset of3 = ZoneOffset.of("+05:30:43");// ���ݱ�ǩ id=+05:30:43
		ZoneOffset ofHours = ZoneOffset.ofHours(5); // ����ֵ id=+05:00
		ZoneOffset ofHoursMinutes = ZoneOffset.ofHoursMinutes(13, 50);// id=+13:50
		ZoneOffset ofHoursMinutesSeconds = ZoneOffset.ofHoursMinutesSeconds(5, 2, 30); // id=+05:02:30
		ZoneOffset ofTotalSeconds = ZoneOffset.ofTotalSeconds(6523); // id=+01:48:43
		// ����һ��Offset������һ��Offset ���+13:00
		ZoneOffset from = ZoneOffset.from(ZoneOffset.ofHours(13));
		// ZoneOffset of5 = ZoneOffset.of("GMT+8");// �����ϸ�ʽ
		// ��ȡ����id
		Map<String, String> shortIds = ZoneOffset.SHORT_IDS;// ��ȡ��д��Ӧ�Ĺ���/����id
		Set<String> availableZoneIds2 = ZoneOffset.getAvailableZoneIds();// ��ȡ���л����п��õ�ZoneId

		// ��offset�ķ�����ȥZoneId
		ZoneId systemDefault2 = ZoneOffset.systemDefault();// ��ȡϵͳĬ�ϵ�ZoneId
															// GMT+08:00
		ZoneId of4 = ZoneOffset.of("ACT", shortIds);// ��ȡָ�������е�ָ���ַ�����ӳ�䣬����ӳ���ID������offset
		ZoneId ofOffset2 = ZoneOffset.ofOffset("GMT", of3);// ��offset��ǰ׺��ͬ�����µ�TimeZone
															// GMT+05:30:43

		ZoneId normalized2 = of3.normalized();// ��Offset����ԭ��������
		ZoneRules rules2 = of3.getRules();// ��ȡOffset��Ӧ�Ĺ���
		String id2 = of3.getId();// ��ȡid
		String displayName2 = of3.getDisplayName(TextStyle.FULL_STANDALONE, Locale.CHINA);// ��ȡ�������õ�ָ����ʽ������
		int compareTo = of3.compareTo(ofHours);// ����offset�Ƚ� ������ǲ�ֵ -1843
		int totalSeconds = of3.getTotalSeconds();// ��ȡƫ���� �������19843
		boolean supported = of3.isSupported(ChronoField.AMPM_OF_DAY);// �Ƿ�֧��ĳ����λfalse

		Instant now = Instant.now();
		LocalDateTime now2 = LocalDateTime.now();
		// int j = of3.get(ChronoField.HOUR_OF_DAY);
		// long long1 = of3.getLong(ChronoField.DAY_OF_WEEK);
		// ValueRange range = of3.range(ChronoField.SECOND_OF_MINUTE); ��λ����
		// Temporal adjustInto = of3.adjustInto(now2);

		Object query = of3.query((TemporalAccessor o) -> {
			TemporalAccessor b = o;// Offset���汣��� ʱ��ֵ +05��30��43��of3һ��
			return null;
		});

	}

	@SuppressWarnings("unused")
	private static void zoneId() {
		// ��̬����
		// ��ȡ���õ�ZoneId ��ǩ��һ�����ã����ҵ����Ļ����ã�ETC��ͷ���ò���
		Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
		HashMap<String, String> aliasMap = new HashMap<String, String>();
		// ���ݱ�ǩ���ֻ�ȡZoneId
		ZoneId of = ZoneId.of("Asia/Aden");
		ZoneId of2 = ZoneId.of("GMT+08:00", aliasMap);
		// ��ȡϵͳ���õı���ZoneId
		ZoneId systemDefault = ZoneId.systemDefault();
		// ����ƫ���� �½�һ��ZoneId
		ZoneId ofOffset = ZoneId.ofOffset("GMT", ZoneOffset.ofHoursMinutes(3, 10));
		// ʵ������
		// ��ȡid
		String id = of.getId();
		// ��ȡrules
		ZoneRules rules = of.getRules();
		// ��ZoneId���������ǩ�ĳ�ʼ�趨
		ZoneId normalized = of.normalized();
		// ���ݴ�����������Ժ���������ʾ�����ǩ������
		String displayName = of.getDisplayName(TextStyle.FULL, Locale.CHINA);

	}

	@SuppressWarnings("unused")
	private static void zoneRulesProviderUsage() {
		// ��ȡ�������п��õ�ZoneId
		Set<String> availableZoneIds = ZoneRulesProvider.getAvailableZoneIds();
		for (String zoneId : availableZoneIds) {
			// ����ZoneId��ȡrules
			ZoneRules rules = ZoneRulesProvider.getRules(zoneId, false);
			// ����ZoneId��ȡ�汾��Ϣ
			NavigableMap<String, ZoneRules> versions = ZoneRulesProvider.getVersions(zoneId);
		}
	}

}
