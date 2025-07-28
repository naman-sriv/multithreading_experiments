package rw_lock_simulator;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {

    private static final int DATA_SIZE = 1000000;
    private static final int[] sharedData = new int[DATA_SIZE];
    private static final ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock(true);
    private static final Lock readLock = rwlock.readLock();

    static {
        Random random = new Random();
        for(int i=0;i<DATA_SIZE;i++){
            sharedData[i] = random.nextInt(100);
        }
    }

    private ReadWriteLock() {}

    public static long readDataSingleThreaded() {
        long sum = 0;
        for(int i=0;i<DATA_SIZE;i++){
            sum += sharedData[i];
        }
        return sum;
    }

    static class ReaderTask implements Runnable {
        private final int start;
        private final int end;
        private long partialSum;

        public ReaderTask(int start, int end){
            this.start=start;
            this.end=end;
        }

        public long getPartialSum() {
            return partialSum;
        }

        @Override
        public void run() {
            readLock.lock();
            try {
                long localsum = 0;
                for(int i=start;i<end;i++){
                    localsum += sharedData[i];
                }
                this.partialSum = localsum;
            }
            finally {
                readLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int numThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available CPU cores: "+numThreads);

        //Single-Threaded Read Performance
        long startTimeSingle = System.nanoTime();
        long singleThreadSum = readDataSingleThreaded();
        long endTimeSingle = System.nanoTime();
        long durationSingle = TimeUnit.NANOSECONDS.toMillis(endTimeSingle-startTimeSingle);
        System.out.println("Single-threaded read time: " + durationSingle + " ms");
        System.out.println("Calculated sum (single-thread): " + singleThreadSum);

        System.out.println("\n----------------------------------------\n");


        //Concurrent Read Performance
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int chunkSize = DATA_SIZE/numThreads;
        long concurrentSum = 0;
        ReaderTask[] readerTasks = new ReaderTask[numThreads];

        long startTimeConcurrent = System.nanoTime();
        for(int i=0;i<numThreads;i++){
            int start = i*chunkSize;
            int end = (i==numThreads-1)?DATA_SIZE : (i+1)*chunkSize;
            readerTasks[i] = new ReaderTask(start, end);
            executor.submit(readerTasks[i]);
        }

        executor.shutdown();
        executor.awaitTermination(1,TimeUnit.MINUTES);

        long endTimeConcurrent = System.nanoTime();
        long durationConcurrent = TimeUnit.NANOSECONDS.toMillis(endTimeConcurrent-startTimeConcurrent);

        for (ReaderTask task : readerTasks) {
            concurrentSum += task.getPartialSum();
        }

        System.out.println("Concurrent read time (using " + numThreads + " threads): " + durationConcurrent + " ms");
        System.out.println("Calculated sum (concurrent): " + concurrentSum);

        // Verify the sums are identical
        boolean sumsMatch = (singleThreadSum == concurrentSum);
        System.out.println("\nVerification: Single-threaded sum == Concurrent sum? " + sumsMatch);

        // Calculate throughput improvement
        if (sumsMatch) {
            double speedup = (double) durationSingle / durationConcurrent;
            System.out.printf("Speedup factor: %.2f%n", speedup);
        }
    }

}
