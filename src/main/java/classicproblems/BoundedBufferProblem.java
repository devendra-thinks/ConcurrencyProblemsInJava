package classicproblems;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Solves bounded buffer problems by using semaphores only
 */
public class BoundedBufferProblem {

    static Queue<Integer > q;
    static Semaphore mutex, size, items;


    static class Producer implements Runnable{
        @Override
        public void run(){
            try {
                size.acquire();
                mutex.acquire();
                int number = ThreadLocalRandom.current().nextInt();
                q.add(number);
                System.out.println("Produced " +  Thread.currentThread().getName() + " " +number);
                items.release();
            }catch (InterruptedException e ){

            }finally{
                mutex.release();
            }
        }
    }

    static class Consumer implements Runnable{
        @Override
        public void run(){
            try {
                items.acquire();
                mutex.acquire();
                int number = q.poll();
                System.out.println("consumed " +  Thread.currentThread().getName()+ " " + number );
                size.release();
            }catch (InterruptedException e ){

            }finally{
                mutex.release();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        q = new LinkedList<>();
        mutex = new Semaphore(1);
        items = new Semaphore(0);
        size = new Semaphore(4);

       // test1();
      // test2();
    }
}
