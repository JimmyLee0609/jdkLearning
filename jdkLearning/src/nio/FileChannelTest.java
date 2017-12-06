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
		int write = fc.write(ByteBuffer.wrap("����������ʲô��".getBytes()));
//		FileLock lock = fc.lock();//����֮��,�������̾��޷���ȡ�ļ�,�ڲ��õ��� lock(0L, Long.MAX_VALUE, false);
		
//		NonReadableChannelException -�ܵ�û��StandardOpenOption.READ��дtrue���쳣
//		NonWritableChannelException - �ܵ�û��StandardOpenOption.WRITE��дfalse���쳣
//		�ܵ����ܽ��ظ��������ܵ��Ĳ�������     ������
		FileLock lock2 = fc.lock(2,30, false);//���ļ��Ĳ�����������,��������,�߳�,���ܶ�ȡ���޸���������,�����׳��쳣.
//		FileLock lock3 = fc.lock(71, 70, true);//���ļ��Ĳ�����������,�������̣��߳�,���Զ�ȡ�ڲ�,���ǲ����޸���������,�����׳��쳣.
		Thread thread = new Thread(() -> {
			FileChannel fcc;
			try {
				fcc = FileChannel.open(Paths.get("d:/temp/abc.txt"), StandardOpenOption.WRITE,StandardOpenOption.READ);//������������״̬�£��޸��������ݳɹ���
				byte[] bytes = "�����������߳��ٴ�ͨ��ȥ�޸�".getBytes();
				
				fcc.read(ByteBuffer.allocate(80));
				int write2 = fcc.write(ByteBuffer.wrap(bytes));
				System.out.println("����д��");
				fcc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		 thread.start();;//��һ�������������ļ���һ���֣������޷����ʡ�
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
		ByteBuffer src = ByteBuffer.wrap("���Թܵ�д������".getBytes());
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
		// ӳ�䵽�����ڴ� һ�����ڴ��ļ�
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
		// ʹ��Direct������ �����ڴ� ӳ���ļ�
		MappedByteBuffer inMappedBuffer = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());

		MappedByteBuffer outMappedBuffer = outChannle.map(MapMode.READ_WRITE, 0, inChannel.size());

		// ֱ�ӶԻ������������ݵĶ�д����
		byte[] dst = new byte[inMappedBuffer.limit()];
		inMappedBuffer.get(dst);
		outMappedBuffer.put(dst);

		// �رչܵ�
		inChannel.close();
		outChannle.close();
	}

	@SuppressWarnings("unused")
	private static void charset() throws CharacterCodingException {
		Charset cs = Charset.forName("GBK");
		// ��ȡ������
		CharsetEncoder newEncoder = cs.newEncoder();
		// ��ȡ������
		CharsetDecoder newDecoder = cs.newDecoder();

		CharBuffer cBuf = CharBuffer.allocate(1024);
		cBuf.put("���Ա���");
		cBuf.flip();
		// ����
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
		// ��ɢ��ȡ
		long read = channel.read(bufs);
		channel.close();
		randomAccessFile.close();

		// �ۼ�д��
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
		// ���ܵ�ת�� �õ���ֱ�ӻ������ķ�ʽDirect ʹ�����⻺�� ӳ�仺��
		
		  int write3 =inChannel.write(ByteBuffer.wrap("����ԭ���Ķ�ͨ��תǰ��д�����ݣ�д������".getBytes("GBK"  ))); 
		  int write = outChannle.write(ByteBuffer.wrap("����ԭ���Ľ���ͨ��������д������".getBytes("GBK")));
		  ByteBuffer one = ByteBuffer.allocate(500); 
		  ByteBuffer two = ByteBuffer.allocate(500); 
		  ByteBuffer three = ByteBuffer.allocate(500); 
		
//		  int read2 = inChannel.read(one); 
//		  String string = new String(one.array(),"gbk");
		// �ϲ�һ����ͨ����
		 long transferFrom = outChannle.transferFrom(inChannel, 0,inChannel.size());
		 outChannle.read(two);
		 String string2 = new String(two.array(),"gbk");
		 inChannel.position(0);
		 inChannel.read(three);
		 String string3 = new String(three.array(),"gbk");
		 
		// �ϲ�һ��дͨ��
//		int write4 = inChannel.write(ByteBuffer.wrap("ԭ����ͨ��transferǰ��write()".getBytes("GBK")));
//		int write = outChannle.write(ByteBuffer.wrap("����ͨ��ǰ��write()".getBytes()));

//		inChannel.transferTo(0, inChannel.size(), outChannle);

//		int write5 = inChannel.write(ByteBuffer.wrap("ԭ����ͨ��transfer��write()".getBytes("GBK")));
//		int write2 = outChannle.write(ByteBuffer.wrap(("����ͨ����write").getBytes()));
		// ��ͨ���ϲ���һ���ɶ���ͨ�������Զ����ϲ�����ǰ�ı��ϲ�ͨ��������
		// ��ͨ�����ϲ�����һ��ͨ�������Խ��ϲ�ǰ����ͨ��������д����һ��ͨ���С�

		inChannel.close();
		outChannle.close();
	}

	private static void originIO() {
		try (// ʹ��try with������
				FileInputStream fi = new FileInputStream(""); FileOutputStream fo = new FileOutputStream("");) {
			// ����ԭʼ��IO����ȡͨ��
			FileChannel inChannel = fi.getChannel();
			FileChannel outChannel = fo.getChannel();

			// �½������� ��ֱ�ӻ�����
			ByteBuffer allocate = ByteBuffer.allocate(1024);
			// ��ͨ���е�����д�뻺����
			while (inChannel.read(allocate) != -1) {
				// ������ȥ�ı�־׼���ö�
				allocate.flip();// limit=position; position=0; mark=-1
				// ��ȡ������������д��ͨ����
				outChannel.write(allocate);
				// ��ջ�����
				allocate.clear();// position=0;limit=capacity;mark=-1
			}
			// �ر������رչܵ�
		} catch (Exception e) {
		} finally {
		}
	}

	@SuppressWarnings("unused")
	private static void createObj() throws IOException {
		// ����ԭʼ�����������ת����ͨ����ԭʼ����Ҳ����getChannle��ȡ��Ӧ�Ĺܵ�
		ReadableByteChannel readableChannel = Channels.newChannel(new FileInputStream("d:/temp/abc.t"));
		WritableByteChannel writableChannel = Channels.newChannel(new FileOutputStream("d:temp/abc.t"));

		// ����byte�ܵ���ȡԭʼ���ַ��� Reader��Writer
		Writer newWriter = Channels.newWriter(writableChannel, "utf-8");
		Reader newReader = Channels.newReader(readableChannel, "utf-8");

		// ����byte�ܵ���ȡԭʼ���ֽ���
		OutputStream newOutputStream = Channels.newOutputStream(writableChannel);
		InputStream newInputStream = Channels.newInputStream(readableChannel);

		// ָ��charset��û��ʵ��
		Writer newWriter2 = Channels.newWriter(writableChannel, Charset.defaultCharset().newEncoder(), 500);
		Reader newReader2 = Channels.newReader(readableChannel, Charset.defaultCharset().newDecoder(), 500);

		// ��ȡFileChannleʵ��
		FileChannel inChannel = FileChannel.open(Paths.get("d:/temp/abc.txt"), StandardOpenOption.READ,
				StandardOpenOption.WRITE);
		FileChannel outChannle = FileChannel.open(Paths.get("d:temp/bbb.txt"), StandardOpenOption.READ,
				StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	}

}
