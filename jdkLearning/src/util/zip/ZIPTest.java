package util.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.attribute.FileTime;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZIPTest {

	public static void main(String[] args) throws IOException {
		// simpleZip();
		// nestZip();
//		 readZip();
		writeZip();
		// modifyZip();
	}

	@SuppressWarnings("unused")
	private static void writeZip() throws IOException {
		FileOutputStream out = new FileOutputStream("d:/temp/out.zip");

		ZipOutputStream stream = new ZipOutputStream(out, Charset.forName("GBK"));
		ZipEntry en = new ZipEntry("abc/");
		stream.putNextEntry(en);
		stream.closeEntry();

		ZipEntry zipEntry = new ZipEntry("abc/t.txt");
		stream.putNextEntry(zipEntry);
		long crc2 = zipEntry.getCrc();//-1
		byte[] b = "��������д��".getBytes();
		stream.write(b, 0, b.length);
		long crc3 = zipEntry.getCrc();//-1
//		�ڹرռ���ʱ�򣬻ὫCRC,�ļ�ѹ��ǰ����Ĵ�С��д��ZipEntry��
		stream.closeEntry();
//		CRC��ֵ������ZIP��close֮ǰ����Ϊ�ʵ���ֵ
		long crc = zipEntry.getCrc();//2581372201
//		�Լ������������������ͻ�ȡ����CRC��,��Ҫ��CRC�����ȡ����ֵ����Ϊѹ���ļ���CRCУ���룬���Լ����ļ���������
//		zipEntry.setCrc(2581373333l);
//		long crc2 = zipEntry.getCrc();
		
		stream.close();
	}

	private static void modifyZip() throws IOException {
		File file = new File("D:\\temp\\abc.zip");
		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(file));
		ZipEntry nextEntry = null;
		while (nextEntry == null || !nextEntry.getName().equals("aaa/temp.txt")) {
			nextEntry = inputStream.getNextEntry();
			long crc = nextEntry.getCrc();
		}
		int available = inputStream.available();

		// ==========���ַ�ʽֱ�ӽ�ZIP���ļ���д��ֻʣ�£���put��ȥ������================================
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
		zipOutputStream.putNextEntry(nextEntry);
		zipOutputStream.write("�޸�ZIP�ļ�����".getBytes());
		zipOutputStream.closeEntry();
		zipOutputStream.close();

		inputStream.close();
	}

	@SuppressWarnings("unused")
	private static void readZip() throws IOException {
		// �½�һ��������
	/*	FileInputStream fileInputStream = new FileInputStream(
				"D:\\BaiduNetdiskDownload\\MYSQL.zip");*/
		FileInputStream fileInputStream = new FileInputStream(
				"d:/temp/out.zip");
		// ��װ������ΪZIP������
		ZipInputStream stream = new ZipInputStream(fileInputStream);
		// ZIP�������Ƿ������ݣ�ֻҪnextEntry����null��Ϊ1������Ϊ0
		int available = stream.available();
		// �Ƿ�֧��mark
		boolean markSupported = stream.markSupported();// false
		// �����ƶ����ֽ���������ʵ���������ֽ���
//		long skip = stream.skip(50l);// 0
		// ���markָ����λ�ã���֧��
		// stream.mark(50);
		// ��markһͬʹ�ã����Խ���ǰλ�ûظ���mark��λ�á���֧��
		// stream.reset();

		// ��ȡ��һ��Entry
		ZipEntry nextEntry2 = stream.getNextEntry();
		ZipEntry nextEntry3 = stream.getNextEntry();
		ZipEntry nextEntry4 = stream.getNextEntry();
		ZipEntry nextEntry5 = stream.getNextEntry();
		ZipEntry nextEntry6 = stream.getNextEntry();
		ZipEntry nextEntry7 = stream.getNextEntry();
		ZipEntry nextEntry8 = stream.getNextEntry();
		ZipEntry nextEntry = stream.getNextEntry();
		// ��ȡ��������
		String name = nextEntry.getName();
		// ��ȡ���ļ�����
		long crc = nextEntry.getCrc();
		FileTime lastModifiedTime = nextEntry.getLastModifiedTime();
		long time = nextEntry.getTime();
		// �½�ZIPFile������ʵ������������һ������
		ZipFile file = new ZipFile(new File("C:\\Users\\JimmyLee\\Downloads\\xmind-7.5-update1-portable.zip"));
		// ��ȡ�ļ��Ĵ�С
		int size = file.size();
		// �ر���
		stream.close();
		// �ر��ļ�����
		file.close();
	}

	private static void nestZip() throws IOException {
		// ZIP������Ƕ�ף�
		FileOutputStream outputStream = new FileOutputStream("d:/temp/abc.zip");
		ZipOutputStream stream = new ZipOutputStream(outputStream);
		ZipOutputStream zipOutputStream = new ZipOutputStream(stream);

		zipOutputStream.putNextEntry(new ZipEntry("aaa/inner.txt"));
		zipOutputStream.write("test for inner".getBytes("utf-8"));
		zipOutputStream.closeEntry();

		zipOutputStream.putNextEntry(new ZipEntry("aaa/inner/i.txt"));
		zipOutputStream.write("test inner test".getBytes());
		zipOutputStream.closeEntry();
		zipOutputStream.close();
		stream.close();
		outputStream.close();
	}

	@SuppressWarnings("unused")
	private static void simpleZip() throws IOException {

		// �½�һ���ļ������,ZIP��������ļ�д����һ���ļ��С�
		FileOutputStream outputStream = new FileOutputStream("d:/temp/abc.zip");
		// �½�һ��ZIPѹ�������,Ĭ�ϵı�����utf-8.���Ը���ʵ�ʵ�����������±���
		// �������̳� DeflaterOutputStream
		ZipOutputStream stream = new ZipOutputStream(outputStream, Charset.forName("GBK"));

		// ============��������ZIP�����===============================================

		// ����ѹ�����ĵȼ����������þ�ZIP�ļ�����CRC32���ļ��޸�ʱ�䣬�ļ���С����Ϣ
		stream.setLevel(Deflater.DEFAULT_COMPRESSION);
		// ����ѹ���������ȼ��ж�Ӧ�ĳ���.�Լ�Ĭ�Ͼ��� 8
		stream.setMethod(ZipOutputStream.DEFLATED);
		// ���ע��
		stream.setComment("���ǲ���һ��ע��");

		// ���ļ��е�ѹ����ʽ�� �ļ���·��+�ļ��ָ��� /
		ZipEntry floder = new ZipEntry("cc/");
		boolean directory = floder.isDirectory();// true
		stream.putNextEntry(floder);
		stream.closeEntry();

		// �½�һ��ѹ������������һ��Ŀ¼�ṹ�µ�·���ļ������������·����
		ZipEntry entry = new ZipEntry("aaa/temp.txt");
		// ��ѹ�����ŵ�ѹ����
		stream.putNextEntry(entry);
		// д������
		stream.write("ni shi shui a ".getBytes());
		// �رյ�ǰ�ļ���ÿ��д�����ݶ���Ҫ�رռ���ÿ��putNextEntry��رյ�ǰ�ļ�
		entry.setCrc(Integer.parseInt("6b73d28b", 16));// ��CRC��ֵд��entry��
		int method = entry.getMethod();

		// ��DEFLATEDģʽ�� ���ڱ�֤�����������finishȻ���ļ�ѹ��ǰ��ѹ����Ĵ�С���Լ�CRC32��ֵд��������ݵĺ���
		// e.size = def.getBytesRead(); e.csize = def.getBytesWritten(); e.crc =
		// crc.getValue();
		// writeEXT(e); ��дһ��EXT��ͷwriteInt(EXTSIG); 0x08074b50L---�� 134695760��
		// Ȼ��CRC��ֵ��Ȼ��ѹ��ǰ�Ĵ�С��Ȼ��ѹ����Ĵ�С
		stream.closeEntry();

		// �½�һ�����ļ��ṹ�ļ�
		ZipEntry zipEntry = new ZipEntry("aaa/bbb/bcd.txt");
		// �����ŵ�ѹ������
		stream.putNextEntry(zipEntry);
		// ���������д������
		stream.write("jiushi ceshi yixia luo".getBytes());
		// ����ע��, �ַ����뼯�������ı���ſ���д���ģ������׳��쳣��ע�͵ı�����Ҫ�����ı����Ӧ
		// stream.setComment("����ѹ����");
		// �رյ�ǰ�ļ�
		stream.closeEntry();

		// ���������Ƿ�����,�����ַ�Ҳ��Ҫ�����ı���һ�¡�
		ZipEntry entry2 = new ZipEntry("aaa/chiness.doc");
		stream.putNextEntry(entry2);
		// д���ļ������ݿ������������룬ֻҪ����ļ��ڶ�ȡ��ʱ��ʹ�ö�Ӧ�Ľ������
		stream.write("��������".getBytes("gbk"));
		// �رռ�����ʾ���������Ѿ�д��
		stream.closeEntry();

		// �ر�ѹ��������ʵѹ�����رյ�ʱ�����ͬ�ײ�������һͬ�رգ�
		// ���ֻ��Ҫ�رյ�ǰ��ѹ��������Ҫʹ��finish
		// stream.finish();
		stream.close();

		// �ر��ļ������
		outputStream.close();
	}

}
