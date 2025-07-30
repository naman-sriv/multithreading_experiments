package futureExample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Demo {

    public static void main(String[] args) throws Exception {

        try(ExecutorService es = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<?>> futures = new ArrayList<>();
            for(int i=0;i<1000;i++){
                Future<?> future = es.submit(new Task());
                futures.add(future);
            }
            for(int i=0;i<1000;i++){
                try {
                    Future<?> future = futures.get(i);
                    System.out.println(future.get());
                }catch (InterruptedException e){
                    System.out.println(e.getCause().getMessage());
                    Thread.currentThread().interrupt();
                }catch (ExecutionException e){
                    System.out.println("Execution failed due to "+e.getCause().getMessage());
                }
            }
        }catch (Exception e){
            System.out.println(e.getCause().getMessage());
        }finally {
            System.out.println("ExecutorService is closed now");
        }

    }
}
