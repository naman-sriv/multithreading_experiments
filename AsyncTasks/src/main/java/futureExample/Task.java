package futureExample;

import java.util.Random;
import java.util.concurrent.Callable;

class Task implements Callable {

    public Task() {}

    public Object call() throws Exception {

        Random random = new Random();
        Integer num = random.nextInt(10);
        Thread.sleep(num * 1000);
        return num;
    }
}
