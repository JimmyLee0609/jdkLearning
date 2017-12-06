package net;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class URITest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws URISyntaxException, IOException {
		url();
		uri();
	}

	@SuppressWarnings("unused")
	private static void url() throws IOException, URISyntaxException {
//		新建URL
//		URL url = new URL("http://www.bing.com");
		URL url = new URL("file:/d:/temp/abc.txt");
//		获取协议
		String protocol = url.getProtocol();//http    file
//		获取文件
		String file = url.getFile();//""   /d:/temp/abc.txt
//	获取主机
		String host = url.getHost();//www.bing.com   ""
//		获取授权
		String authority = url.getAuthority();//www.bing.com  null
//		获取默认端口
		int defaultPort = url.getDefaultPort();//80   -1
//		获取路径
		String path = url.getPath();//""   /d:/temp/abc.txt
//		获取引用
		String ref = url.getRef();//null  null
//		转换为拓展形式
		String externalForm = url.toExternalForm();//http://www.bing.com   file:/d:/temp/abc.txt
//		转换为URI
		URI uri = url.toURI();//http://www.bing.com    file:/d:/temp/abc.txt
//		是否是相同的文件
		boolean sameFile = url.sameFile(new URL("file:/d:/temp"));//false   false
//		获取端口
		int port = url.getPort();//-1  -1
//		获取查询
		String query = url.getQuery();//null   null
//		获取用户信息
		String userInfo = url.getUserInfo();//null  null
		
//		打开连接
		URLConnection openConnection = url.openConnection();
//		打开流
		InputStream openStream = url.openStream();
//		流的可用字节
		int available = openStream.available();//9122   84
//		打开连接,获取上下文
		Object content = url.getContent();//sun.net.www.protocol.http.HttpURLConnection$HttpInputStream
		Object content2 = url.getContent(new Class[]{url.getClass()});//null  null
		
//		URL.setURLStreamHandlerFactory(fac);
	}

	@SuppressWarnings("unused")
	private static void uri()  throws MalformedURLException, URISyntaxException{
		// 创建一个URI
				URI create = URI.create("HTTP://WWW.BING.COM:80/ABG?TTT=YY#TEST");
				// 将uri转换为url
				URL url = create.toURL();
				// 比较两个uri,从协议的字母开始比较
				int compareTo = create.compareTo(url.toURI());// 0
				// 获取权限，就是 [[userinfo@]hostname[:port]]
				String authority = create.getAuthority();// WWW.BING.COM:80
				// 获取片段
				String fragment = create.getFragment();// TEST
				// 获取主机
				String host = create.getHost();// WWW.BING.COM
				// 获取端口
				int port = create.getPort();// 80
				// 获取原始的权限
				String rawAuthority = create.getRawAuthority();// WWW.BING.COM:80
				// 获取原始的片段
				String rawFragment = create.getRawFragment();// TEST
				// 获取原始的路径
				String rawPath = create.getRawPath();// /ABG
				// 获取原始的用户信息
				String rawUserInfo = create.getRawUserInfo();// NULL
				// 获取协议的指明部分
				String schemeSpecificPart = create.getSchemeSpecificPart();// //WWW.BING.COM:80/ABG?TTT=YY
				// 是否是绝对路径
				boolean absolute = create.isAbsolute();// true
				// 是否是透明的
				boolean opaque = create.isOpaque();// false
				// 将相对路径转换为绝对路径
				URI normalize = create.normalize();//
				URI parseServerAuthority = create.parseServerAuthority();
				// 获取路径的相对路径
				URI relativize = create.relativize(URI.create("HTTP://WWW.BING.COM:80/ABC"));// HTTP://WWW.BING.COM:80/ABC
				URI relativize1 = create.relativize(URI.create("HTTP://WWW.BING.COM:80/ABG/ABC"));// ABC

				// 将相对路径转换为绝对路径
				URI resolve = create.resolve("/bbb/sss");// HTTP://WWW.BING.COM:80/bbb/sss
				URI resolve3 = create.resolve("bbb/sss");// HTTP://WWW.BING.COM:80/bbb/sss
				URI resolve2 = create.resolve(URI.create("/ZZZ/DDD"));// HTTP://WWW.BING.COM:80/ZZZ/DDD

				// 将相对路径正常化为绝对路径
				URI create2 = URI.create("file:/d:/temp/abc/../");
				URI normalize2 = create2.normalize();// file:/d:/temp/
				URI create3 = URI.create("file:/d:/temp/abc/./bcf");
				URI normalize3 = create3.normalize();// file:/d:/temp/abc/bcf		
	}

}
