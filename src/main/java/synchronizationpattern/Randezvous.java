package synchronizationpattern;

import java.util.concurrent.Semaphore;

public class Randezvous {
    static Semaphore a, b;
    static  class A implements Runnable{
        @Override
        public void run() {
            try {
                a.release();
                b.acquire();
                System.out.println("Thread A");
            }catch (InterruptedException e) {

            }
        }
    }

    static  class B implements Runnable{
        @Override
        public void run() {
            try {
                b.release();
                a.acquire();
                System.out.println("Thread B");
            }catch (InterruptedException e) {

            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
         a = new Semaphore(0, true);
         b = new Semaphore(0 , true);
         Thread t1 = new Thread(new A());
         t1.start();
         Thread.sleep(1000);
         Thread t2 = new Thread(new B());
         t2.start();
    }
}
