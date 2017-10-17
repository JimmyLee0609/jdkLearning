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
		// 类方法
		// 获取ZoneRules实例的方法
		ZoneRules of = ZoneRules.of(ZoneOffset.of("+08:00"));// 根据offset获取rules
		ZoneRules of2 = ZoneRules.of(ZoneOffset.of("+05:00"), ZoneOffset.of("+06:00"), list, list, listr);

		// 实例方法
		ZoneOffsetTransition nextTransition = of.nextTransition(Instant.now());
		ZoneOffsetTransition previousTransition = of.previousTransition(Instant.now());

		Duration daylightSavings = of.getDaylightSavings(Instant.now());// 0

		ZoneOffset offset = of.getOffset(Instant.now());// +08：00
		ZoneOffset offset2 = of.getOffset(LocalDateTime.of(2017, 9, 10, 21, 43, 30));// +08：00
		ZoneOffset standardOffset = of.getStandardOffset(Instant.now());// +08：00
		ZoneOffsetTransition transition = of.getTransition(LocalDateTime.of(2017, 9, 10, 21, 43, 30));
		List<ZoneOffsetTransition> transitions = of.getTransitions();
		List<ZoneOffsetTransitionRule> transitionRules = of.getTransitionRules();
		List<ZoneOffset> validOffsets = of.getValidOffsets(LocalDateTime.of(2017, 9, 10, 21, 43, 30));// +08：00
		boolean daylightSavings2 = of.isDaylightSavings(Instant.now());// false
		boolean fixedOffset = of.isFixedOffset();// true
		boolean validOffset = of.isValidOffset(LocalDateTime.of(2017, 9, 10, 21, 43, 30), ZoneOffset.of("+08:00"));// true
		ZoneOffsetTransition previousTransition2 = of.previousTransition(Instant.now());
		String string = of.toString();// ZoneRules[currentStandardOffset=+08:00]
	}

	@SuppressWarnings("unused")
	private static void zoneOffset() {
		// 获取实例
		// 格式需要 - +h, +hh, +hhmm, +hh:mm, +hhmmss, +hh:mm:ss
		ZoneOffset of3 = ZoneOffset.of("+05:30:43");// 根据标签 id=+05:30:43
		ZoneOffset ofHours = ZoneOffset.ofHours(5); // 根据值 id=+05:00
		ZoneOffset ofHoursMinutes = ZoneOffset.ofHoursMinutes(13, 50);// id=+13:50
		ZoneOffset ofHoursMinutesSeconds = ZoneOffset.ofHoursMinutesSeconds(5, 2, 30); // id=+05:02:30
		ZoneOffset ofTotalSeconds = ZoneOffset.ofTotalSeconds(6523); // id=+01:48:43
		// 根据一个Offset来创建一个Offset 结果+13:00
		ZoneOffset from = ZoneOffset.from(ZoneOffset.ofHours(13));
		// ZoneOffset of5 = ZoneOffset.of("GMT+8");// 不符合格式
		// 获取可用id
		Map<String, String> shortIds = ZoneOffset.SHORT_IDS;// 获取简写对应的国家/地区id
		Set<String> availableZoneIds2 = ZoneOffset.getAvailableZoneIds();// 获取运行环境中可用的ZoneId

		// 用offset的方法后去ZoneId
		ZoneId systemDefault2 = ZoneOffset.systemDefault();// 获取系统默认的ZoneId
															// GMT+08:00
		ZoneId of4 = ZoneOffset.of("ACT", shortIds);// 获取指定集合中的指定字符串的映射，根据映射的ID来创建offset
		ZoneId ofOffset2 = ZoneOffset.ofOffset("GMT", of3);// 将offset和前缀共同构成新的TimeZone
															// GMT+05:30:43

		ZoneId normalized2 = of3.normalized();// 将Offset返回原来的配置
		ZoneRules rules2 = of3.getRules();// 获取Offset对应的规则
		String id2 = of3.getId();// 获取id
		String displayName2 = of3.getDisplayName(TextStyle.FULL_STANDALONE, Locale.CHINA);// 获取区域设置的指定格式的名字
		int compareTo = of3.compareTo(ofHours);// 两个offset比较 结果就是差值 -1843
		int totalSeconds = of3.getTotalSeconds();// 获取偏移量 以秒计算19843
		boolean supported = of3.isSupported(ChronoField.AMPM_OF_DAY);// 是否支持某个单位false

		Instant now = Instant.now();
		LocalDateTime now2 = LocalDateTime.now();
		// int j = of3.get(ChronoField.HOUR_OF_DAY);
		// long long1 = of3.getLong(ChronoField.DAY_OF_WEEK);
		// ValueRange range = of3.range(ChronoField.SECOND_OF_MINUTE); 单位不对
		// Temporal adjustInto = of3.adjustInto(now2);

		Object query = of3.query((TemporalAccessor o) -> {
			TemporalAccessor b = o;// Offset里面保存的 时间值 +05：30：43与of3一样
			return null;
		});

	}

	@SuppressWarnings("unused")
	private static void zoneId() {
		// 静态方法
		// 获取可用的ZoneId 标签不一定能用，国家地区的还能用，ETC开头的用不了
		Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
		HashMap<String, String> aliasMap = new HashMap<String, String>();
		// 根据标签名字获取ZoneId
		ZoneId of = ZoneId.of("Asia/Aden");
		ZoneId of2 = ZoneId.of("GMT+08:00", aliasMap);
		// 获取系统配置的本地ZoneId
		ZoneId systemDefault = ZoneId.systemDefault();
		// 根据偏移量 新建一个ZoneId
		ZoneId ofOffset = ZoneId.ofOffset("GMT", ZoneOffset.ofHoursMinutes(3, 10));
		// 实例方法
		// 获取id
		String id = of.getId();
		// 获取rules
		ZoneRules rules = of.getRules();
		// 将ZoneId返回这个标签的初始设定
		ZoneId normalized = of.normalized();
		// 根据传入的区域属性和字体风格显示这个标签的名字
		String displayName = of.getDisplayName(TextStyle.FULL, Locale.CHINA);

	}

	@SuppressWarnings("unused")
	private static void zoneRulesProviderUsage() {
		// 获取配置所有可用的ZoneId
		Set<String> availableZoneIds = ZoneRulesProvider.getAvailableZoneIds();
		for (String zoneId : availableZoneIds) {
			// 根据ZoneId获取rules
			ZoneRules rules = ZoneRulesProvider.getRules(zoneId, false);
			// 根据ZoneId获取版本信息
			NavigableMap<String, ZoneRules> versions = ZoneRulesProvider.getVersions(zoneId);
		}
	}

}
