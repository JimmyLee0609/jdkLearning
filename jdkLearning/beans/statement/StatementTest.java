package statement;

import java.beans.Beans;
import java.beans.Introspector;
import java.beans.Statement;
import java.io.IOException;

public class StatementTest {

	public static void main(String[] args) throws Exception, IOException {
		Object instantiate = Beans.instantiate(StatementTest.class.getClassLoader(), "bean.MyBean");
		Statement statement = new Statement(instantiate, "testExe", new Object[] {});
		// 执行指定类的指定方法，这个JSP经常使用。但是不能返回执行后的结果
		statement.execute();
	}

}
