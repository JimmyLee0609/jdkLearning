package bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class PropertiesTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// Properties的方法
//		新建一个Properties
		Properties properties = new Properties();


//		根据键获取值
		String property = properties.getProperty("key");
		String property2 = properties.getProperty("key", "defauleValue");//键对应的位置为null就返回传入的默认值
//		设置键值对,返回key原来的值
		Object setProperty = properties.setProperty("key", "value");
//		获取键的名字
		Set<String> stringPropertyNames = properties.stringPropertyNames();
		Enumeration<?> propertyNames = properties.propertyNames();

//		将属性列表打印到指定的输出流
		File file = new File("./src/bundle/myProperties.properties");
		File fileXml = new File("./src/bundle/myProperties.xml");
		FileOutputStream fi = new FileOutputStream(file);
		FileOutputStream fixml = new FileOutputStream(fileXml);
		properties.list(System.out);
//		properties.save(fi, "comments");
//		properties.store(fi, "comments");
		properties.storeToXML(fixml, "comment", "utf-8");

		Properties properties2 = new Properties();
//		从文件中读取
//		properties2.load(new FileInputStream(file));//读properties文件
		properties2.loadFromXML(new FileInputStream(fileXml));//读xml文件
		
		// HashTable里面的方法
//		添加
		Object put = properties.put("key", "value");//添加一组键值对，如果key存在了就替换掉原来的value
		properties.putAll(properties);//将一个集合添加进来
//		是否包含
		boolean containsKey = properties.containsKey("key");//是否包含制指点键
		boolean contains = properties.contains("value");//是否包含指定值
		boolean containsValue2 = properties.containsValue("value");//是否包含指定值
		
		Object object = properties.get("key");//根据键获取值
		Object orDefault = properties.getOrDefault("key", "default Value");//根据键获取值，如果键对应的位置为null，就返回传入的默认值
		
//		获取键值对的枚举
		Enumeration<Object> elements = properties.elements();
//		获取键值的枚举
		Enumeration<Object> keys = properties.keys();
//		获取值集，在这里改变值集会影响集合中的值
		Collection<Object> values = properties.values();
//		获取键集,在这里改变键值会影响集合中的键
		Set<Object> keySet = properties.keySet();
//		keySet.add("");不能对结构进行改变
//		获取键值对集
		Set<Entry<Object, Object>> entrySet2 = properties.entrySet();
		
//		如果存在传入的键值对，就将值换成传入的新值
		boolean replace = properties.replace("key", "oldValue", "newValue");
//		将集合中的值 ， 与传入的值，进行传入算法的运算将返回的值覆盖原来的值    
		properties.replaceAll((Object old, Object newO) -> {
			return newO;
		});
//		如果存在key就将key中的值与传入的value进行传入算法的运算，key不存在就不尽兴运算
		properties.merge("keyy", "value",
				(Object old,Object value)->{
					return old;
					});
		
		Object clone = properties.clone();
//		
		properties.forEach((Object key, Object value) -> {
			//do something   遍历键值对，并对键值对进行传入算法的操作
		});
//		如果key不存在,就将key进行运算获取新的value,将新的键值对添加到集合中，并返回value
		properties.computeIfAbsent("keyyy", (Object key) -> {
			Object keyy=key;
			return "value";
		});
//		如果键存在就进行传入的算法，key就是传入的key，value就是key对应的value；
		properties.computeIfPresent("key", (Object key, Object value) -> {
			Object onee=key;
			Object secondd=value;
			return value;
		});
//		如果键存在，就进行传入的算法     key就是传入的key，value就是对应的value.
//		如果key不存在，也会进行操作，就是value变成null
		properties.compute("keyo", (Object key, Object value) -> {
			Object firstt=key;
			Object secondd=value;
			return value;
		});
//		移除与传入键相同的第一对键值对
		properties.remove("key");
//		移除第一对与传入键值对相同的对
		properties.remove("key", "value");
		properties.clear();
	}

}
