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
		Charset defaultCharset = Charset.defaultCharset();// �������л�����Ĭ���ַ��� GBK
		boolean supported = Charset.isSupported("iso8859-1");// true
		// ��ȡȡCharsetʵ��
		Charset cs = Charset.forName("utf-8");
		// ��ȡ������
		CharsetEncoder newEncoder = cs.newEncoder();
		// ��ȡ������
		CharsetDecoder newDecoder = cs.newDecoder();
		// ��ȡ��ǰCharset�ı���
		Set<String> aliases = cs.aliases();
		boolean canEncode = cs.canEncode();// true

		// �������ֵ�˳��Ƚϣ��������ֵ�charcode���
		int compareTo = cs.compareTo(Charset.forName("UTF-16"));// 7
		// Charset������
		String name = cs.name();// UTF-8
		String displayName = cs.displayName();// UTF-8
		String localeName = cs.displayName(Locale.CHINA);// UTF-8
		// Charset�Ƿ�ע����
		boolean registered = cs.isRegistered();// true
		// �½�һ������
		CharBuffer charBuf = CharBuffer.wrap("���Ա���1�����Ա���2.test charset one!".toCharArray());
		// ʹ��Charset�Ի����������ݽ��б���
		ByteBuffer encode = cs.encode(charBuf);
		// ʹ��Charset�Ի����������ݽ��н���
		CharBuffer decode = cs.decode(encode);

		// �Ƿ������һ��Charset,
		boolean contains = cs.contains(Charset.forName("UTF-16"));// true
//		���԰������ַ����ܷ���뱻�������ַ���
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
//		��һ��������б��룬��������������д����һ�����塣   ��ʾ�Ƿ������false��״̬��Coding         ����flush
		CoderResult encode = newEncoder.encode(in, out, true);
//		ʵ���Ƿ��ر����״̬����������������״̬���뻺�����޹�
		CoderResult flush = newEncoder.flush(out);
		CharsetEncoder reset = newEncoder.reset();//����״̬
//		��ǰ��������ÿһ��char������ֽ���
		float maxBytesPerChar = newEncoder.maxBytesPerChar();//3.0
//		��ǰ����������ʱÿ��char��ƽ���ֽ���
		float averageBytesPerChar = newEncoder.averageBytesPerChar();//1.1
		
//		��ǰ���������ַ���
		Charset charset = newEncoder.charset();//UTF-8
//		��ǰ�������Ƿ��ܽ���ָ���Ĵ����ַ�
		boolean canEncode = newEncoder.canEncode("can encode");//true
		
//		���ش˱��������滻ֵ��
		byte[] replacement = newEncoder.replacement();//63
//		�ж��滻ֵ�Ƿ���Ч
		boolean legalReplacement = newEncoder.isLegalReplacement(replacement);
//		�����������滻ֵ ��  ���Ϊ�����ֵ,��Ӱ��ȱ�����
		CharsetEncoder replaceWith = newEncoder.replaceWith("~!@".getBytes());
		
		CodingErrorAction malformedInputAction = newEncoder.malformedInputAction();//REPORT
		CodingErrorAction unmappableCharacterAction = newEncoder.unmappableCharacterAction();//REPORT
//		���ı������Ը�ʽ�����������Ĳ�����		�˷�������implOnMalededInput�����������µĲ�����
		CharsetEncoder onMalformedInput = newEncoder.onMalformedInput(unmappableCharacterAction);
//		���Ĵ˱������Բ���ӳ���ַ�����Ĳ�����		�÷�������implOnUnmappableCharacter�����������µĶ�����
		CharsetEncoder onUnmappableCharacter = newEncoder.onUnmappableCharacter(unmappableCharacterAction);
		
//		Ҳ�ǲ��Ի������������� ֻ�ǽ��Լ�״̬���е���
		newDecoder.flush(in);
		
		
		
	}

	@SuppressWarnings("unused")
	private static void testContain() throws CharacterCodingException {
		Charset utf8 = Charset.forName("utf-8");
		Charset utf16 = Charset.forName("utf-16");
		boolean contains = utf8.contains(utf16);

		CharBuffer wrap = CharBuffer.wrap("���԰����ı������ܷ��໥�����test Contains can replace");
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
		//ʹ�ñ�������Charset�����������Charset�����Խ��룬���ǽ������Ԥ��
		ByteBuffer encode4 = en8.encode(wrap);
		CharBuffer decode4 = de16.decode(encode4);// �������
//		ʹ�ñ�������Charset���룬��������Charset�������׳��쳣
		ByteBuffer encode3 = en16.encode(wrap);
		CharBuffer decode3 = de8.decode(encode3);// ���쳣

		wrap.flip();

	}

}
