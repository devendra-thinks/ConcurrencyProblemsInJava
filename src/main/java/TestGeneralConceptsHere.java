import java.util.concurrent.Semaphore;

public class TestGeneralConceptsHere {

    static Semaphore s = new Semaphore(0 , true);
    static class Worker implements Runnable{
        @Override
        public void run() {
            try {
                s.acquire();
                System.out.println("worker saying hello");
            }catch (InterruptedException e){
                System.out.println("having fun in debugging");
            }
        }
    }

    public static void theMoreYouReleaseMoreThreadsWillAcquire(){
        s.release();
        s.release();
        Thread t1 = new Thread(new Worker());
        Thread t2 = new Thread(new Worker());
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        theMoreYouReleaseMoreThreadsWillAcquire();
    }
}
