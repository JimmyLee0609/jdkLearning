package util.zip;

import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Deflater_InflaterTest {

	public static void main(String[] args) throws DataFormatException, UnsupportedEncodingException {
//		simple();
		deflater();
	}

	@SuppressWarnings("unused")
	private static void deflater() {
		Deflater deflater = new Deflater();// ѡ��0level��false
		new Deflater(Deflater.DEFLATED);// ѡ��ѹ����level����0-9�ĵȼ���һ����8
		new Deflater(Deflater.DEFLATED, false);// �Ƿ�ʹ��ZIP��ͷ���ͼ����ֶΡ�true�����ã��ʺ�GZIP��false���ã��ʺ�ZIP

//		����������ݻ�����Ϊ�գ��򷵻�true������Ӧ�õ���setInput�������ṩ��������롣
		boolean needsInput = deflater.needsInput();
//		�����������ݽ���ѹ���� ��needsInput��������trueʱ����Ӧ�ñ����ã���ʾ��Ҫ������������ݡ�
		deflater.setInput(("Many sources of information contain redundant data or data that adds little to the stored information. This results in tremendous amounts of data being transferred between client and server applications or computers in general. The obvious solution to the problems of data storage and information transfer is to install additional storage devices and expand existing communication facilities. To do so, however, requires an increase in an organization's operating costs. One method to alleviate a portion of data storage and information transfer is through the representation of data by more efficient code. This article presents a brief introduction to data compression and decompression, and shows how to compress and decompress data, efficiently and conveniently, from within your Java applications using the java.util.zip package. While it is possible to compress and decompress data using tools such as WinZip, gzip, and Java ARchive (or jar), these tools are used as standalone applications. It is possible to invoke these tools from your Java applications, but this is not a straightforward approach and not an efficient solution. This is especially true if you wish to compress and decompress data on the fly (before transferring it to a remote machine for example). This article: Gives you a brief overview of data compression Describes the java.util.zip package Shows how to use this package to compress and decompress data  Shows how to compress and decompress serialized objects to save disk space Shows how to compress and decompress data on the fly to improve the performance of client/server applications Overview of Data Compression The simplest type of redundancy in a file is the repetition of characters. For example, consider the following string: BBBBHHDDXXXXKKKKWWZZZZ This string can be encoded more compactly by replacing each repeated string of characters by a single instance of the repeated character and a number that represents the number of times it is repeated. The earlier string can be encoded as follows: Here \"4B\" means four B's, and 2H means two H's, and so on. Compressing a string in this way is called run-length encoding. As another example, consider the storage of a rectangular image. As a single color bitmapped image, it can be stored as shown in Figure 1. Figure 1: A bitmap with information for run-length encoding Figure 1: A bitmap with information for run-length encoding Another approach might be to store the image as a graphics metafile: Rectangle 11, 3, 20, 5 This says, the rectangle starts at coordinate (11, 3) of width 20 and length 5 pixels. The rectangular image can be compressed with run-length encoding by counting identical bits as follows: The first line above says that the first line of the bitmap consists of 40 0's. The third line says that the third line of the bitmap consists of 10 0's followed by 20 1's followed by 10 more 0's, and so on for the other lines. Note that run-length encoding requires separate representations for the file and its encoded version. Therefore, this method cannot work for all files. Other compression techniques include variable-length encoding (also known as Huffman Coding), and many others. For more information, there are many books available on data and image compression techniques. There are many benefits to data compression. The main advantage of it, however, is to reduce storage requirements. Also, for data communications, the transfer of compressed data over a medium results in an increase in the rate of information transfer. Note that data compression can be implemented on existing hardware by software or through the use of special hardware devices that incorporate compression techniques. Figure 2 shows a basic data-compression block diagram.").getBytes());

//		��������ʱ����ʾѹ��Ӧ�������뻺�����ĵ�ǰ���ݽ�����
		deflater.finish();
		deflater.setInput("test for multy input".getBytes());//�����õĻḲ��ԭ�����õ�����
		
//		�½�һ������ѹ�������ݵĻ�����
		byte[] b =new byte[1024];
		
//		ѹ����������ݣ���ѹ���������д��������B��
		int deflate3 = deflater.deflate(b, 0, 100);//100

		// �ı�ѹ�����ԣ�
//		deflater.setStrategy(Deflater.DEFAULT_STRATEGY);
		
//		ѹ���������ݲ���ѹ���������ָ���Ļ������� ����ѹ�����ݵ�ʵ���ֽ�����
		boolean needsInput3 = deflater.needsInput();
		int deflate = deflater.deflate(b, 300, 50, Deflater.NO_FLUSH);//50
		
/*		NO_FLUSH:  ����deflater�ڲ������֮ǰ����Ҫ�ۻ��������ݣ��Ա�ﵽ���ѹ����Ӧ��������ʹ�ó�����ʹ�ã��� �ڴ�ˢ��ģʽ�£�����ֵΪ0��ʾӦ����needsInput������ȷ���Ƿ���Ҫ�����������ݡ�
		SYNC_FLUSH: ��deflater�е����й�����������ˢ�µ�ָ�������������������һ��������ѹ�������ϵ�inflater���Ի�õ�ĿǰΪֹ���п��õ��������ݣ��ر�������ṩ���㹻������ռ䣬needsInput�������������֮�󷵻�true���� ʹ��SYNC_FLUSHˢ�¿��ܻή��ĳЩѹ���㷨��ѹ���ʣ����ֻ���ڱ�Ҫʱ��ʹ�á�
		FULL_FLUSH: ��SYNC_FLUSHһ�������й�����������ˢ�¡� �����ǰ��ѹ�������Ѿ����𻵻�����Ҫ������ʣ�ѹ��״̬�����ã�ʹ�ö�ѹ����������������õĳ��������Դ���һ�����¿�ʼ�� ʹ��FULL_FLUSH���������ؽ���ѹ���ʡ�
	*/	
		
		// �ı�ѹ���ȼ�
//		deflater.setLevel(Deflater.BEST_COMPRESSION);
		boolean needsInput2 = deflater.needsInput();
		int deflate2 = deflater.deflate(b, 800,100, Deflater.FULL_FLUSH);//100
		// ����Ϊѹ�����ݵ�adler-32��ֵ
		int adler = deflater.getAdler();//1454855668
		
//		���ص�ĿǰΪֹ�����δѹ���ֽڵ�������
		long bytesRead = deflater.getBytesRead();//��ѡ  3763
//		��������Ϊֹ�����ѹ���ֽ�������
		long bytesWritten = deflater.getBytesWritten();//��ѡ  250
//		���ص�ĿǰΪֹ�����δѹ���ֽڵ�������
		int totalIn = deflater.getTotalIn();
//		��������Ϊֹ�����ѹ���ֽ�������
		int totalOut = deflater.getTotalOut();
		
		
//		����Ԥ���ֵ����ѹ���� ��Ԥ��ȷ����ʷ������ʱʹ��Ԥ���ֵ䡣 
//		�������Ժ�ʹ��Inflater.inflate�������н�ѹ��ʱ�����Ե���Inflater.getAdler�����Ի�ȡ��ѹ��������ֵ��Adler-32ֵ��
//		deflater.setDictionary("dictionary".getBytes());����������
		
//		����deflater���Ա���Դ���һ���µ��������ݡ� ���ֵ�ǰ��ѹ������Ͳ������á�
		deflater.reset();
		
//		�ر�ѹ���㷨�����еĻ��潫��գ�Ϊ��������ݽ�������
		deflater.end();
	}

	private static void simple() throws DataFormatException, UnsupportedEncodingException {
		/*
		 * Deflater��һ��ѹ�����㷨����������Ӿ���һ��ѹ��������
		 */
		String inputString = "blahblahblah";
		byte[] input = inputString.getBytes("UTF-8");

		// ����ѹ���������
		byte[] output = new byte[100];
//		�½�һ��ѹ������
		Deflater compresser = new Deflater();
//		Ϊѹ����������Դ����
		compresser.setInput(input);
//		��������ʱ����ʾѹ��Ӧ�������뻺�����ĵ�ǰ���ݽ�����
		compresser.finish();
//		ѹ�����ݣ�����ѹ����д���ĸ�����ѹ��������ݽ�������output�Ļ�����
		int compressedDataLength = compresser.deflate(output);
//		�ر�ѹ���㷨�����еĻ��潫��գ�Ϊ��������ݽ�������
		compresser.end();

		/*
		 * Inflater��һ����ӦDeflater�Ľ�ѹ�����㷨�������ǽ�Deflaterѹ�������ݽ��н�ѹ��
		 */
		Inflater decompresser = new Inflater();
		decompresser.setInput(output, 0, compressedDataLength);
		byte[] result = new byte[100];
		int resultLength = decompresser.inflate(result);
		decompresser.end();

		// ����ѹ�����������½��룬��ĵ����blahblahblah
		String outputString = new String(result, 0, resultLength, "UTF-8");
	}

}
