package io;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

import javax.swing.filechooser.FileFilter;

public class FileTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, URISyntaxException {
		filter();
		// 创建File文件方式
		File file3 = new File("d:/temp", "users.xml");// 通过父目录和子目录联合成绝对路径
														// 就是相对路径的可以起作用的缘由
		File file = new File("d:/temp/users.xml");// 通过绝对路径
		File file4 = new File(new URI("file:///d:/temp/users.xml"));// 通过URI就是
																	// 协议++路径

		File file8 = new File("/");// 运行java程序的 当前路径
		String absolutePath3 = file8.getAbsolutePath();// 我的git文件 在 C:\

		String pathseparator = File.pathSeparator;// 多个路径的分隔符 ;
		char pathseparatorchar = File.pathSeparatorChar;// 多个路径的分隔符 ;
		String separator = File.separator;// 路径的分隔符 \
		char separatorchar = File.separatorChar;// 路径的分隔符 \

		// 文件是否可读
		boolean canExecute = file.canExecute();// true
		// 文件是否存在
		boolean exists = file.exists();// true
		// 文件是否可读
		boolean canRead = file.canRead();// true
		// 测试此抽象路径名是否为绝对路径名。
		boolean absolute = file.isAbsolute();// true
		// 文件是否可写
		boolean canWrite = file.canWrite();// true
		// 文件是否是文件夹
		boolean directory = file.isDirectory();// false
		// 文件是否是文件
		boolean file2 = file.isFile();// true
		// 文件是否隐藏
		boolean hidden = file.isHidden();// false

		// 获取文件的绝对路径
		String absolutePath = file.getAbsolutePath(); // d:/temp/users.xml
		// 获取文件的路径
		String path = file.getPath();// d:/temp/users.xml
		// 获取文件的父路径
		String parent = file.getParent();// d:/temp
		// 返回此抽象路径名父目录的抽象路径名；如果此路径名没有指定父目录，则返回 null。
		File parentFile = file.getParentFile();// d:/temp
		// 获取文件的名字
		String name = file.getName();// users.xml
		// 获取最后一次修改的时间
		long lastModified = file.lastModified();// 1487610003538

		// 创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
		boolean mkdirs = file.mkdirs();// 文件已经存在，不需要创建，如果文件不存在，就会创建
		// 创建此抽象路径名指定的目录。
		boolean mkdir = file.mkdir();
		// 当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件。
		boolean createNewFile = file.createNewFile();
		// 返回此抽象路径名的绝对路径名形式。
		File absoluteFile = file.getAbsoluteFile();// d:\\temp\\users.xml

		// 设置最后一次修改的时间
		boolean setLastModified = file.setLastModified(System.currentTimeMillis());
		// 重命名为
		// boolean renameTo = file.renameTo(new File(""));
		// 设置可写
		boolean setWritable2 = file.setWritable(true);
		// 设置是否拥有者可写,还是非拥有者可写
		boolean setWritable = file.setWritable(false, true);
		// 设置可执行
		boolean setExecutable = file.setExecutable(false);
		// 是否拥有者 能否执行
		boolean setExecutable2 = file.setExecutable(true, true);
		// 设置是否可读
		boolean setReadable = file.setReadable(true);
		// 是否拥有者 是否可读
		boolean setReadable2 = file.setReadable(true, true);
		// 设置是否只读
		boolean setReadOnly = file.setReadOnly();

		// 返回此抽象路径名的规范路径名字符串。
		String canonicalPath = file.getCanonicalPath();// D:/temp/users.xml
		// 返回此抽象路径名的规范形式。
		File canonicalFile = file.getCanonicalFile();// D:/temp/users.xml
		String string = file.toString();

		// 获取文件剩余空间 这个获取的是盘符下的大小
		long freeSpace = file.getFreeSpace();// 50574974976
		// 获取文件的整个大小 这个获取的是这个盘符下的大小
		long totalSpace = file.getTotalSpace();// 287631732736
		// 获取文件可用空间 这个获取的是盘符下的大小
		long usableSpace = file.getUsableSpace();// 50574974976
		// 文件的长度 这个获取的是当前文件的大小
		long length = file.length();// 0

		// 将文件转换为路径
		Path path2 = file.toPath();// d:/temp/users.xml
		// 将文件转换为URI
		URI uri = file.toURI();// file:/d:/temp/users.xml

		// 列出可用的文件系统根目录 D:/ C:/ E:/
		File[] listRoots = File.listRoots();// [C:\, D:\, E:\]

		// 将文件夹路径下的文件，文件夹进行转换
		File file5 = new File("D:/temp", "B/abc.txt");
		boolean absolute2 = file5.isAbsolute();
		boolean file6 = file.isFile();// true
		boolean directory2 = file5.isDirectory();// false
		String[] list = file5.list();// 成字符串 null
		File[] listFiles = file5.listFiles();// 成为文件 null

		File file7 = new File("d:/temp");
		boolean directory3 = file7.isDirectory();// true
		String[] list2 = file7.list();// 列出文件名[A, abc.t, ABC.txt, B,
										// HelloWorld.class, HelloWorld.jar,
										// HelloWorld.java, mydomain.csr,
										// seconrC.crt, sHelloWorld.jar,
										// test.crt, testKey.crt, users.xml]
		File[] listFiles2 = file7.listFiles();// 列出文件

		// 按字母顺序比较两个抽象路径名。
		int compareTo = file5.compareTo(file7);// 10

		// C:/Users/cobbl/AppData/Local/Temp 在系统指定的临时文件夹创建
		File createTempFile = File.createTempFile("abc", "test");
		String absolutePath2 = createTempFile.getAbsolutePath();

		// 文件退出时删除
		file.deleteOnExit();
		/// 删除文件
		boolean delete = file.delete();
	}

	@SuppressWarnings("unused")
	private static void filter() {
		File file = new File("D:\\temp");
//		使用过滤器  过滤文件
		String[] list = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// dir 这里表示 file 的路径 name 就是路径下找到的每个文件的名字
				int b = 0;
				return false;
			}
		});
	}

}
