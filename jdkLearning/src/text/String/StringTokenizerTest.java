package text.String;

import java.util.StringTokenizer;

public class StringTokenizerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String label = "this is a label for test StringTokenizer!";
		// 用字符串创建 字符分割器
		StringTokenizer stringTokenizer = new StringTokenizer(label);
		// 使用字符串并声明分隔符 创建分割器
		StringTokenizer stringTokenizer1 = new StringTokenizer(label, "delim");
		// true分隔符返回 false分隔符不返回
		StringTokenizer stringTokenizer2 = new StringTokenizer(label, "delim", true);
		StringTokenizer stringTokenizer3 = new StringTokenizer(label, "delim", false);

		boolean hasMoreTokens = stringTokenizer.hasMoreTokens();
		boolean hasMoreElements = stringTokenizer.hasMoreElements();// 就是hasMoreTokens

		String nextToken = stringTokenizer.nextToken();
		String nextToken2 = stringTokenizer.nextToken("delim");
		Object nextElement = stringTokenizer.nextElement();// 就是nextToken
	}

}
