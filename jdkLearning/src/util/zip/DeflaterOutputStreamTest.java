package util.zip;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class DeflaterOutputStreamTest {

	public static void main(String[] args) throws IOException {
		FileOutputStream out = new FileOutputStream("d:/temp/deflater");
		Deflater def = new Deflater();
//		�㷨��InflaterInputStream�Ƕ�Ӧ�ģ����ڴ���   GZIP   ZIP��������������Ҫ����ϸ�ĸ�ʽҪ��
		DeflaterOutputStream stream = new DeflaterOutputStream(out, def, 1024, true);
		
		stream.write("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest".getBytes());
		
		stream.finish();
		stream.flush();
		stream.close();
	}

}
