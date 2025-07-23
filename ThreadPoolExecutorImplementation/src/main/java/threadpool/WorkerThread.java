package threadpool;

import java.util.concurrent.BlockingQueue;

public class WorkerThread extends Thread{
    private BlockingQueue<Runnable> taskQueue;

    public WorkerThread(BlockingQueue<Runnable> taskQueue){
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        try{
            while(true){
                Runnable task = taskQueue.take();
                task.run();
            }
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
