package statement;

import java.beans.Beans;
import java.beans.Expression;
import java.io.IOException;

public class ExpressionTest {

	public static void main(String[] args) throws Exception, IOException {
		// ʹ�ñ��ʽ�ķ�ʽ�ദ��
		Object instantiate = Beans.instantiate(ExpressionTest.class.getClassLoader(), "bean.MyBean");
		// �������ʽ
		Expression expression = new Expression(instantiate, "testExe", new Object[] {});
		// ִ��
		expression.execute();
		// �������ʽ
		Expression expression2 = new Expression(instantiate, "getId", new Object[] {});
		// ִ��
		expression2.execute();
		// ��ȡ����ֵ��������õķ����з���ֵ�ͷ��ض�Ӧ��ֵ
		Object value = expression2.getValue();
		System.out.println(value);
	}

}
