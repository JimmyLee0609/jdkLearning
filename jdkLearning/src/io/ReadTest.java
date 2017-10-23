package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class ReadTest {

	public static void main(String[] args) throws IOException {
		inputStreamReader();
		streamTokenizer();
	}

	@SuppressWarnings("unused")
	private static void streamTokenizer() throws IOException {
		StreamTokenizer tokenizer = new StreamTokenizer(new FileReader(""));
		int nextToken = tokenizer.nextToken();
		tokenizer.wordChars(4, 8);//  指定 low <= c <= high 范围中的所有字符 c 都是文字成分。
		tokenizer.whitespaceChars(1, 2);// 指定 low <= c <= high 范围中的所有字符 c 都是空白字符。
		tokenizer.ordinaryChars(5, 6);// 指定 low <= c <= high 范围中的所有字符 c 在此标记生成器中都是“普通”字符。
		tokenizer.commentChar(5);//指定该字符参数启动一个单行注释。
		tokenizer.eolIsSignificant(false);//确定是否将行末尾视为标记。
		int lineno = tokenizer.lineno();//    返回当前行号。
		tokenizer.lowerCaseMode(false);//  确定是否对文字标记自动使用小写字母。
		tokenizer.resetSyntax();//重置此标记生成器的语法表，使所有字符都成为“普通”字符。
		tokenizer.parseNumbers();// 指定此标记生成器应解析的数字。
		
//		导致对此标记生成器的 nextToken 方法的下一个调用返回 ttype 字段中的当前值，而不修改 nval 或 sval 字段中的值。
		tokenizer.pushBack();
//		指定此字符的匹配对分隔此标记生成器中的字符串常量。
		tokenizer.quoteChar('a');
//		确定标记生成器是否识别 C++ 样式注释。
		tokenizer.slashSlashComments(false);
//		确定标记生成器是否识别 C 样式注释。
		tokenizer.slashStarComments(false);
		double nval = tokenizer.nval;
		String sval = tokenizer.sval;
		int ttype = tokenizer.ttype;
		String string = tokenizer.toString();
		
		
	}

	@SuppressWarnings("unused")
	private static void inputStreamReader() throws IOException {
//		========转换流====将读取的字节流的数据转换字符流======================
		InputStreamReader reader = new InputStreamReader(new FileInputStream("d:/temp/abc.t"),"utf-8");
		boolean markSupported = reader.markSupported();
		reader.mark(15);
		reader.reset();
		long skip = reader.skip(13);
		int read = reader.read();
		String encoding = reader.getEncoding();
		
		reader.close();
	}

}
