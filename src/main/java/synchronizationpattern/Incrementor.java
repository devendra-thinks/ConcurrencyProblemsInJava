package synchronizationpattern;

import java.util.logging.Logger;

public class Incrementor implements Runnable{
    Signal   signal;
    Logger   logger;

    public Incrementor(  Signal signal){
         this.signal = signal;
         logger = Logger.getLogger("Incrementor");
    }

    @Override
    public void run() {
          ++signal.count;
          logger.info(" incremented current count is " + signal.count);
          signal.semaphore.release();
    }
}
