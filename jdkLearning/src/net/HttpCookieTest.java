package net;

import java.net.HttpCookie;
import java.util.List;

public class HttpCookieTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 新建一个Cookie的方式
		HttpCookie httpCookie = new HttpCookie("PID", "PID213");
		httpCookie.setComment("我测试一下Cookie的备注l");// 设置浏览器的显示备注信息
		httpCookie.setCommentURL("www.localhost.com");// 设置浏览器显示的备注网址
		httpCookie.setDiscard(false);// 代理是否丢弃这个Cookie
		httpCookie.setDomain("localhost.com");// 设置域名
		httpCookie.setHttpOnly(false);// 是否是只能用Http协议
		httpCookie.setMaxAge(3600);// 最大存活期限 秒为单位
		httpCookie.setPath("/");// 路径
		httpCookie.setPortlist("80");// 端口
		httpCookie.setSecure(false);// 是否需要安全通道传送Cookie
		httpCookie.setValue("PID214");// 设置name的值
		httpCookie.setVersion(1);// 设置版本

		boolean hasExpired = httpCookie.hasExpired();// false

		httpCookie.setMaxAge(0);// 最大存活期限 秒为单位
		boolean hasExpired2 = httpCookie.hasExpired();// true

		String string = httpCookie.toString();

		// 检查主机名是否在域中的实用方法。
		// 请求的主机是 y.x.foo.com cookie的属性是 Domain=.foo.com 这就不匹配的 . 的个数对不上
		// 请求主机 example.local Cookie的属性是Domain=.local 这就匹配了
		boolean domainMatches = HttpCookie.domainMatches(".local", "example.local");// true
		boolean domainMatches2 = HttpCookie.domainMatches(".foo.com", " y.x.foo.com");// false
		boolean domainMatches3 = HttpCookie.domainMatches("x.foo.com", " y.x.foo.com");// false
		boolean domainMatches4 = HttpCookie.domainMatches(".x.foo.com", " y.x.foo.com");// true

		// 将获取到的Cookie的完整字符串内容解析成HttpCookie
		parse();
	}

	@SuppressWarnings("unused")
	private static void parse() {

		List<HttpCookie> parse = HttpCookie
				.parse("Set-Cookie:REFF=\"PID394716392010\"" + ";Version=\"1\"" + ";Path=\"/myTestPath/path\""
						+ ";Comment=\"This is my Test Comment!\"" + ";CommentURL=\"http://www.testUrl.com\""
						+ ";Domain=\"localhost\"" + ";Discard" + ";Secure" + ";Port=\"80\"" + ";Max-Age=\"3600\"");
		// 获取解析到的第一个Cookie
		HttpCookie httpCookie = parse.get(0);
		// REFF="PID394716392010";$Path="/myTestPath/path";$Domain="localhost";$Port="80"
		// 获取Comment属性，用来在浏览器显示这个Cookie的备注信息。有些浏览器不支持
		String comment = httpCookie.getComment();// This is my Test Comment!
		// CommentURL属性，用于浏览器显示的备注信息
		String commentURL = httpCookie.getCommentURL();// http://www.testUrl.com
		// Cookie的名称
		String name = httpCookie.getName();// REFF
		// Cookie名称对应的值
		String value = httpCookie.getValue();// PID394716392010

		// Domian属性，请求的域名的后缀要与这个值匹配，不然会被抛弃
		String domain = httpCookie.getDomain();// localhost

		// Path属性，请求路径需要与这个路径相同，或者是请求路径的一部分
		String path = httpCookie.getPath();/// myTestPath/path

		long maxAge = httpCookie.getMaxAge();// 3600
		// 代理直接抛弃这个Cookie
		boolean discard = httpCookie.getDiscard();// true
		// Cookie需要使用安全的Socket发送，一般是TSL ssh
		boolean secure = httpCookie.getSecure();// true
		// Cookie的版本，有rfc版 1 和netscape版本 0 ，就是值的字符串需不需要加 “
		int version = httpCookie.getVersion();// 1
		// 获取Cookie的请求的端口号
		String portlist = httpCookie.getPortlist();// 80
	}

}
