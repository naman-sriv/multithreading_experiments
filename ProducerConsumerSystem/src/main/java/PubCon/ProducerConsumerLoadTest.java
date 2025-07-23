package PubCon;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerLoadTest {

    public static void main(String[] args) throws InterruptedException {
        final int NUM_PRODUCERS = 5;
        final int NUM_CONSUMERS = 5;
        final int TASKS_PER_PRODUCER = 10_000;
        final int BUFFER_CAPACITY = 100; // Typically matches your bounded buffer size

        // Replace with your custom system
        ProducerConsumer buffer = new ProducerConsumer(BUFFER_CAPACITY);

        Thread[] producers = new Thread[NUM_PRODUCERS];
        Thread[] consumers = new Thread[NUM_CONSUMERS];

        // For validation: a thread-safe counter to ensure all tasks are processed
        java.util.concurrent.atomic.AtomicInteger receivedCount = new java.util.concurrent.atomic.AtomicInteger(0);

        // Start producers
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            producers[i] = new Thread(() -> {
                for (int j = 0; j < TASKS_PER_PRODUCER; j++) {
                    try {
                        buffer.produce("Message-" + j);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Producer-" + i);
            producers[i].start();
        }

        // Start consumers
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            consumers[i] = new Thread(() -> {
                while (true) {
                    try {
                        Object item = buffer.consume();
                        // Optionally validate message content
                        if (item != null) {
                            receivedCount.incrementAndGet();
                        }
                    } catch (InterruptedException e) {
                        break; // Stop signal on interruption
                    }
                }
            }, "Consumer-" + i);
            consumers[i].start();
        }

        long startTime = System.currentTimeMillis();

        // Wait for all producers to finish
        for (Thread producer : producers) {
            producer.join();
        }

        // Wait for consumers to process all messages, then interrupt them to exit
        int expectedTotal = NUM_PRODUCERS * TASKS_PER_PRODUCER;
        while (receivedCount.get() < expectedTotal) {
            Thread.sleep(100);
        }

        // Stop consumers
        for (Thread consumer : consumers) {
            consumer.interrupt();
            consumer.join();
        }

        long duration = System.currentTimeMillis() - startTime;

        System.out.println("Produced:  " + (NUM_PRODUCERS * TASKS_PER_PRODUCER));
        System.out.println("Consumed:  " + receivedCount.get());
        System.out.println("Processing time: " + duration + "ms");
    }
}
