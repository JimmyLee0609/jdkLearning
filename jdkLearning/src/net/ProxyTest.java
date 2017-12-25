package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ProxyTest {

	public static void main(String[] args) throws IOException {
		Type http = Proxy.Type.HTTP;
		// 就是包装了一个类型和地址
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 5050));

		InetSocketAddress inetSocketAddress = new InetSocketAddress("192.16.3.103", 9898);
		Proxy proxy3 = new Proxy(Proxy.Type.HTTP, inetSocketAddress);

		URL url = new URL("http://www.baidu.com");
		URLConnection openConnection = url.openConnection(proxy3);
		Object content = openConnection.getContent();

		ProxySelector default1 = ProxySelector.getDefault();
		List<Proxy> select = default1.select(URI.create("http://www.baidu.com"));
		Proxy proxy2 = select.get(0);
		Type type = proxy2.type();
		SocketAddress address = proxy2.address();

	}

}
