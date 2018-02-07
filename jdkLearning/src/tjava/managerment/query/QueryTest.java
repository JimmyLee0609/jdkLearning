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
		 * 返回表示字符串匹配约束的查询表达式返回表示字符串参数匹配约束的查询表达式。
		 * 匹配语法与文件匹配是一致的：支持“？”，“*”，“[”，每个都可以用“\”来转义;字符类可以使用“！”否定和“ - ”范围。
		 * （*用于任何字符序列，？用于单个任意字符，用于字符序列）。 例如：a *
		 * b？c会匹配以字符a开头的字符串，后跟任意数量的字符，后跟b，任何单个字符和c.argument。
		 * 匹配语法与文件匹配是一致的：支持“？”，“*”，“[”，每个都可以用“\”来转义;字符类可以使用“！”否定和“ - ”范围。
		 * （*用于任何字符序列，？用于单个任意字符，用于字符序列）。 例如：a * b？c将匹配以字符a开头的字符串，后跟任意数量的字符，后跟b，任何单个字符和c。
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
		 * 返回一个新的类属性表达式，可以在需要ValueExp的任何Query调用中使用。
		 * 为给定的objectName评估这个表达式包括执行MBeanServer.getObjectInstance（objectName）。
		 */
		AttributeValueExp classattr = Query.classattr();

		// 返回一个新的属性表达式。 有关表达式语义的详细说明，请参阅AttributeValueExp。

		// 为给定的objectName评估这个表达式包括执行MBeanServer.getAttribute（objectName，name）。
		AttributeValueExp attr = Query.attr("name");
		/*产生了一个新的合格的属性表达式。
		为给定objectName评估此表达式包括执行MBeanServer.getObjectInstance（objectName）
		和MBeanServer.getAttribute（objectName，name）。*/
		Query.attr("tjavax.managerment.standardbean.StandardD", "name");
		// 做成Query表达式
		// 返回表示两个值的等式约束的查询表达式。
		QueryExp eq2 = Query.eq(Query.attr("Enabled"), Query.value(true));

		// 返回一个查询表达式，它表示两个值上的“ 大于”约束。
		QueryExp gt2 = Query.gt(Query.value("value"), Query.value("value2"));
		// 返回一个查询表达式，该表达式对两个值表示 “大于或等于”约束。
		QueryExp geq = Query.geq(Query.value("value"), Query.value("value2"));
		// 返回两个值表示“小于或等于”约束的查询表达式。
		QueryExp leq = Query.leq(Query.value("value"), Query.value("value2"));
		// 返回一个查询表达式，表示对两个值的“小于”约束。
		QueryExp lt2 = Query.lt(Query.value("value"), Query.value("value2"));
		// 返回一个查询表达式，该表达式表示一个值在两个其他值之间的约束条件。
		QueryExp between = Query.between(Query.value("value"), Query.value("value2"), Query.value("value3"));

		// 返回表示两个数值之和的二进制表达式，或两个字符串值的串联。
		ValueExp plus2 = Query.plus(Query.value("value"), Query.value("value2"));
		// 返回表示两个数值的乘积的二进制表达式。
		ValueExp times2 = Query.times(Query.value("value"), Query.value("value2"));
		// 返回表示两个数值之间差异的二进制表达式。
		ValueExp minus2 = Query.minus(Query.value("value"), Query.value("value2"));
		// 返回表示两个数值的商的二进制表达式。
		ValueExp div2 = Query.div(Query.value("value"), Query.value("value2"));
		
		
		// and 使得查询表达式是两个其他查询表达式的联合。
		QueryExp query = Query.and(Query.eq(Query.attr("Enabled"), Query.value(true)),
				Query.eq(Query.attr("Owner"), Query.value("Duke")));
		// or 返回一个查询表达式，它是两个其他查询表达式的析取。
		QueryExp or = Query.or(Query.match(Query.attr("attr"), Query.value("value")),
				Query.match(Query.attr("attr2"), Query.value("value2")));
		// not 返回其参数否定的约束。
		QueryExp not = Query.not(Query.match(Query.attr("attr"), Query.value("value")));
		// in 返回一个约束值的表达式，作为显式列表之一。
		QueryExp in = Query.in(Query.value(true), new ValueExp[] { Query.value(true), Query.value(false) });

		/*
		 * 返回表示字符串参数的匹配约束的查询表达式。 匹配语法与文件匹配是一致的：支持“？”，“*”，“[”，每个都可以用“\”来转义;
		 * 字符类可以使用“！”否定和“ - ”范围。 （*用于任何字符序列，？用于单个任意字符，用于字符序列）。 例如：a *
		 * b？c将匹配以字符a开头的字符串，后跟任意数量的字符，后跟b，任何单个字符和c。
		 */
		QueryExp match = Query.match(Query.attr("attr"), Query.value("value"));

		// 返回表示字符串参数的匹配约束的查询表达式。 该值必须以给定的文字字符串值开始。
		QueryExp initialSubString = Query.initialSubString(Query.attr("attr"), Query.value("value"));

		// 返回表示字符串参数的匹配约束的查询表达式。 该值必须包含给定的文字字符串值。
		QueryExp anySubString = Query.anySubString(Query.attr("attr"), Query.value("value"));

		// 返回表示字符串参数的匹配约束的查询表达式。 该值必须以给定的文字字符串值结束。
		QueryExp finalSubString = Query.finalSubString(Query.attr("attr"), Query.value("value"));
		/*
		 * 返回表示MBean类的继承约束的查询表达式。 示例：要查找作为NotificationBroadcaster实例的MBean，
		 * 请使用Query.isInstanceOf（Query.value（NotificationBroadcaster.class.getName（）））。
		 * 为给定objectName评估此表达式包括执行
		 * MBeanServer.isInstanceOf(objectName，((StringValueExp)classNameValue.apply(
		 * objectName)).getValue()。
		 */
		QueryExp isInstanceOf = Query.isInstanceOf(Query.value("className"));

		String string = query.toString();

	}

}
