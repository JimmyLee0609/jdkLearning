package statement;

import java.beans.Beans;
import java.beans.Introspector;
import java.beans.Statement;
import java.io.IOException;

public class StatementTest {

	public static void main(String[] args) throws Exception, IOException {
		Object instantiate = Beans.instantiate(StatementTest.class.getClassLoader(), "bean.MyBean");
		Statement statement = new Statement(instantiate, "testExe", new Object[] {});
		// ִ��ָ�����ָ�����������JSP����ʹ�á����ǲ��ܷ���ִ�к�Ľ��
		statement.execute();
	}

}
