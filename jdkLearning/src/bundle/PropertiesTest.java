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
		// Properties�ķ���
//		�½�һ��Properties
		Properties properties = new Properties();


//		���ݼ���ȡֵ
		String property = properties.getProperty("key");
		String property2 = properties.getProperty("key", "defauleValue");//����Ӧ��λ��Ϊnull�ͷ��ش����Ĭ��ֵ
//		���ü�ֵ��,����keyԭ����ֵ
		Object setProperty = properties.setProperty("key", "value");
//		��ȡ��������
		Set<String> stringPropertyNames = properties.stringPropertyNames();
		Enumeration<?> propertyNames = properties.propertyNames();

//		�������б��ӡ��ָ���������
		File file = new File("./src/bundle/myProperties.properties");
		File fileXml = new File("./src/bundle/myProperties.xml");
		FileOutputStream fi = new FileOutputStream(file);
		FileOutputStream fixml = new FileOutputStream(fileXml);
		properties.list(System.out);
//		properties.save(fi, "comments");
//		properties.store(fi, "comments");
		properties.storeToXML(fixml, "comment", "utf-8");

		Properties properties2 = new Properties();
//		���ļ��ж�ȡ
//		properties2.load(new FileInputStream(file));//��properties�ļ�
		properties2.loadFromXML(new FileInputStream(fileXml));//��xml�ļ�
		
		// HashTable����ķ���
//		���
		Object put = properties.put("key", "value");//���һ���ֵ�ԣ����key�����˾��滻��ԭ����value
		properties.putAll(properties);//��һ��������ӽ���
//		�Ƿ����
		boolean containsKey = properties.containsKey("key");//�Ƿ������ָ���
		boolean contains = properties.contains("value");//�Ƿ����ָ��ֵ
		boolean containsValue2 = properties.containsValue("value");//�Ƿ����ָ��ֵ
		
		Object object = properties.get("key");//���ݼ���ȡֵ
		Object orDefault = properties.getOrDefault("key", "default Value");//���ݼ���ȡֵ���������Ӧ��λ��Ϊnull���ͷ��ش����Ĭ��ֵ
		
//		��ȡ��ֵ�Ե�ö��
		Enumeration<Object> elements = properties.elements();
//		��ȡ��ֵ��ö��
		Enumeration<Object> keys = properties.keys();
//		��ȡֵ����������ı�ֵ����Ӱ�켯���е�ֵ
		Collection<Object> values = properties.values();
//		��ȡ����,������ı��ֵ��Ӱ�켯���еļ�
		Set<Object> keySet = properties.keySet();
//		keySet.add("");���ܶԽṹ���иı�
//		��ȡ��ֵ�Լ�
		Set<Entry<Object, Object>> entrySet2 = properties.entrySet();
		
//		������ڴ���ļ�ֵ�ԣ��ͽ�ֵ���ɴ������ֵ
		boolean replace = properties.replace("key", "oldValue", "newValue");
//		�������е�ֵ �� �봫���ֵ�����д����㷨�����㽫���ص�ֵ����ԭ����ֵ    
		properties.replaceAll((Object old, Object newO) -> {
			return newO;
		});
//		�������key�ͽ�key�е�ֵ�봫���value���д����㷨�����㣬key�����ھͲ���������
		properties.merge("keyy", "value",
				(Object old,Object value)->{
					return old;
					});
		
		Object clone = properties.clone();
//		
		properties.forEach((Object key, Object value) -> {
			//do something   ������ֵ�ԣ����Լ�ֵ�Խ��д����㷨�Ĳ���
		});
//		���key������,�ͽ�key���������ȡ�µ�value,���µļ�ֵ����ӵ������У�������value
		properties.computeIfAbsent("keyyy", (Object key) -> {
			Object keyy=key;
			return "value";
		});
//		��������ھͽ��д�����㷨��key���Ǵ����key��value����key��Ӧ��value��
		properties.computeIfPresent("key", (Object key, Object value) -> {
			Object onee=key;
			Object secondd=value;
			return value;
		});
//		��������ڣ��ͽ��д�����㷨     key���Ǵ����key��value���Ƕ�Ӧ��value.
//		���key�����ڣ�Ҳ����в���������value���null
		properties.compute("keyo", (Object key, Object value) -> {
			Object firstt=key;
			Object secondd=value;
			return value;
		});
//		�Ƴ��봫�����ͬ�ĵ�һ�Լ�ֵ��
		properties.remove("key");
//		�Ƴ���һ���봫���ֵ����ͬ�Ķ�
		properties.remove("key", "value");
		properties.clear();
	}

}
