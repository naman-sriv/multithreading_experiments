package prodConsumerDemo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer<E> {

    private final int capacity;
    private final Queue<E> buffer;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
    }

    public void put(E item) throws InterruptedException {
        lock.lock();
        try {
            while (buffer.size() == capacity) {
                System.out.println("Buffer is full. Producer is waiting..");
                notFull.await();
            }
            buffer.add(item);
            System.out.println("Producer add: " + item + " . Buffer size: " + buffer.size());
            notEmpty.signal();
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.isEmpty()) {
                System.out.println("Buffer is Empty. Consumer is waiting..");
                notEmpty.await();
            }
            E item = buffer.remove();
            System.out.println("Consumer took: " + item + ". Buffer size: " + buffer.size());
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }
}
