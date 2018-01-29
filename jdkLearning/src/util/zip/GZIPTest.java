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
//		印象中是html中的一种可选压缩方式，
		 output();
//		input();
	}

	private static void input() throws IOException {
		// 新建一个文件输入流
		FileInputStream fi = new FileInputStream("d:/temp/outer.gzip");
		// 包装一个GZIP文件输入流 500:缓冲区大小
//		这个对象 继承      InflaterInputStream
		GZIPInputStream stream = new GZIPInputStream(fi, 500);
		// 新建一个字节缓冲区
		byte[] buf = new byte[500];
		// 读取文件内容到缓冲区           JDK实现中的方法就这一个，如果将
		int read = stream.read(buf, 0, buf.length);
		// 将读取到的内容进行解码。
		CharBuffer decode = Charset.forName("GBK").newDecoder().decode(ByteBuffer.wrap(buf));
		// 关闭流
		stream.close();

	}

	private static void output() throws FileNotFoundException, IOException {
		// 新建一个文件输出流
		FileOutputStream out = new FileOutputStream("d:/temp/outer.gzip");
		// 包装一个GZIP压缩流 500： 缓冲区大小 true: 是否同步flush，跟缓冲区有关
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out, 500, true);
		// 新建一个字节缓冲区
		byte[] buf = "文件输出流GZIP".getBytes();
		// 将缓冲区的数据写到压缩文件中
		gzipOutputStream.write(buf, 0, buf.length);
		// 完成压缩流的输出但是不关闭文件输出流
		gzipOutputStream.finish();
		// 关闭文件输出流
		gzipOutputStream.close();
	}

}
