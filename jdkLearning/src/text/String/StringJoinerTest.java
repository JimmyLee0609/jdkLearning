package text.String;

import java.util.StringJoiner;

public class StringJoinerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		StringJoiner joiner = new StringJoiner(null);
		int length = joiner.length();
		StringJoiner merge = joiner.merge(joiner);
		joiner.setEmptyValue(null);
		joiner.add(null);
		;

	}

}
