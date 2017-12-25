package nio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileStoreAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.time.Instant;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.naming.directory.BasicAttribute;

public class FilesTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// 复制文件
		// copyFile();
		// 访问文件属性
//		 fileAttitude();
		// readBasicAttribute();
		// 创建文件夹
//		 createDirectory();
		// 创建文件，连接
		// createFile();
		// 删除文件
		// delete();
		// 文件是否存在
		// exists();
		// 列出文件夹中的文件及文件夹
		// listFile();
		// 读取文件
		// readFile();
		// 写出文件
		// writeFile();
		// 文件存储设备
//		fileStore();
		walkFile();
	}

	private static void walkFile()  {
		try {
			Stream<Path> walk = Files.walk(Paths.get("d:/temp"), 2, FileVisitOption.FOLLOW_LINKS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		遍历文件              文件基础目录                                    访问的方式                               遍历文件夹深度
		try {
			Files.walkFileTree(Paths.get("d:/temp"), EnumSet.of(FileVisitOption.FOLLOW_LINKS), 50,
					new SimpleFileVisitor<Path>() { //visitor
				 @Override
				    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
				        throws IOException
				    {
//				 正在访问的文件夹
				        Objects.requireNonNull(dir);
				        Objects.requireNonNull(attrs);
//			        					继续遍历                     跳过本层目录的文件       跳过这个文件夹的子目录           终止
//			        FileVisitResult.CONTINUE;FileVisitResult.SKIP_SIBLINGS;FileVisitResult.SKIP_SUBTREE;FileVisitResult.TERMINATE
//			        使用FileVisitResult来控制遍历到这个文件夹后的操作
				        return FileVisitResult.CONTINUE;
				    }
				 @Override
				    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				        throws IOException
				    {
//				 遍历到每一个文件
				        Objects.requireNonNull(file);
				        Objects.requireNonNull(attrs);
				        try{
				        FileChannel open = FileChannel.open(file, StandardOpenOption.READ);
				        int read = open.read(ByteBuffer.allocate(50));
				        }catch(IOException e){
				        	visitFileFailed(file, e);
				        }
				        return FileVisitResult.CONTINUE;
				    }
				 @Override
				    public FileVisitResult visitFileFailed(Path file, IOException exc)
				        throws IOException
				    {
//				访问文件发生错误时调用，如无权限
				        Objects.requireNonNull(file);
//				        throw exc;
				       return  FileVisitResult.CONTINUE;
				    }
				 
				 @Override
				    public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				        throws IOException
				    {
//				 访问文件夹之后条用
				        Objects.requireNonNull(dir);
				        if (exc != null)
				            throw exc;
				        return FileVisitResult.CONTINUE;
				    }
				 
					});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private static void fileStore() throws IOException {
		// 文件存储 FileStore表示存储池，设备，分区，卷，具体文件系统或其他实现文件存储的具体方式。
		FileStore fileStore = Files.getFileStore(Paths.get("d:/temp"));
		
		long totalSpace = fileStore.getTotalSpace();// 287631732736
		long unallocatedSpace = fileStore.getUnallocatedSpace();// 50070798336
		long usableSpace = fileStore.getUsableSpace();// 50070798336
		
		boolean readOnly = fileStore.isReadOnly();// false
		String name = fileStore.name();// DATA
		String type = fileStore.type();// NTFS

		Object attribute = fileStore.getAttribute("totalSpace");// totalSpace,unallocatedSpace,usableSpace就这三个参数
		FileStoreAttributeView fileStoreAttributeView = fileStore
				.getFileStoreAttributeView(FileStoreAttributeView.class);// null

		boolean supportsFileAttributeView2 = fileStore.supportsFileAttributeView(DosFileAttributeView.class);// true
		boolean supportsFileAttributeView = fileStore.supportsFileAttributeView("basic");// true
//		见过的支持的view的类型basic     posix   dos    acl     user
		
		String string = fileStore.toString();// DATA (d:)
	}

	@SuppressWarnings("unused")
	private static void writeFile() throws IOException {
		// 根据path，字符集，文件打开参数，获取字符输出流
		BufferedWriter newBufferedWriter = Files.newBufferedWriter(Paths.get("d:/temp/abc.txt"), Charset.forName("GBK"),
				StandardOpenOption.APPEND);
		newBufferedWriter.close();
		// 根据path和文件打开参数，获取字节流
		OutputStream newOutputStream = Files.newOutputStream(Paths.get("D:/TEMP/ABC.TXT"), StandardOpenOption.APPEND);
		newOutputStream.close();
		// 将字节数据写入文件，并指定写入文件的参数，append在文件后面续写，还是从文件开头覆盖，还是将文件截断重写
		Path write = Files.write(Paths.get("d:/temp/abc.txt"), "just for test Files Write to File，就是测试一下".getBytes(),
				StandardOpenOption.APPEND);
		// 将一组可以迭代的数据按照指定的字符集写到文件中，并指定写出文件时，打开文件的参数
		List<String> lines = Arrays.asList(new String[] { "第一行", "第二行", "第三行", "第四行" });
		Files.write(Paths.get("d:/temp/abc.txt"), lines, Charset.forName("GBK"), StandardOpenOption.APPEND);
	}

	@SuppressWarnings({ "unused", "resource" })
	private static void readFile() throws IOException {
		// 将文件的所有内容都读进数组
		byte[] readAllBytes = Files.readAllBytes(Paths.get("d:/temp/bbb.txt"));
		// 将文件的所有内容，按照编码以行为单位读进一个List集合
		List<String> readAllLines2 = Files.readAllLines(Paths.get("d:/temp/bbb.txt"), Charset.forName("GBK"));
		// 准备好读取文件的路径，和字符集，创建一个流，没有开始读取文件。需要在流开始才会读取文件
		Stream<String> lines2 = Files.lines(Paths.get("D:/temp/bbb.txt"), Charset.forName("GBK"));
		// 根据path,文件打开方式，创建一个-------字节输入流--------
		InputStream newInputStream = Files.newInputStream(Paths.get("d:/temp/bbb.txt"), StandardOpenOption.READ);
		newInputStream.close();
		// 根据路径,字符集创建一个bufferReadder,带缓冲的-------字符输入流---------
		BufferedReader newBufferedReader = Files.newBufferedReader(Paths.get("d:/temp/bbb.txt"),
				Charset.forName("GBK"));
		newBufferedReader.close();
		// 根据路径和文件打开选项创建一个---------------ByteChannel-管道-------------
		SeekableByteChannel newByteChannel = Files.newByteChannel(Paths.get("d:/temp/bbb.txt"),
				StandardOpenOption.READ);
		// 根据文件夹，和传入的前缀或者后缀获取一个文件夹路径下的文件，文件夹迭代
		DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get("d:/temp"), "*.txt");
		// 在path下根据传入的lambda表达式查找符合的文件
		DirectoryStream<Path> newDirectoryStream2 = Files.newDirectoryStream(Paths.get("d:/temp"), (path) -> {
			return false;
		});
	}

	private static void listFile() throws IOException {
		// 转换为流的形式，处理文件夹中的path，不会递归
		Stream<Path> list = Files.list(Paths.get("d:/temp"));
		list.forEach((path) -> {
			// System.out.println(path.getFileName());
		});

	}

	@SuppressWarnings("unused")
	private static void exists() throws IOException {
		boolean exists = Files.exists(Paths.get("d:/temp/abc.t"), LinkOption.NOFOLLOW_LINKS);

		// 从指定路径开始查找 ， Paths.get("d:/temp"),
		// 最大查找深度是指定的5层，超过5层的文件夹，忽略
		// lambda表达式是核心的文件匹配方式
		// 最后的Option就是连接是否跟随过去查看实际的文件
		Files.find(Paths.get("d:/temp"), 5, (path, basicFileAttributes) -> {
			// 最简单的实现，文件名相同就返回真，否则返回假
			Path fileName = path.getFileName();
			return fileName.equals("findFile") ? true : false;
		}, FileVisitOption.FOLLOW_LINKS);
	}

	@SuppressWarnings("unused")
	private static void delete() throws IOException {
		// 删除指定路径下的文件
		Files.delete(Paths.get("d:/temp/link.l"));// 文件不存在抛出异常
		boolean deleteIfExists = Files.deleteIfExists(Paths.get("d:/temp/link.txt"));// 文件不存在返回false

	}

	@SuppressWarnings("unused")
	private static void createFile() throws IOException {
		// 创建文件，DOS系统下，FileAttribute可以省略，Lunix系统下，需要写权限
		/*
		 * Path createFile =
		 * Files.createFile(Paths.get("d:/temp/abc.ttt"));//文件存在就不能再创建了，
		 * java.nio.file.FileAlreadyExistsException: File file =
		 * createFile.toFile();
		 */
		// 创建一个连接指向一个文件，连接和文件的改动都会影响到实际的文件，不能重复中创建已经存在的连接
		// Path createLink = Files.createLink(Paths.get("d:/temp/link.txt"),
		// Paths.get("d:/temp/abc.txt"));
		// 客户端没有所需的特权。
		// Path createSymbolicLink =
		// Files.createSymbolicLink(Paths.get("d:/temp/simple.link"),
		// Paths.get("d:/temp/abc.txt"));
		// 在系统临时文件夹中创建临时文件C:\Users\cobbl\AppData\Local\Temp
		// 文件在JVM退出时删除，但是没有哦实现的。 会根据文件的前缀 +系统添加的数字 + 后缀，组成文件名
		Path createTempFile = Files.createTempFile("abc", ".txt");
		// 在指定路径下，创建临时文件
		Path createTempFile2 = Files.createTempFile(Paths.get("d:/temp"), "ggggggg", ".txt");
	}

	@SuppressWarnings("unused")
	private static void createDirectory() throws IOException {
		// Lunix相关的属性，许可，Windos下不可用
		/*
		 * Set<PosixFilePermission> pfp =
		 * PosixFilePermissions.fromString("rwxr--r--");
		 * HashSet<PosixFilePermission> ps = new HashSet<PosixFilePermission>();
		 * ps.add(PosixFilePermission.OWNER_READ);
		 * ps.add(PosixFilePermission.OWNER_WRITE);
		 * ps.add(PosixFilePermission.GROUP_READ);
		 * ps.add(PosixFilePermission.OTHERS_READ);
		 * FileAttribute<Set<PosixFilePermission>> asFileAttribute =
		 * PosixFilePermissions.asFileAttribute(ps);
		 */

		// DOS系统下使用下面的就可以创建，LUNIX下需要上面的属性添加权限
		// 创建目录，只能创建一层目录，父目录需要存在，文件夹存在就不能创建了，抛出异常
		// Path createDirectories =
		// Files.createDirectory(Paths.get("d:/temp/aaa/bbb/ccc/ddd/aa"));
		// 父目录可以不存在，会自动创建
		// Path createDirectories2 =
		// Files.createDirectories(Paths.get("d:/temp/aaa/bbb/ccc/ddd/")); //
		// java.nio.file.NoSuchFileException

		// 创建临时文件，文件在jvm正常退出时时会删除，退出时文件没有删掉的。。。。。。。。。。。
		// 创建的临时文件除了指定的名称，还会添加后缀数字
		// 在系统的临时文件夹中创建临时文件C:\Users\cobbl\AppData\Local\Temp
		Path createTempDirectory = Files.createTempDirectory("ggggg");
		Path fileName = createTempDirectory.getFileName();
		// 在指定路径下创建临时文件
		Path createTempDirectory2 = Files.createTempDirectory(Paths.get("d:/temp/"), "ggggggg.temp");
		Path fileName2 = createTempDirectory2.getFileName();
	}

	@SuppressWarnings("unused")
	private static void readBasicAttribute() throws IOException {
		// 基础的属性
		BasicFileAttributes readAttributes = Files.readAttributes(Paths.get("d:/temp/abc.t"), BasicFileAttributes.class,
				LinkOption.NOFOLLOW_LINKS);
		// 文件的创建时间
		FileTime creationTime = readAttributes.creationTime();// 2017-10-20T10:16:53.759654Z
		// 是否是文件夹
		boolean directory = readAttributes.isDirectory();// false
		// 是否是其他用户
		boolean other = readAttributes.isOther();// false
		// 是否是常规文件
		boolean regularFile = readAttributes.isRegularFile();// true
		// 是否是超链接
		boolean symbolicLink = readAttributes.isSymbolicLink();// false
		// 上一次修改访问
		FileTime lastAccessTime = readAttributes.lastAccessTime();// 2017-10-20T10:16:53.759654Z
		// 上一次修改时间
		FileTime lastModifiedTime = readAttributes.lastModifiedTime();// 2017-10-28T07:10:04.434539Z
		// 返回唯一标识给定文件的对象，如果文件密钥不可用，则返回null
		Object fileKey = readAttributes.fileKey();// null
		long size = readAttributes.size();// 75
		String string = readAttributes.toString();

		// Dos系统下的属性。就是Windows下
		DosFileAttributes readAttributes2 = Files.readAttributes(Paths.get("d:/temp/abc.t"), DosFileAttributes.class,
				LinkOption.NOFOLLOW_LINKS);
		FileTime creationTime2 = readAttributes2.creationTime();// 2017-10-20T10:16:53.759654Z
		// 返回归档属性值
		boolean archive = readAttributes2.isArchive();// true
		// 是否是隐藏的
		boolean hidden = readAttributes2.isHidden();// false
		// 是否是只读
		boolean readOnly = readAttributes2.isReadOnly();// false
		// 返回系统属性的值，通常是系统组件才有
		boolean system = readAttributes2.isSystem();// false
		// 下面的是basic继承的属性值
		// 是否是文件夹
		boolean directory2 = readAttributes2.isDirectory();// false
		// 是否是常规文件
		boolean regularFile2 = readAttributes2.isRegularFile();// true
		// 是否是连接文件
		boolean symbolicLink2 = readAttributes2.isSymbolicLink();// false
		// 上一次访问时间
		FileTime lastAccessTime2 = readAttributes2.lastAccessTime();// 2017-10-20T10:16:53.759654Z
		// 上一次修改时间
		FileTime lastModifiedTime2 = readAttributes2.lastModifiedTime();// 2017-10-28T07:10:04.434539Z
		// 文件大小
		long size2 = readAttributes2.size();// 75
		// 返回唯一标识给定文件的对象，如果文件密钥不可用，则返回null
		Object fileKey2 = readAttributes2.fileKey();// null
		// 文件的创建时间
		FileTime creationTime3 = readAttributes2.creationTime();// 2017-10-20T10:16:53.759654Z

		// Lunix 就是Unix系统的属性
		/*
		 * PosixFileAttributes readAttributes3 =
		 * Files.readAttributes(Paths.get("s:/temp/abc.t"),
		 * PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS); FileTime
		 * creationTime3 = readAttributes3.creationTime(); Object fileKey2 =
		 * readAttributes3.fileKey(); GroupPrincipal group =
		 * readAttributes3.group(); boolean directory3 =
		 * readAttributes3.isDirectory(); boolean other2 =
		 * readAttributes3.isOther(); boolean regularFile3 =
		 * readAttributes3.isRegularFile(); boolean symbolicLink3 =
		 * readAttributes3.isSymbolicLink(); FileTime lastAccessTime3 =
		 * readAttributes3.lastAccessTime(); FileTime lastModifiedTime3 =
		 * readAttributes3.lastModifiedTime(); UserPrincipal owner =
		 * readAttributes3.owner(); Set<PosixFilePermission> permissions =
		 * readAttributes3.permissions(); long size3 = readAttributes3.size();
		 * String string2 = readAttributes3.toString();
		 */

	}

	@SuppressWarnings("unused")
	private static void fileAttitude() throws IOException {
		// 文件的属性
		// 获取指定路径的文件大小
		long size = Files.size(Paths.get("d:/temp/abc.t"));// 文件可以 大小75
		long size2 = Files.size(Paths.get("d:/temp"));// 文件夹也可以 大小4096
		// 指定路径是否是文件夹
		boolean directory = Files.isDirectory(Paths.get("d:/temp/abc.t"), LinkOption.NOFOLLOW_LINKS);// false
		// 是否是可执行文件 
		boolean executable = Files.isExecutable(Paths.get("d:/temp/abc.t"));// true
		// 是否隐藏
		boolean hidden = Files.isHidden(Paths.get("d:/temp/abc.t"));// false
		// 是否只读
		boolean readable = Files.isReadable(Paths.get("d:/temp/abc.t"));// true
		// 是否是连接
		boolean symbolicLink = Files.isSymbolicLink(Paths.get("d:/temp/abc.t"));// false
		// 是否可写
		boolean writable = Files.isWritable(Paths.get("d:/temp/abc.t"));// true
		// 路径下,两个文件是否相同
		boolean sameFile = Files.isSameFile(Paths.get("d:/temp/abc.t"), Paths.get("d:/temp/abc.t"));// true
		// 获取指定名字的属性
		Object attribute = Files.getAttribute(Paths.get("d:/temp/abc.t"), "dos:archive", LinkOption.NOFOLLOW_LINKS);// true
		Object attribute2 = Files.getAttribute(Paths.get("d:/temp/abc.t"), "basic:size", LinkOption.NOFOLLOW_LINKS);// Long75
		Object attribute3 = Files.getAttribute(Paths.get("d:/temp/abc.t"), "dos:hidden", LinkOption.NOFOLLOW_LINKS);// false

		// 获取路径下文件的指定属性集,这里使用通配符,获取了全部的属性.可以使用 doc:指定属性,获取它的值
		Map<String, Object> map = Files.readAttributes(Paths.get("d:/temp/abc.t"), "dos:*");// {readonly=false}
		// {lastAccessTime=2017-10-20T10:16:53.759654Z,
		// lastModifiedTime=2017-10-29T06:23:04.898Z,
		// creationTime=2017-10-20T10:16:53.759654Z, hidden=false,
		// isRegularFile=true, fileKey=null, archive=true, system=false,
		// size=75, readonly=false, isSymbolicLink=false, attributes=32,
		// isOther=false, isDirectory=false}
		// 可以通过 , 的方式隔开 来获取. 除了dos 只要系统支持还可以使用 unix:ino posix:*
		Map<String, Object> map2 = Files.readAttributes(Paths.get("d:/temp/abc.t"), "dos:creationTime,hidden");// {creationTime=2017-10-20T10:16:53.759654Z,
																												// hidden=false}
		// Dos下不支持
		// Map<String, Object> map3 =
		// Files.readAttributes(Paths.get("d:/temp/abc.t"),
		// "posix:*");//{creationTime=2017-10-20T10:16:53.759654Z, hidden=false}

		// 获取属性视图 获取文件访问控制视图
		AclFileAttributeView fileAttributeView = Files.getFileAttributeView(Paths.get("d:/temp/abc.t"),
				AclFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);// sun.nio.fs.WindowsAclFileAttributeView
		viewAcl(fileAttributeView);
		// 获取进出的文件属性视图
		BasicFileAttributeView fileAttributeView2 = Files.getFileAttributeView(Paths.get("d:/temp/abc.t"),
				BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);// sun.nio.fs.WindowsFileAttributeViews$Basic
		viewBasic(fileAttributeView2);
		// 获取dos环境下的文件属性视图
		DosFileAttributeView fileAttributeView3 = Files.getFileAttributeView(Paths.get("d:/temp/abc.t"),
				DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);// sun.nio.fs.WindowsFileAttributeViews$Dos
		viewDos(fileAttributeView3);
		// 获取lunix下文件属性视图
		PosixFileAttributeView fileAttributeView4 = Files.getFileAttributeView(Paths.get("d:/temp/abc.t"),
				PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);// null
		// 获取用户自定义的文件属性视图
		UserDefinedFileAttributeView fileAttributeView5 = Files.getFileAttributeView(Paths.get("d:/temp/abc.t"),
				UserDefinedFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);// WindowsUserDefinedFileAttributeView
		viewUserDF(fileAttributeView5);
		// 获取文件的最后修改时间
		FileTime lastModifiedTime = Files.getLastModifiedTime(Paths.get("D:/temp/abc.t"), LinkOption.NOFOLLOW_LINKS);// 2017-10-28T07:10:04.434539Z
		// 获取文件的拥有者
		UserPrincipal owner = Files.getOwner(Paths.get("d:/temp/abc.t"), LinkOption.NOFOLLOW_LINKS);// DESKTOP-0MCNN4Q\cobbl
																									// (User)

		// DOS系统不支持
		// Set<PosixFilePermission> posixFilePermissions =
		// Files.getPosixFilePermissions(Paths.get("d:/temp/abc.t"),LinkOption.NOFOLLOW_LINKS);//
		// java.lang.UnsupportedOperationException
		// 文件是否不存在
		boolean notExists = Files.notExists(Paths.get("d:/temp/abc.t"), LinkOption.NOFOLLOW_LINKS);// false

		// 探测文件的内容类型。
		String probeContentType = Files.probeContentType(Paths.get("d:/temp/abc.t"));// null

		// 设置文件的具体属性的具体指
		Path setAttribute = Files.setAttribute(Paths.get("d:/temp/abc.t"), "dos:readonly", false,
				LinkOption.NOFOLLOW_LINKS);
		// 设置上一次修改时间
		Path setLastModifiedTime = Files.setLastModifiedTime(Paths.get("d:/temp/abc.t"), FileTime.from(Instant.now()));

		// 将文件的所有者设置为指定的用户,需要超级管理员权限,我的账户没有权限,没有办法更改.
		// Path setOwner = Files.setOwner(Paths.get("d:/temp/abc.t"),owner );
		// Lunix下才能用，Windows下的文件系统不支持，，，拥有者，Group,Public三种角色的权限，读，写，执行
		// Set<PosixFilePermission> posixFilePermissions =
		// PosixFilePermissions.fromString("rwxr--r--");
		/*
		 * Dos下不支持 Path setPosixFilePermissions =
		 * Files.setPosixFilePermissions(Paths.get("d:/temp/abc.t"),
		 * posixFilePermissions);
		 */

	}

	@SuppressWarnings("unused")
	private static void viewUserDF(UserDefinedFileAttributeView fileAttributeView5) throws IOException {
		// 用户自定义的文件属性
		String name = fileAttributeView5.name();// user
		List<String> list = fileAttributeView5.list();// []
		for (String string : list) {

		}
		String string = fileAttributeView5.toString();
	}

	@SuppressWarnings("unused")
	private static void viewDos(DosFileAttributeView fileAttributeView3) throws IOException {
		// 返回视图的名字
		String name = fileAttributeView3.name();// dos
		// 返回视图的属性
		DosFileAttributes readAttributes = fileAttributeView3.readAttributes();// WindowsFileAttributes
		// 文件创建时间
		FileTime creationTime = readAttributes.creationTime();// 2017-10-29T07:13:02.665Z
		// 文件的key
		Object fileKey = readAttributes.fileKey();// null
		// 文件是否archive
		boolean archive = readAttributes.isArchive();// true
		// 文件是否文件夹
		boolean directory = readAttributes.isDirectory();// false
		// 文件是否隐藏
		boolean hidden = readAttributes.isHidden();// false
		// 文件是否是其他用户
		boolean other = readAttributes.isOther();// false
		// 文件是否只读
		boolean readOnly = readAttributes.isReadOnly();// false
		// 文件是否是常规文件
		boolean regularFile = readAttributes.isRegularFile();// true
		// 文件是否是连接
		boolean symbolicLink = readAttributes.isSymbolicLink();// false
		// 文件是否是系统组件
		boolean system = readAttributes.isSystem();// false
		// 文件的上一次访问时间
		FileTime lastAccessTime = readAttributes.lastAccessTime();// 2017-10-29T07:13:02.665Z
		// 文件的上一次修改时间
		FileTime lastModifiedTime = readAttributes.lastModifiedTime();// 2017-10-29T07:13:02.665Z
		// 文件的大小
		long size = readAttributes.size();// 75
		// 将文件的属性设置为
		fileAttributeView3.setReadOnly(false);
		fileAttributeView3.setHidden(false);
		fileAttributeView3.setArchive(true);
		fileAttributeView3.setSystem(false);
		String string = readAttributes.toString();
	}

	@SuppressWarnings("unused")
	private static void viewBasic(BasicFileAttributeView fileAttributeView2) throws IOException {
		// 视图的名字
		String name = fileAttributeView2.name();// basic
		// 视图的属性
		BasicFileAttributes readAttributes = fileAttributeView2.readAttributes();// WindowsFileAttributes
		// 文件的创建时间
		FileTime creationTime = readAttributes.creationTime();// 2017-10-20T10:16:53.759654Z
		// 文件的key
		Object fileKey = readAttributes.fileKey();// null
		// 文件是否是文件夹
		boolean directory = readAttributes.isDirectory();// false
		// 文件是否是常规文件
		boolean regularFile = readAttributes.isRegularFile();// true
		// 是否是其他拥有者
		boolean other = readAttributes.isOther();// false
		// 是否是连接
		boolean symbolicLink = readAttributes.isSymbolicLink();// false
		// 最后一次访问时间
		FileTime lastAccessTime = readAttributes.lastAccessTime();// 2017-10-20T10:16:53.759654Z
		// 最后一次修改时间
		FileTime lastModifiedTime = readAttributes.lastModifiedTime();// 2017-10-29T06:40:46.083Z
		// 文件的大小
		long size = readAttributes.size();// 75
		// 设置文件的创建时间,修改时间,访问时间
		fileAttributeView2.setTimes(FileTime.from(Instant.now()), FileTime.from(Instant.now()),
				FileTime.from(Instant.now()));
		String string = readAttributes.toString();
	}

	@SuppressWarnings("unused")
	private static void viewAcl(AclFileAttributeView fileAttributeView) throws IOException {
		List<AclEntry> acl = fileAttributeView.getAcl();
		for (AclEntry aclEntry : acl) {
			Set<AclEntryFlag> flags = aclEntry.flags();// []
			Set<AclEntryPermission> permissions = aclEntry.permissions();
			// [WRITE_OWNER, READ_ACL, APPEND_DATA, READ_DATA,
			// WRITE_NAMED_ATTRS, DELETE_CHILD, WRITE_ACL, WRITE_DATA, DELETE,
			// WRITE_ATTRIBUTES, READ_ATTRIBUTES, EXECUTE, READ_NAMED_ATTRS,
			// SYNCHRONIZE]
			UserPrincipal principal = aclEntry.principal();// BUILTIN\Administrators
															// (Alias)
			// NT AUTHORITY\SYSTEM (Well-known group)
			// NT AUTHORITY\Authenticated Users (Well-known group)
			// BUILTIN\Users (Alias)
			AclEntryType type = aclEntry.type();// ALLOW
			String string = aclEntry.toString();
		}
		UserPrincipal owner = fileAttributeView.getOwner();// DESKTOP-0MCNN4Q\cobbl
															// (User)
		String name = fileAttributeView.name();// acl
		// 抛出异常,没有权限
		// fileAttributeView.setOwner(owner);

		fileAttributeView.setAcl(acl);
	}

	@SuppressWarnings("unused")
	private static void copyFile() throws FileNotFoundException, IOException {
		// 将文件复制到另一个地方
		long copy = Files.copy(Paths.get("d:/temp/abc.t"), new FileOutputStream("d:/temp/copyPathtoOutputStream.txt"));

		long copy2 = Files.copy(new FileInputStream("d:/temp/abc.t"), Paths.get("d:/temp/copyInputStreamtoPath"),
				StandardCopyOption.REPLACE_EXISTING);

		Path copy3 = Files.copy(Paths.get("d:/temp/abc.t"), Paths.get("d:/temp/copyPathtoPath"),
				StandardCopyOption.REPLACE_EXISTING);

	}

}
