package uitl;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class ThreadLocalRandomTest {

	public static void main(String[] args) {
//	使用线程随机数,因为Random是一个伪随机数,根据系统时间换算过来的,如果同一时间多线程同时获取,相同的几率很大
//		使用这个类可以减少随机数再多线程的环境下,保证随机性.一定程度上
ThreadLocalRandom random = ThreadLocalRandom.current();
byte[] bytes = "my byte".getBytes();
random.nextBytes(bytes);//将数组内的内容使用新的随机数代替
DoubleStream doubles = random.doubles();
//限定 这个流的大小,以供9个元素     随机种子 7.5     随机的变化范围  25
DoubleStream doubles2 = random.doubles(9, 7.5, 25);

IntStream ints = random.ints(9, 7, 25);
LongStream longs = random.longs(9, 7, 25);

IntStream ints2 = random.ints();
float nextFloat = random.nextFloat();
boolean nextBoolean = random.nextBoolean();
//0-1之间的随机数
double nextGaussian = random.nextGaussian();
//设定随机种子
random.setSeed(7598);

	}

}
