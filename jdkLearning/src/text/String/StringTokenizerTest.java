package text.String;

import java.util.StringTokenizer;

public class StringTokenizerTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String label = "this is a label for test StringTokenizer!";
		// ���ַ������� �ַ��ָ���
		StringTokenizer stringTokenizer = new StringTokenizer(label);
		// ʹ���ַ����������ָ��� �����ָ���
		StringTokenizer stringTokenizer1 = new StringTokenizer(label, "delim");
		// true�ָ������� false�ָ���������
		StringTokenizer stringTokenizer2 = new StringTokenizer(label, "delim", true);
		StringTokenizer stringTokenizer3 = new StringTokenizer(label, "delim", false);

		boolean hasMoreTokens = stringTokenizer.hasMoreTokens();
		boolean hasMoreElements = stringTokenizer.hasMoreElements();// ����hasMoreTokens

		String nextToken = stringTokenizer.nextToken();
		String nextToken2 = stringTokenizer.nextToken("delim");
		Object nextElement = stringTokenizer.nextElement();// ����nextToken
	}

}
