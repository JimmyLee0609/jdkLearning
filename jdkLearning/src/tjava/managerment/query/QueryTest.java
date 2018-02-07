package tjava.managerment.query;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.AttributeValueExp;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.Query;
import javax.management.QueryExp;
import javax.management.StringValueExp;
import javax.management.ValueExp;

public class QueryTest {
	public static interface SimpleMBean {
		public String getStringNumber();
	}

	public static class Simple implements SimpleMBean {
		public Simple(String number) {
			this.number = number;
		}

		public String getStringNumber() {
			return number;
		}

		private String number;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		query();
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName on = new ObjectName("domain:type=Simple,pattern=" + ObjectName.quote("2[7-9]") + ",name=21");
		ObjectName on2 = new ObjectName("domain:type=Simple,pattern=" + ObjectName.quote("2[7-9]") + ",name=22");
		ObjectName on3 = new ObjectName("domain:type=Simple,pattern=" + ObjectName.quote("2[7-9]") + ",name=23");
		Simple s = new Simple("21");
		Simple s2 = new Simple("28");
		Simple s3 = new Simple("27");
		mbs.registerMBean(s, on);
		mbs.registerMBean(s2, on2);
		mbs.registerMBean(s3, on3);

		/*
		 * ���ر�ʾ�ַ���ƥ��Լ���Ĳ�ѯ���ʽ���ر�ʾ�ַ�������ƥ��Լ���Ĳ�ѯ���ʽ��
		 * ƥ���﷨���ļ�ƥ����һ�µģ�֧�֡���������*������[����ÿ���������á�\����ת��;�ַ������ʹ�á������񶨺͡� - ����Χ��
		 * ��*�����κ��ַ����У������ڵ��������ַ��������ַ����У��� ���磺a *
		 * b��c��ƥ�����ַ�a��ͷ���ַ�������������������ַ������b���κε����ַ���c.argument��
		 * ƥ���﷨���ļ�ƥ����һ�µģ�֧�֡���������*������[����ÿ���������á�\����ת��;�ַ������ʹ�á������񶨺͡� - ����Χ��
		 * ��*�����κ��ַ����У������ڵ��������ַ��������ַ����У��� ���磺a * b��c��ƥ�����ַ�a��ͷ���ַ�������������������ַ������b���κε����ַ���c��
		 */
		QueryExp q = Query.match(Query.attr("StringNumber"), Query.value("2[7-9]"));
		q.setMBeanServer(mbs);
		boolean r = q.apply(on);// false
		boolean apply = q.apply(on2);// true
		Set<ObjectInstance> queryMBeans = mbs.queryMBeans(new ObjectName("*:*"), q);// [tjava.managerment.query.QueryTest$Simple[domain:type=Simple,pattern="2[7-9]",name=22],
																					// tjava.managerment.query.QueryTest$Simple[domain:type=Simple,pattern="2[7-9]",name=23]]
		System.out.print("Attribute Value = " + mbs.getAttribute(on, "StringNumber"));

	}

