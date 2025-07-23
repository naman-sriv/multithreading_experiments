package threadpool;

import java.util.concurrent.BlockingQueue;

public class WorkerThread extends Thread{
    private BlockingQueue<Runnable> taskQueue;
    private volatile boolean keepRunning = true;

    public WorkerThread(BlockingQueue<Runnable> taskQueue, String name){
        super(name);
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        try{
            while(keepRunning){
                Runnable task = taskQueue.take();
                task.run();
            }
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopWorker() {
        keepRunning = false;
        interrupt();
    }
}
