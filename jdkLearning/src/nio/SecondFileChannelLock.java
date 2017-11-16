package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SecondFileChannelLock {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		FileChannel open = FileChannel.open(Paths.get("d:/temp/abc.txt"), StandardOpenOption.READ,
				StandardOpenOption.WRITE);
		open.force(true);
		ByteBuffer dst = ByteBuffer.allocate(10);
		int write = open.write(ByteBuffer.wrap("另一个进程写".getBytes()));
		open.read(dst);
		open.close();
	}

}
