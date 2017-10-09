package util.optional;

import java.util.OptionalInt;

public class OptionalIntTest {

	public static void main(String[] args) {
//		就是包装了int的基本数据类型。其他和Optioanal一样的。由于是基本数据类型就没有map的方法了
		OptionalInt of = OptionalInt.of(15);
		int asInt = of.getAsInt();
		boolean present = of.isPresent();
		int orElse = of.orElse(16);
		int orElseGet = of.orElseGet(()->{return 99;});
	}

}
