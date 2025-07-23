package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class CustomThreadPool implements AutoCloseable {

    private BlockingQueue<Runnable> taskQueue;
    private List<WorkerThread> workerThreads;
    private AtomicBoolean isShutdown;

    public CustomThreadPool(int numOfThreads, int queueCapacity){
        taskQueue = new LinkedBlockingQueue<>(queueCapacity);
        workerThreads = new ArrayList<>(numOfThreads);
        isShutdown = new AtomicBoolean(false);

        for(int i=0;i<numOfThreads;i++){
            WorkerThread worker = new WorkerThread(taskQueue,"worker-"+i);
            workerThreads.add(worker);
            worker.start();
        }
    }

    public void submit(Runnable task){
        if(!isShutdown.get()) {
            try {
                taskQueue.put(task);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                throw new RejectedExecutionException();
            }
        }else {
            throw new RejectedExecutionException("Cannot submit tasks after shutdown");
        }
    }

    public void shutdown() {
        if(!isShutdown.compareAndSet(false,true)){
            return;
        }

        for(WorkerThread worker : workerThreads){
            worker.stopWorker();
        }

        for(WorkerThread worker : workerThreads){
            try {
                worker.join();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public List<Runnable> shutdownNow() {

        if(!isShutdown.compareAndSet(false,true)){
            return List.of();
        }
        List<Runnable> notExecuted = new ArrayList<>();
        taskQueue.drainTo(notExecuted);
        for (WorkerThread worker : workerThreads) {
            worker.stopWorker();
        }

        return notExecuted;
    }

    @Override
    public void close(){
        shutdown();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        for(WorkerThread worker : workerThreads){
            long start = System.nanoTime();
            worker.join(TimeUnit.NANOSECONDS.toMillis(nanos));
            nanos -= (System.nanoTime()-start);
            if(nanos <= 0)
                return false;
        }
        return true;
    }
}
