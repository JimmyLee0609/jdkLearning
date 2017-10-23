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
		tokenizer.wordChars(4, 8);//  ָ�� low <= c <= high ��Χ�е������ַ� c �������ֳɷ֡�
		tokenizer.whitespaceChars(1, 2);// ָ�� low <= c <= high ��Χ�е������ַ� c ���ǿհ��ַ���
		tokenizer.ordinaryChars(5, 6);// ָ�� low <= c <= high ��Χ�е������ַ� c �ڴ˱���������ж��ǡ���ͨ���ַ���
		tokenizer.commentChar(5);//ָ�����ַ���������һ������ע�͡�
		tokenizer.eolIsSignificant(false);//ȷ���Ƿ���ĩβ��Ϊ��ǡ�
		int lineno = tokenizer.lineno();//    ���ص�ǰ�кš�
		tokenizer.lowerCaseMode(false);//  ȷ���Ƿ�����ֱ���Զ�ʹ��Сд��ĸ��
		tokenizer.resetSyntax();//���ô˱�����������﷨��ʹ�����ַ�����Ϊ����ͨ���ַ���
		tokenizer.parseNumbers();// ָ���˱��������Ӧ���������֡�
		
//		���¶Դ˱���������� nextToken ��������һ�����÷��� ttype �ֶ��еĵ�ǰֵ�������޸� nval �� sval �ֶ��е�ֵ��
		tokenizer.pushBack();
//		ָ�����ַ���ƥ��Էָ��˱���������е��ַ���������
		tokenizer.quoteChar('a');
//		ȷ������������Ƿ�ʶ�� C++ ��ʽע�͡�
		tokenizer.slashSlashComments(false);
//		ȷ������������Ƿ�ʶ�� C ��ʽע�͡�
		tokenizer.slashStarComments(false);
		double nval = tokenizer.nval;
		String sval = tokenizer.sval;
		int ttype = tokenizer.ttype;
		String string = tokenizer.toString();
		
		
	}

	@SuppressWarnings("unused")
	private static void inputStreamReader() throws IOException {
//		========ת����====����ȡ���ֽ���������ת���ַ���======================
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
