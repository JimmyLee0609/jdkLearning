package nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class FileSystemProviderTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, URISyntaxException {
		List<FileSystemProvider> installedProviders = FileSystemProvider.installedProviders();
		FileSystemProvider fileSystemProvider = installedProviders.get(0);// WindowsFileSystemProvider
		fileSystemProvider.checkAccess(Paths.get("d:/temp/"), AccessMode.READ);
		fileSystemProvider.copy(Paths.get("d:/temp/abc.txt"), Paths.get("d:/temp/ges.txt"),
				StandardCopyOption.REPLACE_EXISTING);
		// fileSystemProvider.createDirectory(Paths.get("d:/temp/adc"));//已经存在的目录不能再创建
		// fileSystemProvider.createLink(Paths.get("d:/temp/link.link"),
		// Paths.get("d:/temp/abc.txt"));//文件存在不能重复创建
		// fileSystemProvider.delete(Paths.get("d:/temp/link.link"));
		boolean deleteIfExists = fileSystemProvider.deleteIfExists(Paths.get("d:/temp/ggg.f"));
		FileStore fileStore = fileSystemProvider
				.getFileStore(Paths.get("D:\\WEBtools\\apache-tomcat-9.0.0.M15-windows-x64.zip"));

		/*
		 * URI create = URI.create("file:///D:/WEBtools/");//有问题 FileSystem
		 * fileSystem = fileSystemProvider .getFileSystem(create);
		 */
		Map<String, String> env = new HashMap<String, String>();
		env.put("create", "true");
		// com.sun.nio.zipfs.ZipFileSystem
		fileSystemProvider.newFileSystem(new URI("jar:file:/D:/WEBtools/apache-tomcat-9.0.0.M15-windows-x64.zip"), 
				env);
		 
		Path path = fileSystemProvider.getPath(URI.create("file:///D:/TEMP/ABC.T"));
		String scheme = fileSystemProvider.getScheme();
		boolean hidden = fileSystemProvider.isHidden(Paths.get("d:/temp"));
		boolean sameFile = fileSystemProvider.isSameFile(Paths.get("d:/temp"), Paths.get("d:/temp"));
		// fileSystemProvider.move(Paths.get("d:/temp/abc.txt"),
		// Paths.get("d:/temp/abc"),
		// StandardCopyOption.REPLACE_EXISTING);
		AsynchronousFileChannel newAsynchronousFileChannel = fileSystemProvider.newAsynchronousFileChannel(
				Paths.get("d:/temp/abc.txt"), EnumSet.of(StandardOpenOption.READ), Executors.newCachedThreadPool());

		SeekableByteChannel newByteChannel = fileSystemProvider.newByteChannel(Paths.get("d:/temp/abc.txt"),
				EnumSet.of(StandardOpenOption.APPEND));

		DirectoryStream<Path> newDirectoryStream = fileSystemProvider.newDirectoryStream(Paths.get("d:/temp"),
				(directory) -> {

					return directory.endsWith(".java");
				});
		FileChannel newFileChannel = fileSystemProvider.newFileChannel(Paths.get("d:/temp/abc.txt"),
				EnumSet.of(StandardOpenOption.APPEND));
//		根据路径和配置的变量创建文件系统
		 HashMap<String, String> map3 = new HashMap<String, String>();
		 map3.put("create", "true");
		 FileSystem newFileSystem6 = fileSystemProvider.newFileSystem(Paths.get("d:/temp/bbc"), 
				 map3);
		 
		BasicFileAttributeView fileAttributeView = fileSystemProvider.getFileAttributeView(Paths.get("d:/temp"), BasicFileAttributeView.class,LinkOption.NOFOLLOW_LINKS);
		InputStream newInputStream = fileSystemProvider.newInputStream(Paths.get("d:/temp/abc.txt"),
				StandardOpenOption.READ);

		OutputStream newOutputStream = fileSystemProvider.newOutputStream(Paths.get("d:/temp/adc/bbb.t"),
				StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.SPARSE);// 文件不存在不会自己创建

		DosFileAttributes readAttributes2 = fileSystemProvider.readAttributes(Paths.get("d:/temp/temp.java"), DosFileAttributes.class,
				LinkOption.NOFOLLOW_LINKS);
		
		Map<String, Object> readAttributes = fileSystemProvider.readAttributes(Paths.get("d:/temp/abc.txt"), "dos:*",
				LinkOption.NOFOLLOW_LINKS);
		// 此文件或目录不是一个重分析点。我使用createLink的方式创建的文件
		// Path readSymbolicLink =
		// fileSystemProvider.readSymbolicLink(Paths.get("d:/temp/link.link"));

		fileSystemProvider.setAttribute(Paths.get("D:/TEMP/ABC.Txt"), "dos:readonly", false, LinkOption.NOFOLLOW_LINKS);
		FileSystemProvider fileSystemProvider2 = installedProviders.get(1);// ZipFileSystemProvider
	}

}
