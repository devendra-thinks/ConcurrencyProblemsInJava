package classicproblems;

import java.util.concurrent.TimeUnit;

public class DelayRunnable implements Delay, Runnable{
    long fixedDelay;

    public DelayRunnable(long fixedDelay){
        this.fixedDelay = fixedDelay;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(fixedDelay - System.nanoTime(), TimeUnit.NANOSECONDS );
    }

    @Override
    public int compareTo(Delay o) {
        return Long.compare(this.getDelay(TimeUnit.NANOSECONDS), o.getDelay(TimeUnit.NANOSECONDS));
    }

    @Override
    public void run() {

    }
}
