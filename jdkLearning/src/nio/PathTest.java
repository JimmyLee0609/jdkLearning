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
		Path path = Paths.get("d:/temp/abc.txt");// �������ȡĬ�ϵ��ļ�ϵͳ��path
		// ��ʵ���ǵ�ͬ��
		Path path2 = FileSystems.getDefault().provider().getPath(new URI("file:///d:/temp/abc.txt"));
		
		File file = path.toFile();
//		�Ƿ���ָ����path��β
		boolean endsWith2 = path.endsWith(Paths.get("/abc.txt"));//false
		boolean endsWith3 = path.endsWith(Paths.get("abc.txt"));//true
//		�Ƿ���ָ�����ַ�����β
		boolean endsWith = path.endsWith(".txt");//false
		boolean endsWith4 = path.endsWith("abc.txt");//true
//		�Ƿ���ָ�����ַ�����ͷ
		boolean startsWith = path.startsWith("d:/");//true
		boolean startsWith3 = path.startsWith("d");//false
//		�Ƿ���ָ��·����ͷ
		boolean startsWith2 = path.startsWith(Paths.get("d:/temp"));//true
//		�Ƚ�����·���Ƿ���ͬ�����ܱȽϲ�ͬfilesystem��·�����˷������÷����ļ�ϵͳ�ģ�����Ҫ�����ļ�ϵͳ
		int compareTo = path.compareTo(path2);//0
//		�Ƚ�����·���Ƿ����
		boolean equals = path.equals(path2);//true
//		��ȡpath������
		Path fileName = path.getFileName();//abc.txt
//		��ȡ�������·�����ļ�ϵͳ
		FileSystem fileSystem = path.getFileSystem();
//		��ȡ��·��
		Path parent = path.getParent();//d:\temp
//		��ȡ��·��
		Path root = path.getRoot();//d:\
//		�Ƿ��Ǿ���·��
		boolean absolute = path.isAbsolute();//true
//		��ȡ·��������Ԫ�أ�����Ӧ��·��
		Path name = path.getName(0);//temp
//		��ȡpath������Ԫ�ص�����
		int nameCount = path.getNameCount();//2
//		�Ƿ��Ǿ���·��
		boolean absolute2 = path.isAbsolute();//true
//		����һ����������Ԫ�ص�·��
		Path normalize2 = path.normalize();//d:\temp\abc.txt
//		����·������·��
		Path subpath = path.subpath(0, 1);//temp
//		��������Ԫ�صĵ�����
		Iterator<Path> iterator = path.iterator();//temp     abc.txt
		while(iterator.hasNext()){
			System.out.println(iterator.next().toString());
		}
//		���·��      ���ᴴ���ļ�   ���Խ�·��Ԫ�ؽ��кϲ����γ��µ�·��
		Path path4 = Paths.get("d:/temp");
		Path resolve = path4.resolve("resolveString.txt");//d:\temp\resolveString.txt
		Path resolve2 = path4.resolve(Paths.get("resolvePath.txt"));//d:\temp\resolvePath.txt
		Path resolveSibling = path4.resolveSibling(Paths.get("abc/resolveSiblingPath"));//d:\temp\abc\resolveSiblingPath
		Path resolveSibling2 = path4.resolveSibling("abc/resolveStringSibling");//d:\temp\abc\resolveStringSibling
//		Ϊ·��ע��۲����,�۲����Ҫ���ļ���
		Path path3 = Paths.get("d:/temp");
		WatchKey register = path3.register(fileSystem.newWatchService(),StandardWatchEventKinds.ENTRY_MODIFY);
//		�����·���ʹ���·�������·��
		Path relativize2 = path.relativize(Paths.get("d:/temp/"));//..
		
		Path path5 = Paths.get("ggg");
		Path absolutePath2 = path5.toAbsolutePath();//C:\Users\cobbl\git\jdkLearning\jdkLearning\ggg
		Path relativize3 = path5.relativize(Paths.get("temp"));//..\temp
		Path absolutePath3 = relativize3.toAbsolutePath();//C:\Users\cobbl\git\jdkLearning\jdkLearning\..\temp
		File file2 = absolutePath3.toFile();//C:\Users\cobbl\git\jdkLearning\jdkLearning\..\temp
     	boolean exists = file.exists();// �ļ���ʵ����·��  "C:\Users\cobbl\git\jdkLearning\temp"
		
     	
     	Path path6 = Paths.get("ggg/temp/abc/ccc");//ggg\ddd
     	Path relativize4 = path6.relativize(Paths.get("ggg/temp/abc/ccc/ggg/ddd"));//ggg\ddd
     	Path absolutePath4 = relativize4.toAbsolutePath();//C:\Users\cobbl\git\jdkLearning\jdkLearning\ggg\ddd
    
     	Path resolve3 = path6.resolve("ggg/temp/abc/ccc");
     	Path parent2 = resolve3.getParent();
     	
//		����·����URI
     	URI uri = path.toUri();//file:///d:/temp/abc.txt
//		����·���ľ���·��
     	Path absolutePath = path.toAbsolutePath();//d:\temp\abc.txt
     	Path realPath = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
     	
     	String string = path.toString();
	}

}
