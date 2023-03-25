package notsoclassicalproblems.delayqueue;

import java.util.Random;

public class TestGeneralConceptsHere {
    public static void main(String[] args) {
        DelayQueue delayQueue = new DelayQueue(2);
        long currentTimemillis = System.currentTimeMillis();
        for(int i = 0 ; i < 15 ; i++ ){
            int rnd = new Random().nextInt(10);
            long time  = currentTimemillis + 1000*rnd;
            System.out.println("Task to be delayed " + rnd );
            delayQueue.put(new DelayMessage(() -> System.out.println("" + rnd), time  , "" + rnd ));
        }
    }
}
