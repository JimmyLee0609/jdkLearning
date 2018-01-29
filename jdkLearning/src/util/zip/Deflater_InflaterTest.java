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
		Deflater deflater = new Deflater();// 选择0level，false
		new Deflater(Deflater.DEFLATED);// 选择压缩的level就是0-9的等级，一般是8
		new Deflater(Deflater.DEFLATED, false);// 是否使用ZIP的头部和检验字段。true不适用，适合GZIP，false适用，适合ZIP

//		如果输入数据缓冲区为空，则返回true，并且应该调用setInput（）以提供更多的输入。
		boolean needsInput = deflater.needsInput();
//		设置输入数据进行压缩。 当needsInput（）返回true时，这应该被调用，表示需要更多的输入数据。
		deflater.setInput(("Many sources of information contain redundant data or data that adds little to the stored information. This results in tremendous amounts of data being transferred between client and server applications or computers in general. The obvious solution to the problems of data storage and information transfer is to install additional storage devices and expand existing communication facilities. To do so, however, requires an increase in an organization's operating costs. One method to alleviate a portion of data storage and information transfer is through the representation of data by more efficient code. This article presents a brief introduction to data compression and decompression, and shows how to compress and decompress data, efficiently and conveniently, from within your Java applications using the java.util.zip package. While it is possible to compress and decompress data using tools such as WinZip, gzip, and Java ARchive (or jar), these tools are used as standalone applications. It is possible to invoke these tools from your Java applications, but this is not a straightforward approach and not an efficient solution. This is especially true if you wish to compress and decompress data on the fly (before transferring it to a remote machine for example). This article: Gives you a brief overview of data compression Describes the java.util.zip package Shows how to use this package to compress and decompress data  Shows how to compress and decompress serialized objects to save disk space Shows how to compress and decompress data on the fly to improve the performance of client/server applications Overview of Data Compression The simplest type of redundancy in a file is the repetition of characters. For example, consider the following string: BBBBHHDDXXXXKKKKWWZZZZ This string can be encoded more compactly by replacing each repeated string of characters by a single instance of the repeated character and a number that represents the number of times it is repeated. The earlier string can be encoded as follows: Here \"4B\" means four B's, and 2H means two H's, and so on. Compressing a string in this way is called run-length encoding. As another example, consider the storage of a rectangular image. As a single color bitmapped image, it can be stored as shown in Figure 1. Figure 1: A bitmap with information for run-length encoding Figure 1: A bitmap with information for run-length encoding Another approach might be to store the image as a graphics metafile: Rectangle 11, 3, 20, 5 This says, the rectangle starts at coordinate (11, 3) of width 20 and length 5 pixels. The rectangular image can be compressed with run-length encoding by counting identical bits as follows: The first line above says that the first line of the bitmap consists of 40 0's. The third line says that the third line of the bitmap consists of 10 0's followed by 20 1's followed by 10 more 0's, and so on for the other lines. Note that run-length encoding requires separate representations for the file and its encoded version. Therefore, this method cannot work for all files. Other compression techniques include variable-length encoding (also known as Huffman Coding), and many others. For more information, there are many books available on data and image compression techniques. There are many benefits to data compression. The main advantage of it, however, is to reduce storage requirements. Also, for data communications, the transfer of compressed data over a medium results in an increase in the rate of information transfer. Note that data compression can be implemented on existing hardware by software or through the use of special hardware devices that incorporate compression techniques. Figure 2 shows a basic data-compression block diagram.").getBytes());

//		当被调用时，表示压缩应该以输入缓冲区的当前内容结束。
		deflater.finish();
		deflater.setInput("test for multy input".getBytes());//新设置的会覆盖原来设置的数据
		
//		新建一个保存压缩后数据的缓冲区
		byte[] b =new byte[1024];
		
//		压缩传入的数据，将压缩后的数据写到缓冲区B中
		int deflate3 = deflater.deflate(b, 0, 100);//100

		// 改变压缩策略，
//		deflater.setStrategy(Deflater.DEFAULT_STRATEGY);
		
