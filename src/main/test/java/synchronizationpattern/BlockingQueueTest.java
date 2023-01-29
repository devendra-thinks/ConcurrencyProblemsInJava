package synchronizationpattern;

import junit.framework.TestCase;
import org.junit.Test;


public class BlockingQueueTest extends TestCase {

    BlockingQueue blockingQueue = new BlockingQueue();

    @Test
    public void test() throws InterruptedException {

        Thread consumer1 = new Thread(() -> {
            try {
                System.out.println(" consumer starting  ... ");
                Runnable r = blockingQueue.poll();
                r.run();
                System.out.println(" consumer ending   ... " + r);
            }catch (Exception e ){
            }
        });
        Thread consumer2 = new Thread(() -> {
            try {
                System.out.println(" consumer starting  ... ");
                Runnable r = blockingQueue.poll();
                r.run();
                System.out.println(" consumer ending   ... " + r);
            }catch (Exception e ){
            }
        });
        consumer1.start();
        consumer2.start();

        Thread.sleep(10000);

        Runnable prod = () ->  {
                try {
                    System.out.println(" producer starting  ... ");
                    blockingQueue.add(()-> System.out.println(" 1 Runnable is run "));
                }catch (Exception e ){
                }
            System.out.println(" producer ending   ... ");
        };
        Thread producer = new Thread(prod);
        Thread producer1 = new Thread(prod);
        producer.start();
        producer1.start();

    }

}