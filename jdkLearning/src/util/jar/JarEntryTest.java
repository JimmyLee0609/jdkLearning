package util.jar;

import java.io.File;
import java.io.IOException;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class JarEntryTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
//		�½�JAR�ļ�����ʵ��ZIP��һ�������Ƕ������ԣ���֤�飬��ǩ��
		JarEntry entry = new JarEntry("aaa/");
		Attributes attributes = entry.getAttributes();
		Certificate[] certificates = entry.getCertificates();
		CodeSigner[] codeSigners = entry.getCodeSigners();
		
//		��ȡһ��ʵ�ʵ�JARFile����
		JarFile jarFile = new JarFile(
				new File("D:\\github repository\\jdkLearning\\jdkLearning\\src\\util\\jar\\Signed.jar"));
		// ���ǽ� META-INF/MANIFEST.MF ������������ݷ��أ��̶���ʽ����jar�ļ���һ��
		Manifest manifest = jarFile.getManifest();
		
		jarFile.stream().forEach((entri)->{
			String name = entri.getName();
			Certificate[] certificates2 = entri.getCertificates();
			try {
				Attributes attributes2 = entri.getAttributes();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CodeSigner[] codeSigners2 = entri.getCodeSigners();
			String string = new String();
		});
		jarFile.close();
	}

}
