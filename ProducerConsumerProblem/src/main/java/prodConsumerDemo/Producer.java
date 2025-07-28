package prodConsumerDemo;

import java.util.Random;

public class Producer implements Runnable {
    private final BoundedBuffer<String> buffer;
    private final Random random = new Random();

    public Producer(BoundedBuffer<String> buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                String item = "Item " + (i + 1);
                buffer.put(item);
                Thread.sleep(random.nextInt(100));
            }
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
