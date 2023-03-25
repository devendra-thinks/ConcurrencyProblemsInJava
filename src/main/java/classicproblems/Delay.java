package classicproblems;

import java.util.concurrent.TimeUnit;

public interface Delay extends Comparable<Delay>{
    long  getDelay(TimeUnit unit);
}
