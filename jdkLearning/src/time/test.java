package time;

import static java.time.temporal.ChronoUnit.FOREVER;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.time.Year;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;

public enum test implements TemporalField {

	// 枚举测试
	// 这个枚举类型，就像是字段创建本类对象 ，下面是传入的具体参数
	NANO_OF_SECOND("NanoOfSecond", NANOS, SECONDS, ValueRange.of(0, 999_999_999)),

	YEAR("Year", YEARS, FOREVER, ValueRange.of(Year.MIN_VALUE, Year.MAX_VALUE), "year"),

	OFFSET_SECONDS("OffsetSeconds", SECONDS, FOREVER, ValueRange.of(-18 * 3600, 18 * 3600));

	private final String name;
	private final TemporalUnit baseUnit;
	private final TemporalUnit rangeUnit;
	private final ValueRange range;
	private final String displayNameKey;

	private test(String name, TemporalUnit baseUnit, TemporalUnit rangeUnit, ValueRange range) {
		this.name = name;
		this.baseUnit = baseUnit;
		this.rangeUnit = rangeUnit;
		this.range = range;
		this.displayNameKey = null;
	}

	private test(String name, TemporalUnit baseUnit, TemporalUnit rangeUnit, ValueRange range, String displayNameKey) {
		this.name = name;
		this.baseUnit = baseUnit;
		this.rangeUnit = rangeUnit;
		this.range = range;
		this.displayNameKey = displayNameKey;
	}

	@Override
	public TemporalUnit getBaseUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemporalUnit getRangeUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueRange range() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDateBased() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTimeBased() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportedBy(TemporalAccessor temporal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getFrom(TemporalAccessor temporal) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <R extends Temporal> R adjustInto(R temporal, long newValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
