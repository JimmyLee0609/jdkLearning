package util.optional;

import java.util.Optional;
import java.util.function.Function;

import util.Domain;

public class OptionalTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		//��ȡOptionalʵ���ķ���
		Optional<String> name = Optional.of("name");
//		�����nullֵ�ͷ���һ��empty��ֵ
		Optional<String> empty = Optional.ofNullable(null);
//		this.value=null
		Optional<Domain> empty2 = Optional.empty();

//		���Optional��value��Ϊnull�ͷ���Optional��value�����򷵻ش����ֵ
		String orElse = name.orElse("There is no value in it");
		String orElse2 = empty.orElse("There is no value in it");
		Domain orElse3 = empty2.orElse(new Domain(10,"domian10"));

//		��ȡOptional�е�value��ֵ		
		String string = name.get();
//		���Optinal�����ֵ�ͷ���value������,�ͷ��غ����з��ص�ֵ
		String orElseGet = name.orElseGet(() -> { return "default value"; });
		String orElseGet2 = empty.orElseGet(() -> { return "default value"; });
		Domain orElseGet3 = empty2.orElseGet(()->{return new Domain(15,"domain15");});
//		���Optional�����ֵ�ͷ���value��ֵ�������׳�������쳣
		String orElseThrow = name.orElseThrow(Exception::new);
//		Optional�е�value��ֵ��true��  ��null��false
		boolean present = name.isPresent();
		
//		���Optional��value��Ϊnull��ִ�к�����ߵĲ�����
		name.ifPresent((String value) -> {
			String q=value;//name
			// do something
		});
//		���Optional��valueΪnull�Ͳ�ִ�к�����ߵĲ���
		empty2.ifPresent((Domain dom)->{
			//do something
			System.out.println("Option ��value ��Ϊnull");
		});
		boolean equals = name.equals("name");

//		���ݴ�����㷨��Optional  �����valueֵ�������㣬���valueΪnull�Ͳ�ִ�д�����㷨
		Optional<String> map = name.map((String value) -> {
			String d=value;																//name   ���Ƕ����Լ���ֵ
			return value.toUpperCase();
		});
//		���ݴ���ĺ����㷨���й��ˣ�����true�ͽ��գ�����false�Ͳ����ܣ����valueΪnull�Ͳ�ִ�д�����㷨
		Optional<String> filter = name.filter((String value) -> {
			String c=value;																//name    ���Ƕ����Լ���ֵ
			return false;																	//����false  filter����  Optional.empty
		});
		
//		���ݴ�����㷨���¹���Optional�����أ����valueΪnull�Ͳ�ִ�д�����㷨
		Optional<String> flatMap = name.flatMap((String value) -> {
			String b=value;           													//name       ���Ƕ����Լ���ֵ
			return Optional.of("inner optional");         //�½�һ��Optional������
		});
		boolean equals2 = name.equals(flatMap);//false
		String string2 = name.toString();
	}
}
