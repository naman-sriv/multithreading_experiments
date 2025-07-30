package completableFutureExample;

import exceptions.PaymentException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class PaymentGateway {

    public CompletableFuture<String> processPayment(String userId, double amount) {
        return CompletableFuture.supplyAsync(() -> {
           System.out.println(Thread.currentThread().getName()+" - Processing payment for user "+userId);
           try {
               Thread.sleep(120);
           }catch (InterruptedException e){
               Thread.currentThread().interrupt();
               throw new RuntimeException("Payment Failure", e);
           }

            if (amount > 1000.0) {
                System.err.println(Thread.currentThread().getName() + " - Payment failed: Amount too high.");
                throw new PaymentException("Payment failed: Amount too high (" + amount + ")");
            }
            if (userId.equals("blocked_user")) {
                System.err.println(Thread.currentThread().getName() + " - Payment failed: User blocked.");
                throw new PaymentException("Payment failed: User is blocked");
            }

            String transactionId = "TXN-" + ThreadLocalRandom.current().nextInt(10000, 99999);
            System.out.println(Thread.currentThread().getName() + " - Payment successful. TxId: " + transactionId);
            return transactionId;
        });
    }
}
