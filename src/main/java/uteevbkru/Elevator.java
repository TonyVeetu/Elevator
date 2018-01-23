package uteevbkru;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is elevator without Underground floors!
 */
public class Elevator extends Thread {
    private int countOfFloors;
    private double speed;
    private double floorHeight;
    private int gapOpenClose;
    private int currentFloor = 0;
    private BlockingQueue<Integer> queueOfFloors;
    private AtomicBoolean isIterable;
    private static final int MS = 1000;

    public Elevator(final int countOfFloors, final double speed, final double floorHeight, final int gapOpenClose, final BlockingQueue<Integer> queueOfFloors, final AtomicBoolean isIterable) {
        this.countOfFloors = countOfFloors;
        this.speed = speed;
        this.floorHeight = floorHeight;
        this.gapOpenClose = gapOpenClose;
        this.queueOfFloors = queueOfFloors;
        this.isIterable = isIterable;
    }

    public void run() {
        while (!isIterable.get()) {
            boolean up;
            int nextFloor = nextFloor();
            up = isUpOrDown(nextFloor);
            int floors = getCountOfFloors(nextFloor);
            if (floors > 0) {
                long timeForOneFloor = getTimeForOneFloor();
                System.out.println("Time For Move: "
                        + timeForOneFloor * floors);
                moving(floors, timeForOneFloor, up);
                doorOpenClose(nextFloor);
            }
        }
    }

    private void doorOpenClose(final int floor) {
        try {
            System.out.println("Open doors on " + floor + " floor!");
            Thread.sleep(gapOpenClose * MS);
            System.out.println("Close doors on " + floor + " floor");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int nextFloor() {
        int floor = 0;
        try {
            floor = queueOfFloors.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return floor;
    }

    private boolean isUpOrDown(final int nextFloor) {
        boolean up;
        if (nextFloor > currentFloor) {
            up = true;
        } else {
            up = false;
        }
        System.out.println("Current floor: " + currentFloor);
        return up;
    }

    public int getCountOfFloors(final int nextFloor) {
        return Math.abs(nextFloor - currentFloor);
    }

    public long getTimeForOneFloor() {
        return Math.round(floorHeight / speed) * MS;
    }

    private void moving(final int countOfFloor, final long timeForOneFloor, final boolean up) {
        for (int i = 0; i < countOfFloor; i++) {
            try {
                Thread.sleep(timeForOneFloor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            whereIsElevator(up);
        }
    }

    private void whereIsElevator(final boolean up) {
        if (up) {
            System.out.println("\t" + "Elevator is near with "
                    + (++currentFloor) + " floor");
        } else {
            System.out.println("\t" + "Elevator is near with "
                    + (--currentFloor) + " floor");
        }
    }
}
