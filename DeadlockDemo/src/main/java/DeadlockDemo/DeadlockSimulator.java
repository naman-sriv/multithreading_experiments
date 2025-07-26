package DeadlockDemo;

public class DeadlockSimulator {

    public static final Object resource1 = new Object();
    public static final Object resource2 = new Object();

    public static void main(String[] args){

        //Deadlock Simulator
        /*Thread thread1 = new Thread(() -> {
            synchronized (resource1){
                System.out.println("Thread 1: Locked resource 1");
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1: Waiting for resource 2");

                synchronized (resource2) {
                    System.out.println("Thread 1: Locked resource 2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource2){
                System.out.println("Thread 2: Locked resource 2");
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2: Waiting for resource 1");

                synchronized (resource1) {
                    System.out.println("Thread 2: Locked resource 1");
                }
            }
        });

        thread1.start();
        thread2.start();//Deadlock simulation
        */

        //Fix for deadlock : Ensure threads acquire lock in the same consistent order.
        Thread thread1 = new Thread(() -> {
            synchronized (resource1){
                System.out.println("Thread 1: Locked resource 1");
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1: Waiting for resource 2");

                synchronized (resource2) {
                    System.out.println("Thread 1: Locked resource 2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource1){
                System.out.println("Thread 2: Locked resource 2");
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2: Waiting for resource 1");

                synchronized (resource2) {
                    System.out.println("Thread 2: Locked resource 1");
                }
            }
        });

        thread1.start();
        thread2.start();//Fixed Deadlock simulation
    }
}
