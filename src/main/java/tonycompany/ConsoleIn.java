package tonycompany;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsoleIn extends Thread{
    private AtomicBoolean isIterable;
    private  Scanner scanner;

    public ConsoleIn(BlockingQueue<Integer> queue, AtomicBoolean isIterable){
        this.queue = queue;
        this.isIterable = isIterable;
        scanner = new Scanner(System.in);
    }

    private BlockingQueue<Integer> queue;

    public void run() {
        while (true) {
            if (scanner.hasNext()) {
                String str = scanner.nextLine();
                if(str.equals("Stop")){
                    isIterable.set(true);
                    break;
                }
                Integer floor = Integer.decode(str);
                if ((floor >= 0) && (floor <= 10)) {
                    try {
                        queue.put(floor);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        scanner.close();
    }
}
