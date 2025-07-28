package prodConsumerDemo;

import java.util.Random;

public class Consumer implements Runnable {

    private final BoundedBuffer<String> buffer;
    private final Random random = new Random();

    public Consumer(BoundedBuffer<String> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while(true){
                buffer.take();
                Thread.sleep(random.nextInt(100));
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