	@SuppressWarnings("unused")
	private static void query() {
		int div = Query.DIV;// div(javax.management.ValueExp, javax.management.ValueExp)
		int eq = Query.EQ;// eq(javax.management.ValueExp, javax.management.ValueExp)
		int ge = Query.GE;// geq(javax.management.ValueExp, javax.management.ValueExp)
		int gt = Query.GT;// gt(javax.management.ValueExp, javax.management.ValueExp)
		int le = Query.LE;// leq(javax.management.ValueExp, javax.management.ValueExp)
		int lt = Query.LT;// lt(javax.management.ValueExp, javax.management.ValueExp)
		int minus = Query.MINUS;// minus(javax.management.ValueExp, javax.management.ValueExp)
		int plus = Query.PLUS;// plus(javax.management.ValueExp, javax.management.ValueExp)
		int times = Query.TIMES;// times(javax.management.ValueExp, javax.management.ValueExp)

		ValueExp value = Query.value(false);
		ValueExp value2 = Query.value(5.3d);
		ValueExp value3 = Query.value(53.6f);
		ValueExp value4 = Query.value(5);
		ValueExp value5 = Query.value(50l);
		ValueExp value6 = Query.value(80);
		StringValueExp value7 = Query.value("dasda");

		/*
		 * ����һ���µ������Ա��ʽ����������ҪValueExp���κ�Query������ʹ�á�
		 * Ϊ������objectName����������ʽ����ִ��MBeanServer.getObjectInstance��objectName����
		 */
		AttributeValueExp classattr = Query.classattr();

		// ����һ���µ����Ա��ʽ�� �йر��ʽ�������ϸ˵���������AttributeValueExp��

		// Ϊ������objectName����������ʽ����ִ��MBeanServer.getAttribute��objectName��name����
		AttributeValueExp attr = Query.attr("name");
		/*������һ���µĺϸ�����Ա��ʽ��
		Ϊ����objectName�����˱��ʽ����ִ��MBeanServer.getObjectInstance��objectName��
		��MBeanServer.getAttribute��objectName��name����*/
		Query.attr("tjavax.managerment.standardbean.StandardD", "name");
		// ����Query���ʽ
		// ���ر�ʾ����ֵ�ĵ�ʽԼ���Ĳ�ѯ���ʽ��
		QueryExp eq2 = Query.eq(Query.attr("Enabled"), Query.value(true));

		// ����һ����ѯ���ʽ������ʾ����ֵ�ϵġ� ���ڡ�Լ����
		QueryExp gt2 = Query.gt(Query.value("value"), Query.value("value2"));
		// ����һ����ѯ���ʽ���ñ��ʽ������ֵ��ʾ �����ڻ���ڡ�Լ����
		QueryExp geq = Query.geq(Query.value("value"), Query.value("value2"));
		// ��������ֵ��ʾ��С�ڻ���ڡ�Լ���Ĳ�ѯ���ʽ��
		QueryExp leq = Query.leq(Query.value("value"), Query.value("value2"));
		// ����һ����ѯ���ʽ����ʾ������ֵ�ġ�С�ڡ�Լ����
		QueryExp lt2 = Query.lt(Query.value("value"), Query.value("value2"));
		// ����һ����ѯ���ʽ���ñ��ʽ��ʾһ��ֵ����������ֵ֮���Լ��������
		QueryExp between = Query.between(Query.value("value"), Query.value("value2"), Query.value("value3"));

		// ���ر�ʾ������ֵ֮�͵Ķ����Ʊ��ʽ���������ַ���ֵ�Ĵ�����
		ValueExp plus2 = Query.plus(Query.value("value"), Query.value("value2"));
		// ���ر�ʾ������ֵ�ĳ˻��Ķ����Ʊ��ʽ��
		ValueExp times2 = Query.times(Query.value("value"), Query.value("value2"));
		// ���ر�ʾ������ֵ֮�����Ķ����Ʊ��ʽ��
		ValueExp minus2 = Query.minus(Query.value("value"), Query.value("value2"));
		// ���ر�ʾ������ֵ���̵Ķ����Ʊ��ʽ��
		ValueExp div2 = Query.div(Query.value("value"), Query.value("value2"));
		
		
		// and ʹ�ò�ѯ���ʽ������������ѯ���ʽ�����ϡ�
		QueryExp query = Query.and(Query.eq(Query.attr("Enabled"), Query.value(true)),
				Query.eq(Query.attr("Owner"), Query.value("Duke")));
		// or ����һ����ѯ���ʽ����������������ѯ���ʽ����ȡ��
		QueryExp or = Query.or(Query.match(Query.attr("attr"), Query.value("value")),
				Query.match(Query.attr("attr2"), Query.value("value2")));
		// not ����������񶨵�Լ����
		QueryExp not = Query.not(Query.match(Query.attr("attr"), Query.value("value")));
		// in ����һ��Լ��ֵ�ı��ʽ����Ϊ��ʽ�б�֮һ��
		QueryExp in = Query.in(Query.value(true), new ValueExp[] { Query.value(true), Query.value(false) });

		/*
		 * ���ر�ʾ�ַ���������ƥ��Լ���Ĳ�ѯ���ʽ�� ƥ���﷨���ļ�ƥ����һ�µģ�֧�֡���������*������[����ÿ���������á�\����ת��;
		 * �ַ������ʹ�á������񶨺͡� - ����Χ�� ��*�����κ��ַ����У������ڵ��������ַ��������ַ����У��� ���磺a *
		 * b��c��ƥ�����ַ�a��ͷ���ַ�������������������ַ������b���κε����ַ���c��
		 */
		QueryExp match = Query.match(Query.attr("attr"), Query.value("value"));

		// ���ر�ʾ�ַ���������ƥ��Լ���Ĳ�ѯ���ʽ�� ��ֵ�����Ը����������ַ���ֵ��ʼ��
		QueryExp initialSubString = Query.initialSubString(Query.attr("attr"), Query.value("value"));

		// ���ر�ʾ�ַ���������ƥ��Լ���Ĳ�ѯ���ʽ�� ��ֵ������������������ַ���ֵ��
		QueryExp anySubString = Query.anySubString(Query.attr("attr"), Query.value("value"));

		// ���ر�ʾ�ַ���������ƥ��Լ���Ĳ�ѯ���ʽ�� ��ֵ�����Ը����������ַ���ֵ������
		QueryExp finalSubString = Query.finalSubString(Query.attr("attr"), Query.value("value"));
		/*
		 * ���ر�ʾMBean��ļ̳�Լ���Ĳ�ѯ���ʽ�� ʾ����Ҫ������ΪNotificationBroadcasterʵ����MBean��
		 * ��ʹ��Query.isInstanceOf��Query.value��NotificationBroadcaster.class.getName����������
		 * Ϊ����objectName�����˱��ʽ����ִ��
		 * MBeanServer.isInstanceOf(objectName��((StringValueExp)classNameValue.apply(
		 * objectName)).getValue()��
		 */
		QueryExp isInstanceOf = Query.isInstanceOf(Query.value("className"));

		String string = query.toString();

	}

}
