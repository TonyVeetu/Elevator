package tonycompany;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Elevator{
    private int countOfFloors;
    private double speed;
    private double floorHeight;
    private int gapOpenClose;
    private int currentFloor = 0;
    private BlockingQueue<Integer> queueOfFloors;
    private AtomicBoolean isIterable;

    public Elevator(int countOfFloors, double speed, double floorHeight, int gapOpenClose, BlockingQueue<Integer> queueOfFloors,  AtomicBoolean isIterable){
        this.countOfFloors = countOfFloors;
        this.speed = speed;
        this.floorHeight = floorHeight;
        this.gapOpenClose = gapOpenClose;
        this.queueOfFloors = queueOfFloors;
        this.isIterable = isIterable;
    }

    public void start() throws InterruptedException {
        while (!isIterable.get()) {
            boolean up;
            int nextFloor = 0;
            try {
                nextFloor = queueOfFloors.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (nextFloor > currentFloor)
                up = true;
            else
                up = false;
            System.out.println("Current floor: " + currentFloor);

            int countOfFloor = Math.abs(nextFloor - currentFloor);
            long timeForOneFloor = (long) Math.round(floorHeight / speed) * 1000; //TODO test сделать!
            System.out.println("Time For Move in millisec " + timeForOneFloor * countOfFloor);
            long prom = timeForOneFloor;
            for (int i = 0; i < countOfFloor; i++) {
                Thread.sleep(timeForOneFloor);
                if (up)
                    System.out.println("\t" + "Elevator is near with " + (++currentFloor) + " floor");
                else
                    System.out.println("\t" + "Elevator is near with " + (--currentFloor) + " floor");
            }
            doorOpenClose(nextFloor);
        }
    }


    public void doorOpenClose(int floor) throws InterruptedException {
        System.out.println("Open doors on " + floor + " floor!");
        Thread.sleep(gapOpenClose * 1000);
        System.out.println("Close doors on " + floor + " floor");
    }

}
