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
//		�½�һ��Socket�׽���
		Socket socket = new Socket();
//		�½�һ���׽��ֵ�ַ��Զ�̵�ַ����������
		InetSocketAddress inetSocketAddress = new InetSocketAddress("www.baidu.com",80);
//		�׽������ӵ�Զ�̵�ַ
		socket.connect(inetSocketAddress);
//		��ȡ�׽��ֵ������
		OutputStream outputStream = socket.getOutputStream();
//		�����������ת��
		BufferedOutputStream bos = new BufferedOutputStream(outputStream);
		PrintStream out = new PrintStream(bos);
//		HTTP�ı�������ͷ
		out.println("GET / HTTP/1.1");
		out.println("Accept: text/html, application/xhtml+xml, image/jxr, */*");
		out.println("Accept-Language: zh-CN");
		out.println("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 Edge/15.15063");
		out.println("Accept-Encoding: gzip, deflate");
		out.println("Host: www.baidu.com");
		out.println("Connection: Keep-Alive");
		out.println("Accept-Charset: utf-8");
//		���ĸ�ʽ����
		out.println();
//		������
		out.println();
		out.flush();
//		��ȡ�׽��ֵ�������
		InputStream inputStream = socket.getInputStream();
//		����ת����ͨ��
		ReadableByteChannel newChannel = Channels.newChannel(inputStream); 
//		ʹ�û���������ͨ��
		ByteBuffer allocate = ByteBuffer.allocate(1024);
		newChannel.read(allocate);
		int read = newChannel.read(allocate);
		Charset defaultCharset = Charset.forName("utf-8");
		allocate.rewind();
		CharsetDecoder newDecoder = defaultCharset.newDecoder();
		CharBuffer decode = newDecoder.decode(allocate);
		
//		���ж�ȡ   java����StringTokenizer���Ը��ݴ�����ַ����������е����ݷָ��ȡ
		StringTokenizer stringTokenizer = new StringTokenizer(decode.toString(),"\r\n");
		
		String nextToken = stringTokenizer.nextToken();
		
		System.out.println(nextToken);
		String nextToken2 = stringTokenizer.nextToken();
		System.out.println(nextToken2);
		
		socket.close();
	}

	@SuppressWarnings("unused")
	private static void tryConnect() throws IOException {
//		�½�һ��URL����
		URL url = new URL("http://www.imooc.com:80/");
//		��ȡЭ��    http
		String protocol = url.getProtocol();
//		������
		HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
//		��ȡ���ӵ�����ͷ
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
		System.out.println("===============��Ӧ=====================");
		Object content = openConnection.getContent();
		
		String responseMessage = openConnection.getResponseMessage();
		System.out.println(responseMessage);
		
		
		
		System.out.println("===============��Ӧͷ=====================");
//		��Ӧͷ
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
		// �½�url����
		URL url = new URL("http://www.baidu.com");
		// ������
		java.net.URLConnection openConnection = url.openConnection();// HttpURLConnection:http://www.baidu.com
		// ��ȡ�ֽ�������
		InputStream inputStream = openConnection.getInputStream();// HttpURLConnection$HttpInputStream
		// ��ȡ����޸�ʱ��
		long lastModified = openConnection.getLastModified();
		// ��ȡcontent-type��ֵ
		String contentType = openConnection.getContentType();// text/html
		// ��ȡallowUserInteraction �ֶε�ֵ
		boolean allowUserInteraction = openConnection.getAllowUserInteraction();// false
		// ��ȡ���ӳ�ʱ
		int connectTimeout = openConnection.getConnectTimeout();// 0
		// ��ȡ���ݳ���
		int contentLength = openConnection.getContentLength();// 2381
		long contentLengthLong = openConnection.getContentLengthLong();// 2381
		// ��ȡ����
		Object content = openConnection.getContent();// .HttpURLConnection$HttpInputStream
		// ��ȡ���ݱ���
		String contentEncoding = openConnection.getContentEncoding();// null
		// ��ȡheader���ֶ�
		Map<String, List<String>> headerFields = openConnection.getHeaderFields();
		for (String string : headerFields.keySet()) {
			List<String> list = headerFields.get(string);
			System.out.println(string + "field: " + Arrays.toString(list.toArray()));
		}
		// ����URL����ʵ��
		URL url2 = openConnection.getURL();// http://www.baidu.com

		// ��ȡ���
		// ("java.net.SocketPermission" "www.baidu.com:80" "connect,resolve")
		Permission permission = openConnection.getPermission();
		// ��ȡUseCaches�ֶε�ֵ
		boolean useCaches = openConnection.getUseCaches();// true
		// ��ȡ��������
		/*
		 * Map<String, List<String>> requestProperties =
		 * openConnection.getRequestProperties(); for (String string :
		 * requestProperties.keySet()) { List<String> list =
		 * requestProperties.get(string);
		 * System.out.println(string+"requestProperties:  "+Arrays.toString(list
		 * .toArray())); }
		 */
		// ��ȡ��ȡ��ʱ
		int readTimeout = openConnection.getReadTimeout();// 0
		// ��ȡ�����
		// OutputStream outputStream = openConnection.getOutputStream();
		// ��ȡ����޸�ʱ��
		long ifModifiedSince = openConnection.getIfModifiedSince();// 0
		// ��ȡʱ��
		long date = openConnection.getDate();
		// ��ȡĬ�ϵĻ���
		boolean defaultUseCaches = openConnection.getDefaultUseCaches();// true
		// ���ش� URLConnection �� doInput ��־��ֵ��
		boolean doInput = openConnection.getDoInput();// true
		// ���ش� URLConnection �� doOutput ��־��ֵ��
		boolean doOutput = openConnection.getDoOutput();// false
		// ���� expires ͷ�ֶε�ֵ��
		long expiration = openConnection.getExpiration();// 0

		openConnection.setUseCaches(true);
	}

	@SuppressWarnings("unused")
	private static void urlencodedecode() throws UnsupportedEncodingException {
		String encode = URLEncoder.encode("www.baicu.com", "utf-8");// www.baicu.com
		String decode = URLDecoder.decode(encode, "utf-16");// www.baicu.com

		// ת��Ϊ % ASSICC��
		String encode2 = URLEncoder.encode("www.baicu.com?����˭", "utf-8");// www.baicu.com%3F%E6%88%91%E6%98%AF%E8%B0%81
		String decode2 = URLDecoder.decode(encode2, "utf-8");// www.baicu.com?����˭

	}

}
