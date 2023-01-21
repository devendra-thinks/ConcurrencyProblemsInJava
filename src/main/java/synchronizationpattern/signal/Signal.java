package synchronizationpattern.signal;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Signal {
    Semaphore semaphore;
    Logger logger;
    Integer count  = new Integer(0);

    public Signal(int permits){
        semaphore = new Semaphore(permits, true);
    }

    public static void main(String []argc) throws InterruptedException {
          Signal signal = new Signal(0);
          signal.logger  = Logger.getLogger("main");
          Incrementor incrementor = new Incrementor(signal);
          Decrementor decrementor = new Decrementor(signal);

          for(int i = 0 ; i < 1 ; i++ ){
              Thread inc = new Thread(decrementor);
              inc.start();
          }

          Thread.sleep(1000);
          signal.logger.info("main current count is  " + signal.count);

          for(int i = 0; i < 1 ; i++ ){
              Thread d = new Thread(incrementor);
              d.start();
          }
          Thread.sleep(1000);

          signal.logger.info("main current count is  " + signal.count);
    }

}
