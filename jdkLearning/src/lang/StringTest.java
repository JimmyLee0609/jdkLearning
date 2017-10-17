package lang;

import java.lang.Character.UnicodeBlock;
import java.lang.Character.UnicodeScript;

public class StringTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
String string = new String("asas~!@#$%#^^&水岸东方fagagadsg");
int codePointAt = string.codePointAt(0);//97
int codePointAt1 = string.codePointAt(1);//115
int codePointBefore = string.codePointBefore(6);//33
int codePointCount = string.codePointCount(3, 8);//5
int codePointCount2 = string.codePointCount(3, 25);//22

Byte decode = Byte.decode("0x16");//将其他进制的数转换为十进制 存为Byte的值22
UnicodeBlock of = Character.UnicodeBlock.of(56);//BASIC_LATIN
UnicodeBlock of2 = Character.UnicodeBlock.of(58);//BASIC_LATIN


UnicodeScript script = Character.UnicodeScript.of(56);//COMMON
UnicodeScript of3 = Character.UnicodeScript.of(58);//COMMON
char titleCase = Character.toTitleCase('a');//A
int parseInt = Integer.decode("#3a");//58


int c=0;
	}

}
