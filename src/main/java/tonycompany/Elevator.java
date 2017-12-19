package tonycompany;

import java.util.*;

public class Elevator implements Move{
    private int countOfFloors;
    private double speed;
    private double floorHeight;
    private int gapOpenClose;

    private int currentFloor = 0;

    private Queue<Integer> queueOfFloors;


    public Elevator(int countOfFloors, double speed, int floorHeight, int gapOpenClose){
        this.countOfFloors = countOfFloors;
        this.speed = speed;
        this.floorHeight = floorHeight;
        this.gapOpenClose = gapOpenClose;
    }

    private void init(){
        queueOfFloors = new LinkedList<Integer>();
        queueOfFloors.add(8);
        queueOfFloors.add(4);
        queueOfFloors.add(10);
    }

    public void start() throws InterruptedException{
        SecondCounter counter = new SecondCounter();
        counter.start();
        ConsoleIn consoleIn = new ConsoleIn(queueOfFloors);
        consoleIn.start();

        while(!queueOfFloors.isEmpty()){
            boolean up = false;
            int nextFloor = queueOfFloors.poll();
            if(nextFloor > currentFloor)
                up = true;
            else
                up = false;
            System.out.println("Current floor: " + currentFloor);

            int countOfFloor = nextFloor - currentFloor;
            long timeForOneFloor = (long)Math.round(floorHeight/speed)*1000; //TODO test сделать!
            System.out.println("Time For Move in millisec " + timeForOneFloor*countOfFloor);
            long prom = timeForOneFloor;
            for(int i = 0; i < countOfFloor; i++) {
                Thread.sleep(timeForOneFloor);
                if(up)
                    System.out.println("\t"+"Elevator is near with " + (++currentFloor) + " floor");
                else
                    System.out.println("\t"+"Elevator is near with " + (--currentFloor) + " floor");
            }
            openAndClose(nextFloor);
            currentFloor = nextFloor;
        }
        counter.setIsIterable();
    }

    public void up(){

    }

    public void down(){

    }

    public void openAndClose(int floor){
        System.out.println("Open doors on " + floor + " floor!");
        try {
            Thread.sleep(gapOpenClose * 1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Close doors on " + floor + " floor");
    }


    public static void main(String[] args){

        Elevator elevator = new Elevator(10, 2.3,2, 2);
        elevator.init();
        try {
            elevator.start();
        }
        catch (InterruptedException e ){
            e.printStackTrace();
        }
    }
}
