package time;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;

public class ClockTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 静态方法
		Clock systemUTC = Clock.systemUTC();// 只保留时区信息 SystemClock[Z]
		Clock systemDefaultZone = Clock.systemDefaultZone();// 只保留时区信息
															// SystemClock[GMT+08:00]

		ZoneId of = ZoneId.systemDefault();// GMT+08:00

		Clock system = Clock.system(of);// SystemClock[GMT+08:00]

		Clock tickSeconds = Clock.tickSeconds(of);// TickClock[SystemClock[GMT+08:00],PT1S]
		Instant instant3 = tickSeconds.instant();// 2017-10-11T09:57:05Z
		Clock tickMinutes = Clock.tickMinutes(of);// TickClock[SystemClock[GMT+08:00],PT1M]
		Instant instant4 = tickMinutes.instant();// 2017-10-11T09:57:00Z
		Clock tick = Clock.tick(system, Duration.ofMinutes(100));// TickClock[SystemClock[Z],PT1H40M]
		Instant instant2 = tick.instant();// 2017-10-11T08:20:00Z

		// OffsetClock[SystemClock[Z],PT50S]
		Clock offset = Clock.offset(systemUTC, Duration.ofMillis(50000));// 保存一个Clock实例，和偏移量Duration
		Instant instant5 = offset.instant();// 2017-10-11T10:00:06.466Z

		// SystemClock[GMT+08:00]
		Clock withZone = systemDefaultZone.withZone(ZoneId.of("+01:00"));// 保存时区实例
																			// 时间跟随系统
		Instant instant6 = withZone.instant();// 2017-10-11T09:59:16.466Z

		// FixedClock[2017-10-11T09:34:21.710Z,GMT+08:00]
		Clock fixed = Clock.fixed(Calendar.getInstance().toInstant(), of);// 保存Instance
																			// 和
																			// 时区
																			// 实例
		Instant instant7 = fixed.instant();// 2017-10-11T09:59:16.466Z
		// 实例方法
		ZoneId zone = systemUTC.getZone();// 获取对象保留的时区 Z
		Instant instant = systemUTC.instant();// 根据当前Clock的时间获取Instant实例
												// 2017-10-11T09:34:22.069Z
		long millis = systemUTC.millis();// 获取当前毫秒数 1507714462069
		String string = system.toString();
	}

}
