package time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.List;

public class DurationTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 单位支持天以内
		// 静态方法
		Duration ofSeconds = Duration.ofSeconds(10, 500); // 构造一个10秒500纳秒的Duration
		Duration ofSeconds2 = Duration.ofSeconds(5); // 构造一个5秒
		Duration ofNanos = Duration.ofNanos(490); // 构造一个490纳秒
		Duration ofMinutes = Duration.ofMinutes(32); // 构造一个32分钟
		Duration ofMillis = Duration.ofMillis(4923); // 构造一个4923毫秒
		Duration ofHours = Duration.ofHours(2); // 构造一个2小时
		Duration ofDays = Duration.ofDays(1); // 构造一个1天
		// 根据传入的单位和时间创建Duration
		Duration of = Duration.of(5, ChronoUnit.HOURS); // 构造一个5小时

		// 目前只能用到 Duration的对象
		Duration from = Duration.from(Duration.ofHours(3)); // 10800秒
		// 实例方法
		minus();
		plus();

		// 获取
		int nano = from.getNano(); // 获取本Duration 的纳秒数 结果0
		long seconds = from.getSeconds(); // 获取本Duration的秒数 结果10800
		// 只能是这两个单位其他都不行
		long l = from.get(ChronoUnit.SECONDS); // 根据传入的单位转换结果 结果10800
		long h = from.get(ChronoUnit.NANOS); // 根据传入的单位转换结果 结果10800
		// 转换
		long nanos = from.toNanos(); // 将Duration 转换成 纳秒 结果10800000000000
		long days = from.toDays(); // 将其转换成天 结果0
		long hours = from.toHours(); // 将其转换成小时 结果3
		long millis = from.toMillis(); // 将其转换成毫秒 结果10800000
		long minutes = from.toMinutes(); // 将其转换成分钟 结果180

		List<TemporalUnit> units2 = from.getUnits();// [Seconds, Nanos]

		String string = from.toString(); // PT3H

		// 需要以秒为单位，LocalDate不能用
		Duration between = Duration.between(Instant.now(), Instant.now().plus(10, ChronoUnit.SECONDS)); // 计算两个Instant
																										// 之间的间隔
																										// 结果
																										// 10秒
		Duration between2 = Duration.between(LocalDateTime.now(), // 计算两个LocalDateTime之间的间隔，
																	// 结果36000秒
				LocalDateTime.now().plus(10, ChronoUnit.HOURS));

		Duration abs = from.abs(); // 获取绝对值 结果10800
		Temporal addTo = from.addTo(Instant.now()); // 将Duration加到Instant 实例
													// 结果2017-10-10T10:36:51.909Z

		int compareTo = from.compareTo(ofDays); // 与传入的Duration 比较 结果-1
		Duration dividedBy = from.dividedBy(7); // 除以传入的值 1542秒 857142857纳秒
		List<TemporalUnit> units = from.getUnits(); // 获取Duration的单位 Seconds
													// Nanos
		boolean negative = from.isNegative(); // 是否是负值 false
		boolean zero = from.isZero(); // 是否是0 false

		// 根据传入的秒值和原来的纳秒值创建新的Duration对象 结果 700秒
		Duration withSeconds = from.withSeconds(700);
		// 根据传入的纳秒值和原来的秒值创建新的Duration对象 结果10800秒 900纳秒
		Duration withNanos = from.withNanos(900);
		Temporal subtractFrom = from.subtractFrom(Instant.now());// 根据传入的Instant
																	// 摘要它的秒
																	// 和毫秒数

		Duration negated = from.negated(); // 取反-10800秒

		// from.parse(text)
	}

	@SuppressWarnings("unused")
	private static void minus() {
		Duration from = Duration.from(Duration.ofHours(3));
		// 在原来的基础上 减少 minus

		Duration minus2 = from.minus(7, ChronoUnit.MINUTES);// 根据传入的量 和单位 减少
															// 结果-14400秒
		Duration minus = from.minus(Duration.ofMillis(7)); // 根据传入的Duration 减少
															// 结果8880秒
		Duration minusDays = from.minusDays(1); // 减少1天 结果-75600秒
		Duration minusHours = from.minusHours(13); // 减少13小时 结果-36000秒 10小时
		Duration minusMinutes = from.minusMinutes(300); // 减少300分钟 结果-7200秒
		Duration minusSeconds = from.minusSeconds(5830); // 减少5380秒 结果4970秒
		Duration minusMillis = from.minusMillis(7000); // 减少7000毫秒 结果10793秒
		Duration minusNanos = from.minusNanos(9000); // 减少9000纳秒 结果10799秒
														// 999991000纳秒

	}

	@SuppressWarnings("unused")
	private static void plus() {
		Duration from = Duration.from(Duration.ofHours(3));
		// 添加传入的偏移
		Duration plus = from.plus(Duration.ofHours(3)); // 与传入的Duration相加
														// ofHours是两个小时 结果 18000
		Duration plus2 = from.plus(5, ChronoUnit.HOURS); // 与传入的指定单位时间量相加
															// 结果28800
		Duration plusDays = from.plusDays(1); // 加一天的量 结果97200
		Duration plusHours = from.plusHours(7); // 加7小时的量 结果36000
		Duration plusMinutes = from.plusMinutes(80); // 加80分钟的量 15600
		Duration plusSeconds = from.plusSeconds(535); // 加535分钟的量 结果11335
		Duration plusMillis = from.plusMillis(5000); // 加5000毫秒的量 结果10805
		Duration plusNanos = from.plusNanos(9000); // 加9000纳秒的量 结果10800秒 9000毫秒

	}

}
