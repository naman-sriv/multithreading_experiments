package threadpool;

public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException{

        CustomThreadPool customThreadPool = new CustomThreadPool(10, 100);

        for(int i=0; i<10;i++){
            int taskId = i;
            customThreadPool.submit(() -> {
                System.out.println("Task: "+taskId+" is running on "+ Thread.currentThread().getName());
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " completed.");
            });
        }

        Thread.sleep(3000);

        customThreadPool.shutdown();

        System.out.println("All tasks submitted. Thread pool shut down.");
    }
}
