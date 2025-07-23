package PubCon;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {

    private LinkedList<String> taskQueue;
    private int bufferCapacity;

    public ProducerConsumer(int bufferCapacity){
        this.taskQueue = new LinkedList<>();
        this.bufferCapacity = bufferCapacity;
    }

    public void produce(String message) throws InterruptedException {

        synchronized (this) {
            if (taskQueue.size() == bufferCapacity) {
                wait();
            }
            taskQueue.add(message);
            notifyAll();
        }

    }

    public Object consume() throws InterruptedException {

        synchronized(this) {
            if(taskQueue.isEmpty()){
                wait();
            }

            String message = taskQueue.removeFirst();
            System.out.println(message+"consumed");
            notifyAll();
            return message;
        }
    }


}
