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
//		���� ���������ļ�����ҪȨ�ޣ� ����������������ʱ��ֻҪ��������Ȩ�޾Ϳ��Ե���run�����������
		AccessController.doPrivileged(new PrivilegedAction<String>(){
			public String run(){
				File file3 = new File("D:\\new\\testPolicy\\test1.txt");
				file3.isDirectory();
				return "";
			}
		});
	}
}
