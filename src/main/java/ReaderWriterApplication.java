import java.util.concurrent.Semaphore;

public class ReaderWriterApplication {
    static Semaphore readerSemaphore = new Semaphore(1);
    static Semaphore writerSemaphore = new Semaphore(1);
    static int readers = 0;

    static class Reader implements Runnable
    {
        @Override
        public void run() {
            try {
                readerSemaphore.acquire();
                // ++readers is not an atomic operations it consist of fetching, incrementing and then setting
                // if we allow others readers as well they will mess this up
                // thus better to have mutual exclusion on this
                ++readers;
                // we want to wait for writer to finish or block future writer while reading
                // if we lock in all readers then it would not make sense and also keep a track of permits in that case
                // which increases overhead so better take the writer permit this way
                if(readers == 1)
                    writerSemaphore.acquire();
                // release for other reader to read simultaneously
                readerSemaphore.release();
                // read peacefully with other reader -  friends
                System.out.println("Thread "+Thread.currentThread().getName() + " is READING");
                Thread.sleep(1000);
                System.out.println("Thread "+Thread.currentThread().getName() + " has finished READING");
                readerSemaphore.acquire();
                // again --reader we need exclusion
                --readers;
                //allow a writer once all readers are gone
                if(readers == 0)
                    writerSemaphore.release();
                // release
                readerSemaphore.release();
            } catch (InterruptedException e) {
               System.out.println(e.getMessage());
            }

        }
    }


    static class Writer  implements Runnable{

        // take a lock, concurrent overhead is being handled in reader
        @Override
        public void run() {
            try {
                writerSemaphore.acquire();
                // write peacefully
                System.out.println("Thread "+Thread.currentThread().getName() + " is WRITING");
                Thread.sleep(2500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has finished WRITING");
                writerSemaphore.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }
    }
    public static void main(String[] args) {
        Reader read = new Reader();
        Writer write = new Writer();
        Thread t1 = new Thread(read);
        t1.setName("thread1");
        Thread t2 = new Thread(read);
        t2.setName("thread2");
        Thread t3 = new Thread(write);
        t3.setName("thread3");
        Thread t4 = new Thread(read);
        t4.setName("thread4");
        t1.start();
        t3.start();
        t2.start();
        t4.start();
    }
}
