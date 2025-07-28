package prodConsumerDemo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Demo {

    public static void main(String[] args) {
        BoundedBuffer<String> buffer = new BoundedBuffer<>(10);

        Thread producer = new Thread(new Producer(buffer), "Producer-Thread");
        Thread consumer = new Thread(new Consumer(buffer), "Consumer-Thread");

        System.out.println("Starting Producer and Consumer threads..");
        producer.start();
        consumer.start();

        try{
            Thread.sleep(2000);
            producer.interrupt();
            consumer.interrupt();
            producer.join();
            consumer.join();
        }catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        System.out.println("Producer and Consumer Threads finished.");
    }
}
