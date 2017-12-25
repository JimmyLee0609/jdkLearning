package net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.security.Permission;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class URLConnectionTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// urlencodedecode();
		// urlOpenConnection();
//trySocket();
		tryConnect();
		
		
		
	}

	private static void trySocket() throws IOException {
//		新建一个Socket套接字
		Socket socket = new Socket();
//		新建一个套接字地址，远程地址，用于连接
		InetSocketAddress inetSocketAddress = new InetSocketAddress("www.baidu.com",80);
//		套接字连接到远程地址
		socket.connect(inetSocketAddress);
//		获取套接字的输出流
		OutputStream outputStream = socket.getOutputStream();
//		将输出流进行转换
		BufferedOutputStream bos = new BufferedOutputStream(outputStream);
		PrintStream out = new PrintStream(bos);
//		HTTP的报文请求头
		out.println("GET / HTTP/1.1");
		out.println("Accept: text/html, application/xhtml+xml, image/jxr, */*");
		out.println("Accept-Language: zh-CN");
		out.println("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 Edge/15.15063");
		out.println("Accept-Encoding: gzip, deflate");
		out.println("Host: www.baidu.com");
		out.println("Connection: Keep-Alive");
		out.println("Accept-Charset: utf-8");
//		报文格式换行
		out.println();
//		请求体
		out.println();
		out.flush();
//		获取套接字的输入流
		InputStream inputStream = socket.getInputStream();
//		将流转换成通道
		ReadableByteChannel newChannel = Channels.newChannel(inputStream); 
//		使用缓冲区操作通道
		ByteBuffer allocate = ByteBuffer.allocate(1024);
		newChannel.read(allocate);
		int read = newChannel.read(allocate);
		Charset defaultCharset = Charset.forName("utf-8");
		allocate.rewind();
		CharsetDecoder newDecoder = defaultCharset.newDecoder();
		CharBuffer decode = newDecoder.decode(allocate);
		
//		逐行读取   java的类StringTokenizer可以根据传入的字符串来将流中的数据分割读取
		StringTokenizer stringTokenizer = new StringTokenizer(decode.toString(),"\r\n");
		
		String nextToken = stringTokenizer.nextToken();
		
		System.out.println(nextToken);
		String nextToken2 = stringTokenizer.nextToken();
		System.out.println(nextToken2);
		
		socket.close();
	}

	@SuppressWarnings("unused")
	private static void tryConnect() throws IOException {
//		新建一个URL对象
		URL url = new URL("http://www.imooc.com:80/");
//		获取协议    http
		String protocol = url.getProtocol();
//		打开连接
		HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
//		获取连接的请求头
		Map<String, List<String>> requestProperties = openConnection.getRequestProperties();
		for (String reqs : requestProperties.keySet()) {
			List<String> list = requestProperties.get(reqs);
			System.out.println(reqs + Arrays.toString(list.toArray()));
		}
		openConnection.setDoOutput(true);
		openConnection.setDoInput(true);
		openConnection.setUseCaches(true);
		HttpURLConnection.setFollowRedirects(true);
		openConnection.addRequestProperty("Accept", "text/html, application/xhtml+xml, */*");
		openConnection.addRequestProperty("Accept-Encoding", "gzip, default");
		openConnection.addRequestProperty("Accept-Language", "cn");
		openConnection.addRequestProperty("Connection", "Keep-Alive");
		openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 Edge/15.15063");
		
		Map<String, List<String>> requestProperties2 = openConnection.getRequestProperties();
		for (String reqs : requestProperties2.keySet()) {
			try{
			List<String> list = requestProperties.get(reqs);
			System.out.println(reqs + Arrays.toString(list.toArray()));
			}catch(NullPointerException e){
				System.out.println(reqs+"----exception---"+e.getMessage());
			}
		}
		openConnection.connect();
		System.out.println("===============响应=====================");
		Object content = openConnection.getContent();
		
		String responseMessage = openConnection.getResponseMessage();
		System.out.println(responseMessage);
		
		
		
		System.out.println("===============响应头=====================");
//		响应头
		Map<String, List<String>> headerFields = openConnection.getHeaderFields();
		for (String temp : headerFields.keySet()) {
			String headerField = openConnection.getHeaderField(temp);
			System.out.println(temp+" :  "+headerField);
		}
		String contentType = openConnection.getContentType();
		URL url2 = openConnection.getURL();
		openConnection.connect();
		
		
	}

	@SuppressWarnings("unused")
	private static void urlOpenConnection() throws IOException {
		// 新建url对象
		URL url = new URL("http://www.baidu.com");
		// 打开连接
		java.net.URLConnection openConnection = url.openConnection();// HttpURLConnection:http://www.baidu.com
		// 获取字节输入流
		InputStream inputStream = openConnection.getInputStream();// HttpURLConnection$HttpInputStream
		// 获取最后修改时间
		long lastModified = openConnection.getLastModified();
		// 获取content-type的值
		String contentType = openConnection.getContentType();// text/html
		// 获取allowUserInteraction 字段的值
		boolean allowUserInteraction = openConnection.getAllowUserInteraction();// false
		// 获取连接超时
		int connectTimeout = openConnection.getConnectTimeout();// 0
		// 获取内容长度
		int contentLength = openConnection.getContentLength();// 2381
		long contentLengthLong = openConnection.getContentLengthLong();// 2381
		// 获取内容
		Object content = openConnection.getContent();// .HttpURLConnection$HttpInputStream
		// 获取内容编码
		String contentEncoding = openConnection.getContentEncoding();// null
		// 获取header的字段
		Map<String, List<String>> headerFields = openConnection.getHeaderFields();
		for (String string : headerFields.keySet()) {
			List<String> list = headerFields.get(string);
			System.out.println(string + "field: " + Arrays.toString(list.toArray()));
		}
		// 湖区URL连接实例
		URL url2 = openConnection.getURL();// http://www.baidu.com

		// 获取许可
		// ("java.net.SocketPermission" "www.baidu.com:80" "connect,resolve")
		Permission permission = openConnection.getPermission();
		// 获取UseCaches字段的值
		boolean useCaches = openConnection.getUseCaches();// true
		// 获取请求属性
		/*
		 * Map<String, List<String>> requestProperties =
		 * openConnection.getRequestProperties(); for (String string :
		 * requestProperties.keySet()) { List<String> list =
		 * requestProperties.get(string);
		 * System.out.println(string+"requestProperties:  "+Arrays.toString(list
		 * .toArray())); }
		 */
		// 获取读取超时
		int readTimeout = openConnection.getReadTimeout();// 0
		// 获取输出流
		// OutputStream outputStream = openConnection.getOutputStream();
		// 获取最后修改时间
		long ifModifiedSince = openConnection.getIfModifiedSince();// 0
		// 获取时间
		long date = openConnection.getDate();
		// 获取默认的缓存
		boolean defaultUseCaches = openConnection.getDefaultUseCaches();// true
		// 返回此 URLConnection 的 doInput 标志的值。
		boolean doInput = openConnection.getDoInput();// true
		// 返回此 URLConnection 的 doOutput 标志的值。
		boolean doOutput = openConnection.getDoOutput();// false
		// 返回 expires 头字段的值。
		long expiration = openConnection.getExpiration();// 0

		openConnection.setUseCaches(true);
	}

	@SuppressWarnings("unused")
	private static void urlencodedecode() throws UnsupportedEncodingException {
		String encode = URLEncoder.encode("www.baicu.com", "utf-8");// www.baicu.com
		String decode = URLDecoder.decode(encode, "utf-16");// www.baicu.com

		// 转换为 % ASSICC码
		String encode2 = URLEncoder.encode("www.baicu.com?我是谁", "utf-8");// www.baicu.com%3F%E6%88%91%E6%98%AF%E8%B0%81
		String decode2 = URLDecoder.decode(encode2, "utf-8");// www.baicu.com?我是谁

	}

}
