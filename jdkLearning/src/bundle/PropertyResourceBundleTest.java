package bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.Set;

public class PropertyResourceBundleTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException, IOException {
		PropertyResourceBundle bundle = new PropertyResourceBundle(new FileInputStream(new File("")));
		String string2 = bundle.getString("key");
		Enumeration<String> keys = bundle.getKeys();
		String baseBundleName = bundle.getBaseBundleName();
		Locale locale = bundle.getLocale();
		Set<String> keySet = bundle.keySet();
		boolean containsKey = bundle.containsKey("key");
		Object object = bundle.getObject("key");
		String[] stringArray = bundle.getStringArray("key");
		Object handleGetObject = bundle.handleGetObject("key");
		PropertyResourceBundle.clearCache();
		PropertyResourceBundle.clearCache(ClassLoader.getSystemClassLoader());
		
		PropertyResourceBundle.getBundle("baseName", 
				Locale.CHINA, 
				ClassLoader.getSystemClassLoader(), 
				Control.getControl(Arrays.asList("","")));
	}

}
