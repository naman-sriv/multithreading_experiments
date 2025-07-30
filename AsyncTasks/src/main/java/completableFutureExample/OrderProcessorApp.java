package completableFutureExample;

import java.util.concurrent.CompletableFuture;

public class OrderProcessorApp {

    private final AuthService authService = new AuthService();
    private final PaymentGateway paymentGateway = new PaymentGateway();
    private final ShippingService shippingService = new ShippingService();

    public OrderProcessorApp() {}

    public CompletableFuture<String> processOrder(String userId, String token, double amount, String address){

        if(userId==null ||userId.isEmpty()){
            return CompletableFuture.failedFuture(new IllegalArgumentException("User Id cannot be empty"));
        }

        return authService.authorizeUser(userId, token)
                .thenCompose(isAuthorized -> {
                    if(!isAuthorized){
                        return CompletableFuture.failedFuture(new IllegalArgumentException("User not authorized"));
                    }
                    return paymentGateway.processPayment(userId, amount);
                }).thenCompose(transactionId -> {
                    return shippingService.generateShippingLabel(transactionId,address);
                });
    }
}
