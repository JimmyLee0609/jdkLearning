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
//		�½�URL
//		URL url = new URL("http://www.bing.com");
		URL url = new URL("file:/d:/temp/abc.txt");
//		��ȡЭ��
		String protocol = url.getProtocol();//http    file
//		��ȡ�ļ�
		String file = url.getFile();//""   /d:/temp/abc.txt
//	��ȡ����
		String host = url.getHost();//www.bing.com   ""
//		��ȡ��Ȩ
		String authority = url.getAuthority();//www.bing.com  null
//		��ȡĬ�϶˿�
		int defaultPort = url.getDefaultPort();//80   -1
//		��ȡ·��
		String path = url.getPath();//""   /d:/temp/abc.txt
//		��ȡ����
		String ref = url.getRef();//null  null
//		ת��Ϊ��չ��ʽ
		String externalForm = url.toExternalForm();//http://www.bing.com   file:/d:/temp/abc.txt
//		ת��ΪURI
		URI uri = url.toURI();//http://www.bing.com    file:/d:/temp/abc.txt
//		�Ƿ�����ͬ���ļ�
		boolean sameFile = url.sameFile(new URL("file:/d:/temp"));//false   false
//		��ȡ�˿�
		int port = url.getPort();//-1  -1
//		��ȡ��ѯ
		String query = url.getQuery();//null   null
//		��ȡ�û���Ϣ
		String userInfo = url.getUserInfo();//null  null
		
//		������
		URLConnection openConnection = url.openConnection();
//		����
		InputStream openStream = url.openStream();
//		���Ŀ����ֽ�
		int available = openStream.available();//9122   84
//		������,��ȡ������
		Object content = url.getContent();//sun.net.www.protocol.http.HttpURLConnection$HttpInputStream
		Object content2 = url.getContent(new Class[]{url.getClass()});//null  null
		
//		URL.setURLStreamHandlerFactory(fac);
	}

	@SuppressWarnings("unused")
	private static void uri()  throws MalformedURLException, URISyntaxException{
		// ����һ��URI
				URI create = URI.create("HTTP://WWW.BING.COM:80/ABG?TTT=YY#TEST");
				// ��uriת��Ϊurl
				URL url = create.toURL();
				// �Ƚ�����uri,��Э�����ĸ��ʼ�Ƚ�
				int compareTo = create.compareTo(url.toURI());// 0
				// ��ȡȨ�ޣ����� [[userinfo@]hostname[:port]]
				String authority = create.getAuthority();// WWW.BING.COM:80
				// ��ȡƬ��
				String fragment = create.getFragment();// TEST
				// ��ȡ����
				String host = create.getHost();// WWW.BING.COM
				// ��ȡ�˿�
				int port = create.getPort();// 80
				// ��ȡԭʼ��Ȩ��
				String rawAuthority = create.getRawAuthority();// WWW.BING.COM:80
				// ��ȡԭʼ��Ƭ��
				String rawFragment = create.getRawFragment();// TEST
				// ��ȡԭʼ��·��
				String rawPath = create.getRawPath();// /ABG
				// ��ȡԭʼ���û���Ϣ
				String rawUserInfo = create.getRawUserInfo();// NULL
				// ��ȡЭ���ָ������
				String schemeSpecificPart = create.getSchemeSpecificPart();// //WWW.BING.COM:80/ABG?TTT=YY
				// �Ƿ��Ǿ���·��
				boolean absolute = create.isAbsolute();// true
				// �Ƿ���͸����
				boolean opaque = create.isOpaque();// false
				// �����·��ת��Ϊ����·��
				URI normalize = create.normalize();//
				URI parseServerAuthority = create.parseServerAuthority();
				// ��ȡ·�������·��
				URI relativize = create.relativize(URI.create("HTTP://WWW.BING.COM:80/ABC"));// HTTP://WWW.BING.COM:80/ABC
				URI relativize1 = create.relativize(URI.create("HTTP://WWW.BING.COM:80/ABG/ABC"));// ABC

				// �����·��ת��Ϊ����·��
				URI resolve = create.resolve("/bbb/sss");// HTTP://WWW.BING.COM:80/bbb/sss
				URI resolve3 = create.resolve("bbb/sss");// HTTP://WWW.BING.COM:80/bbb/sss
				URI resolve2 = create.resolve(URI.create("/ZZZ/DDD"));// HTTP://WWW.BING.COM:80/ZZZ/DDD

				// �����·��������Ϊ����·��
				URI create2 = URI.create("file:/d:/temp/abc/../");
				URI normalize2 = create2.normalize();// file:/d:/temp/
				URI create3 = URI.create("file:/d:/temp/abc/./bcf");
				URI normalize3 = create3.normalize();// file:/d:/temp/abc/bcf		
	}

}
