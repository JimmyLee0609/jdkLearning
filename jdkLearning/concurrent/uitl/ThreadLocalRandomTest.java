package uitl;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class ThreadLocalRandomTest {

	public static void main(String[] args) {
//	ʹ���߳������,��ΪRandom��һ��α�����,����ϵͳʱ�任�������,���ͬһʱ����߳�ͬʱ��ȡ,��ͬ�ļ��ʺܴ�
//		ʹ���������Լ���������ٶ��̵߳Ļ�����,��֤�����.һ���̶���
ThreadLocalRandom random = ThreadLocalRandom.current();
byte[] bytes = "my byte".getBytes();
random.nextBytes(bytes);//�������ڵ�����ʹ���µ����������
DoubleStream doubles = random.doubles();
//�޶� ������Ĵ�С,�Թ�9��Ԫ��     ������� 7.5     ����ı仯��Χ  25
DoubleStream doubles2 = random.doubles(9, 7.5, 25);

IntStream ints = random.ints(9, 7, 25);
LongStream longs = random.longs(9, 7, 25);

IntStream ints2 = random.ints();
float nextFloat = random.nextFloat();
boolean nextBoolean = random.nextBoolean();
//0-1֮��������
double nextGaussian = random.nextGaussian();
//�趨�������
random.setSeed(7598);

	}

}
