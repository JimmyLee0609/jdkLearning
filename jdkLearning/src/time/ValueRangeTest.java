package time;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.ValueRange;

public class ValueRangeTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ValueRange of = ValueRange.of(13, 79);// 13 - 79
		ValueRange of2 = ValueRange.of(7, 3, 9);// 7 - 3/9
		ValueRange of3 = ValueRange.of(7, 21, 5, 23);// 7/21 - 5/23

		long largestMinimum = of2.getLargestMinimum();// 7
		long maximum = of2.getMaximum();// 9
		long minimum = of2.getMinimum();// 7
		long smallestMaximum = of2.getSmallestMaximum();// 3
		boolean fixed = of2.isFixed();// false
		boolean intValue = of.isIntValue();// 是否是int范围的值 true
		boolean validIntValue = of.isValidIntValue(55);// 是否是int范围的有效值 true
		boolean validValue = of.isValidValue(73l);// min<?<max true
		String string = of.toString();// 13 - 79
		int checkValidIntValue = of.checkValidIntValue(20, ChronoField.DAY_OF_MONTH);// 20
		long checkValidValue = of.checkValidValue(50l, ChronoField.DAY_OF_MONTH);// 50
		int c = 0;

	}

}
