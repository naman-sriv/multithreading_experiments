package throttling_rateLimitingLogic;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo {

    private static final int MAX_REQUESTS_PER_SECOND = 10;
    private static final long RESET_INTERVAL_MILLIS = 1000;

    private final AtomicInteger requestCounter = new AtomicInteger(0);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final ExecutorService clientExecutor = Executors.newCachedThreadPool();

    public Demo() {
        scheduler.scheduleAtFixedRate(this::resetCounter,0,RESET_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
    }

    private void resetCounter() {
        requestCounter.set(0);
    }

    public void submitClientRequest(int reqId){
        clientExecutor.submit(() -> {
            boolean processed = false;
            while(!processed) {
                if(requestCounter.incrementAndGet() <= MAX_REQUESTS_PER_SECOND){
                    System.out.println(Thread.currentThread().getName()+"-Request "+ reqId + ": PROCESSED at " + System.currentTimeMillis() % 1000 + "ms");
                    processed = true;
                    try{
                        Thread.sleep(50);
                    }catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(Thread.currentThread().getName() + " - Request " + reqId + ": Interrupted while working.");
                    }
                }else {
                    requestCounter.decrementAndGet();
                    System.out.println(Thread.currentThread().getName() + " - Request " + reqId + ": THROTTLED. Waiting... (Current count: " + requestCounter.get() + ")");
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                        System.out.println(Thread.currentThread().getName() + " - Request " + reqId + ": Interrupted while waiting for throttle.");
                        break;
                    }
                }
            }
        });
    }

    public void shutdown() {
        // Shutdown client executor first
        clientExecutor.shutdown();
        try {
            if (!clientExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                clientExecutor.shutdownNow(); // Force shutdown if tasks don't complete
            }
        } catch (InterruptedException e) {
            clientExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Shutdown scheduler
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("\n--- All executors shut down. ---");
    }

    public static void main(String[] args){
        Demo demo = new Demo();

        final int totalRequestsToSend = 50;
        System.out.println("Simulating a burst of "+totalRequestsToSend+" reqs with a limit of "+MAX_REQUESTS_PER_SECOND+" reqs/sec");

        Instant burstStartTime = Instant.now();

        for(int i=0;i<totalRequestsToSend;i++){
            demo.submitClientRequest(i+1);
            try {
                Thread.sleep(1);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        try {
            // Wait for a duration that ensures all requests *can* be processed.
            // (totalRequestsToSend / MAX_REQUESTS_PER_SECOND) * RESET_INTERVAL_MILLIS + some buffer
            long expectedMinDuration = (long) Math.ceil((double) totalRequestsToSend / MAX_REQUESTS_PER_SECOND) * RESET_INTERVAL_MILLIS + 1000;
            System.out.println("\nWaiting for all requests to complete (max " + (expectedMinDuration / 1000) + " seconds)...");
            Thread.sleep(expectedMinDuration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            demo.shutdown();
        }

        Instant burstEndTime = Instant.now();
        long totalDurationMillis = Duration.between(burstStartTime, burstEndTime).toMillis();
        System.out.printf("Total simulation duration: %d ms\n", totalDurationMillis);
        System.out.printf("Expected minimum time for %d requests at %d/s: %d ms\n",
                totalRequestsToSend, MAX_REQUESTS_PER_SECOND,
                (totalRequestsToSend / MAX_REQUESTS_PER_SECOND) * RESET_INTERVAL_MILLIS + (totalRequestsToSend % MAX_REQUESTS_PER_SECOND > 0 ? RESET_INTERVAL_MILLIS : 0) // rough calculation
        );
    }
}
