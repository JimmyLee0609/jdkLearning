package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;

public class FileChannelTest {

	public static void main(String[] args) throws IOException {
//		lock();
		transfer();
		openChannel();
		fileChannelMethod();
		createObj();
		originIO();
		filechannel();
	}

	@SuppressWarnings("unused")
	private static void lock() throws IOException {
		FileChannel fc = FileChannel.open(Paths.get("d:/temp/abc.txt"), StandardOpenOption.READ,StandardOpenOption.WRITE);
		fc.force(true);
		int write = fc.write(ByteBuffer.wrap("测试锁定有什么用".getBytes()));
//		FileLock lock = fc.lock();//锁定之后,其他进程就无法读取文件,内部用的是 lock(0L, Long.MAX_VALUE, false);
		
//		NonReadableChannelException -管道没有StandardOpenOption.READ，写true抛异常
//		NonWritableChannelException - 管道没有StandardOpenOption.WRITE，写false抛异常
//		管道不能将重复的锁定管道的部分内容     读共享
		FileLock lock2 = fc.lock(2,30, false);//将文件的部分内容锁定,其他进程,线程,不能读取，修改锁定内容,否则抛出异常.
//		FileLock lock3 = fc.lock(71, 70, true);//将文件的部分内容锁定,其他进程，线程,可以读取内部,但是不能修改锁定内容,否则抛出异常.
		Thread thread = new Thread(() -> {
			FileChannel fcc;
			try {
				fcc = FileChannel.open(Paths.get("d:/temp/abc.txt"), StandardOpenOption.WRITE,StandardOpenOption.READ);//这里在锁定的状态下，修改其他内容成功了
				byte[] bytes = "被锁定后新线程再打开通道去修改".getBytes();
				
				fcc.read(ByteBuffer.allocate(80));
				int write2 = fcc.write(ByteBuffer.wrap(bytes));
				System.out.println("数据写完");
				fcc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		 thread.start();;//另一个程序已锁定文件的一部分，进程无法访问。
//		lock3.release();
		lock2.release();
//		 lock.release();
		fc.close();
	}

	private static void openChannel() throws IOException {
		FileChannel appendChannel = FileChannel.open(Paths.get("d:/temp/abc.t"), StandardOpenOption.READ);
		long size = appendChannel.size();
		boolean open = appendChannel.isOpen();
		long position = appendChannel.position();
		ByteBuffer src = ByteBuffer.wrap("测试管道写出数据".getBytes());
		// appendChannel.write(src);
		ByteBuffer readBuf = ByteBuffer.allocate(500);
		long position2 = appendChannel.position();
		appendChannel.read(readBuf);
		src.rewind();
		HashSet<OpenOption> hashSet = new HashSet<OpenOption>();
		hashSet.add(StandardOpenOption.READ);
		hashSet.add(StandardOpenOption.WRITE);

	}

	@SuppressWarnings("unused")
	private static void filechannel() throws IOException {
		FileChannel fChannel = FileChannel.open(Paths.get("d:/temp/abc.t"), StandardOpenOption.READ,
				StandardOpenOption.WRITE);

		long size = fChannel.size();

		boolean open = fChannel.isOpen();
		long position = fChannel.position();
		FileLock tryLock = fChannel.tryLock();
		fChannel.force(true);
		FileChannel truncate = fChannel.truncate(size - 1);
		FileLock lock = fChannel.lock(position, size, false);

		FileChannel position2 = fChannel.position(size - size / 2);
		// 映射到虚拟内存 一般用在大文件
		MappedByteBuffer map = fChannel.map(MapMode.READ_WRITE, 0, size);

		fChannel.close();

	}

	private static void fileChannelMethod() throws IOException {
		charset();
		transfer();
		ScatterGather();
		FileChannel inChannel = FileChannel.open(Paths.get("d:/temp/abc.t"), StandardOpenOption.READ);
		FileChannel outChannle = FileChannel.open(Paths.get("d:temp/bbb.txt"), StandardOpenOption.READ,
				StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		// 使用Direct缓存区 虚拟内存 映射文件
		MappedByteBuffer inMappedBuffer = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());

		MappedByteBuffer outMappedBuffer = outChannle.map(MapMode.READ_WRITE, 0, inChannel.size());

		// 直接对缓冲区进行数据的读写操作
		byte[] dst = new byte[inMappedBuffer.limit()];
		inMappedBuffer.get(dst);
		outMappedBuffer.put(dst);

		// 关闭管道
		inChannel.close();
		outChannle.close();
	}

	@SuppressWarnings("unused")
	private static void charset() throws CharacterCodingException {
		Charset cs = Charset.forName("GBK");
		// 获取编码器
		CharsetEncoder newEncoder = cs.newEncoder();
		// 获取解码器
		CharsetDecoder newDecoder = cs.newDecoder();

		CharBuffer cBuf = CharBuffer.allocate(1024);
		cBuf.put("测试编码");
		cBuf.flip();
		// 编码
		ByteBuffer bBuf = newEncoder.encode(cBuf);
		CharBuffer decode = newDecoder.decode(bBuf);

	}

	private static void ScatterGather() throws IOException {
		RandomAccessFile randomAccessFile = new RandomAccessFile("", "rw");
		FileChannel channel = randomAccessFile.getChannel();

		ByteBuffer buf1 = ByteBuffer.allocate(100);
		ByteBuffer buf2 = ByteBuffer.allocate(100);
		ByteBuffer buf3 = ByteBuffer.allocate(100);

		ByteBuffer[] bufs = { buf1, buf2, buf3 };
		// 分散读取
		long read = channel.read(bufs);
		channel.close();
		randomAccessFile.close();

		// 聚集写入
		RandomAccessFile randomAccessFile2 = new RandomAccessFile("", "rw");
		FileChannel channel2 = randomAccessFile2.getChannel();
		long write = channel2.write(bufs);
		channel2.close();
		randomAccessFile2.close();
	}

	@SuppressWarnings("unused")
	private static void transfer() throws IOException {
		FileChannel inChannel = FileChannel.open(Paths.get("d:/temp/abc.txt"), StandardOpenOption.READ,
				StandardOpenOption.WRITE);
		FileChannel outChannle = FileChannel.open(Paths.get("d:temp/bbb.txt"), StandardOpenOption.READ,
				StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		// 将管道转换 用的是直接缓冲区的方式Direct 使用虚拟缓存 映射缓存
		
		  int write3 =inChannel.write(ByteBuffer.wrap("测试原来的读通道转前，写出数据，写到哪里".getBytes("GBK"  ))); 
		  int write = outChannle.write(ByteBuffer.wrap("测试原来的接收通道的数据写到哪里".getBytes("GBK")));
		  ByteBuffer one = ByteBuffer.allocate(500); 
		  ByteBuffer two = ByteBuffer.allocate(500); 
		  ByteBuffer three = ByteBuffer.allocate(500); 
		
//		  int read2 = inChannel.read(one); 
//		  String string = new String(one.array(),"gbk");
		// 合并一个读通道。
		 long transferFrom = outChannle.transferFrom(inChannel, 0,inChannel.size());
		 outChannle.read(two);
		 String string2 = new String(two.array(),"gbk");
		 inChannel.position(0);
		 inChannel.read(three);
		 String string3 = new String(three.array(),"gbk");
		 
		// 合并一个写通道
//		int write4 = inChannel.write(ByteBuffer.wrap("原来的通道transfer前，write()".getBytes("GBK")));
//		int write = outChannle.write(ByteBuffer.wrap("接收通道前，write()".getBytes()));

//		inChannel.transferTo(0, inChannel.size(), outChannle);

//		int write5 = inChannel.write(ByteBuffer.wrap("原来的通道transfer后，write()".getBytes("GBK")));
//		int write2 = outChannle.write(ByteBuffer.wrap(("接收通道后。write").getBytes()));
		// 本通道合并另一个可读的通道，可以读到合并操作前的被合并通道的内容
		// 本通道，合并到另一个通道，可以将合并前，本通道的内容写到另一个通道中。

		inChannel.close();
		outChannle.close();
	}

	private static void originIO() {
		try (// 使用try with创建流
				FileInputStream fi = new FileInputStream(""); FileOutputStream fo = new FileOutputStream("");) {
			// 根据原始的IO流获取通道
			FileChannel inChannel = fi.getChannel();
			FileChannel outChannel = fo.getChannel();

			// 新建缓冲区 非直接缓冲区
			ByteBuffer allocate = ByteBuffer.allocate(1024);
			// 将通道中的数据写入缓冲区
			while (inChannel.read(allocate) != -1) {
				// 将缓冲去的标志准备好读
				allocate.flip();// limit=position; position=0; mark=-1
				// 读取缓冲区的数据写到通道中
				outChannel.write(allocate);
				// 清空缓冲区
				allocate.clear();// position=0;limit=capacity;mark=-1
			}
			// 关闭流，关闭管道
		} catch (Exception e) {
		} finally {
		}
	}

	@SuppressWarnings("unused")
	private static void createObj() throws IOException {
		// 根据原始输入输出流，转换成通道，原始的流也可以getChannle获取对应的管道
		ReadableByteChannel readableChannel = Channels.newChannel(new FileInputStream("d:/temp/abc.t"));
		WritableByteChannel writableChannel = Channels.newChannel(new FileOutputStream("d:temp/abc.t"));

		// 根据byte管道获取原始的字符流 Reader和Writer
		Writer newWriter = Channels.newWriter(writableChannel, "utf-8");
		Reader newReader = Channels.newReader(readableChannel, "utf-8");

		// 根据byte管道获取原始的字节流
		OutputStream newOutputStream = Channels.newOutputStream(writableChannel);
		InputStream newInputStream = Channels.newInputStream(readableChannel);

		// 指定charset的没有实现
		Writer newWriter2 = Channels.newWriter(writableChannel, Charset.defaultCharset().newEncoder(), 500);
		Reader newReader2 = Channels.newReader(readableChannel, Charset.defaultCharset().newDecoder(), 500);

		// 获取FileChannle实例
		FileChannel inChannel = FileChannel.open(Paths.get("d:/temp/abc.txt"), StandardOpenOption.READ,
				StandardOpenOption.WRITE);
		FileChannel outChannle = FileChannel.open(Paths.get("d:temp/bbb.txt"), StandardOpenOption.READ,
				StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	}

}
