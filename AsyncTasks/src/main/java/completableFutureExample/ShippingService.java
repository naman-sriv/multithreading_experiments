package completableFutureExample;

import exceptions.ShippingException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class ShippingService {

    public CompletableFuture<String> generateShippingLabel(String transactionId, String address){

        return CompletableFuture.supplyAsync(() -> {
           System.out.println(Thread.currentThread().getName()+" - Generating shipping label for transaction Id "+transactionId);
           try{
               Thread.sleep(90);
           }catch (InterruptedException e){
               Thread.currentThread().interrupt();
               throw new RuntimeException("Shipping interrupted",e);
           }

            if (transactionId.endsWith("123")) { // Example: a specific transaction ID causes failure
                System.err.println(Thread.currentThread().getName() + " - Shipping failed: Problem with specific transaction.");
                throw new ShippingException("Shipping failed: Problem with transaction " + transactionId);
            }

            String trackingNumber = "TRACK-" + ThreadLocalRandom.current().nextInt(100000, 999999);
            System.out.println(Thread.currentThread().getName() + " - Shipping label generated. Tracking: " + trackingNumber);
            return trackingNumber;
        });
    }
}
