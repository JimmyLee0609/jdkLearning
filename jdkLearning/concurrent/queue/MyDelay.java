package queue;

import java.sql.Time;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyDelay implements Delayed {
	long currentTimeMillis;
	long delay = 0;

	@SuppressWarnings("unused")
	public MyDelay(long s) {
		super();
		currentTimeMillis = System.currentTimeMillis();
		delay=1000*s;
	}

	public MyDelay() {
		super();
		currentTimeMillis = System.currentTimeMillis();
	}

	@Override
	public int compareTo(Delayed o) {
		long delay = o.getDelay(TimeUnit.MILLISECONDS);
		long delay2 = this.getDelay(TimeUnit.MILLISECONDS);
		return (int) (delay - delay2);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long remain=delay-(System.currentTimeMillis()-this.currentTimeMillis);
		return unit.convert(remain, TimeUnit.SECONDS);
	}

}
