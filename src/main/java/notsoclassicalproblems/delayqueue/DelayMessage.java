package notsoclassicalproblems.delayqueue;

public class DelayMessage  implements Comparable {
    Runnable runnable;
    long time;
    String name;

    public DelayMessage(Runnable runnable, long time, String name){
        this.runnable = runnable;
        this.time = time;
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        DelayMessage delayMessage = (DelayMessage) o;
        long currentTs = System.currentTimeMillis();
        return Long.compare(this.time - currentTs, delayMessage.time - currentTs);
    }
}
