package statement;

import java.beans.Beans;
import java.beans.Expression;
import java.io.IOException;

public class ExpressionTest {

	public static void main(String[] args) throws Exception, IOException {
		// 使用表达式的方式类处理
		Object instantiate = Beans.instantiate(ExpressionTest.class.getClassLoader(), "bean.MyBean");
		// 构建表达式
		Expression expression = new Expression(instantiate, "testExe", new Object[] {});
		// 执行
		expression.execute();
		// 构建表达式
		Expression expression2 = new Expression(instantiate, "getId", new Object[] {});
		// 执行
		expression2.execute();
		// 获取返回值，如果调用的方法有返回值就返回对应的值
		Object value = expression2.getValue();
		System.out.println(value);
	}

}
