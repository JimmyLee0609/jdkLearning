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
		// ��ȡϵͳĬ�ϵ��ļ�ϵͳ
		FileSystem defaultSys = FileSystems.getDefault();
		// ����URI��ȡ�ļ�ϵͳ��ֻ��������д����ȡ������sun.nio.fs.WindowsFileSystem
		FileSystem fileSystem = FileSystems.getFileSystem(new URI("file:///"));
		
		// ��ȡ������com.sun.nio.zipfs.ZipFileSystem
		 FileSystem newFileSystem2 = FileSystems.newFileSystem(Paths.get("D:/WEBtools/apache-tomcat-9.0.0.M15-windows-x64.zip"),
		 								ClassLoader.getSystemClassLoader());
		
		Map<String, String> env = new HashMap<String, String>();
		env.put("create", "true");
		// com.sun.nio.zipfs.ZipFileSystem
		FileSystems.newFileSystem(new URI("jar:file:/D:/WEBtools/apache-tomcat-9.0.0.M15-windows-x64.zip"), 
				env,
				ClassLoader.getSystemClassLoader());

		// ��ȡpath. ��ݵķ�ʽ��paths.get("path");
		Path path = defaultSys.getPath("d:/temp");
		// ��ȡ�洢�ļ�������
		Iterable<FileStore> fileStores = defaultSys.getFileStores();// Windows,DATA,RECOVERY
		fileStores.forEach((fileStore) -> {
			boolean readOnly = fileStore.isReadOnly();
//			dos : readonly         dos : hidden   dos  :  system  dos : archive 
			boolean supportsFileAttributeView = fileStore.supportsFileAttributeView(DosFileAttributeView.class);
			System.out.println(fileStore.name()+"�Ƿ�ֻ���� "+readOnly+"֪��֧��Dos�ļ�ϵͳ"+supportsFileAttributeView);
		});
		// ��ȡ��Ŀ¼
		Iterable<Path> rootDirectories = defaultSys.getRootDirectories();// [C:\,// D:\, E:\]
		
		// ��ȡ�ļ��ָ���
		String separator = defaultSys.getSeparator();// \
		// ��ȡƥ��
		PathMatcher pathMatcher = defaultSys.getPathMatcher("glob:.java");// �������﷨ glob regex
		// ��ȡ�û�Ѱ�ҷ���
		UserPrincipalLookupService userPrincipalLookupService = defaultSys.getUserPrincipalLookupService();
//																																					���Ե��û���
		UserPrincipal lookupPrincipalByName = userPrincipalLookupService.lookupPrincipalByName("cobbl");
		// �ļ�ϵͳ�Ƿ��
		boolean open = defaultSys.isOpen();// true
		// �ļ�ϵͳ�Ƿ�ֻ��
		boolean readOnly = defaultSys.isReadOnly();// false
		// �µ��ļ�����
		WatchService newWatchService = defaultSys.newWatchService();
		// �ļ�ϵͳ���ṩ��
		FileSystemProvider provider = defaultSys.provider();// WindowsFileSystemProvider

		// �ļ�ϵͳ֧�ֵ�����view
		Set<String> supportedFileAttributeViews = defaultSys.supportedFileAttributeViews();// [owner, dos, acl,  basic, user]
		String string = defaultSys.toString();

	}

}
