package uteevbkru;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is elevator without Underground floors!
 */
public class Elevator extends Thread {
    private final int countOfFloors;
    private final double speed;
    private final double floorHeight;
    private int gapOpenClose;
    private int currentFloor = 0;
    private BlockingQueue<Integer> queueOfFloors;
    private AtomicBoolean isIterable;

    public Elevator(int countOfFloors, double speed, double floorHeight, int gapOpenClose, BlockingQueue<Integer> queueOfFloors,  AtomicBoolean isIterable) {
        this.countOfFloors = countOfFloors;
        this.speed = speed;
        this.floorHeight = floorHeight;
        this.gapOpenClose = gapOpenClose;
        this.queueOfFloors = queueOfFloors;
        this.isIterable = isIterable;
    }

    public void run() {
        while (!isIterable.get()){
            boolean up;
            int nextFloor = nextFloor();
            up = isUpOrDown(nextFloor);
            int countOfFloors = getCountOfFloors(nextFloor);
            if (countOfFloors > 0) {
                long timeForOneFloor = getTimeForOneFloor();
                System.out.println("Time For Move: " + timeForOneFloor * countOfFloors);
                moving(countOfFloors, timeForOneFloor, up);
                doorOpenClose(nextFloor);
            }
        }
    }

    private void doorOpenClose(int floor)  {
        try {
            System.out.println("Open doors on " + floor + " floor!");
            Thread.sleep(gapOpenClose * 1000);
            System.out.println("Close doors on " + floor + " floor");
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private int nextFloor(){
        int floor = 0;
        try {
            floor = queueOfFloors.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return floor;
    }

    private boolean isUpOrDown(int nextFloor){
        boolean up = false;
        if (nextFloor > currentFloor)
            up = true;
        else
            up = false;
        System.out.println("Current floor: " + currentFloor);
        return up;
    }

    public int getCountOfFloors(int nextFloor){
        return Math.abs(nextFloor - currentFloor);
    }

    public long getTimeForOneFloor(){
        return Math.round(floorHeight / speed) * 1000;
    }

    private void moving(int countOfFloor, long timeForOneFloor, boolean up) {
        for (int i = 0; i < countOfFloor; i++) {
            try {
                Thread.sleep(timeForOneFloor);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            whereIsElevator(up);
        }
    }

    private void whereIsElevator(boolean up) {
        if (up)
            System.out.println("\t" + "Elevator is near with " + (++currentFloor) + " floor");
        else
            System.out.println("\t" + "Elevator is near with " + (--currentFloor) + " floor");
    }
}
