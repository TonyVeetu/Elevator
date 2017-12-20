package tonycompany;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsoleIn extends Thread{
    private AtomicBoolean isIterable = new AtomicBoolean(false);
    private AtomicBoolean firstInput;
    private  Scanner scanner;

    public ConsoleIn(BlockingQueue<Integer> queue, AtomicBoolean firstInput){
        this.queue = queue;
        this.firstInput = firstInput;
        scanner = new Scanner(System.in);
    }

    private BlockingQueue<Integer> queue;

    public void run() {
        while (!(isIterable.get())) {
            if (scanner.hasNext()) {// я не понимаю почему поток здесь сидит!!!!
                System.out.println("\t\t\t\t"+isIterable.get());
                if(firstInput.get() == false){
                    firstInput.set(true);
                }
                String str = scanner.nextLine();
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
    public void setIsIterable(){
        isIterable.set(true);
        System.out.println("Set isIterable = "  + isIterable.get());
        // ??????????
        //scanner.close();
    }
}
