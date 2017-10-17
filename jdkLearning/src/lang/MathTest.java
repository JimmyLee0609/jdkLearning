package lang;

public class MathTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		double pi = Math.PI;//3.141592653589793
		double e = Math.E;//2.718281828459045
		// 绝对值
		double abs = Math.abs(5.3d);//5.3
		float abs2 = Math.abs(53.1f);//53.1
		int abs3 = Math.abs(56);//56
		long abs4 = Math.abs(165l);//165
		
//		严格的计算选用StrictMath.  方法都是一样的
		double abs5 = StrictMath.abs(56.3);
		
		// 三角函数
		double acos = Math.acos(0.3);// 1.2661036727794992  返回值的反余弦值
		double asin = Math.asin(0.354);//0.36184461255069844   返回一个值的正弦值
		double atan = Math.atan(45.0);//1.5485777614681775  返回值的反正切值;
		double atan2 = Math.atan2(45.0, 45.0);//0.7853981633974483   从直角坐标（x，y）到极坐标（r，theta）的转换返回角度θ。该方法通过在-pi至pi的范围内计算y /x的反正切来计算相位θ。
		double cos = Math.cos(53.5);//-0.9956855884367365   返回角度的三角余弦
		double tan = Math.tan(90.0);//-1.995200412208242   返回角度的三角正切。
		double tanh = Math.tanh(56.3);//1.0   返回双值的双曲正切值。
		double sin = Math.sin(53.6);//-0.19173033639936585   返回角度的三角正弦。
		double sinh = Math.sinh(45.3);//2.3578166736596787E19   返回双值的双曲正弦。
		double cosh = Math.cosh(53.4);//7.767751758743762E22    返回双值的双曲余弦值。 x的双曲余弦被定义为（ex + e-x）/ 2，其中e是欧拉数

		double cbrt = Math.cbrt(50.3);//3.6913848744364715   返回double的多维数据集根。
	
		// 两个数相加
		int addExact = Math.addExact(5, 3);//8   两个数精确相加
		long addExact2 = Math.addExact(56l, 55l);//111   两个数精确相加   
//		赋值符号
		double copySign = Math.copySign(12.3, -23.3);//-12.3  使用第二个浮点参数的符号返回第一个浮点参数
		double copySign2 = Math.copySign(-12.5, 45.3);//12.5

		// 对数
		double log = Math.log(53.2);//3.9740583963475986  返回双值的自然对数（基数e）。
		double log10 = Math.log10(124.5);//2.095169351431755   返回双值的基数10对数。
		double log1p = Math.log1p(54.2);//4.01096295328305  返回参数和1的和的自然对数。

		int decrementExact = Math.decrementExact(531);//530  返回一个递减1的参数，如果结果溢出一个int，则抛出一个异常。
		long decrementExact2 = Math.decrementExact(536l);//535
		double exp = Math.exp(3.2);//24.532530197109352   返回欧拉的数字e提高到双重值的力量。
		double expm1 = Math.expm1(2.2);//8.025013499434122    e的x次方减1

		// 不大于 或不小于 界限值
		
		double rint = Math.rint(45.1);//45.0    返回与参数最接近的值的double值，等于数学整数。
		long round = Math.round(36.5);//37   返回与参数最接近的int，其中边界四舍五入为正无穷大。

		double ceil = Math.ceil(53.2);//54.0   返回大于或等于参数的最小（最接近负的无穷大）双重值，并等于一个数学整数
		double floor = Math.floor(5.2);//5.0   返回小于或等于参数的最大（最接近正的无穷大）双值，并且等于数学整数

		int floorDiv = Math.floorDiv(5, 3);//1    返回小于或等于代数商的最大（最接近正无穷大）的int值。
		long floorDiv2 = Math.floorDiv(153l, 156l);//0   返回小于或等于代数商的最大（最接近正无穷大）的int值。
		int floorMod = Math.floorMod(153, 23);//15    返回int参数的floor模数。
		long floorMod2 = Math.floorMod(53l, 21l);//11   返回int参数的floor模数。

		double max = Math.max(53.1d, 56.1d);//56.1    返回两个长值中的较大值。
		int max2 = Math.max(53, 56);//56   返回两个长值中的较大值。
		int min = Math.min(125, 53);//53    返回两个长值中的较小值。

		
		// 参数自增长 1
		int incrementExact = Math.incrementExact(8);//9
		long incrementExact2 = Math.incrementExact(53l);//54    返回一个增加1的参数，如果结果溢出一个长度，则抛出异常。

		// 指数
		int exponent = Math.getExponent(30.5d);//4    返回双重表示中使用的无偏指数
		int exponent2 = Math.getExponent(530.2f);//9    返回浮点数表示中使用的无偏指数。
		double hypot = Math.hypot(20.3, 52.1);//55.915114235777075      返回sqrt（x2 + y2），没有中间溢出或下溢。
		double pow = Math.pow(5.4, 2);//29.160000000000004  将第一个参数的值返回到第二个参数的幂。

		// 浮点数
		double ieeEremainder = Math.IEEEremainder(23.4, 25.4);//-2.0  根据IEEE 754标准规定的两个参数计算余数运算。

		double nextAfter = Math.nextAfter(53.2, 2.0);//53.199999999999996   返回与第二个参数方向相邻的第一个参数的浮点数。
		double nextDown = Math.nextDown(45.5);//45.49999999999999   返回与负无穷大方向相邻的f的浮点值。
		double nextUp = Math.nextUp(78.8);//78.80000000000001   返回与正无穷大方向相邻的f的浮点值。
		double random = Math.random();//0.6544774001446286  
		double scalb = Math.scalb(56.244, 2);//224.976   返回f×2scaleFactor四舍五入，如同通过单个正确四舍五入的浮点数乘以浮点值集合的成员执行的。
		double signum = Math.signum(53.5);//1.0    返回参数的signum函数; 如果参数为零，则为零，如果参数大于零则为1.0f，如果参数小于零，则为-1.0f。

		int negateExact = Math.negateExact(465);//-465 返回参数的否定，如果结果溢出一个int，则抛出异常。
		int multiplyExact = Math.multiplyExact(53, 2);//106   返回参数的乘积，如果结果溢出，则抛出异常。
		double sqrt = Math.sqrt(56.3);//7.503332592921628   返回正确舍入的双倍值的正平方根。
		int subtractExact = Math.subtractExact(5, 63);//-58    返回参数的差异，如果结果溢出，则抛出异常。

		int intExact = Math.toIntExact(1654846l);//1654846    返回long参数的值; 如果值溢出一个int，则抛出异常。
		double ulp = Math.ulp(56.5);//7.105427357601002E-15    返回参数的ulp的大小。
		float ulp2 = Math.ulp(51.63f);//3.8146973E-6    返回参数的ulp的大小。

		// 角度
		double radians = Math.toRadians(168.51651);//2.94116794346995    将以度为单位的角度转换为以弧度测量的大致相等的角度。
		double degrees = Math.toDegrees(120.0);//6875.493541569879     将以弧度测量的角度转换为以度为单位的近似等效角度

		int c = 0;
	}

}
