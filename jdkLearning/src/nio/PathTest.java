package nio;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.Iterator;

public class PathTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws URISyntaxException, IOException {
		Path path = Paths.get("d:/temp/abc.txt");// 工具类获取默认的文件系统的path
		// 其实就是等同于
		Path path2 = FileSystems.getDefault().provider().getPath(new URI("file:///d:/temp/abc.txt"));
		
		File file = path.toFile();
//		是否以指定的path结尾
		boolean endsWith2 = path.endsWith(Paths.get("/abc.txt"));//false
		boolean endsWith3 = path.endsWith(Paths.get("abc.txt"));//true
//		是否以指定的字符串结尾
		boolean endsWith = path.endsWith(".txt");//false
		boolean endsWith4 = path.endsWith("abc.txt");//true
//		是否以指定的字符串开头
		boolean startsWith = path.startsWith("d:/");//true
		boolean startsWith3 = path.startsWith("d");//false
//		是否以指定路径开头
		boolean startsWith2 = path.startsWith(Paths.get("d:/temp"));//true
//		比较两个路径是否相同，不能比较不同filesystem的路径。此方法不用访问文件系统的，不需要存在文件系统
		int compareTo = path.compareTo(path2);//0
//		比较两个路径是否相等
		boolean equals = path.equals(path2);//true
//		获取path的名字
		Path fileName = path.getFileName();//abc.txt
//		获取创建这个路径的文件系统
		FileSystem fileSystem = path.getFileSystem();
//		获取父路径
		Path parent = path.getParent();//d:\temp
//		获取根路径
		Path root = path.getRoot();//d:\
//		是否是绝对路径
		boolean absolute = path.isAbsolute();//true
//		获取路径中名字元素，所对应的路径
		Path name = path.getName(0);//temp
//		获取path中名字元素的数量
		int nameCount = path.getNameCount();//2
//		是否是绝对路径
		boolean absolute2 = path.isAbsolute();//true
//		返回一个消除冗余元素的路径
		Path normalize2 = path.normalize();//d:\temp\abc.txt
//		返回路径的子路径
		Path subpath = path.subpath(0, 1);//temp
//		返回名字元素的迭代器
		Iterator<Path> iterator = path.iterator();//temp     abc.txt
		while(iterator.hasNext()){
			System.out.println(iterator.next().toString());
		}
//		解决路径      不会创建文件   可以将路径元素进行合并，形成新的路径
		Path path4 = Paths.get("d:/temp");
		Path resolve = path4.resolve("resolveString.txt");//d:\temp\resolveString.txt
		Path resolve2 = path4.resolve(Paths.get("resolvePath.txt"));//d:\temp\resolvePath.txt
		Path resolveSibling = path4.resolveSibling(Paths.get("abc/resolveSiblingPath"));//d:\temp\abc\resolveSiblingPath
		Path resolveSibling2 = path4.resolveSibling("abc/resolveStringSibling");//d:\temp\abc\resolveStringSibling
//		为路径注册观察服务,观察的需要是文件夹
		Path path3 = Paths.get("d:/temp");
		WatchKey register = path3.register(fileSystem.newWatchService(),StandardWatchEventKinds.ENTRY_MODIFY);
//		构造此路径和传入路径的相对路径
		Path relativize2 = path.relativize(Paths.get("d:/temp/"));//..
		
		Path path5 = Paths.get("ggg");
		Path absolutePath2 = path5.toAbsolutePath();//C:\Users\cobbl\git\jdkLearning\jdkLearning\ggg
		Path relativize3 = path5.relativize(Paths.get("temp"));//..\temp
		Path absolutePath3 = relativize3.toAbsolutePath();//C:\Users\cobbl\git\jdkLearning\jdkLearning\..\temp
		File file2 = absolutePath3.toFile();//C:\Users\cobbl\git\jdkLearning\jdkLearning\..\temp
     	boolean exists = file.exists();// 文件的实际是路径  "C:\Users\cobbl\git\jdkLearning\temp"
		
     	
     	Path path6 = Paths.get("ggg/temp/abc/ccc");//ggg\ddd
     	Path relativize4 = path6.relativize(Paths.get("ggg/temp/abc/ccc/ggg/ddd"));//ggg\ddd
     	Path absolutePath4 = relativize4.toAbsolutePath();//C:\Users\cobbl\git\jdkLearning\jdkLearning\ggg\ddd
    
     	Path resolve3 = path6.resolve("ggg/temp/abc/ccc");
     	Path parent2 = resolve3.getParent();
     	
//		返回路径的URI
     	URI uri = path.toUri();//file:///d:/temp/abc.txt
//		返回路径的绝对路径
     	Path absolutePath = path.toAbsolutePath();//d:\temp\abc.txt
     	Path realPath = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
     	
     	String string = path.toString();
	}

}
