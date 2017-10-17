package random;

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class RandomTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		long l=181783497276652981l*8682522807148012L;//8006678197202707420l
		Random random = new Random();//seed=8006678197202707420l^当前时间毫秒数
		int nextInt = random.nextInt(30);						//返回一个不大于30的int
		int nextInt2 = random.nextInt();						//返回一个int
		long nextLong = random.nextLong();           		//返回一个long
		boolean nextBoolean = random.nextBoolean();//返回一个boolean
		double nextDouble = random.nextDouble();		//返回一个double
		float nextFloat = random.nextFloat();				//返回一个float
		double nextGaussian = random.nextGaussian();//返回一个Gaussian高斯分布值，正太分布的值 0.0--1.0之间
		byte[] b = new byte[] { 3, 5, 7, 2 };			
		random.nextBytes(b);									//将byte数组中的元素随机的换成其他数字，数组长度不变
		
		IntStream ints = random.ints();
		IntStream ints3 = random.ints(7);
		IntStream ints2 = random.ints(0, 3);
		IntStream ints4 = random.ints(9, 5, 20);

		LongStream longs = random.longs();
		LongStream longs2 = random.longs(13);
		LongStream longs4 = random.longs(55, 71);
		LongStream longs3 = random.longs(8, 77, 89);

		DoubleStream doubles = random.doubles();
		DoubleStream doubles3 = random.doubles(10);
		DoubleStream doubles2 = random.doubles(5.0, 10.0);
		DoubleStream doubles4 = random.doubles(5, 100.0, 110.0);

		random.setSeed(53);
	}

}
