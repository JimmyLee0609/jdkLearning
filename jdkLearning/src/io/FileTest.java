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
		// ����File�ļ���ʽ
		File file3 = new File("d:/temp", "users.xml");// ͨ����Ŀ¼����Ŀ¼���ϳɾ���·��
														// �������·���Ŀ��������õ�Ե��
		File file = new File("d:/temp/users.xml");// ͨ������·��
		File file4 = new File(new URI("file:///d:/temp/users.xml"));// ͨ��URI����
																	// Э��++·��

		File file8 = new File("/");// ����java����� ��ǰ·��
		String absolutePath3 = file8.getAbsolutePath();// �ҵ�git�ļ� �� C:\

		String pathseparator = File.pathSeparator;// ���·���ķָ��� ;
		char pathseparatorchar = File.pathSeparatorChar;// ���·���ķָ��� ;
		String separator = File.separator;// ·���ķָ��� \
		char separatorchar = File.separatorChar;// ·���ķָ��� \

		// �ļ��Ƿ�ɶ�
		boolean canExecute = file.canExecute();// true
		// �ļ��Ƿ����
		boolean exists = file.exists();// true
		// �ļ��Ƿ�ɶ�
		boolean canRead = file.canRead();// true
		// ���Դ˳���·�����Ƿ�Ϊ����·������
		boolean absolute = file.isAbsolute();// true
		// �ļ��Ƿ��д
		boolean canWrite = file.canWrite();// true
		// �ļ��Ƿ����ļ���
		boolean directory = file.isDirectory();// false
		// �ļ��Ƿ����ļ�
		boolean file2 = file.isFile();// true
		// �ļ��Ƿ�����
		boolean hidden = file.isHidden();// false

		// ��ȡ�ļ��ľ���·��
		String absolutePath = file.getAbsolutePath(); // d:/temp/users.xml
		// ��ȡ�ļ���·��
		String path = file.getPath();// d:/temp/users.xml
		// ��ȡ�ļ��ĸ�·��
		String parent = file.getParent();// d:/temp
		// ���ش˳���·������Ŀ¼�ĳ���·�����������·����û��ָ����Ŀ¼���򷵻� null��
		File parentFile = file.getParentFile();// d:/temp
		// ��ȡ�ļ�������
		String name = file.getName();// users.xml
		// ��ȡ���һ���޸ĵ�ʱ��
		long lastModified = file.lastModified();// 1487610003538

		// �����˳���·����ָ����Ŀ¼���������б��赫�����ڵĸ�Ŀ¼��
		boolean mkdirs = file.mkdirs();// �ļ��Ѿ����ڣ�����Ҫ����������ļ������ڣ��ͻᴴ��
		// �����˳���·����ָ����Ŀ¼��
		boolean mkdir = file.mkdir();
		// ���ҽ��������ھ��д˳���·����ָ�����Ƶ��ļ�ʱ�����ɷֵش���һ���µĿ��ļ���
		boolean createNewFile = file.createNewFile();
		// ���ش˳���·�����ľ���·������ʽ��
		File absoluteFile = file.getAbsoluteFile();// d:\\temp\\users.xml

		// �������һ���޸ĵ�ʱ��
		boolean setLastModified = file.setLastModified(System.currentTimeMillis());
		// ������Ϊ
		// boolean renameTo = file.renameTo(new File(""));
		// ���ÿ�д
		boolean setWritable2 = file.setWritable(true);
		// �����Ƿ�ӵ���߿�д,���Ƿ�ӵ���߿�д
		boolean setWritable = file.setWritable(false, true);
		// ���ÿ�ִ��
		boolean setExecutable = file.setExecutable(false);
		// �Ƿ�ӵ���� �ܷ�ִ��
		boolean setExecutable2 = file.setExecutable(true, true);
		// �����Ƿ�ɶ�
		boolean setReadable = file.setReadable(true);
		// �Ƿ�ӵ���� �Ƿ�ɶ�
		boolean setReadable2 = file.setReadable(true, true);
		// �����Ƿ�ֻ��
		boolean setReadOnly = file.setReadOnly();

		// ���ش˳���·�����Ĺ淶·�����ַ�����
		String canonicalPath = file.getCanonicalPath();// D:/temp/users.xml
		// ���ش˳���·�����Ĺ淶��ʽ��
		File canonicalFile = file.getCanonicalFile();// D:/temp/users.xml
		String string = file.toString();

		// ��ȡ�ļ�ʣ��ռ� �����ȡ�����̷��µĴ�С
		long freeSpace = file.getFreeSpace();// 50574974976
		// ��ȡ�ļ���������С �����ȡ��������̷��µĴ�С
		long totalSpace = file.getTotalSpace();// 287631732736
		// ��ȡ�ļ����ÿռ� �����ȡ�����̷��µĴ�С
		long usableSpace = file.getUsableSpace();// 50574974976
		// �ļ��ĳ��� �����ȡ���ǵ�ǰ�ļ��Ĵ�С
		long length = file.length();// 0

		// ���ļ�ת��Ϊ·��
		Path path2 = file.toPath();// d:/temp/users.xml
		// ���ļ�ת��ΪURI
		URI uri = file.toURI();// file:/d:/temp/users.xml

		// �г����õ��ļ�ϵͳ��Ŀ¼ D:/ C:/ E:/
		File[] listRoots = File.listRoots();// [C:\, D:\, E:\]

		// ���ļ���·���µ��ļ����ļ��н���ת��
		File file5 = new File("D:/temp", "B/abc.txt");
		boolean absolute2 = file5.isAbsolute();
		boolean file6 = file.isFile();// true
		boolean directory2 = file5.isDirectory();// false
		String[] list = file5.list();// ���ַ��� null
		File[] listFiles = file5.listFiles();// ��Ϊ�ļ� null

		File file7 = new File("d:/temp");
		boolean directory3 = file7.isDirectory();// true
		String[] list2 = file7.list();// �г��ļ���[A, abc.t, ABC.txt, B,
										// HelloWorld.class, HelloWorld.jar,
										// HelloWorld.java, mydomain.csr,
										// seconrC.crt, sHelloWorld.jar,
										// test.crt, testKey.crt, users.xml]
		File[] listFiles2 = file7.listFiles();// �г��ļ�

		// ����ĸ˳��Ƚ���������·������
		int compareTo = file5.compareTo(file7);// 10

		// C:/Users/cobbl/AppData/Local/Temp ��ϵͳָ������ʱ�ļ��д���
		File createTempFile = File.createTempFile("abc", "test");
		String absolutePath2 = createTempFile.getAbsolutePath();

		// �ļ��˳�ʱɾ��
		file.deleteOnExit();
		/// ɾ���ļ�
		boolean delete = file.delete();
	}

	@SuppressWarnings("unused")
	private static void filter() {
		File file = new File("D:\\temp");
//		ʹ�ù�����  �����ļ�
		String[] list = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// dir �����ʾ file ��·�� name ����·�����ҵ���ÿ���ļ�������
				int b = 0;
				return false;
			}
		});
	}

}
