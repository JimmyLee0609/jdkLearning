package util.optional;

import java.util.OptionalInt;

public class OptionalIntTest {

	public static void main(String[] args) {
//		���ǰ�װ��int�Ļ����������͡�������Optioanalһ���ġ������ǻ����������;�û��map�ķ�����
		OptionalInt of = OptionalInt.of(15);
		int asInt = of.getAsInt();
		boolean present = of.isPresent();
		int orElse = of.orElse(16);
		int orElseGet = of.orElseGet(()->{return 99;});
	}

}
