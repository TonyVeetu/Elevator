package uteevbkru;

import uteevbkru.porch.Porch;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(final String[] args) throws InterruptedException {
        //        int countOfFloors = Integer.decode(args[0]);
        //        int speed = Integer.decode(args[1]);
        //        int floorHeight = Integer.decode(args[2]);
        //        int gapOpenClose = Integer.decode(args[3]);

        int countOfFloors = 10;
        double speed = 2.3;
        double floorHeight = 2.0;
        int gapOpenClose = 2;

        Porch porch = new Porch(countOfFloors, floorHeight);

        /**
         Не может очередь быть больше количества этажей в подьезде!
         */
        int capacityOfQueue = countOfFloors;
        BlockingQueue<Integer> queueOfFloors = new ArrayBlockingQueue<Integer>(capacityOfQueue);
        AtomicBoolean isIterable = new AtomicBoolean(false);

        ElevatorOverTheGround elevatorOver = new ElevatorOverTheGround(porch, speed, gapOpenClose, queueOfFloors, isIterable);
        Thread elevator = new Thread(elevatorOver);
        elevator.start();
        Controller controller = new Controller(elevatorOver, porch, queueOfFloors, isIterable);
        controller.start();
    }
}
