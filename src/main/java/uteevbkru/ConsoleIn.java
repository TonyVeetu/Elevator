package uteevbkru;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsoleIn extends Thread {
    private int maxFloors;
    private AtomicBoolean isIterable;
    private Scanner scanner;
    private BlockingQueue<Integer> queue;

    public ConsoleIn(BlockingQueue<Integer> queue, AtomicBoolean isIterable, int maxFloors) {
        this.maxFloors = maxFloors;
        this.queue = queue;
        this.isIterable = isIterable;
        scanner = new Scanner(System.in);
    }

    public void run() {
        Integer currentFloor = 0;
        while (!isIterable.get()) {
            if (scanner.hasNext()) {
                String str = scanner.nextLine();
                if(!check(str, currentFloor))
                    break;
                currentFloor = getFloor(str);
                injectFloor(currentFloor);
            }
        }
        scanner.close();
    }


    private Integer getFloor(String str) {
        Integer floor = 0;
        try {
            floor = Integer.decode(str);
        } catch (NumberFormatException e) {
            //empty!
        }
        return floor;
    }

    private void injectFloor(Integer floor){
        if ((floor >= 0) && (floor <= maxFloors)) {
            try {
                queue.put(floor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("Input unreal floor!");
    }

    private boolean check(String str, int floor){
        if (str.equals("Stop") || str.equals("stop") || str.equals("стоп")){
            isIterable.set(true);
            injectFloor(floor);
            // второй поток сидит в while и его надо вытащить от туда
            // добавив что-то при вводе Stop!
            return false;
        }
        return true;
    }
}