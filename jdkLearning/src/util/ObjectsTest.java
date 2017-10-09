package util;

import java.util.Arrays;
import java.util.Objects;

public class ObjectsTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//Objects ��һ��������
		Domain domain = new Domain(10, "10domain");
		Domain domain2 = new Domain(5, "domain5");
		Class<?> c = Objects.class;
		int hashCode = Objects.hashCode(domain2);//ʹ��ϵͳ�ķ�ʽ��ȡ�����hashֵ
		int hash = Objects.hash(domain, domain2);//������������hash�������ϼƵ�hashֵ����
		
		boolean null1 = Objects.isNull(domain2);   //false     �ж϶����Ƿ�Ϊnull    �˶���Ϊnull
		boolean nonNull = Objects.nonNull(domain2);//true  �ж϶����Ƿ��null  �˶���Ϊnull
	
		Domain requireNonNull = Objects.requireNonNull(domain);//������������null�����쳣
		Domain requireNonNull2 = Objects.requireNonNull(domain2, "domian2NonNUll");
//		Domain requireNonNull3 = Objects.requireNonNull(null, "domian2NonNUll");       ���������null�ͽ�mesg�����쳣��Ϣ
	
		String string = Objects.toString(domain);//Domain [index=10, name=10domain]
		String string2 = Objects.toString(domain, "domain Objects toString");//Domain [index=10, name=10domain]
		String string3 = Objects.toString(null, "domain Objects toString");//domain Objects toString  ����Ķ�����null�ͷ���message
	
		boolean equals = Objects.equals(domain, domain2);//false       a.equals(b)          �ж����������Ƿ����
		boolean deepEquals = Objects.deepEquals(domain2, domain);//false   Arrays.deepEquals0(a, b);  ���εıȽ���������
//		ʹ�ñȽ���  �Ƚ����������Ƿ����
		int compare = Objects.compare("first", domain, (Object one, Object two) -> {
			return 0;
		});

	}

}
