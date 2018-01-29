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
		byte[] b = "测试中文写出".getBytes();
		stream.write(b, 0, b.length);
		long crc3 = zipEntry.getCrc();//-1
//		在关闭键的时候，会将CRC,文件压缩前，后的大小，写到ZipEntry中
		stream.closeEntry();
//		CRC的值可以在ZIP流close之前更改为适当的值
		long crc = zipEntry.getCrc();//2581372201
//		自己随意变更后在输入流就获取不到CRC了,需要是CRC对象获取到的值，是为压缩文件的CRC校验码，可以检验文件的完整性
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

		// ==========这种方式直接将ZIP的文件重写，只剩下，新put进去的内容================================
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
		zipOutputStream.putNextEntry(nextEntry);
		zipOutputStream.write("修改ZIP文件内容".getBytes());
		zipOutputStream.closeEntry();
		zipOutputStream.close();

		inputStream.close();
	}

	@SuppressWarnings("unused")
	private static void readZip() throws IOException {
		// 新建一个输入流
	/*	FileInputStream fileInputStream = new FileInputStream(
				"D:\\BaiduNetdiskDownload\\MYSQL.zip");*/
		FileInputStream fileInputStream = new FileInputStream(
				"d:/temp/out.zip");
		// 包装输入流为ZIP输入流
		ZipInputStream stream = new ZipInputStream(fileInputStream);
		// ZIP输入流是否还有数据，只要nextEntry不是null就为1，否则为0
		int available = stream.available();
		// 是否支持mark
		boolean markSupported = stream.markSupported();// false
		// 跳过制定的字节数，返回实际跳过的字节数
//		long skip = stream.skip(50l);// 0
		// 标记mark指定的位置，不支持
		// stream.mark(50);
		// 与mark一同使用，可以将当前位置回复到mark的位置。不支持
		// stream.reset();

		// 获取下一个Entry
		ZipEntry nextEntry2 = stream.getNextEntry();
		ZipEntry nextEntry3 = stream.getNextEntry();
		ZipEntry nextEntry4 = stream.getNextEntry();
		ZipEntry nextEntry5 = stream.getNextEntry();
		ZipEntry nextEntry6 = stream.getNextEntry();
		ZipEntry nextEntry7 = stream.getNextEntry();
		ZipEntry nextEntry8 = stream.getNextEntry();
		ZipEntry nextEntry = stream.getNextEntry();
		// 获取键的名字
		String name = nextEntry.getName();
		// 获取键的检验码
		long crc = nextEntry.getCrc();
		FileTime lastModifiedTime = nextEntry.getLastModifiedTime();
		long time = nextEntry.getTime();
		// 新建ZIPFile对象，其实就是输入流的一个整合
		ZipFile file = new ZipFile(new File("C:\\Users\\JimmyLee\\Downloads\\xmind-7.5-update1-portable.zip"));
		// 获取文件的大小
		int size = file.size();
		// 关闭流
		stream.close();
		// 关闭文件的流
		file.close();
	}

	private static void nestZip() throws IOException {
		// ZIP流不能嵌套，
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

		// 新建一个文件输出流,ZIP输出流将文件写到这一个文件中。
		FileOutputStream outputStream = new FileOutputStream("d:/temp/abc.zip");
		// 新建一个ZIP压缩输出流,默认的编码是utf-8.可以根据实际的情况进行重新编码
		// 这个对象继承 DeflaterOutputStream
		ZipOutputStream stream = new ZipOutputStream(outputStream, Charset.forName("GBK"));

		// ============整体设置ZIP输出流===============================================

		// 设置压缩流的等级，这样设置就ZIP文件有了CRC32，文件修改时间，文件大小等信息
		stream.setLevel(Deflater.DEFAULT_COMPRESSION);
		// 设置压缩方法，等级有对应的常量.以及默认就是 8
		stream.setMethod(ZipOutputStream.DEFLATED);
		// 添加注释
		stream.setComment("就是测试一个注释");

		// 空文件夹的压缩方式是 文件夹路径+文件分隔符 /
		ZipEntry floder = new ZipEntry("cc/");
		boolean directory = floder.isDirectory();// true
		stream.putNextEntry(floder);
		stream.closeEntry();

		// 新建一个压缩键，键就是一个目录结构下的路径文件名，就是相对路径，
		ZipEntry entry = new ZipEntry("aaa/temp.txt");
		// 将压缩键放到压缩流
		stream.putNextEntry(entry);
		// 写入数据
		stream.write("ni shi shui a ".getBytes());
		// 关闭当前的键。每次写完数据都需要关闭键，每次putNextEntry会关闭当前的键
		entry.setCrc(Integer.parseInt("6b73d28b", 16));// 将CRC的值写到entry中
		int method = entry.getMethod();

		// 在DEFLATED模式下 会在保证这个键的数据finish然后将文件压缩前，压缩后的大小，以及CRC32的值写到这块数据的后面
		// e.size = def.getBytesRead(); e.csize = def.getBytesWritten(); e.crc =
		// crc.getValue();
		// writeEXT(e); 先写一个EXT的头writeInt(EXTSIG); 0x08074b50L---》 134695760，
		// 然后CRC的值，然后压缩前的大小，然后压缩后的大小
		stream.closeEntry();

		// 新建一个带文件结构的键
		ZipEntry zipEntry = new ZipEntry("aaa/bbb/bcd.txt");
		// 将键放到压缩流中
		stream.putNextEntry(zipEntry);
		// 在这个键中写入数据
		stream.write("jiushi ceshi yixia luo".getBytes());
		// 设置注释, 字符编码集含有中文编码才可以写中文，否则抛出异常，注释的编码需要和流的编码对应
		// stream.setComment("测试压缩流");
		// 关闭当前的键
		stream.closeEntry();

		// 测试中文是否乱码,键的字符也需要和流的编码一致。
		ZipEntry entry2 = new ZipEntry("aaa/chiness.doc");
		stream.putNextEntry(entry2);
		// 写到文件的内容可以是其他编码，只要这个文件在读取的时候使用对应的解码就行
		stream.write("测试中文".getBytes("gbk"));
		// 关闭键，提示键的内容已经写完
		stream.closeEntry();

		// 关闭压缩流，其实压缩流关闭的时候就连同底层的输出流一同关闭，
		// 如果只需要关闭当前的压缩流，需要使用finish
		// stream.finish();
		stream.close();

		// 关闭文件输出流
		outputStream.close();
	}

}
