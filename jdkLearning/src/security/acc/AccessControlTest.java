package security.acc;

import java.io.File;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class AccessControlTest {

	public static void main(String[] args) {
	
		new AccessControlTest().testAcc();
	
	}
	
	
	@SuppressWarnings("unused")
	public void testAcc(){
//		本类 访问下面文件，需要权限， 其他类调用这个方法时，只要这类类有权限就可以调用run里面的内容了
		AccessController.doPrivileged(new PrivilegedAction<String>(){
			public String run(){
				File file3 = new File("D:\\new\\testPolicy\\test1.txt");
				file3.isDirectory();
				return "";
			}
		});
	}
}
