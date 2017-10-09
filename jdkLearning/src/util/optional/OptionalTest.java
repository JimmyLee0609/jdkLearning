package util.optional;

import java.util.Optional;
import java.util.function.Function;

import util.Domain;

public class OptionalTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		//获取Optional实例的方法
		Optional<String> name = Optional.of("name");
//		如果是null值就返回一个empty的值
		Optional<String> empty = Optional.ofNullable(null);
//		this.value=null
		Optional<Domain> empty2 = Optional.empty();

//		如果Optional的value不为null就返回Optional的value，否则返回传入的值
		String orElse = name.orElse("There is no value in it");
		String orElse2 = empty.orElse("There is no value in it");
		Domain orElse3 = empty2.orElse(new Domain(10,"domian10"));

//		获取Optional中的value的值		
		String string = name.get();
//		如果Optinal里边有值就返回value，否则,就返回函数中返回的值
		String orElseGet = name.orElseGet(() -> { return "default value"; });
		String orElseGet2 = empty.orElseGet(() -> { return "default value"; });
		Domain orElseGet3 = empty2.orElseGet(()->{return new Domain(15,"domain15");});
//		如果Optional里边有值就返回value的值，否则抛出传入的异常
		String orElseThrow = name.orElseThrow(Exception::new);
//		Optional中的value有值就true，  是null就false
		boolean present = name.isPresent();
		
//		如果Optional的value不为null就执行函数里边的操作。
		name.ifPresent((String value) -> {
			String q=value;//name
			// do something
		});
//		如果Optional的value为null就不执行函数里边的操作
		empty2.ifPresent((Domain dom)->{
			//do something
			System.out.println("Option 的value 不为null");
		});
		boolean equals = name.equals("name");

//		根据传入的算法对Optional  里面的value值进行运算，如果value为null就不执行传入的算法
		Optional<String> map = name.map((String value) -> {
			String d=value;																//name   就是对象自己的值
			return value.toUpperCase();
		});
//		根据传入的函数算法进行过滤，返回true就接收，返回false就不接受，如果value为null就不执行传入的算法
		Optional<String> filter = name.filter((String value) -> {
			String c=value;																//name    就是对象自己的值
			return false;																	//返回false  filter就是  Optional.empty
		});
		
//		根据传入的算法重新构造Optional来返回，如果value为null就不执行传入的算法
		Optional<String> flatMap = name.flatMap((String value) -> {
			String b=value;           													//name       就是对象自己的值
			return Optional.of("inner optional");         //新建一个Optional来返回
		});
		boolean equals2 = name.equals(flatMap);//false
		String string2 = name.toString();
	}
}
