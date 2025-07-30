package completableFutureExample;

import java.util.concurrent.CompletableFuture;

public class AuthService {

    public CompletableFuture<Boolean> authorizeUser(String userId, String token){
        return CompletableFuture.supplyAsync(() -> {
           System.out.println(Thread.currentThread().getName()+" - Authorizing user "+userId);
           try{
               Thread.sleep(80);
           }catch(InterruptedException e){
               throw new RuntimeException("Authorization failed", e);
           }
           boolean authorized = !userId.equals("unauthorized") && token.equals("valid");
           System.out.println(Thread.currentThread().getName()+" - User "+userId+" authorized: "+authorized);
           return authorized;
        });
    }
}
