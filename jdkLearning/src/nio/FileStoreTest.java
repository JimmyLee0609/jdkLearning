package nio;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;

public class FileStoreTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Iterable<FileStore> fileStores = fs.getFileStores();
		for (FileStore fileStore : fileStores) {
			String type = fileStore.type();
			String name = fileStore.name();
			boolean basic = fileStore.supportsFileAttributeView("basic");
			boolean posix = fileStore.supportsFileAttributeView("posix");
			boolean dos = fileStore.supportsFileAttributeView("dos");
			boolean acl = fileStore.supportsFileAttributeView("acl");
			boolean user = fileStore.supportsFileAttributeView("user");
			boolean owner = fileStore.supportsFileAttributeView("owner");//basic     posix   dos    acl     user
			System.out.println();
		}
		FileStore fileStore = Files.getFileStore(Paths.get("d:/temp"));
		String type = fileStore.type();
		long totalSpace = fileStore.getTotalSpace();
		long unallocatedSpace = fileStore.getUnallocatedSpace();
		long usableSpace = fileStore.getUsableSpace();
		boolean readOnly = fileStore.isReadOnly();
		
		boolean supportsFileAttributeView = fileStore.supportsFileAttributeView("basic");
		
		boolean supportsFileAttributeView2 = fileStore.supportsFileAttributeView(DosFileAttributeView.class);
		
		Object attribute = fileStore.getAttribute("totalSpace");//totalSpace,unallocatedSpace,usableSpace
		FileStoreAttributeView fileStoreAttributeView = fileStore.getFileStoreAttributeView(FileStoreAttributeView.class);//null
		String string = attribute.toString();
	}

}
