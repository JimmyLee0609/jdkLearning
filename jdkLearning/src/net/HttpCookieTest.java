package net;

import java.net.HttpCookie;
import java.util.List;

public class HttpCookieTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// �½�һ��Cookie�ķ�ʽ
		HttpCookie httpCookie = new HttpCookie("PID", "PID213");
		httpCookie.setComment("�Ҳ���һ��Cookie�ı�עl");// �������������ʾ��ע��Ϣ
		httpCookie.setCommentURL("www.localhost.com");// �����������ʾ�ı�ע��ַ
		httpCookie.setDiscard(false);// �����Ƿ������Cookie
		httpCookie.setDomain("localhost.com");// ��������
		httpCookie.setHttpOnly(false);// �Ƿ���ֻ����HttpЭ��
		httpCookie.setMaxAge(3600);// ��������� ��Ϊ��λ
		httpCookie.setPath("/");// ·��
		httpCookie.setPortlist("80");// �˿�
		httpCookie.setSecure(false);// �Ƿ���Ҫ��ȫͨ������Cookie
		httpCookie.setValue("PID214");// ����name��ֵ
		httpCookie.setVersion(1);// ���ð汾

		boolean hasExpired = httpCookie.hasExpired();// false

		httpCookie.setMaxAge(0);// ��������� ��Ϊ��λ
		boolean hasExpired2 = httpCookie.hasExpired();// true

		String string = httpCookie.toString();

		// ����������Ƿ������е�ʵ�÷�����
		// ����������� y.x.foo.com cookie�������� Domain=.foo.com ��Ͳ�ƥ��� . �ĸ����Բ���
		// �������� example.local Cookie��������Domain=.local ���ƥ����
		boolean domainMatches = HttpCookie.domainMatches(".local", "example.local");// true
		boolean domainMatches2 = HttpCookie.domainMatches(".foo.com", " y.x.foo.com");// false
		boolean domainMatches3 = HttpCookie.domainMatches("x.foo.com", " y.x.foo.com");// false
		boolean domainMatches4 = HttpCookie.domainMatches(".x.foo.com", " y.x.foo.com");// true

		// ����ȡ����Cookie�������ַ������ݽ�����HttpCookie
		parse();
	}

	@SuppressWarnings("unused")
	private static void parse() {

		List<HttpCookie> parse = HttpCookie
				.parse("Set-Cookie:REFF=\"PID394716392010\"" + ";Version=\"1\"" + ";Path=\"/myTestPath/path\""
						+ ";Comment=\"This is my Test Comment!\"" + ";CommentURL=\"http://www.testUrl.com\""
						+ ";Domain=\"localhost\"" + ";Discard" + ";Secure" + ";Port=\"80\"" + ";Max-Age=\"3600\"");
		// ��ȡ�������ĵ�һ��Cookie
		HttpCookie httpCookie = parse.get(0);
		// REFF="PID394716392010";$Path="/myTestPath/path";$Domain="localhost";$Port="80"
		// ��ȡComment���ԣ��������������ʾ���Cookie�ı�ע��Ϣ����Щ�������֧��
		String comment = httpCookie.getComment();// This is my Test Comment!
		// CommentURL���ԣ������������ʾ�ı�ע��Ϣ
		String commentURL = httpCookie.getCommentURL();// http://www.testUrl.com
		// Cookie������
		String name = httpCookie.getName();// REFF
		// Cookie���ƶ�Ӧ��ֵ
		String value = httpCookie.getValue();// PID394716392010

		// Domian���ԣ�����������ĺ�׺Ҫ�����ֵƥ�䣬��Ȼ�ᱻ����
		String domain = httpCookie.getDomain();// localhost

		// Path���ԣ�����·����Ҫ�����·����ͬ������������·����һ����
		String path = httpCookie.getPath();/// myTestPath/path

		long maxAge = httpCookie.getMaxAge();// 3600
		// ����ֱ���������Cookie
		boolean discard = httpCookie.getDiscard();// true
		// Cookie��Ҫʹ�ð�ȫ��Socket���ͣ�һ����TSL ssh
		boolean secure = httpCookie.getSecure();// true
		// Cookie�İ汾����rfc�� 1 ��netscape�汾 0 ������ֵ���ַ����費��Ҫ�� ��
		int version = httpCookie.getVersion();// 1
		// ��ȡCookie������Ķ˿ں�
		String portlist = httpCookie.getPortlist();// 80
	}

}