//		压缩输入数据并用压缩数据填充指定的缓冲区。 返回压缩数据的实际字节数。
		boolean needsInput3 = deflater.needsInput();
		int deflate = deflater.deflate(b, 300, 50, Deflater.NO_FLUSH);//50
		
/*		NO_FLUSH:  允许deflater在产生输出之前决定要累积多少数据，以便达到最佳压缩（应该在正常使用场景中使用）。 在此刷新模式下，返回值为0表示应调用needsInput（）以确定是否需要更多输入数据。
		SYNC_FLUSH: 在deflater中的所有挂起的输出都被刷新到指定的输出缓冲区，这样一个工作在压缩数据上的inflater可以获得到目前为止所有可用的输入数据（特别是如果提供了足够的输出空间，needsInput（）在这个调用之后返回true）。 使用SYNC_FLUSH刷新可能会降低某些压缩算法的压缩率，因此只能在必要时才使用。
		FULL_FLUSH: 与SYNC_FLUSH一样，所有挂起的输出都被刷新。 如果以前的压缩数据已经被损坏或者需要随机访问，压缩状态被重置，使得对压缩的输出数据起作用的充气器可以从这一点重新开始。 使用FULL_FLUSH常常会严重降低压缩率。
	*/	
		
		// 改变压缩等级
//		deflater.setLevel(Deflater.BEST_COMPRESSION);
		boolean needsInput2 = deflater.needsInput();
		int deflate2 = deflater.deflate(b, 800,100, Deflater.FULL_FLUSH);//100
		// 返回为压缩数据的adler-32的值
		int adler = deflater.getAdler();//1454855668
		
//		返回到目前为止输入的未压缩字节的总数。
		long bytesRead = deflater.getBytesRead();//首选  3763
//		返回迄今为止输出的压缩字节总数。
		long bytesWritten = deflater.getBytesWritten();//首选  250
//		返回到目前为止输入的未压缩字节的总数。
		int totalIn = deflater.getTotalIn();
//		返回迄今为止输出的压缩字节总数。
		int totalOut = deflater.getTotalOut();
		
		
//		设置预设字典进行压缩。 当预先确定历史缓冲器时使用预置字典。 
//		当数据稍后使用Inflater.inflate（）进行解压缩时，可以调用Inflater.getAdler（）以获取解压缩所需的字典的Adler-32值。
//		deflater.setDictionary("dictionary".getBytes());参数不会用
		
//		重置deflater，以便可以处理一组新的输入数据。 保持当前的压缩级别和策略设置。
		deflater.reset();
		
//		关闭压缩算法，其中的缓存将清空，为处理的数据将被抛弃
		deflater.end();
	}

	private static void simple() throws DataFormatException, UnsupportedEncodingException {
		/*
		 * Deflater是一个压缩的算法。下面的例子就是一个压缩的例子
		 */
		String inputString = "blahblahblah";
		byte[] input = inputString.getBytes("UTF-8");

		// 保存压缩后的数据
		byte[] output = new byte[100];
//		新建一个压缩对象
		Deflater compresser = new Deflater();
//		为压缩对象设置源数据
		compresser.setInput(input);
//		当被调用时，表示压缩应该以输入缓冲区的当前内容结束。
		compresser.finish();
//		压缩数据，返回压缩后写出的个数，压缩后的数据将保存在output的缓存中
		int compressedDataLength = compresser.deflate(output);
//		关闭压缩算法，其中的缓存将清空，为处理的数据将被抛弃
		compresser.end();

		/*
		 * Inflater是一个对应Deflater的解压缩的算法。下面是将Deflater压缩的数据进行解压缩
		 */
		Inflater decompresser = new Inflater();
		decompresser.setInput(output, 0, compressedDataLength);
		byte[] result = new byte[100];
		int resultLength = decompresser.inflate(result);
		decompresser.end();

		// 将解压缩的数据重新解码，会的到结果blahblahblah
		String outputString = new String(result, 0, resultLength, "UTF-8");
	}

}
