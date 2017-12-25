package nio;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileStoreAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileSystemTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, URISyntaxException {
		// 获取系统默认的文件系统
		FileSystem defaultSys = FileSystems.getDefault();
		// 根据URI获取文件系统，只能是这样写，获取到的是sun.nio.fs.WindowsFileSystem
		FileSystem fileSystem = FileSystems.getFileSystem(new URI("file:///"));
		
		// 获取到的是com.sun.nio.zipfs.ZipFileSystem
		 FileSystem newFileSystem2 = FileSystems.newFileSystem(Paths.get("D:/WEBtools/apache-tomcat-9.0.0.M15-windows-x64.zip"),
		 								ClassLoader.getSystemClassLoader());
		
		Map<String, String> env = new HashMap<String, String>();
		env.put("create", "true");
		// com.sun.nio.zipfs.ZipFileSystem
		FileSystems.newFileSystem(new URI("jar:file:/D:/WEBtools/apache-tomcat-9.0.0.M15-windows-x64.zip"), 
				env,
				ClassLoader.getSystemClassLoader());

		// 获取path. 便捷的方式是paths.get("path");
		Path path = defaultSys.getPath("d:/temp");
		// 获取存储文件的容器
		Iterable<FileStore> fileStores = defaultSys.getFileStores();// Windows,DATA,RECOVERY
		fileStores.forEach((fileStore) -> {
			boolean readOnly = fileStore.isReadOnly();
//			dos : readonly         dos : hidden   dos  :  system  dos : archive 
			boolean supportsFileAttributeView = fileStore.supportsFileAttributeView(DosFileAttributeView.class);
			System.out.println(fileStore.name()+"是否只读： "+readOnly+"知否支持Dos文件系统"+supportsFileAttributeView);
		});
		// 获取根目录
		Iterable<Path> rootDirectories = defaultSys.getRootDirectories();// [C:\,// D:\, E:\]
		
		// 获取文件分隔符
		String separator = defaultSys.getSeparator();// \
		// 获取匹配
		PathMatcher pathMatcher = defaultSys.getPathMatcher("glob:.java");// 有两种语法 glob regex
		// 获取用户寻找服务
		UserPrincipalLookupService userPrincipalLookupService = defaultSys.getUserPrincipalLookupService();
//																																					电脑的用户名
		UserPrincipal lookupPrincipalByName = userPrincipalLookupService.lookupPrincipalByName("cobbl");
		// 文件系统是否打开
		boolean open = defaultSys.isOpen();// true
		// 文件系统是否只读
		boolean readOnly = defaultSys.isReadOnly();// false
		// 新的文件服务
		WatchService newWatchService = defaultSys.newWatchService();
		// 文件系统的提供者
		FileSystemProvider provider = defaultSys.provider();// WindowsFileSystemProvider

		// 文件系统支持的属性view
		Set<String> supportedFileAttributeViews = defaultSys.supportedFileAttributeViews();// [owner, dos, acl,  basic, user]
		String string = defaultSys.toString();

	}

}
