package util.zip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPTest {

	public static void main(String[] args) throws IOException {
//		=========Gzip=======================
//		ӡ������html�е�һ�ֿ�ѡѹ����ʽ��
		 output();
//		input();
	}

	private static void input() throws IOException {
		// �½�һ���ļ�������
		FileInputStream fi = new FileInputStream("d:/temp/outer.gzip");
		// ��װһ��GZIP�ļ������� 500:��������С
//		������� �̳�      InflaterInputStream
		GZIPInputStream stream = new GZIPInputStream(fi, 500);
		// �½�һ���ֽڻ�����
		byte[] buf = new byte[500];
		// ��ȡ�ļ����ݵ�������           JDKʵ���еķ�������һ���������
		int read = stream.read(buf, 0, buf.length);
		// ����ȡ�������ݽ��н��롣
		CharBuffer decode = Charset.forName("GBK").newDecoder().decode(ByteBuffer.wrap(buf));
		// �ر���
		stream.close();

	}

	private static void output() throws FileNotFoundException, IOException {
		// �½�һ���ļ������
		FileOutputStream out = new FileOutputStream("d:/temp/outer.gzip");
		// ��װһ��GZIPѹ���� 500�� ��������С true: �Ƿ�ͬ��flush�����������й�
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out, 500, true);
		// �½�һ���ֽڻ�����
		byte[] buf = "�ļ������GZIP".getBytes();
		// ��������������д��ѹ���ļ���
		gzipOutputStream.write(buf, 0, buf.length);
		// ���ѹ������������ǲ��ر��ļ������
		gzipOutputStream.finish();
		// �ر��ļ������
		gzipOutputStream.close();
	}

}
