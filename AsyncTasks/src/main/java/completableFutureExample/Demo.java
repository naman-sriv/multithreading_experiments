package completableFutureExample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Demo {

    public static void main(String[] args) throws Exception {

        OrderProcessorApp processor = new OrderProcessorApp();

        System.out.println("--- Test Case 1: Successful Order ---");
        processor.processOrder("user123", "valid", 500.0, "123 Main St, Anytown").whenComplete((trackingNumber, ex) -> {
            if (ex != null) {
                Throwable actualEx = ex.getCause() != null ? ex.getCause() : ex;
                System.err.println("Order 1 Failed: " + actualEx.getMessage());
            } else {
                System.out.println("Order 1 Success! Tracking Number: " + trackingNumber);
            }
        });

        System.out.println("\n--- Test Case 2: Unauthorized User ---");
        processor.processOrder("unauthorized", "invalid", 200.0, "456 Oak Ave, Somewhere").whenComplete((trackingNumber, ex) -> {
            if (ex != null) {
                Throwable actualEx = ex.getCause() != null ? ex.getCause() : ex;
                System.err.println("Order 2 Failed: " + actualEx.getMessage());
            } else {
                System.out.println("Order 2 Success! Tracking Number: " + trackingNumber);
            }
        });

        System.out.println("\n--- Test Case 3: Empty User ID ---");
        processor.processOrder("", "valid", 100.0, "789 Pine Ln, Nowhere").whenComplete((trackingNumber, ex) -> {
            if (ex != null) {
                Throwable actualEx = ex.getCause() != null ? ex.getCause() : ex;
                System.err.println("Order 3 Failed: " + actualEx.getMessage());
            } else {
                System.out.println("Order 3 Success! Tracking Number: " + trackingNumber);
            }
        });

        System.out.println("\n--- Test Case 4: Payment Failure (Amount too high) ---");
        processor.processOrder("user456", "valid", 1200.0, "101 High St, Big City").whenComplete((trackingNumber, ex) -> {
            if (ex != null) {
                Throwable actualEx = ex.getCause() != null ? ex.getCause() : ex;
                System.err.println("Order 4 Failed: " + actualEx.getMessage());
            } else {
                System.out.println("Order 4 Success! Tracking Number: " + trackingNumber);
            }
        });

        System.out.println("\n--- Test Case 5: Shipping Failure (Invalid Address) ---");
        processor.processOrder("user789", "valid", 300.0, "202 Invalid City Rd, Errorville").whenComplete((trackingNumber, ex) -> {
            if (ex != null) {
                Throwable actualEx = ex.getCause() != null ? ex.getCause() : ex;
                System.err.println("Order 5 Failed: " + actualEx.getMessage());
            } else {
                System.out.println("Order 5 Success! Tracking Number: " + trackingNumber);
            }
        });

        // Ensure all futures complete before exiting main
        try {
            TimeUnit.SECONDS.sleep(5); // Give time for all async tasks to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
