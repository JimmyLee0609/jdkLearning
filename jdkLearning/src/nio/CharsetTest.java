package nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;

public class CharsetTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws CharacterCodingException {
		SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
		Charset defaultCharset = Charset.defaultCharset();// 根据运行环境的默认字符集 GBK
		boolean supported = Charset.isSupported("iso8859-1");// true
		// 获取取Charset实例
		Charset cs = Charset.forName("utf-8");
		// 获取编码器
		CharsetEncoder newEncoder = cs.newEncoder();
		// 获取解码器
		CharsetDecoder newDecoder = cs.newDecoder();
		// 获取当前Charset的别名
		Set<String> aliases = cs.aliases();
		boolean canEncode = cs.canEncode();// true

		// 按照名字的顺序比较，就是名字的charcode差别
		int compareTo = cs.compareTo(Charset.forName("UTF-16"));// 7
		// Charset的名字
		String name = cs.name();// UTF-8
		String displayName = cs.displayName();// UTF-8
		String localeName = cs.displayName(Locale.CHINA);// UTF-8
		// Charset是否注册了
		boolean registered = cs.isRegistered();// true
		// 新建一个缓冲
		CharBuffer charBuf = CharBuffer.wrap("测试编码1，测试编码2.test charset one!".toCharArray());
		// 使用Charset对缓冲区的数据进行编码
		ByteBuffer encode = cs.encode(charBuf);
		// 使用Charset对缓冲区的数据进行解码
		CharBuffer decode = cs.decode(encode);

		// 是否包含另一个Charset,
		boolean contains = cs.contains(Charset.forName("UTF-16"));// true
//		测试包含的字符集能否解码被包含的字符集
//		testContain();

		testde_enCoder();
		String d = cs.toString();
	}

	@SuppressWarnings("unused")
	private static void testde_enCoder() {
		Charset utf8 = Charset.forName("utf-8");
		CharsetEncoder newEncoder = utf8.newEncoder();
		CharsetDecoder newDecoder = utf8.newDecoder();
		CharBuffer in = CharBuffer.wrap("in charBuff".toCharArray());
		ByteBuffer out = ByteBuffer.wrap("out charbuf".getBytes());
//		对一个缓冲进行编码，并将编码后的数据写到另一个缓冲。   表示是否结束，false的状态的Coding         不能flush
		CoderResult encode = newEncoder.encode(in, out, true);
//		实际是返回编码的状态，来调整编码器的状态，与缓冲区无关
		CoderResult flush = newEncoder.flush(out);
		CharsetEncoder reset = newEncoder.reset();//设置状态
//		当前解码器的每一个char的最大字节数
		float maxBytesPerChar = newEncoder.maxBytesPerChar();//3.0
//		当前解码器解码时每个char的平均字节数
		float averageBytesPerChar = newEncoder.averageBytesPerChar();//1.1
		
//		当前解码器的字符集
		Charset charset = newEncoder.charset();//UTF-8
//		当前解码器是否能解码指定的传入字符
		boolean canEncode = newEncoder.canEncode("can encode");//true
		
//		返回此编码器的替换值。
		byte[] replacement = newEncoder.replacement();//63
//		判断替换值是否有效
		boolean legalReplacement = newEncoder.isLegalReplacement(replacement);
//		将编码器的替换值 ，  变更为传入的值,会影响比编码结果
		CharsetEncoder replaceWith = newEncoder.replaceWith("~!@".getBytes());
		
		CodingErrorAction malformedInputAction = newEncoder.malformedInputAction();//REPORT
		CodingErrorAction unmappableCharacterAction = newEncoder.unmappableCharacterAction();//REPORT
//		更改编码器对格式错误输入错误的操作。		此方法调用implOnMalededInput方法，传递新的操作。
		CharsetEncoder onMalformedInput = newEncoder.onMalformedInput(unmappableCharacterAction);
//		更改此编码器对不可映射字符错误的操作。		该方法调用implOnUnmappableCharacter方法，传递新的动作。
		CharsetEncoder onUnmappableCharacter = newEncoder.onUnmappableCharacter(unmappableCharacterAction);
		
//		也是不对缓冲区做动作。 只是将自己状态进行调整
		newDecoder.flush(in);
		
		
		
	}

	@SuppressWarnings("unused")
	private static void testContain() throws CharacterCodingException {
		Charset utf8 = Charset.forName("utf-8");
		Charset utf16 = Charset.forName("utf-16");
		boolean contains = utf8.contains(utf16);

		CharBuffer wrap = CharBuffer.wrap("测试包含的编码器能否相互替代，test Contains can replace");
		CharsetEncoder en16 = utf16.newEncoder();
		CharsetDecoder de16 = utf16.newDecoder();
		CharsetEncoder en8 = utf8.newEncoder();
		CharsetDecoder de8 = utf8.newDecoder();

		ByteBuffer encode = en16.encode(wrap);
		CharBuffer decode = de16.decode(encode);

		wrap.flip();

		ByteBuffer encode2 = en8.encode(wrap);
		CharBuffer decode2 = de8.decode(encode2);

		wrap.flip();
		//使用被包含的Charset解码包含它的Charset，可以解码，可是结果不可预测
		ByteBuffer encode4 = en8.encode(wrap);
		CharBuffer decode4 = de16.decode(encode4);// 变成乱码
//		使用被包含的Charset编码，包含它的Charset，解码抛出异常
		ByteBuffer encode3 = en16.encode(wrap);
		CharBuffer decode3 = de8.decode(encode3);// 抛异常

		wrap.flip();

	}

}
