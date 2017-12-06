package net;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookiesManagerTest {

	public static void main(String[] args) throws IOException {
//		simpleUse();
		
		
		String urlString = "http://www.baidu.com";
		CookieManager ckman = new CookieManager();
	    
	    
	    URL url = new URL(urlString);
	    URLConnection connection = url.openConnection();
	    connection.getContent();
	    
	    CookieStore ckStore = ckman.getCookieStore();
	    
	    List<URI> uriList=ckStore.getURIs();
	    for (URI uri : uriList) {
	        System.out.println(uri.getHost());
	    }
	    List<HttpCookie> cks = ckStore.getCookies();
	    for (HttpCookie ck : cks) {
	      System.out.println(ck);
	    }
System.out.println("finish");
	}

	@SuppressWarnings("unused")
	private static void simpleUse() throws IOException {

	    CookieManager ckman = new CookieManager();
	    cookieStore(ckman);
	    cookieHandler(ckman);
	    
	    HashMap<String, List<String>> requestHeader = new HashMap<String,List<String>>();
//	    ������ʵ����ֻ����ҪURI�����ݣ������û���õ�, ֱ����cookieStore�в���
	    Map<String, List<String>> map = ckman.get(URI.create(""), requestHeader);	
	    
//	    ����cookie�Ľ��ղ���
	    ckman.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
	    CookiePolicy acceptAll = CookiePolicy.ACCEPT_ALL;//ȫ������
	    CookiePolicy acceptNone = CookiePolicy.ACCEPT_NONE;//ȫ��������
	    CookiePolicy acceptOriginalServer = CookiePolicy.ACCEPT_ORIGINAL_SERVER;//������cookie�м�¼��Domainƥ��ķ�������Cookie
	    
	    HashMap<String, List<String>> responseHeaders = new HashMap<String,List<String>>();
//	    �����uri������� List�е��ַ�������ΪHttpCookieȻ�󱣴浽cookiestore��, �����ʱ��Ҳ���� uir=httpCookie�ķ�ʽ
	    ckman.put(URI.create(""), responseHeaders);
	}

	private static void cookieHandler(CookieManager ckman) throws IOException {
//		====================JDK��CookieManagerʵ��=========================
		
//		CookieHandler��ʵ�������CookieManager, ��������Ĭ��handler��null
	    CookieHandler.setDefault(ckman);
	    CookieHandler default1 = CookieHandler.getDefault();//null
	    
//	    �ὫList�е����ݽ���ΪHttpCookieȻ�󱣴浽Cookie��   uri=httpCookie
	    HashMap<String, List<String>> responseHeaders = new HashMap<String,List<String>>();
	    default1.put(URI.create(""), responseHeaders);
	    
//	    ��cookieStore�и���URI��ȡhttpCookie     ����Ĳ���ֻҪ����null������
	    HashMap<String, List<String>> requestHeaders = new HashMap<String,List<String>>();
	    default1.get(URI.create(""), requestHeaders);
	    
	}

	private static void cookieStore(CookieManager ckman) {
//		��ȡCookieManager��CookieStore   ��JDK��ʵ����   InMemoryCookieStore   ��uri=httpCookie�ķ�ʽ����ӳ��
		 CookieStore cookieStore = ckman.getCookieStore();
//		 ��ȡ���������Cookie
		    List<HttpCookie> cookies = cookieStore.getCookies();	
//		    ��ȡ���е�URI
		    List<URI> urIs = cookieStore.getURIs();
//		    ɾ�����еļ�¼
		    boolean removeAll = cookieStore.removeAll();
//			ɾ��ָ��URI��ָ��Cookie
		    boolean remove = cookieStore.remove(URI.create(""),new HttpCookie("",""));
//		    ��ȡָ��URI������Cookie
		    List<HttpCookie> list = cookieStore.get(URI.create(""));
//		    ��ָ��URI�����Ӧ��Cookie��ӵ�cookieStore�б���
		    cookieStore.add(URI.create(""), new HttpCookie("",""));
		    cookieStore.toString();
	}

}
