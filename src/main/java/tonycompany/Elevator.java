package tonycompany;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Elevator implements Move{
    private int countOfFloors;
    private double speed;
    private double floorHeight;
    private int gapOpenClose;
    private int currentFloor = 0;
    private BlockingQueue<Integer> queueOfFloors;
    private AtomicBoolean firstInputFromConsole = new AtomicBoolean(false);


    public Elevator(int countOfFloors, double speed, int floorHeight, int gapOpenClose){
        this.countOfFloors = countOfFloors;
        this.speed = speed;
        this.floorHeight = floorHeight;
        this.gapOpenClose = gapOpenClose;
        queueOfFloors = new ArrayBlockingQueue<Integer>(countOfFloors);
    }

    public void start() throws InterruptedException {
        ConsoleIn consoleIn = new ConsoleIn(queueOfFloors, firstInputFromConsole);
        consoleIn.start();
        //Пока не будет получена хотя бы одна команда с консоли!!
        while (!queueOfFloors.isEmpty() || (!firstInputFromConsole.get())) {
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
            openAndClose(nextFloor);
        }
        consoleIn.setIsIterable();

    }

    public void up(){

    }

    public void down(){

    }

    public void openAndClose(int floor) throws InterruptedException {
        System.out.println("Open doors on " + floor + " floor!");
        Thread.sleep(gapOpenClose * 1000);
        System.out.println("Close doors on " + floor + " floor");
    }


    public static void main(String[] args){

        Elevator elevator = new Elevator(10, 2.3,2, 2);
        //elevator.init();
        try {
            elevator.start();
        }
        catch (InterruptedException e ){
            e.printStackTrace();
        }
        System.out.println("end of main!!");
    }
}
