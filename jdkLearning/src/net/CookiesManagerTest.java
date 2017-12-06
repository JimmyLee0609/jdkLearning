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
//	    方法在实现上只是需要URI的数据，后面的没有用到, 直接在cookieStore中查找
	    Map<String, List<String>> map = ckman.get(URI.create(""), requestHeader);	
	    
//	    设置cookie的接收策略
	    ckman.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
	    CookiePolicy acceptAll = CookiePolicy.ACCEPT_ALL;//全部接收
	    CookiePolicy acceptNone = CookiePolicy.ACCEPT_NONE;//全部不接收
	    CookiePolicy acceptOriginalServer = CookiePolicy.ACCEPT_ORIGINAL_SERVER;//仅接收cookie中记录的Domain匹配的服务器的Cookie
	    
	    HashMap<String, List<String>> responseHeaders = new HashMap<String,List<String>>();
//	    会根据uri将传入的 List中的字符串解析为HttpCookie然后保存到cookiestore中, 保存的时候也是用 uir=httpCookie的方式
	    ckman.put(URI.create(""), responseHeaders);
	}

	private static void cookieHandler(CookieManager ckman) throws IOException {
//		====================JDK的CookieManager实现=========================
		
//		CookieHandler的实现类就是CookieManager, 但是它的默认handler是null
	    CookieHandler.setDefault(ckman);
	    CookieHandler default1 = CookieHandler.getDefault();//null
	    
//	    会将List中的数据解析为HttpCookie然后保存到Cookie中   uri=httpCookie
	    HashMap<String, List<String>> responseHeaders = new HashMap<String,List<String>>();
	    default1.put(URI.create(""), responseHeaders);
	    
//	    从cookieStore中根据URI获取httpCookie     后面的参数只要不是null就行了
	    HashMap<String, List<String>> requestHeaders = new HashMap<String,List<String>>();
	    default1.get(URI.create(""), requestHeaders);
	    
	}

	private static void cookieStore(CookieManager ckman) {
//		获取CookieManager的CookieStore   ，JDK的实现是   InMemoryCookieStore   以uri=httpCookie的方式保存映射
		 CookieStore cookieStore = ckman.getCookieStore();
//		 获取保存的所有Cookie
		    List<HttpCookie> cookies = cookieStore.getCookies();	
//		    获取所有的URI
		    List<URI> urIs = cookieStore.getURIs();
//		    删除所有的记录
		    boolean removeAll = cookieStore.removeAll();
//			删除指定URI的指定Cookie
		    boolean remove = cookieStore.remove(URI.create(""),new HttpCookie("",""));
//		    获取指定URI的所有Cookie
		    List<HttpCookie> list = cookieStore.get(URI.create(""));
//		    将指定URI和其对应的Cookie添加的cookieStore中保存
		    cookieStore.add(URI.create(""), new HttpCookie("",""));
		    cookieStore.toString();
	}

}
