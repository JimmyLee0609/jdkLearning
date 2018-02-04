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
//		新建JAR的键，其实和ZIP是一样，就是多了属性，和证书，和签名
		JarEntry entry = new JarEntry("aaa/");
		Attributes attributes = entry.getAttributes();
		Certificate[] certificates = entry.getCertificates();
		CodeSigner[] codeSigners = entry.getCodeSigners();
		
//		获取一个实际的JARFile对象
		JarFile jarFile = new JarFile(
				new File("D:\\github repository\\jdkLearning\\jdkLearning\\src\\util\\jar\\Signed.jar"));
		// 就是将 META-INF/MANIFEST.MF ，这个键的内容返回，固定格式所有jar文件都一样
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
