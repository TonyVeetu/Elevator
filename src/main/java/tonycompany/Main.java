package tonycompany;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        //        int countOfFloors = Integer.decode(args[0]);
//        int speed = Integer.decode(args[1]);
//        int floorheight = Integer.decode(args[2]);
//        int gapOpenClose = Integer.decode(args[3]);

        int countOfFloors = 10;
        double speed = 2.3;
        double floorHeight = 2.0;
        int gapOpenClose = 2;

        int capacityOfQueue = countOfFloors;//Не может очередь быть больше количества этажей в подьезде!
        BlockingQueue<Integer> queueOfFloors = new ArrayBlockingQueue<Integer>(capacityOfQueue);
        AtomicBoolean isIterable = new AtomicBoolean(false);

        Elevator elevator = new Elevator(countOfFloors, speed, floorHeight, gapOpenClose, queueOfFloors, isIterable);
        elevator.start();

        ConsoleIn consoleIn = new ConsoleIn(queueOfFloors, isIterable);
        consoleIn.start();

        System.out.println("end of main!!");
    }
}
