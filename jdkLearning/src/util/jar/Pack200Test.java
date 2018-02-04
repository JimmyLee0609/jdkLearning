package util.jar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.jar.Pack200.Packer;
import java.util.jar.Pack200.Unpacker;

public class Pack200Test {

	public static void main(String[] args) {
		 // 创建一个打包对象
	    Packer packer = Pack200.newPacker();

	    // 初始化属性
	    Map p = packer.properties();
	    // take more time choosing codings for better compression
	    p.put(Packer.EFFORT, "7");  // default is "5"
	    // use largest-possible archive segments (>10% better compression).
	    p.put(Packer.SEGMENT_LIMIT, "-1");
	    // reorder files for better compression.
	    p.put(Packer.KEEP_FILE_ORDER, Packer.FALSE);
	    // smear modification times to a single value.
	    p.put(Packer.MODIFICATION_TIME, Packer.LATEST);
	    // ignore all JAR deflation requests,
	    // transmitting a single request to use "store" mode.
	    p.put(Packer.DEFLATE_HINT, Packer.FALSE);
	    // discard debug attributes
	    p.put(Packer.CODE_ATTRIBUTE_PFX+"LineNumberTable", Packer.STRIP);
	    // throw an error if an attribute is unrecognized
	    p.put(Packer.UNKNOWN_ATTRIBUTE, Packer.ERROR);
	    // pass one class file uncompressed:
	    p.put(Packer.PASS_FILE_PFX+0, "mutants/Rogue.class");
	    try {
	        JarFile jarFile = new JarFile("/tmp/testref.jar");
	        FileOutputStream fos = new FileOutputStream("/tmp/test.pack");
	        // 将一个JAR文件打包成pack文件
	        packer.pack(jarFile, fos);
	        jarFile.close();
	        fos.close();

	        File f = new File("/tmp/test.pack");
	        FileOutputStream fostream = new FileOutputStream("/tmp/test.jar");
	        JarOutputStream jostream = new JarOutputStream(fostream);
	        Unpacker unpacker = Pack200.newUnpacker();
	        // 将一个pack文件解包成jar文件
	        unpacker.unpack(f, jostream);
	        // Must explicitly close the output.
	        jostream.close();
	    } catch (IOException ioe) {
	        ioe.printStackTrace();
	    }
	}

}
