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
		// �����ļ�
		// copyFile();
		// �����ļ�����
//		 fileAttitude();
		// readBasicAttribute();
		// �����ļ���
//		 createDirectory();
		// �����ļ�������
		// createFile();
		// ɾ���ļ�
		// delete();
		// �ļ��Ƿ����
		// exists();
		// �г��ļ����е��ļ����ļ���
		// listFile();
		// ��ȡ�ļ�
		// readFile();
		// д���ļ�
		// writeFile();
		// �ļ��洢�豸
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
//		�����ļ�              �ļ�����Ŀ¼                                    ���ʵķ�ʽ                               �����ļ������
		try {
			Files.walkFileTree(Paths.get("d:/temp"), EnumSet.of(FileVisitOption.FOLLOW_LINKS), 50,
					new SimpleFileVisitor<Path>() { //visitor
				 @Override
				    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
				        throws IOException
				    {
//				 ���ڷ��ʵ��ļ���
				        Objects.requireNonNull(dir);
				        Objects.requireNonNull(attrs);
//			        					��������                     ��������Ŀ¼���ļ�       ��������ļ��е���Ŀ¼           ��ֹ
//			        FileVisitResult.CONTINUE;FileVisitResult.SKIP_SIBLINGS;FileVisitResult.SKIP_SUBTREE;FileVisitResult.TERMINATE
//			        ʹ��FileVisitResult�����Ʊ���������ļ��к�Ĳ���
				        return FileVisitResult.CONTINUE;
				    }
				 @Override
				    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				        throws IOException
				    {
//				 ������ÿһ���ļ�
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
//				�����ļ���������ʱ���ã�����Ȩ��
				        Objects.requireNonNull(file);
//				        throw exc;
				       return  FileVisitResult.CONTINUE;
				    }
				 
				 @Override
				    public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				        throws IOException
				    {
//				 �����ļ���֮������
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
		// �ļ��洢 FileStore��ʾ�洢�أ��豸���������������ļ�ϵͳ������ʵ���ļ��洢�ľ��巽ʽ��
		FileStore fileStore = Files.getFileStore(Paths.get("d:/temp"));
		
		long totalSpace = fileStore.getTotalSpace();// 287631732736
		long unallocatedSpace = fileStore.getUnallocatedSpace();// 50070798336
		long usableSpace = fileStore.getUsableSpace();// 50070798336
		
		boolean readOnly = fileStore.isReadOnly();// false
		String name = fileStore.name();// DATA
		String type = fileStore.type();// NTFS

		Object attribute = fileStore.getAttribute("totalSpace");// totalSpace,unallocatedSpace,usableSpace������������
		FileStoreAttributeView fileStoreAttributeView = fileStore
				.getFileStoreAttributeView(FileStoreAttributeView.class);// null

		boolean supportsFileAttributeView2 = fileStore.supportsFileAttributeView(DosFileAttributeView.class);// true
		boolean supportsFileAttributeView = fileStore.supportsFileAttributeView("basic");// true
//		������֧�ֵ�view������basic     posix   dos    acl     user
		
		String string = fileStore.toString();// DATA (d:)
	}

	@SuppressWarnings("unused")
	private static void writeFile() throws IOException {
		// ����path���ַ������ļ��򿪲�������ȡ�ַ������
		BufferedWriter newBufferedWriter = Files.newBufferedWriter(Paths.get("d:/temp/abc.txt"), Charset.forName("GBK"),
				StandardOpenOption.APPEND);
		newBufferedWriter.close();
		// ����path���ļ��򿪲�������ȡ�ֽ���
		OutputStream newOutputStream = Files.newOutputStream(Paths.get("D:/TEMP/ABC.TXT"), StandardOpenOption.APPEND);
		newOutputStream.close();
		// ���ֽ�����д���ļ�����ָ��д���ļ��Ĳ�����append���ļ�������д�����Ǵ��ļ���ͷ���ǣ����ǽ��ļ��ض���д
		Path write = Files.write(Paths.get("d:/temp/abc.txt"), "just for test Files Write to File�����ǲ���һ��".getBytes(),
				StandardOpenOption.APPEND);
		// ��һ����Ե��������ݰ���ָ�����ַ���д���ļ��У���ָ��д���ļ�ʱ�����ļ��Ĳ���
		List<String> lines = Arrays.asList(new String[] { "��һ��", "�ڶ���", "������", "������" });
		Files.write(Paths.get("d:/temp/abc.txt"), lines, Charset.forName("GBK"), StandardOpenOption.APPEND);
	}

	@SuppressWarnings({ "unused", "resource" })
	private static void readFile() throws IOException {
		// ���ļ����������ݶ���������
		byte[] readAllBytes = Files.readAllBytes(Paths.get("d:/temp/bbb.txt"));
		// ���ļ����������ݣ����ձ�������Ϊ��λ����һ��List����
		List<String> readAllLines2 = Files.readAllLines(Paths.get("d:/temp/bbb.txt"), Charset.forName("GBK"));
		// ׼���ö�ȡ�ļ���·�������ַ���������һ������û�п�ʼ��ȡ�ļ�����Ҫ������ʼ�Ż��ȡ�ļ�
		Stream<String> lines2 = Files.lines(Paths.get("D:/temp/bbb.txt"), Charset.forName("GBK"));
		// ����path,�ļ��򿪷�ʽ������һ��-------�ֽ�������--------
		InputStream newInputStream = Files.newInputStream(Paths.get("d:/temp/bbb.txt"), StandardOpenOption.READ);
		newInputStream.close();
		// ����·��,�ַ�������һ��bufferReadder,�������-------�ַ�������---------
		BufferedReader newBufferedReader = Files.newBufferedReader(Paths.get("d:/temp/bbb.txt"),
				Charset.forName("GBK"));
		newBufferedReader.close();
		// ����·�����ļ���ѡ���һ��---------------ByteChannel-�ܵ�-------------
		SeekableByteChannel newByteChannel = Files.newByteChannel(Paths.get("d:/temp/bbb.txt"),
				StandardOpenOption.READ);
		// �����ļ��У��ʹ����ǰ׺���ߺ�׺��ȡһ���ļ���·���µ��ļ����ļ��е���
		DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get("d:/temp"), "*.txt");
		// ��path�¸��ݴ����lambda���ʽ���ҷ��ϵ��ļ�
		DirectoryStream<Path> newDirectoryStream2 = Files.newDirectoryStream(Paths.get("d:/temp"), (path) -> {
			return false;
		});
	}

	private static void listFile() throws IOException {
		// ת��Ϊ������ʽ�������ļ����е�path������ݹ�
		Stream<Path> list = Files.list(Paths.get("d:/temp"));
		list.forEach((path) -> {
			// System.out.println(path.getFileName());
		});

	}

	@SuppressWarnings("unused")
	private static void exists() throws IOException {
		boolean exists = Files.exists(Paths.get("d:/temp/abc.t"), LinkOption.NOFOLLOW_LINKS);

		// ��ָ��·����ʼ���� �� Paths.get("d:/temp"),
		// �����������ָ����5�㣬����5����ļ��У�����
		// lambda���ʽ�Ǻ��ĵ��ļ�ƥ�䷽ʽ
		// ����Option���������Ƿ�����ȥ�鿴ʵ�ʵ��ļ�
		Files.find(Paths.get("d:/temp"), 5, (path, basicFileAttributes) -> {
			// ��򵥵�ʵ�֣��ļ�����ͬ�ͷ����棬���򷵻ؼ�
			Path fileName = path.getFileName();
			return fileName.equals("findFile") ? true : false;
		}, FileVisitOption.FOLLOW_LINKS);
	}

	@SuppressWarnings("unused")
	private static void delete() throws IOException {
		// ɾ��ָ��·���µ��ļ�
		Files.delete(Paths.get("d:/temp/link.l"));// �ļ��������׳��쳣
		boolean deleteIfExists = Files.deleteIfExists(Paths.get("d:/temp/link.txt"));// �ļ������ڷ���false

	}

	@SuppressWarnings("unused")
	private static void createFile() throws IOException {
		// �����ļ���DOSϵͳ�£�FileAttribute����ʡ�ԣ�Lunixϵͳ�£���ҪдȨ��
		/*
		 * Path createFile =
		 * Files.createFile(Paths.get("d:/temp/abc.ttt"));//�ļ����ھͲ����ٴ����ˣ�
		 * java.nio.file.FileAlreadyExistsException: File file =
		 * createFile.toFile();
		 */
		// ����һ������ָ��һ���ļ������Ӻ��ļ��ĸĶ�����Ӱ�쵽ʵ�ʵ��ļ��������ظ��д����Ѿ����ڵ�����
		// Path createLink = Files.createLink(Paths.get("d:/temp/link.txt"),
		// Paths.get("d:/temp/abc.txt"));
		// �ͻ���û���������Ȩ��
		// Path createSymbolicLink =
		// Files.createSymbolicLink(Paths.get("d:/temp/simple.link"),
		// Paths.get("d:/temp/abc.txt"));
		// ��ϵͳ��ʱ�ļ����д�����ʱ�ļ�C:\Users\cobbl\AppData\Local\Temp
		// �ļ���JVM�˳�ʱɾ��������û��Ŷʵ�ֵġ� ������ļ���ǰ׺ +ϵͳ��ӵ����� + ��׺������ļ���
		Path createTempFile = Files.createTempFile("abc", ".txt");
		// ��ָ��·���£�������ʱ�ļ�
		Path createTempFile2 = Files.createTempFile(Paths.get("d:/temp"), "ggggggg", ".txt");
	}

	@SuppressWarnings("unused")
	private static void createDirectory() throws IOException {
		// Lunix��ص����ԣ���ɣ�Windos�²�����
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

		// DOSϵͳ��ʹ������ľͿ��Դ�����LUNIX����Ҫ������������Ȩ��
		// ����Ŀ¼��ֻ�ܴ���һ��Ŀ¼����Ŀ¼��Ҫ���ڣ��ļ��д��ھͲ��ܴ����ˣ��׳��쳣
		// Path createDirectories =
		// Files.createDirectory(Paths.get("d:/temp/aaa/bbb/ccc/ddd/aa"));
		// ��Ŀ¼���Բ����ڣ����Զ�����
		// Path createDirectories2 =
		// Files.createDirectories(Paths.get("d:/temp/aaa/bbb/ccc/ddd/")); //
		// java.nio.file.NoSuchFileException

		// ������ʱ�ļ����ļ���jvm�����˳�ʱʱ��ɾ�����˳�ʱ�ļ�û��ɾ���ġ���������������������
		// ��������ʱ�ļ�����ָ�������ƣ�������Ӻ�׺����
		// ��ϵͳ����ʱ�ļ����д�����ʱ�ļ�C:\Users\cobbl\AppData\Local\Temp
		Path createTempDirectory = Files.createTempDirectory("ggggg");
		Path fileName = createTempDirectory.getFileName();
		// ��ָ��·���´�����ʱ�ļ�
		Path createTempDirectory2 = Files.createTempDirectory(Paths.get("d:/temp/"), "ggggggg.temp");
		Path fileName2 = createTempDirectory2.getFileName();
	}

	@SuppressWarnings("unused")
	private static void readBasicAttribute() throws IOException {
		// ����������
		BasicFileAttributes readAttributes = Files.readAttributes(Paths.get("d:/temp/abc.t"), BasicFileAttributes.class,
				LinkOption.NOFOLLOW_LINKS);
		// �ļ��Ĵ���ʱ��
		FileTime creationTime = readAttributes.creationTime();// 2017-10-20T10:16:53.759654Z
		// �Ƿ����ļ���
		boolean directory = readAttributes.isDirectory();// false
		// �Ƿ��������û�
		boolean other = readAttributes.isOther();// false
		// �Ƿ��ǳ����ļ�
		boolean regularFile = readAttributes.isRegularFile();// true
		// �Ƿ��ǳ�����
		boolean symbolicLink = readAttributes.isSymbolicLink();// false
		// ��һ���޸ķ���
		FileTime lastAccessTime = readAttributes.lastAccessTime();// 2017-10-20T10:16:53.759654Z
		// ��һ���޸�ʱ��
		FileTime lastModifiedTime = readAttributes.lastModifiedTime();// 2017-10-28T07:10:04.434539Z
		// ����Ψһ��ʶ�����ļ��Ķ�������ļ���Կ�����ã��򷵻�null
		Object fileKey = readAttributes.fileKey();// null
		long size = readAttributes.size();// 75
		String string = readAttributes.toString();

		// Dosϵͳ�µ����ԡ�����Windows��
		DosFileAttributes readAttributes2 = Files.readAttributes(Paths.get("d:/temp/abc.t"), DosFileAttributes.class,
				LinkOption.NOFOLLOW_LINKS);
		FileTime creationTime2 = readAttributes2.creationTime();// 2017-10-20T10:16:53.759654Z
		// ���ع鵵����ֵ
		boolean archive = readAttributes2.isArchive();// true
		// �Ƿ������ص�
		boolean hidden = readAttributes2.isHidden();// false
		// �Ƿ���ֻ��
		boolean readOnly = readAttributes2.isReadOnly();// false
		// ����ϵͳ���Ե�ֵ��ͨ����ϵͳ�������
		boolean system = readAttributes2.isSystem();// false
		// �������basic�̳е�����ֵ
		// �Ƿ����ļ���
		boolean directory2 = readAttributes2.isDirectory();// false
		// �Ƿ��ǳ����ļ�
		boolean regularFile2 = readAttributes2.isRegularFile();// true
		// �Ƿ��������ļ�
		boolean symbolicLink2 = readAttributes2.isSymbolicLink();// false
		// ��һ�η���ʱ��
		FileTime lastAccessTime2 = readAttributes2.lastAccessTime();// 2017-10-20T10:16:53.759654Z
		// ��һ���޸�ʱ��
		FileTime lastModifiedTime2 = readAttributes2.lastModifiedTime();// 2017-10-28T07:10:04.434539Z
		// �ļ���С
		long size2 = readAttributes2.size();// 75
		// ����Ψһ��ʶ�����ļ��Ķ�������ļ���Կ�����ã��򷵻�null
		Object fileKey2 = readAttributes2.fileKey();// null
		// �ļ��Ĵ���ʱ��
		FileTime creationTime3 = readAttributes2.creationTime();// 2017-10-20T10:16:53.759654Z

		// Lunix ����Unixϵͳ������
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
		// �ļ�������
		// ��ȡָ��·�����ļ���С
		long size = Files.size(Paths.get("d:/temp/abc.t"));// �ļ����� ��С75
		long size2 = Files.size(Paths.get("d:/temp"));// �ļ���Ҳ���� ��С4096
		// ָ��·���Ƿ����ļ���
		boolean directory = Files.isDirectory(Paths.get("d:/temp/abc.t"), LinkOption.NOFOLLOW_LINKS);// false
		// �Ƿ��ǿ�ִ���ļ� 
		boolean executable = Files.isExecutable(Paths.get("d:/temp/abc.t"));// true
		// �Ƿ�����
		boolean hidden = Files.isHidden(Paths.get("d:/temp/abc.t"));// false
		// �Ƿ�ֻ��
		boolean readable = Files.isReadable(Paths.get("d:/temp/abc.t"));// true
		// �Ƿ�������
		boolean symbolicLink = Files.isSymbolicLink(Paths.get("d:/temp/abc.t"));// false
		// �Ƿ��д
		boolean writable = Files.isWritable(Paths.get("d:/temp/abc.t"));// true
		// ·����,�����ļ��Ƿ���ͬ
		boolean sameFile = Files.isSameFile(Paths.get("d:/temp/abc.t"), Paths.get("d:/temp/abc.t"));// true
		// ��ȡָ�����ֵ�����
		Object attribute = Files.getAttribute(Paths.get("d:/temp/abc.t"), "dos:archive", LinkOption.NOFOLLOW_LINKS);// true
		Object attribute2 = Files.getAttribute(Paths.get("d:/temp/abc.t"), "basic:size", LinkOption.NOFOLLOW_LINKS);// Long75
		Object attribute3 = Files.getAttribute(Paths.get("d:/temp/abc.t"), "dos:hidden", LinkOption.NOFOLLOW_LINKS);// false

		// ��ȡ·�����ļ���ָ�����Լ�,����ʹ��ͨ���,��ȡ��ȫ��������.����ʹ�� doc:ָ������,��ȡ����ֵ
		Map<String, Object> map = Files.readAttributes(Paths.get("d:/temp/abc.t"), "dos:*");// {readonly=false}
		// {lastAccessTime=2017-10-20T10:16:53.759654Z,
		// lastModifiedTime=2017-10-29T06:23:04.898Z,
		// creationTime=2017-10-20T10:16:53.759654Z, hidden=false,
		// isRegularFile=true, fileKey=null, archive=true, system=false,
		// size=75, readonly=false, isSymbolicLink=false, attributes=32,
		// isOther=false, isDirectory=false}
		// ����ͨ�� , �ķ�ʽ���� ����ȡ. ����dos ֻҪϵͳ֧�ֻ�����ʹ�� unix:ino posix:*
		Map<String, Object> map2 = Files.readAttributes(Paths.get("d:/temp/abc.t"), "dos:creationTime,hidden");// {creationTime=2017-10-20T10:16:53.759654Z,
																												// hidden=false}
		// Dos�²�֧��
		// Map<String, Object> map3 =
		// Files.readAttributes(Paths.get("d:/temp/abc.t"),
		// "posix:*");//{creationTime=2017-10-20T10:16:53.759654Z, hidden=false}

		// ��ȡ������ͼ ��ȡ�ļ����ʿ�����ͼ
		AclFileAttributeView fileAttributeView = Files.getFileAttributeView(Paths.get("d:/temp/abc.t"),
				AclFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);// sun.nio.fs.WindowsAclFileAttributeView
		viewAcl(fileAttributeView);
		// ��ȡ�������ļ�������ͼ
		BasicFileAttributeView fileAttributeView2 = Files.getFileAttributeView(Paths.get("d:/temp/abc.t"),
				BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);// sun.nio.fs.WindowsFileAttributeViews$Basic
		viewBasic(fileAttributeView2);
		// ��ȡdos�����µ��ļ�������ͼ
		DosFileAttributeView fileAttributeView3 = Files.getFileAttributeView(Paths.get("d:/temp/abc.t"),
				DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);// sun.nio.fs.WindowsFileAttributeViews$Dos
		viewDos(fileAttributeView3);
		// ��ȡlunix���ļ�������ͼ
		PosixFileAttributeView fileAttributeView4 = Files.getFileAttributeView(Paths.get("d:/temp/abc.t"),
				PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);// null
		// ��ȡ�û��Զ�����ļ�������ͼ
		UserDefinedFileAttributeView fileAttributeView5 = Files.getFileAttributeView(Paths.get("d:/temp/abc.t"),
				UserDefinedFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);// WindowsUserDefinedFileAttributeView
		viewUserDF(fileAttributeView5);
		// ��ȡ�ļ�������޸�ʱ��
		FileTime lastModifiedTime = Files.getLastModifiedTime(Paths.get("D:/temp/abc.t"), LinkOption.NOFOLLOW_LINKS);// 2017-10-28T07:10:04.434539Z
		// ��ȡ�ļ���ӵ����
		UserPrincipal owner = Files.getOwner(Paths.get("d:/temp/abc.t"), LinkOption.NOFOLLOW_LINKS);// DESKTOP-0MCNN4Q\cobbl
																									// (User)

		// DOSϵͳ��֧��
		// Set<PosixFilePermission> posixFilePermissions =
		// Files.getPosixFilePermissions(Paths.get("d:/temp/abc.t"),LinkOption.NOFOLLOW_LINKS);//
		// java.lang.UnsupportedOperationException
		// �ļ��Ƿ񲻴���
		boolean notExists = Files.notExists(Paths.get("d:/temp/abc.t"), LinkOption.NOFOLLOW_LINKS);// false

		// ̽���ļ����������͡�
		String probeContentType = Files.probeContentType(Paths.get("d:/temp/abc.t"));// null

		// �����ļ��ľ������Եľ���ָ
		Path setAttribute = Files.setAttribute(Paths.get("d:/temp/abc.t"), "dos:readonly", false,
				LinkOption.NOFOLLOW_LINKS);
		// ������һ���޸�ʱ��
		Path setLastModifiedTime = Files.setLastModifiedTime(Paths.get("d:/temp/abc.t"), FileTime.from(Instant.now()));

		// ���ļ�������������Ϊָ�����û�,��Ҫ��������ԱȨ��,�ҵ��˻�û��Ȩ��,û�а취����.
		// Path setOwner = Files.setOwner(Paths.get("d:/temp/abc.t"),owner );
		// Lunix�²����ã�Windows�µ��ļ�ϵͳ��֧�֣�����ӵ���ߣ�Group,Public���ֽ�ɫ��Ȩ�ޣ�����д��ִ��
		// Set<PosixFilePermission> posixFilePermissions =
		// PosixFilePermissions.fromString("rwxr--r--");
		/*
		 * Dos�²�֧�� Path setPosixFilePermissions =
		 * Files.setPosixFilePermissions(Paths.get("d:/temp/abc.t"),
		 * posixFilePermissions);
		 */

	}

	@SuppressWarnings("unused")
	private static void viewUserDF(UserDefinedFileAttributeView fileAttributeView5) throws IOException {
		// �û��Զ�����ļ�����
		String name = fileAttributeView5.name();// user
		List<String> list = fileAttributeView5.list();// []
		for (String string : list) {

		}
		String string = fileAttributeView5.toString();
	}

	@SuppressWarnings("unused")
	private static void viewDos(DosFileAttributeView fileAttributeView3) throws IOException {
		// ������ͼ������
		String name = fileAttributeView3.name();// dos
		// ������ͼ������
		DosFileAttributes readAttributes = fileAttributeView3.readAttributes();// WindowsFileAttributes
		// �ļ�����ʱ��
		FileTime creationTime = readAttributes.creationTime();// 2017-10-29T07:13:02.665Z
		// �ļ���key
		Object fileKey = readAttributes.fileKey();// null
		// �ļ��Ƿ�archive
		boolean archive = readAttributes.isArchive();// true
		// �ļ��Ƿ��ļ���
		boolean directory = readAttributes.isDirectory();// false
		// �ļ��Ƿ�����
		boolean hidden = readAttributes.isHidden();// false
		// �ļ��Ƿ��������û�
		boolean other = readAttributes.isOther();// false
		// �ļ��Ƿ�ֻ��
		boolean readOnly = readAttributes.isReadOnly();// false
		// �ļ��Ƿ��ǳ����ļ�
		boolean regularFile = readAttributes.isRegularFile();// true
		// �ļ��Ƿ�������
		boolean symbolicLink = readAttributes.isSymbolicLink();// false
		// �ļ��Ƿ���ϵͳ���
		boolean system = readAttributes.isSystem();// false
		// �ļ�����һ�η���ʱ��
		FileTime lastAccessTime = readAttributes.lastAccessTime();// 2017-10-29T07:13:02.665Z
		// �ļ�����һ���޸�ʱ��
		FileTime lastModifiedTime = readAttributes.lastModifiedTime();// 2017-10-29T07:13:02.665Z
		// �ļ��Ĵ�С
		long size = readAttributes.size();// 75
		// ���ļ�����������Ϊ
		fileAttributeView3.setReadOnly(false);
		fileAttributeView3.setHidden(false);
		fileAttributeView3.setArchive(true);
		fileAttributeView3.setSystem(false);
		String string = readAttributes.toString();
	}

	@SuppressWarnings("unused")
	private static void viewBasic(BasicFileAttributeView fileAttributeView2) throws IOException {
		// ��ͼ������
		String name = fileAttributeView2.name();// basic
		// ��ͼ������
		BasicFileAttributes readAttributes = fileAttributeView2.readAttributes();// WindowsFileAttributes
		// �ļ��Ĵ���ʱ��
		FileTime creationTime = readAttributes.creationTime();// 2017-10-20T10:16:53.759654Z
		// �ļ���key
		Object fileKey = readAttributes.fileKey();// null
		// �ļ��Ƿ����ļ���
		boolean directory = readAttributes.isDirectory();// false
		// �ļ��Ƿ��ǳ����ļ�
		boolean regularFile = readAttributes.isRegularFile();// true
		// �Ƿ�������ӵ����
		boolean other = readAttributes.isOther();// false
		// �Ƿ�������
		boolean symbolicLink = readAttributes.isSymbolicLink();// false
		// ���һ�η���ʱ��
		FileTime lastAccessTime = readAttributes.lastAccessTime();// 2017-10-20T10:16:53.759654Z
		// ���һ���޸�ʱ��
		FileTime lastModifiedTime = readAttributes.lastModifiedTime();// 2017-10-29T06:40:46.083Z
		// �ļ��Ĵ�С
		long size = readAttributes.size();// 75
		// �����ļ��Ĵ���ʱ��,�޸�ʱ��,����ʱ��
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
		// �׳��쳣,û��Ȩ��
		// fileAttributeView.setOwner(owner);

		fileAttributeView.setAcl(acl);
	}

	@SuppressWarnings("unused")
	private static void copyFile() throws FileNotFoundException, IOException {
		// ���ļ����Ƶ���һ���ط�
		long copy = Files.copy(Paths.get("d:/temp/abc.t"), new FileOutputStream("d:/temp/copyPathtoOutputStream.txt"));

		long copy2 = Files.copy(new FileInputStream("d:/temp/abc.t"), Paths.get("d:/temp/copyInputStreamtoPath"),
				StandardCopyOption.REPLACE_EXISTING);

		Path copy3 = Files.copy(Paths.get("d:/temp/abc.t"), Paths.get("d:/temp/copyPathtoPath"),
				StandardCopyOption.REPLACE_EXISTING);

	}

}
