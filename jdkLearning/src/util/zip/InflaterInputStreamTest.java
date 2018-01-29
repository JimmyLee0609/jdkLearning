package util.zip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		FileInputStream in = new FileInputStream("d:/temp/deflater");
		Inflater inflater = new Inflater();
		
//		与DeflaterOutputStream是相对的，算法用于是实现GZIP  和  ZIP的输入流
		InflaterInputStream stream = new InflaterInputStream(in, inflater, 1024);
		byte[] b = new byte[1024];
		int read = stream.read(b);

		int available = stream.available();

		String string = new String(b);

		stream.close();
	}

}
