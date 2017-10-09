package util;

import java.util.Arrays;
import java.util.Objects;

public class ObjectsTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//Objects 是一个工具类
		Domain domain = new Domain(10, "10domain");
		Domain domain2 = new Domain(5, "domain5");
		Class<?> c = Objects.class;
		int hashCode = Objects.hashCode(domain2);//使用系统的方式获取对象的hash值
		int hash = Objects.hash(domain, domain2);//将多个对象进行hash，并将合计的hash值返回
		
		boolean null1 = Objects.isNull(domain2);   //false     判断对象是否为null    此对象不为null
		boolean nonNull = Objects.nonNull(domain2);//true  判断对象是否非null  此对象不为null
	
		Domain requireNonNull = Objects.requireNonNull(domain);//如果传入对象是null就抛异常
		Domain requireNonNull2 = Objects.requireNonNull(domain2, "domian2NonNUll");
//		Domain requireNonNull3 = Objects.requireNonNull(null, "domian2NonNUll");       如果对象是null就将mesg传入异常信息
	
		String string = Objects.toString(domain);//Domain [index=10, name=10domain]
		String string2 = Objects.toString(domain, "domain Objects toString");//Domain [index=10, name=10domain]
		String string3 = Objects.toString(null, "domain Objects toString");//domain Objects toString  传入的对象是null就返回message
	
		boolean equals = Objects.equals(domain, domain2);//false       a.equals(b)          判断两个对象是否相等
		boolean deepEquals = Objects.deepEquals(domain2, domain);//false   Arrays.deepEquals0(a, b);  深层次的比较两个对象
//		使用比较器  比较两个对象是否相等
		int compare = Objects.compare("first", domain, (Object one, Object two) -> {
			return 0;
		});

	}

}
