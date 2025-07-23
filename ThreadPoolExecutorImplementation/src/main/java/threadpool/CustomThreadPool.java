package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPool {

    private BlockingQueue<Runnable> taskQueue;
    private List<WorkerThread> workerThreads;
    private volatile boolean isShutdown;

    public CustomThreadPool(int numOfThreads){
        taskQueue = new LinkedBlockingQueue<>();
        workerThreads = new ArrayList<>();
        isShutdown = false;

        for(int i=0;i<numOfThreads;i++){
            WorkerThread worker = new WorkerThread(taskQueue);
            workerThreads.add(worker);
            worker.start();
        }
    }

    public void submit(Runnable task){
        if(!isShutdown) {
            try {
                taskQueue.put(task);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }else {
            System.out.println("Cannot submit tasks after shutdown");
        }
    }

    public void shutdown() {
        isShutdown = true;

        for(WorkerThread worker : workerThreads){
            worker.interrupt();
        }

        for(WorkerThread worker : workerThreads){
            try {
                worker.join();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Thread pool shut down.");
    }
}
