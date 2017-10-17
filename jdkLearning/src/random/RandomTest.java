package random;

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class RandomTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		long l=181783497276652981l*8682522807148012L;//8006678197202707420l
		Random random = new Random();//seed=8006678197202707420l^��ǰʱ�������
		int nextInt = random.nextInt(30);						//����һ��������30��int
		int nextInt2 = random.nextInt();						//����һ��int
		long nextLong = random.nextLong();           		//����һ��long
		boolean nextBoolean = random.nextBoolean();//����һ��boolean
		double nextDouble = random.nextDouble();		//����һ��double
		float nextFloat = random.nextFloat();				//����һ��float
		double nextGaussian = random.nextGaussian();//����һ��Gaussian��˹�ֲ�ֵ����̫�ֲ���ֵ 0.0--1.0֮��
		byte[] b = new byte[] { 3, 5, 7, 2 };			
		random.nextBytes(b);									//��byte�����е�Ԫ������Ļ����������֣����鳤�Ȳ���
		
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
