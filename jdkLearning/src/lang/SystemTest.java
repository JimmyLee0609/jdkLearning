package lang;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.channels.Channel;
import java.util.Map;
import java.util.Properties;

public class SystemTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		PrintStream err = System.err;
		InputStream in = System.in;
		PrintStream out = System.out;

		int[] src = new int[3];
		int[] dest = new int[3];
		System.arraycopy(src, 0, dest, 0, 3);

		Console console = System.console();//null

		long currentTimeMillis = System.currentTimeMillis();//1507967742623
		Properties properties = System.getProperties();
		properties.list(out);
		Map<String, String> getenv = System.getenv();
		for (String key : getenv.keySet()) {
			String string = getenv.get(key);
			System.out.println(key+"----"+string);
		}
		SecurityManager securityManager = System.getSecurityManager();//null

		int identityHashCode = System.identityHashCode(console);//0
		
		Channel inheritedChannel = System.inheritedChannel();//null

		String lineSeparator = System.lineSeparator();//"\r\n"
		long nanoTime = System.nanoTime();//409438875402454
		System.setErr(err);
		System.setIn(in);
		System.setOut(out);

		String mapLibraryName = System.mapLibraryName("");
		System.load("");//通过文件名字（全路径）读取native library
		System.loadLibrary("");//通过native library读取
//		System.setSecurityManager(securityManager);
		System.runFinalization();
		System.gc();
		System.exit(0);

	}

}
