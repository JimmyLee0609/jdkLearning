package io;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedStreamTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		PipedWriter pipedWriter = new PipedWriter();
		PipedReader pipedReader = new PipedReader(pipedWriter);

		new Thread(() -> {
			while (true) {
				int c = 0;

				try {
					if (c < 3) {
						pipedWriter.append("就是测试以下啦");
						pipedWriter.write("能不能停下来，再开始");
						c++;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					try {
						pipedWriter.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(() -> {
			int c = 0;
			while (true) {
				try {
					if (pipedReader.ready()) {
						int o = pipedReader.read();
						System.out.println("第" + c++ + "次" + (char) o);
					}
				} catch (IOException e) {
					e.printStackTrace();
					try {
						Thread.currentThread().wait();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}).start();

	}

}
